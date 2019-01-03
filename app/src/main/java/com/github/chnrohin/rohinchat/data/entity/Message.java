/*
 * Copyright © Rohin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed mTo in writing, software
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
public class Message implements Parcelable {

    public static final int SEND = 1;

    public static final int RECEIVE = 2;

    private String mId;

    /**
     * 1. send
     * 2. receive
     */
    private int mFrom;

    private long mDate;

    private String mContent;

    public Message() {
        mId = UUID.randomUUID().toString();
    }

    public Message(Parcel source) {
        mFrom = source.readInt();
        mDate = source.readLong();
        mContent = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mFrom);
        dest.writeLong(mDate);
        dest.writeString(mContent);
    }

    public static final Parcelable.Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    public String getId() {
        return mId;
    }

    public int getFrom() {
        return mFrom;
    }

    public void setFrom(int from) {
        mFrom = from;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(long date) {
        mDate = date;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }
}
