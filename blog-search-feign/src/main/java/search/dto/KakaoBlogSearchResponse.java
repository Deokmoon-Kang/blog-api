package search.dto;

import search.dto.KakaoBlogSearchDocument;
import search.dto.KakaoBlogSearchMeta;

import java.util.List;

public class KakaoBlogSearchResponse {
    private KakaoBlogSearchMeta meta;
    private List<KakaoBlogSearchDocument> documents;

    public KakaoBlogSearchResponse() {}
    public KakaoBlogSearchResponse(KakaoBlogSearchMeta meta, List<KakaoBlogSearchDocument> documents) {
        this.meta = meta;
        this.documents = documents;
    }

    public KakaoBlogSearchMeta getMeta() {
        return meta;
    }

    public void setMeta(KakaoBlogSearchMeta meta) {
        this.meta = meta;
    }

    public List<KakaoBlogSearchDocument> getDocuments() {
        return documents;
    }

    public void setDocuments(List<KakaoBlogSearchDocument> documents) {
        this.documents = documents;
    }


}

