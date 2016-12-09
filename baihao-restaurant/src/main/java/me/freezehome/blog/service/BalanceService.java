package me.freezehome.blog.service;

import com.sun.istack.internal.NotNull;
import me.freezehome.blog.dao.BalanceDAO;
import me.freezehome.blog.model.Balance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 皓 on 2016/11/20.
 */
@Service
public class BalanceService {
    @Autowired
    private BalanceDAO balanceDAO;

    public String getExpense(@NotNull String date) {
        //System.out.println("balanceService");
        return balanceDAO.getExpense(date);
    }

    public String getIncome(@NotNull String date) {
        //System.out.println("balanceService");
        return balanceDAO.getIncome(date);
    }

//    public String getNetIncome() {
//        //System.out.println("balanceService");
//        return balanceDAO.getNetIncome();
//    }

    public int updateExpense(@NotNull String expense, @NotNull String date) {
        //System.out.println("balanceService1");
        return balanceDAO.updateExpense(expense, date);
    }

    public int updateIncome(@NotNull String income, @NotNull String date) {
        return balanceDAO.updateIncome(income, date);
    }

    public int insertRecord(@NotNull String date, @NotNull String expense, @NotNull String income) {
        return balanceDAO.insertRecord(date, expense, income);
    }

    public List<Balance> getBill(@NotNull String beginDate, @NotNull String endDate) {
        return balanceDAO.getBill(beginDate, endDate);
    }
}
