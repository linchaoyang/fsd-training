package fsd.msservice.product.api.controller;

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

import fsd.msservice.product.api.domain.SuggestCarousel;
import fsd.msservice.product.api.service.SuggestCarouselService;

@RestController
@RequestMapping(value = "/api/carousel", produces = MediaType.APPLICATION_JSON_VALUE)
public class CarouselController {

	@Autowired
	private SuggestCarouselService service;

	/**
	 * Find carousels between start and end date
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	@GetMapping("/")
	public List<SuggestCarousel> findAllWithinShowPeriod(@RequestParam("start") String start,
			@RequestParam("end") String end) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date startDate;
		try {
			startDate = format.parse(start);
			Date endDate = format.parse(start);
			return service.findAllWithinShowPeriod(startDate, endDate);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * Add new carousel
	 * 
	 * @param carousel
	 * @return
	 */
	@PostMapping
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