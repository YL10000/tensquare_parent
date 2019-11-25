package com.tensquare.service;

import com.tensquare.dao.EnterpriseDao;
import com.tensquare.pojo.Enterprise;
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
public class EnterpriseService {

    @Autowired
    private EnterpriseDao enterpriseDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询全部列表
     * @return
     */
    public List<Enterprise> findAll() {
        return enterpriseDao.findAll();
    }


    /**
     * 条件查询+分页
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<Enterprise> findSearch(Map whereMap, int page, int size) {
        Specification<Enterprise> specification = createSpecification(whereMap);
        PageRequest pageRequest =  PageRequest.of(page-1, size);
        return enterpriseDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     * @param whereMap
     * @return
     */
    public List<Enterprise> findSearch(Map whereMap) {
        Specification<Enterprise> specification = createSpecification(whereMap);
        return enterpriseDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     * @param id
     * @return
     */
    public Enterprise findById(String id) {
        return enterpriseDao.findById(id).get();
    }

    /**
     * 增加
     * @param enterprise
     */
    public void add(Enterprise enterprise) {
        enterprise.setId( idWorker.nextId()+"" );
        enterpriseDao.save(enterprise);
    }

    /**
     * 修改
     * @param enterprise
     */
    public void update(Enterprise enterprise) {
        enterpriseDao.save(enterprise);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteById(String id) {
        enterpriseDao.deleteById(id);
    }

    /**
     * 动态条件构建
     * @param params
     * @return
     */
    private Specification<Enterprise> createSpecification(Map params) {

        return new Specification<Enterprise>() {

            @Override
            public Predicate toPredicate(Root<Enterprise> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
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
     * 查询热门企业列表
     * @return
     */
    public List<Enterprise> hotList(){
        return enterpriseDao.findByIshot("1");
    }
}
