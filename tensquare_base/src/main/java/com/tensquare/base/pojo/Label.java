package com.tensquare.base.pojo;

import com.tensquare.base.validator.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

//创建实体交给jpa管理
@Entity
@Table(name = "tb_label")

//下面是lombok的注解
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Label implements Serializable {

    //声明主键
    @Id
    private String id;

    @NotNull
    private String labelname;

    @NotNull(message = "状态不能为空")
    @State
    private String state;

    private Long count;

    private Long fans;

    private String recommend;
}
