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

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import rohin.pers.rohinchat.common.base.Preconditions;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public final class ActivityUtils {

    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager
            , @NonNull Fragment fragment, @IdRes int containerViewId) {
        FragmentTransaction fragmentTransaction =
                Preconditions.checkNotNull(fragmentManager).beginTransaction();
        fragmentTransaction.add(containerViewId, Preconditions.checkNotNull(fragment));
        fragmentTransaction.commit();
    }

}
