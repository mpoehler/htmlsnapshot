package eu.tuxoo.htmlsnapshot;

import org.apache.commons.io.IOUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.color.ICC_Profile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HtmlSnapShotServlet extends HttpServlet {

    protected static ChromeDriverService service;

    protected WebDriver driver = new ChromeDriver();
    protected Map<String, Dimension> dimensionMap;


    @Override
    public void init() throws ServletException {
        super.init();
        dimensionMap = new HashMap<String, Dimension>();
        dimensionMap.put("BigDesktop", new Dimension(1920, 1200));
        dimensionMap.put("GenericNotebook", new Dimension(1280,800));
        dimensionMap.put("GoogleNexus5", new Dimension(360,640));
        dimensionMap.put("GoogleNexus7", new Dimension(966,604));

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("fetching " + req.getParameter("url"));
        String output = req.getParameter("output");
        driver.get(req.getParameter("url"));

        // sleep 3s to let the driver load and execute everything
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (output == null || output.equalsIgnoreCase("source")) {
            // pagesource

            // return the rendered source
            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(driver.getPageSource());
        } else if (output.equalsIgnoreCase("screenshot")) {
            // screenshot
            String resolution = req.getParameter("resolution");
            if (resolution != null && dimensionMap.containsKey(resolution)) {
                driver.manage().window().setSize(dimensionMap.get(resolution));
            } else {
                // default resolution
                driver.manage().window().setSize(new Dimension(1280,800));
            }

            // return the screenshot
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            IOUtils.copy(new FileInputStream(screenshotFile),resp.getOutputStream());
        }

    }

    @Override
    public void destroy() {
        super.destroy();
        driver.quit();
    }
}