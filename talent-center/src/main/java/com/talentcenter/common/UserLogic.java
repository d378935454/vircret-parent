package com.talentcenter.common;

import com.talentcenter.entity.User;
import com.talentcenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.List;

@Component
public class UserLogic {

    @Autowired
    private UserService userService;

    /**
     * 添加用户
     * @param user
     * @return
     */
    public boolean addUser(User user){
        int res = userService.insertSelective(user);
        if(res>0) return true;
        else return false;
    }

    /**
     * 删除用户
     * @param userId 用户ID
     * @return
     */
    public boolean delUser(Long userId){
        User u = new User();
        u.setUserId(userId);
        u.setDel(0);
        int res = userService.updateByPrimaryKeySelective(u);
        if (res>0) return true;
        else return false;
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    public boolean updateUser(User user){
        int res = userService.updateByPrimaryKeySelective(user);
        if (res>0) return true;
        else return false;
    }

    /**
     * 用户LIST
     * @param user
     * @return
     */
    public List<User> getUsers(User user){
        List<User> users = userService.selectByInfo(user);
        return users;
    }
}
