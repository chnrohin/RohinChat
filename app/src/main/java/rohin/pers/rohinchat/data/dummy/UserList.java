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

package rohin.pers.rohinchat.data.dummy;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rohin.pers.rohinchat.data.eneity.User;

/**
 * @author Rohin
 * @date 2018/07/15
 */
public class UserList {

    private User mUser;

    private List<User> mUsers;

    public UserList() {
        mUsers = new ArrayList<>();
        List<String> uidList = Arrays.asList("lx_12345678901", "lx_12345678902"
                , "lx_12345678903", "lx_12345678904", "lx_12345678905");
//        List<byte[]> profilePic = Arrays.asList("", "", "", "", "");
        List<String> phoneList = Arrays.asList("13112345678", "15212345678"
                , "18912345678", "18712345678", "13312345678");
        List<String> paswList = Arrays.asList("aa13112345678", "bb15212345678"
                , "cc18912345678", "dd18712345678", "ee13312345678");
        List<String> nickNameList = Arrays.asList("机器人 #1号", "机器人 #2号"
                , "机器人 #3号", "机器人 #4号", "机器人 #5号");
        List<String> perSignatureList = Arrays.asList("保健按摩Q987654321", "这个人很懒，什么都没留下"
                , "同性交友Q123456789", "我的机器人女友", "这个人很懒，什么都没留下");
        Byte[] bytes = {0, 0, 1, 0, 1};
        List<Byte> sexList = Arrays.asList(bytes);
        List<String> locationList = Arrays.asList("北京", "上海"
                , "广州", "深圳", "香港");

        for (int i = 0; i < uidList.size(); i++) {
            User user = new User();
            user.setUsername(uidList.get(i));
            user.setProfilePic(null);
            user.setPhone(phoneList.get(i));
            user.setPassword(paswList.get(i));
            user.setNickname(nickNameList.get(i));
            user.setPerSignature(perSignatureList.get(i));
            user.setSex(sexList.get(i));
            user.setLocation(locationList.get(i));
            mUsers.add(user);
        }
    }

    public List<User> getUsers() {
        return mUsers;
    }

    public User getUser(@NonNull String phone) {
        for (User user : mUsers) {
            if (phone.equals(user.getPhone())) {
                return user;
            }
        }
        return null;
    }
}
