package cn.ac.iscas.cloudeploy.v2.puppet.compare.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cn.ac.iscas.cloudeploy.v2.model.entity.crawl.ChangeLine;
import cn.ac.iscas.cloudeploy.v2.model.entity.crawl.LinePair;
import cn.ac.iscas.cloudeploy.v2.puppet.compare.ast.entity.ASTAction;
import cn.ac.iscas.cloudeploy.v2.puppet.compare.ast.entity.Diff;
import cn.ac.iscas.cloudeploy.v2.puppet.compare.ast.entity.Diff.DiffBuilder;
import cn.ac.iscas.cloudeploy.v2.puppet.compare.ast.entity.DiffDetail;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.ResouceType;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.ast.ASTASTArray;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.ast.ASTBase;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.ast.ASTBlockExpression;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.ast.ASTCaseOpt;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.ast.ASTCaseStatement;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.ast.ASTFunction;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.ast.ASTIfStatement;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.ast.ASTNop;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.ast.ASTNot;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.ast.ASTRelationship;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.ast.ASTResource;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.ast.ASTResourceInstance;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.ast.ASTResourceParam;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.ast.ASTSelector;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.ast.ASTVarDef;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.ast.ElementTop;

/**
 * @author admin
 *
 */
public class ASTCompareImpl {
	/**
	 * @param after
	 * @param before
	 * @param range 变动的行范围
	 * @return
	 */
	
	public List<Diff> compare(ResouceType after,ResouceType before,List<ChangeLine> lines,boolean argument){
		List<Diff> result = new ArrayList<>();
		if (before == null || after == null ) {
			return result;
		}
		//compare arguments of two resourcetype
		if(argument){
			compareArguments(after,before,result);
		}
		for (ChangeLine cl : lines) {
			compareBlockExpression(after.getCode(), before.getCode(),result, cl.getAfter(), cl.getBefore());
		}
		return result;
	}
	
	/**
	 * @param code
	 * @param class1
	 * @param result
	 */
	private void compareBlockExpression(ASTBlockExpression after,ASTBlockExpression before, 
			List<Diff> result,LinePair afline,LinePair bfline) { 
		//type of Code: resource,fuction,vardef,relationship,ifstatement,casestate
		// 两次遍历查找不同
		//compare blockExpression
		if (after == null || before == null|| after instanceof ASTNop||before instanceof ASTNop) {
			return ;
		}
		List<ASTBase> afChildren = after.getChildren();
		List<ASTBase> bfChildren = before.getChildren();
		int afstart = 0,afc = 0;//afstart : the begin of changed code,afc :count number of changed ASTBase
 		int i = 0;
		for (; i < afChildren.size(); i++ ) {
			if (afChildren.get(i).getLine() >= afline.getEnd()){
				if(afc == 0){
					afc++;
				}
				break;
			}
			if (afChildren.get(i).getLine() > afline.getStart()){
				afc++;
			}
		}
		if (i == afChildren.size()) {
			i--;
		}
		afc++;
		afstart = i-afc;
		afstart = Math.max(afstart, -1);		
		int bfstart = 0,bfc = 0;
		i= 0;
		for (; i < bfChildren.size();i++ ) {
			if (bfChildren.get(i).getLine() >= bfline.getEnd()){
				if(bfc == 0){
					bfc++;
				}
				break;
			}
			if (bfChildren.get(i).getLine() > bfline.getStart()){
				bfc++;
			}
			
		}
		if (i == bfChildren.size()) {
			i--;
		}
		bfc++;
		bfstart = i-bfc;
		bfstart = Math.max(bfstart, -1);
		i = 1;		
		if (afc == bfc) {//表明是修改update,依次比较不同
			for (; i <= afc && afstart+i < afChildren.size() && bfstart+i < bfChildren.size(); i++) {
				compareBase(afChildren.get(afstart+i),bfChildren.get(bfstart+i),result,afline,bfline);
			}
		}else if(afc > bfc){//表明是增加,或删除后增加
			System.err.println("addition");
		}else {//表明是删除,即减少了
			System.err.println("deleted");
		}
	}
	
