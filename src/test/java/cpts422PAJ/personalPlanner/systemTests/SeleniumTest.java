package cpts422PAJ.personalPlanner.systemTests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.webdriver.MockMvcHtmlUnitDriverBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.MockMvcConfigurer;
import org.springframework.web.context.WebApplicationContext;

import java.time.Duration;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class SeleniumTest {


    @Autowired
    WebDriver webDriver;



    @BeforeEach
    void setUp(){
        webDriver.get("http://localhost:8080/login");

    }



    @AfterEach
    void tearDown(){
        webDriver.quit();
    }



    @Test
    void loginPageTest(){


        WebElement userName = webDriver.findElement(By.cssSelector("input[name='userName']"));
        userName.sendKeys("jonb");
        WebElement passWord = webDriver.findElement(By.cssSelector("input[name='passWord']"));
        passWord.sendKeys("pass");

        WebElement login = webDriver.findElement(By.cssSelector("input[value='login']"));
        login.click();

        WebElement success = webDriver.findElement(By.cssSelector("html > body > table > thead > tr > th:nth-of-type(1)"));
        assertEquals("Task Name", success.getText());
    }

    @Test
    void loginWrongPageTest(){


        WebElement userName = webDriver.findElement(By.cssSelector("input[name='userName']"));
        userName.sendKeys("jonb");
        WebElement passWord = webDriver.findElement(By.cssSelector("input[name='passWord']"));
        passWord.sendKeys("passs");

        WebElement login = webDriver.findElement(By.cssSelector("input[value='login']"));
        login.click();

        WebElement notSuccess = webDriver.findElement(By.cssSelector("html > body > form > label:nth-of-type(1)"));
        assertEquals("User name:", notSuccess.getText());
    }

    @Test
    void registerPageTest(){
        WebElement register = webDriver.findElement(By.cssSelector("input[value='register']"));
        register.click();

        WebElement email = webDriver.findElement(By.cssSelector("html > body > form > label:nth-of-type(1)"));
        assertEquals("Email:", email.getText());

    }



    @Test
    void registerWrongUser(){
        WebElement register = webDriver.findElement(By.cssSelector("input[value='register']"));
        register.click();

        WebElement email = webDriver.findElement(By.cssSelector("input[name='email']"));
        email.sendKeys("Joe.Randwsu.edu");

        WebElement fName = webDriver.findElement(By.cssSelector("input[name='first']"));
        fName.sendKeys("Joe");

        WebElement lName = webDriver.findElement(By.cssSelector("input[name='last']"));
        lName.sendKeys("Rand");

        WebElement userName = webDriver.findElement(By.cssSelector("input[name='userName']"));
        userName.sendKeys("Jo");

        WebElement passWord = webDriver.findElement(By.cssSelector("input[name='passWord']"));
        passWord.sendKeys("password");

        WebElement submit =webDriver.findElement(By.cssSelector("input[class='btn']"));

        submit.click();

        //means we didn't go to the next page
        WebElement emailCheck = webDriver.findElement(By.cssSelector("html > body > form > label:nth-of-type(1)"));
        assertEquals("Email:", emailCheck.getText());

    }

    @Test
    void registerAdmin(){
        WebElement register = webDriver.findElement(By.cssSelector("input[value='register']"));
        register.click();



        WebElement email = webDriver.findElement(By.cssSelector("input[name='email']"));
        email.sendKeys("Joey.Randy@gmail.com");

        WebElement fName = webDriver.findElement(By.cssSelector("input[name='first']"));
        fName.sendKeys("Joey");

        WebElement lName = webDriver.findElement(By.cssSelector("input[name='last']"));
        lName.sendKeys("Randy");

        WebElement userName = webDriver.findElement(By.cssSelector("input[name='userName']"));
        userName.sendKeys("JoeyRY");

        WebElement passWord = webDriver.findElement(By.cssSelector("input[name='passWord']"));
        passWord.sendKeys("JoeyRPassword");

        WebElement adminPassword = webDriver.findElement(By.cssSelector("input[name='adminPassword']"));
        adminPassword.sendKeys("GoCougs");

        WebElement submit =webDriver.findElement(By.cssSelector("input[class='btn']"));

        submit.click();

        WebElement checkRegistered = webDriver.findElement(By.cssSelector("html > body > form > label:nth-of-type(1)"));

        assertEquals("User name:", checkRegistered.getText());

    }


    @Test
    void registerUser(){
        WebElement register = webDriver.findElement(By.cssSelector("input[value='register']"));
        register.click();

        WebElement email = webDriver.findElement(By.cssSelector("input[name='email']"));
        email.sendKeys("Joe.Rand@wsu.edu");

        WebElement fName = webDriver.findElement(By.cssSelector("input[name='first']"));
        fName.sendKeys("Joe");

        WebElement lName = webDriver.findElement(By.cssSelector("input[name='last']"));
        lName.sendKeys("Rand");

        WebElement userName = webDriver.findElement(By.cssSelector("input[name='userName']"));
        userName.sendKeys("JoeR");

        WebElement passWord = webDriver.findElement(By.cssSelector("input[name='passWord']"));
        passWord.sendKeys("password");

        WebElement submit =webDriver.findElement(By.cssSelector("input[class='btn']"));

        submit.click();

        WebElement checkRegistered = webDriver.findElement(By.cssSelector("html > body > form > label:nth-of-type(1)"));

        assertEquals("User name:", checkRegistered.getText());

    }



    @Test
    void editTask(){
        WebElement userName = webDriver.findElement(By.cssSelector("input[name='userName']"));
        userName.sendKeys("jonb");
        WebElement passWord = webDriver.findElement(By.cssSelector("input[name='passWord']"));
        passWord.sendKeys("pass");

        WebElement login = webDriver.findElement(By.cssSelector("input[value='login']"));
        login.click();
        //We are in main page rn

        WebElement editTask = webDriver.findElement(By.cssSelector("html > body > table > tbody > tr:nth-of-type(1) > td:nth-of-type(7) > a"));
        editTask.click();
        //Tag for first task is Homework

        WebElement changeTag = webDriver.findElement(By.cssSelector("select[id='tag']"));
        Select selectDropDown = new Select(changeTag);
        selectDropDown.selectByVisibleText("Life");

        WebElement submit = webDriver.findElement(By.cssSelector("input[class='btn']"));
        submit.click();

        WebElement tag = webDriver.findElement(By.cssSelector("html > body > table > tbody > tr:nth-of-type(1) > td:nth-of-type(6)"));
        assertEquals("Life", tag.getText());
        WebElement logout3 = webDriver.findElement(By.cssSelector("input[value='Logout']"));
        logout3.click();

    }

    @Test
    void addTask(){
        WebElement userName = webDriver.findElement(By.cssSelector("input[name='userName']"));
        userName.sendKeys("jonb");
        WebElement passWord = webDriver.findElement(By.cssSelector("input[name='passWord']"));
        passWord.sendKeys("pass");
        WebElement login = webDriver.findElement(By.cssSelector("input[value='login']"));
        login.click();

        WebElement addT = webDriver.findElement(By.cssSelector("input[value$='task']"));
        addT.click();

        WebElement taskName = webDriver.findElement(By.cssSelector("input[id='taskName']"));
        taskName.sendKeys("selenium test");

        WebElement note = webDriver.findElement(By.cssSelector("input[id='note']"));
        note.sendKeys("This is testing selenium scripts");

        WebElement changeTag = webDriver.findElement(By.cssSelector("select[id='tag']"));
        Select selectDropDown = new Select(changeTag);
        selectDropDown.selectByVisibleText("Homework");

        WebElement submit = webDriver.findElement(By.cssSelector("input[class='btn']"));
        submit.click();

        WebElement newTask = webDriver.findElement(By.cssSelector("html > body > table > tbody > tr:nth-of-type(10) > td:nth-of-type(1)"));

        assertEquals("selenium test", newTask.getText());
        WebElement logout3 = webDriver.findElement(By.cssSelector("input[value='Logout']"));
        logout3.click();



    }

    @Test
    void addWrongTask(){
        WebElement userName = webDriver.findElement(By.cssSelector("input[name='userName']"));
        userName.sendKeys("jonb");
        WebElement passWord = webDriver.findElement(By.cssSelector("input[name='passWord']"));
        passWord.sendKeys("pass");
        WebElement login = webDriver.findElement(By.cssSelector("input[value='login']"));
        login.click();

        WebElement addT = webDriver.findElement(By.cssSelector("input[value$='task']"));
        addT.click();

        //wont work because already have max tasks for a non-distinct name

        WebElement newTask = webDriver.findElement(By.cssSelector("html > body > table > tbody > tr:nth-of-type(10) > td:nth-of-type(1)"));

        assertEquals("selenium test", newTask.getText());
        WebElement logout3 = webDriver.findElement(By.cssSelector("input[value='Logout']"));
        logout3.click();

    }
    @Test
    void addTaskAdmin(){
        WebElement userName = webDriver.findElement(By.cssSelector("input[name='userName']"));
        userName.sendKeys("admin");
        WebElement passWord = webDriver.findElement(By.cssSelector("input[name='passWord']"));
        passWord.sendKeys("admin");
//        webDriver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
        WebElement login = webDriver.findElement(By.cssSelector("input[value='login']"));
        login.click();
        WebElement logout4 = webDriver.findElement(By.cssSelector("input[value='Logout']"));
        logout4.click();

        WebElement userName2 = webDriver.findElement(By.cssSelector("input[name='userName']"));
        userName2.sendKeys("admin");
        WebElement passWord2 = webDriver.findElement(By.cssSelector("input[name='passWord']"));
        passWord2.sendKeys("admin");
        WebElement login3 = webDriver.findElement(By.cssSelector("input[value='login']"));
        login3.click();


        //In Admin account
//        webDriver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
        WebElement addT = webDriver.findElement(By.cssSelector("input[value$='task']"));
        addT.click();

        WebElement taskName = webDriver.findElement(By.cssSelector("input[id='taskName']"));
        taskName.sendKeys("selenium test for sergio");

        WebElement note = webDriver.findElement(By.cssSelector("input[id='note']"));
        note.sendKeys("This is testing selenium scripts");

        WebElement changeTag = webDriver.findElement(By.cssSelector("select[id='tag']"));
        Select selectDropDown = new Select(changeTag);
        selectDropDown.selectByVisibleText("Homework");

        WebElement changeTags = webDriver.findElement(By.cssSelector("select[id='users']"));
        Select selectDropDowns = new Select(changeTags);
        selectDropDowns.selectByVisibleText("Sergio");

        WebElement submit = webDriver.findElement(By.cssSelector("input[class='btn']"));
        submit.click();

        WebElement logout = webDriver.findElement(By.cssSelector("input[value='Logout']"));
        logout.click();

        WebElement userName3 = webDriver.findElement(By.cssSelector("input[name='userName']"));
        userName3.sendKeys("ser");
        WebElement passWord3 = webDriver.findElement(By.cssSelector("input[name='passWord']"));
        passWord3.sendKeys("ser");
        WebElement login2 = webDriver.findElement(By.cssSelector("input[value='login']"));
        login2.click();

        WebElement checkValid = webDriver.findElement(By.cssSelector("html > body > table > tbody > tr:nth-of-type(10) > td:nth-of-type(1)"));

        assertEquals("selenium test for sergio", checkValid.getText());

    }

    @Test
    void addTag(){
        WebElement userName = webDriver.findElement(By.cssSelector("input[name='userName']"));
        userName.sendKeys("jonb");
        WebElement passWord = webDriver.findElement(By.cssSelector("input[name='passWord']"));
        passWord.sendKeys("pass");
        WebElement login = webDriver.findElement(By.cssSelector("input[value='login']"));
        login.click();

        WebElement addT = webDriver.findElement(By.cssSelector("input[value$='tag']"));
        addT.click();

        //won't work because already have max tasks for a non-distinct name

        WebElement tagName = webDriver.findElement(By.cssSelector("input[id='name']"));
        tagName.sendKeys("Project");

        WebElement submit = webDriver.findElement(By.cssSelector("input[type='submit']"));
        submit.click();

        WebElement editTask = webDriver.findElement(By.cssSelector("html > body > table > tbody > tr:nth-of-type(1) > td:nth-of-type(7) > a"));
        editTask.click();
        //Tag for first task is Homework

        WebElement changeTag = webDriver.findElement(By.cssSelector("select[id='tag']"));
        Select selectDropDown = new Select(changeTag);
        selectDropDown.selectByVisibleText("Project");

        WebElement submit2 = webDriver.findElement(By.cssSelector("input[class='btn']"));
        submit2.click();


        WebElement tag = webDriver.findElement(By.cssSelector("html > body > table > tbody > tr:nth-of-type(1) > td:nth-of-type(6)"));
        assertEquals("Project", tag.getText());

        WebElement logout = webDriver.findElement(By.cssSelector("input[value='Logout']"));
        logout.click();


    }

    @Test
    void logOut(){
        WebElement userName = webDriver.findElement(By.cssSelector("input[name='userName']"));
        userName.sendKeys("jonb");
        WebElement passWord = webDriver.findElement(By.cssSelector("input[name='passWord']"));
        passWord.sendKeys("pass");
        WebElement login = webDriver.findElement(By.cssSelector("input[value='login']"));
        login.click();

        WebElement logout = webDriver.findElement(By.cssSelector("input[value='Logout']"));
        logout.click();
        WebElement checkRegistered = webDriver.findElement(By.cssSelector("html > body > form > label:nth-of-type(1)"));

        assertEquals("User name:", checkRegistered.getText());



    }


    //regular user editing other task
    @Test
    void editOtherUser(){
        WebElement userName = webDriver.findElement(By.cssSelector("input[name='userName']"));
        userName.sendKeys("jonb");
        WebElement passWord = webDriver.findElement(By.cssSelector("input[name='passWord']"));
        passWord.sendKeys("pass");
        WebElement login = webDriver.findElement(By.cssSelector("input[value='login']"));
        login.click();

        WebElement editTask = webDriver.findElement(By.cssSelector("html > body > table > tbody > tr:nth-of-type(1) > td:nth-of-type(7) > a"));
        editTask.click();
        //Tag for first task is Homework

        WebElement changeTag = webDriver.findElement(By.cssSelector("select[id='users']"));
        Select selectDropDown = new Select(changeTag);
        selectDropDown.selectByVisibleText("Jennifer");

        WebElement submit = webDriver.findElement(By.cssSelector("input[class='btn']"));
        submit.click();

        WebElement logout = webDriver.findElement(By.cssSelector("input[value='Logout']"));
        logout.click();

        //logging into jennifers info
        WebElement userName2 = webDriver.findElement(By.cssSelector("input[name='userName']"));
        userName2.sendKeys("jDoe");
        WebElement passWord2 = webDriver.findElement(By.cssSelector("input[name='passWord']"));
        passWord2.sendKeys("123");

        WebElement login2 = webDriver.findElement(By.cssSelector("input[value='login']"));
        login2.click();

        WebElement unchangedTaskName = webDriver.findElement(By.cssSelector("html > body > table > tbody > tr:nth-of-type(1) > td:nth-of-type(1)"));
        assertEquals("homework2", unchangedTaskName.getText());

        WebElement logout3 = webDriver.findElement(By.cssSelector("input[value='Logout']"));
        logout3.click();

    }



    //admin editing users task
    @Test
    void editTaskAdmin(){
        WebElement userName = webDriver.findElement(By.cssSelector("input[name='userName']"));
        userName.sendKeys("admin");
        WebElement passWord = webDriver.findElement(By.cssSelector("input[name='passWord']"));
        passWord.sendKeys("admin");
        WebElement login = webDriver.findElement(By.cssSelector("input[value='login']"));
        login.click();

        WebElement logout4 = webDriver.findElement(By.cssSelector("input[value='Logout']"));
        logout4.click();

        WebElement userName3 = webDriver.findElement(By.cssSelector("input[name='userName']"));
        userName3.sendKeys("admin");
        WebElement passWord3 = webDriver.findElement(By.cssSelector("input[name='passWord']"));
        passWord3.sendKeys("admin");
        WebElement login3 = webDriver.findElement(By.cssSelector("input[value='login']"));
        login3.click();


        WebElement editTask = webDriver.findElement(By.cssSelector("html > body > table > tbody > tr:nth-of-type(1) > td:nth-of-type(7) > a"));
        editTask.click();
        //Tag for first task is Homework

        WebElement taskName = webDriver.findElement(By.cssSelector("input[id='taskName']"));
        taskName.sendKeys(" Changed by admin");

        WebElement submit = webDriver.findElement(By.cssSelector("input[class='btn']"));
        submit.click();

        WebElement logout = webDriver.findElement(By.cssSelector("input[value='Logout']"));
        logout.click();

        WebElement userName2 = webDriver.findElement(By.cssSelector("input[name='userName']"));
        userName2.sendKeys("jonb");
        WebElement passWord2 = webDriver.findElement(By.cssSelector("input[name='passWord']"));
        passWord2.sendKeys("pass");
        WebElement login2 = webDriver.findElement(By.cssSelector("input[value='login']"));
        login2.click();

        WebElement changedTaskName = webDriver.findElement(By.cssSelector("html > body > table > tbody > tr:nth-of-type(1) > td:nth-of-type(1)"));

        assertEquals("homework1 Changed by admin", changedTaskName.getText());



    }








    //Test admin
        // adding tasks to others and editing tasks for others
    //Done



}
