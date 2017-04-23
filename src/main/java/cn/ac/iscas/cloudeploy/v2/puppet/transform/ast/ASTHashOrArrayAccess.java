package cn.ac.iscas.cloudeploy.v2.puppet.transform.ast;

public class ASTHashOrArrayAccess extends ASTBase{
	private Object variable;
	private ASTBase key;
	public Object getVariable() {
		return variable;
	}
	public void setVariable(Object variable) {
		this.variable = variable;
	}
	public ASTBase getKey() {
		return key;
	}
	public void setKey(ASTBase key) {
		this.key = key;
	}
	public String changeString(){
		StringBuffer sb = new StringBuffer();
		if(variable instanceof String){
			sb.append((String)variable);
			
		}
		return sb.toString();
	}
}
