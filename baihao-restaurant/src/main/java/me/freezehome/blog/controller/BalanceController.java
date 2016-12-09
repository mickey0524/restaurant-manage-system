package me.freezehome.blog.controller;

import me.freezehome.blog.service.BalanceService;
import me.freezehome.blog.service.IndentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import me.freezehome.blog.model.Balance;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;
import java.util.List;

/**
 * Created by 皓 on 2016/11/20.
 */
@Controller
@RequestMapping(value = "/balance/*")
public class BalanceController {
    @Autowired
    private BalanceService balanceService;

    @Autowired
    private IndentService indentService;

    public String getDate() {
        Calendar c = Calendar.getInstance();
        String month = String.valueOf(c.get(Calendar.MONTH) + 1);
        String day = String .valueOf(c.get(Calendar.DAY_OF_MONTH));
        String date = month + day;
        return date;
    }

    public String getBeforeDate(String date, int num) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int[] monDays = new int[] {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if ( ( (year) % 4 == 0 && (year) % 100 != 0) ||(year) % 400 == 0)
            monDays[1]++;

        int day = Integer.parseInt(date.substring(date.length() - 2));
        int month = Integer.parseInt(date.substring(0, date.length() - 2));

        if(day < num) {
            month -= 1;
            if(month == 0) {
                month = 12;
            }
            day = monDays[month - 1] - (7 - day);
        }
        else {
            day -= 6;
        }
        return String.valueOf(month) + String.valueOf(day);
    }

    @RequestMapping(value = "getall", method = RequestMethod.GET)
    @ResponseBody
    public Balance getAll() {
       // System.out.println("Getall");
        Balance balance = new Balance();
        String date = getDate();
       // System.out.println(balanceService.getExpense() + " " + balanceService.getIncome() + " " + balanceService.getNetIncome());
        balance.setExpense(balanceService.getExpense(date));
        balance.setIncome(balanceService.getIncome(date));
        //balance.setNetIncome(balanceService.getNetIncome());
        return balance;
    }

    @RequestMapping(value = "expense", method = RequestMethod.GET)
    @ResponseBody
    public void updateExpense(@RequestParam("Expense") String Expense) {
       // System.out.println("Expense");
        String date = getDate();
        String nowExpense = balanceService.getExpense(date);
      //  System.out.println(NowExpense);
        String newExpense = String.valueOf(Integer.parseInt(nowExpense) + Integer.parseInt(Expense));
      //  System.out.println(NewExpense);
        balanceService.updateExpense(newExpense, date);
    }

    @RequestMapping(value = "income", method = RequestMethod.GET)
    @ResponseBody
    public void updateIncome(@RequestParam("Income") String Income, @RequestParam("tableNum") String tableNum) {
        //System.out.println("income");
        String date = getDate();
        String nowIncome = balanceService.getIncome(date);

        String newIncome = String.valueOf(Integer.parseInt(nowIncome) + Integer.parseInt(Income));
        balanceService.updateIncome(newIncome, date);
        indentService.deleteOrder(tableNum);
    }

    @RequestMapping(value = "bill", method = RequestMethod.GET)
    @ResponseBody
    public List<Balance> getBill() {
        System.out.println("bill");
        String beginDate = getDate();
        String endDate = getBeforeDate(beginDate, 7);
        System.out.println(beginDate + ' ' + endDate);
        List<Balance> bill = balanceService.getBill(beginDate, endDate);
        System.out.print(bill.size());
        return bill;
    }
}
