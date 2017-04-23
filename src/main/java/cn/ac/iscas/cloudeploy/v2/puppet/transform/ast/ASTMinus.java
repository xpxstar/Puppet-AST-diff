package cn.ac.iscas.cloudeploy.v2.puppet.transform.ast;

public class ASTMinus extends ASTBlockExpression{
	private ASTBase value;

	public ASTBase getValue() {
		return value;
	}

	public void setValue(ASTBase value) {
		this.value = value;
	}
	
}
