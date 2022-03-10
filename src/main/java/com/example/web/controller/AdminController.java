package com.example.web.controller;

import com.example.web.model.User;
import com.example.web.service.RoleService;
import com.example.web.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String getUserList(@AuthenticationPrincipal UserDetails userDetails,
                              Model model) {
        String username = userDetails.getUsername();
        User user = userService.getUserByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("userList", userService.getAllUser());
        model.addAttribute("newUser", new User());
        model.addAttribute("roleList", roleService.getAllRoles());
        return "admin";
    }

    @PostMapping(value="/add")
    public String addUser(@ModelAttribute User newUser,
                          @RequestParam(value = "checked", required = false) Long[] checked){
        if (checked == null) {
            newUser.setOneRole(roleService.getRoleByRole("USER"));
        } else {
            for (Long aLong : checked) {
                if (aLong != null) {
                    newUser.setOneRole(roleService.getRoleByID(aLong));
                }
            }
        }
        userService.addUser(newUser);
        return "redirect:/admin";
    }

    @PatchMapping(value="/edit/{id}")
    public String updateUser(@ModelAttribute User user,
                             @RequestParam(value = "checked", required = false) Long[] checked) {
        if (checked == null) {
            user.setOneRole(roleService.getRoleByRole("USER"));
            userService.updateUser(user);
        } else {
            for (Long aLong : checked) {
                if (aLong != null) {
                    user.setOneRole(roleService.getRoleByID(aLong));
                    userService.updateUser(user);
                }
            }
        }
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String getUserId(@PathVariable(value="id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }
}