package com.jeeplus.modules.books.service.impl;

import com.jeeplus.modules.books.entity.Books;
import com.jeeplus.modules.books.entity.ConditionDTO;
import com.jeeplus.modules.books.mapper.BookMapper;
import com.jeeplus.modules.books.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;

    //轮播图
    @Override
    public Map slideshowBooks() {
        Map map = new HashMap();
        List<Books> books = bookMapper.slideshowBooks();
        map.put("rows", books);
        return map;
    }

    //主编推荐
    @Override
    public Map recommendBooks() {
        Map map = new HashMap();
        List<Books> books = bookMapper.recommendBooks();
        map.put("rows", books);
        return map;
    }

    //新书上架
    @Override
    public Map newBooks() {
        Map map = new HashMap();
        List<Books> books = bookMapper.newBooks();
        map.put("rows", books);
        return map;
    }

    //连载精品
    @Override
    public Map serialBooks() {
        Map map = new HashMap();
        List<Books> books = bookMapper.serialBooks();
        map.put("rows", books);
        return map;
    }

    //完结精品
    @Override
    public Map completionBooks() {
        Map map = new HashMap();
        List<Books> books = bookMapper.completionBooks();
        map.put("rows", books);
        return map;
    }

    //(书库)条件查询
    @Override
    public Map selectConditionBooks(ConditionDTO conditionDTO) {
        Map map = new HashMap();
        List<Books> books = bookMapper.selectConditionBooks(conditionDTO);
        map.put("rows", books);
        return map;
    }

    //(搜索)条件查询
    @Override
    public Map seekBooks(String name) {
        Map map = new HashMap();
        String bname = null;
        if (name != null) {
            bname = "%" + name + "%";
        }
        List<Books> books = bookMapper.seekBooks("%" + name + "%");
        map.put("rows", books);
        return map;
    }

    @Override
    public Map popularityBooks() {
        Map map = new HashMap();
        List<Books> books = bookMapper.popularityBooks();
        map.put("rows", books);
        return map;
    }

}
