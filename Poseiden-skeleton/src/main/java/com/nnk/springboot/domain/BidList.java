package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bid_list")
public class BidList {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer BidListId;

	@NonNull
	@NotBlank(message = "Account is mandatory")
	private String account;

	@NotBlank(message = "Type is mandatory")
	private String type;

	@Positive(message = "Bid Quantity must be greater than zero")
	@Column(name = "bid_quantity")
	private Double bidQuantity;

	@Column(name = "ask_quantity")
	private Double askQuantity;

	private Double bid;

	private Double ask;

	private String benchmark;

	@Column(name = "bid_list_date")
	private Timestamp bidListDate;

	private String commentary;

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

	public BidList(String account_test, String type_test, double v) {
	}
}
