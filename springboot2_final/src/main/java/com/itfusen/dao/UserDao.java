package com.itfusen.dao;

import com.itfusen.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by lifusen on 2019/1/24 14:15
 */
@Mapper
public interface UserDao {
    int insertUser(User user);
    List<User> selectAllUser();
}
