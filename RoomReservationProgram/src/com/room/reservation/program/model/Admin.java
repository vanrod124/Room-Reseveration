package com.room.reservation.program.model;

public class Admin {

    private String name;

    private String code;

    private String password;


    public Admin() {
    }

    public Admin(String name, String code, String password) {
        this.name = name;
        this.code = code;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
