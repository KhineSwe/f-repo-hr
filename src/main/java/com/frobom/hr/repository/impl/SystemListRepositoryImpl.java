package com.frobom.hr.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.frobom.hr.entity.SystemList;
import com.frobom.hr.entity.User;
import com.frobom.hr.repository.SystemListRepository;

@Repository
public class SystemListRepositoryImpl implements SystemListRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(SystemList systemList) {

        if (systemList.getId() == null) {
            entityManager.persist(systemList);
        } else {
            entityManager.merge(systemList);
        }

    }

    @Override
    public List<SystemList> findAll() {
        Query query = entityManager.createQuery("SELECT s FROM SystemList s where s.deleted='0'", SystemList.class);
        return query.getResultList();
    }

    @Override
    public SystemList findById(int id) {
        return entityManager.find(SystemList.class, id);
    }

    @Override
    public SystemList findBySystemId(String systemId) {

        Query query = this.entityManager.createQuery("SELECT s from SystemList s WHERE s.systemId = ? and s.deleted = ?", SystemList.class);
        query.setParameter(1, systemId);
        query.setParameter(2, "0");
        SystemList systemList = null;
        try {
            systemList = (SystemList) query.getSingleResult();
        } catch (Exception e) {
            // to log
        }
        return systemList;
    }

    @Override
    public SystemList findExistedBySystemId(int id, String systemId) {

        Query query = this.entityManager.createQuery("SELECT s from SystemList s WHERE s.id != :id AND s.systemId = :systemId AND s.deleted = :deleted", SystemList.class);
        query.setParameter("id", id);
        query.setParameter("systemId", systemId);
        query.setParameter("deleted", "0");
        SystemList systemList = null;
        try {
            systemList = (SystemList) query.getSingleResult();
        } catch (Exception e) {

        }
        return systemList;
    }
}
