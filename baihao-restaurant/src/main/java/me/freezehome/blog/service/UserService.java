package me.freezehome.blog.service;

import com.sun.istack.internal.NotNull;
import me.freezehome.blog.dao.UserDAO;
import me.freezehome.blog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by freeze on 16-11-13.
 */
@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    public User getUserById(@NotNull String userId){
       // System.out.println("UserService");
        return userDAO.getUserById(userId);
    }

    public List<String> getAllUserName() {
        return userDAO.getAllUserName();
    }

//    public int updateUserLogin(@NotNull String isLogin, String userName) {
//        return userDAO.updateUserLogin(isLogin, userName);
//    }

    public String getLoginById(@NotNull String userName) {
        return userDAO.getLoginById(userName);
    }

    /*public String getMenuNum(@NotNull String menuName) {
        return
    }*/

//    public User getUserByUserName(@NotNull String userName){
//        return userDAO.getUserByName(userName);
//    }
//
//    public User getUserByUserEmail(@NotNull String userEmail){
//        return userDAO.getUserByEmail(userEmail);
//    }
//
//    public User getUserByUserPhone(@NotNull String userPhone){
//        return userDAO.getUserByPhone(userPhone);
//    }
//
//    public String getUserPasswordByUserId(long userId){
//        return userDAO.getUserPasswordByUserId(userId);
//    }
//
//    public int addUser(@NotNull User user){
//        return userDAO.insertUser(user);
//    }
//
//    public int deleteUserById(long userId){
//        return userDAO.deleteUserById(userId);
//    }
//
//    public int updateUseName(@NotNull  String userName , long userId){
//        return userDAO.updateUserName(userName, userId);
//    }
//
//    public int updateUserEmail(@NotNull String userEmail, long userId){
//        return userDAO.updateUserEmail(userEmail, userId);
//    }
//
//    public int updateUserPhone(@NotNull String userPhone, long userId){
//        return userDAO.updateUserPhone(userPhone, userId);
//    }
//
//    public int updateUserPassword(@NotNull String userPassword, long userId){
//        return userDAO.updateUserPassword(userPassword, userId);
//    }
//
//    public int updateUseRank(@NotNull UserEnum userEnum, long userId){
//        return userDAO.updateUserRank(userEnum.getRank(), userId);
//    }
}