package com.college.pojo;

public class GrievenceDetail {
    String g_id;
    String g_type;
    String g_u_id;
    String gs_id;
    String g_name;
    String g_phone;
    String g_description;
    String g_photo;
    String g_reply_id;
    String g_reply;
    String g_time;

    public GrievenceDetail(String g_id, String g_type, String g_u_id, String gs_id, String g_name, String g_phone, String g_description, String g_photo, String g_reply_id, String g_reply, String g_time) {
        this.g_id = g_id;
        this.g_type = g_type;
        this.g_u_id = g_u_id;
        this.gs_id = gs_id;
        this.g_name = g_name;
        this.g_phone = g_phone;
        this.g_description = g_description;
        this.g_photo = g_photo;
        this.g_reply_id = g_reply_id;
        this.g_reply = g_reply;
        this.g_time = g_time;
    }

    public String getG_id() {
        return g_id;
    }

    public void setG_id(String g_id) {
        this.g_id = g_id;
    }

    public String getG_type() {
        return g_type;
    }

    public void setG_type(String g_type) {
        this.g_type = g_type;
    }

    public String getG_u_id() {
        return g_u_id;
    }

    public void setG_u_id(String g_u_id) {
        this.g_u_id = g_u_id;
    }

    public String getGs_id() {
        return gs_id;
    }

    public void setGs_id(String gs_id) {
        this.gs_id = gs_id;
    }

    public String getG_name() {
        return g_name;
    }

    public void setG_name(String g_name) {
        this.g_name = g_name;
    }

    public String getG_phone() {
        return g_phone;
    }

    public void setG_phone(String g_phone) {
        this.g_phone = g_phone;
    }

    public String getG_description() {
        return g_description;
    }

    public void setG_description(String g_description) {
        this.g_description = g_description;
    }

    public String getG_photo() {
        return g_photo;
    }

    public void setG_photo(String g_photo) {
        this.g_photo = g_photo;
    }

    public String getG_reply_id() {
        return g_reply_id;
    }

    public void setG_reply_id(String g_reply_id) {
        this.g_reply_id = g_reply_id;
    }

    public String getG_reply() {
        return g_reply;
    }

    public void setG_reply(String g_reply) {
        this.g_reply = g_reply;
    }

    public String getG_time() {
        return g_time;
    }

    public void setG_time(String g_time) {
        this.g_time = g_time;
    }
}
