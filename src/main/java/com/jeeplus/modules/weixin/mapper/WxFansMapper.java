/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.weixin.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.weixin.entity.WxFans;

import java.util.List;

/**
 * 微信粉丝MAPPER接口
 * @author toteny
 * @version 2018-07-14
 */
@MyBatisMapper
public interface WxFansMapper extends BaseMapper<WxFans> {
    WxFans getLastOpenId();

    WxFans getByOpenId(String openId);
    /**
     * 统计性别和语言
     *
     * @return
     */
    WxFans getFansBySex();

    /**
     * 根据省分组统计
     *
     * @return
     */

    List<WxFans> getFansListByProvince();

    void updateStatus(WxFans wxFans);

    List<WxFans> getListCountry();
}