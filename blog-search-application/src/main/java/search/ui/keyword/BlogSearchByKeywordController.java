package search.ui.keyword;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import search.keyword.application.BlogSearchByKeywordService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import search.keyword.dto.BlogSearchDocument;
import search.keyword.dto.BlogSearchPopularKeyword;

import java.util.Arrays;
import java.util.List;

import static search.keyword.common.CommonConstants.DEFAULT_PAGE;
import static search.keyword.common.CommonConstants.DEFAULT_SIZE;
import static search.keyword.common.CommonConstants.DEFAULT_SORT;
import static search.keyword.common.CommonErrorMessage.INVALID_KEYWORD_FORMAT_IS_EMPTY;

@RestController
@RequestMapping("/v1/blog")
public class BlogSearchByKeywordController {
    private static final List<String> KIND_OF_SORT = Arrays.asList("accuracy", "recency");

    private final BlogSearchByKeywordService blogSearchByKeywordService;

    public BlogSearchByKeywordController(BlogSearchByKeywordService blogSearchByKeywordService) {
        this.blogSearchByKeywordService = blogSearchByKeywordService;
    }

    @GetMapping("/search")
    ResponseEntity<List<BlogSearchDocument>> blogSearchByKeyword(
            @RequestParam(value = "query", required = true) String keyword
            , @RequestParam(value = "sort", required = false, defaultValue = DEFAULT_SORT) String sort
            , @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE) int page
            , @RequestParam(value = "size", required = false, defaultValue = DEFAULT_SIZE) int size) {
        if (keyword.isEmpty()) {
            throw new IllegalArgumentException(INVALID_KEYWORD_FORMAT_IS_EMPTY.getErrorMessage());
        }
        if (!KIND_OF_SORT.contains(sort)) {
            sort = DEFAULT_SORT;
        }
        if (page < 0) {
            page = Integer.valueOf(DEFAULT_PAGE);
        }
        if (size < 10) {
            size = Integer.valueOf(DEFAULT_SIZE);
        }
        return ResponseEntity.ok().body(blogSearchByKeywordService.blogSearchByKeyword(keyword.trim(), sort, page, size));
    }

    @GetMapping("/popular/keyword")
    ResponseEntity<List<BlogSearchPopularKeyword>> retrievePopularBlogSearchKeyword() {
        return ResponseEntity.ok().body(blogSearchByKeywordService.retrievePopularBlogSearchKeyword());
    }

}
