package club.geek66.querydsl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * @author: orange
 * @date: 2020/12/3
 * @time: 下午12:03
 * @copyright: Copyright 2020 by orange
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String title;

	@OneToOne(cascade = CascadeType.PERSIST, mappedBy = "order")
	private User user;

}
