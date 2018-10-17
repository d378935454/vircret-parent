package com.talentcenter.service;


import com.talentcenter.entity.Certificate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface CertificateService extends BaseService<Certificate> {

    int batchDel(Map<String, Object> map);

}
