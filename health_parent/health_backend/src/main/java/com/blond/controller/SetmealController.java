package com.blond.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.blond.constant.MessageConstant;
import com.blond.constant.RedisConstant;
import com.blond.entity.PageResult;
import com.blond.entity.QueryPageBean;
import com.blond.entity.Result;
import com.blond.pojo.Setmeal;
import com.blond.service.SetmealService;
import com.blond.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Client;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * 体检套餐管理
 * @author Blonde
 * @program: blond_health
 * @create 2021-08-04 23:12
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    // 使用JedisPool操作Redis
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile")MultipartFile imgFile){
        Jedis resource = jedisPool.getResource();
        // 获取原生名文件
        String originalFilename = imgFile.getOriginalFilename();
        // 截取后缀
        int index = originalFilename.lastIndexOf("."); // 获取 最后一个 . 的位置 即后缀名的起始位置
        String Extention = originalFilename.substring(index - 1);
        // 随机生成新的文件名并拼接上后缀名
        String fileName = UUID.randomUUID().toString() + Extention;

        // 调用七牛云工具类，将文件上传到七牛云
        try {
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            // 文件上传的同时，将图片名称添加Redis中,
            resource.sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
        } catch (IOException e) { // 上传失败
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }finally {
            resource.close();
        }
        return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
    }


    @Reference
    private SetmealService setmealService;

    @PreAuthorize("hasAuthority('SETMEAL_ADD')")
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds){
        try {
            setmealService.add(setmeal,checkgroupIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return setmealService.pageQuery(queryPageBean);
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        try{
            Setmeal setmeal = setmealService.findById(id);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

    @RequestMapping("/getImg")
    public Result getImg(Integer id){
        try{
            String imgUrl = setmealService.findImgById(id);
            return new Result(true,MessageConstant.GET_IMG_SUCCESS,imgUrl);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_IMG_FAIL);
        }
    }

    @RequestMapping("/findCheckGroupIdsById")
    public Result findCheckGroupIdsById(Integer id){
        try{
            List<Integer> checkgroupIds = setmealService.findCheckGroupIdsById(id);
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkgroupIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    @PreAuthorize("hasAuthority('SETMEAL_EDIT')")
    @RequestMapping("/edit")
    public Result edit(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        try{
            setmealService.edit(setmeal,checkgroupIds);
            return new Result(true,MessageConstant.EDIT_SETMEAL_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_SETMEAL_FAIL);
        }
    }

    @PreAuthorize("hasAuthority('SETMEAL_DELETE')")
    @RequestMapping("/delete")
    public Result delete(Integer id){
        try{
            setmealService.delete(id);
            return new Result(true,MessageConstant.DELETE_SETMEAL_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_SETMEAL_FAIL);
        }
    }
}
