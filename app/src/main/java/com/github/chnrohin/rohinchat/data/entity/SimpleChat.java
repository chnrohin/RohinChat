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

package com.github.chnrohin.rohinchat.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public class SimpleChat implements Parcelable {

    private String uuid;

    private Contacts mContacts;

    private Message mMessage;

    public SimpleChat() {
        uuid = UUID.randomUUID().toString();
    }

    public SimpleChat(Parcel in) {
        mContacts = in.readParcelable(Contacts.class.getClassLoader());
        mMessage = in.readParcelable(Message.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mContacts, flags);
        dest.writeParcelable(mMessage, flags);
    }

    public static final Parcelable.Creator<SimpleChat> CREATOR = new Creator<SimpleChat>() {
        @Override
        public SimpleChat createFromParcel(Parcel source) {
            return new SimpleChat(source);
        }

        @Override
        public SimpleChat[] newArray(int size) {
            return new SimpleChat[size];
        }
    };

    public String getId() {
        return uuid;
    }

    public void setContacts(UUID uuid) {
        this.uuid = uuid.toString();
    }

    public Contacts getContacts() {
        return mContacts;
    }

    public void setContacts(Contacts contacts) {
        mContacts = contacts;
    }

    public Message getMessage() {
        return mMessage;
    }

    public void setMessage(Message message) {
        mMessage = message;
    }
}
