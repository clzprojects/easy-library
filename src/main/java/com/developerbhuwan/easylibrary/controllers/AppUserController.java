package com.developerbhuwan.easylibrary.controllers;

import com.developerbhuwan.easylibrary.entities.AppUser;
import com.developerbhuwan.easylibrary.controllers.util.JsfUtil;
import com.developerbhuwan.easylibrary.controllers.util.PaginationHelper;
import com.developerbhuwan.easylibrary.ejbs.AppGroupFacade;
import com.developerbhuwan.easylibrary.ejbs.AppUserFacade;
import com.developerbhuwan.easylibrary.entities.AppGroup;
import com.google.common.hash.Hashing;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

/**
 *
 * @author Bhuwan Pd. Upadhyay
 */
@Named("appUserController")
@RequestScoped
public class AppUserController implements Serializable {

    @EJB
    private com.developerbhuwan.easylibrary.ejbs.AppUserFacade ejbFacade;
    @EJB
    private AppGroupFacade appGroupFacade;
    
    private static final long serialVersionUID = -3577899435512875698L;

    private AppUser current;
    private DataModel items = null;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private String userGroup;

    public AppUserController() {
    }

    public AppUser getSelected() {
        if (current == null) {
            current = new AppUser();
            selectedItemIndex = -1;
        }
        return current;
    }

    private AppUserFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (AppUser) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new AppUser();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            String tempPass = current.getPassword();
            if (tempPass != null) {
                current.setPassword(Hashing.sha256().hashString(tempPass, Charset.forName("UTF-8")).toString());
            }
            getFacade().create(current);
            AppGroup appGroup = new AppGroup();
            appGroup.setUsername(current.getUsername());
            appGroup.setGroupname(userGroup);
            appGroupFacade.create(appGroup);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/bundle/AdminMessage").getString("AppUserCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/bundle/AdminMessage").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (AppUser) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/bundle/AdminMessage").getString("AppUserUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/bundle/AdminMessage").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (AppUser) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/bundle/AdminMessage").getString("AppUserDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/bundle/AdminMessage").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public AppUser getAppUser(java.lang.String id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = AppUser.class)
    public static class AppUserControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AppUserController controller = (AppUserController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "appUserController");
            return controller.getAppUser(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof AppUser) {
                AppUser o = (AppUser) object;
                return getStringKey(o.getUsername());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + AppUser.class.getName());
            }
        }

    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

}
