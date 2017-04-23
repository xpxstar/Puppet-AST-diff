package cn.ac.iscas.cloudeploy.v2.puppet.transform.ast;

import lombok.Getter;
import lombok.Setter;


/**
 * @author admin
 * @ASTVariable
 * @ASTUndef
 * @ASTName
 *
 */
public class ASTString extends ASTBase{
	@Getter @Setter protected String value;
	
	public ASTString() {
		super();
	}

	public ASTString(String value) {
		super();
		this.value = value;
	}

	@Override
	public String changeString(){
		return value;
	}
	@Override
	public String elementName(){
		return value;
	}
}
