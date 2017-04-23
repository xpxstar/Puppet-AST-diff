package cn.ac.iscas.cloudeploy.v2.puppet.transform;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import cn.ac.iscas.cloudeploy.v2.model.entity.crawl.CommitBase;
import cn.ac.iscas.cloudeploy.v2.model.entity.crawl.CommitSimple;
import cn.ac.iscas.cloudeploy.v2.model.util.DataCrawler;
import cn.ac.iscas.cloudeploy.v2.model.util.DataExtractor;
public class DataExtractorTest {
	DataCrawler dataCrawler;
	DataExtractor dataExtractor;
	String url;
	@Before
	public void init(){
		dataCrawler = new DataCrawler();
		dataExtractor = new DataExtractor();
//		url = "https://api.github.com/repos/puppetlabs/puppetlabs-mysql/commits?path=manifests/server.pp&page=1";
//		url = "https://api.github.com/repos/puppetlabs/puppetlabs-mysql/commits/211da13545930d80978159b414d2ea9bd174e037";
		
	}
	@Test
	public void extractDataCommitTest(){
		url = "https://api.github.com/repos/puppetlabs/puppetlabs-mysql/commits?path=manifests/server.pp&page=3";
		String data = dataCrawler.getData(url);
		CommitBase[] commit = dataExtractor.extractCommitList(data);
		for (CommitBase c : commit) {
			System.out.println(c.getUrl());
		}
	}
	@Ignore
	@Test
	public void extractSingleCommitTest(){
		url = "https://api.github.com/repos/puppetlabs/puppetlabs-mysql/commits/9570b0342fb0710b8c346294312f42fa9af0436d";
		String data = dataCrawler.getData(url);
		CommitSimple commit = dataExtractor.extractCommit(data);
		System.out.println(commit.toString());
	}
	
}
