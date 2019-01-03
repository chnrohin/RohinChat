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

import com.github.chnrohin.rohinchat.data.entity.Contacts;

/**
 * @author Rohin
 * @date 2018/07/15
 */
public class ContactsList {

    private static ContactsList sContactsList;

    private List<Contacts> mContacts;

    private ContactsList() {
        mContacts = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            Contacts contacts = new Contacts();
            contacts.setProfilePic(null);
            contacts.setNickname("机器人 #" + i + "号");
            mContacts.add(contacts);
        }
    }

    public static ContactsList getInstance() {
        if (null == sContactsList) {
            sContactsList = new ContactsList();
        }
        return sContactsList;
    }

    public List<Contacts> getContacts() {
        return mContacts;
    }

    public Contacts getContact(UUID uuid) {
        for (Contacts contacts : mContacts) {
            if (uuid.toString().equals(contacts.getUuid())) {
                return contacts;
            }
        }
        return null;
    }
}
