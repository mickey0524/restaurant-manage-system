package me.freezehome.blog.controller;

import me.freezehome.blog.service.IndentService;
import me.freezehome.blog.model.Order;
import me.freezehome.blog.service.PurchaseService;
import me.freezehome.blog.service.ServeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by 皓 on 2016/11/21.
 */

@Controller
@RequestMapping(value = "/indent/*")
public class IndentController {
    @Autowired
    private IndentService indentService;

    @Autowired
    private ServeService serveService;

    @Autowired
    private PurchaseService purchaseService;

    public String getMin(Map<String, String> map) {
        int minNum = Integer.MAX_VALUE;
        String result = "";
        Iterator<Map.Entry<String, String>> iter = map.entrySet().iterator();
        while(iter.hasNext()) {
            Map.Entry<String, String> entry = iter.next();
            String name = entry.getKey();
            String value = entry.getValue();
            if(Integer.parseInt(value) < minNum) {
                minNum = Integer.parseInt(value);
                result = name;
            }
        }
        return result;
    }

    public String changeFormat(int second, int minute, int hour) {
        String res = "";
        if(second >= 60) {
            minute += (second / 60);
            second %= 60;
        }
        if(second >= 0 && second < 10) {
            res = res + '0' + String.valueOf(second);
        }
        else {
            res = res + String.valueOf(second);
        }
        if(minute >= 60) {
            hour += (minute / 60);
            minute %= 60;
        }
        if(minute >= 0 && minute < 10) {
            res = '0' + String.valueOf(minute) + res;
        }
        else {
            res = String.valueOf(minute) + res;
        }
        res = String.valueOf(hour % 24) + res;
        return res;
    }

    public int deleteZero(String string) {
        if(string.charAt(0) == '0') {
            return string.charAt(1) - '0';
        }
        else {
            return Integer.parseInt(string);
        }
    }

    @RequestMapping(value = "order", method = RequestMethod.GET)
    @ResponseBody
    public String addOrder(@RequestParam("Order") String Order) {
        //System.out.println("Order");
        String cookerList[] = new String[]{"Mark_Simmy", "Alan_Smith", "Carol_Adams"};
        ArrayList<String> menuList = new ArrayList(Arrays.asList("breadCrumbsSoup", "deliciousCarrotBread", "yummyNoodles",
                                         "vegetablePasta", "mixedVegSalad", "breakFastSpecial",
                                         "healthyTomatoSoup", "deciliousSushi"));

        Map<String, String> maxTime = new HashMap<String, String>();
        for(int i = 0; i < cookerList.length; i++) {
            String time = serveService.getMaxTime(cookerList[i]);
            //System.out.println("maxTime:" + time);
            maxTime.put(cookerList[i], serveService.getMaxTime(cookerList[i]));
        }
        //System.out.println("order_1");
        System.out.println(Order);
        String order[] = Order.split(" ");
        Map<String, String> map = new HashMap<String, String>();
        String isReturn = "";
        boolean isDeal = false;
        for(int i = 0; i < order.length; i++) {
            String name = order[i].substring(0, order[i].indexOf(':'));
            String num = order[i].substring(order[i].indexOf(':') + 1);
            map.put(name, num);
            if(menuList.contains(name) && Integer.parseInt(num) > 0) {
                String lastNum = purchaseService.getMenuNumByName(name);
                int nowNum = Integer.parseInt(lastNum) - Integer.parseInt(num);
                System.out.println(nowNum);
                if(nowNum < 0) {
                    isReturn = isReturn + name + ' ';
                    map.put(name, "0");
                }
                else {
                    purchaseService.updateMenuNumByName(name, String.valueOf(nowNum));
                    isDeal = true;
                }
            }
        }

        List<String> allTableNum = indentService.getAllTableNum();
        if(allTableNum.contains(map.get("tableNum"))) {
            return "false";
        }

        if(!isDeal) {
            return isReturn;
        }

        indentService.insertOrder(map.get("tableNum"), map.get("breadCrumbsSoup"), map.get("deliciousCarrotBread"),
                                  map.get("yummyNoodles"), map.get("vegetablePasta"), map.get("mixedVegSalad"),
                                  map.get("breakFastSpecial"), map.get("healthyTomatoSoup"), map.get("deciliousSushi"),
                                  map.get("sum"), map.get("menuRequire"));

        //System.out.println("order_2");

        for(int i = 2; i < order.length - 1; i++) {
            String name = order[i].substring(0, order[i].indexOf(':'));
            String num = map.get(name);
          //  System.out.println(name + " " + num);
            if(!num.equals("0")) {        //这道菜需要分配厨师
                int loop = Integer.parseInt(num);
                while(loop > 0) {
                    String cookerName = getMin(maxTime);          //找分配这道菜的厨师
                  //  System.out.println("cook : " + cookerName);
                    String cookerLast = maxTime.get(cookerName);    //厨师当前做完最后一道菜的时间
                   // System.out.println("cookLast : " + cookerLast);
                   // System.out.println("time : " + purchaseService.getMenuTime(name));
                    int menuTime = Integer.parseInt(purchaseService.getMenuTime(name));   //这道菜需要的时间
                    int minuteNeed = menuTime / 60;
                    int secondNeed = menuTime % 60;
                 //   System.out.println("minute : " + minuteNeed);
                 //   System.out.println("second : " + secondNeed);
                    String sqlTime = "";
                    int second, minute, hour;
                    //System.out.println(cookerLast == "0");
                    if (cookerLast.equals("0")) {               //分配的厨师原先没有分配的任务
                       // System.out.println("0");
                        Calendar c = Calendar.getInstance();
                        second = c.get(Calendar.SECOND);      //获得当前的秒钟
                        minute = c.get(Calendar.MINUTE);      //获得当前的分钟
                        hour = c.get(Calendar.HOUR_OF_DAY);   //获得当前的小时
                    } else {                //分配的厨师原先有分配的任务,从数据库中去厨师最后一道菜完成时间
                      //  System.out.println("not 0");
                        second = deleteZero(cookerLast.substring(cookerLast.length() - 2, cookerLast.length()));
                        minute = deleteZero(cookerLast.substring(cookerLast.length() - 4, cookerLast.length() - 2));
                        hour = Integer.parseInt(cookerLast.substring(0, cookerLast.length() - 4));
                    }
                  //  System.out.println(hour + " " + minute + " " + second);
                    sqlTime = changeFormat(second + secondNeed, minute + minuteNeed, hour);
                   // System.out.println("time:" + sqlTime);
                    serveService.addMenu(cookerName, map.get("tableNum"), name, sqlTime);
                    maxTime.put(cookerName, sqlTime);
                    loop -= 1;
                }
            }
        }
        if(isReturn == "") {
            return "true";
        }
        else {
            return isReturn;
        }
    }

    @RequestMapping(value = "getOrder", method = RequestMethod.GET)
    @ResponseBody
    public List<Order> getOrder() {
        List<Order> order = indentService.getOrder();
        System.out.println(order.size());
        return order;
    }
}
