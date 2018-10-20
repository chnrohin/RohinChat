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

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public abstract class SoftkeyboardUtils {

    private static final String TAG = "SoftkeyboardUtils";

    private SoftkeyboardUtils() {throw new AssertionError("禁止通过构造器实例化此工具类！");}

    /**
     * 关闭软键盘
     * @param et        - 执行操作的控件
     * @param context   - 当前上下文
     */
    public static void closeSoftKeyBoard(EditText et, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            LogUtils.d("无法获取InputMethodManager服务");
            return;
        }
        if (imm.isActive() && et.hasFocus()) {
            if (et.getWindowToken() != null) {
                imm.hideSoftInputFromWindow(et.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 打开软键盘
     * @param et        - 执行操作的控件
     * @param context   - 当前上下文
     */
    public static void openSoftKeyBoard(EditText et, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            LogUtils.d("无法获取InputMethodManager服务");
            return;
        }
        imm.showSoftInput(et, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 判断软键盘是否打开
     * @param activity - 活动面板
     * @return
     */
    public static boolean isSoftInputShow(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (imm == null) {
                LogUtils.d("无法获取InputMethodManager服务");
                return false;
            }
            return imm.isActive() && activity.getWindow().getCurrentFocus() != null;
        }
        return false;
    }

}
