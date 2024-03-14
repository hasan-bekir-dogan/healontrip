package com.healontrip.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Builder
@Log4j2

@Entity
@Table(name = "social_medias")
public class SocialMediaEntity extends BaseEntity implements Serializable {
    @Column(name = "facebook_link")
    private String facebookLink;

    @Column(name = "instagram_link")
    private String instagramLink;

    @Column(name = "twitter_link")
    private String twitterLink;

    @Column(name = "linkedin_link")
    private String linkedinLink;

    public SocialMediaEntity(String facebookLink, String instagramLink, String twitterLink, String linkedinLink) {
        this.facebookLink = facebookLink;
        this.instagramLink = instagramLink;
        this.twitterLink = twitterLink;
        this.linkedinLink = linkedinLink;
    }
}
