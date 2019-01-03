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

package com.github.chnrohin.rohinchat.common.util;

import java.util.ArrayList;
import java.util.Comparator;

import com.github.chnrohin.rohinchat.data.entity.SimpleChat;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public final class ComparatorUtils {

    private ComparatorUtils() {
        throw new AssertionError("禁止实例化工具类！");
    }

    /**
     * 根据SimpleChat.getMessage().getDate()对容器进行整体排序的比较函数
     * 大于、等于和小于分别返回1、0和-1
     * 根据最后接收的消息日期(long型/ms)由大到小(倒序)排列
     */
    public static Comparator<SimpleChat> cpSimpleChat() {
        return (SimpleChat s1, SimpleChat s2) -> {
                if (s1.getMessage().getDate() - s2.getMessage().getDate() > 0)
                    return -1;
                else if (s1.getMessage().getDate() - s2.getMessage().getDate() == 0)
                    return 0;
                return 1;
            };
    }

    public static Comparator<ArrayList<SimpleChat>> cpSimpleChats() {
        return (ArrayList<SimpleChat> ss1, ArrayList<SimpleChat> ss2) -> {
                if (ss1.iterator().next().getMessage().getDate() -
                        ss2.iterator().next().getMessage().getDate() > 0)
                    return -1;
                else if (ss1.iterator().next().getMessage().getDate() -
                        ss2.iterator().next().getMessage().getDate() == 0)
                    return 0;
                return 1;
        };
    }

}
