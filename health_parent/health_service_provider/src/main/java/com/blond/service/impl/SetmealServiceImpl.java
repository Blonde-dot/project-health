package com.blond.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.blond.constant.RedisConstant;
import com.blond.dao.SetmealDao;
import com.blond.entity.PageResult;
import com.blond.entity.QueryPageBean;
import com.blond.pojo.Setmeal;
import com.blond.service.SetmealService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Blonde
 * @program: blond_health
 * @create 2021-08-05 0:10
 */
@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService{

    @Autowired
    private SetmealDao setmealDao;

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Value("${out_put_path}")
    private String outPutPath; // 根据属性文件读取生成的html对应目录

    /**
     * 新增套餐，关联检查组
     * @param setmeal
     * @param checkgroupIds
     * @return void
     */
    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        // 新增套餐
        setmealDao.add(setmeal);

        // 设置套餐和检查组的关联
        this.setSetmealAndCheckGroup(setmeal.getId(),checkgroupIds);

        // 新增套餐信息完成，将图片名称添加到Redis
        String fileName = setmeal.getImg();
        Jedis resource = jedisPool.getResource();
        try{
            resource.sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,fileName);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            resource.close();
        }


        //新增套餐后需要重新生成静态页面
        generateMobileStaticHtml();
    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return com.blond.entity.PageResult
     */
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> page = setmealDao.findByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 查找所有套餐
     * @param
     * @return java.util.List<com.blond.pojo.Setmeal>
     */
    @Override
    public List<Setmeal> findAll() {

        return setmealDao.findAll();
    }

    /**
     * 根据Id查找套餐
     * @param id
     * @return com.blond.pojo.Setmeal
     */
    @Override
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }

    @Override
    public String findNameById(Integer id) {
        return setmealDao.findNameById(id);
    }

    /**
     * 查询所有套餐名称以及对应的已预约次数，用于统计报表
     * @param
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @Override
    public List<Map<String, Object>> findSetmealCount() {
        return setmealDao.findSetmealCount();
    }

    /**
     * 根据套餐名查询套餐id
     * @param setmealName
     * @return java.lang.Integer
     */
    @Override
    public Integer findByName(String setmealName) {
        return setmealDao.findByName(setmealName);
    }

    /**
     * 根据套餐id查询出套餐图片
     * @param id
     * @return java.lang.String
     */
    @Override
    public String findImgById(Integer id) {

        return setmealDao.findImgById(id);
    }

    /**
     * 根据套餐id查询出套餐关联的检查组
     * @param id
     * @return java.util.List<java.lang.Integer>
     */
    @Override
    public List<Integer> findCheckGroupIdsById(Integer id) {
        return setmealDao.findCheckGroupIdsById(id);
    }

    /**
     * 编辑套餐
     * @param setmeal
     * @param checkgroupIds
     * @return void
     */
    @Override
    public void edit(Setmeal setmeal, Integer[] checkgroupIds) {

        // 编辑套餐的基本属性
        setmealDao.edit(setmeal);
        // 先清除关联
        setmealDao.deleteSetmealAndCheckgroupIds(setmeal.getId());
        // 设置套餐的关联检查组
        this.setSetmealAndCheckGroup(setmeal.getId(),checkgroupIds);

        // 将图片名称添加到Redis库中
        String filename = setmeal.getImg();
        Jedis resource = jedisPool.getResource();
        try{
            resource.sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,filename);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            resource.close();
        }


        // 重新生成移动端的静态页面
        generateMobileStaticHtml();
    }

    /**
     * 删除套餐
     * @param id
     * @return void
     */
    @Override
    public void delete(Integer id) {
        // 删除依赖的检查组
        setmealDao.deleteSetmealAndCheckgroupIds(id);
        // 删除套餐内
        setmealDao.deleteById(id);
    }

    /**
     * 公共方法：设置套餐和检查组的关联
     * 操作数据表 t_setmeal_checkgroup
     * @param setmealId
     * @param checkgroupIds
     * @return void
     */
    public void setSetmealAndCheckGroup(Integer setmealId,Integer[] checkgroupIds){
        if(checkgroupIds !=null && checkgroupIds.length > 0){
            for (Integer checkgroupId : checkgroupIds) {
                Map<String,Integer> map = new HashMap<>();
                map.put("setmealId",setmealId);
                map.put("checkgroupId",checkgroupId);
                setmealDao.setSetmealAndCheckGroup(map);
            }
        }
    }

    public void generateMobileStaticHtml(){
        List<Setmeal> list = setmealDao.findAll();
        // 生成套餐列表静态页面
        generateMobileSetmealListHtml(list);
        // 生成套餐详情静态页面
        generateMobileSetmealDetailHtml(list);
    }

    /**
     * 生成套餐列表静态页面
     * @param list
     * @return void
     */
    public void generateMobileSetmealListHtml(List<Setmeal> list){
        Map map = new HashMap();
        // 为模板提供数据，key根据模板文件中用于绑定值的名字命名
        map.put("setmealList",list);
        generateHtml("mobile_setmeal.ftl","m_setmeal.html",map);
    }

    /**
     * 生成套餐详情静态页面（多个）
     * @param list
     * @return void
     */
    public void generateMobileSetmealDetailHtml(List<Setmeal> list){

        for (Setmeal setmeal : list) {
            Map map = new HashMap();
            map.put("setmeal",setmealDao.findById(setmeal.getId()));
            generateHtml("mobile_setmeal_detail.ftl","setmeal_detail_"+setmeal.getId()+".html",map);
        }
    }

    /**
     * 通用--根据模板生成静态页面文件（套餐页面、套餐详情页）
     * @param templateName
     * @param htmlName
     * @param map
     * @return void
     */
    public void generateHtml(String templateName,String htmlName,Map map){
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Writer out = null;
        try {
            Template template = configuration.getTemplate(templateName);
            out = new FileWriter(new File(outPutPath+"/"+htmlName));
            template.process(map,out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }
}
