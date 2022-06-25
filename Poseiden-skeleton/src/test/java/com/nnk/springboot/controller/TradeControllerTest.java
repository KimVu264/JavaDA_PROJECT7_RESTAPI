package com.nnk.springboot.controller;


import com.nnk.springboot.config.UserDetailsServiceImpl;
import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.BidListService;
import com.nnk.springboot.services.TradeService;
import com.nnk.springboot.validator.BidListValidator;
import com.nnk.springboot.validator.TradeValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@WebMvcTest(TradeController.class)
@ExtendWith(SpringExtension.class)
@WithMockUser(username="kim")
public class TradeControllerTest {

	@MockBean
	private TradeService tradeService;

	@MockBean
	UserDetailsServiceImpl userDetailsService;

	@MockBean
	TradeValidator tradeValidator;

	@Autowired
	private MockMvc mockMvc;

	Trade trade;
	List<Trade> findAll;

	@BeforeEach
	void setupTest()
	{
		trade = new Trade("accountTest", "typeTest", 100d);
		findAll = new ArrayList<>(Arrays.asList(new Trade("account1Test", "type1Test", 100d),
				new Trade("account2Test", "type2Test", 200d)));
	}

	@Test
	void homeTest() throws Exception
	{
		Mockito.when(tradeService.findAll()).thenReturn(findAll);

		mockMvc.perform(get("/trade/list"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("trades"))
				.andExpect(MockMvcResultMatchers.model().attribute("trades", findAll))
				.andExpect(view().name("trade/list"));
	}

	@Test
	void addBidFormTest() throws Exception
	{
		mockMvc.perform(get("/trade/add"))
				.andExpect(status().isOk())
				.andExpect(view().name("trade/add"));
	}

	@Test
	void validateTest() throws Exception
	{
		Mockito.when(tradeService.save(trade)).thenReturn(trade);
		//Then
		mockMvc.perform(post("/trade/validate")
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.content(String.valueOf(trade))
						.with(csrf()))
				.andExpect(redirectedUrl("/trade/list"));
	}

	@Test
	void showUpdateFormTest() throws Exception
	{
		Mockito.when(tradeService.findById(1)).thenReturn(trade);

		mockMvc.perform(get("/trade/update/1"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("trade"))
				.andExpect(MockMvcResultMatchers.model().attribute("trade", trade))
				.andExpect(view().name("trade/update"));
	}

	@Test
	void updateTradeTest() throws Exception
	{
		Mockito.when(tradeService.save(trade)).thenReturn(trade);

		mockMvc.perform(post("/trade/update/1")
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.content(String.valueOf(trade))
						.with(csrf()))
				.andExpect(redirectedUrl("/trade/list"));
	}

	@Test
	void deleteTradeTest() throws Exception
	{
		Mockito.when(tradeService.findById(1)).thenReturn(trade);
		Mockito.doNothing().when(tradeService).delete(trade);

		mockMvc.perform(get("/trade/delete/1")).andExpect(redirectedUrl("/trade/list"));
		verify(tradeService,Mockito.times(1)).delete(trade);
	}
}
