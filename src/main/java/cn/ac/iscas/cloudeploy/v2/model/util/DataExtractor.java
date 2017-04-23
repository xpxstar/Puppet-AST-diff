package cn.ac.iscas.cloudeploy.v2.model.util;

import java.io.IOException;
import java.util.regex.Pattern;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import cn.ac.iscas.cloudeploy.v2.model.entity.crawl.ChangeLine;
import cn.ac.iscas.cloudeploy.v2.model.entity.crawl.CommitBase;
import cn.ac.iscas.cloudeploy.v2.model.entity.crawl.CommitChange;
import cn.ac.iscas.cloudeploy.v2.model.entity.crawl.CommitSimple;
import cn.ac.iscas.cloudeploy.v2.model.entity.crawl.LinePair;
import cn.ac.iscas.cloudeploy.v2.puppet.compare.ast.entity.Diff;
/**
 * @author xpxstar@gmail.com
 *
 */
public class DataExtractor {
	ObjectMapper objectMapper;
	Pattern sigment = Pattern.compile("@@.*@@");
	Pattern number = Pattern.compile("[0-9]{1,}");  
	public DataExtractor(){
		objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.setSerializationInclusion(Inclusion.NON_NULL);
	}
	
	/**
	 * @param data string of commit list in form of json.
	 * @return the commit url in order of submit time(desc: new - old)
	 */
	public CommitBase[] extractCommitList(String data){
		CommitBase[] commits = null;
		try {
			commits = (CommitBase[])objectMapper.readValue(data, CommitBase[].class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return commits;
	}
	public String parseJSON(Object obj){
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * @param data
	 * @return
	 */
	public CommitSimple extractCommit(String data){
		CommitSimple result = null;
		try {
			result = objectMapper.readValue(data, CommitSimple.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * @param data
	 * @return
	 */
	public CommitChange extractChange(String data){
		CommitChange result = null;
		try {
			result = objectMapper.readValue(data, CommitChange.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * @param data
	 * @return
	 */
	public String encodeDiff(List<Diff> diffs){
		String result = null;
		try {
			result = objectMapper.writeValueAsString(diffs);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * @param last
	 * @param cs
	 * @return
	 */
	public CommitChange extractCommitChange(String last,CommitSimple cs){
		if (null == cs ||cs.getTarget() == null|| cs.getTarget().getChanges() == 0 ) {
			return null;
		}
		CommitChange re = new CommitChange();
		re.setBefore(last);
		re.setSha(cs.getSha());
		re.setChanges(cs.getTarget().getChanges());
		re.setAdditions(cs.getTarget().getAdditions());
		re.setDeletions(cs.getTarget().getDeletions());
		re.setUrl(cs.getTarget().getRaw_url());
		Matcher smacher = sigment.matcher(cs.getTarget().getPatch());
		List<ChangeLine> changes = new ArrayList<>();
		while (smacher.find()) {
			String tt = smacher.group();
			Matcher nmacher = number.matcher(tt);
			nmacher.find();
			ChangeLine cl = new ChangeLine();
			LinePair before = new LinePair();
			before.setStart(Integer.valueOf(nmacher.group()));
			nmacher.find();
			before.setEnd(before.getStart()+Integer.valueOf(nmacher.group()));
			LinePair after = new LinePair();
			nmacher.find();
			after.setStart(Integer.valueOf(nmacher.group()));
			nmacher.find();
			after.setEnd(after.getStart()+Integer.valueOf(nmacher.group()));
			cl.setAfter(after);
			cl.setBefore(before);
			changes.add(cl);
		}
		re.setChangeLines(changes);
		return re;
	}
}
