package com.talentcenter.service.impl;

import com.talentcenter.dao.NoticeTemplateMapper;
import com.talentcenter.entity.NoticeTemplate;
import com.talentcenter.service.NoticeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("NoticeTemplateService")
public class NoticeTemplateServiceImpl implements NoticeTemplateService {
    @Autowired
    private NoticeTemplateMapper noticeTemplateMapper;

    @Override
    public List<NoticeTemplate> selectAll() {
        return noticeTemplateMapper.selectAll();
    }

    @Override
    public List<NoticeTemplate> select(NoticeTemplate noticeTemplate) {
        return noticeTemplateMapper.select(noticeTemplate);
    }

    @Override
    public List<NoticeTemplate> selectByInfo(Object o) {
       return noticeTemplateMapper.selectByExample(o);
//        return noticeTemplateMapper.selectByInfo(o);
    }

    @Override
    public NoticeTemplate selectByPrimaryKey(Long id) {
        return noticeTemplateMapper.selectByPrimaryKey(id);
    }

    @Override
    public NoticeTemplate selectOne(NoticeTemplate noticeTemplate) {
        return noticeTemplateMapper.selectOne(noticeTemplate);
    }

    @Override
    public int insert(NoticeTemplate noticeTemplate) {
        return noticeTemplateMapper.insert(noticeTemplate);
    }

    @Override
    public int insertSelective(NoticeTemplate noticeTemplate) {
        return noticeTemplateMapper.insertSelective(noticeTemplate);
    }

    @Override
    public int insertList(List<NoticeTemplate> noticeTemplates) {
        return noticeTemplateMapper.insertList(noticeTemplates);
    }

    @Override
    public int updateByPrimaryKey(NoticeTemplate noticeTemplate) {
        return noticeTemplateMapper.updateByPrimaryKey(noticeTemplate);
    }

    @Override
    public int updateByPrimaryKeySelective(NoticeTemplate noticeTemplate) {
        return noticeTemplateMapper.updateByPrimaryKeySelective(noticeTemplate);
    }

    @Override
    public int selectCount(NoticeTemplate noticeTemplate) {
        return noticeTemplateMapper.selectCount(noticeTemplate);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public List<NoticeTemplate> selectByInfo(Map<String, Object> map) {
        return null;
    }

    @Override
    public int batchDel(Map<String, Object> map) {
        return noticeTemplateMapper.batchDel(map);
    }
}
