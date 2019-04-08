package com.talentcenter.service;


import com.talentcenter.entity.ItemCertificate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ItemCertificateService extends BaseService<ItemCertificate> {

    int batchDel(Map<String, Object> map);

    int delByItemConfigId(Long itemConfigId);

    List<ItemCertificate> selectByItemId(Long configId);
}
