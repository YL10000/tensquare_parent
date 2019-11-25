package com.tensquare.qa.service;

import com.tensquare.qa.dao.ProblemDao;
import com.tensquare.qa.pojo.Problem;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.AssertTrue;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProblemService {
    
    @Autowired
    ProblemDao problemDao;

    @Autowired
    private IdWorker idworker;

    /**
     * 查询所有的问题
     * @return
     */
    public List<Problem> findAll() {
        return problemDao.findAll();
    }

    /**
     * 查询指定的问题
     * @param id
     * @return
     */
    @Cacheable(value = "problem",key = "#id")//添加缓存
    public Problem findById(String id){
        Assert.assertTrue("1".equalsIgnoreCase(id));
        return problemDao.findById(id).get();
    }

    /**
     * 添加问题
     * @param label
     */
    public void add(Problem label){
        label.setId(idworker.nextId()+"");
        problemDao.save(label);
    }

    /**
     * 修改问题
     * @param problem
     */
    @CacheEvict(value = "problem",key = "#problem.id")//删除缓存
    public void update(Problem problem){
        problemDao.save(problem);
    }

    /**
     * 删除问题
     * @param id
     */
    public void deleteById(String id){
        problemDao.deleteById(id);
    }

    /**
     * 按条件查询
     * @param params
     * @return
     */
    public List<Problem> findSearch(Map params){
        return problemDao.findAll(createSpecification(params));
    }

    /**
     * 构件查询条件
     * @param params
     * @return
     */
    private Specification<Problem> createSpecification(Map params){
        return new Specification<Problem> () {

            @Override
            public Predicate toPredicate(Root<Problem> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicatesList=new ArrayList<>();
                params.forEach((key, value) -> {
                    if (null!=value&& StringUtils.isNotBlank(value.toString())){
                        predicatesList.add(criteriaBuilder.equal(root.get(key.toString()).as(String.class),value));
                    }
                });
                return criteriaBuilder.and(predicatesList.toArray(new Predicate [predicatesList.size()]));
            }
        };
    }

    /**
     * 分页查询符合条件的数据
     * @param params
     * @param page
     * @param size
     * @return
     */
    public Page<Problem> findSearch(Map params, int page, int size) {
        Specification<Problem> specification = createSpecification(params);
        PageRequest pageRequest=PageRequest.of(page-1,size);
        return problemDao.findAll(specification,pageRequest);
    }

    /**
     * 查询该标签下的最新的问题
     * @param labelId
     * @param page
     * @param size
     * @return
     */
    public Page<Problem> newList(String labelId,int page,int size){
        return problemDao.newList(labelId,PageRequest.of(page-1,size));
    }

    /**
     * 查询该标签下的最热的问题
     * @param labelId
     * @param page
     * @param size
     * @return
     */
    public Page<Problem> hotList(String labelId,int page,int size){
        return problemDao.hotList(labelId,PageRequest.of(page-1,size));
    }

    /**
     * 查询该标签下的等待回答的问题
     * @param labelId
     * @param page
     * @param size
     * @return
     */
    public Page<Problem> waitList(String labelId,int page,int size){
        return problemDao.waitList(labelId,PageRequest.of(page-1,size));
    }
}
