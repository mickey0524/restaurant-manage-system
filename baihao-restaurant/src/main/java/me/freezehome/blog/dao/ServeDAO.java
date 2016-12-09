package me.freezehome.blog.dao;

import me.freezehome.blog.model.Serve;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by 皓 on 2016/11/22.
 */
public interface ServeDAO {
    String table = "serve";
    String serve = "cooker_name, table_num, menu_name, finish_time";

    @Insert({"Insert into", table, "values (#{cookerName}, #{tableNum}, #{menuName}, #{finishTime})"})
    int addMenu(@Param("cookerName") String cookerName, @Param("tableNum") String tableNum,
                @Param("menuName") String menuName, @Param("finishTime") String finishTime);

    @Select({"Select IFNULL(max(finish_time), 0) from", table, "where cooker_name=#{cookerName}"})
    String getMaxTime(@Param("cookerName") String cookerName);

    @Select({"Select", serve, "From", table, "Where cooker_name=#{cookerName}"})
    List<Serve> getServe(@Param("cookerName") String cookerName);

    @Delete({"Delete From", table, "Where table_num=#{tableNum} and menu_name=#{menuName} and cooker_name=#{cookerName}"})
    int deleteMenu(@Param("tableNum") String tableNum, @Param("menuName") String menuName, @Param("cookerName") String cookerName);
}
