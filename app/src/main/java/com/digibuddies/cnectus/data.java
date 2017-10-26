package com.digibuddies.cnectus;

import java.io.Serializable;

/**
 * Created by Divya Vashisth on 3/27/2017.
 */

public class data implements Serializable {
    int p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p01;
    String uname,mp,allow;
    int aid;
    int id;
    String devid;
    String email;
    String op1;
    String op2;
    String op3;
    String op4;
    String op5;
    String op6;
    String op7;
    String op8;
    String op9;
    String op10;
    String op11;
    String op12;
    String op13;
    String op14;
    String op15;
    String op16,op01="";
    String time;

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getOp1() {
        return op1;
    }

    public void setOp1(String op1) {
        this.op1 = op1;
    }

    public String getOp2() {
        return op2;
    }

    public void setOp2(String op2) {
        this.op2 = op2;
    }

    public String getOp4() {
        return op4;
    }

    public void setOp4(String op4) {
        this.op4 = op4;
    }

    public String getOp3() {
        return op3;
    }

    public void setOp3(String op3) {
        this.op3 = op3;
    }

    public String getOp5() {
        return op5;
    }

    public void setOp5(String op5) {
        this.op5 = op5;
    }

    public String getOp6() {
        return op6;
    }

    public void setOp6(String op6) {
        this.op6 = op6;
    }

    public String getOp7() {
        return op7;
    }

    public void setOp7(String op7) {
        this.op7 = op7;
    }

    public String getOp8() {
        return op8;
    }

    public void setOp8(String op8) {
        this.op8 = op8;
    }

    public String getOp9() {
        return op9;
    }

    public void setOp9(String op9) {
        this.op9 = op9;
    }

    public String getOp10() {
        return op10;
    }

    public void setOp10(String op10) {
        this.op10 = op10;
    }

    public String getOp11() {
        return op11;
    }

    public void setOp11(String op11) {
        this.op11 = op11;
    }

    public String getOp12() {
        return op12;
    }

    public void setOp12(String op12) {
        this.op12 = op12;
    }

    public String getOp01() {
        return op01;
    }

    public void setOp01(String op01) {
        this.op01 = op01;
    }

    public String getOp14() {
        return op14;
    }

    public void setOp14(String op14) {
        this.op14 = op14;
    }

    public String getOp15() {
        return op15;
    }

    public void setOp15(String op15) {
        this.op15 = op15;
    }

    public String getOp16() {
        return op16;
    }

    public void setOp16(String op16) {
        this.op16 = op16;
    }

    public int getP10() {
        return p10;
    }

    public void setP10(int p10) {
        this.p10 = p10;
    }

    public int getP11() {
        return p11;
    }

    public void setP11(int p11) {
        this.p11 = p11;
    }

    public int getP12() {
        return p12;
    }

    public void setP12(int p12) {
        this.p12 = p12;
    }

    public int getP1() {
        return p1;
    }

    public void setP1(int p1) {
        this.p1 = p1;
    }

    public int getP2() {
        return p2;
    }

    public void setP2(int p2) {
        this.p2 = p2;
    }

    public int getP3() {
        return p3;
    }

    public void setP3(int p3) {
        this.p3 = p3;
    }

    public int getP4() {
        return p4;
    }

    public void setP4(int p4) {
        this.p4 = p4;
    }

    public int getP5() {
        return p5;
    }

    public void setP5(int p5) {
        this.p5 = p5;
    }

    public int getP6() {
        return p6;
    }

    public void setP6(int p6) {
        this.p6 = p6;
    }

    public int getP7() {
        return p7;
    }

    public void setP7(int p7) {
        this.p7 = p7;
    }

    public int getP8() {
        return p8;
    }

    public void setP8(int p8) {
        this.p8 = p8;
    }

    public int getP9() {
        return p9;
    }

    public void setP9(int p9) {
        this.p9 = p9;
    }

    public data() {
    }

    public data(int aid,String time, String uname, String email, String mp, String op1, String op2, String op3, String op4, String op5, String op6, String op7, String op8, String op9, String op10,String op01,String devid) {
        this.aid = aid;
        this.time=time;
        this.email = email;
        this.mp = mp;
        this.op1 = op1;
        this.op2 = op2;
        this.op3 = op3;
        this.op4 = op4;
        this.op5 = op5;
        this.op6 = op6;
        this.op7 = op7;
        this.op8 = op8;
        this.op9 = op9;
        this.op10 = op10;
        this.op01 = op01;
        this.uname = uname;
        this.devid=devid;
    }

    public String getDevid() {
        return devid;
    }

    public void setDevid(String devid) {
        this.devid = devid;
    }

    public String getMp() {
        return mp;
    }

    public void setMp(String mp) {
        this.mp = mp;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getP13() {
        return p13;
    }

    public void setP13(int p13) {
        this.p13 = p13;
    }

    public String getAllow() {
        return allow;
    }

    public void setAllow(String allow) {
        this.allow = allow;
    }
}



