package com.nnk.springboot.domain;

import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@NoArgsConstructor
@Generated
@Table(name = "rule_name")
public class RuleName {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

	@NotBlank(message= "Rule name is mandatory")
	private String name;

	@NotBlank(message = "Description is mandatory")
	private String description;

	private String json;

	private String template;

	@Column(name = "sql_str")
	private String sqlStr;

	@Column(name = "sql_part")
	private String sqlPart;

	public RuleName(String name, String description, String json, String template, String sqlStr, String sqlPart)
	{
		this.name = name;
		this.description = description;
		this.json = json;
		this.template =template;
		this.sqlStr = sqlStr;
		this.sqlPart = sqlPart;
	}
}
