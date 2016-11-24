package com.bingdou.core.cache;


import com.bingdou.core.model.SafeInfo;

/**
 *
 */
public interface ISafeCacheManager {

    SafeInfo getSafeInfo(String requestSourceIndex);

    boolean setSafeInfo(String requestSourceIndex, SafeInfo safeInfo);

}
