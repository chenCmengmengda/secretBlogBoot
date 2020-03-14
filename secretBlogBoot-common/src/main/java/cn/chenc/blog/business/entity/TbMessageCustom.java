package cn.chenc.blog.business.entity;

public class TbMessageCustom extends TbMessage {
    TbMyreply myreply;

    public TbMyreply getMyreply() {
        return myreply;
    }

    public void setMyreply(TbMyreply myreply) {
        this.myreply = myreply;
    }
}
