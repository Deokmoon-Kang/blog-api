package search.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import search.config.KakaoApiConfiguration;
import search.dto.KakaoBlogSearchResult;

@FeignClient(name = "blogSearch", url = "https://dapi.kakao.com/v2/search/blog", configuration = KakaoApiConfiguration.class)
public interface BlogSearchClient {

    @GetMapping("")
    KakaoBlogSearchResult searchBlogByKeyword(@RequestParam(value = "query", required = true) String keyword, @RequestParam(value = "sort", required = false) String sort
                                                , @RequestParam(value = "page", required = false) int page, @RequestParam(value = "size", required = false) int size);
}
