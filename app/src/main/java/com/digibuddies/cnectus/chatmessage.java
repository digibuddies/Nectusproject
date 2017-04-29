package com.digibuddies.cnectus;

import java.util.Date;

/**
 * Created by vikram singh on 25-04-2017.
 */

public class chatmessage {

    String message;
    long time;
    String key;
    String user;
    String read;

    public chatmessage(String message,String user) {
        this.message = message;
        this.user = user;
        time= new Date().getTime();
    }

    public chatmessage(String message, String user, String read) {
        this.message = message;
        this.user = user;
        time= new Date().getTime();
        this.read = read;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTime() {
        return time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public chatmessage() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }
}
