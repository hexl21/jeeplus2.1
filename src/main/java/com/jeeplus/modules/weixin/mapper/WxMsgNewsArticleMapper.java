/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.weixin.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.weixin.entity.WxMsgNewsArticle;

/**
 * 图文信息MAPPER接口
 * @author toteny
 * @version 2018-07-14
 */
@MyBatisMapper
public interface WxMsgNewsArticleMapper extends BaseMapper<WxMsgNewsArticle> {
    WxMsgNewsArticle getArticle(WxMsgNewsArticle article);

    Long findListByArticleCount(WxMsgNewsArticle article);
}