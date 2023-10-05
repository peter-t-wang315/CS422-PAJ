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
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    // Display all tags
    @GetMapping
    public String listTags(Model model) {
        model.addAttribute("tags", tagService.findAll());
        return "tags/list";
    }

    // Display form to create a new tag
    @GetMapping("/create")
    public String createTagForm(Model model) {
        model.addAttribute("tag", new Tag());
        return "tags/create";
    }

    // Handle the creation of a new tag
    @PostMapping("/create")
    public String createTag(Tag tag) {
        tagService.save(tag);
        return "redirect:/tags";
    }

    // Handle the deletion of a tag
    @GetMapping("/delete")
    public String deleteTag(@RequestParam Long id) {
        tagService.deleteById(id);
        return "redirect:/tags";
    }

}
