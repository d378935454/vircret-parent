package com.talentcenter.dao;

import com.talentcenter.entity.Notice;
import mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {

    int batchDel(Map<String, Object> map);

}