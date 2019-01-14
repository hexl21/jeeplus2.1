/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ebook.web;

import com.google.common.collect.Lists;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.ebook.entity.SnowflakeIdWorker;
import com.jeeplus.modules.ebook.entity.Users;
import com.jeeplus.modules.ebook.service.UsersService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;

import static com.jeeplus.modules.ebook.utils.MD5.encodeMD5;

/**
 * 管理用户Controller
 *
 * @author 高龙
 * @version 2019-01-12
 */
@Controller
@RequestMapping(value = "${adminPath}/ebook/users")
public class UsersController extends BaseController {

    @Autowired
    private UsersService usersService;

    @ModelAttribute
    public Users get(@RequestParam(required = false) String id) {
        Users entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = usersService.get(id);
        }
        if (entity == null) {
            entity = new Users();
        }
        return entity;
    }

    /**
     * 管理用户列表页面
     */
    @RequiresPermissions("ebook:users:list")
    @RequestMapping(value = {"list", ""})
    public String list(Users users, Model model) {
        model.addAttribute("users", users);
        return "modules/ebook/usersList";
    }

    /**
     * 管理用户列表数据
     */
    @ResponseBody
    @RequiresPermissions("ebook:users:list")
    @RequestMapping(value = "data")
    public Map<String, Object> data(Users users, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Users> page = usersService.findPage(new Page<Users>(request, response), users);
        return getBootstrapData(page);
    }

    /**
     * 查看，增加，编辑管理用户表单页面
     */
    @RequiresPermissions(value = {"ebook:users:view", "ebook:users:add", "ebook:users:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(Users users, Model model) {
        model.addAttribute("users", users);
        return "modules/ebook/usersForm";
    }

    /**
     * 保存管理用户
     */
    @ResponseBody
    @RequiresPermissions(value = {"ebook:users:add", "ebook:users:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public AjaxJson save(Users users, Model model) throws Exception {

        //=====================(后添的代码)=======================
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        long id = idWorker.nextId();
        users.setUserid(id + "");//获取用户id
        String password = users.getPassword();
        String md5 = encodeMD5(password);
        users.setPassword(md5);//对密码进行MD5加密
        //====================(后添的代码END)=====================


        AjaxJson j = new AjaxJson();
        /**
         * 后台hibernate-validation插件校验
         */
        String errMsg = beanValidator(users);
        if (StringUtils.isNotBlank(errMsg)) {
            j.setSuccess(false);
            j.setMsg(errMsg);
            return j;
        }
        //新增或编辑表单保存
        usersService.save(users);//保存
        j.setSuccess(true);
        j.setMsg("保存管理用户成功");
        return j;
    }

    /**
     * 删除管理用户
     */
    @ResponseBody
    @RequiresPermissions("ebook:users:del")
    @RequestMapping(value = "delete")
    public AjaxJson delete(Users users) {
        AjaxJson j = new AjaxJson();
        usersService.delete(users);
        j.setMsg("删除管理用户成功");
        return j;
    }

    /**
     * 批量删除管理用户
     */
    @ResponseBody
    @RequiresPermissions("ebook:users:del")
    @RequestMapping(value = "deleteAll")
    public AjaxJson deleteAll(String ids) {
        AjaxJson j = new AjaxJson();
        String idArray[] = ids.split(",");
        for (String id : idArray) {
            usersService.delete(usersService.get(id));
        }
        j.setMsg("删除管理用户成功");
        return j;
    }

    /**
     * 导出excel文件
     */
    @ResponseBody
    @RequiresPermissions("ebook:users:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Users users, HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        try {
            String fileName = "管理用户" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<Users> page = usersService.findPage(new Page<Users>(request, response, -1), users);
            new ExportExcel("管理用户", Users.class).setDataList(page.getList()).write(response, fileName).dispose();
            j.setSuccess(true);
            j.setMsg("导出成功！");
            return j;
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg("导出管理用户记录失败！失败信息：" + e.getMessage());
        }
        return j;
    }

    /**
     * 导入Excel数据
     */
    @ResponseBody
    @RequiresPermissions("ebook:users:import")
    @RequestMapping(value = "import")
    public AjaxJson importFile(@RequestParam("file") MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<Users> list = ei.getDataList(Users.class);
            for (Users users : list) {
                try {
                    usersService.save(users);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    failureNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条管理用户记录。");
            }
            j.setMsg("已成功导入 " + successNum + " 条管理用户记录" + failureMsg);
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg("导入管理用户失败！失败信息：" + e.getMessage());
        }
        return j;
    }

    /**
     * 下载导入管理用户数据模板
     */
    @ResponseBody
    @RequiresPermissions("ebook:users:import")
    @RequestMapping(value = "import/template")
    public AjaxJson importFileTemplate(HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        try {
            String fileName = "管理用户数据导入模板.xlsx";
            List<Users> list = Lists.newArrayList();
            new ExportExcel("管理用户数据", Users.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg("导入模板下载失败！失败信息：" + e.getMessage());
        }
        return j;
    }

}