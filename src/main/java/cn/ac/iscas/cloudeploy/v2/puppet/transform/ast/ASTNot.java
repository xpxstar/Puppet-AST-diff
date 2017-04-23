package cn.ac.iscas.cloudeploy.v2.puppet.transform.ast;

import java.util.List;

public class ASTNot extends ElementTop{
	private ASTBase value;
	private List<ASTBase> children;

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
}
