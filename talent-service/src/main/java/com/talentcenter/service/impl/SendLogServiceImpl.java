package com.talentcenter.service.impl;

import com.talentcenter.dao.SendLogMapper;
import com.talentcenter.entity.SendLog;
import com.talentcenter.service.SendLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("SendLogService")
public class SendLogServiceImpl implements SendLogService {
    @Autowired
    private SendLogMapper sendLogMapper;

    @Override
    public List<SendLog> selectAll() {
        return sendLogMapper.selectAll();
    }

    @Override
    public List<SendLog> select(SendLog sendLog) {
        return sendLogMapper.select(sendLog);
    }

    @Override
    public List<SendLog> selectByInfo(Object o) {
       return sendLogMapper.selectByExample(o);
//        return sendLogMapper.selectByInfo(o);
    }

    @Override
    public SendLog selectByPrimaryKey(Long id) {
        return sendLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public SendLog selectOne(SendLog sendLog) {
        return sendLogMapper.selectOne(sendLog);
    }

    @Override
    public int insert(SendLog sendLog) {
        return sendLogMapper.insert(sendLog);
    }

    @Override
    public int insertSelective(SendLog sendLog) {
        return sendLogMapper.insertSelective(sendLog);
    }

    @Override
    public int insertList(List<SendLog> sendLogs) {
        return sendLogMapper.insertList(sendLogs);
    }

    @Override
    public int updateByPrimaryKey(SendLog sendLog) {
        return sendLogMapper.updateByPrimaryKey(sendLog);
    }

    @Override
    public int updateByPrimaryKeySelective(SendLog sendLog) {
        return sendLogMapper.updateByPrimaryKeySelective(sendLog);
    }

    @Override
    public int selectCount(SendLog sendLog) {
        return sendLogMapper.selectCount(sendLog);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public List<SendLog> selectByInfo(Map<String, Object> map) {
        return null;
    }

    @Override
    public int updateAmount(Map<String,Object> map){
        return sendLogMapper.updateAmount(map);
    }
}
