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

package rohin.pers.rohinchat.data.source.local.sqlite.tables;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public class UserDbSchema {

    public static final class UserTable {
        public static final String NAME = "user";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String PROFILE_PIC = "profilepic";
            public static final String PHONE = "phone";
            public static final String PASSWORD = "password";
            public static final String NICKNAME = "nickname";
            public static final String SEX = "sex";
            public static final String LOCATION = "location";
            public static final String PER_SIGNATURE = "persignature";
        }
    }

}
