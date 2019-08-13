package com.be.common.service;


import com.be.common.dao.UserDao;
import com.be.common.entity.Group;
import com.be.common.entity.Role;
import com.be.common.entity.User;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户管理
 */
@Service
public class UserManager {

    private final
    UserDao userDao;

    private final
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserManager(UserDao userDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    /**
     * 查询用户
     *
     * @param id 用户ID
     * @return 用户
     */

    @Cacheable(value = "cache_users", key = "#id")
    public User findUser(Long id) {
        return userDao.findById(id).orElse(null);
    }

    public Page<User> list(Pageable pageable) {
        return userDao.findAll(pageable);
    }


    public User findUserByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public User register() {
        return null;
    }

    @CachePut(value = "cache_users", key = "userId")
    public User groupPut(Long userId, Long groupId) {
        if (userId == null) return null;
        return groupPut(userId, Collections.singletonList(groupId));
    }

    @CachePut(value = "cache_users", key = "#userId")
    public User groupPut(Long userId, List<Long> groupIdList) {
        if (userId == null) return null;
        User user = findUser(userId);
        if (groupIdList == null) return user;
        Set<Group> gs = user.getGroups();
        Set<Group> groups = groupIdList.stream().map(Group::new).collect(Collectors.toSet());
        if (gs == null) gs = groups;
        else gs.addAll(groups);
        user.setGroups(gs);
        return userDao.save(user);
    }

    @CachePut(value = "cache_users", key = "#userId")
    public User groupDrop(Long userId, Long group) {
        if (userId == null) return null;
        return groupDrop(userId, Collections.singletonList(group));
    }

    @CachePut(value = "cache_users", key = "#userId")
    public User groupDrop(Long userId, List<Long> groupIdList) {
        if (userId == null) return null;
        User user = findUser(userId);
        Set<Group> gs = user.getGroups();
        if (gs == null) return user;
        user.setGroups(gs.stream().filter(a -> !groupIdList.contains(a.getId())).collect(Collectors.toSet()));
        return userDao.save(user);
    }

    @CachePut(value = "cache_users", key = "#userId")
    public User groupMove(Long userId, Long fromId, Long toId) {
        if (userId == null) return null;
        User user = findUser(userId);
        Set<Group> gs = user.getGroups();
        if (gs == null || gs.size() < 1) return user;
        gs = gs.stream().map(a -> {
            if (a.getId().equals(fromId))
                return new Group(toId);
            else return a;
        }).collect(Collectors.toSet());
        user.setGroups(gs);
        return userDao.save(user);
    }

    @CachePut(value = "cache_users", key = "#userId")
    public User rolePut(Long userId, Long roleId) {
        if (userId == null) return null;
        return rolePut(userId, Collections.singletonList(roleId));
    }

    @CachePut(value = "cache_users", key = "#userId")
    public User rolePut(Long userId, List<Long> roleIdList) {
        if (userId == null) return null;
        User user = findUser(userId);
        if (roleIdList == null) return user;
        Set<Role> rs = user.getRoles();
        Set<Role> roles = roleIdList.stream().map(Role::new).collect(Collectors.toSet());
        if (rs == null) rs = roles;
        else rs.addAll(roles);
        user.setRoles(rs);
        return userDao.save(user);
    }

    @CachePut(value = "cache_users", key = "#userId")
    public User roleDrop(Long userId, Long roleId) {
        if (userId == null) return null;
        return roleDrop(userId, Collections.singletonList(roleId));
    }

    @CachePut(value = "cache_users", key = "#userId")
    public User roleDrop(Long userId, List<Long> roleIdList) {
        if (userId == null) return null;
        User user = findUser(userId);
        Set<Role> rs = user.getRoles();
        if (rs == null) return user;
        user.setRoles(rs.stream().filter(a -> !roleIdList.contains(a.getId())).collect(Collectors.toSet()));
        return userDao.save(user);
    }

    @CachePut(value = "cache_users", key = "#userId")
    public User changePassword(Long userId, String original, String current) {
        User user = findUser(userId);
        if (!checkPassword(user, original)) {
            throw new RuntimeException("password error!");
        }
        user.setPassword(bCryptPasswordEncoder.encode(current));
        user = userDao.save(user);
        return user;
    }

    private boolean checkPassword(User user, String password) {
        if (user == null) return false;
        if (StringUtils.isEmpty(user.getPassword()))
            user = findUser(user.getId());
        return bCryptPasswordEncoder.matches(password, user.getPassword());
    }
}
