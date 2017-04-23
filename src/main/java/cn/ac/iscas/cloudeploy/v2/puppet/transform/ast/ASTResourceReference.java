package cn.ac.iscas.cloudeploy.v2.puppet.transform.ast;

import java.util.List;

public class ASTResourceReference extends ASTBase{
	private String type;
	private ASTASTArray title;
	private List<ASTBase> children;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ASTASTArray getTitle() {
		return title;
	}
	public void setTitle(ASTASTArray title) {
		this.title = title;
	}
	public List<ASTBase> getChildren() {
		return children;
	}
	public void setChildren(List<ASTBase> children) {
		this.children = children;
	}
	/**change reference to string
	 * @param array
	 * @return
	 */
	@Override
	public String changeString(){
		StringBuffer sb = new StringBuffer();
		sb.append(this.type);
		for (ASTBase item : this.getTitle().getChildren()) {
			sb.append(" ");
			sb.append(item.changeString());
		}
		return sb.toString();
	}
}
