package cpts422PAJ.personalPlanner.services;

import cpts422PAJ.personalPlanner.entities.Users;
import cpts422PAJ.personalPlanner.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;
    Users testUser;
    Users testUser2;
    Users testUser3;
    Users testUser4;
    Users adminUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        testUser = new Users("test.pore@wsu.edu", "testUserName", "testUserPass","testF", "testL", false);
        testUser2 = new Users("test.wsu.edu", "test2Name", "test2Pass", "John", "Test2L", false);
        testUser3 = new Users("test3.wsu.edu", "test3Name", "test3Pass", "David", "Blaine", false);
        testUser4 = new Users("tes4.wsu.edu", "testUserName", "test2Pass", "John", "Test2L", false);
        adminUser = new Users("testAdmin.wsu.edu", "testAdminName", "testAdminPass", "testAdminF", "testAdminL", true);
    }

    @Test
    void findAll() {

        List<Users> userList = Arrays.asList(testUser, testUser2);
        when(userRepository.findAll()).thenReturn(userList);
        Iterable<Users> users = userService.findAll();

        List<Users> usersList = (List<Users>) users;
        assertEquals(userList,usersList);
    }

    @Test
    void getUserById() {
        //Should be the very first item in the setup, so testUser


        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(testUser));
        Optional<Users> idUser = Optional.ofNullable(userService.getUserById(1L));
        assertEquals(Optional.of( testUser), idUser);


    }

    @Test
    void save() {
        when(userRepository.save(testUser)).thenReturn(testUser);
        Users savedUser = userService.save(testUser);
        assertEquals((testUser), savedUser);

    }

    @Test
    void isUser() {
        when(userRepository.findAll()).thenReturn(List.of(testUser));
        boolean checkifUser = userService.isUser("testUserName", "testUserPass");
        boolean notUser = userService.isUser("testUserName", " TestUserPass");
        boolean notUser2 = userService.isUser("nope", "testUserPass");
        boolean notUser3 = userService.isUser("no", "tess");

        assertTrue(checkifUser);
        assertFalse(notUser);
        assertFalse(notUser2);
        assertFalse(notUser3);
    }

    @Test
    void changeUserActivity() {

        when(userRepository.findAll()).thenReturn(List.of(testUser));
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        userService.changeUserActivity("testUserName", "testUserPass");
        verify(userRepository).save(testUser);
    }

    @Test
    void changeUserActivity2() {

        when(userRepository.findAll()).thenReturn(List.of(testUser));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        userService.changeUserActivity("testUsName", "stUserPass");

        verify(userRepository, never()).save(testUser);
    }

    @Test
    void findActiveUser() {
        List<Users> users = new ArrayList<>();
        users.add(new Users("Tes@gmail.com", "Tes","Tes", "tes", "tes", false));
        users.add(new Users("Tes2@gmail.com", "Tes2","Tes2", "tes2", "tes2", false));
        users.add(new Users("Tes3@gmail.com", "Tes3","Tes3", "tes3", "tes3", false));

        when(userRepository.findAll()).thenReturn(users);
        users.get(0).setCurrUser(true);
        Long result = userService.findActiveUser();

        assertEquals(1L, result);

    }

    @Test
    void findActiveUser2() {
        List<Users> users = new ArrayList<>();
        users.add(new Users("Tes@gmail.com", "Tes","Tes", "tes", "tes", false));
        users.add(new Users("Tes2@gmail.com", "Tes2","Tes2", "tes2", "tes2", false));
        users.add(new Users("Tes3@gmail.com", "Tes3","Tes3", "tes3", "tes3", false));

        when(userRepository.findAll()).thenReturn(users);
        Long result = userService.findActiveUser();

        assertEquals(0L, result);

    }

    @Test
    void logOffUser() {
        when(userRepository.findAll()).thenReturn(List.of(testUser));
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        testUser.setCurrUser(true);
        userService.logOffUser();
        verify(userRepository).save(testUser);
    }

    @Test
    void logOffUser2() {
        when(userRepository.findAll()).thenReturn(List.of(testUser));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        userService.logOffUser();
        verify(userRepository, never()).save(testUser);
    }

    @Test
    void logOffAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(testUser));
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        testUser.setCurrUser(true);
        userService.logOffAllUsers();
        verify(userRepository).save(testUser);
    }

    @Test
    void logOffAllUsers2() {
        when(userRepository.findAll()).thenReturn(List.of(testUser));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        userService.logOffAllUsers();
        verify(userRepository, never()).save(testUser);
    }


    @Test
    void isCommonName() {
        assertTrue(userService.isCommonName("John"));
    }
    @Test
    void isCommonName2() {
        assertFalse(userService.isCommonName("Johnny"));
    }

    @Test
    void m_z() {
        assertTrue(userService.m_z("Mona"));
    }

    @Test
    void m_z2() {
        assertFalse(userService.m_z("Cona"));
    }

    @Test
    void notUnique() { //admin branch
        when(userRepository.findAll()).thenReturn(List.of(testUser));
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        testUser.setCurrUser(true);
        testUser.setAdmin(true);
        Long typeOfUnique = userService.notUnique();
        assertEquals(1000000L, typeOfUnique);
    }

    @Test
    void notUnique2() { //there are no current users branch
        when(userRepository.findAll()).thenReturn(List.of(testUser));
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        Long typeOfUnique = userService.notUnique();

        assertEquals(-1L, typeOfUnique);
    }
    @Test
    void notUnique3() { //Goes into else, straight to save
        when(userRepository.findAll()).thenReturn(List.of(testUser));
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        testUser.setCurrUser(true);
        Long typeOfUnique = userService.notUnique();

        assertEquals(-1, typeOfUnique);
    }

    @Test
    void notUnique4() { //Goes into else, and then goes into if and then next if
        when(userRepository.findAll()).thenReturn(List.of(testUser2));
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser2));
        testUser2.setCurrUser(true);
        Long typeOfUnique = userService.notUnique();

        assertEquals(10L, typeOfUnique);
    }

    @Test
    void notUnique5() { //Goes into else, and then goes into if and returns 5
        when(userRepository.findAll()).thenReturn(List.of(testUser3));
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser3));
        testUser3.setCurrUser(true);
        Long typeOfUnique = userService.notUnique();

        assertEquals(5L, typeOfUnique);
    }

    @Test
    void checkSameUser() {
        when(userRepository.findAll()).thenReturn(List.of(testUser,testUser4));
        boolean ifSameUser = userService.checkSameUser("testUserName");
        assertFalse(ifSameUser);

    }
    @Test
    void checkSameUser2() {
        when(userRepository.findAll()).thenReturn(List.of(testUser,testUser4));
        boolean ifSameuser = userService.checkSameUser("testUseName");
        assertTrue(ifSameuser);
    }





    @Test
    void getAdminPassword() {
        when(userRepository.findAll()).thenReturn(List.of(testUser));
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        String password = userService.getAdminPassword();
        assertEquals(testUser.getAdminPassword(), password);
    }



