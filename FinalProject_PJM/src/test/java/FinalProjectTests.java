import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

public class FinalProjectTests {
    static WebDriver driver;

    @BeforeAll
    static void WarmUp(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }


    @AfterAll
    static void closeBrowser(){
        driver.quit();
    }

// LogInTests
    @Test
    void shouldLogInProperly(){
        driver.navigate().to("http://automationpractice.com");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@id=\"header\"]/div[2]/div/div/nav/div[1]/a")).click();
        driver.findElement(By.id("email")).sendKeys("test@softie.pl");
        driver.findElement(By.id("passwd")).sendKeys("1qaz!QAZ");
        driver.findElement(By.id("SubmitLogin")).click();
        Assertions.assertTrue(driver.getCurrentUrl().contains("controller=my-account"));
    }
    @Test
    void shouldLogOutAfterLogIn(){
        driver.navigate().to("http://automationpractice.com");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@id=\"header\"]/div[2]/div/div/nav/div[1]/a")).click();
        driver.findElement(By.id("email")).sendKeys("test@softie.pl");
        driver.findElement(By.id("passwd")).sendKeys("1qaz!QAZ");
        driver.findElement(By.id("SubmitLogin")).click();
        driver.findElement(By.className("logout")).click();
        Assertions.assertTrue(driver.getCurrentUrl().contains("back=my-account"));
    }


    @Test
    void shouldNotLogInProperly(){
        driver.navigate().to("http://automationpractice.com");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@id=\"header\"]/div[2]/div/div/nav/div[1]/a")).click();
        driver.findElement(By.id("email")).sendKeys("test@softie.pl");
        driver.findElement(By.id("passwd")).sendKeys("1234545756");
        driver.findElement(By.id("SubmitLogin")).click();
        Assertions.assertFalse(driver.getCurrentUrl().contains("controller=my-account"));
    }


    @Test
    void shouldNotLogInWithoutLogin(){
        driver.navigate().to("http://automationpractice.com");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@id=\"header\"]/div[2]/div/div/nav/div[1]/a")).click();
        driver.findElement(By.id("passwd")).sendKeys("1234567890");
        driver.findElement(By.id("SubmitLogin")).click();
        Assertions.assertFalse(driver.getCurrentUrl().contains("controller=my-account"));
    }


    @Test
    void shouldNotLoginWithoutPassword(){
        Faker faker = new Faker();
        String uniqueEmail = faker.name().firstName() + faker.name().lastName() + "@gmail.com";
        driver.navigate().to("http://automationpractice.com");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@id=\"header\"]/div[2]/div/div/nav/div[1]/a")).click();
        driver.findElement(By.id("email")).sendKeys(uniqueEmail);
        driver.findElement(By.id("SubmitLogin")).click();
        Assertions.assertFalse(driver.getCurrentUrl().contains("controller=my-account"));
    }


    @Test
    void singInTheFormOnPage() throws InterruptedException {
        Faker faker = new Faker();
        String uniqueEmail = faker.name().firstName() + faker.name().lastName() + "@gmail.com";
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.navigate().to("http://automationpractice.com/");
        driver.findElement(By.xpath("//*[@id=\"header\"]/div[2]/div/div/nav/div[1]/a")).click();
        driver.findElement(By.id("email_create")).sendKeys(uniqueEmail);
        driver.findElement(By.id("SubmitCreate")).click();
        Thread.sleep(5000);
        driver.findElement(By.id("customer_firstname")).sendKeys(faker.name().firstName());
        driver.findElement(By.id("customer_lastname")).sendKeys(faker.name().lastName());
        driver.findElement(By.id("passwd")).sendKeys("123456");
        driver.findElement(By.id("firstname")).sendKeys(faker.name().firstName());
        driver.findElement(By.id("lastname")).sendKeys(faker.name().lastName());
        driver.findElement(By.id("address1")).sendKeys(faker.address().streetAddress());
        driver.findElement(By.id("city")).sendKeys(faker.address().city());
        driver.findElement(By.id("postcode")).sendKeys("88123");
        driver.findElement(By.id("phone_mobile")).sendKeys(String.valueOf("00" + faker.number().randomNumber()) + "54327");
        driver.findElement(By.id("alias")).sendKeys(" 1231");
        Select state = new Select(driver.findElement(By.id("id_state")));
        state.selectByValue("1");
        driver.findElement(By.id("submitAccount")).click();
        Assertions.assertTrue(driver.getCurrentUrl().contains("controller=my-account"));
    }


