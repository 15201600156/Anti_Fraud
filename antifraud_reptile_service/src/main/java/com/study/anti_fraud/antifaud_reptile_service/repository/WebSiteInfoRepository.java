package com.study.anti_fraud.antifaud_reptile_service.repository;

import com.study.anti_fraud.antifaud_reptile_service.entity.WebSiteInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

public interface WebSiteInfoRepository extends ElasticsearchCrudRepository<WebSiteInfo,Long> {
}
