package com.college.pojo;

public class CourseDepartment {
    int cd_id;
    String cd_department, cd_course;
    int cd_status;

    public CourseDepartment(int cd_id, String cd_department, String cd_course, int cd_status) {
        this.cd_id = cd_id;
        this.cd_status = cd_status;
        this.cd_department = cd_department;
        this.cd_course = cd_course;
    }

    public int getCd_id() {
        return cd_id;
    }

    public void setCd_id(int cd_id) {
        this.cd_id = cd_id;
    }

    public String getCd_department() {
        return cd_department;
    }

    public void setCd_department(String cd_department) {
        this.cd_department = cd_department;
    }

    public String getCd_course() {
        return cd_course;
    }

    public void setCd_course(String cd_course) {
        this.cd_course = cd_course;
    }

    public int getCd_status() {
        return cd_status;
    }

    public void setCd_status(int cd_status) {
        this.cd_status = cd_status;
    }
}
