package com.bingdou.core.service.live;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaoshan on 16-11-25.
 */
public interface IRecordCallTypeService {

    String getCallType();
    void dealRecordCallback(HttpServletRequest request);
}
