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

package com.github.chnrohin.rohinchat.common.listener

import android.view.View

/**
 *
 * @author Rohin
 * @date 2018/11/5
 */
class OnDoubleClickListenerKt(iCallBack: ICallBack): View.OnClickListener {

    /**
     * 两次点击间隔时间
     */
    private val mInterval = 1500

    /**
     * 记录第一次点击时间
     */
    private var istClickTime: Long = 0

    /**
     * 回调
     */
    private val mICallBack: ICallBack = iCallBack

    /**
     * @see ICallBack.callBack
     */
    override fun onClick(v: View) {
        if (isDoubleClick(istClickTime, System.currentTimeMillis())) {
            mICallBack.callBack()
        }
        istClickTime = System.currentTimeMillis()
    }

    /**
     * 是否双击
     * @param istClickTime 第一次点击时间
     * @param secClickTime 第二次点击时间
     * @return 双击 -> true
     */
    private fun isDoubleClick(istClickTime: Long, secClickTime: Long): Boolean {
        return secClickTime - istClickTime < mInterval
    }

    /**
     * 回调接口
     */
    interface ICallBack {
        /**
         * 回调方法，确认为指定时间双击操作后执行。
         */
        fun callBack()
    }

}