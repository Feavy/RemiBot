package reminator.RemiBot.Services;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import reminator.RemiBot.bot.RemiBot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ChangePseudoService {

    private final ChromeDriver driver;

    public ChangePseudoService() throws InterruptedException, FileNotFoundException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("--headless");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);

        driver.get("https://www.messenger.com/t/100013823291154");
        System.out.println(driver.getCurrentUrl());

        WebElement buttonLogin = driver.findElement(By.id("loginbutton"));


        try {
            WebElement cookie = driver.findElement(By.xpath("//button[@title='Only allow essential cookies']"));
            new Actions(driver)
                    .pause(Duration.ofMillis(500))
                    .click(cookie)
                    .perform();
        } catch (NotFoundException ignored){
            System.out.println("not find 1");
        }

        try {
            WebElement cookie = driver.findElement(By.xpath("//button[@title='Only allow essential cookies']"));
            new Actions(driver)
                    .pause(Duration.ofMillis(500))
                    .click(cookie)
                    .perform();
        } catch (NotFoundException ignored){
            System.out.println("not find 2");
        }


        WebElement emailElem = driver.findElement(By.id("email"));
        WebElement passElem = driver.findElement(By.id("pass"));

        new Actions(driver)
                .pause(Duration.ofMillis(500))
                .sendKeys(emailElem, "rlaborie2000@gmail.com")
                .perform();

        new Actions(driver)
                .pause(Duration.ofMillis(500))
                .sendKeys(passElem, RemiBot.mdpFB)
                .sendKeys(Keys.ENTER)
                .perform();

/*
        new Actions(driver)
                .pause(Duration.ofMillis(500))
                .click(buttonLogin)
                .perform();
*/



        Thread.sleep(10000);
        System.out.println(driver.getCurrentUrl());
        new File("page.html");
        PrintWriter printWriter = new PrintWriter("page.html");
        printWriter.write(driver.getPageSource());
        printWriter.close();



        new Actions(driver)
                .click(driver.findElement(By.xpath("//div[contains(@class, 'j9ispegn pmk7jnqg k4urcfbm datstx6m b5wmifdl kr520xx4 mdpwds66 b2cqd1jy n13yt9zj eh67sqbx')]")))
                .perform();


        WebElement elements = driver.findElement(By.xpath("//div[contains(@class, 'b20td4e0 muag1w35')]"));
        WebElement personaliserElemContainer = elements.findElements(By.className("k4urcfbm")).get(0);
        List<WebElement> personaliserElems = personaliserElemContainer.findElements(By.xpath("//div[contains(@class, 'scb9dxdr dflh9lhu')]"));

        new Actions(driver)
                .pause(Duration.ofMillis(500))
                .click(personaliserElems.get(0))
                .perform();

        WebElement modifierElem = personaliserElems
                .get(1)
                .findElements(By.xpath("//div[contains(@class, 'oajrlxb2 gs1a9yip g5ia77u1 mtkw9kbi tlpljxtp qensuy8j ppp5ayq2 goun2846 ccm00jje s44p3ltw mk2mc5f4 rt8b4zig n8ej3o3l agehan2d sk4xxmp2 rq0escxv nhd2j8a9 mg4g778l pfnyh3mw p7hjln8o tgvbjcpo hpfvmrgz jb3vyjys qt6c0cv9 l9j0dhe7 i1ao9s8h esuyzwwr f1sip0of du4w35lb btwxx1t3 abiwlrkh p8dawk7l lzcic4wl a8c37x1j kvgmc6g5 cxmmr5t8 oygrvhab hcukyx3x beltcj47 p86d2i9g aot14ch1 kzx2olss dflh9lhu scb9dxdr')]"))
                .get(2);

        new Actions(driver)
                .pause(Duration.ofMillis(500))
                .click(modifierElem)
                .perform();
    }


    public void start() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Quentin Gendarme']")));

                WebElement quentinButton = driver.findElement(By.xpath("//span[text()='Quentin Gendarme']"));
                new Actions(driver)
                        .pause(Duration.ofMillis(500))
                        .click(quentinButton)
                        .pause(Duration.ofMillis(500))
                        .keyDown(Keys.CONTROL)
                        .sendKeys("A")
                        .keyUp(Keys.CONTROL)
                        .pause(Duration.ofMillis(500))
                        .sendKeys("Miam")
                        .pause(Duration.ofMillis(500))
                        .sendKeys(Keys.ENTER)
                        .perform();
            }
        }, 0, 1000 * 5);
    }
}
