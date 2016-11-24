package com.bingdou.core.repository.user;

import com.bingdou.core.mapper.user.ISmsSendRecordMapper;
import com.bingdou.core.model.SmsSendRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SmsSendRecordDao {

    @Autowired
    private ISmsSendRecordMapper smsSendRecordMapper;

    public void insertRecord(SmsSendRecord smsSendRecord) {
        smsSendRecordMapper.insertRecord(smsSendRecord);
    }

}
