package com.saucedemo.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

/**
 * Базовый класс для тестов, отвечающий за настройку и завершение работы WebDriver.
 */
public abstract class BaseTest {

    protected WebDriver driver;

    /**
     * Метод, выполняемый перед каждым тестовым методом.
     * Инициализирует WebDriver, настраивает его и открывает базовый URL.
     */
    @BeforeMethod
    public void setUp() {
        System.out.println("Начало выполнения @BeforeMethod: Настройка WebDriver.");

        // Управление версиями драйвера в автоматическом режиме
        WebDriverManager.chromedriver().clearDriverCache();
        WebDriverManager.chromedriver().setup();

        // Опции для запуска Chrome в headless-режиме (без UI)
        ChromeOptions options = new ChromeOptions();
        // Раскомментируйте следующую строку для запуска в headless-режиме
        // options.addArguments("--headless");
        options.addArguments("--window-size=1920,1080");

        // Инициализация драйвера с опциями
        driver = new ChromeDriver(options);

        // Неявное ожидание для всех операций поиска элементов
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Разворачиваем окно браузера на весь экран
        driver.manage().window().maximize();

        // Открываем базовый URL
        driver.get("https://www.saucedemo.com/");
        System.out.println("WebDriver настроен и базовый URL открыт.");
    }

    /**
     * Метод, выполняемый после каждого тестового метода.
     * Завершает сессию WebDriver.
     */
    @AfterMethod
    public void tearDown() {
        System.out.println("Начало выполнения @AfterMethod: Завершение работы WebDriver.");
        if (driver != null) {
            // Закрываем браузер и завершаем сессию
            driver.quit();
            System.out.println("WebDriver успешно завершил работу.");
        }
    }
}