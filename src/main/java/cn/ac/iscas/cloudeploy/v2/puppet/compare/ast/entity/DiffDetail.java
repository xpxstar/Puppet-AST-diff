package cn.ac.iscas.cloudeploy.v2.puppet.compare.ast.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 差别具体的改变，
 * @attribute 
 * 	type： 值的类型，before： 改变前的值 after 改变后的值
 * @discription 如果某一项是null 表明 是add或 delete操作
 * @author admin
 *
 */
@Builder
@AllArgsConstructor
@ToString
public class DiffDetail {
	@Setter @Getter String type;
	@Setter @Getter String before;
	@Setter @Getter String after;
}
