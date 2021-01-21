package com.stormbroken.mongodbdemo.entity;

import com.stormbroken.mongodbdemo.form.MongoDBEntityForm;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author stormbroken
 * Create by 2021/01/21
 * @Version 1.0
 **/

@Document(collection = "col")
public class MongoDBEntity implements Serializable {
    @Id
    private Long id;
    private String name;
    private List<String> tag;
    private Integer likes;
    private LocalDateTime timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public MongoDBEntity() {
    }

    public MongoDBEntity(MongoDBEntityForm mongoDBEntityForm) {
        this.id = mongoDBEntityForm.getId();
        this.name = mongoDBEntityForm.getName();
        this.tag = mongoDBEntityForm.getTag();
        this.likes = mongoDBEntityForm.getLikes();
        this.timestamp = LocalDateTime.now();
    }

    public MongoDBEntity(Long id, String name, List<String> tag, Integer likes, LocalDateTime timestamp) {
        this.id = id;
        this.name = name;
        this.tag = tag;
        this.likes = likes;
        this.timestamp = timestamp;
    }
}
