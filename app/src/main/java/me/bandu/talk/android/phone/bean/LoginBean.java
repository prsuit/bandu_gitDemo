package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

import java.io.Serializable;

/**
 * 创建者:taoge
 * 时间：2015/12/8
 * 类描述：
 * 修改人:taoge
 * 修改时间：2015/12/8
 * 修改备注：
 */
public class LoginBean extends BaseBean implements Serializable{


    /**
     * uid : 20101
     * role : 2
     * name :
     * school :
     * avatar :
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity implements Serializable{
        private String uid;
        private String password;
        private int role;
        private String name;
        private String school;
        private String avatar;
        private String bindcode;
        private String phone;
        private boolean is_first_login;
        private String cid;
        private String state;
        private String class_name;

        public void setCid(String cid) {
            this.cid = cid;
        }

        public void setState(String state) {
            this.state = state;
        }

        public void setClass_name(String class_name) {
            this.class_name = class_name;
        }

        public String getCid() {

            return cid;
        }

        public String getState() {
            return state;
        }

        public String getClass_name() {
            return class_name;
        }

        public boolean is_first_login() {
            return is_first_login;
        }

        public void setIs_first_login(boolean is_first_login) {
            this.is_first_login = is_first_login;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getBindcode() {
            return bindcode;
        }

        public void setBindcode(String bindcode) {
            this.bindcode = bindcode;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public void setRole(int role) {
            this.role = role;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getUid() {
            return uid;
        }

        public int getRole() {
            return role;
        }

        public String getName() {
            return name;
        }

        public String getSchool() {
            return school;
        }

        public String getAvatar() {
            return avatar;
        }
    }
}
