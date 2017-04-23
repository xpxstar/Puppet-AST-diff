package cn.ac.iscas.cloudeploy.v2.model.service.commit;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import cn.ac.iscas.cloudeploy.v2.model.entity.crawl.CProcess;
public class DataStorageTest {
	DataStorage dataStorage;
	@Before
	public void init(){
		dataStorage = new DataStorage();
//		url = "https://api.github.com/repos/puppetlabs/puppetlabs-mysql/commits?path=manifests/server.pp&page=1";
//		url = "https://api.github.com/repos/puppetlabs/puppetlabs-mysql/commits/211da13545930d80978159b414d2ea9bd174e037";
		
	}
	@Test
	@Ignore
	public void scanModuleListTest(){
		//String[] tars = {"puppetlabs-puppetdb","puppetlabs-stdlib","puppetlabs-apt","puppetlabs-firewall","haraldsk-nfs","stackstormst2"}
		int num = dataStorage.scanModuleList("F:/my/modulefound/statistic/puppet/order400/unzip/jfryman-selinux-0.3.1","voxpupuli/puppet-selinux");
		System.out.println(num);
	}
	@Test
	@Ignore
	public void storeCommitListTest(){
		dataStorage.storeCommitList("puppetlabs/puppetlabs-mysql","manifests/bindings.pp");
	}
	@Test
	public void correctModuleCommits(){
		CProcess cp = new CProcess(1,1,1);
		dataStorage.correctCommitFiles("puppetlabs/puppetlabs-nodejs",cp);
	}
	@Test
	@Ignore
	public void storeModuleCommitDetailTest(){
		CProcess cp = new CProcess(1,1,1);
		dataStorage.storeModuleCommitDetail("puppetlabs/puppetlabs-nodejs",cp);
	}
	@Test
	@Ignore
	public void storeCommitDetailTest(){
		CProcess cp = new CProcess(1,1,1);
		dataStorage.storeCommitDetail("puppetlabs/puppetlabs-mysql","manifests/bindings.pp",cp);
	}
	
	@Test
	@Ignore
	public void storeRawDataTest(){
		dataStorage.storeRawData(null,"puppetlabs/puppetlabs-mysql/manifests/server.pp");
	}
}
