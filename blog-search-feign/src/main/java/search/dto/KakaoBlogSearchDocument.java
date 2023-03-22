package search.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class KakaoBlogSearchDocument {
    private String title;
    private String contents;
    private String url;
    private String blogname;
    private String thumbnail;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private LocalDateTime datetime;

    private int size;
    private int current;
    private int page;

    public KakaoBlogSearchDocument() {}

    public KakaoBlogSearchDocument(
            String title
            , String contents
            , String url
            , String blogName
            , String thumbNail
            , LocalDateTime dateTime
    ) {
        this.title = title;
        this.contents = contents;
        this.url = url;
        this.blogname = blogName;
        this.thumbnail = thumbNail;
        this.datetime = dateTime;
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

    public String getBlogname() {
        return blogname;
    }

    public void setBlogname(String blogname) {
        this.blogname = blogname;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public int getSize() {
        return size;
    }

    public int getCurrent() {
        return current;
    }

    public int getPage() {
        return page;
    }

    public void makePagination(int page, int size, int current) {
        this.size = size;
        this.page = page;
        this.current = current;
    }
}
