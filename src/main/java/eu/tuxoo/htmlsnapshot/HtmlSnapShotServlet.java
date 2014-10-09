package eu.tuxoo.htmlsnapshot;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class HtmlSnapShotServlet extends HttpServlet {

    protected static ChromeDriverService service;

    protected WebDriver driver = new ChromeDriver();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("url=" + req.getParameter("url"));
        driver.get(req.getParameter("url"));

        // TODO set a delay here to wait that all js is executed. ??

        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);


        resp.getWriter().write(driver.getPageSource());

    }

    @Override
    public void destroy() {
        super.destroy();
        driver.quit();
    }
}