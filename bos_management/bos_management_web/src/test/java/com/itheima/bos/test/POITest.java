package com.itheima.bos.test;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;

public class POITest {
    //基于POI解析Excel,读取数据
    @Test
    public void test1() throws Exception {
        //加载本地磁盘物理文件
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File("E:\\develop\\com.itheima\\lv2\\07.Project\\01CRM\\速运快递\\速运快递项目全部资料和讲义\\资料\\day04\\03_区域测试数据\\区域导入测试数据.xls")));
        //读取第一个工作簿中的数据
        HSSFSheet sheet = workbook.getSheetAt(0);
        //遍历工作簿中所有的行
        for (Row row : sheet) {
            int rowNum = row.getRowNum();
            if (rowNum == 0) {
                continue;
            }
            //遍历行中的每个单元格
            for (Cell cell : row) {
                String value = cell.getStringCellValue();
                System.out.print(value);
            }
            System.out.println();
        }
    }
}
