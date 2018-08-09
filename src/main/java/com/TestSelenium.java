package com;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.Optional;

public class TestSelenium {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.gecko.driver", "/users/replaceme/Downloads/geckodriver/geckodriver");
        System.setProperty("webdriver.chrome.driver", "/users/replaceme/Downloads/chromedriver");
        ChromeDriver driver = new ChromeDriver();

        // Navigate to a web page
        driver.get("http://gaptech.service-now.com");
        Thread.sleep(5000);
        //Perform actions on HTML elements, entering text and submitting the form
        WebElement usernameElement = driver.findElement(By.id("username"));
        WebElement passwordElement = driver.findElement(By.id("password"));
        WebElement formElement = driver.findElement(By.name("LoginPage"));

        usernameElement.sendKeys("replaceme");
        passwordElement.sendKeys("replaceme");

        //passwordElement.submit(); // submit by text input element
        formElement.submit();        // submit by form element
        Thread.sleep(1000);
        //driver.findElement(By.xpath("//a[@href='/incident_list.do?sysparm_view=&sysparm_first_row=1&sysparm_query=assignment_group.nameSTARTSWITHPT%20-%20Order%20Services%5EstateNOT%20IN6%2C7%5Ecaller_idLIKEMML&sysparm_clear_stack=true']")).click();
        while (true) {
            //Thread.sleep(1000);
            driver.get("http://gaptech.service-now.com/incident_list.do?sysparm_view=&sysparm_first_row=1&sysparm_query=assignment_group.nameSTARTSWITHPT%20-%20Order%20Services%5EstateNOT%20IN6%2C7%5Ecaller_idLIKEMML&sysparm_clear_stack=true");
            //Thread.sleep(1000);
            List<WebElement> formlink = driver.findElementsByTagName("a");
            Optional<WebElement> incident = formlink.stream()
                .filter(link -> link.getAttribute("href") != null
                    && link.getAttribute("href").contains("incident.do")
                    && link.getAttribute("href").contains("sysparm_record_target=incident"))
                .findFirst();
            if (incident.isPresent()) {
                driver.get(incident.get().getAttribute("href"));

                selectValue(driver, "incident.state", "Resolved");

                WebElement assignedTo = driver.findElement(By.id("sys_display.incident.assigned_to"));
                assignedTo.sendKeys("mu3o2l2");
                assignedTo.sendKeys(Keys.TAB);

                WebElement affectedCi = driver.findElement(By.id("sys_display.incident.cmdb_ci"));
                affectedCi.clear();
                affectedCi.sendKeys("Order Service (Sourcing)"); //replaceme
                //affectedCi.sendKeys(Keys.TAB);
                Thread.sleep(1000);

                WebElement marketUnlock = driver.findElement(By.id("incident.u_market_unlock"));
                marketUnlock.click();

                WebElement marketEdit = driver.findElement(By.id("sys_display.incident.u_market"));
                marketEdit.sendKeys("NA");
                marketEdit.sendKeys(Keys.TAB);

                WebElement brandUnlock = driver.findElement(By.id("incident.u_brand_unlock"));
                brandUnlock.click();

                WebElement brandEdit = driver.findElement(By.id("sys_display.incident.u_brand"));
                brandEdit.sendKeys("Gap");
                brandEdit.sendKeys(Keys.TAB);

                Thread.sleep(1000);

                selectValue(driver, "incident.u_response_type", "Reactive");

                selectValue(driver, "incident.u_app_infra", "App");

                List<WebElement> tabs = driver.findElementsByClassName("tab_caption_text");
                tabs.get(4).click();

                selectValue(driver, "incident.close_code", "No Action Required");

                selectValue(driver, "incident.u_root_cause_code", "Working as Designed");


                WebElement closeNotes = driver.findElement(By.id("incident.close_notes"));
                closeNotes.sendKeys("no action required");

                WebElement rootCauseNotes = driver.findElement(By.id("incident.u_root_cause_notes"));
                rootCauseNotes.sendKeys("working as designed");
                rootCauseNotes.sendKeys(Keys.TAB);

                Thread.sleep(7000);
                List<WebElement> buttons = driver.findElementsByTagName("button");
                buttons.get(12).click();
            } else {
                break;
            }
        }
        // Conclude a test
        // driver.quit();
    }

    private static void selectValue(ChromeDriver driver, String fieldName, String fieldValue) {
        WebElement selectionBox = driver.findElement(By.id(fieldName));
        List<WebElement> options = selectionBox.findElements(By.tagName("option"));
        for (WebElement option : options) {
            if (option.getText().equals(fieldValue)) {
                option.click();
                break;
            }
        }
    }
}
