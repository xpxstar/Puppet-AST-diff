package cn.ac.iscas.cloudeploy.v2.puppet.transform.ast.compare;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import cn.ac.iscas.cloudeploy.v2.model.entity.crawl.ChangeLine;
import cn.ac.iscas.cloudeploy.v2.model.entity.crawl.LinePair;
import cn.ac.iscas.cloudeploy.v2.puppet.compare.ast.entity.Diff;
import cn.ac.iscas.cloudeploy.v2.puppet.compare.service.ASTCompareImpl;
import cn.ac.iscas.cloudeploy.v2.puppet.compare.service.CompareModule;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.PuppetParser;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.ResouceType;
import gumtree.spoon.AstComparator;
public class ASTCompareTest {
	ASTCompareImpl comparable;
	String target = "";
	String path = "";
	PuppetParser parser;
	String rubyEnvironment;
	String PuppetParserSource;
	String PuppetAnalyseRuby;
	@Before
	public void initASTCompareTest(){
		parser=new PuppetParser();
		rubyEnvironment = "E:\\ruby\\Ruby21-x64\\bin\\ruby.exe";
		PuppetParserSource = "D:\\puppet_parser\\resource";
		PuppetAnalyseRuby = "D:\\puppet_parser\\main\\single-file-scanner.rb";
		comparable = new ASTCompareImpl();
		path = System.getProperty("user.dir");
		target = path+"/commit/puppetlabs/puppetlabs-postgresql/manifests/server.pp";
	}
	@Test
	public void geneAST(){
		ResouceType bb = parser.parserSingle(rubyEnvironment, PuppetParserSource, PuppetAnalyseRuby,target+"/raw/ab5c177b7665bbdce3ead22adc47e65fa4dbf819",target+"/ast/ab5c177b7665bbdce3ead22adc47e65fa4dbf819");
//		ResouceType aa = parser.parserSingle(rubyEnvironment, PuppetParserSource, PuppetAnalyseRuby,target+"raw/b7761efd5b25532c3753997c4dee4a97dd2328f2",target+"ast/b7761efd5b25532c3753997c4dee4a97dd2328f2");
	}
	@Test
	@Ignore
	public void compareTest(){
		ResouceType aa = parser.extractTree(target+"/ast/2abccab4d954436fe7239e7bf75809b05d528f8c");
		ResouceType bb = parser.extractTree(target+"/ast/369c83126b4b68f5fd8b32d0278f89b35c0b63c6");
		ChangeLine cl = new ChangeLine();
		cl.setAfter(new LinePair(13, 12));
		cl.setBefore(new LinePair(13, 9));
		List<ChangeLine> cls = new ArrayList<>();
		cls.add(cl);
		// last true/false implies argument compare
		List<Diff> relist=comparable.compare(aa,bb,cls,true);
//		comparable.compareArguments(aa,bb,relist);
		for (Diff diff : relist) {
			System.out.println(diff);
		}
	}
}
