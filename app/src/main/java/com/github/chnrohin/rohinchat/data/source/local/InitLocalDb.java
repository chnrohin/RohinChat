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

package com.github.chnrohin.rohinchat.data.source.local;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.github.chnrohin.rohinchat.data.entity.User;
import com.github.chnrohin.rohinchat.data.source.local.sqlite.BaseDao;
import com.github.chnrohin.rohinchat.data.source.local.sqlite.IBaseDao;
import com.github.chnrohin.rohinchat.data.source.local.sqlite.helper.SQLiteBaseHelper;

/**
 * @author Rohin
 * @date 2018/10/17
 */
public class InitLocalDb implements IInitLocalDb {

    private SQLiteDatabase database;

    @Override
    public void init() {
        String dbPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/" + SQLiteBaseHelper.DATABASE_NAME + ".db";
        database = SQLiteDatabase.openOrCreateDatabase(dbPath, null);
        initUser(database);
        closeDb();
    }

    private void initUser(SQLiteDatabase database) {
        IBaseDao<User> userIBaseDao = new BaseDao<>();
        ((BaseDao<User>) userIBaseDao).init(User.class, database);
    }

    private void closeDb() {
        if (database.isOpen()) {
            database.close();
        }
    }
}
