package cpts422PAJ.personalPlanner.controllers;

import cpts422PAJ.personalPlanner.entities.Tag;
import cpts422PAJ.personalPlanner.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    // Display form to create a new tag
    @RequestMapping("/addTag")
    public String addTag(Model model) {
        model.addAttribute("tag", new Tag());
        return "/create";
    }

    // Handle the creation of a new tag
    @RequestMapping("/create")
    public String createTag(Tag tag) {
        tagService.save(tag);
        return "redirect:/";
    }

}
