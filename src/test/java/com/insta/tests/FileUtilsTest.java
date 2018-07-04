package com.insta.tests;

import static org.testng.Assert.assertNotNull;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.insta.java.FileUtils;
import com.insta.reporter.ExtentTestNGReportBuilder;

public class FileUtilsTest extends ExtentTestNGReportBuilder{
	
    @BeforeMethod
    public synchronized void beforeMethod(Method method) {
        ExtentTest child = ((ExtentTest) parentTest.get()).createNode(method.getName());
        test.set(child);
        ((ExtentTest)test.get()).log(Status.INFO, "Test '"  + method.getName() +  "' starting now");
    }
	
	@Test (expectedExceptions = IOException.class)
	public void testFileDoesNotExist() throws IOException{		
		((ExtentTest)test.get()).log(Status.INFO, "This test checks the logic if file is missing");
		FileUtils file = new FileUtils();
		((ExtentTest)test.get()).log(Status.FAIL, "Actual exception = NullPointerException  BUT  Expected exception = IOException");
		List<String>  list = file.getFileContent("invalidfile.txt");
		
	}
	
	@Test
	public void testFileNotNull() throws IOException{
		((ExtentTest)test.get()).log(Status.INFO, "This test checks the logic if file is not null");
		FileUtils file = new FileUtils();
		List<String>  list = file.getFileContent("fileutil.txt");
		assertNotNull(list);
		((ExtentTest)test.get()).log(Status.PASS, "Asserting not null");
		assert list.size() == 4;
		
	}
	
	@Test
	public void testFileSize() throws IOException{
		((ExtentTest)test.get()).log(Status.INFO, "This test checks the number of lines in file");
		FileUtils file = new FileUtils();
		List<String>  list = file.getFileContent("fileutil.txt");
		assert list.size() == 4;
		((ExtentTest)test.get()).log(Status.PASS, "Actual = " + list.size() + " Expected = 4");
	}
	
	@Test
	public void testFileContent() throws IOException{
		((ExtentTest)test.get()).log(Status.INFO, "This test checks the content");
		boolean containsText = false;
		FileUtils file = new FileUtils();
		List<String>  list = file.getFileContent("fileutil.txt");
		for (String str : list) {
			if(str.contains("instaqa")){
				containsText = true;
				break;
			}
		}
		assert containsText;
		((ExtentTest)test.get()).log(Status.PASS, "File contains text: instaqa" );
	}

}
