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

package rohin.pers.rohinchat.data.eneity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

import rohin.pers.rohinchat.data.source.local.sqlite.annotion.DbTable;

/**
 * @author Rohin
 * @date 2018/7/15
 */
@DbTable("Contacts")
public class Contacts implements Parcelable {

    private String uuid;

    private String username;

    private String phone;

    private byte[] profilePic;

    private String nickname;

    public Contacts() {
        uuid = UUID.randomUUID().toString();
    }

    public Contacts(Parcel source) {
        int length = source.readInt();
        byte[] profilePic = null;
        if (length > 0) {
            profilePic = new byte[length];
            source.readByteArray(profilePic);
        }
        this.profilePic = profilePic;
        nickname = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (null == profilePic) {
            dest.writeInt(0);
        } else {
            dest.writeInt(profilePic.length);
            dest.writeByteArray(profilePic);
        }
        dest.writeString(nickname);
    }

    public static final Parcelable.Creator<Contacts> CREATOR = new Creator<Contacts>() {
        @Override
        public Contacts createFromParcel(Parcel in) {
            return new Contacts(in);
        }

        @Override
        public Contacts[] newArray(int size) {
            return new Contacts[size];
        }
    };

    public String getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid.toString();
    }

    public byte[] getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(byte[] profilePic) {
        this.profilePic = profilePic;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
