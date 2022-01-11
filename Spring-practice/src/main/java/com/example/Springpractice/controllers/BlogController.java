package com.example.Springpractice.controllers;

import com.example.Springpractice.models.Post;
import com.example.Springpractice.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlogController {
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/blog")
    public String blog(Model model) {
        model.addAttribute("title", "Блог");
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);

        return "blog";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        model.addAttribute("title", "Новая статья");
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogPostadd(@RequestParam String title, String anons, String fulltext, Model model) {
        Post post = new Post(title, anons, fulltext);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model) {
        if(!postRepository.existsById(id)) {
            return "redirect:/blog";
        }

        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Model model) {
        if(!postRepository.existsById(id)) {
            return "redirect:/blog";
        }

        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@PathVariable(value = "id") long id, @RequestParam String title, String anons, String fulltext, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(fulltext);
        postRepository.save(post);

        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogPostRemove(@PathVariable(value = "id") long id, Model model) {
//        Post post = postRepository.findById(id).orElseThrow();
        postRepository.deleteById(id);

        return "redirect:/blog";
    }
}
