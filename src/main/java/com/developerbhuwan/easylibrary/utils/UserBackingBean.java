/*
 * Copyright (c) 2015 Developer Bhuwan
 * Author : Bhuwan Pd. Upadhyay 
 * Created at : 2015-00-12
 * Website : http://www.bhuwanupadhyay.com.np
 */
package com.developerbhuwan.easylibrary.utils;

import com.developerbhuwan.easylibrary.ejbs.AppUserFacade;
import com.developerbhuwan.easylibrary.entities.AppUser;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;

/**
 *
 * @author Bhuwan Pd. Upadhyay
 */
@Named(value = "userBackingBean")
@RequestScoped
public class UserBackingBean extends BaseBacking {

    @EJB
    private AppUserFacade appUserFacade;

    @Produces
    @Named(value = "loggedUser")
    @RequestScoped
    private AppUser loggedUser = new AppUser();

    /**
     * Creates a new instance of UserBackingBean
     */
    public UserBackingBean() {
    }

    @PostConstruct
    public void loadUser() {
        try {
            String userID = getRequest().getUserPrincipal().getName();
            AppUser user = appUserFacade.find(userID);
            this.loggedUser.setUsername(user.getUsername());
            this.loggedUser.setFirstName(user.getFirstName());
            this.loggedUser.setMiddleName(user.getMiddleName());
            this.loggedUser.setLastName(user.getLastName());
            this.loggedUser.setPicture(user.getPicture());
        } catch (Exception ex) {
            Logger.getLogger(UserBackingBean.class.getName()).log(Level.SEVERE, null, ex);
            getContext().addMessage(null, new FacesMessage(SYSTEM_ERROR));
        }
    }

    public String logoutUser() {
        try {
            getRequest().logout();
            return "/home?faces-redirect=true";
        } catch (Exception ex) {
            Logger.getLogger(UserBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(c.get(Calendar.YEAR)));
        sb.append("-");
        sb.append(String.valueOf(c.get(Calendar.MONTH) + 1));
        sb.append("-");
        sb.append(String.valueOf(c.get(Calendar.DAY_OF_MONTH) + 1));
        return sb.toString();
    }
}
