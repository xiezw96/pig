package com.pig4cloud.pig.tools.oss;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
@Component
@ConfigurationProperties(prefix = "alibaba.oss")
@Setter
@Getter
public class AlibabaOssConfig {
    private List<String> buckets =new ArrayList<String>();
    
}
