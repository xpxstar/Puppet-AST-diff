package cn.ac.iscas.cloudeploy.v2.puppet.transform.ast.compare;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import cn.ac.iscas.cloudeploy.v2.model.entity.crawl.CProcess;
import cn.ac.iscas.cloudeploy.v2.model.process.DataProcess;
import cn.ac.iscas.cloudeploy.v2.puppet.compare.service.CompareModule;
public class DataProcessTest {
	DataProcess dp;
	@Before
	public void init(){
		dp = new DataProcess();
	}
	@Test
//	@Ignore
	public void compareFileTest(){
		dp.executeProcess("stankevich-python-1.12.0","stankevich/puppet-python");
		/*
		 * 
		"garethr/garethr-docker",
		"puppetlabs/puppetlabs-rabbitmq",
		"puppetlabs/puppetlabs-concat",
		"stankevich/puppet-python",
		"puppetlabs/puppetlabs-ntp",
		"puppetlabs/puppetlabs-vcsrepo",
		"elastic/puppet-logstash",
				
		 */
	}
//	@Test
//	public void compareModuleTest(){
//		CProcess cp = new CProcess(1,1,1);
//		cm.compareModules(cp);
//	}
}
