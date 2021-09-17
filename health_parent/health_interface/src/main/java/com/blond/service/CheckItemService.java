package com.blond.service;

import com.blond.entity.PageResult;
import com.blond.entity.QueryPageBean;
import com.blond.entity.Result;
import com.blond.pojo.CheckItem;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 检查项服务接口
 * @author Blonde
 * @program: blond_health
 * @create 2021-08-01 1:06
 */
public interface CheckItemService {

    public void add(CheckItem checkItem);

    public PageResult pageQuery(QueryPageBean queryPageBean);

    public void deleteById(Integer id);

    public void edit(CheckItem checkItem);

    public CheckItem findById(Integer id);

    public List<CheckItem> findAll();
}
