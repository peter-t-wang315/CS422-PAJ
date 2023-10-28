package cpts422PAJ.personalPlanner.entities;

import org.apache.catalina.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsersTest {
    private Users testUser;

    @BeforeEach
    void setUp() {
        testUser = new Users("test.pore@wsu.edu", "testUserName", "testUserPass","testF", "testL", false);

        Users testUserEmpty = new Users();
    }

    @Test
    public void testUserCreation(){

    }

    @Test
    void getPassword() {
        Users testUserEmpty = new Users();
        assertEquals("testUserPass", testUser.getPassword());
    }

    @Test
    void isCurrentUser() {

        assertFalse(testUser.isCurrentUser());
    }

    @Test
    void isAdmin() {

        assertFalse(testUser.isAdmin());
    }

    @Test
    void setAdmin() {

        testUser.setAdmin(true);//starts with false
        assertTrue(testUser.isAdmin());
    }

    @Test
    void getAdminPassword() {
        assertEquals( "GoCougs",testUser.getAdminPassword());
    }

    @Test
    void getAdminPasswords() {

    }

    @Test
    void getEmail() {
        assertEquals("test.pore@wsu.edu", testUser.getEmail());
    }

    @Test
    void getUserName() {
        assertEquals("testUserName", testUser.getUserName());
    }

    @Test
    void getFirstName() {
        assertEquals("testF", testUser.getFirstName());
    }

    @Test
    void getLastName() {
        assertEquals("testL", testUser.getLastName());
    }

    @Test
    void setEmail() {
        testUser.setEmail("test.user@wsu.edu");
        assertEquals("test.user@wsu.edu", testUser.getEmail());
    }

    @Test
    void setUserName() {
        testUser.setUserName("newTestUserName");
        assertEquals("newTestUserName", testUser.getUserName());
    }

    @Test
    void setFirstName() {
        testUser.setFirstName("newFirst");
        assertEquals("newFirst", testUser.getFirstName());
    }

    @Test
    void setLastName() {
        testUser.setLastName("newLast");
        assertEquals("newLast", testUser.getLastName());
    }



    @Test
    void getId() {
        //should not be created yet
        assertEquals(null, testUser.getId());
    }

    @Test
    void toStringMethod() {
        assertEquals("Users{email='test.pore@wsu.edu', userName='testUserName', firstName='testF', lastName='testL', currentUser=false, id=null}", testUser.toString());
    }

    @Test
    void setCurrUser(){
        testUser.setCurrUser(true);//This starts with false
        assertTrue(testUser.isCurrentUser());
        testUser.setCurrUser(false);
        assertFalse(testUser.isCurrentUser());
    }

}