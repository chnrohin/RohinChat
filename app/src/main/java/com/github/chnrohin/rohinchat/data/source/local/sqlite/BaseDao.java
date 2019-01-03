/*
 * Copyright © Rohin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.chnrohin.rohinchat.data.source.local.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.github.chnrohin.rohinchat.common.util.LogUtils;
import com.github.chnrohin.rohinchat.data.source.local.sqlite.annotion.DbFiled;
import com.github.chnrohin.rohinchat.data.source.local.sqlite.annotion.DbTable;

/**
 * @author Rohin
 * @date 2018/7/20
 */
public class BaseDao<T> implements IBaseDao<T> {

    private SQLiteDatabase database;

    private Class<T> entity;

    /**
     * 是否初始化
     */
    private boolean initialized = false;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 频繁增删改查 - 缓存映射，提高性能。
     */
    private HashMap<String, Field> cacheMap;

    public synchronized boolean init(Class<T> entity, SQLiteDatabase database) {
        if (!initialized) {
            this.entity = entity;
            this.database = database;
            if (!database.isOpen()) {
                LogUtils.d("SQLiteDataBase is not open!");
                return false;
            }
            tableName = Objects.requireNonNull(entity.getAnnotation(DbTable.class)).value();
            if (!autoCreateTable()) {
                LogUtils.d("Create table failure!");
                return false;
            }
            initialized = true;
        }
        initCacheMap();
        return true;
    }

    /**
     * 查找真实表列，与当前实体类的字段，映射保存在cacheMap
     * 改变数据库字段或结构且未改变实体类时操作数据库将导致失败甚至崩溃，
     * 因此操作数据库前先利用（limit 1，0）查询空表，获取列名并与entity实体类中字段对比，
     * 相对应的话则存入CacheMap。
     */
    private void initCacheMap() {
        String sql = "SELECT * FROM " + tableName + " LIMIT 1,0";
        Cursor cursor = database.rawQuery(sql, null);
        cacheMap = new HashMap<>(cursor.getColumnCount());
        String[] columnNames = cursor.getColumnNames();
        Field[] fields = entity.getDeclaredFields();
        for (String columnName : columnNames) {
            Field columnField = null;
            for (Field field : fields) {
                if (columnName.equals(
                        Objects.requireNonNull(entity.getAnnotation(DbFiled.class)).value())) {
                    columnField = field;
                    break;
                }
            }
            if (columnField != null) {
                cacheMap.put(columnName, columnField);
            }
        }
        cursor.close();
    }

    /**
     * 自动建表
     * @return 是/否 - 成功
     */
    private boolean autoCreateTable() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE IF NOT EXIST ");
        stringBuilder.append(tableName);
        stringBuilder.append(" (");
        Field[] fields = entity.getDeclaredFields();
        for (Field field : fields) {
            Class type = field.getType();
            if (type == String.class) {
                stringBuilder.append(field.getAnnotation(DbFiled.class).value());
                stringBuilder.append(" TEXT,");
            } else if (type == Integer.class) {
                stringBuilder.append(field.getAnnotation(DbFiled.class).value());
                stringBuilder.append(" INTEGER,");
            } else if (type == Double.class) {
                stringBuilder.append(field.getAnnotation(DbFiled.class).value());
                stringBuilder.append(" DOUBLE,");
            } else if (type == Long.class) {
                stringBuilder.append(field.getAnnotation(DbFiled.class).value());
                stringBuilder.append(" BIGINT,");
            } else if (type == byte[].class) {
                stringBuilder.append(field.getAnnotation(DbFiled.class).value());
                stringBuilder.append(" BLOB,");
            } else {
                // 不支持的类型
                LogUtils.e("不支持的类型:" + type.toString());
                continue;
            }
            if (stringBuilder.charAt(stringBuilder.length() - 1) == ',') {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            }
            stringBuilder.append(")");
            try {
                database.execSQL(stringBuilder.toString());
            } catch (SQLException e) {
                return false;
            }
            LogUtils.d("<SQL> " + stringBuilder.toString());
        }
        return true;
    }

    @Override
    public long insert(T entity) {
        ContentValues contentValues = getValues(entity);
        return database.insert(tableName, null, contentValues);
    }

    private ContentValues getValues(T entity) {
        ContentValues contentValues = new ContentValues();
        for (Map.Entry<String, Field> fieldEntry : cacheMap.entrySet()) {
            // 表列名
            String key = fieldEntry.getKey();
            // 类字段
            Field field = fieldEntry.getValue();
            field.setAccessible(true);
            try {
                Object o = field.get(entity);
                Class type = field.getType();
                if (type == String.class) {
                    String value = (String) o;
                    contentValues.put(key, value);
                } else if (type == Integer.class) {
                    Integer value = (Integer) o;
                    contentValues.put(key, value);
                } else if (type == Double.class) {
                    Double value = (Double) o;
                    contentValues.put(key, value);
                } else if (type == Long.class) {
                    Long value = (Long) o;
                    contentValues.put(key, value);
                } else if (type == byte[].class) {
                    byte[] value = (byte[]) o;
                    contentValues.put(key, value);
                } else {
                    // 不支持的类型
                    LogUtils.e("不支持的类型:" + type.toString());
//                    continue;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return contentValues;
    }

}
