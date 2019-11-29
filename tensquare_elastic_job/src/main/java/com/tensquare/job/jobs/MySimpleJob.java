package com.tensquare.job.jobs;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.elasticjob.lite.annotation.ElasticSimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ElasticSimpleJob(cron = "* * * * * ?",jobName = "test123",shardingTotalCount = 2,jobParameter = "测试数",shardingItemParameters = "0=A,1=B")
public class MySimpleJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {

        String info=String.format("--------ThreadI:%s,任务总分片数：%s，当前分片项：%s，当前参数：%s，当前任务名称：%s，当前任务数：%s",
            Thread.currentThread().getId(),
                shardingContext.getShardingTotalCount(),
                shardingContext.getShardingItem(),
                shardingContext.getShardingParameter(),
                shardingContext.getJobName(),
                shardingContext.getJobParameter()
        );
    }


}
