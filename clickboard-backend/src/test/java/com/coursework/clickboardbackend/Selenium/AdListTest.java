package com.coursework.clickboardbackend.Selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdListTest {
    private WebDriver driver;


    @BeforeEach
    public void setUp() {
        // Настройка WebDriver для Chrome
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        // Открываем страницу (чтобы localStorage был доступен)
        driver.get("http://localhost:3000/ads?categoryId=1");

        // Устанавливаем значения в localStorage
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("localStorage.setItem('username', 'movavi');");
        js.executeScript("localStorage.setItem('role', 'USER');");

        // Обновляем страницу, чтобы применились значения localStorage
        driver.navigate().refresh();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testAdsDisplayed() {
        // Проверка, что объявления отображаются
        try {
            Thread.sleep(2000); // Рекомендуется заменить на WebDriverWait
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> ads = driver.findElements(By.cssSelector(".card"));
        assertTrue(ads.size() > 0, "Объявления должны отображаться");
    }

    @Test
    public void testNavigateToAdDetails() {
        // Нажатие на первое объявление
        try {
            Thread.sleep(2000); // Рекомендуется заменить на WebDriverWait
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement firstAd = driver.findElement(By.cssSelector(".card"));
        firstAd.click();

        // Ожидание перехода на страницу деталей
        try {
            Thread.sleep(2000); // Рекомендуется заменить на WebDriverWait
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Проверка, что URL изменился на страницу деталей объявления
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/ads/"), "URL должен содержать '/ads/'");
    }

    @Test
    public void testFilterByCategory() {
        // Выбор категории "Автомобили"
        WebElement categorySelect = driver.findElement(By.cssSelector("select"));
        categorySelect.click();
        categorySelect.findElement(By.cssSelector("option[value='3']")).click();

        // Ожидание обновления списка
        try {
            Thread.sleep(2000); // Рекомендуется заменить на WebDriverWait
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Проверка, что объявления соответствуют выбранной категории
        List<WebElement> ads = driver.findElements(By.cssSelector(".card-title"));
        for (WebElement ad : ads) {
            String adTitle = ad.getText().toLowerCase();
            assertTrue(adTitle.contains("Wooden"),
                    "Объявление должно быть связано с категорией 'Автомобили'");
        }
    }

    @Test
    public void testSearchByTitle() {
        // Ввод текста в поиск
        WebElement searchInput = driver.findElement(By.cssSelector("input[type='text']"));
        searchInput.sendKeys("iPhone");

        // Ожидание обновления списка
        try {
            Thread.sleep(2000); // Рекомендуется заменить на WebDriverWait
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Проверка, что все объявления содержат "iPhone" в названии
        List<WebElement> ads = driver.findElements(By.cssSelector(".card-title"));
        for (WebElement ad : ads) {
            String adTitle = ad.getText().toLowerCase();
            assertTrue(adTitle.contains("iphone"), "Название объявления должно содержать 'iPhone'");
        }
    }
}

