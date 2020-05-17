package fsd.msservice.transaction.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fsd.msservice.transaction.api.entity.TransactionDetailEntity;
import fsd.msservice.transaction.api.model.BuyerTransactionDetailVO;

@Repository
public interface TransactionDetailRepository extends JpaRepository<TransactionDetailEntity, Integer> {

	@Query(value = "SELECT td.product_id as productId, td.image_url as imagUrl, td.product_name as productName,"
			+ " td.stock_number as stock, td.total_amount as totalAmount, td.total_tax as totalTax,"
			+ " td.seller_id as sellerId,td.seller_name as sellerName FROM transaction_detail td"
			+ " where td.transaction_id = :id order by td.seq asc", nativeQuery = true)
	List<BuyerTransactionDetailVO> findDetailsById(@Param("id") String transactionId);

}