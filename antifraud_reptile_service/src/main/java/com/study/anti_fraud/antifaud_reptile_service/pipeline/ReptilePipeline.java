package com.study.anti_fraud.antifaud_reptile_service.pipeline;

import cn.hutool.core.collection.CollectionUtil;
import com.study.anti_fraud.antifaud_reptile_service.entity.WebSiteInfo;
import com.study.anti_fraud.antifaud_reptile_service.repository.WebSiteInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

@Component
@Slf4j
public class ReptilePipeline implements Pipeline {
    @Autowired
    WebSiteInfoRepository webSiteInfoRepository;

    @Override
    public void process(ResultItems resultItems, Task task) {

        List<WebSiteInfo> list = (List<WebSiteInfo>) resultItems.get("website");
        if (CollectionUtil.isNotEmpty(list)) {
            webSiteInfoRepository.saveAll(list);
        }


    }
}
