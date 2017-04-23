package cn.ac.iscas.cloudeploy.v2.model.entity.crawl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CProcess {
	@Setter @Getter private int module;
	@Setter @Getter private int file;
	@Setter @Getter private int commit;
}