//    @Test
//    void checkIfAdmin() { //is an admin
//        List<Users> users = new ArrayList<>();
//        users.add(new Users("Tes@gmail.com", "Tes","Tes", "tes", "tes", true));
//        users.add(new Users("Tes2@gmail.com", "Tes2","Tes2", "tes2", "tes2", false));
//        users.add(new Users("Tes3@gmail.com", "Tes3","Tes3", "tes3", "tes3", false));
//
//    when(userRepository.findAll()).thenReturn(users);
//    users.get(0).setCurrUser(true);
//
//    when(userService.findActiveUser()).thenReturn(1L);
//    when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(users.get(0)));
//    boolean ifAdmin = userService.checkIfAdmin();
//    assertTrue(ifAdmin);
//
//    }


//    @Test
//    void checkIfAdmin() { //is an admin
//        List<Users> users = new ArrayList<>();
//        users.add(new Users("Tes@gmail.com", "Tes","Tes", "tes", "tes", true));
//        users.add(new Users("Tes2@gmail.com", "Tes2","Tes2", "tes2", "tes2", false));
//        users.add(new Users("Tes3@gmail.com", "Tes3","Tes3", "tes3", "tes3", false));
//
//        when(userRepository.findAll()).thenReturn(users);
//        users.get(0).setCurrUser(true);
//
//        when(userService.findActiveUser()).thenReturn(1L);
//        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(users.get(0)));
//        boolean ifAdmin = userService.checkIfAdmin();
//        assertTrue(ifAdmin);
//
//    }

    @Test
    void checkIfAdmin() { //is regular

//        when(userService.findActiveUser()).thenReturn(1L);
        when(userRepository.findAll()).thenReturn(List.of(testUser));
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        testUser.setCurrUser(true);
        boolean ifAdmin = userService.checkIfAdmin();
        assertFalse(ifAdmin);

    }

    @Test
    void checkIfAdmin2() { //is an admin

//        when(userService.findActiveUser()).thenReturn(1L);
        when(userRepository.findAll()).thenReturn(List.of(adminUser));
        when(userRepository.findById(1L)).thenReturn(Optional.of(adminUser));

        adminUser.setCurrUser(true);
        boolean ifAdmin = userService.checkIfAdmin();
        assertTrue(ifAdmin);

    }

    @Test
    void checkIfAdmin3() { //i

//        when(userService.findActiveUser()).thenReturn(1L);
        when(userRepository.findAll()).thenReturn(List.of(testUser));
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        boolean ifAdmin = userService.checkIfAdmin();
        assertFalse(ifAdmin);

    }

    @Test
    void checkAtSign() {
        boolean ifSign = userService.checkAtSign("Jon@wsu.edu");
        assertTrue(ifSign);
    }

    @Test
    void checkAtSign2() {
        boolean ifSign = userService.checkAtSign("Jonwsu.edu");
        assertFalse(ifSign);
    }

    @Test
    void checkDomains() {
        boolean ifDomain = userService.checkDomains("jon@wsu.edu");
        assertTrue(ifDomain);
    }

    @Test
    void checkDomains2() {
        boolean ifDomain = userService.checkDomains("jon@wrong.wrong");
        assertFalse(ifDomain);
    }

    @Test
    void checkEmail() { //TT
        boolean ifEmail = userService.checkEmail("jon@wsu.edu");
        assertTrue(ifEmail);
    }
    @Test
    void checkEmail2() { //FF
        boolean ifEmail = userService.checkEmail("jonwsufdsedu");
        assertFalse(ifEmail);
    }
    @Test
    void checkEmail3() { //TF
        boolean ifEmail = userService.checkEmail("jon@wrong");
        assertFalse(ifEmail);
    }
    @Test
    void checkEmail4() { //FT
        boolean ifEmail = userService.checkEmail("jonwsu.edu");
        assertFalse(ifEmail);
    }
    @Test
    void checkEmail5() { //FT
        boolean ifEmail = userService.checkEmail("jon@wsuedu");
        assertFalse(ifEmail);
    }

}