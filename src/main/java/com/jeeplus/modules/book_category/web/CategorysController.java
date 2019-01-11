package com.jeeplus.modules.book_category.web;

import com.jeeplus.modules.book_category.service.CategorysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class CategorysController {
    @Autowired
    private CategorysService categorysService;

    @RequestMapping("/selectAllCategory")
    @ResponseBody
    public Map selectAllCategory() {
        Map map = categorysService.selectAllCategory();
        return map;
    }
}
