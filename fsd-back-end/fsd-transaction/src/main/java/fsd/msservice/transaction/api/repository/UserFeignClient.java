package fsd.msservice.transaction.api.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fsd.model.user.Buyer;

@FeignClient(name = "fsd-user")
public interface UserFeignClient {

	@RequestMapping(value = "/api/buyer/{id}", method = RequestMethod.GET)
	public Buyer findBuyerById(@PathVariable("id") String id);
}