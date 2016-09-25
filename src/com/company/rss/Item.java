package com.company.rss;

/**
 * Object representation of an ITEM tag.
 * Created by nmenego on 9/25/16.
 */
public class Item {
    private String title;
    private String description;
    private String link;
    private String guid;
    private String pubDate;

    public Item(String title, String description, String link, String guid, String pubDate) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.guid = guid;
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    @Override
    public String toString() {
        return "Item{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", guid='" + guid + '\'' +
                ", pubDate='" + pubDate + '\'' +
                '}';
    }
}
