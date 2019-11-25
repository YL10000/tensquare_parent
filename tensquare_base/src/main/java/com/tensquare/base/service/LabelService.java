package com.tensquare.base.service;
import java.util.ArrayList;
import	java.util.List;
import java.util.Map;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Service
public class LabelService {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idworker;

    /**
     * 查询所有的标签
     * @return
     */
    public List<Label> findAll() {
        return labelDao.findAll();
    }

    /**
     * 查询指定的标签
     * @param id
     * @return
     */
    @Cacheable(value = "label",key ="getTargetClass()+#id")
    public Label findById(String id){
        return labelDao.findById(id).get();
    }

    /**
     * 添加标签
     * @param label
     */
    public void add(Label label){
        label.setId(idworker.nextId()+"");
        labelDao.save(label);
    }

    /**
     * 修改标签
     * @param label
     */
    @CacheEvict(value = "label",key ="getTargetClass()+#label.id")
    public void update(Label label){
        labelDao.save(label);
    }

    /**
     * 删除标签
     * @param id
     */
    public void deleteById(String id){
        labelDao.deleteById(id);
    }

    /**
     * 按条件查询
     * @param params
     * @return
     */
    public List<Label> findSearch(Map params){
        return labelDao.findAll(createSpecification(params));
    }

    /**
     * 构件查询条件
     * @param params
     * @return
     */
    private Specification<Label> createSpecification(Map params){
        return new Specification<Label> () {

            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
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
    public Page<Label> findSearch(Map params, int page, int size) {
        Specification<Label> specification = createSpecification(params);
        PageRequest pageRequest=PageRequest.of(page-1,size);
        return labelDao.findAll(specification,pageRequest);
    }
}
