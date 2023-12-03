package cpts422PAJ.personalPlanner.integrationTests;

import cpts422PAJ.personalPlanner.entities.Users;
import cpts422PAJ.personalPlanner.repositories.UserRepository;
import cpts422PAJ.personalPlanner.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceUserRepositoryTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testFindUserById() {
        Long userId = 1L;
        Users mockUser = new Users("test@example.com", "testUser", "password", "Test", "User", false);

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        Users result = userService.getUserById(userId);

        assertNotNull(result, "Result should not be null");
        assertEquals("test@example.com", result.getEmail(), "Email should match");
        assertEquals("testUser", result.getUserName(), "Username should match");

        verify(userRepository).findById(userId);
    }


}
