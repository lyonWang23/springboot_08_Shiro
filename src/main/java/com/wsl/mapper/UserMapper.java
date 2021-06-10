package com.wsl.mapper;

import com.wsl.pojo.User;
import org.apache.catalina.LifecycleState;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    List<User> queryUserList();

    User queryUserById(int id);

    int addUser(User user);

    int updateUser(User user);

    int deleteUser(int id);

    User queryUserByName(String name);
}
