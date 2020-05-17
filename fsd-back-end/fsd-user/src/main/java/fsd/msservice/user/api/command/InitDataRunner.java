package fsd.msservice.user.api.command;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import fsd.model.user.Buyer;
import fsd.model.user.BuyerRole;
import fsd.model.user.Seller;
import fsd.model.user.SellerRole;
import fsd.model.user.UserStatus;
import fsd.msservice.user.api.repository.BuyerRepository;
import fsd.msservice.user.api.repository.BuyerRoleRepository;
import fsd.msservice.user.api.repository.SellerRepository;
import fsd.msservice.user.api.repository.SellerRoleRepository;

/**
 * Initialize database for test
 * 
 * @author LinChaoYang
 *
 */
@Component
@ConditionalOnProperty(prefix = "job.autorun", name = "enabled", havingValue = "true", matchIfMissing = true)
public class InitDataRunner implements CommandLineRunner {

	@Autowired
	private BuyerRepository buyerRepository;

	@Autowired
	private SellerRepository sellerRepository;

	@Autowired
	private BuyerRoleRepository buyerRoleRepository;

	@Autowired
	private SellerRoleRepository sellerRoleRepository;

	@Override
	public void run(String... args) throws Exception {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		// Role adminRole = roleRepository.findByName("ROLE_ADMIN");
		// if (ObjectUtils.isEmpty(adminRole)) {
		// adminRole = new Role("ROLE_ADMIN");
		// adminRole.setNote("Admin");
		// roleRepository.saveAndFlush(adminRole);u
		// }
		BuyerRole buyerRole = buyerRoleRepository.findByName("ROLE_BUYER");
		if (ObjectUtils.isEmpty(buyerRole)) {
			buyerRole = new BuyerRole();
			buyerRoleRepository.saveAndFlush(buyerRole);
		}
		SellerRole sellerRole = sellerRoleRepository.findByName("ROLE_SELLER");
		if (ObjectUtils.isEmpty(sellerRole)) {
			sellerRole = new SellerRole();
			sellerRoleRepository.saveAndFlush(sellerRole);
		}

		Buyer buyer = buyerRepository.findByUsername("zz");
		if (ObjectUtils.isEmpty(buyer)) {
			buyer = new Buyer();
			buyer.setUsername("zz");
			buyer.setPassword(encoder.encode("123456")); // $2a$10$99UTVBaagS09sKyzYKoqiuW6wSWzoAWu5.PegN6tSY0HxllXt62S.
			buyer.setEmail("zz@163.com");
			buyer.setStatus(UserStatus.Normal.getStatus());
			Calendar c = Calendar.getInstance();
			c.setLenient(false);
			c.setTime(new Date());
			// password expire after 3 months
			c.add(Calendar.MONTH, 3);
			buyer.setExpireDate(c.getTime());
			buyer.setName("zz name");
			buyer.setMobile("13811111111");
			// buyer = buyerRepository.save(buyer);
			List<BuyerRole> buyerRoles = new ArrayList<>();
			buyerRoles.add(buyerRole);
			buyer.setRoles(buyerRoles);
			buyerRepository.save(buyer);
		}
		Seller seller = sellerRepository.findByUsername("zls");
		if (ObjectUtils.isEmpty(seller)) {
			seller = new Seller();
			seller.setUsername("zls");
			seller.setPassword(encoder.encode("123456")); // $2a$10$99UTVBaagS09sKyzYKoqiuW6wSWzoAWu5.PegN6tSY0HxllXt62S.
			seller.setStatus(UserStatus.Normal.getStatus());
			seller.setEmail("zls@163.com");
			Calendar c = Calendar.getInstance();
			c.setLenient(false);
			c.setTime(new Date());
			// password expire after 3 months
			c.add(Calendar.MONTH, 3);
			seller.setExpireDate(c.getTime());
			seller.setCompanyName("zls company Inc.");
			seller.setBrief("Fashion seller");
			seller.setGstin("1133323");
			seller.setPostalAddress("Yangpu, Shanghai, China");
			seller.setContactNumber("86-21-33233222");
			// seller = sellerRepository.save(seller);
			List<SellerRole> sellerRoles = new ArrayList<>();
			sellerRoles.add(sellerRole);
			seller.setRoles(sellerRoles);
			sellerRepository.save(seller);
		}

	}

}