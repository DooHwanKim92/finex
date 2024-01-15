package com.example.demo.user;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signup(UserForm userForm) {
        return "user/signup";
    }

    @PostMapping("/signup")
    public String signupPost(@Valid UserForm userForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "user/signup";
        }
        if(!userForm.getPassword1().equals(userForm.getPassword2())) {
            throw new RuntimeException("비밀번호 2개가 일치하지 않습니다.");
        }
        try {
            this.userService.signup(userForm.getUsername(), userForm.getPassword2());
        } catch(DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "user/signup";
        }catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "user/signup";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }
}
