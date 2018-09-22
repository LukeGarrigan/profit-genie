package com.profitgenie.profitgenie.dao.domain;


import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "PG_matched_bet")
public class MatchedBet extends AbstractEntity {


    @Column
    private String description;
    @Column
    private String affiliateLink;
    @Column
    private String pathToImage;
    @Column
    private String title;
    @Column
    private long sequence;


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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }
}
