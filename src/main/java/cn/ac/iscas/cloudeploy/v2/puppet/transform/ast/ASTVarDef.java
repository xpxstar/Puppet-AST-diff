package cn.ac.iscas.cloudeploy.v2.puppet.transform.ast;

import java.util.List;

public class ASTVarDef extends ASTBase{
	private String doc;
	private ASTBase name;
	private ASTBase value;
	private List<ASTBase> children;
	
	public String getDoc() {
		return doc;
	}
	public void setDoc(String doc) {
		this.doc = doc;
	}
	public ASTBase getName() {
		return name;
	}
	public void setName(ASTBase name) {
		this.name = name;
	}
	public ASTBase getValue() {
		return value;
	}
	public void setValue(ASTBase value) {
		this.value = value;
	}
	public List<ASTBase> getChildren() {
		return children;
	}
	public void setChildren(List<ASTBase> children) {
		this.children = children;
	}
	@Override
	public String elementName(){
		return name.elementName();
	}
}
