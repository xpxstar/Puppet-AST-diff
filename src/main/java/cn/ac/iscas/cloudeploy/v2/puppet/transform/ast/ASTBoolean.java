package cn.ac.iscas.cloudeploy.v2.puppet.transform.ast;

import lombok.Getter;
import lombok.Setter;

public class ASTBoolean extends ASTBase{
    @Setter @Getter protected boolean value;
    @Override
	public String changeString(){
		return String.valueOf(value);
	}
}
