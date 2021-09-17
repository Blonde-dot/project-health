package com.blond.service;

import com.blond.entity.PageResult;
import com.blond.entity.QueryPageBean;
import com.blond.pojo.Member;

import java.util.List;

/**
 * @author Blonde
 * @program: blond_health
 * @create 2021-08-08 23:24
 */
public interface MemberService {
    public Member findByTelephone(String telephone);

    public void add(Member member);

    public List<Integer> findMemberCountByMonth(List<String > months);

    public PageResult pageQuery(QueryPageBean queryPageBean) throws Exception;

    public Member findById(Integer id);

    public void edit(Member member);

    public void delete(Integer id);

    public Integer getMemberCount();

    public String getImgById(String telephone);
    public Integer getTodayMember() throws Exception;

    public void updatePassword(String telephone,String newPassword);
}
