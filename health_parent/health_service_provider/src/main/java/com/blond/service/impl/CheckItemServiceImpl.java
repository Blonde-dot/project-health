package com.blond.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.blond.dao.CheckItemDao;
import com.blond.entity.PageResult;
import com.blond.entity.QueryPageBean;
import com.blond.entity.Result;
import com.blond.pojo.CheckItem;
import com.blond.service.CheckItemService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.InterfaceMaker;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 检查项服务实现类
 * @author Blonde
 * @program: blond_health
 * @create 2021-08-01 1:14
 */
@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    /**
     * 添加检查项
     * @param checkItem
     * @return void
     */
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    /**
     * 检查项分页查询
     * @param queryPageBean
     * @return com.blond.entity.PageResult
     */
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        // 使用MyBatis提供的插件pageHelper实现分页查询
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckItem> checkItems = checkItemDao.selectByCondition(queryString);
        List<CheckItem> result = checkItems.getResult();
        long total = checkItems.getTotal();
        return new PageResult(total,result);
    }

    /**
     * 根据id删除检查项
     * @param id
     * @return com.blond.entity.Result
     */
    @Override
    public void deleteById(Integer id) {
        // 判断当前检查项是否已经关联到检查组
        long count = checkItemDao.findCountByCheckItemId(id);
        if(count>0){
            // 当前检查项已经关联了检查组，不允许删除
            new RuntimeException();
        }
        checkItemDao.deleteById(id);

    }

    /**
     * 编辑检查项
     * @param checkItem
     * @return void
     */
    @Override
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }

    /**
     * 根据ID查找检查项
     * @param id
     * @return com.blond.pojo.CheckItem
     */
    @Override
    public CheckItem findById(Integer id) {

        return checkItemDao.findById(id);
    }

    /**
     * 查找所有检查项信息
     * @param
     * @return java.util.List<com.blond.pojo.CheckItem>
     */
    @Override
    public List<CheckItem> findAll() {


        return checkItemDao.findAll();
    }

}
