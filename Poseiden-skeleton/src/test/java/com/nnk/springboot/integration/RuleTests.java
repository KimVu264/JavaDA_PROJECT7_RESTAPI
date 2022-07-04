package com.nnk.springboot.integration;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class RuleTests {
	@Autowired
	private RuleNameRepository ruleNameRepository;

	private RuleName rule;

	@BeforeEach
	void setupTest()
	{
		rule = new RuleName();
		rule.setId(1);
		rule.setName("Rule Name");
		rule.setDescription("Desc test");
		rule.setJson("Json test");
		rule.setTemplate("Template test");
		rule.setSqlStr("SQL test");
		rule.setSqlPart("SQL Part test");
	}

	@Test
	public void ruleSaveTest() {

		// Save
		rule = ruleNameRepository.save(rule);
		Assert.assertNotNull(rule.getId());
		Assert.assertTrue(rule.getName().equals("Rule Name"));
	}

	@Test
	public void ruleUpdateTest() {

		// Update
		rule.setName("Rule Name Update");
		rule = ruleNameRepository.save(rule);

		assertEquals("Rule Name Update", rule.getName());
	}

	@Test
	public void ruleFindTest() {

		// Find
		List<RuleName> listResult = ruleNameRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);
	}

	@Test
	public void ruleDeleteTest() {

		// Delete
		Integer id = rule.getId();
		ruleNameRepository.delete(rule);
		Optional<RuleName> ruleList = ruleNameRepository.findById(id);
		Assert.assertFalse(ruleList.isPresent());
	}
}
