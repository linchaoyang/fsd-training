package fsd.msservice.transaction.api.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fsd.msservice.transaction.api.entity.TransactionEntity;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, String> {

	@Query(value = "select t.id as id, td.image_url as imageUrl, td.product_name as title,"
			+ " min_product.productCount as productCount,t.total_amount as totalAmount,"
			+ " t.total_tax as totalTax, t.discount_code as discountCode, t.created_date as created"
			+ " from transaction t, transaction_detail td, "
			+ " (select transaction_id, count(td2.id) as productCount, min(td2.seq) as min_seq"
			+ "   from transaction t2, transaction_detail td2" + "   where t2.buyer_id = :id and t2.status = '0'"
			+ " and if(:start != '', DATE_FORMAT(t2.created_date,'%Y%m%d') >= :start, 1=1)"
			+ " and if(:end !='', DATE_FORMAT(t2.created_date,'%Y%m%d') <= :end, 1=1)"
			+ " and t2.id = td2.transaction_id group by td2.transaction_id) min_product"
			+ " where t.buyer_id = :id and t.status = '0'"
			+ " and if(:start != '', DATE_FORMAT(t.created_date,'%Y%m%d') >= :start, 1=1)"
			+ " and if(:end != '', DATE_FORMAT(t.created_date,'%Y%m%d') <= :end, 1=1)"
			+ " and t.id = td.transaction_id and t.id = min_product.transaction_id"
			+ " and td.seq = min_product.min_seq", nativeQuery = true)
	List<Map<String, Object>> findAllUnderBuyerByPeriod(@Param("id") String buyerId, @Param("start") String start,
			@Param("end") String end);

	@Query(value = "select td.transaction_id as id, t.buyer_name as buyerName, t.email as email, t.mobile as mobile,"
			+ " td.product_id as productId, td.product_name as productName, td.price as price,"
			+ " td.stock_number as purchaseCount, td.total_amount as totalAmount, td.total_tax as totalTax, t.created_date as created"
			+ " from transaction_detail td, transaction t where td.seller_id = :id and td.transaction_id = t.id"
			+ " and if(:start != '', DATE_FORMAT(t.created_date,'%Y%m%d') >= :start, 1=1)"
			+ " and if(:end != '', DATE_FORMAT(t.created_date,'%Y%m%d') <= :end, 1=1)"
			+ " order by t.created_date asc", nativeQuery = true)
	List<Map<String, Object>> findAllUnderSellerByPeriod(@Param("id") String sellerId, @Param("start") String start,
			@Param("end") String end);

//	@Component
//	public class MyJdbc {
//
//	@Autowired
//	    protected JdbcTemplate jdbc;
//
//	}
//	blob= myJdbc.jdbc.getDataSource().getConnection().createBlob();
//	blob.setBytes(1, str.getBytes());
//	pstmt = myJdbc.jdbc.getDataSource().getConnection().prepareStatement("update user_profile set profileImage=? where user_profile.id in ( select id from user_login where email=?)");
//	final String query = "update user_profile set profileImage=? where user_profile.id in ( select id from user_login where email=?)";
//	myJdbc.jdbc.execute(query, new AbstractLobCreatingPreparedStatementCallback(lobHandler) { 1
//	    protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException {
//	        byte[] bytes = str.getBytes();
//	        ps.setString(2, email);
//	        lobCreator.setBlobAsBinaryStream(ps, 1, str.getBytes()); 
//	    }
//	});
}