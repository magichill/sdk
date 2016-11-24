package com.bingdou.core.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * service工场类
 */
public class MethodServiceFactory {

    private Map<String, IMethodService> methodServiceMap;

    public synchronized void setMethodServiceList(List<IMethodService> methodServiceList) {
        if (methodServiceMap == null)
            methodServiceMap = new HashMap<String, IMethodService>();
        for (IMethodService method : methodServiceList) {
            String methodName = method.getMethodName();
            if (!methodServiceMap.containsKey(methodName)) {
                methodServiceMap.put(methodName, method);
            }
        }
    }

    public Map<String, IMethodService> getMethodServiceMap() {
        return methodServiceMap;
    }

}
