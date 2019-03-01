package cn.madeai.blog;

import java.util.List;

public class Post {
    private String title;
    private String date;
    private String raw;
    private String updated;
    private String permalink;
    private List<Tag> tags;

    protected static class Tag{
        private String name;
        private String slug;
        private String permalink;

        public Tag(String name, String slug, String permalink) {
            this.name = name;
            this.slug = slug;
            this.permalink = permalink;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public String getPermalink() {
            return permalink;
        }

        public void setPermalink(String permalink) {
            this.permalink = permalink;
        }
    }

    public Post(String title, String date, String raw, String updated, String permalink, List<Tag> tags) {
        this.title = title;
        this.date = date;
        this.raw = raw;
        this.updated = updated;
        this.permalink = permalink;
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "title:"+this.title+"date:"+this.date+"tags";
    }
}
