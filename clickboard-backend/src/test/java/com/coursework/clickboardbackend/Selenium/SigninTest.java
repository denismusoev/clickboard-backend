package com.coursework.clickboardbackend.Selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SigninTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("http://localhost:3000/signin");

        // Обновляем страницу, чтобы localStorage применился
        driver.navigate().refresh();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testSuccessfulSignin() {
        try {
            Thread.sleep(1000); // Подождите несколько секунд (оптимально - использовать WebDriverWait)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Ввод имени пользователя
        WebElement usernameInput = driver.findElement(By.id("username"));
        usernameInput.sendKeys("movavi");

        // Ввод пароля
        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys("1234");

        // Нажатие на кнопку "Войти"
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        // Ожидание перехода на другую страницу
        try {
            Thread.sleep(2000); // Подождите несколько секунд (оптимально - использовать WebDriverWait)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Проверка URL после успешной авторизации
        String currentUrl = driver.getCurrentUrl();
        assertEquals("http://localhost:3000/home", currentUrl);
    }

    @Test
    public void testInvalidCredentials() {
        try {
            Thread.sleep(1000); // Подождите несколько секунд (оптимально - использовать WebDriverWait)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Ввод некорректного имени пользователя
        WebElement usernameInput = driver.findElement(By.id("username"));
        usernameInput.sendKeys("invaliduser");

        // Ввод некорректного пароля
        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys("wrongpassword");

        // Нажатие на кнопку "Войти"
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        // Ожидание появления сообщения об ошибке
        try {
            Thread.sleep(2000); // Подождите несколько секунд (оптимально - использовать WebDriverWait)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Проверка сообщения об ошибке
        WebElement alertMessage = driver.findElement(By.className("alert-danger"));
        assertEquals("Неверный логин или пароль", alertMessage.getText());
    }

    @Test
    public void testEmptyFields() {
        // Оставляем поля пустыми и нажимаем кнопку
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        // Проверяем, что страница не изменилась
        String currentUrl = driver.getCurrentUrl();
        assertEquals("http://localhost:3000/signin", currentUrl);
    }
}

