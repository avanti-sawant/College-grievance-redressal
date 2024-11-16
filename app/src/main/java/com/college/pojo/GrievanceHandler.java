package com.college.pojo;

public class GrievanceHandler {
    private String g_id;
    private String g_name;
    private String g_email;
    private String g_phone;

    public GrievanceHandler(String g_id, String g_name, String g_email, String g_phone) {
        this.g_id = g_id;
        this.g_name = g_name;
        this.g_email = g_email;
        this.g_phone = g_phone;
    }

    public String getG_id() {
        return g_id;
    }

    public void setG_id(String g_id) {
        this.g_id = g_id;
    }

    public String getG_name() {
        return g_name;
    }

    public void setG_name(String g_name) {
        this.g_name = g_name;
    }

    public String getG_email() {
        return g_email;
    }

    public void setG_email(String g_email) {
        this.g_email = g_email;
    }

    public String getG_phone() {
        return g_phone;
    }

    public void setG_phone(String g_phone) {
        this.g_phone = g_phone;
    }
}
