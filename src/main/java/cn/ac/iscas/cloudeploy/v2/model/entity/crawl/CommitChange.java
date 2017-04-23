package cn.ac.iscas.cloudeploy.v2.model.entity.crawl;

import java.util.List;

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
public class CommitChange {
	@Setter @Getter private String before;
	@Setter @Getter private String sha;
	@Setter @Getter private int changes;
	@Setter @Getter private int additions;
	@Setter @Getter private int deletions;
	@Setter @Getter private String url;
	@Setter @Getter private List<ChangeLine> changeLines;
}                   
