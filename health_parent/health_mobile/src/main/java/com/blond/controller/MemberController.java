package com.blond.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.blond.constant.MessageConstant;
import com.blond.constant.RedisConstant;
import com.blond.constant.RedisMessageConstant;
import com.blond.entity.Result;
import com.blond.pojo.Member;
import com.blond.pojo.Menu;
import com.blond.service.MemberService;
import com.blond.utils.DateUtils;
import com.blond.utils.QiniuUtils;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 会员管理
 * @author Blonde
 * @program: blond_health
 * @create 2021-08-08 23:17
 */
@RestController
@RequestMapping("/member")
@SuppressWarnings("unchecked")
public class MemberController {

    @Reference
    private MemberService memberService;
    @Autowired
    private JedisPool jedisPool;


    @RequestMapping("/regist")
    public Result regist(HttpServletResponse response, HttpSession httpSession, @RequestBody Map map){
        String telephone = (String) map.get("telephone");
        String code = (String) map.get("validateCode");
        String  password = (String) map.get("password");
        Jedis resource = jedisPool.getResource();
        // 获取服务器发送的验证码（Redis中）
        String validateCode = resource.get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
        if(validateCode == null || !validateCode.equals(code)) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }else{
            // 判断当前用户是否已注册会员
            try{
                // 根据用户手机号码查询会员
                Member member = memberService.findByTelephone(telephone);
                if(member == null){ // 会员不存在，自动为当前用户注册会员
                    // 添加基础信息
                    member = new Member();
                    member.setRegTime(DateUtils.parseDate2String(new Date()));
                    member.setPhoneNumber(telephone);
                    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                    String encodePassword = bCryptPasswordEncoder.encode(password);
                    member.setPassword(encodePassword);
                    // 注册会员
                    memberService.add(member);
                }
                // 登陆成功
                //向客户端写入cookie，用于跟踪用户
                Cookie cookie = new Cookie("login_member_telephone",telephone);
                cookie.setPath("/");
                cookie.setMaxAge(60*60*24*30);
                response.addCookie(cookie);
                /*httpSession.setAttribute("Member",member);*/
                String json = JSON.toJSON(member).toString();
                resource.setex(telephone,60*30,json);
                return new Result(true,MessageConstant.ADD_MEMBER_SUCCESS);
            }catch (Exception e){
                e.printStackTrace();
                return new Result(false,MessageConstant.ADD_MEMBER_FAIL);
            }finally {
                resource.close();
            }
        }
    }

    @RequestMapping("/edit4Member")
    public Result edit4Member(@RequestBody Member member,HttpSession httpSession){
        try{
            org.springframework.security.core.userdetails.User loginMember =
                    (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(member.getBirthday() !=null){
                Date date = DateUtils.parseString2Date(member.getBirthday(), "yyyy-MM-dd");
                String birDate = DateUtils.parseDate2String(date);
                member.setBirthday(birDate);
            }
            memberService.edit(member);
            return new Result(true,MessageConstant.EDIT_MEMBER_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_MEMBER_FAIL);
        }
    }

    @RequestMapping("/findMemberById")
    public Result findMemberById(HttpSession httpSession){
        try{
            // 当SpringSecurity完成用户认证后，会将用户信息存储到框架提供的上下文对象中（基于session实现）
            // .getContext().getAuthentication().getPrincipal()返回框架提供的User类型，而不是自定义的User
            org.springframework.security.core.userdetails.User loginMember =
                    (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String telephone = loginMember.getUsername();
            Member member = memberService.findByTelephone(telephone);
            return new Result(true,MessageConstant.GET_MEMBER_SUCCESS,member);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_MEMBER_FAIL);
        }

    }

    @RequestMapping("/getMembername")
    public Result getMembername(){
        try{
            org.springframework.security.core.userdetails.User loginMember =
                    (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String telephone = loginMember.getUsername();
            String img = memberService.getImgById(telephone);
            return new Result(true,MessageConstant.GET_IMG_SUCCESS,img);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_IMG_FAIL);
        }


    }

    @RequestMapping("/getMember")
    public Result getMember(){
        try{
            org.springframework.security.core.userdetails.User loginMember =
                    (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Member member = memberService.findByTelephone(loginMember.getUsername());
            Map resultMap = new HashMap();
            resultMap.put("fileNumber",member.getFileNumber());
            resultMap.put("name",member.getName());
            resultMap.put("sex",member.getSex());
            resultMap.put("weight",member.getWeight());
            resultMap.put("height",member.getHeight());
            resultMap.put("birthday",member.getBirthday());
            return new Result(true,MessageConstant.GET_MEMBER_SUCCESS,resultMap);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_MEMBER_FAIL);
        }

    }

    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile")MultipartFile imgFile){
        // 获取原生名文件
        String originalFilename = imgFile.getOriginalFilename();
        // 截取后缀
        int index = originalFilename.lastIndexOf("."); // 获取 最后一个 . 的位置 即后缀名的起始位置
        String Extention = originalFilename.substring(index - 1);
        // 随机生成新的文件名并拼接上后缀名
        String fileName = UUID.randomUUID().toString() + Extention;

        Jedis resource = jedisPool.getResource();
        // 调用七牛云工具类，将文件上传到七牛云
        try {
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            // 文件上传的同时，将图片名称添加Redis中
            resource.sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
        } catch (IOException e) { // 上传失败
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }finally {
            resource.close();
        }
        return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
    }

    @RequestMapping("updatePassword")
    public Result updatePassword(@RequestBody Map map){
        try{
            org.springframework.security.core.userdetails.User loginMember =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String oldPassword = (String) map.get("oldPassword");
            String newPassword = (String) map.get("newPassword");
            Member member = memberService.findByTelephone(loginMember.getUsername());
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String oldPw = member.getPassword();
            if(!bCryptPasswordEncoder.matches(oldPassword,oldPw)){
                return new Result(false,MessageConstant.PASSWORD_NOT_SAME);
            }
            String phoneNumber = member.getPhoneNumber();
            memberService.updatePassword(phoneNumber,newPassword);
            return new Result(true,MessageConstant.EDIT_PASSWORD_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_PASSWORD_FAIL);
        }

    }
}
