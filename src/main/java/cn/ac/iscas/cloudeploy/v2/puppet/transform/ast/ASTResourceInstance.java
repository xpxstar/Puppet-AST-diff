package cn.ac.iscas.cloudeploy.v2.puppet.transform.ast;

import java.util.List;

public class ASTResourceInstance extends ASTBase{
	private ASTBase title;
	private ASTASTArray parameters;
	private List<ASTBase> children;
	public ASTBase getTitle() {
		return title;
	}
	public void setTitle(ASTBase title) {
		this.title = title;
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
