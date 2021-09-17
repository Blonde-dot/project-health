package com.blond.service;

import com.blond.entity.PageResult;
import com.blond.entity.QueryPageBean;
import com.blond.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * @author Blonde
 * @program: blond_health
 * @create 2021-08-05 0:08
 */
public interface SetmealService {

    public void add(Setmeal setmeal, Integer[] checkgroupIds);

    public PageResult pageQuery(QueryPageBean queryPageBean);

    public List<Setmeal> findAll();

    public Setmeal findById(Integer id);

    public List<Map<String,Object>> findSetmealCount();
    public Integer findByName(String setmealName);

    public String findImgById(Integer id);

    public List<Integer> findCheckGroupIdsById(Integer id);

    public void edit(Setmeal setmeal,Integer[] checkgroupIds);

    public void delete(Integer id);

    public String findNameById(Integer id);
}
