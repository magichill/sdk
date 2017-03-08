package com.bingdou.core.repository.user;

import com.bingdou.core.mapper.user.ICloudTokenMapper;
import com.bingdou.core.model.CloudToken;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by gaoshan on 17/3/4.
 */
@Repository
public class CloudTokenDao {

    @Autowired
    private ICloudTokenMapper cloudTokenMapper;

    public CloudToken getCloudToken(Integer userId) {
        if (userId <= 0)
            return null;
        return cloudTokenMapper.getCloudToken(userId);
    }

    public boolean addOrUpdateCloudToken(Integer userId, String token, String device,
                                        String requestSource) {
        if (userId == null || userId <= 0 || StringUtils.isEmpty(token) ){
            return false;
        }
        if (existCloudToken(userId)) {
            cloudTokenMapper.updateCloudToken(userId, token, device, requestSource);
        } else {
            cloudTokenMapper.insertCloudToken(userId, token, device, requestSource);
        }
        return true;
    }

    private boolean existCloudToken(Integer userId) {
        int count = cloudTokenMapper.existCloudToken(userId);
        return count > 0;
    }
}
