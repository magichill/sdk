package com.bingdou.core.service.live;

import java.util.Map;

/**
 * Created by gaoshan on 16-11-25.
 */
public class RecordTypeCallBackResponse {

    /**
     * 回调的返回MAP
     */
    private Map<String, String> paramMap;

    public Map<String, String> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, String> paramMap) {
        this.paramMap = paramMap;
    }
}
