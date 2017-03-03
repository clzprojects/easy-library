package com.developerbhuwan.easylibrary.controllers;

import com.developerbhuwan.easylibrary.entities.BookIssue;
import com.developerbhuwan.easylibrary.controllers.util.JsfUtil;
import com.developerbhuwan.easylibrary.controllers.util.PaginationHelper;
import com.developerbhuwan.easylibrary.ejbs.BookFacade;
import com.developerbhuwan.easylibrary.ejbs.BookIssueFacade;
import com.developerbhuwan.easylibrary.entities.Book;
import com.developerbhuwan.easylibrary.entities.Student;

import java.io.Serializable;
import java.util.List;
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

@Named("bookIssueController")
@RequestScoped
public class BookIssueController implements Serializable {

    @EJB
    private BookFacade bookFacade;

    private static final long serialVersionUID = 1L;

    private BookIssue current;
    private DataModel items = null;
    @EJB
    private com.developerbhuwan.easylibrary.ejbs.BookIssueFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public BookIssueController() {
    }

    public BookIssue getSelected() {
        if (current == null) {
            current = new BookIssue();
            selectedItemIndex = -1;
        }
        return current;
    }

    private BookIssueFacade getFacade() {
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
        current = (BookIssue) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new BookIssue();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            Book book = current.getBookId();
            Student student = current.getStudentId();
            if (!getFacade().isLimitAvailable(student)) {
                JsfUtil.addErrorMessage("Student can't issue more book.");
                return null;
            }
            if (book.getStatus().equalsIgnoreCase("Available")) {
                getFacade().create(current);
                book.setStatus("Not Available");
                bookFacade.edit(book);
            } else {
                JsfUtil.addErrorMessage("Book already issued.");
                return null;
            }
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/BookIssue").getString("BookIssueCreated"));
            return prepareCreate();
        } catch (Exception e) {
            if (e.getMessage().contains("Duplicate")) {
                JsfUtil.addErrorMessage("Duplicate Issue Id");
                return null;
            }
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/BookIssue").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (BookIssue) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/BookIssue").getString("BookIssueUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/BookIssue").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (BookIssue) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/BookIssue").getString("BookIssueDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/BookIssue").getString("PersistenceErrorOccured"));
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

    public BookIssue getBookIssue(java.lang.String id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = BookIssue.class)
    public static class BookIssueControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BookIssueController controller = (BookIssueController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "bookIssueController");
            return controller.getBookIssue(getKey(value));
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
            if (object instanceof BookIssue) {
                BookIssue o = (BookIssue) object;
                return getStringKey(o.getIssueId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + BookIssue.class.getName());
            }
        }

    }

}
