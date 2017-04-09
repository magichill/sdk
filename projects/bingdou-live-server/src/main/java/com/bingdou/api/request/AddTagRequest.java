package com.bingdou.api.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 17/4/4.
 */
public class AddTagRequest  extends BaseRequest {

    @SerializedName("tag_title")
    private String tagTitle;

    @SerializedName("tag_pic")
    private String tagPic;

    @SerializedName("select_pic")
    private String selectPic;

    @SerializedName("tag_type")
    private Integer tagType;


    @Override
    protected String getLoggerName() {
        return "AddTagRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        AddTagRequest request = JsonUtil.jsonStr2Bean(requestString, AddTagRequest.class);
        this.tagTitle = request.getTagTitle();
        this.tagPic = request.getTagPic();
        this.tagType = request.getTagType();
        this.selectPic = request.getSelectPic();
        return request;
    }

    public String getTagTitle() {
        return tagTitle;
    }

    public void setTagTitle(String tagTitle) {
        this.tagTitle = tagTitle;
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

    public String getSelectPic() {
        return selectPic;
    }

    public void setSelectPic(String selectPic) {
        this.selectPic = selectPic;
    }
}