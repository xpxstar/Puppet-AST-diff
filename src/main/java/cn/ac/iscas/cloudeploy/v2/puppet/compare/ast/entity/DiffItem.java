package cn.ac.iscas.cloudeploy.v2.puppet.compare.ast.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * @author admin
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DiffItem {
	@Setter @Getter private String type;
	@Setter @Getter private String value;
}
