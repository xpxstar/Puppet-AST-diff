package cn.ac.iscas.cloudeploy.v2.puppet.compare.ast.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Diff {
	/**
	 * 差别种类，如hostclass,function等等
	 */
	@Setter @Getter private String type;
	/**
	 * 差别的类型 如file,service,functionname等等
	 * 
	 */
	@Setter @Getter private String position;
	
	/**
	 * 差别名称 如argument，title，path，enable等等
	 */
	@Setter @Getter private String name;
	/**
	 * 差别的操作限于 add/delete/modify
	 */
	@Setter @Getter private String action;
	/**
	 * 差别的改变，如改变前为true，改变后为false
	 */
	@Setter @Getter private DiffDetail value;
	
	public String geneFeature(){
		StringBuffer sb = new StringBuffer();
		sb.append(type);
		sb.append('-');
		sb.append(position);
		sb.append('-');
		sb.append(name.replaceAll(" ", ""));
		sb.append('-');
		sb.append(action);
		return sb.toString();
	}
	public String geneDetail(){
		StringBuffer sb = new StringBuffer();
		sb.append(type);
		sb.append('-');
		sb.append(position);
		sb.append('-');
		sb.append(name.replaceAll(" ", ""));
		sb.append('-');
		sb.append(action);
		sb.append('-');
		sb.append(value.getBefore().replaceAll(" ", ""));
		sb.append('^');
		sb.append(value.getAfter().replaceAll(" ", ""));
		return sb.toString();
	}
}
