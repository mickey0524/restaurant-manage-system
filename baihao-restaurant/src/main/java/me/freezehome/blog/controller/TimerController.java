package me.freezehome.blog.controller;

import me.freezehome.blog.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Calendar;

/**
 * Created by 皓 on 2016/11/27.
 */
@Controller
public class TimerController {
    @Autowired
    private BalanceService balanceService;

    public void timer(){
        System.out.println("计时器开始工作!");
        Calendar c = Calendar.getInstance();
        String month = String.valueOf(c.get(Calendar.MONTH) + 1);
        String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        String date = month + day;
        balanceService.insertRecord(date, "0", "0");
        System.out.println(month + " " + day);
    }
}