	public void compareBase(ASTBase after,ASTBase before,List<Diff> result,LinePair afline,LinePair bfline){
		if (!after.getClass().equals(before.getClass())) {
			compareDiffType(after,before,result);
		}else if (after instanceof ASTResource && before instanceof ASTResource) {
			compareResource((ASTResource)after,(ASTResource)before,result);
		}else if (after instanceof ASTRelationship && before instanceof ASTRelationship) {
			compareRelationship((ASTRelationship)after,(ASTRelationship)before,result);
		}else if (after instanceof ASTFunction && before instanceof ASTFunction) {
			compareFunction((ASTFunction)after,(ASTFunction)before,result);
		}else if (after instanceof ASTIfStatement && before instanceof ASTIfStatement) {
			compareIfStatement((ASTIfStatement)after,(ASTIfStatement)before,result,afline,bfline);
		}else if (after instanceof ASTVarDef && before instanceof ASTVarDef) {
			compareVarDef((ASTVarDef)after,(ASTVarDef)before,result, bfline, bfline);
		}else if (after instanceof ASTCaseStatement && before instanceof ASTCaseStatement) {
			compareCaseStatement((ASTCaseStatement)after,(ASTCaseStatement)before,result,afline,bfline);
		}else if (after instanceof ASTNot && before instanceof ASTNot) {
			compareNot((ASTNot)after,(ASTNot)before,result,afline,bfline);
		}else if (after instanceof ASTSelector && before instanceof ASTSelector) {
			compareSelector((ASTSelector)after,(ASTSelector)before,result,afline,bfline);
		}else {
			System.err.println("others:"+after.getClass().toString()+"-"+before.getClass());
		}
		
	}
	
	private void compareSelector(ASTSelector after, ASTSelector before, List<Diff> result, LinePair afline,
			LinePair bfline) {
		String aname = ASTAction.value;
		if (after.getParam() instanceof ElementTop ||before.getParam() instanceof ElementTop) {
			compareBase(after.getParam(), before.getParam(), result, afline, bfline);
			aname = after.getParam().elementName();
		}else{
			String astate = after.getParam().changeString();
			aname = after.getParam().elementName();
			String bstate = before.getParam().changeString();
			if (!astate.equals(bstate)) {
				Diff re = new Diff(ASTAction.selector, ASTAction.test,ASTAction.test,ASTAction.modify,
						new DiffDetail(ASTAction.string,bstate ,astate));
				result.add(re);
			}
		}
			DiffBuilder builder = Diff.builder();
			builder.type(ASTAction.selector)
				.position(ASTAction.value)
				.name(aname);
			ASTBase bvalue = before.getValues();
			ASTBase avalue = after.getValues();
			if (bvalue instanceof ASTASTArray && avalue instanceof ASTASTArray ) {
				Map<String,ASTBase> unvisited =new HashMap<>();
				for (ASTBase param : ((ASTASTArray)bvalue).getChildren()) {
					if (param instanceof ASTResourceParam) {
						ASTResourceParam pp = (ASTResourceParam)param;
						unvisited.put(pp.paramString(),pp.getValue());
					}
				}
				Diff re = null;
				for (ASTBase param :((ASTASTArray)avalue).getChildren()) {
					if (param instanceof ASTResourceParam) {
						ASTResourceParam pp = (ASTResourceParam)param;
						String key = pp.paramString();
						ASTBase target = unvisited.get(key);
						if (null != target && target instanceof ElementTop) {
							compareBase(pp.getValue(), target, result, afline, bfline);
						}else {
							re = compareValue(pp.getValue(), target, builder);
							if (re != null ) {
								re.setName(aname);
								result.add(re);
							}						
						}
						unvisited.remove(pp.paramString());
					}
				}
				for (String key:unvisited.keySet()) {
					re = compareString(null,key,builder);
					re.setName(aname);
					result.add(re);
				}
			}else {
				System.err.println("Selector Value diff: "+aname);
			}
	}

	private void compareDiffType(ASTBase after, ASTBase before, List<Diff> result) {
		Diff re = new Diff();
		re.setType(ASTAction.type);
		re.setPosition(getType(before));
		re.setName(getType(after));
		re.setAction(ASTAction.modify);
		re.setValue(new DiffDetail(ASTAction.string,before.getClass().getSimpleName(),after.getClass().getSimpleName()));
		result.add(re);
	}
	
