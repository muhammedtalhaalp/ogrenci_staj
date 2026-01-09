package org.example.selenium;

import org.example.Main;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SeleniumUITest {

    @LocalServerPort
    private int port;

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless"); // Headless modu kapalı (Chrome'u görmek için)
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");

        try {
            driver = new ChromeDriver(options);
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        } catch (Exception e) {
            System.err.println("ChromeDriver başlatılamadı! " + e.getMessage());
            fail("Selenium WebDriver başlatılamadı: " + e.getMessage());
        }
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void loginAsAdmin() {
        driver.get("http://localhost:" + port + "/index.html");
        
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
        emailInput.sendKeys("admin@test.com");
        
        driver.findElement(By.id("loginButton")).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dashboard")));
    }

    @Test
    public void testPageTitle() {
        driver.get("http://localhost:" + port + "/index.html");
        assertEquals("Staj Portalı Giriş", driver.getTitle());
    }

    @Test
    public void testSuccessfulLogin() {


        driver.get("http://localhost:" + port + "/index.html");

        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
        emailInput.sendKeys("admin@test.com");

        driver.findElement(By.id("loginButton")).click();

        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
        wait.until(ExpectedConditions.textToBePresentInElement(message, "Giriş Başarılı!"));
        
        assertEquals("Giriş Başarılı!", message.getText());
    }

    @Test
    public void testFailedLogin() {
        driver.get("http://localhost:" + port + "/index.html");

        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
        emailInput.sendKeys("wrong@test.com");

        driver.findElement(By.id("loginButton")).click();

        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
        wait.until(ExpectedConditions.textToBePresentInElement(message, "Hatalı Giriş!"));
        
        assertEquals("Hatalı Giriş!", message.getText());
    }

    @Test
    public void testDashboardVisibility() {
        loginAsAdmin();
        WebElement dashboard = driver.findElement(By.id("dashboard"));
        assertTrue(dashboard.isDisplayed(), "Giriş sonrası dashboard görünmeli");
    }

    @Test
    public void testLogout() {
        loginAsAdmin();
        
        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("logoutButton")));
        logoutButton.click();
        
        WebElement loginSection = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-section")));
        assertTrue(loginSection.isDisplayed(), "Çıkış sonrası giriş ekranı görünmeli");
    }

    @Test
    public void testFooterText() {
        driver.get("http://localhost:" + port + "/index.html");
        WebElement footer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("site-footer")));
        assertTrue(footer.getText().contains("2024 Öğrenci Staj Portalı"));
    }

    @Test
    public void testMenuNavigation() {
        loginAsAdmin();
        WebElement menuIlanlar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("menu-ilanlar")));
        WebElement menuBasvurular = driver.findElement(By.id("menu-basvurular"));
        assertTrue(menuIlanlar.isDisplayed());
        assertTrue(menuBasvurular.isDisplayed());
    }

    @Test
    public void testIlanListesi() {
        loginAsAdmin();
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("#ilan-listesi li"), 0));
        
        List<WebElement> ilanlar = driver.findElements(By.cssSelector("#ilan-listesi li"));
        assertTrue(ilanlar.size() >= 2, "En az 2 ilan listelenmeli");
    }

    @Test
    public void testIlanEkleButonu() {
        loginAsAdmin();
        
        WebElement ekleButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("ilanEkleButton")));
        ekleButton.click();
        
        WebElement form = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ilan-ekle-form")));
        assertTrue(form.isDisplayed(), "Butona basınca form açılmalı");
    }

    @Test
    public void testEmptyLogin() {
        driver.get("http://localhost:" + port + "/index.html");
        
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("loginButton")));
        loginButton.click();
        
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
        wait.until(ExpectedConditions.textToBePresentInElement(message, "Hatalı Giriş!"));

        assertEquals("Hatalı Giriş!", message.getText());
    }
}
