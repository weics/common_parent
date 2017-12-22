package com.itheima.bos.web.action.base;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.service.base.AreaService;
import com.itheima.bos.utils.PinYin4jUtils;
import com.itheima.bos.web.action.common.CommonAction;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
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
import java.util.List;

@Namespace(value = "/")
@ParentPackage(value = "struts-default")
@Controller
@Scope(value = "prototype")
public class AreaAction extends CommonAction<Area> {
    @Autowired
    private AreaService service;

    //属性驱动,接收页面上传的文件
    private File areaFile;

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
        Pageable pageable = new PageRequest(getPage() - 1, getRows());

        //调用service进行查询
        Page<Area> page = service.pageQuery(pageable);

        page2json(page, new String[]{"subareas"});

        return NONE;
    }

    //查询所有区域
    @Action(value = "areaAction_listajax")
    public String listajax() throws Exception {
        List<Area> list = service.findAll();
        list2json(list, new String[]{"subareas","privince","city","district","shortcode","postcode"});
        return NONE;
    }
}
