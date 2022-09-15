package com.example.demo.src.user;

import com.example.demo.src.user.model.PostUserOAuthReq;
import com.example.demo.src.user.model.PostUserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class OAuthDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int createUserOAuth(PostUserOAuthReq postUserOAuthReq) {
        String createUserQuery = "insert into User (email, password, nickname) VALUES (?,?,?)"; // 실행될 동적 쿼리문
        Object[] createUserParams = new Object[]{postUserOAuthReq.getEmail(), postUserOAuthReq.getPassword(), postUserOAuthReq.getNickname()}; // 동적 쿼리의 ?부분에 주입될 값
        this.jdbcTemplate.update(createUserQuery, createUserParams);
        // email -> postUserReq.getEmail(), password -> postUserReq.getPassword(), nickname -> postUserReq.getNickname() 로 매핑(대응)시킨다음 쿼리문을 실행한다.
        // 즉 DB의 User Table에 (email, password, nickname)값을 가지는 유저 데이터를 삽입(생성)한다.
        System.out.println("회원 가입 성공");
        String lastInsertIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값은 가져온다.
        System.out.println("가입한 휘원의 userIdx = " + lastInsertIdQuery);
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class); // 해당 쿼리문의 결과 마지막으로 삽인된 유저의 userIdx번호를 반환한다.
    }
}
