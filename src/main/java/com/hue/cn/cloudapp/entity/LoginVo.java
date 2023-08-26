package com.hue.cn.cloudapp.entity;

/**
 * @Author:xgd
 * @Date:2023/8/25 20:44
 * @Description:
 */
public class LoginVo {
    /**
     * 登录对象
     */
        /**
         * 手机号
         */
        private String username;

        /**
         * 密码
         */
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

}
