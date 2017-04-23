package cn.ac.iscas.cloudeploy.v2.puppet.transform.ast;

import java.util.List;

public class ASTCollection extends ASTBase{
	private String doc;
	private String type;
	private String form;
	private ASTBase override;
	private ASTCollExpr query;
	private List<ASTBase> children;
	public String getDoc() {
		return doc;
	}
	public void setDoc(String doc) {
		this.doc = doc;
	}
	
	public ASTBase getOveride() {
		return override;
	}
	public void setOverride(ASTBase override) {
		this.override = override;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getForm() {
		return form;
	}
	public void setForm(String form) {
		this.form = form;
	}
	public List<ASTBase> getChildren() {
		return children;
	}
	public void setChildren(List<ASTBase> children) {
		this.children = children;
	}
	public ASTCollExpr getQuery() {
		return query;
	}
	public void setQuery(ASTCollExpr query) {
		this.query = query;
	}
	
}
