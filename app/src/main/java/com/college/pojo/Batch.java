package com.college.pojo;

public class Batch {
    int b_id;
    String b_name;
    int b_status;

    public Batch(int b_id, String b_name, int b_status) {
        this.b_id = b_id;
        this.b_name = b_name;
        this.b_status = b_status;
    }

    public int getB_id() {
        return b_id;
    }

    public void setB_id(int b_id) {
        this.b_id = b_id;
    }

    public String getB_name() {
        return b_name;
    }

    public void setB_name(String b_name) {
        this.b_name = b_name;
    }

    public int getB_status() {
        return b_status;
    }

    public void setB_status(int b_status) {
        this.b_status = b_status;
    }






}
