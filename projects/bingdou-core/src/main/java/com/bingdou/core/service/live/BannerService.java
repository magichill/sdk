package com.bingdou.core.service.live;

import com.bingdou.core.model.live.Banner;
import com.bingdou.core.repository.live.BannerDao;
import com.bingdou.tools.LogContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by gaoshan on 17/4/22.
 */
@Service
public class BannerService {

    @Autowired
    private BannerDao bannerDao;

    public List<Banner> getBannerList(){
        LogContext.instance().info("获取banner列表");
        return bannerDao.getBannerList();
    }
}
