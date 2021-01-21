package com.stormbroken.mongodbdemo.controller;

import com.stormbroken.mongodbdemo.entity.MongoDBEntity;
import com.stormbroken.mongodbdemo.form.MongoDBEntityForm;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author stormbroken
 * Create by 2021/01/21
 * @Version 1.0
 **/

@RestController
public class MongoDBController {
    @Resource
    private MongoTemplate mongoTemplate;

    @PostMapping("/remove")
    public String remove(@RequestParam Long id){
        Query query = new Query(Criteria.where("id").is(id));
        MongoDBEntity mongoDBEntity = mongoTemplate.findOne(query, MongoDBEntity.class);
        mongoTemplate.remove(mongoDBEntity);
        return "删除成功";
    }

    @PostMapping("/save")
    public String save(@RequestBody MongoDBEntityForm mongoDBEntityForm){
        mongoTemplate.save(new MongoDBEntity(mongoDBEntityForm));
        return "添加成功";
    }

    @PostMapping("/update")
    public String update(@RequestBody MongoDBEntityForm mongoDBEntityForm){
        MongoDBEntity mongoDBEntity = new MongoDBEntity(mongoDBEntityForm);
        Query query = new Query(Criteria.where("id").is(mongoDBEntity.getId()));

        Update update = new Update();
        update.set("name", mongoDBEntity.getName());
        update.set("tag", mongoDBEntity.getTag());
        update.set("likes", mongoDBEntity.getLikes());
        update.set("timestamp", LocalDateTime.now());

        mongoTemplate.updateFirst(query, update, MongoDBEntity.class);
        return "更新成功";
    }

    @GetMapping("/findById")
    public MongoDBEntity findByID(@RequestParam Long id){
        Query query = new Query(Criteria.where("id").is(id));
        MongoDBEntity mongoDBEntity = mongoTemplate.findOne(query, MongoDBEntity.class);
        return mongoDBEntity;
    }

    @GetMapping("/findByIdAndName")
    public MongoDBEntity findById(@RequestParam Long id, @RequestParam String name){
        Query query = new Query(Criteria.where("id").is(id).and("name").is(name));
        MongoDBEntity mongoDBEntity = mongoTemplate.findOne(query, MongoDBEntity.class);
        return mongoDBEntity;
    }

    @GetMapping("/findByLikes")
    public List<MongoDBEntity> findByLikes(@RequestParam Integer likes){
        Query query = new Query(Criteria.where("likes").gt(likes));
        List<MongoDBEntity> mongoDBEntities = mongoTemplate.find(query, MongoDBEntity.class);
        return mongoDBEntities;
    }

    @GetMapping("/findByTags")
    public List<MongoDBEntity> findByTags(@RequestParam String tag){
        System.out.println("findByTag?tag=" + tag);
        List<String> tmp = new ArrayList<>();
        tmp.add(tag);
        Query query = new Query(Criteria.where("tag").in(tag));
        List<MongoDBEntity> mongoDBEntities = mongoTemplate.find(query, MongoDBEntity.class);
        return mongoDBEntities;
    }

    @GetMapping("/findAll")
    public List<MongoDBEntity> findAll(){
        return mongoTemplate.findAll(MongoDBEntity.class);
    }

    // 还有比较大扩展空间

    @GetMapping("/findLikesGt0Number")
    public Integer findLikesGt0Number(){
        // Document -> org.bson.Document;
        AggregationResults<Document> result = mongoTemplate.aggregate(Aggregation.newAggregation(
                MongoDBEntity.class,Aggregation.match(Criteria.where("likes").gt(0)),
                Aggregation.count().as("sumOfLikes"),   // 计数并作为
                Aggregation.project("sumOfLikes")), // 控制返回字段
                "col", Document.class);
        return (Integer) result.getMappedResults().get(0).get("sumOfLikes");
    }

    @GetMapping("/findAllLikes")
    public List<Document> findAllLikes(){
        // Document -> org.bson.Document;
        AggregationResults<Document> result = mongoTemplate.aggregate(Aggregation.newAggregation(
                MongoDBEntity.class,
                Aggregation.group("name").
                        sum("likes").as("SumOfLikes").          // 总和
                        first("likes").as("FirstOfLikes").      // 第一个
                        addToSet("likes").as("SetOfLikes")),    // 全部的集合
                "col", Document.class);
        return result.getMappedResults();
    }
}
