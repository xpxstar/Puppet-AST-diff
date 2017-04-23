package cn.ac.iscas.cloudeploy.v2.puppet.transform.ast.compare;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import cn.ac.iscas.cloudeploy.v2.model.entity.crawl.CProcess;
import cn.ac.iscas.cloudeploy.v2.model.util.FileUtil;
import cn.ac.iscas.cloudeploy.v2.puppet.compare.service.CompareModule;
public class CompareModuleTest {
	CompareModule cm;
	String[] tar = {
			"puppetlabs/puppetlabs-apache",
			"puppetlabs/puppetlabs-stdlib",
			"elastic/puppet-elasticsearch",
			"voxpupuli/puppet-nginx",
			"puppetlabs/puppetlabs-mysql",
			"puppetlabs/puppetlabs-postgresql",
			"puppetlabs/puppetlabs-firewall",
			"puppetlabs/puppetlabs-apt",
			"garethr/garethr-docker",
			"puppetlabs/puppetlabs-rabbitmq",
			"stackstorm/puppet-st2",
			"puppetlabs/puppetlabs-concat",
			"stankevich/puppet-python",
			"puppetlabs/puppetlabs-ntp",
//			"puppetlabs/puppetlabs-vcsrepo",
			"elastic/puppet-logstash",
			"puppetlabs/puppetlabs-mongodb",
			};
	
	@Before
	public void init(){
		cm = new CompareModule();
	}
	@Test
	@Ignore
	public void compareFileTest(){
		CProcess cp = new CProcess(1,1,1);
			cm.compareFiles("puppetlabs/puppetlabs-mysql","manifests/params.pp",cp,true);
	}
	@Test
	public void compareModuleTest(){
		CProcess cp = new CProcess(1,1,1);
		FileUtil sum = new FileUtil("","ssimdata-no-arg",false);
		FileUtil detail = new FileUtil("","deData-no-arg",false);
		CompareModule.setSummary(sum);
		CompareModule.setDetail(detail);
		for (String string : tar) {
			cp = new CProcess(1,1,1);
			System.err.println("Module-:"+string);
			cm.compareModules(string,cp,false);
		}
		sum.close();
		detail.close();
	}
}
