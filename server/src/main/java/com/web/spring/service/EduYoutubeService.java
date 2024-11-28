package com.web.spring.service;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonParser;
import com.google.api.client.json.JsonGenerator;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;
import com.google.api.services.youtube.model.ThumbnailDetails;
import com.web.spring.dto.child.edu.YouTubeSearchResult;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class EduYoutubeService {

    @Value("${youtube.api.key}")
    private String apiKey;

    public List<YouTubeSearchResult> searchVideoList() throws IOException {
        JsonFactory jsonFactory = new GsonFactory();

        // YouTube 객체를 빌드하여 API에 접근할 수 있는 YouTube 클라이언트 생성
        YouTube youtube = new YouTube.Builder(
                new NetHttpTransport(),
                jsonFactory,
                request -> {})
                .build();
       
        // 검색할 키워드
        String query = "금쪽이 경제교실";
        
        // YouTube Search API를 사용하여 동영상 검색을 위한 요청 객체 생성
        YouTube.Search.List search = youtube.search().list(Collections.singletonList("id,snippet"));
        search.setKey(apiKey);
        search.setQ(query);
        search.setMaxResults(2L);
        
        // 검색 요청 실행 및 응답 받아오기
        SearchListResponse searchResponse = search.execute();

        // 검색 결과에서 동영상 목록 가져오기
        List<SearchResult> searchResultList = searchResponse.getItems();

        // 결과를 YouTubeSearchResult 리스트로 변환
        return mapSearchResultsToDto(searchResultList);
    }
    
    public YouTubeSearchResult videoDetails(String videoId) throws IOException {
        JsonFactory jsonFactory = new GsonFactory();

        // YouTube 객체를 빌드하여 API에 접근할 수 있는 YouTube 클라이언트 생성
        YouTube youtube = new YouTube.Builder(
                new NetHttpTransport(),
                jsonFactory,
                request -> {})
                .build();
       
        
        // YouTube Search API를 사용하여 동영상 검색을 위한 요청 객체 생성
        YouTube.Search.List search = youtube.search().list(Collections.singletonList("id,snippet"));
        search.setKey(apiKey);
        search.setQ(videoId);
        search.setMaxResults(1L);
        
        // 검색 요청 실행 및 응답 받아오기
        SearchListResponse searchResponse = search.execute();

        // 검색 결과에서 동영상 목록 가져오기
        SearchResult searchResultList = searchResponse.getItems().get(0);

        // 결과를 YouTubeSearchResult 리스트로 변환
        return mapSearchResultToDto(searchResultList);
    }

    private List<YouTubeSearchResult> mapSearchResultsToDto(List<SearchResult> searchResultList) {
        // 결과를 YouTubeSearchResult DTO 객체로 변환
        List<YouTubeSearchResult> results = new ArrayList<>();

        for (SearchResult searchResult : searchResultList) {
            String videoId = null;
            videoId = searchResult.getId().getVideoId();
            
            //썸네일가져오기
            ThumbnailDetails thumbnailDetails=searchResult.getSnippet().getThumbnails();
            Thumbnail thumbnail = thumbnailDetails.getHigh();
            String thumbnailUrl = thumbnail.getUrl();

            // videoId가 존재하거나 playlistId가 존재하면, DTO 객체를 생성하여 결과 리스트에 추가
            if (videoId != null) {
                YouTubeSearchResult result = new YouTubeSearchResult();
                // url 생성을 위해 필요함
                result.setVideoId(videoId);
                result.setChannelId(searchResult.getSnippet().getChannelId());
                result.setChannelTitle(searchResult.getSnippet().getChannelTitle());
                result.setDescription(searchResult.getSnippet().getDescription());
                result.setTitle(searchResult.getSnippet().getTitle());
                result.setPublishedAt(searchResult.getSnippet().getPublishedAt().toString());
                result.setVideoThumbnail(thumbnailUrl);
                // 동영상 URL 추가
                if (videoId != null) {
                    result.setVideoUrl("https://www.youtube.com/watch?v=" + videoId);
                   }
                results.add(result);
            }
        }
        return results;
    }
    
    private YouTubeSearchResult mapSearchResultToDto(SearchResult searchResult) {
        // 결과를 YouTubeSearchResult DTO 객체로 변환
            String videoId = null;
            videoId = searchResult.getId().getVideoId();
            
            //썸네일가져오기
            ThumbnailDetails thumbnailDetails=searchResult.getSnippet().getThumbnails();
            Thumbnail thumbnail = thumbnailDetails.getHigh();
            String thumbnailUrl = thumbnail.getUrl();

            // videoId가 존재하거나 playlistId가 존재하면, DTO 객체를 생성하여 결과 리스트에 추가
            if (videoId != null) {
                YouTubeSearchResult result = new YouTubeSearchResult();
                // url 생성을 위해 필요함
                result.setVideoId(videoId);
                result.setChannelId(searchResult.getSnippet().getChannelId());
                result.setChannelTitle(searchResult.getSnippet().getChannelTitle());
                result.setDescription(searchResult.getSnippet().getDescription());
                result.setTitle(searchResult.getSnippet().getTitle());
                result.setPublishedAt(searchResult.getSnippet().getPublishedAt().toString());
                result.setVideoThumbnail(thumbnailUrl);
                result.setVideoUrl("https://www.youtube.com/watch?v=" + videoId);
                return result;
            }
			return null;
    }
}