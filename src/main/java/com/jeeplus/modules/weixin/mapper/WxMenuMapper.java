/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.weixin.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.weixin.entity.WxMenu;

import java.util.List;

/**
 * 自定义菜单MAPPER接口
 *
 * @author toteny
 * @version 2018-08-01
 */
@MyBatisMapper
public interface WxMenuMapper extends BaseMapper<WxMenu> {
    List<WxMenu> findListByParentId(WxMenu wxMenu);
}