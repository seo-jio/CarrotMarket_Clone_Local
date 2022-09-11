package com.example.demo.src.deal;

import com.example.demo.src.deal.model.PostDealReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class DealDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired //jdbc를 스프링에서 사용시 이와 같이 setDataSource 메소드를 통해 미리 bean으로 등록된 datasource를 주입받는다.
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public int
    createDeal(PostDealReq postDealReq) {
        //거래 생성
        String createDealQuery = "insert into Deal(buyerIdx, productIdx, dealPrice) " +
                                 "values(?, ?, ?)";
        Object[] createDealParams = new Object[]{postDealReq.getBuyerIdx(), postDealReq.getProductIdx(), postDealReq.getDealPrice()};
        jdbcTemplate.update(createDealQuery, createDealParams);

        //거래 상품의 status 판매완료 상태로 변경
        String updateStatusQuery = "update Product p set p.status = 'S' where p.productIdx = ?";
        int updatedStatusParams = postDealReq.getProductIdx();
        jdbcTemplate.update(updateStatusQuery, updatedStatusParams);

        String lastInsertIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값은 가져옴, 노션 참고
        return jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }
}
