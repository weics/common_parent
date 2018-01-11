package com.itheima.bos.web.action.system;

import com.itheima.bos.domain.system.Menu;
import com.itheima.bos.service.system.MenuService;
import com.itheima.bos.web.action.common.CommonAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

@Namespace(value = "/")
@ParentPackage("struts-default")
@Scope("prototype")
@Controller
public class MenuAction extends CommonAction<Menu> {
    @Autowired
    private MenuService menuService;

    //查询所有菜单数据,返回json
    @Action(value = "menuAction_listajax")
    public String listajax() throws Exception {
        List<Menu> list = menuService.findTopMenu();//查询顶级菜单
        list2json(list, new String[]{"roles", "childrenMenus", "parentMenu"});
        return NONE;
    }

    //添加菜单数据
    @Action(value = "menuAction_save", results = {@Result(name = "success", type = "redirect", location = "/pages/system/menu.jsp")})
    public String save() throws Exception {
        menuService.save(getModel());
        return SUCCESS;
    }
}
