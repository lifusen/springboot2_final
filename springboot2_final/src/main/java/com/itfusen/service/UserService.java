package com.itfusen.service;

import com.github.pagehelper.PageInfo;
import com.itfusen.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lifusen on 2019/1/24 14:15
 */
public interface UserService {
    int insertUser(User user);
    List<User> selectAllUser();
    PageInfo<User> selectPageUser(int page,int count);
}
