package cn.ac.iscas.cloudeploy.v2.puppet.transform.ast;

import java.util.List;

public class ASTASTArray extends ASTBase{
    private List<ASTBase> children;
	
	public List<ASTBase> getChildren() {
		return children;
	}
	public void setChildren(List<ASTBase> children) {
		this.children = children;
	}
	/**change array to string
	* @return
	*/
	@Override
	public String changeString(){
		StringBuffer sb = new StringBuffer();
		for (ASTBase item : this.getChildren()) {
			sb.append(item.changeString());
			sb.append(" ");
		}
		return sb.toString();
	}
}
