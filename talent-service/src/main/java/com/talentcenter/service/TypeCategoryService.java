package com.talentcenter.service;


import com.talentcenter.entity.TypeCategory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface TypeCategoryService extends BaseService<TypeCategory> {

    int batchDel(Map<String, Object> map);

}
