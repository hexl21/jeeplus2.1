/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.weixin.service;

import java.util.List;

import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.weixin.entity.WxTplMsgSub;
import com.jeeplus.modules.weixin.mapper.WxTplMsgSubMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.weixin.entity.WxTplMsg;
import com.jeeplus.modules.weixin.mapper.WxTplMsgMapper;

/**
 * 模板消息Service
 *
 * @author toteny
 * @version 2018-07-14
 */
@Service
@Transactional(readOnly = true)
public class WxTplMsgService extends CrudService<WxTplMsgMapper, WxTplMsg> {

    @Autowired
    private WxTplMsgSubMapper subMapper;

    public WxTplMsg get(String id) {
        WxTplMsg msg = super.get(id);
        WxTplMsgSub sub = new WxTplMsgSub();
        sub.setTplId(id);
        List<WxTplMsgSub> subList = subMapper.findList(sub);
        msg.setSubList(subList);
        return super.get(id);
    }

    public List<WxTplMsg> findList(WxTplMsg wxTplMsg) {
        return super.findList(wxTplMsg);
    }

    public Page<WxTplMsg> findPage(Page<WxTplMsg> page, WxTplMsg wxTplMsg) {
        return super.findPage(page, wxTplMsg);
    }

    @Transactional(readOnly = false)
    public void save(WxTplMsg wxTplMsg) {
        if (StringUtils.isBlank(wxTplMsg.getId())) {
            wxTplMsg.preInsert();
            mapper.insert(wxTplMsg);
        } else {
            wxTplMsg.preUpdate();
            mapper.update(wxTplMsg);
        }
        for (WxTplMsgSub sub : wxTplMsg.getSubList()) {
            if (sub.getId() == null)
                continue;
            if (WxTplMsgSub.DEL_FLAG_NORMAL.equals(sub.getDelFlag())) {
                sub.setTplId(wxTplMsg.getId());
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
    public void delete(WxTplMsg wxTplMsg) {
        WxTplMsgSub sub = new WxTplMsgSub();
        sub.setTplId(wxTplMsg.getId());
        List<WxTplMsgSub> subList = subMapper.findList(sub);
        for (WxTplMsgSub s : subList) {
            subMapper.delete(s);
        }
        super.delete(wxTplMsg);
    }

}