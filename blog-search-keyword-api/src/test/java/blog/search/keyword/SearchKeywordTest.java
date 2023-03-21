package blog.search.keyword;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SearchKeywordTest {

    @DisplayName("검색어 Entity는 키워드와 횟수를 가지며 횟수의 기본은 1이다. ")
    @Test
    final void createSearchKeywordTest() {
        final String keyWord = "test";

        SearchKeyword searchKeyword = new SearchKeyword(keyWord);

        assertThat(searchKeyword.retrieveSearchCount()).isEqualTo(1L);
    }

}
