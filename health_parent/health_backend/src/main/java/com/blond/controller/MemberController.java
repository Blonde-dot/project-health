package com.blond.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.blond.constant.MessageConstant;
import com.blond.entity.PageResult;
import com.blond.entity.QueryPageBean;
import com.blond.entity.Result;
import com.blond.pojo.Member;
import com.blond.service.MemberService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Blonde
 * @program: blond_health
 * @create 2021-08-14 16:35
 */
@RestController
@RequestMapping("/member")
public class MemberController {

    @Reference
    private MemberService memberService;

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) throws Exception {
        return memberService.pageQuery(queryPageBean);
    }

    @PreAuthorize("hasAuthority('MEMBER_ADD')")
    @RequestMapping("/add")
    public Result add(@RequestBody Member member){
        try{
            memberService.add(member);
            return new Result(true, MessageConstant.ADD_MEMBER_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_MEMBER_FAIL);
        }
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        try{
            Member member = memberService.findById(id);
            return new Result(true,MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,member);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
    }

    @PreAuthorize("hasAuthority('MEMBER_EDIT')")
    @RequestMapping("/edit")
    public Result edit(@RequestBody Member member){
        try{
            memberService.edit(member);
            return new Result(true,MessageConstant.EDIT_MEMBER_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_MEMBER_FAIL);
        }
    }

    @PreAuthorize("hasAuthority('MEMBER_DELETE')")
    @RequestMapping("/delete")
    public Result delete(Integer id){
        try{
            memberService.delete(id);
            return new Result(true,MessageConstant.DELETE_MEMBER_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_MEMBER_FAIL);
        }

    }
}
