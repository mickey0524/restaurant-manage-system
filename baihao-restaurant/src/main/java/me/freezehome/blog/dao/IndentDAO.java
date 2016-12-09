package me.freezehome.blog.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import me.freezehome.blog.model.Order;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by 皓 on 2016/11/21.
 */
public interface IndentDAO {
    String table = "indent";
    String order = "table_num, bread_crumbs_soup, delicious_carrot_bread, yummy_noodles, vegetable_pasta," +
                   "mixed_veg_salad, breakFast_special, healthy_tomato_soup, decilious_sushi, sum, menu_require";

    @Insert({"insert into", table, "values (#{tableNum}, #{breadCrumbsSoup}, " +
            "#{deliciousCarrotBread}, #{yummyNoodles}, #{vegetablePasta}, " +
            "#{mixedVegSalad}, #{breakFastSpecial}, #{healthyTomatoSoup}," +
            "#{deciliousSushi}, #{sum}, #{menuRequire})" })
    int insertOrder(@Param("tableNum") String tableNum, @Param("breadCrumbsSoup") String breadCrumbsSoup,
                    @Param("deliciousCarrotBread") String deliciousCarrotBread, @Param("yummyNoodles") String yummyNoodles,
                    @Param("vegetablePasta") String vegetablePasta, @Param("mixedVegSalad") String mixedVegSalad,
                    @Param("breakFastSpecial") String breakFastSpecial, @Param("healthyTomatoSoup") String healthyTomatoSoup,
                    @Param("deciliousSushi") String deciliousSushi, @Param("sum") String sum, @Param("menuRequire") String menuRequire);

    @Select({"select", order, "from", table})
    List<Order> getOrder();

    @Delete({"delete from", table, "where table_num=#{tableNum}"})
    int deleteOrder(@Param("tableNum") String tableNum);

    @Select({"SELECT table_num From", table})
    List<String> getAllTableNum();
}
