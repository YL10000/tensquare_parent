package com.tensquare.search.service;

import com.tensquare.search.dao.ArticleDao;
import com.tensquare.search.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    public void add(Article article){
        articleDao.save(article);
    }

    public Page<Article> search(String key,int page,int size){
        PageRequest pageRequest=PageRequest.of(page-1,size);
        return  articleDao.findByTitleOrContent(key,key,pageRequest);
    }
}
