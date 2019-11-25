package com.tensquare.service;

import com.tensquare.dao.RecruitDao;
import com.tensquare.pojo.Recruit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RecruitService {

    @Autowired
    private RecruitDao recruitDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询全部列表
     * @return
     */
    public List<Recruit> findAll() {
        return recruitDao.findAll();
    }


    /**
     * 条件查询+分页
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<Recruit> findSearch(Map whereMap, int page, int size) {
        Specification<Recruit> specification = createSpecification(whereMap);
        PageRequest pageRequest =  PageRequest.of(page-1, size);
        return recruitDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     * @param whereMap
     * @return
     */
    public List<Recruit> findSearch(Map whereMap) {
        Specification<Recruit> specification = createSpecification(whereMap);
        return recruitDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     * @param id
     * @return
     */
    public Recruit findById(String id) {
        return recruitDao.findById(id).get();
    }

    /**
     * 增加
     * @param recruit
     */
    public void add(Recruit recruit) {
        recruit.setId( idWorker.nextId()+"" );
        recruitDao.save(recruit);
    }

    /**
     * 修改
     * @param recruit
     */
    public void update(Recruit recruit) {
        recruitDao.save(recruit);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteById(String id) {
        recruitDao.deleteById(id);
    }

    /**
     * 动态条件构建
     * @param params
     * @return
     */
    private Specification<Recruit> createSpecification(Map params) {

        return new Specification<Recruit>() {

            @Override
            public Predicate toPredicate(Root<Recruit> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
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

    public List<Recruit> findTop4ByStateOrderByCreatetimeDesc(String state){
        return recruitDao.findTop4ByStateOrderByCreatetimeDesc(state);
    }

    public List <Recruit> findByNameLike(String name){
        if (StringUtils.isNotBlank(name)) name="%"+name+"%";
        return recruitDao.findByJobnameLike(name);
    }


}
