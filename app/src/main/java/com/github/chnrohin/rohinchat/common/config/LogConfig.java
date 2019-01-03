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

package com.github.chnrohin.rohinchat.common.config;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public abstract class LogConfig {

    private int level;

    private boolean save;

    private String fileName, pathName;

    /**
     * 日志
     */
    private static final String DEF_SAVE_PREFIX = getCurrFormatDate();
    private static String getCurrFormatDate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss", Locale.CHINA);
        sdf.format(date);
        return "".equals(date.toString()) ? System.currentTimeMillis() + "def" : date.toString();
    }

    /**
     * 保存的文件后缀名
     */
    private static final String DEF_SAVE_SUFFIX = ".log";



}
