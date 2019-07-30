package com.be.common.service;


import com.be.common.dao.UserDao;
import com.be.common.entity.Group;
import com.be.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

@Service
public class UserManager {

    @Autowired
    UserDao userDao;

    public User findUser(Long id) {
        return userDao.findById(id).orElse(null);
    }

    @Cacheable(value = "user-names", key = "#username")
    public User findUser(String username) {
        return userDao.findByUsername(username);

    }

    @CachePut(value = "user-names", key = "user.username")
    public User register() {

        return null;
    }

    public User groupPut(String username, Group group) {
        if (StringUtils.isEmpty(username) || group == null) return null;
        return groupPut(username, Collections.singletonList(group));
    }

    public User groupPut(String username, List<Group> groupList) {
        if (StringUtils.isEmpty(username)) return null;
        User user = findUser(username);
        if (groupList == null) return user;
        List<Group> gs = user.getGroups();
        if (gs == null) gs = groupList;
        else gs.addAll(groupList);
        user.setGroups(gs);
        return userDao.save(user);
    }

    public User groupMove(User user, Group group) {
        return null;
    }

    public User groupDrop(User user, List<Group> groupList) {
        if (user == null || StringUtils.isEmpty(user.getUsername()) || groupList == null) return user;
        user = findUser(user.getUsername());

        return user;
    }
}
