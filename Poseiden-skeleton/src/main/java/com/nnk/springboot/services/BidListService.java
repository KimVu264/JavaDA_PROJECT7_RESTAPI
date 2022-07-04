package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidListService {

	@Autowired
	BidListRepository bidListRepository;

	public List<BidList> findAll() {
		return bidListRepository.findAll();
	}

	public BidList findById(int id) {
		return bidListRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("BidList", "id"));
	}

	public void delete(BidList object) {
		bidListRepository.delete(object);
	}

	public BidList save(BidList object) {
		return bidListRepository.save(object);
	}

}
