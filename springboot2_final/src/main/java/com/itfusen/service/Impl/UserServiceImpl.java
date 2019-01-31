package com.itfusen.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itfusen.dao.UserDao;
import com.itfusen.domain.User;
import com.itfusen.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lifusen on 2019/1/24 14:23
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    @Transactional
    public int insertUser(User user) {
        return userDao.insertUser(user);
    }

    @Override
    public List<User> selectAllUser() {
        return userDao.selectAllUser();
    }

    @Override
    public PageInfo<User> selectPageUser(int page, int count) {
        PageHelper.startPage(page,count);
        List<User> userList =  userDao.selectAllUser();
        return new PageInfo<>(userList);
    }
}
