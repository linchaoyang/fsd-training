package fsd.msservice.cart.api.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fsd.msservice.cart.api.entity.CartEntity;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Integer> {

	@Query(value = "select s.username as sellerName, p.seller_id as sellerId, c.product_id as productId, p.image_url as imageUrl, p.name as name,"
			+ " p.price as price,sc.tax as gtn, c.stock_number as stockNumber,(case when p.price is null then '1'"
			+ "	when sc.tax is null then '1' else '0' end) as available"
			+ " from cart c inner join product p on c.product_id = p.id"
			+ " left outer join subcategory sc on p.category_id = sc.category_id and p.subcategory_id = sc.id"
			+ " left outer join seller s on p.seller_id = s.id where c.buyer_id = :buyerId"
			+ " order by available, p.seller_id, c.id asc", nativeQuery = true)
	List<Map<String, Object>> findAllByBuyerId(@Param("buyerId") String buyerId);

	@Query("select c from CartEntity c where c.buyerId = :buyerId and c.productId=:productId")
	CartEntity findByBuyerIdAndProductId(@Param("buyerId") String buyerId, @Param("productId") String productId);

	@Modifying
	@Query(value = "delete from cart c where c.buyer_id = :buyerId and c.product_id=:productId", nativeQuery = true)
	void deleteByBuyerIdAndProductId(@Param("buyerId") String buyerId, @Param("productId") String productId);
}
