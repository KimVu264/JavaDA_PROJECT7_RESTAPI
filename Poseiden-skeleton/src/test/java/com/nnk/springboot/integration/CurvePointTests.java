package com.nnk.springboot.integration;

import com.nnk.springboot.config.UserDetailsServiceImpl;
import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class CurvePointTests {

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	CurveController curveController;

	@Autowired
	CurvePointRepository curvePointRepository;

	CurvePoint curvePoint;
	CurvePoint curveTest;

	@BeforeEach
	void setupTest()
	{
		curvePoint = new CurvePoint();
		curvePoint.setCurveId(10);
		curvePoint.setTerm(10d);
		curvePoint.setValue(30d);
	}

	@Test
	void curvePointSaveTest() {
		String addCurveForm = curveController.addBidForm(curvePoint);
		List<CurvePoint> curvePointList = curvePointRepository.findAll();

		curveTest = curvePointList.get(0);

		assertEquals("curvePoint/add", addCurveForm);
		assertNotNull(curvePointList);
		assertEquals(1, curvePointList.size());
		assertEquals(20, curveTest.getCurveId());
		assertEquals(10d, curveTest.getTerm());
		assertEquals(30d, curveTest.getValue());
	}

	@Test
	void curvePointUpdateTest() {

		// Update
		curvePoint.setCurveId(20);
		curvePoint = curvePointRepository.save(curvePoint);
		assertEquals(20,curvePoint.getCurveId());

	}
	@Test
	void curvePointFindTest() {
	// find
		List<CurvePoint> listResult = curvePointRepository.findAll();
		assertTrue(listResult.size() == 0);
	}

	@Test
	void curvePointDeleteTest() {

		// Delete
		curvePointRepository.save(curvePoint);
		List<CurvePoint> curveListTest = curvePointRepository.findAll();
		curveTest = curveListTest.get(0);

		curvePointRepository.delete(curveTest);
		List<CurvePoint> testResult = curvePointRepository.findAll();

		assertEquals(0, testResult.size());
		assertTrue(testResult.isEmpty());
	}

}
