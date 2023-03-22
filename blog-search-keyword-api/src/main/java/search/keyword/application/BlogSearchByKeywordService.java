package search.keyword.application;

import search.client.BlogSearchClient;
import org.springframework.stereotype.Service;
import search.dto.KakaoBlogSearchDocument;
import search.dto.KakaoBlogSearchResponse;
import search.keyword.domain.BlogSearchResultRepository;

import java.util.List;

@Service
public class BlogSearchByKeywordService {

    private final BlogSearchResultRepository blogSearchResultRepository;
    private final BlogSearchClient blogSearchClient;

    public BlogSearchByKeywordService(BlogSearchResultRepository blogSearchResultRepository
                , BlogSearchClient blogSearchClient) {
        this.blogSearchResultRepository = blogSearchResultRepository;
        this.blogSearchClient = blogSearchClient;
    }


    public List<KakaoBlogSearchDocument> blogSearchByKeyword(String keyword) {
        KakaoBlogSearchResponse kakaoBlogSearchResponse = blogSearchClient.searchBlogByKeyword(keyword);
        return kakaoBlogSearchResponse.getDocuments();
    }

}
