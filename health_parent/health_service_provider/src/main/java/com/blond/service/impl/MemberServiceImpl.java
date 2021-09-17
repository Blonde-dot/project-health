package com.blond.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.blond.dao.MemberDao;
import com.blond.entity.PageResult;
import com.blond.entity.QueryPageBean;
import com.blond.pojo.Member;
import com.blond.service.MemberService;
import com.blond.utils.DateUtils;
import com.blond.utils.MD5Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.tags.ParamAware;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 会员服务实现
 * @author Blonde
 * @program: blond_health
 * @create 2021-08-08 23:28
 */
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    /**
     * 根据电话号码查询会员
     * @param telephone
     * @return com.blond.pojo.Member
     */
    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    /**
     * 注册会员
     * @param member
     * @return void
     */
    @Override
    public void add(Member member) {
        // 注册会员三种情况：
        // 系统自动注册，只有基本信息：电话、日期
        // 后台自动注册
        // 用户自己注册，包含密码
        /*String password = member.getPassword();*/

       /* if(password !=null){ // 用户提交了密码
            // 使用md5对用户提交明文密码进行加密
            password = MD5Utils.md5(password);
            member.setPassword(password);
        }*/
        memberDao.add(member);
    }

    /**
     * 根据月份查询当月会员总数
     * @param months
     * @return java.util.List<java.lang.Integer>
     */
    @Override
    public List<Integer> findMemberCountByMonth(List<String> months) {
        List<Integer> memberCount = new ArrayList<>();
        for (String month : months) {
            String date = month + "-31"; // yyyy-mm-dd
            Integer count = memberDao.findMemberCountBeforeDate(date);
            memberCount.add(count);
        }

        return memberCount;
    }

    /**
     * 分页查询会员信息
     * @param queryPageBean
     * @return com.blond.entity.PageResult
     */
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) throws Exception {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();

        PageHelper.startPage(currentPage,pageSize);
        Page<Member> members = memberDao.selectByCondition(queryString);
        long total = members.getTotal();
        List<Member> result = members.getResult();
        /*for (Member member : result) {
            if(member.getBirthday() != null){
                String birthDay = DateUtils.parseDate2String(member.getBirthday());
            }
            if(member.getRegTime() != null){
                String formatRegTime = new SimpleDateFormat("yyyy-MM-dd").format(member.getRegTime());
                String[] split = formatRegTime.split(" ");
                String regTime = split[0];
                member.setRegTime(DateUtils.parseString2Date(regTime));
            }
        }*/
        return new PageResult(total,result);
    }

    /**
     * 根据Id查询会员信息
     * @param id
     * @return com.blond.pojo.Member
     */
    @Override
    public Member findById(Integer id) {

        return memberDao.findById(id);
    }

    /**
     * 编辑会员信息
     * @param member
     * @return void
     */
    @Override
    public void edit(Member member) {
        memberDao.edit(member);
    }

    /**
     * 根据id删除会员信息
     * @param id
     * @return void
     */
    @Override
    public void delete(Integer id) {

        memberDao.deleteById(id);
    }

    /**
     * 获取会员总数
     * @param
     * @return java.lang.Integer
     */
    @Override
    public Integer getMemberCount() {
        return memberDao.findMemberTotalCount();
    }

    /**
     * 查询单日新增会员
     * @param
     * @return java.lang.Integer
     */
    @Override
    public Integer getTodayMember() throws Exception {

        String date = DateUtils.parseDate2String(new Date());
        return memberDao.findMemberCountByDate(date);
    }

    /**
     * 获取用户头像
     * @param telephone
     * @return java.lang.String
     */
    @Override
    public String getImgById(String telephone) {
        return memberDao.findImgByTelephone(telephone);
    }

    /**
     * 更新用户密码
     * @param telephone
     * @param newPassword
     * @return void
     */
    @Override
    public void updatePassword(String telephone, String newPassword) {
        Member member = new Member();
        member.setPhoneNumber(telephone);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodePassword = bCryptPasswordEncoder.encode(newPassword);
        member.setPassword(encodePassword);
        memberDao.updatePasswordByPhoneNumber(member);
    }
}
