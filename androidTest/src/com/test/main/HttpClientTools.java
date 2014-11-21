/**
 * 
 */
package com.test.main;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * @author zg
 *
 */
public class HttpClientTools {
	/**
	 * 方法1： 作用：实现网络访问文件，将获取到数据储存在文件流中
	 * 
	 * @param url
	 *            ：访问网络的url地址
	 * @return inputstream
	 */
	public static InputStream loadFileFromURL(String url) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet requestGet = new HttpGet(url);
		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(requestGet);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = httpResponse.getEntity();
				return entity.getContent();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 方法2： 作用：实现网络访问文件，将获取到的数据存在字节数组中
	 * 
	 * @param url
	 *            ：访问网络的url地址
	 * @return byte[]
	 */
	public static byte[] loadByteFromURL(String url) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet requestGet = new HttpGet(url);
		try {
			HttpResponse httpResponse = httpClient.execute(requestGet);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				return EntityUtils.toByteArray(httpEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("====>" + e.toString());
		}
		return null;
	}

	/**
	 * 方法3： 作用：实现网络访问文件，返回字符串
	 * 
	 * @param url
	 *            ：访问网络的url地址
	 * @return String
	 */
	public static String loadTextFromURL(String url) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet requestGet = new HttpGet(url);
		try {
			HttpResponse httpResponse = httpClient.execute(requestGet);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				return EntityUtils.toString(httpEntity, "utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 方法4： 作用：实现网络访问文件，先给服务器通过“GET”方式提交数据，再返回相应的数据
	 * 
	 * @param url
	 *            ：访问网络的url地址
	 * @param params
	 *            String url：访问url时，需要传递给服务器的参数。
	 *            第二个参数格式为：username=wangxiangjun&password=123456
	 * @return byte[]
	 */
	public static byte[] doGetSubmit(String url, String params) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet requestGet = new HttpGet(url + "?" + params);
		try {
			HttpResponse httpResponse = httpClient.execute(requestGet);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				return EntityUtils.toByteArray(httpEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 方法5： 作用：实现网络访问文件，先给服务器通过“POST”方式提交数据，再返回相应的数据
	 * 
	 * @param url
	 *            ：访问网络的url地址
	 * @param params
	 *            String url：访问url时，需要传递给服务器的参数。 第二个参数为：List<NameValuePair>
	 * @return byte[]
	 */
	public static byte[] doPostSubmit(String url, List<NameValuePair> params) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost requestPost = new HttpPost(url);
		try {
			requestPost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
			HttpResponse httpResponse = httpClient.execute(requestPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				return EntityUtils.toByteArray(httpEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 方法6： 作用：实现网络访问文件，先给服务器通过“POST”方式提交数据，再返回相应的数据
	 * 
	 * @param url
	 *            ：访问网络的url地址
	 * @param params
	 *            String url：访问url时，需要传递给服务器的参数。 Map<String , Object>
	 * @return byte[]
	 */
	public static byte[] doPostSubmit(String url, Map<String, Object> params) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost requestPost = new HttpPost(url);

		List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
		try {
			if (params != null) {
				for (Map.Entry<String, Object> entry : params.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue().toString();
					BasicNameValuePair nameValuePair = new BasicNameValuePair(
							key, value);
					parameters.add(nameValuePair);
				}
			}
			requestPost
					.setEntity(new UrlEncodedFormEntity(parameters, "utf-8"));
			HttpResponse httpResponse = httpClient.execute(requestPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				return EntityUtils.toByteArray(httpEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
