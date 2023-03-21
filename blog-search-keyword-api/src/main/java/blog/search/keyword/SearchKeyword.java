package blog.search.keyword;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SearchKeyword {

    private final long DEFAULT_COUNT = 1L;

    @Id
    private String keyWord;

    private long searchCount;

    protected SearchKeyword() {}

    public SearchKeyword(String keyWord) {
        this.keyWord = keyWord;
        this.searchCount = DEFAULT_COUNT;
    }

    public long retrieveSearchCount() {
        return this.searchCount;
    }

}
