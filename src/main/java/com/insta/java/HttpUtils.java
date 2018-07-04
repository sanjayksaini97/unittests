package com.insta.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

public class HttpUtils {
	static CookieStore httpCookieStore = new BasicCookieStore();
	
	public CloseableHttpClient deleteResource(String url, String gurl, String username, String pwd) throws IOException, HttpException {
		CloseableHttpClient httpclient = buildHttpClient(gurl, username, pwd);
		try {
			HttpDelete httpDelete = new HttpDelete(url);
			httpDelete.setHeader("Accept", "application/json");
			CloseableHttpResponse response = httpclient.execute(httpDelete);
			try {
				System.out.println(response.getStatusLine().getStatusCode() + "      " + url);
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
		return httpclient;
	}

	public CloseableHttpClient buildHttpClient(String url, String username, String password) {
		int port;
		String host;
		String strippedURL = url.substring("http://".length());
		if (strippedURL.contains("/"))
			strippedURL = strippedURL.substring(0, strippedURL.indexOf("/"));
		if (strippedURL.contains(":")) {
			host = strippedURL.substring(0, strippedURL.indexOf(":"));
			port = Integer.parseInt(strippedURL.substring(strippedURL.indexOf(":") + 1));
		} else {
			host = strippedURL;
			port = 80;
		}
		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(new AuthScope(host, port), new UsernamePasswordCredentials(username, password));
		return HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();
	}
	
	public String httpPost(HashMap<String, String> headers, String URL, JSONObject body)  throws IOException {
		CloseableHttpClient httpClient = null;
		HttpClientBuilder builder = HttpClientBuilder.create().setDefaultCookieStore(httpCookieStore);
		httpClient = builder.build();
		HttpPost httpPost = new HttpPost(URL);
		for (Map.Entry<String, String> entry : headers.entrySet()) {
			httpPost.addHeader(entry.getKey(), entry.getValue());
		}
		
		if(body!=null){
		    StringEntity entity = new StringEntity(body.toString(), ContentType.APPLICATION_JSON);
		    httpPost.setEntity(entity);
		}

		CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
		System.out.println("POST Response Status:: " + httpResponse.getStatusLine().getStatusCode());

		BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));

		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = reader.readLine()) != null) {
			response.append(inputLine);
		}
		reader.close();

		System.out.println(response.toString());
		httpClient.close();
		return response.toString();
	}

}
