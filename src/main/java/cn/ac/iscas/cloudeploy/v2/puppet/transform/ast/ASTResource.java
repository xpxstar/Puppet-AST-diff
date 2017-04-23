package cn.ac.iscas.cloudeploy.v2.puppet.transform.ast;

import java.util.List;

public class ASTResource extends ElementTop{
	private String type;
	private String doc;
	private String virtual;
	private ASTASTArray instances;
	private Object exported;
	private List<ASTBase> children;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDoc() {
		return doc;
	}
	public void setDoc(String doc) {
		this.doc = doc;
	}
	public ASTASTArray getInstances() {
		return instances;
	}
	public String getVirtual() {
		return virtual;
	}
	public void setVirtual(String virtual) {
		this.virtual = virtual;
	}
	public void setInstances(ASTASTArray instances) {
		this.instances = instances;
	}
	
	public Object getExported() {
		return exported;
	}
	public void setExported(Object exported) {
		this.exported = exported;
	}
	public List<ASTBase> getChildren() {
		return children;
	}
	public void setChildren(List<ASTBase> children) {
		this.children = children;
	}
}
