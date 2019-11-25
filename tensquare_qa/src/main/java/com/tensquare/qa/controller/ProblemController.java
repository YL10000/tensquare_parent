package com.tensquare.qa.controller;

import com.tensquare.qa.pojo.Problem;
import com.tensquare.qa.service.ProblemService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/problem")
@CrossOrigin
public class ProblemController {

    @Autowired
    ProblemService problemService;

    /**
     * 查询该标签下最新的问题
     * @param labelId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/newList/{labelId}/{page}/{size}",method = RequestMethod.GET)
    public Result<PageResult> newList(@PathVariable String labelId, @PathVariable int page, @PathVariable int size){
        Page pageList=problemService.newList(labelId,page,size);
        return Result.of(true, StatusCode.OK,"查询成功",new PageResult(pageList.getTotalElements(),pageList.getContent()));
    }

    /**
     * 查询该标签下最火的问题
     * @param labelId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/hotList/{labelId}/{page}/{size}",method = RequestMethod.GET)
    public Result<PageResult> hotList(@PathVariable String labelId, @PathVariable int page, @PathVariable int size){
        Page pageList=problemService.hotList(labelId,page,size);
        return Result.of(true, StatusCode.OK,"查询成功",new PageResult(pageList.getTotalElements(),pageList.getContent()));
    }

    /**
     * 查询该标签下等待回答的问题
     * @param labelId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/waitList/{labelId}/{page}/{size}",method = RequestMethod.GET)
    public Result<PageResult> waitList(@PathVariable String labelId, @PathVariable int page, @PathVariable int size){
        Page pageList=problemService.waitList(labelId,page,size);
        return Result.of(true, StatusCode.OK,"查询成功",new PageResult(pageList.getTotalElements(),pageList.getContent()));
    }

    /**
     * 查找指定的问题
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result<Problem> findById(@PathVariable String id){
        return Result.of(true,StatusCode.OK,"查询成功",problemService.findById(id));
    }

    /**
     * 修改指定的问题
     * @param problem
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Result update(@RequestBody Problem problem,@PathVariable String id){
        problem.setId(id);
        problemService.update(problem);
        return Result.of(true,StatusCode.OK,"修改成功");
    }

}
