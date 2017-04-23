package cn.ac.iscas.cloudeploy.v2.puppet.transform.ast;

import java.util.List;

public class ASTConcat extends ASTBase{
	private List<ASTBase> value;

	public List<ASTBase> getValue() {
		return value;
	}

	public void setValue(List<ASTBase> value) {
		this.value = value;
	}
	/**
	 * change concat to string
	 * @return
	 */
	@Override
	public String changeString(){
		StringBuffer sb = new StringBuffer();
		for (ASTBase child : this.getValue()) {
			if (child instanceof ASTVariable) {
				sb.append("${");
				sb.append(((ASTVariable) child).getValue());
				sb.append("}");
			}else if (child instanceof ASTString) {
				sb.append(((ASTString) child).getValue());
			}
		}
		return sb.toString();
	}
}
