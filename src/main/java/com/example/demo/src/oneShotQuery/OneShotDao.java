package com.example.demo.src.oneShotQuery;

import com.example.demo.src.oneShotQuery.model.GetLifePageRes;
import com.example.demo.src.oneShotQuery.model.GetMainPageRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
@Repository
public class OneShotDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired //jdbc를 스프링에서 사용시 이와 같이 setDataSource 메소드를 통해 미리 bean으로 등록된 datasource를 주입받는다.
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetMainPageRes> getMainPage(int userIdx) {
        System.out.println("DAO 시작");
        String mainPageQuery = "select p.productIdx, p.title, p.price, uaa.roadNameAddress, pi.productImageUrl, ifnull(pc.cnt, 0) as chattingRoomCount,\n" +
                "       ifnull(wl.cnt, 0) as likeCount,\n" +
                "       @isUpdated := (case\n" +
                "                        when p.createdAt != p.updatedAt\n" +
                "                        then '끌올'\n" +
                "                        else '초기생성'\n" +
                "                    end) as isUpdated,\n" +
                "        case\n" +
                "            when @isUpdated = '끌올' and timestampdiff(second, p.updatedAt, now()) < 60\n" +
                "               then concat(timestampdiff(second, p.updatedAt, now()), '초 전')\n" +
                "           when @isUpdated = '끌올' and timestampdiff(minute, p.updatedAt, now()) < 60\n" +
                "               then concat(timestampdiff(minute, p.updatedAt, now()), '분 전')\n" +
                "           when @isUpdated = '끌올' and timestampdiff(hour, p.updatedAt, now()) < 60\n" +
                "               then concat(timestampdiff(hour, p.updatedAt, now()), '시간 전')\n" +
                "           when @isUpdated = '끌올' and timestampdiff(hour, p.updatedAt, now()) >= 60\n" +
                "               then concat(timestampdiff(day, p.updatedAt, now()), '일 전')\n" +
                "           when @isUpdated = '초기생성' and timestampdiff(second, p.createdAt, now()) < 60\n" +
                "               then concat(timestampdiff(second, p.createdAt, now()), '초 전')\n" +
                "           when @isUpdated = '초기생성' and timestampdiff(minute, p.createdAt, now()) < 60\n" +
                "               then concat(timestampdiff(minute, p.createdAt, now()), '분 전')\n" +
                "           when @isUpdated = '초기생성' and timestampdiff(hour, p.createdAt, now()) < 60\n" +
                "               then concat(timestampdiff(hour, p.createdAt, now()), '시간 전')\n" +
                "           when @isUpdated = '초기생성' and timestampdiff(hour, p.createdAt, now()) >= 60\n" +
                "               then concat(timestampdiff(day, p.createdAt, now()), '일 전')\n" +
                "        end as productTime\n" +
                "from (select * from Product where Product.status = 'Y') p\n" +
                "    join (select ua.addressIdx, a.roadNameAddress from UserAddress ua join Address a on ua.userIdx = ? and ua.addressIdx = a.addressIdx) uaa\n" +
                "        on p.location = uaa.addressIdx\n" +
                "    left join ProductImage pi on p.productIdx = pi.productIdx and pi.status = 'Y' and pi.isMain = 'T'\n" +
                "    left join (select pc.productIdx, count(pc.productIdx) as cnt\n" +
                "                from ProductChatting pc\n" +
                "                where pc.status = 'Y'\n" +
                "                group by pc.productIdx) pc on p.productIdx = pc.productIdx\n" +
                "    left join (select wl.productIdx, count(wl.userIdx) as cnt\n" +
                "                from WishList wl\n" +
                "                where wl.status = 'Y'\n" +
                "                group by wl.productIdx) wl on p.productIdx = wl.productIdx";
        int mainPageParams = userIdx;
        return jdbcTemplate.query(mainPageQuery,
                (rs, rowNum) -> new GetMainPageRes(
                        rs.getInt("productIdx"),
                        rs.getString("title"),
                        rs.getInt("price"),
                        rs.getString("roadNameAddress"),
                        rs.getString("productImageUrl"),
                        rs.getInt("chattingRoomCount"),
                        rs.getInt("likeCount"),
                        rs.getString("isUpdated"),
                        rs.getString("productTime")),
                mainPageParams);
    }

    public List<GetLifePageRes> getLifePage(int userIdx) {
        String lifePageQuery = "select p.postIdx, p.writerIdx, u.nickname, a.roadNameAddress, p.title, p.content, pt.typeName as postTypeName, pi.postImageUrl,\n" +
                "       pcsc.commentCount, pcsc.sympathyCount, pcsc.curiousCount,\n" +
                "       case\n" +
                "           when timestampdiff(second, p.createdAt, now()) < 60\n" +
                "               then concat(timestampdiff(second, p.createdAt, now()), '초 전')\n" +
                "           when timestampdiff(minute, p.createdAt, now()) < 60\n" +
                "               then concat(timestampdiff(minute, p.createdAt, now()), '분 전')\n" +
                "           when timestampdiff(hour, p.createdAt, now()) < 60\n" +
                "               then concat(timestampdiff(hour, p.createdAt, now()), '시간 전')\n" +
                "           when timestampdiff(hour, p.createdAt, now()) >= 60\n" +
                "               then concat(timestampdiff(day, p.createdAt, now()), '일 전')\n" +
                "        end as postTime\n" +
                "from (select * from Post p where p.status = 'Y') p\n" +
                "    join (select a.addressIdx, a.roadNameAddress\n" +
                "            from UserAddress ua\n" +
                "            join Address a on ua.addressIdx = a.addressIdx and ua.userIdx = ?) uaa on p.location = uaa.addressIdx\n" +
                "    join PostType pt on p.postTypeIdx = pt.postTypeIdx\n" +
                "    left join PostImage pi on p.postIdx = pi.postIdx and pi.status = 'Y'\n" +
                "    left join (select pcs.postIdx, pcs.title, pcs.commentCount, pcs.sympathyCount, count(c.curiousIdx) as curiousCount\n" +
                "                from (select pc.postIdx, pc.title, pc.commentCount, count(ps.sympathyIdx) as sympathyCount\n" +
                "                from (select p.postIdx, p.title, count(c.commentIdx) as commentCount\n" +
                "                        from (select * from Post p where p.status = 'Y') p\n" +
                "                            left join Comment c on p.postIdx = c.postIdx\n" +
                "                            group by p.postIdx) pc\n" +
                "                                left join PostSympathy ps on pc.postIdx = ps.postIdx\n" +
                "                                group by pc.postIdx) pcs\n" +
                "                                    left join Curious c on pcs.postIdx = c.postIdx\n" +
                "                                    group by pcs.postIdx) pcsc on p.postIdx = pcsc.postIdx\n" +
                "    join User u on p.writerIdx = u.userIdx\n" +
                "    join UserAddress ua on u.userIdx = ua.userIdx\n" +
                "    join Address a on a.addressIdx = ua.addressIdx";
        int lifePageParams = userIdx;
        return jdbcTemplate.query(lifePageQuery,
                (rs, rowNum) -> new GetLifePageRes(
                        rs.getInt("postIdx"),
                        rs.getInt("writerIdx"),
                        rs.getString("nickname"),
                        rs.getString("roadNameAddress"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("postTypeName"),
                        rs.getString("postImageUrl"),
                        rs.getInt("commentCount"),
                        rs.getInt("sympathyCount"),
                        rs.getInt("curiousCount"),
                        rs.getString("postTime")),
                lifePageParams);
    }
}
