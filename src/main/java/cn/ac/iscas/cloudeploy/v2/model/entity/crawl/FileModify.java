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
public class FileModify {
	@Getter @Setter private String sha;
	@Getter @Setter private String filename;
	@Getter @Setter private int deletions;
	@Getter @Setter private int additions;
	@Getter @Setter private int changes;
	@Getter @Setter private String raw_url;
	@Getter @Setter private String patch;
}
