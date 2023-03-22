package search.dto;

import java.time.LocalDate;

public class KakaoBlogSearchDocument {
    private String title;
    private String contents;
    private String url;
    private String blogName;
    private String thumbNail;
    private LocalDate dateTime;

    public KakaoBlogSearchDocument() {}

    public KakaoBlogSearchDocument(
            String title
            , String contents
            , String url
            , String blogName
            , String thumbNail
            , LocalDate dateTime
    ) {
        this.title = title;
        this.contents = contents;
        this.url = url;
        this.blogName = blogName;
        this.thumbNail = thumbNail;
        this.dateTime = dateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBlogName() {
        return blogName;
    }

    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }

    public String getThumbNail() {
        return thumbNail;
    }

    public void setThumbNail(String thumbNail) {
        this.thumbNail = thumbNail;
    }

    public LocalDate getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
    }
}
