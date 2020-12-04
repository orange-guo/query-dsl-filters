package club.geek66.querydsl;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * @author: orange
 * @date: 2020/12/3
 * @time: 上午9:36
 * @copyright: Copyright 2020 by orange
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	@Enumerated(EnumType.STRING)
	private Country country;

	private Long money;

	@OneToOne(cascade = CascadeType.ALL)
	private Orders order;

}
