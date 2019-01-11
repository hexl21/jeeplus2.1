package com.jeeplus.modules.book_category.service.Impl;

import com.jeeplus.modules.book_category.entity.Category;
import com.jeeplus.modules.book_category.mapper.CategorysMapper;
import com.jeeplus.modules.book_category.service.CategorysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategorysServiceImpl implements CategorysService {
    @Autowired
    private CategorysMapper categorysMapper;

    @Override
    public Map selectAllCategory() {
        Map map = new HashMap();
        List<Category> categories = categorysMapper.selectAllCategory();
        map.put("rows", categories);
        return map;
    }
}
