package com.imooc.pojo.vo;

/**
 * @author erpljq
 * @date 2018/10/8
 */
public class AdminUser {

    private String userToken;
    private String username;
    private String password;

    public AdminUser() {
    }

    public AdminUser(String username, String password, String userToken) {
        this.userToken = userToken;
        this.username = username;
        this.password = password;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

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
