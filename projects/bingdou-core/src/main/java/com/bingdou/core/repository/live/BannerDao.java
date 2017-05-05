package com.bingdou.core.repository.live;

import com.bingdou.core.mapper.live.IBannerMapper;
import com.bingdou.core.model.live.Banner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gaoshan on 17/4/22.
 */
@Repository
public class BannerDao {

    @Autowired
    private IBannerMapper bannerMapper;

    public List<Banner> getBannerList(){
        return bannerMapper.getBannerList();
    }
}
