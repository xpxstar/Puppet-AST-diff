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
public class CommitSimple {
	@Getter @Setter private String sha;
	@Getter @Setter private String message;
	@Getter @Setter private FileModify[] files;
	@Getter @Setter private FileModify target;
}