	private String getType(ASTBase target) {
		if(target instanceof ASTResource){
			return ASTAction.resource;
		}else if (target instanceof ASTRelationship) {
			return ASTAction.relation;
		}else if (target instanceof ASTFunction) {
			return ASTAction.function;
		}else if (target instanceof ASTIfStatement) {
			return ASTAction.ifstatement;
		}else if (target instanceof ASTVarDef) {
			return ASTAction.vardef;
		}else if (target instanceof ASTCaseStatement) {
			return ASTAction.casestatement;
		}else if (target instanceof ASTNot) {
			return ASTAction.not;
		}else 
			return "";
	}

	public void compareNot(ASTNot after,ASTNot before,List<Diff> result,LinePair afline, LinePair bfline){
		compareBase(after.getValue(), before.getValue(), result, afline, bfline);
	}
	/** conpare resource
	 * @param after
	 * @param before
	 * @param result
	 */
	public void compareResource(ASTResource after,ASTResource before,List<Diff> result){
		if (!after.getType().equals(before.getType())) {//must be same resource.
			Diff re = new Diff(ASTAction.resource,after.getType(),ASTAction.type,ASTAction.modify,
					new DiffDetail(ASTAction.string,before.getType() ,after.getType()));
			result.add(re);
			return;
		} 
		DiffBuilder builder = Diff.builder();
		builder.type(ASTAction.resource)
		.position(after.getType());
		List<ASTBase> achildren = after.getInstances().getChildren();
		List<ASTBase> bchildren = before.getInstances().getChildren();
		int asize = achildren.size();
		int bsize = bchildren.size();
		//一般是数量相同
		for(int i = 0;i < asize && i < bsize;i++){
			if (achildren.get(i) instanceof ASTResourceInstance && bchildren.get(i) instanceof ASTResourceInstance ) {
				ASTResourceInstance ainstance = (ASTResourceInstance)achildren.get(i);
				ASTResourceInstance binstance = (ASTResourceInstance)bchildren.get(i);
				//compare title start
				Diff re = null;
				re = compareValue(ainstance.getTitle(), binstance.getTitle(), builder);
				if (re != null) {
					re.setName(ASTAction.title);
					result.add(re);
				}
				//compare title end,compare param start
				Map<String,String> unvisited =new HashMap<>();
				for (ASTBase param : binstance.getParameters().getChildren()) {
					if (param instanceof ASTResourceParam) {
						ASTResourceParam pp = (ASTResourceParam)param;
						unvisited.put(pp.paramString(),pp.getValue().changeString());
					}
				}
				for (ASTBase param : ainstance.getParameters().getChildren()) {
					if (param instanceof ASTResourceParam) {
						ASTResourceParam pp = (ASTResourceParam)param;
						String key = pp.paramString();
						String target = unvisited.get(key);
						re = compareString(pp.getValue().changeString(), target, builder);
						if (re != null ) {
							re.setName(key);
							result.add(re);
						}
						unvisited.remove(pp.paramString());
					}
				}
				for (String key:unvisited.keySet()) {
					re = compareString(null,key,builder);
					re.setName(key);
					result.add(re);
				}
			}else {
				System.err.println("Instance Diff: "+achildren.get(i).getLine()+": "+bchildren.get(i).getLine());
			}
		}
		
	}
	/**
	 * @param after
	 * @param before
	 * @param result
	 */
	private void compareFunction(ASTFunction after, ASTFunction before, List<Diff> result) {
		DiffBuilder builder = Diff.builder();
		if (!after.getName().equals(before.getName())) {
			Diff re = new Diff(ASTAction.function, after.getName(),ASTAction.name, ASTAction.modify,
					new DiffDetail(ASTAction.string,before.getName() ,after.getName()));
			result.add(re);
			return;
		}
		builder.type(ASTAction.function)
			.position(after.getName());
		//compare arguments start
		Diff re = null;
		re = compareValue(after.getArguments(), before.getArguments(), builder);
		if (re != null) {
			re.setName(ASTAction.param);
			result.add(re);
		}
	}
	
