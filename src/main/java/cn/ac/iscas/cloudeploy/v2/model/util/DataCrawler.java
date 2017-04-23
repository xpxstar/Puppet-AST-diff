package cn.ac.iscas.cloudeploy.v2.model.util;

import java.io.IOException;
import java.util.Date;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * @author admin xpxstar@gmail.com
 *
 */
public class DataCrawler {
	/**
	 * 默认编码 utf-8
	 */
	private static String charset = "utf-8";
	/**
	 * 配置项  连接超时 3000 ms，读超时 3000 ms
	 */
	private static  RequestConfig requestCfg;
	
	public DataCrawler(){
		setConfig(3000, 3000);
	}
	public String getData(String uri){
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HttpGet request = new HttpGet(uri);
		request.setConfig(requestCfg);
		request.addHeader("Authorization", "Basic eHB4c3RhcjpIZWxsb1dvcmxkNzEx");
		CloseableHttpClient client = HttpClients
				.custom()
				.setDefaultRequestConfig(requestCfg)
				.build();
		String result = null;
		try {
			CloseableHttpResponse response = client.execute(request);
			Header[] hs = response.getHeaders("X-RateLimit-Remaining");
			if (hs !=null && hs.length > 0) {
				System.out.print(hs[0].getValue());
				System.out.print("--");
				System.out.println(new Date(Long.valueOf(response.getHeaders("X-RateLimit-Reset")[0].getValue())));
			}
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				result = EntityUtils.toString(entity,charset);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ConnectTimeoutException e){
			e.printStackTrace();
			System.err.println(request);
		} catch(IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println(request);
			e.printStackTrace();
		}finally {
			//关闭连接
			try {
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public void setConfig(int socketTimeout,int connectTimeout){
		requestCfg = RequestConfig.custom()
				.setSocketTimeout(socketTimeout)
				.setConnectTimeout(connectTimeout)
				.build();
	}
}
