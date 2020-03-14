package cn.chenc.blog.business.entity;

public class TbBlogWithBLOBs extends TbBlog {
    private String summary;

    private String blogDesc;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public String getBlogDesc() {
        return blogDesc;
    }

    public void setBlogDesc(String blogDesc) {
        this.blogDesc = blogDesc == null ? null : blogDesc.trim();
    }
}
