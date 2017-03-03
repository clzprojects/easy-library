/*
 * Copyright (c) 2015 Developer Bhuwan
 * Author : Bhuwan Pd. Upadhyay 
 * Created at : 2015-00-23
 * Website : http://www.bhuwanupadhyay.com.np
 */
package com.developerbhuwan.easylibrary.ejbs;

import com.developerbhuwan.easylibrary.entities.BookIssue;
import com.developerbhuwan.easylibrary.entities.Student;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Bhuwan Pd. Upadhyay
 */
@Stateless
public class BookIssueFacade extends AbstractFacade<BookIssue> {
    @PersistenceContext(unitName = "EasyLibrary_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BookIssueFacade() {
        super(BookIssue.class);
    }
    
    public boolean isLimitAvailable(Student studentId) {
        Query query = em.createNamedQuery("BookIssue.findByIssueSid").setParameter("studentId", studentId);
        List resultList = query.getResultList();
        return resultList.size() < 5;
    }
    
}
