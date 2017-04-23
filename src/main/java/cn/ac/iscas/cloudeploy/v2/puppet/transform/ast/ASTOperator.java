package cn.ac.iscas.cloudeploy.v2.puppet.transform.ast;

import java.util.List;

public class ASTOperator extends ASTBase{
	private String operator;
	private ASTBase lval;
	private ASTBase rval;
	private List<ASTBase> children;
	
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public ASTBase getLval() {
		return lval;
	}
	public void setLval(ASTBase lval) {
		this.lval = lval;
	}
	public ASTBase getRval() {
		return rval;
	}
	public void setRval(ASTBase rval) {
		this.rval = rval;
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
		sb.append(lval.changeString());
		sb.append(" "+operator+" ");
		sb.append(rval.changeString());
		return sb.toString();
	}
}