// PagesTests
    @Test
    void categoriesShouldOpenProperly() {
        driver.navigate().to("http://automationpractice.com");
        driver.findElement(By.xpath("//*[@id=\"block_top_menu\"]/ul/li[1]/a"));
        driver.findElement(By.xpath("//*[@id=\"block_top_menu\"]/ul/li[2]/a"));
        driver.findElement(By.xpath("//*[@id=\"block_top_menu\"]/ul/li[3]/a")).click();
    }


    @Test
    void mainPageShouldHaveLogoAndSearchingWindow(){
        driver.navigate().to("http://automationpractice.com");
        driver.findElement(By.id("header_logo"));
        driver.findElement(By.id("searchbox"));
        System.out.println("Strona główna posiada logo i okno wyszukiwania");
    }


    @Test
    void checkPageTitle() {
        driver.navigate().to("http://automationpractice.com");
        Assertions.assertNotEquals("AutomationPractice", driver.getTitle());
        System.out.println(driver.getTitle());
    }


    @Test
    void loggedInPageHaveLogoAndSearchingWindow(){
        driver.navigate().to("http://automationpractice.com");
        driver.findElement(By.xpath("//*[@id=\"header\"]/div[2]/div/div/nav/div[1]/a")).click();
        driver.findElement(By.id("header_logo"));
        driver.findElement(By.id("searchbox"));
        System.out.println("Strona logowania posiada logo i okno wyszukiwania");
    }


    @Test
    void checkingWayFromMainPageToContact(){
        driver.navigate().to("http://automationpractice.com");
        driver.findElement(By.id("contact-link")).click();
        Assertions.assertTrue(driver.getCurrentUrl().contains("controller=contact"));
    }


    @Test
    void checkingWayFromLogInPageToMainPage(){
        driver.navigate().to("http://automationpractice.com");
        driver.findElement(By.xpath("//*[@id=\"header\"]/div[2]/div/div/nav/div[1]/a")).click();
        driver.findElement(By.id("header_logo")).click();
        Assertions.assertTrue(driver.getCurrentUrl().contains("index.php"));
    }


//ProductsTests
    @Test
    void SearchingBoxWorkingProperly(){
        driver.navigate().to("http://automationpractice.com");
        driver.findElement(By.id("search_query_top")).sendKeys("Blouse");
        driver.findElement(By.name("submit_search")).click();
        Assertions.assertTrue(driver.getCurrentUrl().contains("submit_search="));
    }


    @Test
    void addProductsToShoppingBasket(){
        driver.navigate().to("http://automationpractice.com");
        driver.findElement(By.xpath("//*[@id=\"homefeatured\"]/li[2]/div/div[2]/h5/a")).click();
        driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
        driver.findElement(By.cssSelector("#add_to_cart > button")).click();
        driver.findElement(By.xpath("//*[@id=\"layer_cart\"]/div[1]/div[2]/div[4]/a/span")).click();
        Assertions.assertTrue(driver.getCurrentUrl().contains("controller=order"));
    }


    @Test
    void addAndDeleteProductFromShoppingBasket(){
        driver.navigate().to("http://automationpractice.com");
        driver.findElement(By.xpath("//*[@id=\"homefeatured\"]/li[2]/div/div[2]/h5/a")).click();
        driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
        driver.findElement(By.cssSelector("#add_to_cart > button")).click();
        driver.findElement(By.xpath("//*[@id=\"layer_cart\"]/div[1]/div[2]/div[4]/a/span")).click();
        driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
        driver.findElement(By.id("2_7_0_0")).click();
        System.out.println("Koszyk jest pusty" + driver.findElements(By.linkText("Your shopping cart is empty.")));
    }


    @Test
    void proceedCheckoutToBuyProduct() throws InterruptedException{
        Faker faker = new Faker();
        String uniqueEmail = faker.name().firstName() + faker.name().lastName() + "@gmail.com";
        driver.navigate().to("http://automationpractice.com");
        driver.findElement(By.xpath("//*[@id=\"homefeatured\"]/li[2]/div/div[2]/h5/a")).click();
        driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
        driver.findElement(By.cssSelector("#add_to_cart > button")).click();
        driver.findElement(By.xpath("//*[@id=\"layer_cart\"]/div[1]/div[2]/div[4]/a/span")).click();
        driver.findElement(By.xpath("//*[@id=\"center_column\"]/p[2]/a[1]/span")).click();
        driver.findElement(By.id("email_create")).sendKeys(uniqueEmail);
        driver.findElement(By.id("SubmitCreate")).click();
        Thread.sleep(5000);
        driver.findElement(By.id("customer_firstname")).sendKeys(faker.name().firstName());
        driver.findElement(By.id("customer_lastname")).sendKeys(faker.name().lastName());
        driver.findElement(By.id("passwd")).sendKeys("123456");
        driver.findElement(By.id("firstname")).sendKeys(faker.name().firstName());
        driver.findElement(By.id("lastname")).sendKeys(faker.name().lastName());
        driver.findElement(By.id("address1")).sendKeys(faker.address().streetAddress());
        driver.findElement(By.id("city")).sendKeys(faker.address().city());
        driver.findElement(By.id("postcode")).sendKeys("88123");
        driver.findElement(By.id("phone_mobile")).sendKeys(String.valueOf("00" + faker.number().randomNumber()) + "54327");
        driver.findElement(By.id("alias")).sendKeys(" 1231");
        Select state = new Select(driver.findElement(By.id("id_state")));
        state.selectByValue("1");
        driver.findElement(By.id("submitAccount")).click();
        driver.findElement(By.xpath("//*[@id=\"center_column\"]/form/p/button/span")).click();
        driver.findElement(By.xpath("//*[@id=\"cgv\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"form\"]/p/button/span")).click();
        driver.findElement(By.xpath("//*[@id=\"HOOK_PAYMENT\"]/div[1]/div/p/a")).click();
        driver.findElement(By.xpath("//*[@id=\"cart_navigation\"]/button/span")).click();
        Assertions.assertTrue(driver.getCurrentUrl().contains("order"));
    }

}
