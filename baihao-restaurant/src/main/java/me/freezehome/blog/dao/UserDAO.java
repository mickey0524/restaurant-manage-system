package me.freezehome.blog.dao;

import me.freezehome.blog.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by freeze on 16-11-13.
 */
//@Repository
public interface UserDAO {
    String table = "administrator";
    String id = "user_name";
    String selectField = "user_name, user_password";
    String userRank = "user_rank";
    String insertField = "user_name, user_phone, user_email, user_password";

    @Select({"select user_name from", table})
    List<String> getAllUserName();

    @Select({"SELECT", selectField, "FROM", table, "WHERE user_name=#{userId}" })
    User getUserById(@Param("userId") String userId);

    @Select({"SELECT is_login FROM", table, "WHERE user_name=#{userId}"})
    String getLoginById(@Param("userId") String userID);

    @Update({"UPDATE ", table, "SET is_login=#{isLogin} where user_name=#{userName}"})
    int updateUserLogin(@Param("isLogin") String isLogin,
                        @Param("userName") String userName);

    @Select({"SELECT", selectField, "FROM", table, "WHERE user_name=#{userName}"})
    User getUserByName(@Param("userName") String userName);

    @Select({"SELECT", selectField, "FROM", table, "WHERE user_phone=#{userPhone}"})
    User getUserByPhone(@Param("userPhone") String userPhone);

    @Select({"SELECT", selectField, "FROM", table, "WHERE user_email=#{userEmail}"})
    User getUserByEmail(@Param("userEmail") String userEmail);

    @Insert({"INSERT INTO ", table, "(", insertField, ")", "VALUES(#{userName}, #{userPhone}, #{userEmail}, #{userPassword})" })
    int insertUser(User user);

    @Delete({"DELETE FROM ", table, "WHERE id=#{id}" })
    int deleteUserById(@Param("id") long userId);

    @Select({"SELECT user_password FROM ", table, "WHERE id=#{userId}"})
    String getUserPasswordByUserId(@Param("userId") long userId);

    @Update({"UPDATE ", table, "SET user_phone=#{userPhone} WHERE id=#{userId}"})
    int updateUserPhone(@Param("userPhone") String userPhone,
                        @Param("userId") long userId);

    @Update({"UPDATE ", table, "SET user_name=#{userName} WHERE id=#{userId}"})
    int updateUserName(@Param("userName") String userName,
                       @Param("userId") long userId);

    @Update({"UPDATE ", table, "SET user_email=#{userEmail} where id=#{userId}"})
    int updateUserEmail(@Param("userEmail") String userEmail,
                        @Param("userId") long userId);

    @Update({"UPDATE ", table, "SET user_password=#{userPassword} where id=#{userId}"})
    int updateUserPassword(@Param("userPassword") String userPassword,
                           @Param("userId") long userId);


    @Update({"UPDATE ", table, "SET user_rank=#{userRank} where id=#{userId}"})
    int updateUserRank(@Param("userRank") int userRank,
                       @Param("userId") long userId);
}
