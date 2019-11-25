package com.tensquare.controller;

import com.tensquare.pojo.Recruit;
import com.tensquare.service.RecruitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.naming.Name;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/recurit")
public class RecuritController {

    @Autowired
    private RecruitService recruitService;

    /**
     * 查询全部数据
     * @return
     */
    @RequestMapping(method= RequestMethod.GET)
    public Result findAll(){
        return Result.of(true,StatusCode.OK,"查询成功",recruitService.findAll());
    }

    /**
     * 根据ID查询
     * @param id ID
     * @return
     */
    @RequestMapping(value="/{id}",method= RequestMethod.GET)
    public Result findById(@PathVariable String id){
        return Result.of(true,StatusCode.OK,"查询成功",recruitService.findById(id));
    }


    /**
     * 分页+多条件查询
     * @param searchMap 查询条件封装
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
        Page<Recruit> pageList = recruitService.findSearch(searchMap, page, size);
        return  Result.of(true,StatusCode.OK,"查询成功",  new PageResult<Recruit>(pageList.getTotalElements(), pageList.getContent()) );
    }

    /**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return Result.of(true,StatusCode.OK,"查询成功",recruitService.findSearch(searchMap));
    }

    /**
     * 增加
     * @param recruit
     */
    @RequestMapping(method=RequestMethod.POST)
    public Result add(@RequestBody Recruit recruit  ){
        recruitService.add(recruit);
        return Result.of(true,StatusCode.OK,"增加成功");
    }

    /**
     * 修改
     * @param recruit
     */
    @RequestMapping(value="/{id}",method= RequestMethod.PUT)
    public Result update(@RequestBody Recruit recruit, @PathVariable String id ){
        recruit.setId(id);
        recruitService.update(recruit);
        return Result.of(true, StatusCode.OK,"修改成功");
    }

    /**
     * 删除
     * @param id
     */
    @RequestMapping(value="/{id}",method= RequestMethod.DELETE)
    public Result delete(@PathVariable String id ){
        recruitService.deleteById(id);
        return Result.of(true,StatusCode.OK,"删除成功");
    }

    /**
     * 获取最近的推荐职位列表
     * @return
     */
    @RequestMapping(value = "/recommend",method = RequestMethod.GET)
    public Result<List> recommend(){
        return Result.of(true,StatusCode.OK,"查询成功",recruitService.findTop4ByStateOrderByCreatetimeDesc("1"));
    }

    /**
     * 根据名称查询职位
     * @param name
     * @return
     */
    @RequestMapping(value = "/search/{name}",method = RequestMethod.GET)
    public Result<List> findByName(@PathVariable String name){
        return Result.of(true,StatusCode.OK,"查询成功",recruitService.findByNameLike(name));
    }

}
