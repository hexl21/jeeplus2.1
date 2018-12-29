/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.weixin.service;

import java.util.List;

import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.weixin.entity.WxMsgBaseSub;
import com.jeeplus.modules.weixin.mapper.WxMsgBaseSubMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.weixin.entity.WxMsgBase;
import com.jeeplus.modules.weixin.mapper.WxMsgBaseMapper;

/**
 * 微信消息Service
 *
 * @author toteny
 * @version 2018-07-14
 */
@Service
@Transactional(readOnly = true)
public class WxMsgBaseService extends CrudService<WxMsgBaseMapper, WxMsgBase> {
    @Autowired
    private WxMsgBaseSubMapper subMapper;

    public WxMsgBase get(String id) {
        WxMsgBase base = super.get(id);
        WxMsgBaseSub sub = new WxMsgBaseSub();
        sub.setBaseId(id);
        List<WxMsgBaseSub> subList = subMapper.findList(sub);
        base.setSubList(subList);
        return base;
    }

    public List<WxMsgBase> findList(WxMsgBase wxMsgBase) {
        return super.findList(wxMsgBase);
    }

    public Page<WxMsgBase> findPage(Page<WxMsgBase> page, WxMsgBase wxMsgBase) {
        return super.findPage(page, wxMsgBase);
    }

    @Transactional(readOnly = false)
    public void save(WxMsgBase wxMsgBase) {
        if (StringUtils.isBlank(wxMsgBase.getId())) {
            wxMsgBase.preInsert();
            mapper.insert(wxMsgBase);
        } else {
            wxMsgBase.preUpdate();
            mapper.update(wxMsgBase);
        }
        for (WxMsgBaseSub sub : wxMsgBase.getSubList()) {
            if (sub.getId() == null)
                continue;
            if (WxMsgBaseSub.DEL_FLAG_NORMAL.equals(sub.getDelFlag())) {
                sub.setBaseId(wxMsgBase.getId());
                if (StringUtils.isBlank(sub.getId())) {
                    sub.preInsert();
                    subMapper.insert(sub);
                } else {
                    sub.preUpdate();
                    subMapper.update(sub);
                }
            } else {
                subMapper.delete(sub);
            }
        }
    }

    @Transactional(readOnly = false)
    public void delete(WxMsgBase wxMsgBase) {

        WxMsgBaseSub sub = new WxMsgBaseSub();
        sub.setBaseId(wxMsgBase.getId());
        List<WxMsgBaseSub> subList = subMapper.findList(sub);
        for (WxMsgBaseSub s : subList) {
            subMapper.delete(s);
        }
        super.delete(wxMsgBase);
    }

    public WxMsgBase getByCode(WxMsgBase base) {
        base = mapper.getByCode(base);
        WxMsgBaseSub sub = new WxMsgBaseSub();
        sub.setBaseId(base.getId());
        List<WxMsgBaseSub> subList = subMapper.findList(sub);
        base.setSubList(subList);
        return base;

    }
}