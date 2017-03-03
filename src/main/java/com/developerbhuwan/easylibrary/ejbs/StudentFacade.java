/*
 * Copyright (c) 2015 Developer Bhuwan
 * Author : Bhuwan Pd. Upadhyay 
 * Created at : 2015-00-12
 * Website : http://www.bhuwanupadhyay.com.np
 */
package com.developerbhuwan.easylibrary.ejbs;

import com.developerbhuwan.easylibrary.entities.Student;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Bhuwan Pd. Upadhyay
 */
@Stateless
public class StudentFacade extends AbstractFacade<Student> {
    @PersistenceContext(unitName = "EasyLibrary_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StudentFacade() {
        super(Student.class);
    }
    
}
