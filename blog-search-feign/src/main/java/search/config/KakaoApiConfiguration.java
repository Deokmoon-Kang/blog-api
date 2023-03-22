package search.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;

public class KakaoApiConfiguration implements RequestInterceptor {

    private String kakaoAK = "KakaoAK ";
    private String kakaoApiKey = "5a60cf339c638fc06dc05cfe14d28804";

    @Override
    public void apply(RequestTemplate template) {
        template.header("Authorization", kakaoAK + kakaoApiKey);
        template.header("Content-Type", "application/json");
        template.header("Accept", "application/json");
    }
}
