package com.nnk.springboot.domain;

import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@Generated
@Table(name = "curve_point")
public class CurvePoint {
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

	public CurvePoint(Integer curveId, Double term, Double value)
	{
		this.curveId = curveId;
		this.term = term;
		this.value = value;
	}


}
