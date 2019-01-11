package com.jeeplus.modules.books.web;

import com.jeeplus.modules.books.entity.ConditionDTO;
import com.jeeplus.modules.books.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    //轮播图
    @RequestMapping("/slideshowBooks")
    @ResponseBody
    public Map slideshowBooks() {
        Map map = bookService.slideshowBooks();
        return map;
    }

    //主编推荐
    @RequestMapping("/recommendBooks")
    @ResponseBody
    public Map recommendBooks() {
        Map map = bookService.recommendBooks();
        return map;
    }


    //新书上架
    @RequestMapping("/newBooks")
    @ResponseBody
    public Map newBooks() {
        Map map = bookService.newBooks();

        return map;
    }


    //连载精品
    @RequestMapping("/serialBooks")
    @ResponseBody
    public Map serialBooks() {
        Map map = bookService.serialBooks();
        return map;
    }

    //完结精品
    @RequestMapping("/completionBooks")
    @ResponseBody
    public Map completionBooks() {
        Map map = bookService.completionBooks();
        return map;
    }

    //(书库)条件查询
    @RequestMapping("/selectConditionBooks")
    @ResponseBody
    public Map selectConditionBooks(ConditionDTO conditionDTO) {
        System.out.println("conditionDTO >==> " + conditionDTO);
        Map map = bookService.selectConditionBooks(conditionDTO);
        return map;
    }

    //(搜索)条件查询
    @RequestMapping("/seekBooks")
    @ResponseBody
    public Map seekBooks(String name) {
        Map map = bookService.seekBooks(name);
        return map;
    }

    //热门搜索
    @RequestMapping("/popularityBooks")
    @ResponseBody
    public Map popularityBooks() {
        Map map = bookService.popularityBooks();
        return map;
    }

}
