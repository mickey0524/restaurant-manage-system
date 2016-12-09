package me.freezehome.blog.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import me.freezehome.blog.model.Balance;

import java.util.List;

/**
 * Created by 皓 on 2016/11/20.
 */
public interface BalanceDAO {
    String table = "balance";

    @Select({"SELECT expense FROM", table, "WHERE date=#{date}" })
    String getExpense(@Param("date") String date);

    @Select({"SELECT income FROM", table, "WHERE date=#{date}" })
    String getIncome(@Param("date") String date);

//    @Select({"SELECT net_income FROM", table, "WHERE name='bupt'" })
//    String getNetIncome();

    @Update({"UPDATE", table, "SET expense=#{expense} where date=#{date}"})
    int updateExpense(@Param("expense") String expense, @Param("date") String date);

    @Update({"UPDATE", table, "SET income=#{income} where date=#{date}"})
    int updateIncome(@Param("income") String income, @Param("date") String date);

    @Insert({"INSERT INTO", table, "VALUES(#{date}, #{expense}, #{income})"})
    int insertRecord(@Param("date") String date, @Param("expense") String expense, @Param("income") String income);

    @Select({"SELECT * FROM", table, "where date <= #{beginDate} and date >= #{endDate}"})
    List<Balance> getBill(@Param("beginDate") String beginDate, @Param("endDate") String endDate);

}
