package com.bingdou.core.service.safe;

import com.bingdou.core.cache.MonitorJedisCacheManager;
import com.bingdou.core.repository.safe.MonitorDao;
import com.bingdou.tools.LogContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by gaoshan on 16-11-24.
 */
@Service
public class MonitorService {

    @Autowired
    private MonitorDao monitorDao;

    @Autowired
    private MonitorJedisCacheManager monitorJedisCacheManager;

    public boolean monitorRedis() {
        return monitorJedisCacheManager.monitor();
    }

    public boolean monitorUserDB() {
        boolean result = false;
        try {
            int num = monitorDao.monitorUserDB();
            result = num >= 0;
        } catch (Exception e) {
            LogContext.instance().error(e, "监控用户库错误");
        }
        return result;
    }

    public boolean monitorPayDB() {
        boolean result = false;
        try {
            int num = monitorDao.monitorPayDB();
            result = num >= 0;
        } catch (Exception e) {
            LogContext.instance().error(e, "监控支付库错误");
        }
        return result;
    }

    public boolean monitorLiveDB() {
        boolean result = false;
        try {
            int num = monitorDao.monitorLiveDB();
            result = num >= 0;
        } catch (Exception e) {
            LogContext.instance().error(e, "监控直播库错误");
        }
        return result;
    }
}
