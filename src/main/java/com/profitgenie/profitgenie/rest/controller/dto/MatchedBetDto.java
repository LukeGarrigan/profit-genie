package com.profitgenie.profitgenie.rest.controller.dto;

import java.io.Serializable;
import java.util.Date;

public class MatchedBetDto implements Serializable {

    private String description;
    private String affiliateLink;
    private String pathToImage;
    private String title;
    private String linkLabel;
    private long sequence;
    private long id;
    private Date date;


    public String getLinkLabel() {
        return linkLabel;
    }

    public void setLinkLabel(String linkLabel) {
        this.linkLabel = linkLabel;
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

    public String getAffiliateLink() {
        return affiliateLink;
    }

    public void setAffiliateLink(String affiliateLink) {
        this.affiliateLink = affiliateLink;
    }

    public String getPathToImage() {
        return pathToImage;
    }

    public void setPathToImage(String pathToImage) {
        this.pathToImage = pathToImage;
    }

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
