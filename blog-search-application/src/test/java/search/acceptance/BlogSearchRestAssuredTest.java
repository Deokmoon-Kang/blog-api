package search.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import search.AcceptanceTest;
import search.keyword.domain.SearchKeyword;
import search.keyword.dto.BlogSearchPopularKeyword;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class BlogSearchRestAssuredTest extends AcceptanceTest {

    @BeforeEach
    public void setUp() {
        super.setUp();
        searchByKeyword("test1");
        searchByKeyword("test2");
    }

    @DisplayName("검색어 기본 테스트")
    @Test
    final void searchKeywordTest() {
        final String keyWord = "hello";

        ExtractableResponse<Response> response = searchByKeyword(keyWord);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("인기검색어 테스트")
    @TestFactory
    Stream<DynamicTest> popularKeywordTest() {
        return Stream.of(
                dynamicTest("test1 키워드를 한번 더 조회 한다.", () -> {
                    ExtractableResponse<Response> response = searchByKeyword("test1");

                    assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
                }),

                dynamicTest("인기검색어 조회", () -> {
                    ExtractableResponse<Response> response = retrievePopularKeyword();
                    List<BlogSearchPopularKeyword> blogSearchPopularKeywords = response.body().jsonPath().getList(".", BlogSearchPopularKeyword.class);
                    BlogSearchPopularKeyword popularKeyword = blogSearchPopularKeywords.stream()
                                                                .findAny().filter(_this -> _this.getRank() == 1).get();
                    String keyword = popularKeyword.getKeyword();
                    assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
                    assertThat(keyword).isEqualTo("test1");
                })
        );

    }

    public static ExtractableResponse<Response> searchByKeyword(String keyword) {

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/v1/blog/search?query=" + keyword)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> retrievePopularKeyword() {

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/v1/blog/popular/keyword")
                .then().log().all()
                .extract();
    }
}
