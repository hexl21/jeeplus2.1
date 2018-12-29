/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.weixin.service;

import java.util.List;

import com.jeeplus.modules.weixin.entity.WxMsgNewsArticle;
import com.jeeplus.modules.weixin.mapper.WxMsgNewsArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.weixin.entity.WxMsgNews;
import com.jeeplus.modules.weixin.mapper.WxMsgNewsMapper;

/**
 * 图文信息Service
 *
 * @author toteny
 * @version 2018-07-28
 */
@Service
@Transactional(readOnly = true)
public class WxMsgNewsService extends CrudService<WxMsgNewsMapper, WxMsgNews> {
    @Autowired
    private WxMsgNewsArticleMapper articleMapper;

    public WxMsgNews get(String id) {
        WxMsgNews msgNews = super.get(id);
        WxMsgNewsArticle article = new WxMsgNewsArticle();
        article.setNewsId(id);
        List<WxMsgNewsArticle> articleList = articleMapper.findList(article);
        msgNews.setArticleList(articleList);
        return msgNews;
    }

    public List<WxMsgNews> findList(WxMsgNews wxMsgNews) {
        return super.findList(wxMsgNews);
    }

    public Page<WxMsgNews> findPage(Page<WxMsgNews> page, WxMsgNews wxMsgNews) {
        return super.findPage(page, wxMsgNews);
    }

    @Transactional(readOnly = false)
    public void save(WxMsgNews wxMsgNews) {
        super.save(wxMsgNews);
    }

    @Transactional(readOnly = false)
    public void delete(WxMsgNews wxMsgNews) {
        WxMsgNewsArticle article = new WxMsgNewsArticle();
        article.setNewsId(wxMsgNews.getId());
        List<WxMsgNewsArticle> list = articleMapper.findList(article);
        for (WxMsgNewsArticle a : list) {
            articleMapper.delete(a);
        }
        super.delete(wxMsgNews);
    }

}