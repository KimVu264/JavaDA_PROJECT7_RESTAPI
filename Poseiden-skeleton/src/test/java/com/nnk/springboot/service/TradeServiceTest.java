package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeService;
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
public class TradeServiceTest {
	@MockBean
	TradeRepository tradeRepository;

	@Autowired
	TradeService tradeService;

	Trade trade;

	@BeforeEach
	void setUp()
	{
		trade = new Trade("AccountTest", "TypeTEst", 10d);
	}

	@Test
	void findAllTest()
	{
		tradeService.findAll();

		verify(tradeRepository, Mockito.times(1)).findAll();
	}

	@Test
	void findByIdTest()
	{
		Mockito.when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));
		tradeService.findById(1);

		verify(tradeRepository, Mockito.times(1)).findById(1);
		assertEquals(trade, tradeService.findById(1));
	}

	@Test
	void findByIdExceptionTest()
	{
		assertThrows(ObjectNotFoundException.class,()->tradeService.findById(1));
	}

	@Test
	void saveTest()
	{
		tradeService.save(trade);

		verify(tradeRepository, Mockito.times(1)).save(trade);
	}

	@Test
	void deleteTest()
	{
		Mockito.when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));
		tradeService.delete(trade);

		verify(tradeRepository, Mockito.times(1)).delete(trade);
	}
}
