package com.blond.dao;

import com.blond.entity.PageResult;
import com.blond.pojo.CheckItem;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * 检查项持久层
 * @author Blonde
 * @program: blond_health
 * @create 2021-08-01 1:16
 */
public interface CheckItemDao {

    public void add(CheckItem checkItem);

    public Page<CheckItem> selectByCondition(String queryString);

    public long findCountByCheckItemId(Integer id);

    public void deleteById(Integer id);

    public void edit(CheckItem checkItem);

    public CheckItem findById(Integer id);

    public List<CheckItem> findAll();
}
