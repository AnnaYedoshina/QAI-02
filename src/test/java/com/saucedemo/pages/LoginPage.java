package com.saucedemo.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * PageObject для страницы входа (Login).
 * Содержит элементы и методы для взаимодействия со страницей входа.
 */
public class LoginPage {

    private final WebDriver driver;

    // --- Элементы страницы ---

    @FindBy(id = "user-name")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "h3[data-test='error']")
    private WebElement errorMessage;


    /**
     * Конструктор класса.
     * @param driver Экземпляр WebDriver для взаимодействия с браузером.
     */
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        // Инициализация элементов страницы
        PageFactory.initElements(driver, this);
        System.out.println("LoginPage инициализирован.");
    }

    // --- Методы для взаимодействия с элементами ---

    /**
     * Вводит имя пользователя в соответствующее поле.
     * @param username Имя пользователя.
     */
    @Step("Ввод имени пользователя: {username}")
    public void enterUsername(String username) {
        System.out.printf("Ввод имени пользователя: '%s'%n", username);
        usernameInput.sendKeys(username);
    }

    /**
     * Вводит пароль в соответствующее поле.
     * @param password Пароль.
     */
    @Step("Ввод пароля")
    public void enterPassword(String password) {
        System.out.println("Ввод пароля.");
        passwordInput.sendKeys(password);
    }

    /**
     * Нажимает на кнопку входа.
     * @return Объект страницы продуктов (ProductsPage), которая открывается после успешного входа.
     */
    @Step("Нажатие на кнопку 'Login'")
    public ProductsPage clickLoginButton() {
        System.out.println("Нажатие на кнопку 'Login'.");
        loginButton.click();
        // После успешного логина мы ожидаем оказаться на странице продуктов
        return new ProductsPage(driver);
    }

    /**
     * Комплексный метод для выполнения входа в систему.
     * @param username Имя пользователя.
     * @param password Пароль.
     * @return Объект страницы продуктов (ProductsPage).
     */
    @Step("Выполнение входа с именем пользователя '{username}'")
    public ProductsPage login(String username, String password) {
        System.out.println("Начало процесса входа.");
        enterUsername(username);
        enterPassword(password);
        System.out.println("Процесс входа завершен.");
        return clickLoginButton();
    }

    /**
     * Получает текст сообщения об ошибке.
     * @return Текст сообщения об ошибке.
     */
    @Step("Получение текста сообщения об ошибке")
    public String getErrorMessage() {
        String errorText = errorMessage.getText();
        System.out.printf("Получен текст ошибки: '%s'%n", errorText);
        return errorText;
    }
}
