package com.college.util;

public class Keys {

    public static String HOME_PATH = "https://myfinalyearproject.in/2022/grievenceMgmtAvanti/app/";
    //public static String HOME_PATH = "http://192.168.0.155/cproject/2022/app/GrievenceMgmtApp/app/";

    public static class Admin{
        public static String ADMIN_LOGIN = HOME_PATH + "admin/admin_login.php";
        public static String ADD_GRIEVENCE_HANDLER = HOME_PATH + "admin/add_grievence_handler.php";
        public static String ADD_NEW_BATCH = HOME_PATH + "admin/add_batch.php";
        public static String ADD_GRIEVENCE_SUBJECT = HOME_PATH + "admin/add_grievence_subject.php";

        public static String GET_ALL_FACULTY = HOME_PATH + "admin/get_all_faculty.php";
        public static String GET_ALL_STUDENT = HOME_PATH + "admin/get_all_students.php";
        public static String GET_ALL_PARENT = HOME_PATH + "admin/get_all_parents.php";

        public static String APPROVE_USER = HOME_PATH + "admin/approve_user.php";

        public static String GET_ALL_HANDLER = HOME_PATH + "admin/get_all_handler.php";
    }

    public static class Student{
        public static String STUDENT_REGISTER = HOME_PATH + "student/student_register.php";
        public static String STUDENT_LOGIN = HOME_PATH + "student/student_login.php";
    }

    public static class Parent{
        public static String PARENT_REGISTER = HOME_PATH + "parent/parent_register.php";
        public static String PARENT_LOGIN = HOME_PATH + "parent/parent_login.php";
    }

    public static class Faculty{
        public static String FACULTY_REGISTER = HOME_PATH + "faculty/faculty_register.php";
        public static String FACULTY_LOGIN = HOME_PATH + "faculty/faculty_login.php";
    }

    public static class Handler{
        public static String HANDLER_LOGIN = HOME_PATH + "handler/handler_login.php";
        public static String GET_ALL_GRIEVENCE = HOME_PATH + "handler/get_all_grievence.php";
        public static String REPLY_GRIEVENCE = HOME_PATH + "handler/reply_grievence.php";
    }

    public static class Common{
        public static String GET_ALL_BATCH_COURSE = HOME_PATH + "get_all_batch_course.php";
        public static String GET_ALL_DEPARTMENT = HOME_PATH + "get_all_department.php";
        public static String POST_GRIEVENCE = HOME_PATH + "post_grievence.php";
        public static String GET_GRIEVENCE_SUBJECT = HOME_PATH + "get_grievence_subject.php";
        public static String DOCUMENT_PATH = HOME_PATH + "uploads/grievence/";
        public static String GET_MY_GRIEVENCE = HOME_PATH + "get_my_grievence.php";
    }
}
