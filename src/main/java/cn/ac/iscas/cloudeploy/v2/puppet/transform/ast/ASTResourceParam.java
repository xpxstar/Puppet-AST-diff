package cn.ac.iscas.cloudeploy.v2.puppet.transform.ast;

public class ASTResourceParam extends ASTBlockExpression{
	private Object param;
	private ASTBase value;
	private String add;

	public Object getParam() {
		return param;
	}
	public void setParam(Object param) {
		this.param = param;
	}
	public ASTBase getValue() {
		return value;
	}
	public void setValue(ASTBase value) {
		this.value = value;
	}
	
	public String getAdd() {
		return add;
	}
	public void setAdd(String add) {
		this.add = add;
	}
	public String paramString(){
		if (param instanceof String) {
			return (String)param; 
		}else
			return ((ASTBase) param).changeString();
	}
}
