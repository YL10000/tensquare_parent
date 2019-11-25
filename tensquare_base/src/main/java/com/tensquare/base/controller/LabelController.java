package com.tensquare.base.controller;


import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/label")
//允许跨域访问
@CrossOrigin
public class LabelController {

    @Autowired
    private LabelService labelService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(method = RequestMethod.GET)
    public Result<List> findAll(){
        redisTemplate.opsForValue().set("yanglin",456,10, TimeUnit.SECONDS);
        return Result.of(true, StatusCode.OK,"查询成功",labelService.findAll());
    }

    /**
     * 获取指定id的label
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result<Label> findById(@PathVariable String id){
        return Result.of(true,StatusCode.OK,"查询成功",labelService.findById(id));
    }

    /**
     * 新增标签
     * @param label
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Label label){
        labelService.add(label);
        return Result.of(true, StatusCode.OK,"新增成功");
    }

    /**
     * 修改标签
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Result<Label> update(@RequestBody Label label, @PathVariable String id){
        label.setId(id);
        labelService.update(label);
        return Result.of(true,StatusCode.OK,"修改成功");
    }

    /**
     * 删除标签
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String id){
        labelService.deleteById(id);
        return Result.of(true,StatusCode.OK,"删除成功");
    }

    /**
     * 按条件查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public Result<List> findAll(@RequestBody Map params){
        return Result.of(true,StatusCode.OK,"查询完成",labelService.findSearch(params));
    }

    /**
     * 分页查询符合条件的标签
     * @param params
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/search/{page}/{size}",method = RequestMethod.POST)
    public Result<PageResult> findAll(@RequestBody Map params,@PathVariable int page,@PathVariable int size){
        Page pageList=labelService.findSearch(params,page,size);
        return Result.of(true,StatusCode.OK,"查询完成",new PageResult<>(pageList.getTotalElements(),pageList.getContent()));
    }
}
