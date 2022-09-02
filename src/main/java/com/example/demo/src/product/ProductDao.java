package com.example.demo.src.product;

import com.example.demo.src.product.model.GetProductRes;
import com.example.demo.src.product.model.PatchChangeStatusReq;
import com.example.demo.src.product.model.PostProductReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ProductDao {
    private JdbcTemplate jdbcTemplate;

    //setter 주입이 아닌 생성자 주입을 해주어도 상관 없다.
    @Autowired //jdbc를 스프링에서 사용시 이와 같이 setDataSource 메소드를 통해 미리 bean으로 등록된 datasource를 주입받는다.
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetProductRes> getProducts() {
        String getProductsQuery = "select * from Product where status = 'Y'";
        return this.jdbcTemplate.query(getProductsQuery,
                (rs, rowNum) -> new GetProductRes(
                        rs.getInt("productIdx"),
                        rs.getInt("sellerIdx"),
                        rs.getInt("location"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getInt("price"),
                        rs.getString("canSuggestPrice"))
                );
    }

    public List<GetProductRes> getProductsByLocation(int location) {
        String getProductsQuery = "select * from Product where location = ? and status = 'Y'";
        int getProductsByLocationParams = location;
        return this.jdbcTemplate.query(getProductsQuery,
                (rs, rowNum) -> new GetProductRes(
                        rs.getInt("productIdx"),
                        rs.getInt("sellerIdx"),
                        rs.getInt("location"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getInt("price"),
                        rs.getString("canSuggestPrice")),
                getProductsByLocationParams);
    }

    public List<GetProductRes> getProductsBySellerIdx(int sellerIdx) {
        String getProductsQuery = "select * from Product where sellerIdx = ? and status = 'Y'";
        int getProductsBySellerIdxParams = sellerIdx;
        return this.jdbcTemplate.query(getProductsQuery,
                (rs, rowNum) -> new GetProductRes(
                        rs.getInt("productIdx"),
                        rs.getInt("sellerIdx"),
                        rs.getInt("location"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getInt("price"),
                        rs.getString("canSuggestPrice")),
                getProductsBySellerIdxParams);
    }

    public int createProduct(PostProductReq postProductReq) {
        String createProductQuery = "insert into Product(sellerIdx, location, title, content, price, canSuggestPrice) " +
                                    "values (?, ?, ?, ?, ?, ?)";
        Object[] createProductParams = new Object[]{postProductReq.getSellerIdx(), postProductReq.getLocation(),
                                                    postProductReq.getTitle(), postProductReq.getContent(),
                                                    postProductReq.getPrice(), postProductReq.getCanSuggestPrice()};
        jdbcTemplate.update(createProductQuery, createProductParams);
        String lastInsertIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값은 가져옴, 노션 참고
        return jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    public int updateStatus(PatchChangeStatusReq patchChangeStatusReq) {
        String updateStatusQuery = "update Product p set p.status = 'N' where p.productIdx = ?";
        int updateStatusQueryParams = patchChangeStatusReq.getProductIdx();
        jdbcTemplate.update(updateStatusQuery, updateStatusQueryParams);
        //updatedAt 컬럼 값을 확인하여 가장 최근에 수정한 row의 id를 가져온다.
        String lastUpdateIdQuery = "select productIdx from Product order by updatedAt desc limit 1";
        int result =  jdbcTemplate.queryForObject(lastUpdateIdQuery, int.class);
        return result;
    }
}
