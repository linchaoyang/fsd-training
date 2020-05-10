package fsd.msservice.auth.api.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fsd.model.user.Buyer;
import fsd.model.user.Seller;

@FeignClient(name="fsd-user")
public interface UserFeignClient {
    
    @RequestMapping(value="/api/buyer/{id}", method = RequestMethod.GET)
    public Buyer findBuyerById(@PathVariable("id") long id);

    @RequestMapping(value="/api/buyer", method = RequestMethod.GET)
    public Buyer findBuyerByUserName(@RequestParam("username") String username);

    @RequestMapping(value="/api/buyer", method = RequestMethod.POST)
    public Buyer registerBuyer(@RequestBody Buyer buyer);

    @RequestMapping(value="/api/seller/{id}", method = RequestMethod.GET)
    public Seller findSellerById(@PathVariable("id") long id);

    @RequestMapping(value="/api/seller", method = RequestMethod.GET)
    public Seller findSellerByUserName(@RequestParam("name") String name);

    @RequestMapping(value="/api/seller", method = RequestMethod.POST)
    public Seller registerSeller(@RequestBody Seller seller);
}