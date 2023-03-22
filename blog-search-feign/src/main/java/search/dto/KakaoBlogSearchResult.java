package search.dto;

import search.dto.KakaoBlogSearchDocument;
import search.dto.KakaoBlogSearchMeta;

import java.util.List;

public class KakaoBlogSearchResult {
    private KakaoBlogSearchMeta meta;
    private List<KakaoBlogSearchDocument> documents;

    public KakaoBlogSearchResult() {}
    public KakaoBlogSearchResult(KakaoBlogSearchMeta meta, List<KakaoBlogSearchDocument> documents) {
        this.meta = meta;
        this.documents = documents;
    }

    public KakaoBlogSearchMeta getMeta() {
        return meta;
    }

    public void setMeta(KakaoBlogSearchMeta meta) {
        this.meta = meta;
    }

    public List<KakaoBlogSearchDocument> getDocuments(int page, int size) {
        int index = 0;
        for (KakaoBlogSearchDocument kakaoBlogSearchDocument : documents) {
            kakaoBlogSearchDocument.makePagination(page, size, index++);
        }
        return documents;
    }

    public void setDocuments(List<KakaoBlogSearchDocument> documents) {
        this.documents = documents;
    }


}