	private void compareRelationship(ASTRelationship after, ASTRelationship before, List<Diff> result) {
		String aship = after.changeString();
		String bship = before.changeString();
		if (!aship.equals(bship)) {
			Diff re = new Diff();
			re.setType(ASTAction.relation);
			re.setPosition(ASTAction.order);
			re.setName(after.getArrow());
			int anum = aship.split("->").length;
			int bnum = bship.split("->").length;
			if (anum < bnum) {
				re.setAction(ASTAction.delete);
			}else if (anum > bnum) {
				re.setAction(ASTAction.add);
			}else {
				re.setAction(ASTAction.modify);
			}
			re.setValue(new DiffDetail(ASTAction.string,bship,aship));
			result.add(re);
			return;
		}
	}
//	private void compareRelationship(ASTRelationship after, ASTRelationship before, List<Diff> result) {
//		System.err.println("relationship");
//	}
	
	private void compareIfStatement(ASTIfStatement after, ASTIfStatement before, List<Diff> result,LinePair afline, LinePair bfline) {
		if (after.getTest() instanceof ElementTop ||before.getTest() instanceof ElementTop ) {
			compareBase(after.getTest(), before.getTest(), result, afline, bfline);
		}else{
			String astate = after.getTest().changeString();
			String bstate = before.getTest().changeString();
			if (!astate.equals(bstate)) {
				Diff re = new Diff(ASTAction.ifstatement, ASTAction.iftest,ASTAction.test,ASTAction.modify,
						new DiffDetail(ASTAction.bool,bstate ,astate));
				result.add(re);
			}
				
		}
		compareBlockExpression(after.getStatements(), before.getStatements(), result,afline,bfline);
		if (null != after.getIfStatementElse() && null != before.getIfStatementElse() ) {
			compareBlockExpression(after.getIfStatementElse().getStatements(), before.getIfStatementElse().getStatements(), result,afline,bfline);
		}
	}
	public void compareVarDef(ASTVarDef after,ASTVarDef before,List<Diff> result,LinePair afline,LinePair bfline){
		DiffBuilder builder = Diff.builder();
		builder.type(ASTAction.vardef).position(ASTAction.var);
		if (after.getName() instanceof ElementTop ||before.getName() instanceof ElementTop ) {
			compareBase(after.getName(), before.getName(), result, afline, bfline);
		}else {
			String aname = after.getName().changeString();
			String bname = before.getName().changeString();
			if (aname.equals(bname)) {
				if (after.getValue() instanceof ElementTop || before.getValue() instanceof ElementTop) {
					compareBase(after.getValue(), before.getValue(), result, afline, bfline);
				}else {
					Diff re = compareValue(after, before, builder);
					if (null != re) {
						re.setName(ASTAction.value);
						result.add(re);
					}
				}
				
			}else {
				Diff re = compareString(aname, bname, builder);
				if (null != re) {
					re.setName(ASTAction.name);
					result.add(re);
				}
			}
		}
	}
	/**
	 * @param after
	 * @param before
	 * @param result
	 * @param afline
	 * @param bfline
	 */
	public void compareCaseStatement(ASTCaseStatement after,ASTCaseStatement before,List<Diff> result, LinePair afline, LinePair bfline){
		String atest=ASTAction.value;
		if (after.getTest() instanceof ElementTop ||before.getTest() instanceof ElementTop) {
			compareBase(after.getTest(), before.getTest(), result, afline, bfline);
			atest = after.getTest().elementName();
		}else{
			String astate = after.getTest().changeString();
			atest = after.getTest().elementName();
			String bstate = before.getTest().changeString();
			if (!astate.equals(bstate)) {
				Diff re = new Diff(ASTAction.casestatement,ASTAction.cases , ASTAction.test,ASTAction.modify,
						new DiffDetail(ASTAction.bool,bstate ,astate));
				result.add(re);
			}
		}
			List<ASTBase> aopts =  after.getOptions().getChildren();
			List<ASTBase> bopts =  before.getOptions().getChildren();
			Map<String, ASTBlockExpression> unvisited = new HashMap<>();
			for (int i = 0; i < bopts.size() ; i++) {
				if (bopts.get(i) instanceof ASTCaseOpt) {
					ASTCaseOpt opt = (ASTCaseOpt)bopts.get(i);
					unvisited.put(opt.getValue().changeString(), opt.getStatements());
				}
			}
			DiffBuilder builder = Diff.builder();
			builder.type(ASTAction.casestatement).position(ASTAction.option);
			for (ASTBase param :  aopts) {
				if (param instanceof ASTCaseOpt) {
					ASTCaseOpt aco = (ASTCaseOpt)param;
					String key = aco.getValue().changeString();
					ASTBlockExpression target = unvisited.get(key);
					if (target == null) {
						Diff re =  compareString(key,null,builder);
						re.setName(atest);
						result.add(re);
						continue;
					}
					compareBlockExpression(((ASTCaseOpt) param).getStatements(), target, result, afline, bfline);
					unvisited.remove(key);
				}
			}
			for (String key:unvisited.keySet()) {
				Diff re =  compareString(null,atest,builder);
				re.setName(key);
				result.add(re);
			}
	}

