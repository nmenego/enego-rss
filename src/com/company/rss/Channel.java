package com.company.rss;

import java.util.ArrayList;
import java.util.List;

/**
 * Object representation of a CHANNEL tag.
 * Created by nmenego on 9/25/16.
 */
public class Channel {

    private String title;
    private String link;
    private String description;
    private String lastBuildDate;
    private String docs;
    private String generator;

    private List<Item> items = new ArrayList<>();

    public Channel(String title, String link, String description, String lastBuildDate, String docs, String generator) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.lastBuildDate = lastBuildDate;
        this.docs = docs;
        this.generator = generator;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLastBuildDate() {
        return lastBuildDate;
    }

    public void setLastBuildDate(String lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDocs() {
        return docs;
    }

    public void setDocs(String docs) {
        this.docs = docs;
    }

    public String getGenerator() {
        return generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", lastBuildDate='" + lastBuildDate + '\'' +
                ", description='" + description + '\'' +
                ", docs='" + docs + '\'' +
                ", generator='" + generator + '\'' +
                '}';
    }
}
