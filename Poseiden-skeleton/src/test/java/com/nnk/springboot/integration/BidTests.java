package com.nnk.springboot.integration;

import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@WithMockUser("kim")
public class BidTests {

	@Autowired
	private BidListRepository bidListRepository;

	@Autowired
	BidListController bidListController;

	BidList bid;
	BidList bidTest;

	@BeforeEach
	void setupTest()
	{
		bid = new BidList();
		bid.setAccount("Account Test");
		bid.setType("Type Test");
		bid.setBidQuantity(10d);

	}

	@Test
	void bidListSaveTest()
	{
		// Save
		String addBidForm = bidListController.addBidForm(bid);
		List<BidList> bidListTest = bidListRepository.findAll();

		bidTest = bidListTest.get(0);

		assertEquals("bidList/add", addBidForm);
		assertNotNull(bidListTest);
		assertEquals(1, bidListTest.size());
		assertEquals("Account Test", bidTest.getAccount());
		assertEquals("Type Test", bidTest.getType());
		assertEquals(20d, bidTest.getBidQuantity());
	}

	@Test
	void bidListUpdateTest()
	{
		// Update
		bid.setBidQuantity(20d);
		bidTest = bidListRepository.save(bid);

		assertEquals(20, bidTest.getBidQuantity());
	}

	@Test
	void bidListFindTest()
	{
		// Find
		List<BidList> listResult = bidListRepository.findAll();

		assertTrue(listResult.size() == 0);
	}

	@Test
	void bidListDeleteTest()
	{
		// Delete
		List<BidList> bidListTest = bidListRepository.findAll();
		bidTest = bidListTest.get(0);

		bidListRepository.delete(bidTest);
		List<BidList> testResult = bidListRepository.findAll();

		assertEquals(0, testResult.size());
		assertTrue(testResult.isEmpty());
	}
}
