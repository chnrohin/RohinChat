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
import com.github.chnrohin.rohinchat.data.entity.Message;
import com.github.chnrohin.rohinchat.common.util.DateUtils;

/**
 * @author Rohin
 * @date 2018/07/15
 */
public class MessageList {

    private static MessageList mMessageList;

    private List<Message> mMessages;

    public static MessageList getInstance() {
        if (null == mMessageList) {
            mMessageList = new MessageList();
        }
        return mMessageList;
    }

    private MessageList() {
        mMessages = new ArrayList<>();
        long begin = DateUtils.formatDate("2008-08-08", "");
        long end = DateUtils.formatDate("2018-06-28", "");
        long begin2 = DateUtils.formatDate("2018-11-07", "");
        long end2 = DateUtils.formatDate("2018-11-09", "");
        for (int i = 1; i <= 10; i++) {
            Message message = new Message();
            if (i % 2 == 0) {
                message.setFrom(2);
                message.setDate(DateUtils.randomDate(begin, end));
            } else {
                message.setFrom(1);
                message.setDate(DateUtils.randomDate(begin2, end2));
            }
            message.setContent("这是第 #" + i + "条测试信息！");
            mMessages.add(message);
        }

        SortUtils.Companion.insertMessage(mMessages);
    }

    public List<Message> getMessages() {
        return mMessages;
    }

    public Message getMessage(UUID uuid) {
        for (Message message : mMessages) {
            if (message.getId().equals(uuid)) {
                return message;
            }
        }
        return null;
    }

}
