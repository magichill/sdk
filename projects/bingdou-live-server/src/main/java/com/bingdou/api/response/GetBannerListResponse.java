package com.bingdou.api.response;

import com.bingdou.core.model.live.Banner;
import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gaoshan on 17/4/22.
 */
public class GetBannerListResponse {

    @SerializedName("banners")
    private List<BannerResponse> banners;

    public List<BannerResponse> getBanners() {
        return banners;
    }

    public void setBanners(List<BannerResponse> banners) {
        this.banners = banners;
    }

    class BannerResponse {

        @SerializedName("id")
        private Integer id;
        @SerializedName("picture_url")
        private String pictureUrl;

        @SerializedName("title")
        private String title;

        @SerializedName("schema")
        private String schema;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getPictureUrl() {
            return pictureUrl;
        }

        public void setPictureUrl(String pictureUrl) {
            this.pictureUrl = pictureUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSchema() {
            return schema;
        }

        public void setSchema(String schema) {
            this.schema = schema;
        }
    }

    public void parseFromBanners(List<Banner> bannerList){
        if(bannerList == null || bannerList.isEmpty()){
            return ;
        }
        List<BannerResponse> bannerResponses = Lists.newArrayList();
        for(Banner banner : bannerList){
            BannerResponse bannerResponse = new BannerResponse();
            bannerResponse.setId(banner.getId());
            bannerResponse.setPictureUrl(banner.getBannerPic());
            bannerResponse.setTitle(banner.getBannerTitle());
            bannerResponse.setSchema(banner.getBannerContent());
            bannerResponses.add(bannerResponse);
        }
        setBanners(bannerResponses);
    }
}
