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
public class ASTType extends ASTBase{
	@Getter @Setter protected String value;
	
	public ASTType() {
		super();
	}

	public ASTType(String value) {
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
