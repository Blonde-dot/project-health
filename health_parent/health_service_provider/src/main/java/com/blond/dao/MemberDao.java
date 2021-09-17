package com.blond.dao;

import com.blond.pojo.Member;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * @author Blonde
 * @program: blond_health
 * @create 2021-08-08 2:13
 */
public interface MemberDao {

    public List<Member> findAll();
    public Page<Member> selectByCondition(String queryString);
    public void add(Member member);
    public void deleteById(Integer id);
    public Member findById(Integer id);
    public Member findByTelephone(String phoneNumber);
    public void edit(Member member);
    public Integer findMemberCountBeforeDate(String date);
    public Integer findMemberCountByDate(String date);
    public Integer findMemberCountAfterDate(String date);
    public Integer findMemberTotalCount();
    public String findImgByTelephone(String telephone);
    public void updatePasswordByPhoneNumber(Member member);
}

