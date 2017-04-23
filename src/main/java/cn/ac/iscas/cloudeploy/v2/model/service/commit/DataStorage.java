package cn.ac.iscas.cloudeploy.v2.model.service.commit;

import java.io.File;
import java.util.LinkedList;

import cn.ac.iscas.cloudeploy.v2.model.entity.crawl.CProcess;
import cn.ac.iscas.cloudeploy.v2.model.entity.crawl.CommitBase;
import cn.ac.iscas.cloudeploy.v2.model.entity.crawl.CommitChange;
import cn.ac.iscas.cloudeploy.v2.model.entity.crawl.CommitSimple;
import cn.ac.iscas.cloudeploy.v2.model.entity.crawl.FileModify;
import cn.ac.iscas.cloudeploy.v2.model.util.DataCrawler;
import cn.ac.iscas.cloudeploy.v2.model.util.DataExtractor;
import cn.ac.iscas.cloudeploy.v2.model.util.FileUtil;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.ResouceType;

public class DataStorage {
	private DataCrawler dataCrawler;
	private DataExtractor dataExtractor;
	public static String BaseFormat;
	public static int ChangeThreshold;
	public DataStorage() {
		dataCrawler = new DataCrawler();
		dataExtractor = new DataExtractor();
		BaseFormat = "https://api.github.com/repos/%s/commits?path=%s&page=%d";
		ChangeThreshold = 10;
	}
	
	public static String getBaseFormat() {
		return BaseFormat;
	}

	public static void setBaseFormat(String baseformat) {
		BaseFormat = baseformat;
	}
	
	/**遍历指定的module 文件列表
	 * @param module
	 */
	public int  scanModuleList(String path, String module){
		int filenum = 0;
		File ff = new File(path+"/manifests");
		FileUtil nalist = new FileUtil(module,"filelist",false);
		LinkedList<File> list = new LinkedList<>();
		list.add(ff);
		File tmp = null;
		while (!list.isEmpty()) {
			tmp = list.removeFirst();
			File[] files = tmp.listFiles();
			for (File f : files) {
				if (f.isDirectory()) {
					list.add(f);
				}else if (f.getName().endsWith("pp")) {
					String apath = "manifests"+f.getAbsolutePath().split("manifests")[1];
					apath = apath.replaceAll("\\\\", "/");
					nalist.writeLine(apath);
					nalist.flush();
					filenum++;
					storeCommitList(module,apath);
				}
			}
		}
		nalist.close();
		return filenum;
	}
	
