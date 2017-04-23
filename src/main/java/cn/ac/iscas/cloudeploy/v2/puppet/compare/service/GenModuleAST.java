package cn.ac.iscas.cloudeploy.v2.puppet.compare.service;

import java.io.File;

import cn.ac.iscas.cloudeploy.v2.model.entity.crawl.CProcess;
import cn.ac.iscas.cloudeploy.v2.model.util.FileUtil;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.PuppetParser;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.ResouceType;

/**
 * @author admin
 *
 */
public class GenModuleAST {
	PuppetParser parser;
	String rubyEnvironment;
	String PuppetParserSource;
	String PuppetAnalyseRuby;
	String BasePath;
	public GenModuleAST() {
		rubyEnvironment = "E:\\ruby\\Ruby21-x64\\bin\\ruby.exe";
		PuppetParserSource = "D:\\puppet_parser\\resource";
		PuppetAnalyseRuby = "D:\\puppet_parser\\main\\single-file-scanner.rb";
		BasePath = System.getProperty("user.dir")+"\\commit";
		parser = new PuppetParser();
	}
	public void generateAST(String module,CProcess cp){
		FileUtil nalist = new FileUtil(module,"filelist",false);
		String path = nalist.readLine();
		int count = 1;
		while (path != null) {
			if (++count > cp.getFile()) {
				System.out.println(module+" : "+path);
				parserFile(module,path,cp);
				cp.setFile(count);
				cp.setCommit(1);
			}
			path = nalist.readLine();	
		}
		
	}
	public void parserFile(String module,String path,CProcess cp){
		File files = FileUtil.GenerateFile(module+"/"+path,"/raw");
		File astpath = FileUtil.MakeDir(module+"/"+path,"/ast/");
		File[] commits = files.listFiles();
		int count = 1;
		for (File file : commits) {
			if (file.isFile() && (++count > cp.getCommit())) {
				try {
					System.out.println(file.getName());
					parser.usingRubyAnalyseSingle(rubyEnvironment, PuppetParserSource, PuppetAnalyseRuby, file.getAbsolutePath(), astpath.getAbsolutePath()+"/"+file.getName());
				} catch (Exception e) {
					e.printStackTrace();
					System.err.println(module+" : "+cp.getFile()+" : "+count+" : "+file.getName());
				}
				
			}
		}
		
	}
	public ResouceType getResourceType(String module,String path,String sha){
		File file = FileUtil.GenerateFile(module+"/"+path+"/ast",sha);
		
		return parser.extractTree(file);
		
	}
	
}
