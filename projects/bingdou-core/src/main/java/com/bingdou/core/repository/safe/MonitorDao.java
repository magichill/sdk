package com.bingdou.core.repository.safe;

import com.bingdou.core.mapper.safe.IMonitorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by gaoshan on 16-11-24.
 */
@Repository
public class MonitorDao {

    @Autowired
    private IMonitorMapper monitorMapper;

    public int monitorUserDB() {
        return monitorMapper.monitorUserDB();
    }

    public int monitorPayDB() {
        return monitorMapper.monitorPayDB();
    }

    public int monitorLiveDB() {
        return monitorMapper.monitorLiveDB();
    }
}
