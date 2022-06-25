package com.nnk.springboot.integration;

import com.nnk.springboot.config.UserDetailsServiceImpl;
import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@WithMockUser(username = "kim")
public class CurvePointTests {

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	CurveController curveController;

	@Autowired
	CurvePointRepository curvePointRepository;

	CurvePoint curvePoint;
	CurvePoint curveToTest;

	@BeforeEach
	void setupTest()
	{
		curvePoint = new CurvePoint();
		curvePoint.setCurveId(10);
		curvePoint.setTerm(10d);
		curvePoint.setValue(30d);
	}
	@Test
	void curvePointFindTest()
	{
		// Find
		List<CurvePoint> listResult = curvePointRepository.findAll();

		assertTrue(listResult.size() == 0);
	}

	@Test
	void curvePointSaveTest()
	{
		// Save
		String addCurveForm = curveController.addBidForm(curvePoint);
		List<CurvePoint> curvePointList = curvePointRepository.findAll();

		curveToTest = curvePointList.get(0);

		assertEquals("curvePoint/add", addCurveForm);
		assertNotNull(curvePointList);
		assertEquals(1, curvePointList.size());
		assertEquals(20, curveToTest.getCurveId());
		assertEquals(10d, curveToTest.getTerm());
		assertEquals(30d, curveToTest.getValue());
	}

	@Test
	void curvePointUpdateTest()
	{
		// Update
		curvePoint.setCurveId(20);
		curveToTest = curvePointRepository.save(curvePoint);

		assertEquals(20, curvePoint.getCurveId());
	}

	@Test
	void curvePointDeleteTest()
	{
		// Delete
		curvePointRepository.save(curvePoint);
		List<CurvePoint> curveListTest = curvePointRepository.findAll();
		curveToTest = curveListTest.get(0);

		curvePointRepository.delete(curveToTest);
		List<CurvePoint> testResult = curvePointRepository.findAll();

		assertEquals(0, testResult.size());
		assertTrue(testResult.isEmpty());
	}

}
