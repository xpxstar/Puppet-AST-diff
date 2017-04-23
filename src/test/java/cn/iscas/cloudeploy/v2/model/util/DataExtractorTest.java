package cn.iscas.cloudeploy.v2.model.util;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import cn.ac.iscas.cloudeploy.v2.model.entity.crawl.CommitChange;
import cn.ac.iscas.cloudeploy.v2.model.entity.crawl.CommitSimple;
import cn.ac.iscas.cloudeploy.v2.model.entity.crawl.FileModify;
import cn.ac.iscas.cloudeploy.v2.model.util.DataExtractor;
public class DataExtractorTest {
	DataExtractor dataExtractor;
	@Before
	public void init(){
		dataExtractor = new DataExtractor();
//		url = "https://api.github.com/repos/puppetlabs/puppetlabs-mysql/commits?path=manifests/server.pp&page=1";
//		url = "https://api.github.com/repos/puppetlabs/puppetlabs-mysql/commits/211da13545930d80978159b414d2ea9bd174e037";
		
	}
	@Test
	public void extractCommitChangeTest(){
		CommitSimple cs = new CommitSimple();
		FileModify target = new FileModify();
		target.setAdditions(1);
		target.setChanges(6);
		target.setDeletions(3);
		target.setPatch("@@ -15,6 +15,10 @@\n   $service_manage          = $mysql::params::server_service_manage,\n   $service_name            = $mysql::params::server_service_name,\n   $service_provider        = $mysql::params::server_service_provider,\n+  $users                   = {},\n+  $grants                  = {},\n+  $databases               = {},\n+\n   # Deprecated parameters\n   $enabled                 = undef,\n   $manage_service          = undef\n@@ -43,6 +47,7 @@\n   include '::mysql::server::config'\n   include '::mysql::server::service'\n   include '::mysql::server::root_password'\n+  include '::mysql::server::providers'\n \n   if $remove_default_accounts {\n     class { '::mysql::server::account_security':\n@@ -58,6 +63,7 @@\n   Class['mysql::server::config'] ->\n   Class['mysql::server::service'] ->\n   Class['mysql::server::root_password'] ->\n+  Class['mysql::server::providers'] ->\n   Anchor['mysql::server::end']\n \n }");
		cs.setTarget(target);
		CommitChange cc = dataExtractor.extractCommitChange("0",cs);
		System.out.println(cc.getChangeLines().get(0));
	}
	@Test
	@Ignore
	public void extractCommitListTest(){
		dataExtractor.extractCommitList("");
	}
	
	@Test
	@Ignore
	public void extractCommitTest(){
		dataExtractor.extractCommit("");
	}
}
