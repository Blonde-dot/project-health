package com.blond.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.blond.dao.CheckGroupDao;
import com.blond.entity.PageResult;
import com.blond.entity.QueryPageBean;
import com.blond.pojo.CheckGroup;
import com.blond.service.CheckGroupService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 检查组服务
 * @author Blonde
 * @program: blond_health
 * @create 2021-08-03 2:44
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    /**
     * 新增检查组
     * 设置检查组和检查项之间的多对多关联关系
     * @param checkGroup
     * @param checkitemIds
     * @return void
     */
    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        // 新增检查组
        checkGroupDao.add(checkGroup);

        // 设置检查组和检查项之间的多对多关联关系，即添加数据到t_checkgroup_checkitem
        // 在dao映射文件中将自增id获取到并封装到当前checkGroup对象
        Integer checkGroupId = checkGroup.getId();
        this.setCheckGroupAndCheckItem(checkGroupId,checkitemIds);

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
        // 调用分页插件,通过拦截器拦截sql，并追加语句
        PageHelper.startPage(currentPage,pageSize);

        Page<CheckGroup> page = checkGroupDao.findByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 查询单个检查组
     * @param id
     * @return com.blond.pojo.CheckGroup
     */
    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    /**
     * 查询单个检查组关联的检查项
     * @param id
     * @return java.util.List<java.lang.Integer>
     */
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    /**
     * 编辑检查组
     * 设置检查组和检查项之间的多对多关联关系
     * @param checkGroup
     * @param checkitemIds
     * @return void
     */
    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        //修改检查组基本信息，针对t_checkgroup表
        checkGroupDao.edit(checkGroup);

        // 清理检查组关联检查项，针对t_checkgroup_checkitem表
        checkGroupDao.deleteAssociation(checkGroup.getId());

        // 根据传入检查项重新建立检查组跟检查项的关联，针对t_checkgroup_checkitem表
        Integer checkGroupId = checkGroup.getId();
        this.setCheckGroupAndCheckItem(checkGroupId,checkitemIds);

    }

    /**
     *
     * @param
     * @return java.util.List<com.blond.pojo.CheckGroup>
     */
    @Override
    public List<CheckGroup> findAll() {

        return checkGroupDao.findAll();
    }

    @Override
    public void delete(Integer id) {
        checkGroupDao.deleteAssociation(id);
        checkGroupDao.deleteById(id);
    }

    /**
     * 公共方法
     * 根据传入检查项重新建立检查组跟检查项的关联，针对t_checkgroup_checkitem表
     * @param checkGroupId
     * @param checkitemIds
     * @return void
     */
    public void setCheckGroupAndCheckItem(Integer checkGroupId,Integer[] checkitemIds){

        if(checkitemIds !=null && checkitemIds.length > 0){
            for (Integer checkitemId : checkitemIds) {
                Map<String,Integer> resultmap = new HashMap<>();
                resultmap.put("checkgroupId",checkGroupId);
                resultmap.put("checkitemId",checkitemId);
                checkGroupDao.setCheckGroupAndCheckItem(resultmap);
            }
        }
    }
}

