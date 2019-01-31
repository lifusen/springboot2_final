package com.itfusen.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by lifusen on 2019/1/24 14:12
 */
@Data
public class User {

    private Integer id;
    private String name;
    private String phone;
    private String loginName;
    private String loginPwd;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastLoginTime;
    private String sex;
    private String status;

//    public static void main(String[] args)
//    {
//        //lombok可用
//        User user = new User();
//        user.setId(1);
//    }
}
