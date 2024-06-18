package demo;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import io.github.bonigarcia.wdm.WebDriverManager;

public class TestCases {
    static ChromeDriver driver;

    public TestCases() {
        System.out.println("Constructor: TestCases");
        WebDriverManager.chromedriver().timeout(30).setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

    }

    public void endTest() {
        System.out.println("End Test: TestCases");
        driver.close();
        driver.quit();

    }

    public static boolean enterName(WebElement elem, String name) {
        boolean status = false;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.elementToBeClickable(elem));
        elem.click();
        elem.clear();
        elem.sendKeys(name);
        String check = elem.getAttribute("data-initial-value");
        if (check.contains(name)) {
            status = true;
            return status;
        } else {
            return status;
        }
    }

    public static boolean enterDescription(WebElement elem, String desc) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        boolean status = false;
        wait.until(ExpectedConditions.elementToBeClickable(elem));
        elem.click();
        elem.clear();
        elem.sendKeys(desc);
        if (elem.getAttribute("data-initial-value").contains(desc)) {
            status = true;
            return status;
        } else {
            return status;
        }

    }

    public static boolean selectRadioButton(List<WebElement> options, String years) {
        for (WebElement elem : options) {
            if (elem.getText().contains(years)) {
                elem.click();
                List<WebElement> selectedRadio = elem
                        .findElements(By.xpath("//div[@class='d7L4fc bJNwt  FXLARc aomaEc ECvBRb']/div"));
                for (WebElement inner : selectedRadio) {
                    String check = inner.getAttribute("aria-checked").toString();
                    if (check.equals("true")) {
                        return true;
                    } else
                        continue;
                }

            }
        }
        return false;
    }

    public static boolean selectTech(List<WebElement> options, String text1, String text2, String text3) {
        for (WebElement elem : options) {
            if (elem.getText().contains(text1)) {
                elem.click();
            } else if (elem.getText().contains(text2)) {
                elem.click();
            } else if (elem.getText().contains(text3)) {
                elem.click();
            } else
                continue;
        }
        List<WebElement> selectedCheckBoxes = driver
                .findElements(By.xpath("//div[@class='eBFwI']//div/div[@aria-checked='true']"));
        if (selectedCheckBoxes.size() == 3) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean selectGenderTitle(WebElement option, String text) {
       try{
        option.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    
       String xpathExpression = String.format("//div[@role='option']//span[text()='%s']", text);
      
       WebElement select = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathExpression)));

                Actions action = new Actions(driver);
                action.moveToElement(select).click().perform();
                Thread.sleep(1000);
        String check = option.getAttribute("data-value").toString();
        if (check.contains(text)) {
            return true;
        } else {
            return false;
        }
       }
       catch(Exception e){
        e.printStackTrace();
        return false;
       }
    }

    public static boolean selectDate(WebElement date, String text) {
        date.click();
        date.clear();
        date.sendKeys(text);
        
        return true;

    }

    public void testCase01() throws InterruptedException {
        System.out.println("Start Test case: testCase01");
        boolean status = false;
        driver.get(
                "https://docs.google.com/forms/d/e/1FAIpQLSep9LTMntH5YqIXa5nkiPKSs283kdwitBBhXWyZdAS-e4CxBQ/viewform");
        WebElement nameField = driver.findElement(By.xpath("//input[@aria-describedby='i2 i3']"));
        status = enterName(nameField, "Shubham Thapa");
        if (status) {
            System.out.println("Name entered successfully");
        } else {
            System.out.println("Failed to enter name");
        }
        System.out.println("end Test case: testCase01");

    }

    public void testCase02() {
        System.out.println("start Test case: testCase02");
        boolean status;
        WebElement descField = driver.findElement(By.xpath("//textarea[@aria-label='Your answer']"));
        long epocTime = System.currentTimeMillis() / 1000;
        StringBuilder sb = new StringBuilder();
        sb.append("I want to be the best QA Engineer!");
        sb.append(" " + epocTime);
        String des = sb.toString();
        status = enterDescription(descField, des);
        if (status) {
            System.out.println("description entered with current epoch time");
        } else {
            System.out.println("failed to add description");
        }

        System.out.println("end Test case: testCase02");
    }

    public void testCase03() {
        System.out.println("start Test case: testCase03");
        List<WebElement> radioOptions = driver.findElements(By.xpath("//div[@class='bzfPab wFGF8']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement toScroll = driver
                .findElement(By.xpath("//span[text()='How much experience do you have in Automation Testing?']"));
        js.executeScript("arguments[0].scrollIntoView(true);", toScroll);
        Boolean status = selectRadioButton(radioOptions, "0 - 2");
        if (status) {
            System.out.println("radio buttton selected");
        } else {
            System.out.println("failed to select radio button");
        }
        System.out.println("end Test case: testCase03");

    }

    public void testCase04() {
        System.out.println("start Test case: testCase04");
        WebElement title4 = driver.findElement(By
                .xpath("//span[text()='Which of the following have you learned in Crio.Do for Automation Testing?']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", title4);

        List<WebElement> options = driver.findElements(By.xpath("//span[@class='aDTYNe snByac n5vBHf OIC90c']"));
        Boolean status = selectTech(options, "Java", "Selenium", "TestNG");
        if (status) {
            System.out.println("Check boxes selected");
        } else {
            System.out.println("failed to select check boxes");
        }

        System.out.println("end Test case: testCase04");
    }

    public void testCase05() {
        System.out.println("start Test case: testCase05");
        WebElement title = driver.findElement(By.xpath("//span[text()='How should you be addressed?']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", title);
        WebElement dropd = driver.findElement(By.xpath("//div[@role='option']"));
        Boolean status = selectGenderTitle(dropd, "Mr");
        if (status) {
            System.out.println("salutation selected");
        } else {
            System.out.println("salutation not selected");
        }
        System.out.println("end Test case: testCase05");
    }

    public void testCase06() throws InterruptedException {
        System.out.println("start Test case: testCase06");
        WebElement title = driver.findElement(By.xpath("//span[text()='What was the date 7 days ago?']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", title);
        WebElement date = driver.findElement(By.xpath("//input[@type='date']"));
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        Date currentDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String previousDate = dateFormat.format(currentDate);
        boolean status = selectDate(date, previousDate);

        Thread.sleep(3000);
        if(status){
            System.out.println("entered the date which was 7 days ago");
        }
        else{
            System.out.println("date not entered correctly");
        }
        System.out.println("end Test case: testCase06");
    }

    public void testCase07() throws InterruptedException{
        System.out.println("start Test case: testCase07");
        WebElement timeNowLeft = driver.findElement(By.xpath("(//div[@class='PfQ8Lb']//input[@class='whsOnd zHQkBf'])[1]"));
        WebElement timeNowRight = driver.findElement(By.xpath("(//div[@class='PfQ8Lb']//input[@class='whsOnd zHQkBf'])[2]"));
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:ss");
        String formattedDate = now.format(formatter);
        System.out.println(formattedDate);
        String[] time = new String[2];
        time = formattedDate.split(":");
        boolean status = inputTime(timeNowLeft,timeNowRight,time);
        Thread.sleep(4000);
        if(status){
            System.out.println("Time now entered");
        }
        else{
            System.out.println("time not entered");
        }

        
        System.out.println("end Test case: testCase07");
    }
    public void testCase08(){
        System.out.println("start Test case: testCase08");
        driver.navigate().to("www.amazon.in");
        Alert alert = driver.switchTo().alert();
        alert.dismiss();

        WebElement submit = driver.findElement(By.xpath("//span[text()='Submit']"));
        submit.click();
        
        System.out.println("end Test case: testCase08");
    }
    public static boolean inputTime(WebElement timeLeft, WebElement timeRight, String[] times){
        timeLeft.click();
        timeLeft.sendKeys(times[0]);
        timeRight.click();
        timeRight.sendKeys(times[1]);
        return true;
    }

}
