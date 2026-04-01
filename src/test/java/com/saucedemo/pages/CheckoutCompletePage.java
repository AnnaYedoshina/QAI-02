package com.saucedemo.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

/**
 * PageObject для страницы подтверждения успешного заказа.
 */
public class CheckoutCompletePage {

    private final WebDriver driver;

    // --- Элементы страницы ---

    @FindBy(className = "complete-header")
    private WebElement completeHeader;

    @FindBy(className = "title")
    private WebElement pageTitle;


    /**
     * Конструктор класса.
     * @param driver Экземпляр WebDriver.
     */
    public CheckoutCompletePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        System.out.println("CheckoutCompletePage инициализирован.");
    }

    // --- Методы для взаимодействия с элементами ---

    /**
     * Получает текст заголовка об успешном завершении.
     * @return Текст заголовка.
     */
    @Step("Получение текста подтверждения заказа")
    public String getCompleteHeaderText() {
        String headerText = completeHeader.getText();
        System.out.printf("Получен текст подтверждения: '%s'%n", headerText);
        return headerText;
    }

    /**
     * Проверяет, что страница 'Checkout: Complete!' успешно загружена.
     */
    @Step("Проверка успешной загрузки страницы 'Checkout: Complete!'")
    public void verifyPageLoaded() {
        System.out.println("Проверка, что страница 'Checkout: Complete!' загружена.");
        Assert.assertTrue(pageTitle.isDisplayed() && "Checkout: Complete!".equals(pageTitle.getText()),
                "Страница 'Checkout: Complete!' не загрузилась или заголовок неверный.");
        System.out.println("Страница 'Checkout: Complete!' успешно загружена.");
    }
}
