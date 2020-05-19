package fsd.msservice.product.api.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fsd.msservice.product.api.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

	@Query(value = "select p.id as id, p.name as name, p.image_url as imageUrl, p.manufacture as manufacture,"
			+ "p.seller_id as sellerId, s.username as sellerName, p.price as price, sc.tax as tax"
			+ " from product p, seller s, category c, subcategory sc"
			+ " where p.seller_id = s.id and p.category_id = c.id and p.subcategory_id = sc.id"
			+ " and c.id = sc.category_id"
			+ " and if(:name != '', lcase(p.name) like concat('%', lcase(:name), '%'), 1=1)"
			+ " and if(concat(:category,'') != '', p.category_id = :category, 1=1)", nativeQuery = true)
	List<Map<String, Object>> findProductsSummary(@Param("name") String name, @Param("category") String category);

	@Query(value = "select p.id as id, p.name as name, p.image_url as imageUrl, p.manufacture as manufacture,"
			+ "p.seller_id as sellerId, s.username as sellerName, p.price as price, sc.tax as tax,"
			+ "c.id as categoryId, c.name as categoryName, sc.id as subcategoryId, sc.name as subcategoryName,"
			+ "p.description as description, p.stock_number as stockNumber, p.remarks as remarks, pc.image_url as imageUrl"
			+ " from product p" + " left join product_carousel pc on p.id = pc.product_id"
			+ " inner join seller s, category c, subcategory sc"
			+ " where p.seller_id = s.id and p.category_id = c.id and p.subcategory_id = sc.id"
			+ " and c.id = sc.category_id and p.id = :id order by pc.seq", nativeQuery = true)
	List<Map<String, Object>> findProductDetail(@Param("id") String id);
}