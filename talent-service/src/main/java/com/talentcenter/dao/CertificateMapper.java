package com.talentcenter.dao;

import com.talentcenter.entity.Certificate;
import mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface CertificateMapper extends BaseMapper<Certificate> {

    int batchDel(Map<String, Object> map);

}