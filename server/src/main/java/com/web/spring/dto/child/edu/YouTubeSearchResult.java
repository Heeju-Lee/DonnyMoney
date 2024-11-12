package com.web.spring.dto.child.edu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class YouTubeSearchResult {
    private String videoId;
    private String channelId;
    private String channelTitle;
    private String description;
    private String title;
    private String publishedAt;
    private String VideoUrl;
    private String VideoThumbnail;
}