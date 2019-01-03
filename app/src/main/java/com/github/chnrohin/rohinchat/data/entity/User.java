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

import com.github.chnrohin.rohinchat.data.source.local.sqlite.annotion.DbFiled;
import com.github.chnrohin.rohinchat.data.source.local.sqlite.annotion.DbTable;

/**
 * @author Rohin
 * @date 2018/7/15
 */
@DbTable("User")
public class User {

    @DbFiled("username")
    private String username;

    @DbFiled("phone")
    private String phone;

    @DbFiled("password")
    private String password;

    @DbFiled("nickname")
    private String nickname;

    /**
     * 0,female; 1,male.; 2.secret
     */
    @DbFiled("sex")
    private byte sex;

    /**
     * 个人头像
     */
    @DbFiled("profilePic")
    private byte[] profilePic;

    /**
     * 个性签名 personality signature | character signature
     */
    @DbFiled("perSignature")
    private String perSignature;

    /**
     * 所在地
     */
    @DbFiled("location")
    private String location;

    /**
     * 常用手机的唯一标识码
     */
    @DbFiled("imei")
    private String imei;

    public User() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(byte[] profilePic) {
        this.profilePic = profilePic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPerSignature() {
        return perSignature;
    }

    public void setPerSignature(String perSignature) {
        this.perSignature = perSignature;
    }

    public byte getSex() {
        return sex;
    }

    public void setSex(byte sex) {
        this.sex = sex;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
