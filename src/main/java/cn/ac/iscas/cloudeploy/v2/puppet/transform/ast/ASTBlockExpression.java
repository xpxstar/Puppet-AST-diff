package cn.ac.iscas.cloudeploy.v2.puppet.transform.ast;

import java.util.List;

public class ASTBlockExpression extends ElementTop{
	private List<ASTBase> children;

	public List<ASTBase> getChildren() {
		return children;
	}

	public void setChildren(List<ASTBase> children) {
		this.children = children;
	}
}
