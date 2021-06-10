package com.wsl.service;

import com.wsl.pojo.User;

public interface UserService {
    User queryUserByName(String name);
}

