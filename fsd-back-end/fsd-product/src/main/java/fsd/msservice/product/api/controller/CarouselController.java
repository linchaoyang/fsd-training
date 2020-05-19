package fsd.msservice.product.api.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fsd.msservice.product.api.model.SuggestCarouselVO;
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
	public List<SuggestCarouselVO> findAllWithinShowPeriod(@RequestParam("start") String start,
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
	 * Add new carousel
	 * 
	 * @param carousel
	 * @return
	 */
	@PostMapping
	public void add(@RequestBody SuggestCarouselVO carousel) {
		service.add(carousel);
	}

	/**
	 * Update carousel information
	 * 
	 * @param carousel
	 * @return
	 */
	@PutMapping
	public void update(@RequestBody SuggestCarouselVO carousel) {
		service.update(carousel);
	}

	/**
	 * Delete carousel
	 * 
	 * @param carousel
	 */
	@DeleteMapping("/{id}")
	public void delete(@PathVariable String id) {
		service.delete(id);
	}

}