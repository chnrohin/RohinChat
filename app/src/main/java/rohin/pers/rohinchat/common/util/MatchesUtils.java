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

package rohin.pers.rohinchat.common.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;

import java.util.ArrayList;

import rohin.pers.rohinchat.R;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public final class MatchesUtils {

    private MatchesUtils() {}

    public static boolean inputNotNull(@NonNull AppCompatEditText et) {
        if (et.getText() == null || TextUtils.isEmpty(et.getText().toString().trim())) {
            et.setError(et.getContext().getString(R.string.error_field_required));
            et.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean inputNotNull(@NonNull ArrayList<AppCompatEditText> etList) {
        for (AppCompatEditText et : etList) {
            if (et.getText() == null || TextUtils.isEmpty(et.getText().toString().trim())) {
                et.setError(et.getContext().getString(R.string.error_field_required));
                et.requestFocus();
                return false;
            }
        }
        return true;
    }

    public void setErrorToNull(@NonNull AppCompatEditText et) {
        et.setError(null);
    }

    public void setErrorToNull(@NonNull ArrayList<AppCompatEditText> etList) {
        for (AppCompatEditText et : etList) {
            et.setError(null);
        }
    }

    public static boolean isPhone(@NonNull AppCompatEditText et) {
        Context context = et.getContext();
        if (et.getText() == null || !et.getText().toString().trim().matches("^1[0-9]{10}$")) { //context.getString(R.string.is_phone)
            et.setError(context.getString(R.string.error_invalid_phone));
            et.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean isNickname(@NonNull AppCompatEditText et) {
        Context context = et.getContext();
        if (et.getText() == null || !et.getText().toString().trim().matches("^.*[^\\W]{1,14}.*$")) { //context.getString(R.string.is_nickname)
            et.setError(context.getString(R.string.error_invalid_nickname));
            et.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean isPassword(@NonNull AppCompatEditText et) {
        Context context = et.getContext();
        if (et.getText() == null || !et.getText().toString().trim().matches("^.{8,20}$")) { //context.getString(R.string.is_password)
            et.setError(context.getString(R.string.error_invalid_pasw));
            et.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean isPin(@NonNull AppCompatEditText et) {
        Context context = et.getContext();
        if (et.getText() == null || !et.getText().toString().trim().matches("^[\\d]{6}$")) { //context.getString(R.string.is_pin)
            et.setError(context.getString(R.string.error_invalid_sms_verify));
            et.requestFocus();
            return false;
        }
        return true;
    }

}
