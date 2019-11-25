package com.tensquare.qa.dao;
import java.util.List;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ProblemDao extends JpaRepository<Problem,String>, JpaSpecificationExecutor<Problem> {

    @Query(value = "SELECT p.* FROM tb_problem p ,tb_pl pl WHERE pl.problemid=p.id AND pl.labelid=? ORDER BY p.createtime DESC",nativeQuery =true)
    public Page<Problem> newList(String labelId, Pageable pageable);

    @Query(value = "SELECT p.* FROM tb_problem p ,tb_pl pl WHERE pl.problemid=p.id AND pl.labelid=?1 ORDER BY p.reply DESC",nativeQuery =true)
    public Page<Problem> hotList(String labelId, Pageable pageable);

    @Query(value = "SELECT p.* FROM tb_problem p ,tb_pl pl WHERE pl.problemid=p.id AND pl.labelid=? AND p.reply=0 ORDER BY p.createtime DESC",nativeQuery =true)
    public Page<Problem> waitList(String labelId, Pageable pageable);
}
