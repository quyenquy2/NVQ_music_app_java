package com.quyen.musicapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TaiKhoan implements Serializable {
    @SerializedName("Id")
    @Expose
    private String Id;
    @SerializedName("Username")
    @Expose
    private String Username;
    @SerializedName("Password")
    @Expose
    private String Password;
    @SerializedName("Email")
    @Expose
    private String Email;

    public TaiKhoan() {
    }

    public TaiKhoan(String id, String username, String password, String email) {
        Id = id;
        Username = username;
        Password = password;
        Email = email;
    }

    public TaiKhoan(String username, String password, String email) {
        Username = username;
        Password = password;
        Email = email;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
