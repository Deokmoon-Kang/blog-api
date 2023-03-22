package search.keyword.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import search.dto.KakaoBlogSearchDocument;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class BlogSearchDocument {
    private String title;
    private String contents;
    private String url;
    private String blogName;
    private String thumbNail;
    private String dateTime;
    private int size;
    private int page;
    private int current;

    public BlogSearchDocument() {}

    public BlogSearchDocument(KakaoBlogSearchDocument kakaoBlogSearchDocument) {
        this.title = kakaoBlogSearchDocument.getTitle();
        this.contents = kakaoBlogSearchDocument.getContents();
        this.url = kakaoBlogSearchDocument.getUrl();
        this.blogName = kakaoBlogSearchDocument.getBlogname();
        this.thumbNail = kakaoBlogSearchDocument.getThumbnail();
        this.size = kakaoBlogSearchDocument.getSize();
        this.page = kakaoBlogSearchDocument.getPage();
        this.current = kakaoBlogSearchDocument.getCurrent();

//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        ZoneOffset offset = ZoneOffset.of("+09:00");
        OffsetDateTime offsetDateTime = OffsetDateTime.of(kakaoBlogSearchDocument.getDatetime(), offset);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        this.dateTime = offsetDateTime.format(formatter);
    }

    public static List<BlogSearchDocument> makeDocumentsBySearchResult(List<KakaoBlogSearchDocument> kakaoBlogSearchDocuments) {
        return kakaoBlogSearchDocuments.stream()
                .map(BlogSearchDocument::new)
                .collect(toList());
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public String getUrl() {
        return url;
    }

    public String getBlogName() {
        return blogName;
    }

    public String getThumbNail() {
        return thumbNail;
    }

    public String getDateTime() {
        return dateTime;
    }

    public int getSize() {
        return size;
    }

    public int getPage() {
        return page;
    }

    public int getCurrent() {
        return current;
    }
}
