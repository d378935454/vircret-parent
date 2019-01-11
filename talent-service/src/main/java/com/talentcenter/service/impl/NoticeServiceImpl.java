package com.talentcenter.service.impl;

import com.talentcenter.dao.NoticeMapper;
import com.talentcenter.entity.Notice;
import com.talentcenter.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("NoticeService")
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public List<Notice> selectAll() {
        return noticeMapper.selectAll();
    }

    @Override
    public List<Notice> select(Notice notice) {
        return noticeMapper.select(notice);
    }

    @Override
    public List<Notice> selectByInfo(Object o) {
       return noticeMapper.selectByExample(o);
//        return noticeMapper.selectByInfo(o);
    }

    @Override
    public Notice selectByPrimaryKey(Long id) {
        return noticeMapper.selectByPrimaryKey(id);
    }

    @Override
    public Notice selectOne(Notice notice) {
        return noticeMapper.selectOne(notice);
    }

    @Override
    public int insert(Notice notice) {
        return noticeMapper.insert(notice);
    }

    @Override
    public int insertSelective(Notice notice) {
        return noticeMapper.insertSelective(notice);
    }

    @Override
    public int insertList(List<Notice> notices) {
        return noticeMapper.insertList(notices);
    }

    @Override
    public int updateByPrimaryKey(Notice notice) {
        return noticeMapper.updateByPrimaryKey(notice);
    }

    @Override
    public int updateByPrimaryKeySelective(Notice notice) {
        return noticeMapper.updateByPrimaryKeySelective(notice);
    }

    @Override
    public int selectCount(Notice notice) {
        return noticeMapper.selectCount(notice);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public List<Notice> selectByInfo(Map<String, Object> map) {
        return null;
    }

    @Override
    public int batchDel(Map<String, Object> map) {
        return noticeMapper.batchDel(map);
    }

    @Override
    public List<Notice> selectIndexNotic(Notice notice) {
        return noticeMapper.selectIndexNotic(notice);
    }
}
