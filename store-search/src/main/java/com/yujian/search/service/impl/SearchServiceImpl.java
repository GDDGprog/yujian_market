package com.yujian.search.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yujian.param.ProductSearchParam;
import com.yujian.pojo.Product;
import com.yujian.search.service.SearchService;
import com.yujian.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    /**
     * 根据关键字和分页进行数据库数据查询
     * @param productSearchParam
     * @return
     */
    @Override
    public R search(ProductSearchParam productSearchParam) {

        SearchRequest searchRequest = new SearchRequest("product");

        String search = productSearchParam.getSearch();

        if (StringUtils.isEmpty(search)) {
            // null 不添加all关键字,查询全部即可
            searchRequest.source().query(QueryBuilders.matchAllQuery());
        }else{
            //不为null
            //添加all的关键字匹配
            searchRequest.source().query(QueryBuilders.matchQuery("all", search));
        }
        //进行分页数据添加
        searchRequest.source().from((productSearchParam.getCurrentPage()-1) * productSearchParam.getPageSize()); //偏移量  (当前页-1) * 每页条数
        searchRequest.source().size(productSearchParam.getPageSize()); //每页条数

        SearchResponse searchResponse = null;

        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("查询错误");
        }

        SearchHits hits = searchResponse.getHits();
        //查询符合的数量
        long total = hits.getTotalHits().value;

        //查询集合
        SearchHit[] hitHits = hits.getHits();

        List<Product> productList = new ArrayList<>();

        //json 处理器
        ObjectMapper objectMapper = new ObjectMapper();

        for (SearchHit hitHit : hitHits) {
            //查询的内容数据 productDoc模型对应的json数据
            String sourceAsString = hitHit.getSourceAsString();

            Product product = null;

            try {
                //TODO: 修改product额实体类,添加忽略没有属性的注解
                product = objectMapper.readValue(sourceAsString, Product.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            productList.add(product);
        }

        R r = R.ok(null, productList, total);
        log.info("查询结果:{}", r);
        return r;
    }
}
