package cn.ac.iscas.cloudeploy.v2.puppet.transform.ast;

import java.util.List;

public class ASTResourceDefaults extends ASTBase{
	private String doc;
	private String type;
	private ASTASTArray parameters;
	private List<ASTBase> children;
	public String getDoc() {
		return doc;
	}
	public void setDoc(String doc) {
		this.doc = doc;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ASTASTArray getParameters() {
		return parameters;
	}
	public void setParameters(ASTASTArray parameters) {
		this.parameters = parameters;
	}
	public List<ASTBase> getChildren() {
		return children;
	}
	public void setChildren(List<ASTBase> children) {
		this.children = children;
	}
	
}
