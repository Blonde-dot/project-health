package com.blond.dao;

import com.blond.pojo.CheckGroup;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * @author Blonde
 * @program: blond_health
 * @create 2021-08-03 2:46
 */
public interface CheckGroupDao {

    public void add(CheckGroup checkGroup);

    public void setCheckGroupAndCheckItem(Map<String,Integer> map);

    public Page<CheckGroup> findByCondition(String queryString);

    public CheckGroup findById(Integer id);

    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    public void edit(CheckGroup checkGroup);

    public void deleteAssociation(Integer id);

    public void deleteById(Integer id);

    public List<CheckGroup> findAll();
}
