package com.blond.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.blond.constant.MessageConstant;
import com.blond.entity.Result;
import com.blond.service.*;
import com.blond.utils.POIUtils;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.security.spec.ECField;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 报表管理
 * @author Blonde
 * @program: blond_health
 * @create 2021-08-10 2:46
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private MemberService memberService;
    @Reference
    private SetmealService setmealService;
    @Reference
    private OrderService orderService;
    @Reference
    private UserService userService;
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/getMemberReport")
    public Result getMemberReport(){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            List<String> months = new ArrayList<>();
            Calendar calendar = Calendar.getInstance(); // 获得日历对象，即当前时间
            // 按照日历对象，计算出前一年，也就是12个月前对应的月份
            calendar.add(Calendar.MONTH,-12);
            // 循环12次，从12个月前遍历到当前月份
            for (int i = 0; i < 12; i++) {
                calendar.add(Calendar.MONTH,1);
                Date date = calendar.getTime();
                months.add(new SimpleDateFormat("yyyy-MM").format(date));
            }
            resultMap.put("months",months);

            // 根据月份查询会员数量
            List<Integer> memberCount = memberService.findMemberCountByMonth(months);
            resultMap.put("memberCount",memberCount);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,resultMap);
    }

    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport(){
        // 封装结果为Map
        Map<String,Object> resultMap = new HashMap<>();
        try{
            // 调用服务层，查询获得套餐名称以及对应的已预约数量
            List<Map<String,Object>> countList = setmealService.findSetmealCount();
            // 提取套餐名称
            List<String> nameList = new ArrayList<>();
            for (Map<String, Object> map : countList) {
                String  name = (String) map.get("name");
                nameList.add(name);
            }
            resultMap.put("setmealNames",nameList);
            resultMap.put("setmealCount",countList);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }
        return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,resultMap);
    }


    @Reference
    private ReportService reportService;

    @RequestMapping("getBusinessReportData")
    public Result getBusinessReportData(){
        try{
            Map<String,Object> resultMap = reportService.getBusinessReportData();
            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS,resultMap);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

    // 导出excel格式运营数据
    @RequestMapping("exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response){
        try{
            Map<String,Object> result = reportService.getBusinessReportData();
            String reportDate = (String) result.get("reportDate");
            Integer todayNewMember = (Integer) result.get("todayNewMember");
            Integer totalMember = (Integer) result.get("totalMember");
            Integer thisWeekNewMember = (Integer) result.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer) result.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer) result.get("todayOrderNumber");
            Integer thisWeekOrderNumber = (Integer) result.get("thisWeekOrderNumber");
            Integer thisMonthOrderNumber = (Integer) result.get("thisMonthOrderNumber");
            Integer todayVisitsNumber = (Integer) result.get("todayVisitsNumber");
            Integer thisWeekVisitsNumber = (Integer) result.get("thisWeekVisitsNumber");
            Integer thisMonthVisitsNumber = (Integer) result.get("thisMonthVisitsNumber");
            List<Map> hotSetmeal = (List<Map>) result.get("hotSetmeal");

            //基于excel模板文件 创建一个新的模板文件
            String filePath =
                    request.getSession().getServletContext().getRealPath("template")+ File.separator + "report_template.xlsx";
            XSSFWorkbook excel = new XSSFWorkbook(new FileInputStream(filePath));
            // 读取第一个工作表
            XSSFSheet sheet = excel.getSheetAt(0);
            // 根据模板文件，reportDate填充在第3行第6列
            XSSFRow row = sheet.getRow(2);
            XSSFCell cell = row.getCell(5);
            cell.setCellValue(reportDate);
            // 新增会员数填充在第5行第6列
            row = sheet.getRow(4);
            cell = row.getCell(5);
            cell.setCellValue(todayNewMember);
            row.getCell(7).setCellValue(totalMember);//总会员数

            row = sheet.getRow(5);
            row.getCell(5).setCellValue(thisWeekNewMember);//本周新增会员数
            row.getCell(7).setCellValue(thisMonthNewMember);//本月新增会员数

            row = sheet.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);//今日预约数
            row.getCell(7).setCellValue(todayVisitsNumber);//今日到诊数

            row = sheet.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);//本周预约数
            row.getCell(7).setCellValue(thisWeekVisitsNumber);//本周到诊数

            row = sheet.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);//本月预约数
            row.getCell(7).setCellValue(thisMonthVisitsNumber);//本月到诊数

            int rowNum = 12;
            for(Map map : hotSetmeal){//热门套餐
                String name = (String) map.get("name");
                Long setmeal_count = (Long) map.get("setmeal_count");
                BigDecimal proportion = (BigDecimal) map.get("proportion");
                row = sheet.getRow(rowNum ++);
                row.getCell(4).setCellValue(name);//套餐名称
                row.getCell(5).setCellValue(setmeal_count);//预约数量
                row.getCell(6).setCellValue(proportion.doubleValue());//占比
            }

            // 使用输出流进行文件下载
            OutputStream outputStream = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-Disposition", "attachment;filename=report.xlsx");
            excel.write(outputStream);

            outputStream.flush();
            outputStream.close();
            excel.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    // 导出PDF格式报表文件
    @RequestMapping("/exportBusinessReport4PDF")
    public Result exportBusinessReport4PDF(HttpServletRequest request, HttpServletResponse response) {
        try{
            Map<String,Object> result = reportService.getBusinessReportData();

            List<Map> hotSetmeal = (List<Map>) result.get("hotSetmeal");

            //基于PDF模板文件 创建一个新的模板文件
            String jrxmlPath =
                    request.getSession().getServletContext().getRealPath("template")+ File.separator + "health_business3.jrxml";
            String jasperPath =
                    request.getSession().getServletContext().getRealPath("template")+ File.separator + "health_business3.jasper";
            JasperCompileManager.compileReportToFile(jrxmlPath,jasperPath);

            // 以JavaBean形式填充数据
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperPath, result, new JRBeanCollectionDataSource(hotSetmeal));
            // 使用输出流进行文件下载
            OutputStream outputStream = response.getOutputStream();
            response.setContentType("application/pdf");
            response.setHeader("content-Disposition", "attachment;filename=report.pdf");

            JasperExportManager.exportReportToPdfStream(jasperPrint,outputStream);

            outputStream.flush();
            outputStream.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    @RequestMapping("/getBasicData")
    public Result getBasicData(){
        Jedis resource = jedisPool.getResource();
        try{
            Map<String,Object> resultMap = new HashMap<>();

            // 获取订单总数
            int orderCount = orderService.getOrderCount();
            // 获取会员总数
            int memberCount = memberService.getMemberCount();
            // 获取管理员总数
            int adminCount = userService.findAdminCount();
            // 获取健康管理人员总数
            int doctorCount = userService.findDoctorCount();

            // 获取当日新增订单数
            int todayOrder = orderService.getTodayOrder();
            // 获取今日到诊人数
            int todayArrive = orderService.getTodayArrive();
            // 获取当日新增会员
            int todayMember = memberService.getTodayMember();
            //jedisPool.getResource().auth("YBL411412");
            // 获取当日访问量
            int todayVisit = Integer.parseInt(resource.get("DailyVisit"));

            // 封装数据到结果集中
            resultMap.put("totalOrder",orderCount);
            resultMap.put("totalMember",memberCount);
            resultMap.put("totalUser",adminCount);
            resultMap.put("totalDoctor",doctorCount);
            resultMap.put("todayMember",todayMember);
            resultMap.put("todayArrive",todayArrive);
            resultMap.put("todayOrder",todayOrder);
            resultMap.put("todayVisit",todayVisit);

            return new Result(true,MessageConstant.GET_BASIC_DATA_SUCCESS,resultMap);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_BASIC_DATA_FAIL);
        }finally {
            resource.close();
        }
    }
}
