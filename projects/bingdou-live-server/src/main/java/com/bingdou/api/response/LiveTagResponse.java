package com.bingdou.api.response;

import com.bingdou.core.model.live.VideoTag;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 17/4/6.
 */
public class LiveTagResponse {

    @SerializedName("id")
    private Integer id;
    @SerializedName("title")
    private String title;
    @SerializedName("tag_pic")
    private String tagPic;
    @SerializedName("select_pic")
    private String selectPic;
    @SerializedName("tag_type")
    private Integer tagType;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTagPic() {
        return tagPic;
    }

    public void setTagPic(String tagPic) {
        this.tagPic = tagPic;
    }

    public Integer getTagType() {
        return tagType;
    }

    public void setTagType(Integer tagType) {
        this.tagType = tagType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
            this.id = id;
        }

    public String getSelectPic() {
        return selectPic;
    }

    public void setSelectPic(String selectPic) {
        this.selectPic = selectPic;
    }

    public void parseFromTag(VideoTag tag){
        if(tag==null){
            return;
        }
        setId(tag.getId());
        setTagPic(tag.getTagPic());
        setTagType(tag.getTagType());
        setTitle(tag.getTitle());
        setSelectPic(tag.getSelectPic());
    }
}
