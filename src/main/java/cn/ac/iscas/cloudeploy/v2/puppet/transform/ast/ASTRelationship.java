package cn.ac.iscas.cloudeploy.v2.puppet.transform.ast;

import java.util.List;

public class ASTRelationship extends ElementTop{
	private List<ASTBase> children;
	private ASTBase left;
	private ASTBase right;
	private String arrow;
	public List<ASTBase> getChildren() {
		return children;
	}
	public void setChildren(List<ASTBase> children) {
		this.children = children;
	}
	public ASTBase getLeft() {
		return left;
	}
	public void setLeft(ASTBase left) {
		this.left = left;
	}
	public ASTBase getRight() {
		return right;
	}
	public void setRight(ASTBase right) {
		this.right = right;
	}
	public String getArrow() {
		return arrow;
	}
	public void setArrow(String arrow) {
		this.arrow = arrow;
	}
	@Override
	public String changeString(){
		StringBuffer sb = new StringBuffer();
		sb.append(left.changeString());
		sb.append(arrow);
		sb.append(right.changeString());
		return sb.toString();
	}
}
