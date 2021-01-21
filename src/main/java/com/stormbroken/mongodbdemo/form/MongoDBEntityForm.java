package com.stormbroken.mongodbdemo.form;

import java.util.List;

/**
 * @Author stormbroken
 * Create by 2021/01/21
 * @Version 1.0
 **/

public class MongoDBEntityForm {
    private Long id;
    private String name;
    private List<String> tag;
    private Integer likes;

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
}
