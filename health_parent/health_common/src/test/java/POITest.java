import com.blond.utils.DateUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Blonde
 * @program: blond_health
 * @create 2021-08-05 2:40
 */
public class POITest {
/*
    // POI读取Excel -- 正向遍历
    // 核心类：XSSFWorkbook、XSSFSheet、Row、Cell
    @Test
    public void test1() throws IOException {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(new File("D:\\JavaStudy\\test1.xlsx")));
        XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
        for (Row row : sheet) {
            for (Cell cell : row) {
                System.out.println(cell.getStringCellValue());
            }
        }
        xssfWorkbook.close();
    }

    // POI读取Excel -- 获取最后一行号和最后一列号来遍历
    @Test
    public void test2() throws IOException {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(new File("D:\\JavaStudy\\test1.xlsx")));
        XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
        // getLastRowNum从0开始算，即两行数据，得到的lastRowNum为1
        int lastRowNum = sheet.getLastRowNum();
        for (int row = 0; row <= lastRowNum; row++) {
            XSSFRow sheetRow = sheet.getRow(row);
            //getLastCellNum从1开始算
            short lastCellNum = sheetRow.getLastCellNum();
            for (int cell = 0; cell < lastCellNum; cell++) {
                XSSFCell cell1 = sheetRow.getCell(cell);
                System.out.println(cell1.getStringCellValue());
            }
        }
        xssfWorkbook.close();
    }

    // POI写入数据到excel
    @Test
    public void test3() throws IOException {
        //在内存创建一个Excel文件
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        //创建工作表，指定工作表名称
        XSSFSheet sheet = xssfWorkbook.createSheet("测试");
        //创建行，0表示第一行
        XSSFRow row = sheet.createRow(0);
        //创建单元格，0表示第一个单元格
        row.createCell(0).setCellValue("编号");
        row.createCell(1).setCellValue("名称");
        row.createCell(2).setCellValue("年龄");

        XSSFRow row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("1");
        row1.createCell(1).setCellValue("小明");
        row1.createCell(2).setCellValue("10");

        XSSFRow row2 = sheet.createRow(2);
        row2.createCell(0).setCellValue("2");
        row2.createCell(1).setCellValue("小王");
        row2.createCell(2).setCellValue("20");

        FileOutputStream fileOutputStream = new FileOutputStream(new File("D:\\JavaStudy\\test2.xlsx"));
        xssfWorkbook.write(fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
        xssfWorkbook.close();
    }
    */


   /* @Test
    public void test1() throws Exception {
        Date date = new Date();
        System.out.println(date);


    }*/
}
