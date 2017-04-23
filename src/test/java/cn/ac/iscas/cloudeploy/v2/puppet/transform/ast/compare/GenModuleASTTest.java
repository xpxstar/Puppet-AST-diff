package cn.ac.iscas.cloudeploy.v2.puppet.transform.ast.compare;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import cn.ac.iscas.cloudeploy.v2.model.entity.crawl.CProcess;
import cn.ac.iscas.cloudeploy.v2.puppet.compare.service.GenModuleAST;
public class GenModuleASTTest {
	GenModuleAST genAst;
	@Before
	public void init(){
		genAst = new GenModuleAST();
	}
	@Test
	public void generateASTTest(){
		CProcess cp = new CProcess(1,1,1);
//		String[] tar = {"puppetlabs/puppetlabs-puppetdb","puppetlabs/puppetlabs-stdlib","puppetlabs/puppetlabs-firewall","puppetlabs/puppetlabs-java","puppetlabs/puppetlabs-apache","puppetlabs/puppetlabs-nodejs","voxpupuli/puppet-nginx","voxpupuli/puppet-selinux"};
//		for (String string : tar) {
			cp = new CProcess(1,1,1);
			genAst.generateAST("example42/puppet-mysql",cp);
//		}
		
	}
	@Test
	@Ignore
	public void parserFileTest(){
		CProcess cp = new CProcess(1,1,18);
		genAst.parserFile("puppetlabs/puppetlabs-mysql","manifests/params.pp",cp);
	}
}
