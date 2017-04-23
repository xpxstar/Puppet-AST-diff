package cn.ac.iscas.cloudeploy.v2.puppet.transform.ast;

import lombok.Getter;
import lombok.Setter;

public class ASTName extends ASTBase{
	@Getter @Setter protected String value;
	@Override
	public String changeString(){
		return value;
	}
	@Override
	public String elementName(){
		return value;
	}
}
