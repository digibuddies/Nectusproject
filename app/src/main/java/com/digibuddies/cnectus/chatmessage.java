package com.digibuddies.cnectus;

import java.util.Date;

/**
 * Created by vikram singh on 25-04-2017.
 */

public class chatmessage {

    String message;
    long time;
    String idKey;
    String user;
    String read;

    public chatmessage(String message,String user) {
        this.message = message;
        this.user = user;
        time= new Date().getTime();
    }

    public chatmessage(String message, String user, String read,String idKey) {
        this.message = message;
        this.user = user;
        time= new Date().getTime();
        this.read = read;
        this.idKey=idKey;
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

    public String getidKey() {
        return idKey;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }
}
