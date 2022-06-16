package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "curvepoint")
public class CurvePoint {
    // TODO: Map columns in data table CURVEPOINT with corresponding java fields
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

	@Column(name = "curve_id")
	private Integer curveId;

	@Column(name = "as_of_date")
	private Timestamp asOfDate;

	private Double term;

	private Double value;

	@CreationTimestamp
	@Column(name = "creation_date", nullable = false, updatable = false)
	private Timestamp creationDate;

	public CurvePoint(int i, double v, double v1) {
	}
}
