package com.nnk.springboot.controller;

import com.nnk.springboot.config.UserDetailsServiceImpl;
import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import com.nnk.springboot.validator.BidListValidator;
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

@WebMvcTest(BidListController.class)
@ExtendWith(SpringExtension.class)
@WithMockUser(username="kim")
public class BidListControllerTest {

	@MockBean
	private BidListService bidListService;

	@MockBean
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	BidListValidator bidListValidator;

	BidList bidList;
	List<BidList> findAll;

	@BeforeEach
	void setupTest()
	{
		bidList = new BidList("account1","type1",10d);
		findAll = new ArrayList<>(Arrays.asList(new BidList("account1","account1",10d),
				new BidList("account2","type2",20d)));
	}

	@Test
	void validateTest() throws Exception
	{
		Mockito.when(bidListService.save(bidList)).thenReturn(bidList);

		mockMvc.perform(post("/bidList/validate")
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.content(String.valueOf(bidList))
						.with(csrf()))
						.andExpect(redirectedUrl("/bidList/list"));
	}

	@Test
	void homeTest() throws Exception
	{
		Mockito.when(bidListService.findAll()).thenReturn(findAll);

		mockMvc.perform(get("/bidList/list"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("bidLists"))
				.andExpect(MockMvcResultMatchers.model().attribute("bidLists", findAll))
				.andExpect(view().name("bidList/list"));
	}

	@Test
	void addBidFormTest() throws Exception
	{
		mockMvc.perform(get("/bidList/add"))
				.andExpect(status().isOk())
				.andExpect(view().name("bidList/add"));
	}

	@Test
	void showUpdateFormTest() throws Exception
	{
		Mockito.when(bidListService.findById(1)).thenReturn(bidList);

		mockMvc.perform(get("/bidList/update/1"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("bidList"))
				.andExpect(MockMvcResultMatchers.model().attribute("bidList", bidList))
				.andExpect(view().name("bidList/update"));
	}

	@Test
	void updateBidTest() throws Exception
	{
		Mockito.when(bidListService.save(bidList)).thenReturn(bidList);

		mockMvc.perform(post("/bidList/update/1")
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.content(String.valueOf(bidList))
						.with(csrf()))
				.andExpect(redirectedUrl("/bidList/list"));
	}

	@Test
	void deleteBidListTest() throws Exception
	{
		Mockito.when(bidListService.findById(1)).thenReturn(bidList);
		Mockito.doNothing().when(bidListService).delete(bidList);
		mockMvc.perform(get("/bidList/delete/1"))
				.andExpect(redirectedUrl("/bidList/list"));

		verify(bidListService,Mockito.times(1)).delete(bidList);
	}
}
