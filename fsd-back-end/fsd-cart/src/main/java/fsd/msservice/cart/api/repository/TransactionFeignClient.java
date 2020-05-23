package fsd.msservice.cart.api.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import fsd.common.model.transaction.NewTransaction;

@FeignClient(name = "fsd-transaction")
public interface TransactionFeignClient {

	@PostMapping("/api/transaction")
	ResponseEntity<String> add(@RequestBody NewTransaction transaction);
}