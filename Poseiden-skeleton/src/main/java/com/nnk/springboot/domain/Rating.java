package com.nnk.springboot.domain;

import lombok.Data;
import lombok.Generated;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@Generated
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

	@NotBlank(message = "Moodys rating is mandatory")
	@Column(name = "moodys_rating")
	private String moodysRating;

	@NotBlank(message = "SandP rating is mandatory")
	@Column(name = "sand_p_rating")
	private String sandPRating;

	@NotBlank(message = "Fitch rating is mandatory")
	@Column(name = "fitch_rating")
	private String fitchRating;

	@NotBlank(message = "Order number is mandatory")
	@Column(name = "order_number")
	private Double orderNumber;

	public Rating(String moodys_rating, String sand_pRating, String fitch_rating, Double order_number) {
		this.moodysRating = moodys_rating;
		this.sandPRating = sand_pRating;
		this.fitchRating = fitch_rating;
		this.orderNumber = order_number;
	}

	public Rating() {

	}

	public Rating(double orderNumber) {
		this.orderNumber = orderNumber;
	}
}
