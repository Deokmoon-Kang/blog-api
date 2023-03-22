package search.ui.keyword;

import search.dto.KakaoBlogSearchDocument;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import search.keyword.application.BlogSearchByKeywordService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/blog")
public class BlogSearchByKeywordController {

    private final BlogSearchByKeywordService blogSearchByKeywordService;

    public BlogSearchByKeywordController(BlogSearchByKeywordService blogSearchByKeywordService) {
        this.blogSearchByKeywordService = blogSearchByKeywordService;
    }

    @GetMapping("/search")
    ResponseEntity<List<KakaoBlogSearchDocument>> blogSearchByKeyword(@RequestParam(value = "query") String keyword) {
        return ResponseEntity.ok().body(blogSearchByKeywordService.blogSearchByKeyword(keyword));
    }

}
