package com.frobom.hr.service;

import java.util.List;
import com.frobom.hr.entity.SystemList;

public interface SystemListService {

    void save(SystemList systemList);

    void delete(SystemList system);

    List<SystemList> findAll();

    SystemList findById(int id);

    SystemList findBySystemId(String systemId);

    SystemList findExistedBySystemId(int id, String systemId);

}
