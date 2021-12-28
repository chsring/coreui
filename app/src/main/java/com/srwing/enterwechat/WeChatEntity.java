package com.srwing.enterwechat;

import java.io.Serializable;

/**
 * Description:
 * Created by small small su
 * Date: 2021/12/25
 * Email: surao@foryou56.com
 */
public class WeChatEntity extends FoYoBaseEntity {

    public WeChatBase data;

    public static class WeChatBase implements Serializable {
        public int id;
        public String applicationTime;
        public String name;
        public String studentId;
        public String faculty;
        public String grade;
        public String specialty;

        public String backShiJian = "22:00";
        public String outShiJian = "10:00";


        public String sex;
        public String nationality;
        public String phone;
        public String idCard;
        public String counselorName;

        public String url;
        public String backTime;
        public String outTime;
        public String otherReason;
    }
}