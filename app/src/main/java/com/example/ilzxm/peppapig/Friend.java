package com.example.ilzxm.peppapig;

import cn.bmob.v3.BmobObject;

/**好友表
 * @author smile
 * @project Friend
 * @date 2016-04-26
 */
public class Friend extends BmobObject {

    private Users user;
    private Users friendUser;

    //拼音
    private transient String pinyin;

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Users getFriendUser() {
        return friendUser;
    }

    public void setFriendUser(Users friendUser) {
        this.friendUser = friendUser;
    }
}
