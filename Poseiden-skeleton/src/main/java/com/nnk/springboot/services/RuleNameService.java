package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuleNameService {

	@Autowired
	RuleNameRepository ruleNameRepository;

	public List<RuleName> findAll() {
		return ruleNameRepository.findAll();
	}

	public RuleName findById(int id) {
		//Optional<RuleName> optional = ruleNameRepository.findById(id);
		//return optional.isEmpty() ? null : optional.get();
		return ruleNameRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("RuleName", "id"));
	}

	public void delete(RuleName object) {
		ruleNameRepository.delete(object);
	}

	public RuleName save(RuleName object) {
		return ruleNameRepository.save(object);
	}
}
