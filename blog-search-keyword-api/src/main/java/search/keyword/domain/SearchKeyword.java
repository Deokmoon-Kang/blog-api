package search.keyword.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class SearchKeyword {

    private final long DEFAULT_COUNT = 1L;
    private final int MAX_LENGTH_KEYWORD = 50;

    @Id @Column(length = MAX_LENGTH_KEYWORD)
    private String keyWord;

    private long searchCount;

    protected SearchKeyword() {}

    public SearchKeyword(String keyWord) {
        if (keyWord.length() > MAX_LENGTH_KEYWORD) {
            throw new IllegalArgumentException();
        }
        this.keyWord = keyWord;
        this.searchCount = DEFAULT_COUNT;
    }

    public long retrieveSearchCount() {
        return this.searchCount;
    }

    public void increaseSearchCount() {
        this.searchCount++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SearchKeyword that = (SearchKeyword) o;
        return Objects.equals(keyWord, that.keyWord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyWord);
    }
}
