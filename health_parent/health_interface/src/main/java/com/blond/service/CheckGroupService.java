package com.blond.service;

import com.blond.entity.PageResult;
import com.blond.entity.QueryPageBean;
import com.blond.pojo.CheckGroup;

import java.util.List;

/**
 * @author Blonde
 * @program: blond_health
 * @create 2021-08-03 2:41
 */
public interface CheckGroupService {

    public void add(CheckGroup checkGroup,Integer[] checkitemIds);

    public PageResult pageQuery(QueryPageBean queryPageBean);

    public CheckGroup findById(Integer id);

    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    public void edit(CheckGroup checkGroup,Integer[] checkitemIds);

    public List<CheckGroup> findAll();

    public void delete(Integer id);
}
