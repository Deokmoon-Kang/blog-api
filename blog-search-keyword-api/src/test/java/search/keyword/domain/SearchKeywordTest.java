package search.keyword.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SearchKeywordTest {

    @DisplayName("검색어 Entity는 키워드와 횟수를 가지며 횟수의 기본은 1이다. ")
    @Test
    final void createSearchKeywordTest() {
        final String keyWord = "test";

        SearchKeyword searchKeyword = new SearchKeyword(keyWord);

        assertThat(searchKeyword.retrieveSearchCount()).isEqualTo(1L);
    }

    @DisplayName("검색 키워드를 50자를 넘기면 예외가 발생한다.")
    @Test
    final void makeExceptionWhenKeywordLengthOver() {
        final String keyWord = "012345678901234567890123456789012345678901234567892";

        assertThatThrownBy(() -> new SearchKeyword(keyWord))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
