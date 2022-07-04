package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeService {

	@Autowired
	TradeRepository tradeRepository;

	public List<Trade> findAll() {
		return tradeRepository.findAll();
	}

	public Trade findById(int id) {
		return tradeRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("Trade", "id"));
	}

	public void delete(Trade object) {
		tradeRepository.delete(object);
	}

	public Trade save(Trade object) {
		return tradeRepository.save(object);
	}

}
