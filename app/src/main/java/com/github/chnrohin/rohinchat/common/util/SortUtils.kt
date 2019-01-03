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

import com.github.chnrohin.rohinchat.data.entity.Message
import com.github.chnrohin.rohinchat.data.entity.SimpleChat

/**
 * @author Rohin
 * @date 2018/11/1
 */

class SortUtils private constructor() {

    companion object {

        /**
         * 冒泡
         */
        fun bubble(simpleChats: MutableList<SimpleChat>) {
            val length = simpleChats.size
            var flag = true
            for (i in 0..length) {
                if (flag) {
                    flag = false
                    for (j in 0 until length - 1 - i) {
                        if (simpleChats[j].message.date < simpleChats[j + 1].message.date) {
                            swap(simpleChats, j, j + 1)
                            flag = true
                        }
                    }
                }
            }
        }

        /**
         * 快速
         */
        fun quick(simpleChats: MutableList<SimpleChat>) {
            partition(simpleChats, 0, simpleChats.size - 1)
        }

        /**
         * 快速 - 分区
         */
        private fun partition(simpleChats: MutableList<SimpleChat>, start: Int, end: Int) {
            if (start > end) return
            val pivotObj = simpleChats[start]
            val pivotLong = simpleChats[start].message.date
            var i = start
            var j = end
            while (i < j) {
                while (i < j && simpleChats[j].message.date <= pivotLong) {
                    j--
                }
                simpleChats[i] = simpleChats[j]

                while (i < j && simpleChats[i].message.date >= pivotLong) {
                    i++
                }
                simpleChats[j] = simpleChats[i]
            }
            simpleChats[i] = pivotObj
            partition(simpleChats, start, i - 1)
            partition(simpleChats, i + 1, end)
        }

        /**
         * 插入
         */
        fun insertSimpleChat(simpleChats: MutableList<SimpleChat>) {
            val length = simpleChats.size - 1
            for (i in 1..length step 1) {
                var j = i
                while (j > 0 && simpleChats[j].message.date >= simpleChats[j - 1].message.date) {
                    swap(simpleChats, j, j - 1)
                    j--
                }
            }
        }

        fun insertMessage(messages: MutableList<Message>) {
            val length = messages.size - 1
            for (i in 1..length step 1) {
                var j = i
                while (j > 0 && messages[j].date >= messages[j - 1].date) {
                    swap(messages, j, j - 1)
                    j--
                }
            }
        }


        /**
         * 交换
         */
        fun <T> swap(ts: MutableList<T>, i: Int, j: Int) {
            val temp = ts[i]
            ts[i] = ts[j]
            ts[j] = temp
        }
    }
}