package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

	@Autowired
	RatingRepository ratingRepository;

	public List<Rating> findAll() {
		return ratingRepository.findAll();
	}

	public Rating findById(int id) {
		return ratingRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("Rating", "id"));
	}

	public void delete(Rating object) {
		ratingRepository.delete(object);
	}

	public Rating save(Rating object) {
		return ratingRepository.save(object);
	}
}
