package com.bingdou.cdn.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gaoshan on 16-11-11.
 */
public class CdnServiceFactory {

    private Map<String, ICdnService> cdnServiceMap;

    public synchronized void setCdnServiceList(List<ICdnService> cdnServiceList) {
        if (cdnServiceMap == null)
            cdnServiceMap = new HashMap<String, ICdnService>();
        for (ICdnService cdn : cdnServiceList) {
            String cdnName = cdn.getCdnName();
            if (!cdnServiceMap.containsKey(cdnName)) {
                cdnServiceMap.put(cdnName, cdn);
            }
        }
    }

    public Map<String, ICdnService> getCdnServiceMap() {
        return cdnServiceMap;
    }
}
