package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "trade")
public class Trade {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "trade_id")
    private Integer tradeId;

	@NotBlank(message= "Account is mandatory")
	private String account;

	@NotBlank(message= "Type is mandatory")
	private String type;

	@NotBlank(message= "Buy quantity is mandatory")
	@Column(name = "buy_quantity")
	private Double buyQuantity;

	@Column(name = "sell_quantity")
	private Double sellQuantity;

	@Column(name = "buy_price")
	private Double buyPrice;

	@Column(name = "sell_price")
	private Double sellPrice;

	private String benchmark;

	@Column(name = "trade_date")
	private Timestamp tradeDate;

	private String security;

	private String status;

	private String trader;

	private String book;

	@Column(name = "creation_name")
	private String creationName;

	@CreationTimestamp
	@Column(name = "creation_date", nullable = false, updatable = false)
	private Timestamp creationDate;

	@Column(name = "revision_name")
	private String revisionName;

	@Column(name = "revision_date")
	private Timestamp revisionDate;

	@Column(name = "deal_name")
	private String dealName;

	@Column(name = "deal_type")
	private String dealType;

	@Column(name = "source_list_id")
	private String sourceListId;

	private String side;

	public Trade(String trade_account, String type) {
	}
}
