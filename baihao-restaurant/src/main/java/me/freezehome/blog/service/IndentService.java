package me.freezehome.blog.service;

import com.sun.istack.internal.NotNull;
import me.freezehome.blog.dao.IndentDAO;
import me.freezehome.blog.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 皓 on 2016/11/21.
 */
@Service
public class IndentService {
    @Autowired
    private IndentDAO indentDAO;

    public int insertOrder(String tableNum, String breadCrumbsSoup, String deliciousCarrotBread,
                           String yummyNoodles, String vegetablePasta, String mixedVegSalad,
                           String breakFastSpecial, String healthyTomatoSoup, String deciliousSushi, String sum, String menuRequire) {
        //System.out.println("indentSerivce");
        return indentDAO.insertOrder(tableNum, breadCrumbsSoup, deliciousCarrotBread, yummyNoodles,
                                     vegetablePasta, mixedVegSalad, breakFastSpecial, healthyTomatoSoup,
                                     deciliousSushi, sum, menuRequire);
    }

    public List<Order> getOrder() {
        return indentDAO.getOrder();
    }

    public int deleteOrder(@NotNull String tableNum) {
        return indentDAO.deleteOrder(tableNum);
    }

    public List<String> getAllTableNum() {
        return indentDAO.getAllTableNum();
    }
}
