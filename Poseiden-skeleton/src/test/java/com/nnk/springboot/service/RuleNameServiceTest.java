package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.RuleNameService;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class RuleNameServiceTest {

	@Autowired
	RuleNameService ruleNameService;

	@MockBean
	RuleNameRepository ruleNameRepository;

	RuleName ruleName;

	@BeforeEach
	void setUp()
	{
		ruleName = new RuleName("nameTest","descriptionTest", "jsonTest", "templateTest", "sqlTest", "sql-partTest");
	}

	@Test
	void findAllTest()
	{
		ruleNameService.findAll();

		verify(ruleNameRepository, Mockito.times(1)).findAll();
	}

	@Test
	void findByIdTest()
	{
		Mockito.when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));
		ruleNameService.findById(1);
		//Then
		verify(ruleNameRepository, Mockito.times(1)).findById(1);
		assertEquals(ruleName, ruleNameService.findById(1));
	}

	@Test
	void findByIdExceptionTest()
	{
		assertThrows(ObjectNotFoundException.class,()->ruleNameService.findById(1));
	}

	@Test
	void saveTest()
	{
		ruleNameService.save(ruleName);

		verify(ruleNameRepository, Mockito.times(1)).save(ruleName);
	}

	@Test
	void deleteTest()
	{
		Mockito.when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));
		ruleNameService.delete(ruleName);

		verify(ruleNameRepository, Mockito.times(1)).delete(ruleName);
	}
}
