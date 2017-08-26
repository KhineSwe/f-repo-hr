package com.frobom.hr.repository;

import java.util.List;

import com.frobom.hr.entity.SystemList;

public interface SystemListRepository {

    void save(SystemList systemList);

    List<SystemList> findAll();

    SystemList findById(int id);

    SystemList findBySystemId(String systemId);

    SystemList findExistedBySystemId(int id, String systemId);

}
