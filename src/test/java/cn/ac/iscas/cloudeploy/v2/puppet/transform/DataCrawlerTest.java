package cn.ac.iscas.cloudeploy.v2.puppet.transform;
import org.junit.Before;
import org.junit.Test;

import cn.ac.iscas.cloudeploy.v2.model.util.DataCrawler;
public class DataCrawlerTest {
	DataCrawler dataCrawler;
	String url;
	@Before
	public void init(){
		dataCrawler = new DataCrawler();
		url = "https://api.github.com/repos/puppetlabs/puppetlabs-mysql/commits/211da13545930d80978159b414d2ea9bd174e037";
	}
	@Test
	public void getDataTest(){
		System.out.println(dataCrawler.getData(url));
	}
}
