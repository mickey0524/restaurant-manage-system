package me.freezehome.blog.service;

import com.sun.istack.internal.NotNull;
import me.freezehome.blog.dao.ServeDAO;
import me.freezehome.blog.model.Serve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 皓 on 2016/11/22.
 */
@Service
public class ServeService {
    @Autowired
    private ServeDAO serveDAO;

    public int addMenu(@NotNull String cookerName, @NotNull String tableNum, @NotNull String menuName, @NotNull String finishTime) {
        return serveDAO.addMenu(cookerName, tableNum, menuName, finishTime);
    }

    public String getMaxTime(@NotNull String cookerName) {
        return serveDAO.getMaxTime(cookerName);
    }

    public List<Serve> getServe(@NotNull String cookerName) {
        return serveDAO.getServe(cookerName);
    }

    public int deleteMenu(@NotNull String tableNum, @NotNull String menuName, @NotNull String cookerName) {
        return serveDAO.deleteMenu(tableNum, menuName, cookerName);
    }
}
