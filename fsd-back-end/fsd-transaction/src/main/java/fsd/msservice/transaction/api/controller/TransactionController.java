package fsd.msservice.transaction.api.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fsd.msservice.transaction.api.domain.SuggestCarousel;
import fsd.msservice.transaction.api.service.SuggestCarouselService;

@RestController
@RequestMapping(value = "/api/transaction", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionController {

	@Autowired
	private SuggestCarouselService service;

	/**
	 * GET transaction information under one buyer
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	@GetMapping("buyer/{id}")
	public List<SuggestCarousel> findAllWithinShowPeriod(@RequestParam("start") String start,
			@RequestParam("end") String end) {
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			format.parse(start);
			format.parse(end);
			return service.findAllWithinShowPeriod(start, end);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Parameter not valid.");
		}
	}

	/**
	 * Create new transaction for one buyer
	 * 
	 * @param carousel
	 * @return
	 */
	@PostMapping("buyer")
	public SuggestCarousel add(@RequestBody SuggestCarousel carousel) {
		return service.add(carousel);
	}

	/**
	 * Update carousel information
	 * 
	 * @param carousel
	 * @return
	 */
	@PutMapping
	public SuggestCarousel update(@RequestBody SuggestCarousel carousel) {
		return service.update(carousel);
	}

	/**
	 * Delete carousel
	 * 
	 * @param carousel
	 */
	@DeleteMapping()
	public void delete(@RequestBody SuggestCarousel carousel) {
		service.delete(carousel);
	}

}