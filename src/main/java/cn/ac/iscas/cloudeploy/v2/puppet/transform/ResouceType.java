package cn.ac.iscas.cloudeploy.v2.puppet.transform;
import java.util.Map;

import cn.ac.iscas.cloudeploy.v2.puppet.transform.ast.ASTBase;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.ast.ASTBlockExpression;
import lombok.Getter;
import lombok.Setter;

public class ResouceType extends ASTBase{
	@Setter @Getter private String type;
	@Setter @Getter private String name;
	@Setter @Getter private String namespace;
	@Setter @Getter private Map<String,ASTBase> arguments;
	@Setter @Getter private Object argument_types;
	@Setter @Getter private ASTBase match;
	@Setter @Getter private String module_name;
	@Setter @Getter private ASTBlockExpression code;
	@Setter @Getter private String doc;
	@Setter @Getter private String parent;
	
/*	public List<String> diff(ASTBase obj){
		//do not check obj is null, since it must not null;
		// return null indicate not match type
		if (obj == null|| !(obj instanceof ResouceType)) {
			return null;
		}
		List<String> result = new LinkedList<>();
		ResouceType tobj = (ResouceType)obj;
		//diff arguments
		Set<String> visited = new HashSet<>();
		for (Map.Entry<String, ASTBase> arg: arguments.entrySet()) {
			ASTBase target  = tobj.getArguments().get(arg.getKey());
			List<String> tmps = arg.diff(target);
			for (String tmp:tmps) {
				result.add(type+"::argument::"+tmp);
			}if (null != target) {
				
				
			}else {
				result.add(type+"::argument::add::"+arg.getKey());
			}
			result.addAll(c)
		}
			StringBuffer sb = new StringBuffer();
			sb.add();
		}
		
		return null;
		
	}*/
}
