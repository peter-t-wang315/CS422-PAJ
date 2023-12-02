package cpts422PAJ.personalPlanner.integrationTests;

import cpts422PAJ.personalPlanner.entities.Tag;
import cpts422PAJ.personalPlanner.repositories.TagRepository;
import cpts422PAJ.personalPlanner.services.TagService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TagServiceTagRepositoryTest {

    @Autowired
    private TagService tagService;

    @MockBean
    private TagRepository tagRepository;

    @Test
    public void testSaveTag() {
        Mockito.reset(tagRepository); // Resetting the mock

        Tag mockTag = new Tag();
        mockTag.setName("Urgent");
        when(tagRepository.save(any(Tag.class))).thenReturn(mockTag);

        Tag result = tagService.save(mockTag);

        assertNotNull(result);
        assertEquals("Urgent", result.getName());
        verify(tagRepository).save(any(Tag.class));
    }


}