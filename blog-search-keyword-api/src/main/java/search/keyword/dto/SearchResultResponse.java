package search.keyword.dto;

import search.dto.KakaoBlogSearchDocument;

import java.util.ArrayList;
import java.util.List;

public class SearchResultResponse {
    private List<BlogSearchDocument> blogSearchDocuments;

    public SearchResultResponse() {}

    public SearchResultResponse(List<KakaoBlogSearchDocument> kakaoBlogSearchDocuments) {
        blogSearchDocuments = new ArrayList<>();
    }
}
