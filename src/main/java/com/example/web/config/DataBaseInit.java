package com.example.web.config;

import com.example.web.model.Role;
import com.example.web.model.User;
import com.example.web.service.RoleService;
import com.example.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataBaseInit {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public DataBaseInit(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    private void startDB() {
        User user = new User("user", "Ol", "Kk", "@@", "user");
        User userAdmin = new User("useradmin", "Sups", "Gg", "@gmail.", "useradmin");
        User admin = new User("admin", "Adm", "Admin", "@mail.ru", "admin");
        Role userRole = new Role("ROLE_USER");
        Role adminRole = new Role("ROLE_ADMIN");
        Set<Role> userAdminRole = new HashSet<>();
        userAdminRole.add(adminRole);
        userAdminRole.add(userRole);
        roleService.addRole(userRole);
        roleService.addRole(adminRole);
        user.setOneRole(userRole);
        admin.setOneRole(adminRole);
        userAdmin.setRoles(userAdminRole);
        userService.addUser(user);
        userService.addUser(admin);
        userService.addUser(userAdmin);
    }
}
