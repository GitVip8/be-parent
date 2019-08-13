package com.be.auth;

import com.be.common.entity.User;
import com.be.common.service.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class SysUserDetailsService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(SysUserDetailsService.class);

    private final
    UserManager userManager;

    @Autowired
    public SysUserDetailsService(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userManager.findUserByUsername(s);
        if (user == null) {
            logger.debug("user '" + s + "' not found!");
            throw new UsernameNotFoundException("用户不存在");
        }
        return new UserDetail(user);
    }
}