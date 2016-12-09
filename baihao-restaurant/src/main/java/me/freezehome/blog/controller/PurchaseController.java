package me.freezehome.blog.controller;

import me.freezehome.blog.model.Menu;
import me.freezehome.blog.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import me.freezehome.blog.model.menuNum;

import java.util.List;

/**
 * Created by 皓 on 2016/11/19.
 */
@Controller
@RequestMapping(value = "/purchase/*")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    @RequestMapping(value = "num", method = RequestMethod.GET)
    @ResponseBody
    public menuNum getMenuNum() {
        //System.out.println("getMenuNum");
        String menuName[] = new String[]{"breadCrumbsSoup", "deliciousCarrotBread", "yummyNoodles",
                                         "vegetablePasta", "mixedVegSalad", "breakFastSpecial",
                                         "healthyTomatoSoup", "deciliousSushi"};
        String result[] = new String[8];
        for(int i = 0; i < menuName.length; i++) {
            result[i] = purchaseService.getMenuNumByName(menuName[i]);
           // System.out.println(result[i]);
        }
        menuNum menu = new menuNum();
        menu.setBreadCrumbsSoup(result[0]);
        menu.setDeliciousCarrotBread(result[1]);
        menu.setYummyNoodles(result[2]);
        menu.setVegetablePasta(result[3]);
        menu.setMixedVegSalad(result[4]);
        menu.setBreakFastSpecial(result[5]);
        menu.setHealthyTomatoSoup(result[6]);
        menu.setDeciliousSushi(result[7]);
        return menu;
    }

    @RequestMapping(value = "buy", method = RequestMethod.GET)
    @ResponseBody
    public String BuyMenu(@RequestParam("Menu") String menu) {
        System.out.println("BuyMenu");
        String menu_list [] = menu.split(" ");
        for(int i = 0; i < menu_list.length; i++) {
            int index = menu_list[i].indexOf(':');
            String menuName = menu_list[i].substring(0, index);
            String menuChangeNum = menu_list[i].substring(index + 1);
            System.out.println(menuName + " " + menuChangeNum);
            String menuNowNum = purchaseService.getMenuNumByName(menuName);
            System.out.println(menuNowNum);
            String menuNum = String.valueOf(Integer.parseInt(menuNowNum) + Integer.parseInt(menuChangeNum));
            System.out.println(menuNum);
            purchaseService.updateMenuNumByName(menuName, menuNum);
        }
        return "true";
    }

    @RequestMapping(value = "menu", method = RequestMethod.GET)
    @ResponseBody
    public List<Menu> getMenu() {
        List<Menu> menu_list = purchaseService.getMenu();
        //System.out.println(menu_list.size());
        return menu_list;
    }
}
