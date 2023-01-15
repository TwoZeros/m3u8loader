package ru.nodevs.m3u8loader.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.nodevs.m3u8loader.entity.LinkEntity;
import ru.nodevs.m3u8loader.services.KinescopeLoader;

@Controller
@RequiredArgsConstructor
public class LoadController {

    private final KinescopeLoader kinescopeLoader;

    @GetMapping("/")
    public String indexForm(Model model) {
        model.addAttribute("link", new LinkEntity());
        return "index";
    }

    @PostMapping("/load")
    public String load(LinkEntity link, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "index";
        }
        kinescopeLoader.load(link);
        return "endload";
    }
}
