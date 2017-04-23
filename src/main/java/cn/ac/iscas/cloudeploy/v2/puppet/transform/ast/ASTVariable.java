package cn.ac.iscas.cloudeploy.v2.puppet.transform.ast;

import lombok.Getter;
import lombok.Setter;

public class ASTVariable extends ASTBase{
	@Getter @Setter protected String value;
	public String changeString(){
		return value;
	}
	@Override
	public String elementName(){
		return value;
	}
}
