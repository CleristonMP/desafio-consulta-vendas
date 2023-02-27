package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleSellerMinDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleSellerMinDTO> getReport(String minDate, String maxDate, String name, Pageable pageable) {
		LocalDate initialDate;
		LocalDate finalDate;
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());

		if (minDate.isEmpty()) {
			initialDate = today.minusYears(1L);
		} else {
			initialDate = LocalDate.parse(minDate);
		}

		if (maxDate.isEmpty()) {
			finalDate = today;
		} else {
			finalDate = LocalDate.parse(maxDate);
		}

		Page<Sale> result = repository.getReport(initialDate, finalDate, name, pageable);

		return result.map(x -> new SaleSellerMinDTO(x));
	}
}
