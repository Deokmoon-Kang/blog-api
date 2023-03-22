package search.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import search.config.KakaoApiConfiguration;
import search.dto.KakaoBlogSearchResponse;

@FeignClient(name = "blogSearch", url = "https://dapi.kakao.com/v2/search/blog", configuration = KakaoApiConfiguration.class)
public interface BlogSearchClient {

    @GetMapping("")
    KakaoBlogSearchResponse searchBlogByKeyword(@RequestParam(value = "query") String keyword);
}
