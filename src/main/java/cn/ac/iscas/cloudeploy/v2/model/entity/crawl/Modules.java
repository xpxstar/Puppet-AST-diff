package cn.ac.iscas.cloudeploy.v2.model.entity.crawl;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
public class Modules {
	@Getter @Setter private String name;
	@Getter @Setter private List<String> fileList;
}
