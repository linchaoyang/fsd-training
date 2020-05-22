package fsd.msservice.transaction.api.command;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import fsd.msservice.transaction.api.entity.TransactionDetailEntity;
import fsd.msservice.transaction.api.entity.TransactionEntity;
import fsd.msservice.transaction.api.repository.TransactionDetailRepository;
import fsd.msservice.transaction.api.repository.TransactionRepository;

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
	private TransactionRepository repository;

	@Autowired
	private TransactionDetailRepository detailRepository;

	@Override
	public void run(String... args) throws Exception {
		long transactionCount = repository.count();
		if (transactionCount == 0) {

			TransactionEntity transaction = new TransactionEntity();
			transaction.setBuyerId("daf49ca9-7898-44d5-bd2b-f8a92300ed54");
			transaction.setBuyerName("zz");
			transaction.setEmail("zz@163.com");
			transaction.setMobile("13811111111");
			transaction.setTotalAmount(new BigDecimal(500.00));
			transaction.setTotalTax(new BigDecimal(25.00));
			transaction.setDiscountCode("sweewe");
			transaction.setStatus("0");
			transaction = repository.save(transaction);

			TransactionDetailEntity detail = new TransactionDetailEntity();
			detail.setTransaction(transaction);
			detail.setSeq("0");
			detail.setProductId("ec2f425e-5153-4902-ae4b-6a36c1c00a45");
			detail.setProductName("Product 2");
			detail.setSellerId("2d8beb47-1766-46fa-a89c-3105d9238251");
			detail.setSellerName("zls");
			detail.setStock(2);
			detail.setImageUrl("http://sss.com/x.gif");
			detail.setPrice(new BigDecimal(150.00));
			detail.setTotalAmount(new BigDecimal(250.00));
			detail.setTotalTax(new BigDecimal(12.50));

			detailRepository.saveAndFlush(detail);

			detail = new TransactionDetailEntity();
			detail.setTransaction(transaction);
			detail.setSeq("1");
			detail.setProductId("1982ee1a-b01f-46d2-9ce9-e449308dc344");
			detail.setProductName("Product 1");
			detail.setSellerId("2d8beb47-1766-46fa-a89c-3105d9238251");
			detail.setSellerName("zls");
			detail.setStock(2);
			detail.setImageUrl("http://sss.com/x.gif");
			detail.setPrice(new BigDecimal(150.00));
			detail.setTotalAmount(new BigDecimal(250.00));
			detail.setTotalTax(new BigDecimal(12.50));

			detailRepository.saveAndFlush(detail);
		}

	}

}