package search.keyword.application;

import org.springframework.transaction.annotation.Transactional;
import search.client.BlogSearchClient;
import org.springframework.stereotype.Service;
import search.dto.KakaoBlogSearchDocument;
import search.dto.KakaoBlogSearchResponse;
import search.keyword.domain.BlogSearchResultRepository;
import search.keyword.domain.SearchKeyword;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Service
public class BlogSearchByKeywordService {

    private final BlogSearchResultRepository blogSearchResultRepository;
    private final BlogSearchClient blogSearchClient;

    public BlogSearchByKeywordService(BlogSearchResultRepository blogSearchResultRepository
                , BlogSearchClient blogSearchClient) {
        this.blogSearchResultRepository = blogSearchResultRepository;
        this.blogSearchClient = blogSearchClient;
    }

    @Transactional
    public List<KakaoBlogSearchDocument> blogSearchByKeyword(String keyword, String sort, int page, int size) {
        KakaoBlogSearchResponse kakaoBlogSearchResponse = blogSearchClient.searchBlogByKeyword(keyword, sort, page, size);
        Optional<SearchKeyword> searchKeyword = blogSearchResultRepository.findById(keyword);

        if (searchKeyword.isPresent()) {
            searchKeyword.get().increaseSearchCount();
            saveSearchKeyword(searchKeyword.get());
        } else {
            System.out.println("==========저장===========");
            saveSearchKeyword(new SearchKeyword(keyword));
        }
        return kakaoBlogSearchResponse.getDocuments();
    }

    @Transactional
    protected void saveSearchKeyword(SearchKeyword searchKeyword) {
        blogSearchResultRepository.save(searchKeyword);
    }

}
