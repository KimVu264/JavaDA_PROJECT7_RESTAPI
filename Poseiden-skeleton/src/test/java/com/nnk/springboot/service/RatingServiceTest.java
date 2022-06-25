package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;
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
public class RatingServiceTest {

	@MockBean
	RatingRepository ratingRepository;

	@Autowired
	RatingService ratingService;

	Rating rating;

	@BeforeEach
	void setUp()
	{
		rating = new Rating("moodysRatingTest","SandPRatingTEst","fitchRatingTest",10);
	}

	@Test
	void findAllTest()
	{
		ratingService.findAll();

		verify(ratingRepository, Mockito.times(1)).findAll();
	}

	@Test
	void findByIdTest()
	{
		Mockito.when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));
		ratingService.findById(1);

		verify(ratingRepository, Mockito.times(1)).findById(1);
		assertEquals(rating, ratingService.findById(1));
	}

	@Test
	void findByIdExceptionTest()
	{
		assertThrows(ObjectNotFoundException.class,()->ratingService.findById(1));
	}

	@Test
	void saveTest()
	{
		ratingService.save(rating);

		verify(ratingRepository, Mockito.times(1)).save(rating);
	}

	@Test
	void deleteTest()
	{
		Mockito.when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));
		ratingService.delete(rating);

		verify(ratingRepository, Mockito.times(1)).delete(rating);
	}
}
