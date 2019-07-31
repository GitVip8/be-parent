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
import java.util.stream.Collectors;

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

    public User findUser(Long id) {
        return userDao.findById(id).orElse(null);
    }

    public Page<User> list(Pageable pageable) {
        return userDao.findAll(pageable);
    }

    @Cacheable(value = "user-names", key = "#username")
    public User findUser(String username) {
        return userDao.findByUsername(username);
    }

    @CachePut(value = "user-names", key = "user.username")
    public User register() {
        return null;
    }

    @CachePut(value = "user-names", key = "username")
    public User groupPut(String username, Long groupId) {
        if (StringUtils.isEmpty(username)) return null;
        return groupPut(username, Collections.singletonList(groupId));
    }

    @CachePut(value = "user-names", key = "username")
    public User groupPut(String username, List<Long> groupIdList) {
        if (StringUtils.isEmpty(username)) return null;
        User user = findUser(username);
        if (groupIdList == null) return user;
        List<Group> gs = user.getGroups();
        List<Group> groups = groupIdList.stream().map(Group::new).collect(Collectors.toList());
        if (gs == null) gs = groups;
        else gs.addAll(groups);
        user.setGroups(gs);
        return userDao.save(user);
    }

    @CachePut(value = "user-names", key = "username")
    public User groupDrop(String username, Long group) {
        if (StringUtils.isEmpty(username)) return null;
        return groupDrop(username, Collections.singletonList(group));
    }

    @CachePut(value = "user-names", key = "username")
    public User groupDrop(String username, List<Long> groupIdList) {
        if (StringUtils.isEmpty(username)) return null;
        User user = findUser(username);
        List<Group> gs = user.getGroups();
        if (gs == null) return user;
        user.setGroups(gs.stream().filter(a -> !groupIdList.contains(a.getId())).collect(Collectors.toList()));
        return userDao.save(user);
    }

    @CachePut(value = "user-names", key = "username")
    public User groupMove(String username, Long fromId, Long toId) {
        if (StringUtils.isEmpty(username)) return null;
        User user = findUser(username);
        List<Group> gs = user.getGroups();
        if (gs == null || gs.size() < 1) return user;
        gs = gs.stream().map(a -> {
            if (a.getId().equals(fromId))
                return new Group(toId);
            else return a;
        }).collect(Collectors.toList());
        user.setGroups(gs);
        return userDao.save(user);
    }

    @CachePut(value = "user-names", key = "username")
    public User rolePut(String username, Long roleId) {
        if (StringUtils.isEmpty(username)) return null;
        return rolePut(username, Collections.singletonList(roleId));
    }

    @CachePut(value = "user-names", key = "username")
    public User rolePut(String username, List<Long> roleIdList) {
        if (StringUtils.isEmpty(username)) return null;
        User user = findUser(username);
        if (roleIdList == null) return user;
        List<Role> rs = user.getRoles();
        List<Role> roles = roleIdList.stream().map(Role::new).collect(Collectors.toList());
        if (rs == null) rs = roles;
        else rs.addAll(roles);
        user.setRoles(rs);
        return userDao.save(user);
    }

    @CachePut(value = "user-names", key = "username")
    public User roleDrop(String username, Long roleId) {
        if (StringUtils.isEmpty(username)) return null;
        return roleDrop(username, Collections.singletonList(roleId));
    }

    @CachePut(value = "user-names", key = "username")
    public User roleDrop(String username, List<Long> roleIdList) {
        if (StringUtils.isEmpty(username)) return null;
        User user = findUser(username);
        List<Role> rs = user.getRoles();
        if (rs == null) return user;
        user.setRoles(rs.stream().filter(a -> !roleIdList.contains(a.getId())).collect(Collectors.toList()));
        return userDao.save(user);
    }

    @CachePut(value = "user-names", key = "username")
    public User changePassword(String username, String original, String current) {
        User user = findUser(username);
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
            user = findUser(user.getUsername());
        return bCryptPasswordEncoder.matches(password, user.getPassword());
    }
}
