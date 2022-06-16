package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rulename")
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

	public RuleName(String rule_name, String description, String json, String template, String sql, String sql_part) {
	}
}
