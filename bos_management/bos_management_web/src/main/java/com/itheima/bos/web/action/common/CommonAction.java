package com.itheima.bos.web.action.common;

import com.itheima.bos.domain.base.Courier;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonAction<T> extends ActionSupport implements ModelDriven<T> {

    /**
     * 模型驱动代码抽取
     */

    //声明模型对象
    private T model;

    @Override
    public T getModel() {
        return model;
    }

    //无参构造方法,运行期Spring创建Action对象时会调用我们这个方法
    //在构造方法中动态获得泛型类型,通过反射创建model对象

    public CommonAction() {
        //在运行期动态获得父类(Common Action<T>)的类型
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();

        //获得类声明的泛型数组
        Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();

        Class<T> entityClass = (Class<T>) actualTypeArguments[0];

        try {
            model = entityClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 分页代码抽取
     */

    //通过属性驱动获取分页提交的参数page rows
    private int page;//页号
    private int rows;//页大小

    public void setPage(int page) {
        this.page = page;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void page2json(Page<T> page, String[] excludes) {
        //获取总数据量
        long total = page.getTotalElements();
        //获取当前页要展示的数据集合
        List<T> rows = page.getContent();

        //创建Map用于封装总量和页数据,转成json响应给页面
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", rows);

        //如何将page对象转为页面datagrid可以解析的json数据???
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(excludes);
        String json = JSONObject.fromObject(map, jsonConfig).toString();

        //通过输出流将json数据响应到页面
        ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
        try {
            ServletActionContext.getResponse().getWriter().write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
