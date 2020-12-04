package club.geek66.querydsl;


import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author: orange
 * @date: 2020/12/3
 * @time: 上午9:52
 * @copyright: Copyright 2020 by orange
 */
@Data
@Builder
@RequiredArgsConstructor
public class PropertyFilter {

	private final String property;

	private final String operator;

	private final Object value;

}
