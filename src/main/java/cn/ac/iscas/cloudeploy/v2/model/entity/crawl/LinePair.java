package cn.ac.iscas.cloudeploy.v2.model.entity.crawl;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Builder
@ToString
@NoArgsConstructor
public class LinePair {
	@Setter @Getter private int start;
	@Setter @Getter private int end;
	/**
	 * @param start
	 * @param length notice that the length is not end, end = start +length
	 */
	public LinePair(int start, int length) {
		super();
		this.start = start;
		this.end = start+length;
	}
	
}
