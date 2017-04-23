package cn.ac.iscas.cloudeploy.v2.puppet.transform.ast;

public class ASTCaseStatement extends ASTBlockExpression{
	private String doc;
	private ASTBase test;
	private ASTASTArray options;
	
	public String getDoc() {
		return doc;
	}
	public void setDoc(String doc) {
		this.doc = doc;
	}
	public ASTBase getTest() {
		return test;
	}
	public void setTest(ASTBase test) {
		this.test = test;
	}
	public ASTASTArray getOptions() {
		return options;
	}
	public void setOptions(ASTASTArray options) {
		this.options = options;
	}
	@Override
	public String elementName(){
		return test.elementName();
	}
}
