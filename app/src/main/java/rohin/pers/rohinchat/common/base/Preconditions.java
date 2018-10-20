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

package rohin.pers.rohinchat.common.base;

import rohin.pers.rohinchat.common.util.TextUtils;

/**
 * @see android.support.v4.util.Preconditions
 *
 * @author Rohin
 * @date 2018/7/15
 */
public final class Preconditions {

    public static <T> T checkNotNull(T reference) {
        return checkNotNull(reference, null);
    }

    public static <T> T checkNotNull(T reference, String message) {
        if (null == reference) {
            if (TextUtils.isEmpty(message)) {
                throw new NullPointerException();
            } else {
                throw new NullPointerException(message);
            }
        }
        return reference;
    }

}