	/**compare arguments of two resourcetype
	 * @param after
	 * @param before
	 * @param result
	 */
	public void compareArguments(ResouceType after,ResouceType before,List<Diff> result){
		DiffBuilder builder = Diff.builder();
		builder.type(ASTAction.hostclass)
		.position(ASTAction.type);
		Diff re = null;
		re = compareString(after.getParent(),before.getParent(),builder);
		if (re != null) {
			re.setName(ASTAction.inherit);
			result.add(re);
		}
		Set<String> unvisited =new HashSet<>(before.getArguments().keySet());
		for (Entry<String, ASTBase> arg: after.getArguments().entrySet()) {
			ASTBase target  = before.getArguments().get(arg.getKey());
			unvisited.remove(arg.getKey());
			re = null;
			re = compareValue(arg.getValue(),target,builder);
			if (re != null) {
				re.setName(ASTAction.argument);
				re.getValue().setType(arg.getKey());
				result.add(re);
			}
		}
		for (String key:unvisited) {
			re = compareString(null,key,builder);
			re.setName(ASTAction.argument);
			result.add(re);
		}
	}
	
	
	/**
	 * ASTString
	 * @param after 之前
	 * @param before 之后
	 * @param builder builder 前缀
	 * @return
	 */
	public Diff compareValue(ASTBase after,ASTBase before,DiffBuilder builder){
		//check ASTString or ASTConcat
		if (after ==null && before ==null) {
			return null;
		}
		String astring = (null==after)?null:after.changeString();
		String bstring = (null==before)?null:before.changeString();
		if (astring ==null && bstring ==null) {
			return null;
		}
		Diff diff = null;
		if (null == bstring) {
			diff = builder.build();
			diff.setAction(ASTAction.add);
			diff.setValue(new DiffDetail(after.getClass().toString(), "",astring));
		}else if (null == astring) {
			diff = builder.build();
			diff.setAction(ASTAction.delete);
			diff.setValue(new DiffDetail(before.getClass().toString(), bstring,""));
		}else if (!astring.equals(bstring)) {
			diff = builder.build();
			diff.setAction(ASTAction.modify);
			diff.setValue(new DiffDetail(before.getClass().toString(), bstring,astring));
		}
		return diff;
	}
	/**
	 * ASTString
	 * @param after 之前
	 * @param before 之后
	 * @param builder builder 前缀
	 * @return
	 */
	public Diff compareString(String after,String before,DiffBuilder builder){
		//check ASTString or ASTConcat
		if (after ==null && before ==null) {
			return null;
		}
		String astring = after;
		String bstring = before;
		Diff diff = null;
		if (null == bstring) {
			diff = builder.build();
			diff.setAction(ASTAction.add);
			diff.setValue(new DiffDetail(ASTAction.string, "",astring));
		}else if (null == astring) {
			diff = builder.build();
			diff.setAction(ASTAction.delete);
			diff.setValue(new DiffDetail(ASTAction.string, bstring,""));
		}else if (!astring.equals(bstring)) {
			diff = builder.build();
			diff.setAction(ASTAction.modify);
			diff.setValue(new DiffDetail(ASTAction.string, bstring,astring));
		}
		return diff;
	}
}
