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

package com.github.chnrohin.rohinchat.common.util

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources

/**
 *
 * @author Rohin
 * @date 2018/11/17
 */
class AdaptScreen private constructor() {

    companion object {
        /**
         * xml布局参数参考值
         */
        private const val WIDTH = 480f
        private const val HEIGHT = 800f

        /**
         * orientation 方向标识
         */
        private const val W = 1;
        private const val H = 2;

        /**
         * 设备参数
         */
        private var nonCompatDensity = 0f
        private var nonCompatScaleDensity = 0f

        /**
         * 修改 height density
         */
        fun adaptScreen4Vertical(application: Application, activity: Activity) {
            setDensity(application, activity, H)
        }

        /**
         * 修改 width density
         */
        fun adaptScreen4Horizontal(application: Application, activity: Activity) {
            setDensity(application, activity, W)
        }

        /**
         * 修改density
         */
        private fun setDensity(application: Application, activity: Activity, orientation: Int) {
            // 获取Application的density
            val displayMetrics = application.resources.displayMetrics

            if (nonCompatDensity == 0f) {
                nonCompatDensity = displayMetrics.density
                nonCompatScaleDensity = displayMetrics.scaledDensity
                // 监听系统字体大小变化
                application.registerComponentCallbacks(object : ComponentCallbacks {
                    override fun onLowMemory() {}

                    override fun onConfigurationChanged(newConfig: Configuration?) {
                        if (newConfig != null && newConfig.fontScale > 0) {
                            nonCompatScaleDensity = application.resources.displayMetrics.scaledDensity
                        }
                    }
                })
            }

            // 获取目标density
            val targetDensity =
                    if (orientation == W) displayMetrics.widthPixels / WIDTH
                    else displayMetrics.heightPixels / (HEIGHT - getStatusBarHeight(activity))
            val targetScaleDensity = targetDensity * (nonCompatScaleDensity / nonCompatDensity)
            val targetDensityDpi = (targetDensity * 160).toInt()

//            displayMetrics.density = targetDensity
//            displayMetrics.scaledDensity = targetScaleDensity
//            displayMetrics.densityDpi = targetDensityDpi

            // 设置Activity的density
            val actDisplayMetrics = activity.resources.displayMetrics
            actDisplayMetrics.density = targetDensity
            actDisplayMetrics.scaledDensity = targetScaleDensity
            actDisplayMetrics.densityDpi = targetDensityDpi
        }

        /**
         * 获取状态栏高度
         */
        private fun getStatusBarHeight(context: Context): Int {
            val resId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
            return if (resId > 0) context.resources.getDimensionPixelSize(resId) else 60
        }

        /**
         * 取消适配
         */
        fun cancelAdaptScreen(application: Application, activity: Activity) {
            val sysDisplayMetrice = Resources.getSystem().displayMetrics
            val appDisplayMetrics = application.resources.displayMetrics
            val actDisplayMetrics = activity.resources.displayMetrics

            val systemDensity = sysDisplayMetrice.density
            val systemScaledDensity = sysDisplayMetrice.scaledDensity
            val systemDensityDpi = sysDisplayMetrice.densityDpi

            appDisplayMetrics.density = systemDensity
            appDisplayMetrics.scaledDensity = systemScaledDensity
            appDisplayMetrics.densityDpi = systemDensityDpi

            actDisplayMetrics.density = systemDensity
            actDisplayMetrics.scaledDensity = systemScaledDensity
            actDisplayMetrics.densityDpi = systemDensityDpi
        }
    }
}