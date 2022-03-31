package com.study.anti_fraud.antifaud_reptile_service.entity;

import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;

@Data
@Document(indexName = "website_info", type = "_doc", createIndex = false)
public class WebSiteInfo implements Serializable {

    private Long id;

    private String title;

    private String href;

    private Date date;

    private String description;

}
