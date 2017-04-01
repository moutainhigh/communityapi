package net.okdi.apiV4.entity;

public class ContentHis {

    private String name;

    private String content;

    private Long time;

    public ContentHis() {
    }

    public ContentHis(String content, String name, Long time) {
        this.content = content;
        this.name = name;
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
