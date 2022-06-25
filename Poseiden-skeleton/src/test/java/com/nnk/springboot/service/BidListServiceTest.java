package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class BidListServiceTest {

	@MockBean
	BidListRepository bidListRepository;

	@Autowired
	BidListService bidListService;

	BidList bidList;

	@BeforeEach
	void setUp()
	{
		bidList = new BidList("Account Test","Type Test",10d);
	}

	@Test
	void findAllTest()
	{
		bidListService.findAll();

		verify(bidListRepository, Mockito.times(1)).findAll();
	}

	@Test
	void findByIdTest()
	{
		Mockito.when(bidListRepository.findById(1)).thenReturn(java.util.Optional.of(bidList));
		bidListService.findById(1);

		verify(bidListRepository, Mockito.times(1)).findById(1);
		assertEquals(bidList, bidListService.findById(1));
	}

	@Test
	void findByIdExceptionTest()
	{
		assertThrows(ObjectNotFoundException.class,()->bidListService.findById(1));
	}

	@Test
	void saveTest()
	{
		bidListService.save(bidList);

		verify(bidListRepository, Mockito.times(1)).save(bidList);
	}

	@Test
	void deleteTest()
	{
		Mockito.when(bidListRepository.findById(1)).thenReturn(java.util.Optional.of(bidList));
		bidListService.delete(bidList);

		verify(bidListRepository, Mockito.times(1)).delete(bidList);
	}
}
