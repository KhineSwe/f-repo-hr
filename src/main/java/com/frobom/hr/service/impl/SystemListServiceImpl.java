package com.frobom.hr.service.impl;

import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frobom.hr.entity.LoginUserAccount;
import com.frobom.hr.entity.SystemList;
import com.frobom.hr.repository.LoginUserAccountRepository;
import com.frobom.hr.repository.SystemAccountRepository;
import com.frobom.hr.repository.SystemListRepository;
import com.frobom.hr.service.SystemListService;

@Service
public class SystemListServiceImpl implements SystemListService {

    @Autowired
    private SystemListRepository systemListRepository;

    @Autowired
    private LoginUserAccountRepository loginUserAccountRepository;

    @Override
    @Transactional
    public void save(SystemList system) {
        systemListRepository.save(system);
    }

    @Override
    @Transactional
    public void delete(SystemList system) {

        int id = system.getId();
        List<LoginUserAccount> users = loginUserAccountRepository.findBySystem(id);
        for (LoginUserAccount user : users) {
            user.setDeleted("1");
        }

        Calendar cal = Calendar.getInstance();
        system.setArchivedTime(cal.getTime());
        system.setDeleted("1");
        systemListRepository.save(system);

    }

    @Override
    @Transactional(readOnly = true)
    public List<SystemList> findAll() {
        return this.systemListRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public SystemList findById(int id) {
        return this.systemListRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public SystemList findBySystemId(String systemId) {
        return this.systemListRepository.findBySystemId(systemId);
    }

    @Override
    @Transactional
    public SystemList findExistedBySystemId(int id, String systemId) {
        return this.systemListRepository.findExistedBySystemId(id, systemId);
    }

}
