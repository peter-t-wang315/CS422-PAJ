package cpts422PAJ.personalPlanner.services;

import cpts422PAJ.personalPlanner.entities.Tag;
import cpts422PAJ.personalPlanner.repositories.TagRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TagServiceImplTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagServiceImpl tagService;

    public TagServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("Work"));
        tags.add(new Tag("Life"));
        Mockito.when(tagRepository.findAll()).thenReturn(tags);

        Iterable<Tag> result = tagService.findAll();

        List<Tag> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertEquals(2, resultList.size());
        assertEquals("Work", resultList.get(0).getName());
        assertEquals("Life", resultList.get(1).getName());
    }

    @Test
    void findById() {
        Tag tag = new Tag("Work");
        Mockito.when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));

        Tag result = tagService.findById(1L);

        assertEquals("Work", result.getName());
    }

    @Test
    void save() {
        Tag tag = new Tag("Work");
        Mockito.when(tagRepository.save(tag)).thenReturn(tag);

        Tag result = tagService.save(tag);

        assertEquals("Work", result.getName());
    }
}