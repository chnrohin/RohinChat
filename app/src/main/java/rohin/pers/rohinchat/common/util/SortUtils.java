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

import android.support.annotation.NonNull;

import java.util.Comparator;
import java.util.List;

import rohin.pers.rohinchat.data.eneity.SimpleChat;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public class SortUtils {

    private SortUtils() {
        throw new AssertionError("禁止实例化工具类！");
    }

    /**
     * 冒泡
     */
    public static void bubble(@NonNull List<SimpleChat> simpleChats) {
        long startTime = System.currentTimeMillis();
        boolean flag = true;
        for (int i = 0; i < simpleChats.size() - 1 && flag; i++) { // -1 防溢出
            flag = false;
            for (int j = 0; j < simpleChats.size() - i - 1; j++) {
                if (simpleChats.get(j).getMessage().getDate()
                        < simpleChats.get(j + 1).getMessage().getDate()) {
                    flag = true;
                    swap(simpleChats, j, j + 1);
                }
            }
        }
        long endTime = System.currentTimeMillis();
        long consumingTime = endTime - startTime;
        LogUtils.i(consumingTime > 1000 ? "SimpleChats排序耗时:" + consumingTime / 1000 + "s"
                : "SimpleChats排序耗时:" + consumingTime + "ms");
    }

    /**
     * 泛型冒泡，由大到小排序
     */
    public static <T extends Comparable<T>>  void bubbleT(@NonNull List<T> tList, Comparator<T> comparator) {
        boolean flag = true;
        int size = tList.size();
        for (int i = 0; i < size - 1 && flag; i++) {
            flag = false;
            for (int j = 0; j < size - i; j++) {
                if (comparator.compare(tList.get(j), tList.get(j + 1)) < 0) {
                    flag = true;
                    swapT(tList, j, j + 1);
                }
            }
        }
    }

    public static void quick(@NonNull List<SimpleChat> simpleChats, int left, int right) {
        if (left > right) throw new IllegalArgumentException(" Error: " + left + " > " + right);

    }

    /**
     * 快排 - 分区
     */
    private int partition(@NonNull List<SimpleChat> simpleChats, int left, int right) {
        SimpleChat pivot = simpleChats.get(left);
        int index = left + 1;
        for (int i = index; 1 <= right; i++) {
//            if (simpleChats.get(i))
        }
        return 0;
    }

    /**
     * 泛型交换
     */
    private static <T extends Comparable<T>> void swapT(@NonNull List<T> tList, int i, int j) {
        T temp = tList.get(i);
        tList.set(i, tList.get(j));
        tList.set(j, temp);
    }

    private static void swap(@NonNull List<SimpleChat> simpleChats, int i, int j) {
        SimpleChat temp = simpleChats.get(i);
        simpleChats.set(i, simpleChats.get(j));
        simpleChats.set(j, temp);
    }

}
