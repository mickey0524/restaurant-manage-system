package me.freezehome.blog.dao;

import me.freezehome.blog.model.Menu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by 皓 on 2016/11/19.
 */
public interface PurchaseDAO {
    String table = "menu";
    String menu = "menu_name, menu_num, menu_time, menu_cost";

    @Select({"SELECT menu_num FROM", table, "WHERE menu_name=#{menuName}" })
    String getMenuNumByName(@Param("menuName") String menuName);

    @Update({"UPDATE", table, "SET menu_num=#{menuNum} where menu_name=#{menuName}"})
    int updateMenuNumByName(@Param("menuName") String menuName, @Param("menuNum") String menuNum);

    @Select({"SELECT", menu, "FROM", table})
    List<Menu> getMenu();

    @Select({"SELECT menu_time FROM", table, "WHERE menu_name=#{menuName}"})
    String getMenuTime(@Param("menuName") String menuName);
}