	/**climb commit list of filename in module,
	 * and store it to module/fimename/commmits
	 * @param module
	 * @param filename
	 */
	public void storeCommitList(String module,String filename){
		String url = String.format(BaseFormat,module,filename, 1);
		CommitBase[] result = dataExtractor.extractCommitList(dataCrawler.getData(url));
		FileUtil fileUtil = new FileUtil(module+"/"+filename,"commits",false);
		int page=2;
		while(result !=null && result.length > 0 ){
			for (CommitBase c : result) {
				fileUtil.writeLine(c.getUrl());
			}
			url = String.format(BaseFormat,module,filename, page++);
			result = dataExtractor.extractCommitList(dataCrawler.getData(url));
		}
		fileUtil.close();
	}
	public void storeModuleCommitDetail(String module,CProcess cp){
		FileUtil fileUtil = new FileUtil(module,"filelist",false);
		String path = fileUtil.readLine();
		int count = 1;
		while (path != null) {
			if (++count > cp.getFile()) {
				storeCommitDetail(module,path,cp);
				cp.setFile(count);
				cp.setCommit(1);
			}
			path = fileUtil.readLine();		
		}
		fileUtil.close();
	}
	/**climb details of commits ,where commits is read from module/filename/commits.
	 * store the detail to module/filename/detail
	 * and extract changes meta from that detail
	 * then 
	 * @param module
	 * @param filename
	 */
	public boolean storeCommitDetail(String module,String filename,CProcess cp){
		
		FileUtil fileUtil = new FileUtil(module+"/"+filename,"commits",false);
		FileUtil detailfile = new FileUtil(module+"/"+filename,"detail",false);
		FileUtil change = new FileUtil(module+"/"+filename,"changes",false);
		String rawPath = module+"/"+filename+"/raw";
		String url = fileUtil.readLine();
		int count = 1;
		CommitSimple lastSha = new CommitSimple("0",null,null,null);
		boolean last = true,curr = false;
		while (url != null) {//restart from the stop point
			if (++count > cp.getCommit()) {
				CommitSimple cs = dataExtractor.extractCommit(dataCrawler.getData(url));
				if (null == cs || null == cs.getFiles()) {
					url = fileUtil.readLine();
					last = curr;
					lastSha = cs;
					continue;
				}
				for (FileModify fm : cs.getFiles()) {
					if (fm.getFilename().equals(filename)) {
						cs.setTarget(fm);
						break;
					}
				}
				cs.setFiles(null);
				detailfile.writeLine(dataExtractor.parseJSON(cs));
				detailfile.flush();
				CommitChange cc = dataExtractor.extractCommitChange(lastSha.getSha(),cs);
				if (cc == null ) {
					last = curr;
					lastSha = cs;
					url = fileUtil.readLine();
					continue;
				}
				String changestr = dataExtractor.parseJSON(cc);
				change.writeLine(changestr);
				change.flush();
				//store raw data
				curr = cc.getChanges()<ChangeThreshold;
				if (curr||last) {
					if(!storeRawData(cs, rawPath)){
						System.err.println(cp.toString());
					}
					if (!last) {
						if(!storeRawData(cs, rawPath)){
							System.err.println(cp.toString());
						}
					}
				}
				last = curr;
				lastSha = cs;
				System.out.println(cp.getFile()+" : "+filename+" : "+count);
			}
			url = fileUtil.readLine();
		}
		fileUtil.close();
		detailfile.close();
		change.close();
		return true;
	}
	
	/**correct changes
	 * then 
	 * @param module
	 * @param filename
	 */
	public void correctCommitFiles(String module,CProcess cp){
		FileUtil fileUtil = new FileUtil(module,"filelist",false);
		String path = fileUtil.readLine();
		int count = 1;
		while (path != null) {
			if (++count > cp.getFile()) {
				correctCommitChanges(module,path,cp);
				cp.setFile(count);
				cp.setCommit(1);
			}
			path = fileUtil.readLine();		
		}
		fileUtil.close();
	}
	/**correct changes
	 * then 
	 * @param module
	 * @param filename
	 */
	public boolean correctCommitChanges(String module,String filename,CProcess cp){
		
		FileUtil changes = new FileUtil(module+"/"+filename,"changes",false);
		FileUtil cchange = new FileUtil(module+"/"+filename,"cchange",false);
		FileUtil change = new FileUtil(module+"/"+filename,"vchange",false);
		String commitc = changes.readLine();
		CommitChange lastc = dataExtractor.extractChange(commitc);
		int count = 1;
		while (commitc != null) {
			commitc = changes.readLine();
			if (commitc == null) {
				break;
			}
			if (++count > cp.getCommit()) {
				CommitChange tempc = dataExtractor.extractChange(commitc);
				lastc.setBefore(tempc.getSha());
				cchange.writeLine(dataExtractor.parseJSON(lastc));	
				if (lastc.getChanges() < ChangeThreshold) {
					change.writeLine(dataExtractor.parseJSON(lastc));
				}
				lastc = tempc;
			}
		}
		changes.close();
		new File(changes.getFileName()).delete();
		cchange.close();
		change.close();
		return true;
	}
	
	public boolean storeRawData(CommitSimple cs,String path){
		if(cs == null){
			System.err.println("Commit Simple Null");
			return false;
		}
		FileUtil rawdata = new FileUtil(path,cs.getSha(),false);
		String data = dataCrawler.getData(cs.getTarget().getRaw_url());
		if (data != null ) {
			rawdata.write(data);
			rawdata.close();
			return true;
		}else{
			return false;
		}
		
	}
}
