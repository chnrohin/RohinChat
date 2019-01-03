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

package com.github.chnrohin.rohinchat.data.dummy;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.github.chnrohin.rohinchat.common.util.SortUtils;
import com.github.chnrohin.rohinchat.data.entity.Contacts;
import com.github.chnrohin.rohinchat.data.entity.Message;
import com.github.chnrohin.rohinchat.data.entity.SimpleChat;
import com.github.chnrohin.rohinchat.common.util.DateUtils;

/**
 * @author Rohin
 * @date 2018/07/15
 */
public class SimpleChatList {

    private static SimpleChatList sSimpleChatList;

    private List<SimpleChat> mSimpleChats;

    public static SimpleChatList getInstance() {
        if (sSimpleChatList == null) {
            sSimpleChatList = new SimpleChatList();
        }
        return sSimpleChatList;
    }

    private SimpleChatList() {
        mSimpleChats = new ArrayList<>();
        /*
        Resources resources = context.getResources();
        Bitmap bmp = BitmapFactory.decodeResource(resources, R.drawable.ic_bot_nav_chat_slcted_24dp);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Bitmap bitmap = ((BitmapDrawable)context.getResources().getXMLDrawable(R.drawable.ic_bot_nav_chat_slcted_24dp, null)).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byteArrayOutputStream.toByteArray();
        */
        long begin = DateUtils.formatDate("2008-08-08", "");
        long end = DateUtils.formatDate("2018-06-28", "");
        for (int i = 1; i<= 100; i++) {
            SimpleChat simpleChat = new SimpleChat();
            Contacts contacts = new Contacts();
            contacts.setNickname("机器人 #" + i + "号");
            Message message = new Message();
            message.setDate(DateUtils.randomDate(begin, end));
            message.setContent("这是第 #" + i + "条测试信息。");
            simpleChat.setContacts(contacts);
            simpleChat.setMessage(message);
            mSimpleChats.add(simpleChat);
        }

        SimpleChat simpleChat1 = new SimpleChat();
        Contacts contacts1 = new Contacts();
        contacts1.setNickname("机器人 #101号");
        Message message1 = new Message();
        message1.setDate(System.currentTimeMillis());
        message1.setContent("这是第 #101条测试信息。");
        simpleChat1.setContacts(contacts1);
        simpleChat1.setMessage(message1);

        SimpleChat simpleChat2 = new SimpleChat();
        Contacts contacts2 = new Contacts();
        contacts2.setNickname("机器人 #102号");
        Message message2 = new Message();
        message2.setDate(System.currentTimeMillis() - (24 * 60 * 60 * 1000));
        message2.setContent("这是第 #102条测试信息。");
        simpleChat2.setContacts(contacts2);
        simpleChat2.setMessage(message2);

        SimpleChat simpleChat3 = new SimpleChat();
        Contacts contacts3 = new Contacts();
        contacts3.setNickname("机器人 #103号");
        Message message3 = new Message();
        message3.setDate(System.currentTimeMillis() - (48 * 60 * 60 * 1000));
        message3.setContent("这是第 #103条测试信息。");
        simpleChat3.setContacts(contacts3);
        simpleChat3.setMessage(message3);

        mSimpleChats.add(simpleChat1);
        mSimpleChats.add(simpleChat2);
        mSimpleChats.add(simpleChat3);

//        首页 - 聊天信息排序
        SortUtils.Companion.insertSimpleChat(mSimpleChats);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            Collections.sort(mSimpleChats, Comparator.comparing(
//                    (SimpleChat sc) -> sc.getMessage().getDate()).reversed()
//            );
//        }
    }

    public List<SimpleChat> getSimpleChats() {
        return mSimpleChats;
    }

    public SimpleChat getSimpleChat(UUID id) {
        for (SimpleChat simpleChat : mSimpleChats) {
            if (simpleChat.getId().equals(id.toString())) {
                return simpleChat;
            }
        }
        return null;
    }
}
