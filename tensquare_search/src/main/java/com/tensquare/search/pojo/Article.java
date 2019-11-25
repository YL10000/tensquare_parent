package com.tensquare.search.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

//指定索引库和类型
@Document(indexName = "tensquare",type = "article")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article implements Serializable {

    @Id
    private String id;

    /**
     * 是否索引：就是看该域是否被搜索
     * 是否分词：就是搜索的时候是整体匹配还是单词匹配
     * 是否存储：就是是否在页面中显示
     */
    @Field(index = true,analyzer ="ik_max_word",searchAnalyzer = "ik_max_word")
    private String title;

    @Field(index = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String content;

    private String state;
}
