package com.insta.tests;

import static org.testng.Assert.assertNotNull;
import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.testng.annotations.Test;

import com.insta.java.HttpUtils;
import com.insta.reporter.ExtentTestNGReportBuilder;

public class HttpUtilsTest extends ExtentTestNGReportBuilder {
	HttpUtils http = new HttpUtils();
	
	@Test
	public void testPostCall(){
		String url = "http://instaqa.in";
		String username = "username";
		String password = "password";
		CloseableHttpClient client = http.buildHttpClient(url, username, password);
		assertNotNull(client);
	}
	
	@Test
	public void testDeleteCall() throws IOException, HttpException{
		String url = "http://instaqa.in";
		String username = "username";
		String password = "password";
		CloseableHttpClient client = http.deleteResource(url, url, username, password);
		assertNotNull(client);
	}

}
