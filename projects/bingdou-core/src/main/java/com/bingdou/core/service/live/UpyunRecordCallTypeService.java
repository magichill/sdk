package com.bingdou.core.service.live;

import com.bingdou.core.model.live.RecordType;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaoshan on 16-11-25.
 */
@Service
public class UpyunRecordCallTypeService implements IRecordCallTypeService {


    @Override
    public String getCallType() {
        return RecordType.UPYUN.getName();
    }

    @Override
    public boolean dealRecordCallback(HttpServletRequest request) {
        return false;
    }
}
