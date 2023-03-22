package search.keyword.dto;

import org.bouncycastle.pqc.jcajce.provider.qtesla.SignatureSpi;
import search.keyword.domain.SearchKeyword;

import java.util.ArrayList;
import java.util.List;

public class BlogSearchPopularKeyword {
    int rank;
    String keyword;
    long searchCount;

    public BlogSearchPopularKeyword() {}

    public BlogSearchPopularKeyword(int rank, SearchKeyword searchKeyword) {
        this.rank = rank;
        this.keyword = searchKeyword.getKeyWord();
        this.searchCount = searchKeyword.getSearchCount();
    }

    public static List<BlogSearchPopularKeyword> makePopularKeywordResult(List<SearchKeyword> popularKeywords) {
        List<BlogSearchPopularKeyword> blogSearchPopularKeywords = new ArrayList<>();
        for (int i = 0; i < popularKeywords.size(); i++) {
            blogSearchPopularKeywords.add(
                new BlogSearchPopularKeyword(i + 1, popularKeywords.get(i) )
            );
        }
        return blogSearchPopularKeywords;
    }

    public int getRank() {
        return rank;
    }

    public String getKeyword() {
        return keyword;
    }

    public long getSearchCount() {
        return searchCount;
    }
}
