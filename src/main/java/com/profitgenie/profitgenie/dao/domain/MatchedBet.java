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
}
