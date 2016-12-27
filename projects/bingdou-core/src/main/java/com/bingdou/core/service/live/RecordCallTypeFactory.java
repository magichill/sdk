package com.bingdou.core.service.live;

import com.bingdou.core.model.live.RecordType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by gaoshan on 16-11-25.
 */
@Component
public class RecordCallTypeFactory {

    @Autowired
    private CncRecordCallTypeService cncRecordCallTypeService;

    @Autowired
    private UpyunRecordCallTypeService upyunRecordCallTypeService;

    @Autowired
    private CCRecordCallTypeService ccRecordCallTypeService;


    public IRecordCallTypeService getRecordTypeService(RecordType recordType) {
        if (RecordType.CNC.equals(recordType)) {
            return cncRecordCallTypeService;
        } else if (RecordType.UPYUN.equals(recordType)) {
            return upyunRecordCallTypeService;
        } else if (RecordType.CC.equals(recordType)) {
            return ccRecordCallTypeService;
        }
        return null;
    }

}
