package cn.ac.iscas.cloudeploy.v2.model.entity.crawl;

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
public class CommitBase {
	@Getter @Setter private String url;
}
