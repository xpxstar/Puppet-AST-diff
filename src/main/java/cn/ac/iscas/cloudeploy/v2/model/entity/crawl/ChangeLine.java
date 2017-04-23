package cn.ac.iscas.cloudeploy.v2.model.entity.crawl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChangeLine {
	@Setter @Getter private LinePair before;
	@Setter @Getter private LinePair after;
}
