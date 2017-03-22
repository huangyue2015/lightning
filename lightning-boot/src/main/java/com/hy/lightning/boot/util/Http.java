package com.hy.lightning.boot.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

public class Http {

	private static Logger logger = Logger.getLogger(Http.class);

	public static String post(String url, Map<String, String> paramMap) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);
		httppost.addHeader("Content-Type", "application/x-www-form-urlencoded");
		HttpEntity entity = new StringEntity(getFormdata(paramMap));
		httppost.setEntity(entity);
		CloseableHttpResponse response = httpClient.execute(httppost);
		String result = null;
		if (response.getStatusLine().getStatusCode() == 200) {
			result = getResult(response);
		}
		return result;
	}

	private static String getResult(CloseableHttpResponse httpResponse) {
		HttpEntity httpEntity = httpResponse.getEntity();
		StringBuilder stringBuilder = new StringBuilder();
		InputStream inputStream = null;
		try {
			if (httpEntity != null) {
				inputStream = httpEntity.getContent();
				int i;
				byte[] tmp = new byte[1024];
				while ((i = inputStream.read(tmp)) != -1) {
					stringBuilder.append(new String(tmp, 0, i, Charset.forName("UTF-8")));
				}
			}
		} catch (IllegalStateException | IOException e) {
			logger.error(e, e);
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
				if(httpResponse != null)
					httpResponse.close();
			} catch (IOException e) {}
		}
		return stringBuilder.toString();
	}

	private static String getFormdata(Map<String, String> paramMap) {
		if (paramMap == null || paramMap.isEmpty())
			return "";
		StringBuilder stringBuilder = new StringBuilder();
		for (Entry<String, String> entry : paramMap.entrySet()) {
			stringBuilder.append(String.format("&%s=%s", entry.getKey(), entry.getValue()));
		}
		String rel = stringBuilder.toString();
		if (rel.length() > 0)
			return stringBuilder.substring(1);
		else
			return rel;
	}

	public static void main(String[] arges) {
		Map<String, String> map = new HashMap<>();
		map.put("name", "zhangsan");
		map.put("age", "12");
		System.out.println(new StringBuilder().toString().substring(1));
	}
}
