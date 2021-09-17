package com.blond.dao;

import com.blond.pojo.Setmeal;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * @author Blonde
 * @program: blond_health
 * @create 2021-08-05 0:11
 */
public interface SetmealDao {

    public void add(Setmeal setmeal);

    public void setSetmealAndCheckGroup(Map<String,Integer> checkgroupIds);

    public Page<Setmeal> findByCondition(String queryString);

    public List<Setmeal> findAll();

    public Setmeal findById(Integer id);

    public List<Map<String,Object>> findSetmealCount();

    public Integer findByName(String setmealName);

    public String findImgById(Integer id);

    public List<Integer> findCheckGroupIdsById(Integer id);

    public void edit(Setmeal setmeal);

    public void deleteSetmealAndCheckgroupIds(Integer id);

    public void deleteById(Integer id);

    public String findNameById(Integer id);
}
