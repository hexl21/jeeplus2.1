package com.jeeplus.modules.books.service;

import com.jeeplus.modules.books.entity.ConditionDTO;

import java.util.Map;

public interface BookService {
    //轮播图
    public Map slideshowBooks();


    //主编推荐
    public Map recommendBooks();

    //新书上架
    public Map newBooks();

    //连载精品
    public Map serialBooks();

    //完结精品
    public Map completionBooks();

    //(书库)条件查询
    public Map selectConditionBooks(ConditionDTO conditionDTO);

    //(搜索)条件查询
    public Map seekBooks(String name);

    //热门搜索
    public Map popularityBooks();

}
