package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurvePointService {

	@Autowired
	CurvePointRepository curvePointRepository;

	public List<CurvePoint> findAll() {
		return curvePointRepository.findAll();
	}

	public CurvePoint findById(int id) {
		//Optional<CurvePoint> optional = curvePointRepository.findById(id);
		//return optional.isEmpty() ? null : optional.get();
		return curvePointRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("CurvePoint", "id"));
	}

	public void delete(CurvePoint object) {
		curvePointRepository.delete(object);
	}

	public CurvePoint save(CurvePoint object) {
		return curvePointRepository.save(object);
	}
}
