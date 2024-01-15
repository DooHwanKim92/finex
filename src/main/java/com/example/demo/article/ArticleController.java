package com.example.demo.article;


import com.example.demo.user.SiteUser;
import com.example.demo.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    private final UserService userService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "kw", defaultValue = "") String kw) {
        List<Article> articleList = this.articleService.getList(kw);
        model.addAttribute("articleList", articleList);
        return "article/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String create(ArticleForm articleForm) {
        return "article/form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String createPost(@Valid ArticleForm articleForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "article/form";
        }
        SiteUser user = this.userService.findByusername(principal.getName());
        this.articleService.create(articleForm.getSubject(), articleForm.getContent(),user);
        return "redirect:/article/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Article article = this.articleService.findById(id);
        model.addAttribute("article", article);
        return "article/detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modify(Model model, @PathVariable("id") Integer id) {
        Article article = this.articleService.findById(id);
        model.addAttribute("article", article);
        return "article/modify";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modifyPost(@Valid ArticleForm articleForm, BindingResult bindingResult, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "article/modify";
        }
        Article article = this.articleService.findById(id);
        this.articleService.modify(article, articleForm.getSubject(), articleForm.getContent());
        return "redirect:/article/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id) {
        this.articleService.remove(id);
        return "redirect:/article/list";
    }
}
