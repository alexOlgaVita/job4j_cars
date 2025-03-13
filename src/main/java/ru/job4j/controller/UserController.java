package ru.job4j.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.model.User;
import ru.job4j.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.ZoneId;
import java.util.*;

@ThreadSafe
@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getCreationPage(Model model) {
        model.addAttribute("userTimezoneDef", TimeZone.getDefault().getDisplayName());
        model.addAttribute("timezones", getTimeZones());
        return "users/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User todoUser, Model model) {
        var savedUser = userService.save(todoUser);
        if (savedUser.isEmpty()) {
            model.addAttribute("message", "Пользователь с таким логином уже существует");
            return "users/register";
        }
        return "redirect:/";
    }

    @GetMapping("/register/{login}/{password}")
    public String getUserByLoginPass(Model model, @PathVariable String login, @PathVariable String password) {
        var userOptional = userService.findByLoginPassword(login, password);
        if (userOptional.isEmpty()) {
            model.addAttribute("message", "Пользователь с указанными login и password не найден");
            return "errors/404";
        }
        model.addAttribute("user", userOptional.get());
        return "users/register";
    }

    @GetMapping("/login")
    public String getLoginPage() {

        return "users/login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, Model model, HttpServletRequest request) {
        var userOptional = userService.findByLoginPassword(user.getLogin(), user.getPassword());
        if (userOptional.isEmpty()) {
            model.addAttribute("error", "Логин или пароль введены неверно");
            return "users/login";
        }
        var session = request.getSession();
        session.setAttribute("user", userOptional.get());
        return "redirect:/posts";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/login";
    }

    private List<String> getTimeZones() {
        var zones = new ArrayList<String>();
        Set<String> timeIds = ZoneId.getAvailableZoneIds();
        Iterator<String> namesIterator = timeIds.iterator();
        while (namesIterator.hasNext()) {
            zones.add(namesIterator.next());
        }
        return zones.stream().sorted().toList();
    }
}