package search.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import search.AcceptanceTest;
import search.keyword.domain.SearchKeyword;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BlogSearchRestAssuredTest extends AcceptanceTest {

    @BeforeEach
    public void setUp() {
        super.setUp();
        ExtractableResponse<Response> response = searchByKeyword("test");
    }

    @DisplayName("검색어 기본 테스트")
    @Test
    final void searchKeywordTest() {
        final String keyWord = "hello";

        ExtractableResponse<Response> response = searchByKeyword(keyWord);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("같은 키워드를 2번 호출하면 키워드의 검색 수는 2이다.")
    @Test
    final void makeExceptionWhenKeywordLengthOver() {
        final String keyWord = "test";
        ExtractableResponse<Response> response = searchByKeyword(keyWord);

    }

    public static ExtractableResponse<Response> searchByKeyword(String keyword) {

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/v1/blog/search?query=" + keyword)
                .then().log().all()
                .extract();
    }
}
