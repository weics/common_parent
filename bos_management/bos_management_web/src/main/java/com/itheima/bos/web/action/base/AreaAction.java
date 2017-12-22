package com.itheima.bos.web.action.base;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.service.base.AreaService;
import com.itheima.bos.utils.PinYin4jUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Namespace(value = "/")
@ParentPackage(value = "struts-default")
@Controller
@Scope(value = "prototype")
public class AreaAction extends ActionSupport implements ModelDriven<Area> {
    private Area model = new Area();

    @Autowired
    private AreaService service;

    //属性驱动,接收页面上传的文件
    private File areaFile;

    //通过属性驱动获取分页提交的参数page rows
    private int page;//页号
    private int rows;//页大小

    public void setPage(int page) {
        this.page = page;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    @Override
    public Area getModel() {
        return model;
    }

    public void setAreaFile(File areaFile) {
        this.areaFile = areaFile;
    }

    //区域数据批量导入

    @Action(value = "areaAction_importAreaXls")
    public String importAreaXls() throws Exception {
        //基于POI技术解析上传的文件
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(areaFile));

        //读取第一个工作簿
        HSSFSheet sheet = workbook.getSheetAt(0);

        ArrayList<Area> list = new ArrayList<>();
        //遍历行
        for (Row row : sheet) {
            //跳过第一行,表头
            if (row.getRowNum() == 0) {
                continue;
            }
            String id = row.getCell(0).getStringCellValue();
            String province = row.getCell(1).getStringCellValue();
            String city = row.getCell(2).getStringCellValue();
            String district = row.getCell(3).getStringCellValue();
            String postcode = row.getCell(4).getStringCellValue();
            Area area = new Area(id, province, city, district, postcode);

            //简码--->>HBSJZLS
            province = province.substring(0, province.length() - 1);
            city = city.substring(0, city.length() - 1);
            district = district.substring(0, district.length() - 1);
            String[] headByString = PinYin4jUtils.getHeadByString(province + city + district);
            String shortcode = StringUtils.join(headByString);
            area.setShortcode(shortcode);

            //城市编码--->>shijiazhuang
            String citycode = PinYin4jUtils.hanziToPinyin(city, "");
            area.setCitycode(citycode);

            list.add(area);
        }
        service.save(list);
        return NONE;
    }

    //区域分页查询
    @Action(value = "areaAction_pageQuery")
    public String pageQuery() throws Exception {
        //构造一个Pageable对象作为分页的参数,通过给Spring data jpa实现分页
        Pageable pageable = new PageRequest(page - 1, rows);

        //调用service进行查询
        Page<Area> page = service.pageQuery(pageable);

        //获取总数据量
        long total = page.getTotalElements();
        //获取当前页面要展示的数据
        List<Area> rows = page.getContent();

        //创建Map用于封装总量和页数据,转成json响应给页面
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", rows);

        //如何将page对象转为页面datagrid可以解析的json数据???
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"subareas"});
        String json = JSONObject.fromObject(map, jsonConfig).toString();

        //通过输出流将json数据响应到页面
        ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
        ServletActionContext.getResponse().getWriter().write(json);

        return NONE;
    }
}
