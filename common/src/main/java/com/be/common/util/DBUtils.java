package com.be.common.util;

import com.be.common.entity.BaseEntity;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings(value = {"unchecked"})
public class DBUtils {


    private static EntityManager _entityManager;

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        DBUtils._entityManager = entityManager;
    }

    public static List find(String sql) {
        return find(null, sql);

    }

    public static List find(EntityManager entityManager, String sql) {
        return findList(entityManager, sql, null);
    }

    public static List findList(EntityManager entityManager, String sql, Class clazz) {
        return find(entityManager, sql, null, clazz);
    }


    public static List find(EntityManager entityManager, String sql, Object[] params, Class clazz) {
        if (entityManager == null) entityManager = _entityManager;
        Query query = getQuery(entityManager, sql, clazz);
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i + 1, params[1]);
            }
        }
        return query.getResultList();
    }

    public static Page find(EntityManager entityManager, String sql, Pageable pageable) {
        return find(entityManager, sql, null, null, pageable);
    }

    public static Page find(EntityManager entityManager, String sql, Class clazz, Pageable pageable) {
        return find(entityManager, sql, null, clazz, pageable);
    }

    public static Page find(EntityManager entityManager, String sql, Object[] params, Pageable pageable) {
        return find(entityManager, sql, params, null, pageable);
    }


    public static Page find(EntityManager entityManager, String sql, Object[] params, Class clazz, Pageable pageable) {
        if (pageable == null) pageable = PageRequest.of(0, 1000);
        int size = pageable.getPageSize() < 1 ? 1000 : pageable.getPageSize();
        int page = pageable.getPageNumber() < 0 ? 1 : pageable.getPageNumber();
        if (entityManager == null) entityManager = _entityManager;
        String cSQL = String.format("select count(*) c from (%s) _c", sql);
        Query cQuery = entityManager.createNativeQuery(cSQL);
        Query query = getQuery(entityManager, sql, clazz);
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i + 1, params[0]);
                cQuery.setParameter(i + 1, params[0]);
            }
        }
        BigInteger count = (BigInteger) cQuery.getSingleResult();
        if (count.longValue() == 0) return new PageImpl<BaseEntity>(new ArrayList<>(), pageable, count.longValue());
        query.setFirstResult(page * size);
        query.setMaxResults((page + 1) * size);
        return new PageImpl<BaseEntity>(query.getResultList(), pageable, count.longValue());
    }


    public static void findOne(EntityManager entityManager, String sql, Object[] parms) {

    }

    public int execute(EntityManager entityManager, String sql, Object[] params) {
        if (entityManager == null) entityManager = _entityManager;
        Query query = entityManager.createNativeQuery(sql);
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i + 1, params[i]);
            }
        }
        return query.executeUpdate();
    }

    public int execute(EntityManager entityManager, String sql) {
        return execute(entityManager, sql, null);
    }

    public int execute(String sql) {
        return execute(_entityManager, sql, null);
    }


    private static Query getQuery(EntityManager entityManager, String sql, Class clazz) {
        if (entityManager == null) entityManager = _entityManager;
        if (clazz == null)
            return entityManager.createNativeQuery(sql).unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setMaxResults(5000);
        if (BaseEntity.class.isAssignableFrom(clazz))
            return entityManager.createNativeQuery(sql, clazz).setMaxResults(5000);
        else
            return entityManager.createNativeQuery(sql).unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(clazz)).setMaxResults(5000);

    }
}
