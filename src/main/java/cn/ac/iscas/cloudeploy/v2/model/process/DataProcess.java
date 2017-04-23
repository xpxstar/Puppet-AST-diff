package cn.ac.iscas.cloudeploy.v2.model.process;

import cn.ac.iscas.cloudeploy.v2.model.entity.crawl.CProcess;
import cn.ac.iscas.cloudeploy.v2.model.service.commit.DataStorage;
import cn.ac.iscas.cloudeploy.v2.puppet.compare.service.GenModuleAST;

public class DataProcess {
	private DataStorage dataStore;
	private GenModuleAST genModuleAST;
	private static String baseFile="F:/my/modulefound/statistic/puppet/order400/unzip/";
	public DataProcess(){
		dataStore = new DataStorage();
		genModuleAST = new GenModuleAST();
	}
	public void executeProcess(String path,String module){
		dataStore.scanModuleList(baseFile+path, module);
		CProcess cp = new CProcess(1,1,1);
		dataStore.storeModuleCommitDetail(module, cp);
		cp = new CProcess(1,1,1);
		dataStore.correctCommitFiles(module, cp);
		cp = new CProcess(1,1,1);
		genModuleAST.generateAST(module, cp);
		
	}
	
	
	
}
