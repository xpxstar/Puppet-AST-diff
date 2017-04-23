package cn.ac.iscas.cloudeploy.v2.puppet.compare.service;

import java.io.File;
import java.util.List;

import cn.ac.iscas.cloudeploy.v2.model.entity.crawl.CProcess;
import cn.ac.iscas.cloudeploy.v2.model.entity.crawl.CommitChange;
import cn.ac.iscas.cloudeploy.v2.model.util.DataExtractor;
import cn.ac.iscas.cloudeploy.v2.model.util.FileUtil;
import cn.ac.iscas.cloudeploy.v2.puppet.compare.ast.entity.Diff;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.ResouceType;

/**
 * @author admin
 *
 */
public class CompareModule {
	public static FileUtil summary;
	public static FileUtil detail;
	DataExtractor de;
	GenModuleAST gm;
	ASTCompareImpl ac;
	FileUtil total;
	FileUtil simtotal;
	String module;
	
	public static FileUtil getDetail() {
		return detail;
	}
	public static void setDetail(FileUtil detail) {
		CompareModule.detail = detail;
	}
	public static FileUtil getSummary() {
		return summary;
	}
	public static void setSummary(FileUtil summary) {
		CompareModule.summary = summary;
	}
	public CompareModule() {
		de = new DataExtractor();
		gm = new GenModuleAST();
		ac = new ASTCompareImpl();
	}
	public CompareModule(String m) {
		super();
		de = new DataExtractor();
		gm = new GenModuleAST();
		ac = new ASTCompareImpl();
		total = new FileUtil(m,"tdiff",false);
		simtotal = new FileUtil(m,"simdiff",false);
		module = m;
	}
	/**
	 * @param module
	 * @param cp
	 * @param argument whether compare argument
	 */
	public void compareModules(String module,CProcess cp,boolean argument){
		total = new FileUtil(module,"tdiff",false);
		simtotal = new FileUtil(module,"simdiff",false);
		this.module = module;
		FileUtil nalist = new FileUtil(module,"filelist",false);
		String path = nalist.readLine();
		int count = 1;
		while (path != null) {
			if (++count > cp.getFile()) {
				System.out.println(module+" : "+path);
				compareFiles(module,path,cp,argument);
				cp.setFile(count);
				cp.setCommit(1);
			}
			path = nalist.readLine();
		}
		nalist.close();
		total.close();
		simtotal.close();
	}
	/**
	 * @param cp
	 * @param argument whether compare argument
	 */
	public void compareModules(CProcess cp,boolean argument){
		FileUtil nalist = new FileUtil(module,"filelist",false);
		String path = nalist.readLine();
		int count = 1;
		while (path != null) {
			if (++count > cp.getFile()) {
				System.out.println(module+" : "+path);
				compareFiles(module,path,cp,argument);
				cp.setFile(count);
				cp.setCommit(1);
			}
			path = nalist.readLine();
		}
		nalist.close();
		total.close();
		simtotal.close();
	}
	/**
	 * @param module
	 * @param path
	 * @param cp
	 * @param argument whether compare argument
	 */
	public void compareFiles(String module,String path,CProcess cp,boolean argument){
		FileUtil nalist = new FileUtil(module+"/"+path,"vchange",false);
		if (!new File(nalist.getFileName()).exists()) {
			System.err.println("pass: "+path);
			return;
		}
		FileUtil diff = new FileUtil(module+"/"+path,"diff",false);
		FileUtil diffd = new FileUtil(module+"/"+path,"diffd",false);
		String change = nalist.readLine();
		int count = 1;
		ResouceType after  = null;
		CommitChange afterChange = null;
		while (change != null) {
			if (++count > cp.getCommit()) {
				CommitChange cc = de.extractChange(change);
				ResouceType before = gm.getResourceType(module, path, cc.getBefore());
				if (afterChange == null || (!afterChange.getBefore().equals(cc.getSha()))) {
					after = gm.getResourceType(module, path, cc.getSha());
				}
				List<Diff> re = ac.compare(after, before, cc.getChangeLines(),argument);
				if (re.size() > 0) {
					diff.write(cc.getSha()+"@");
					total.write(cc.getSha()+" ");
					summary.write(module+",");
					summary.write(cc.getSha()+",");
					detail.write(module+",");
					detail.write(cc.getSha()+",");
					simtotal.write(cc.getSha()+" ");
					diffd.write(cc.getSha()+"@");
					for (Diff df : re) {
						String fea = df.geneFeature();
						String dea = df.geneDetail();
						diff.write(fea);
						diff.write(",");
						simtotal.write(fea);
						simtotal.write(" ");
						summary.write(fea);
						summary.write(",");
						detail.write(dea);
						detail.write(",");
						total.write(dea);
						total.write(" ");
						diffd.write(dea);
						diffd.write(",");
					}
					summary.writeLine("");
					detail.writeLine("");
					simtotal.writeLine("");
					total.writeLine("");
					diff.writeLine("");
					diffd.writeLine("");
				}
				after = before;
				afterChange = cc;
			}
			change = nalist.readLine();
		}
		diffd.close();
		diff.close();
		nalist.close();
	}
	
}
