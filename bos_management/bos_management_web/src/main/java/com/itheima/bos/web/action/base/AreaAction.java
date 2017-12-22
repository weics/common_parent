package com.itheima.bos.web.action.base;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.service.base.AreaService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

@Namespace(value = "/")
@ParentPackage(value = "struts-default")
@Controller
@Scope(value = "prototype")
public class AreaAction extends ActionSupport implements ModelDriven<Area> {
    private Area model = new Area();

    @Autowired
    private AreaService service;

    @Override
    public Area getModel() {
        return model;
    }

    //属性驱动,接收页面上传的文件
    private File areaFile;

    public void setAreaFile(File areaFile) {
        this.areaFile = areaFile;
    }

    //区域数据批量导入

    @Action(value = "areaAction_importAreaXls")
    public String execute() throws Exception {
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
            list.add(area);
        }
        service.save(list);
        return NONE;
    }
}
