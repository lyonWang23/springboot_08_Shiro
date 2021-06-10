package com.wsl.service;

import com.wsl.mapper.UserMapper;
import com.wsl.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    //service实现类首先自动注入导入mapper类，实际注入的Mapperbean由mapper.xml决定了
    @Autowired
    UserMapper userMapper;

    @Override
    public User queryUserByName(String name) {
        return userMapper.queryUserByName(name);
    }
}

