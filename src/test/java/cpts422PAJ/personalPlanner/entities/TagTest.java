package cpts422PAJ.personalPlanner.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TagTest {

    @Test
    void testGettersAndSetters() {
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("Test");
        tag.setDueDateIncrement(7L);

        assertEquals(1L, tag.getId());
        assertEquals("Test", tag.getName());
        assertEquals(7L, tag.getDueDateIncrement());

    }

    @Test
    void testToString() {
        Tag tag = new Tag("Test");
        tag.setId(1L);
        String expected = "Tag{id=1, name='Test'}";
        assertEquals(expected, tag.toString());
    }

    @Test
    void testEquals() {
        Tag tag1 = new Tag("Test");
        tag1.setId(1L);

        // 1. Case where both objects are the same
        assertEquals(tag1, tag1);

        // 2. Case where the object being compared to is null
        assertNotEquals(tag1, null);

        // 3. Case where the classes of the objects being compared are different
        assertNotEquals(tag1, new Object());

        Tag tag2 = new Tag("Test");
        tag2.setId(1L);

        // 4. Case where the id and name fields are equal
        assertEquals(tag1, tag2);

        Tag tag3 = new Tag("Test");
        tag3.setId(2L);

        // 5. Case where the id fields are not equal
        assertNotEquals(tag1, tag3);

        Tag tag4 = new Tag("Different");
        tag4.setId(1L);

        // 6. Case where the name fields are not equal
        assertNotEquals(tag1, tag4);
    }

    @Test
    void testHashCode() {
        Tag tag1 = new Tag("Test");
        tag1.setId(1L);

        Tag tag2 = new Tag("Test");
        tag2.setId(1L);

        Tag tag3 = new Tag("Test");
        tag3.setId(2L);

        assertEquals(tag1.hashCode(), tag2.hashCode());
        assertNotEquals(tag1.hashCode(), tag3.hashCode());
        assertNotEquals(tag2.hashCode(), tag3.hashCode());
    }
}