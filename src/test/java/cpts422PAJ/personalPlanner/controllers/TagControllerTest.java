package cpts422PAJ.personalPlanner.controllers;

import cpts422PAJ.personalPlanner.entities.Tag;
import cpts422PAJ.personalPlanner.services.TagService;
import cpts422PAJ.personalPlanner.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(TagController.class)
class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TagService tagService;

    @MockBean
    private UserService userService;

    @Mock
    private Model model;

    @InjectMocks
    private TagController tagController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(tagController).build();
    }

    @Test
    void addTag() throws Exception {

        // 1. Adding a tag should redirect to login when there is no active user
        when(userService.findActiveUser()).thenReturn(0L);

        mockMvc.perform(get("/addTag"))
                .andExpect(redirectedUrl("/login"));

        verify(userService).logOffAllUsers();
    }

    @Test
    void addTag2() throws Exception {

        // 2. Adding a tag should return add tag view
        when(userService.findActiveUser()).thenReturn(1L);

        mockMvc.perform(get("/addTag"))
                .andExpect(MockMvcResultMatchers.view().name("addTag"));
    }

    // Tag saving should redirect to home
    @Test
    void saveTag() throws Exception {
        Tag tag = new Tag();
        tag.setName("Test");

        mockMvc.perform(post("/saveTag").flashAttr("tag", tag)).andExpect(redirectedUrl("/"));

        verify(tagService).save(tag);
    }
}