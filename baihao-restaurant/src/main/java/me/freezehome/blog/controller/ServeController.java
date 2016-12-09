package me.freezehome.blog.controller;

import me.freezehome.blog.service.ServeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import me.freezehome.blog.model.Serve;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 皓 on 2016/11/23.
 */

@Controller
@RequestMapping(value = "serve/*")
public class ServeController {
    @Autowired
    private ServeService serveService;

    @RequestMapping(value = "Serve", method = RequestMethod.GET)
    @ResponseBody
    public List<List<Serve>> getServe() {
        System.out.println("Serve");
        String cookerList[] = new String[]{"Mark_Simmy", "Alan_Smith", "Carol_Adams"};
        List<List<Serve>> serveList = new ArrayList<List<Serve>>();
        for(int i = 0; i < cookerList.length; i++) {
            serveList.add(serveService.getServe(cookerList[i]));
        }
        return serveList;
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    @ResponseBody
    public void deleteMenu(@RequestParam("tableNum") String tableNum, @RequestParam("menuName") String menuName,
                           @RequestParam("cookerName") String cookerName) {
        System.out.println("delete");
        serveService.deleteMenu(tableNum, menuName, cookerName);
    }
}
