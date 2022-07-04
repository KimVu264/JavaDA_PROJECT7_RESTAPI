package com.nnk.springboot.integration;

import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.Assert;
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
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WithMockUser(username = "kim")
public class RatingTests {

	@Autowired
	private RatingController ratingController;

	@Autowired
	private RatingRepository ratingRepository;

	Rating rating;
	Rating ratingTest;

	@BeforeEach
	void setupTest() {
		rating = new Rating();
		rating.setMoodysRating("MoodysRatingTest");
		rating.setSandPRating("SandPratingTest");
		rating.setFitchRating("FitchRatingTest");
		rating.setOrderNumber(10d);
		ratingRepository.save(rating);
	}

	@Test
	public void ratingSaveTest() {
		// Save
		String addRatingForm = ratingController.addRatingForm(rating);
		List<Rating> ratingListTest = ratingRepository.findAll();

		ratingTest = ratingListTest.get(0);

		assertEquals("rating/add",addRatingForm);
		assertNotNull(ratingListTest);
		assertTrue(ratingListTest.size()==1);
		assertEquals("MoodysRatingTest", rating.getMoodysRating());
		assertEquals("FitchRatingTest", rating.getFitchRating());
		assertEquals("SandPratingTest", rating.getSandPRating());
		assertEquals(10, rating.getOrderNumber());
	}

	@Test
	public void ratingUpdateTest() {
		// Update
		rating.setOrderNumber(20d);
		rating = ratingRepository.save(rating);

		assertEquals(20, rating.getOrderNumber());
	}

	@Test
	public void ratingFindTest() {
		// Find
		List<Rating> listResult = ratingRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);
	}

	@Test
	public void ratingDeleteTest() {
		// Delete
		List<Rating> ratingList = ratingRepository.findAll();
		ratingTest = ratingList.get(0);

		ratingRepository.delete(ratingTest);
		List<Rating> testResult = ratingRepository.findAll();

		assertEquals(1, testResult.size());
		assertFalse(testResult.isEmpty());
	}
}
