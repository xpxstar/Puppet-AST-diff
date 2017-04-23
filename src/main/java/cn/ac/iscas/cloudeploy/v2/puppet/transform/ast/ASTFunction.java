package cn.ac.iscas.cloudeploy.v2.puppet.transform.ast;

import java.util.List;

public class ASTFunction extends ElementTop{
	private String ftype;
	private String doc;
	private String name;
	private ASTASTArray arguments;
	private List<ASTBase> children;
	
	public String getFtype() {
		return ftype;
	}
	public void setFtype(String ftype) {
		this.ftype = ftype;
	}
	public String getDoc() {
		return doc;
	}
	public void setDoc(String doc) {
		this.doc = doc;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ASTASTArray getArguments() {
		return arguments;
	}
	public void setArguments(ASTASTArray arguments) {
		this.arguments = arguments;
	}
	public List<ASTBase> getChildren() {
		return children;
	}
	public void setChildren(List<ASTBase> children) {
		this.children = children;
	}
	
	@Override
	public String changeString(){
		StringBuffer sb = new StringBuffer();
		sb.append(name);
		for (ASTBase item : arguments.getChildren()) {
			sb.append(" ");
			sb.append(item.changeString());
		}
		return sb.toString();
	}
	@Override
	public String elementName(){
		return name;
	}
}
