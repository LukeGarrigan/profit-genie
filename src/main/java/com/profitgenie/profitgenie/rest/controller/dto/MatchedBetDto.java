package com.profitgenie.profitgenie.rest.controller.dto;

import java.io.Serializable;

public class MatchedBetDto implements Serializable {

    private String description;
    private String affiliateLink;
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
