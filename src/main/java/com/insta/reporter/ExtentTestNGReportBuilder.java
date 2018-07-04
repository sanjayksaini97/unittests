package com.insta.reporter;

import java.lang.reflect.Method;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.google.gson.LongSerializationPolicy;

public class ExtentTestNGReportBuilder {

	public static ExtentReports extent;
	public static ThreadLocal parentTest = new ThreadLocal();
	public static ThreadLocal test = new ThreadLocal();
	public ExtentTest logger ;

	@BeforeSuite
	public void beforeSuite() {
		extent = ExtentManager.createInstance("extent.html");
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("extent.html");
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("HostName", "Instaqa_WIN_System");
		extent.setSystemInfo("Test Type", "Unit and Integration tests");
		extent.setSystemInfo("Blocking mode", "true");
		extent.setSystemInfo("Env", "Development");
	}
	
    @BeforeClass
    public synchronized void beforeClass() {
        ExtentTest parent = extent.createTest(getClass().getName());
        parentTest.set(parent);
    }



    @AfterMethod
    public synchronized void afterMethod(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE){
           // ((ExtentTest) test.get()).fail(result.getThrowable());
            ((ExtentTest)test.get()).log(Status.FAIL, "Test failed!!");
        }
        else if (result.getStatus() == ITestResult.SKIP){
            //((ExtentTest) test.get()).skip(result.getThrowable());
            ((ExtentTest)test.get()).log(Status.SKIP, "Test skipped!!");
        }
        else{
           // ((ExtentTest) test.get()).pass("Test passed");
            ((ExtentTest)test.get()).log(Status.PASS, "Test passed!!");
        }

        extent.flush();
    }
}