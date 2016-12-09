package me.freezehome.blog.service;

import com.sun.istack.internal.NotNull;
import me.freezehome.blog.dao.PurchaseDAO;
import me.freezehome.blog.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 皓 on 2016/11/19.
 */
@Service
public class PurchaseService {
    @Autowired
    private PurchaseDAO purchaseDAO;

    public String getMenuNumByName(@NotNull String menuName) {
        return purchaseDAO.getMenuNumByName(menuName);
    }

    public int updateMenuNumByName(@NotNull String menuName, String menuNum) {
        return purchaseDAO.updateMenuNumByName(menuName, menuNum);
    }

    public List<Menu> getMenu() {
        return purchaseDAO.getMenu();
    }

    public String getMenuTime(@NotNull String menuName) {
        return purchaseDAO.getMenuTime(menuName);
    }
}
