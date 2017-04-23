package cn.ac.iscas.cloudeploy.v2.puppet.transform.ast;


public class ASTSelector extends ASTBlockExpression{
	private ASTBase param;
	private ASTBase values;
	
	public ASTBase getParam() {
		return param;
	}
	public void setParam(ASTBase param) {
		this.param = param;
	}
	public ASTBase getValues() {
		return values;
	}
	public void setValues(ASTBase values) {
		this.values = values;
	}
	@Override
	public String elementName(){
		return param.elementName();
	}
}
