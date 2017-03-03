/*
 * Copyright (c) 2015 Developer Bhuwan
 * Author : Bhuwan Pd. Upadhyay 
 * Created at : 2015-00-23
 * Website : http://www.bhuwanupadhyay.com.np
 */
package com.developerbhuwan.easylibrary.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Bhuwan Pd. Upadhyay
 */
@Entity
@Table(name = "issue_return")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IssueReturn.findAll", query = "SELECT i FROM IssueReturn i"),
    @NamedQuery(name = "IssueReturn.findByReturnId", query = "SELECT i FROM IssueReturn i WHERE i.returnId = :returnId"),
    @NamedQuery(name = "IssueReturn.findByReturnDate", query = "SELECT i FROM IssueReturn i WHERE i.returnDate = :returnDate")})
public class IssueReturn implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "return_id")
    private String returnId;
    @Column(name = "return_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date returnDate;
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    @ManyToOne(optional = false)
    private Student studentId;
    @JoinColumn(name = "book_issue_id", referencedColumnName = "issue_id")
    @ManyToOne(optional = false)
    private BookIssue bookIssueId;

    public IssueReturn() {
    }

    public IssueReturn(String returnId) {
        this.returnId = returnId;
    }

    public String getReturnId() {
        return returnId;
    }

    public void setReturnId(String returnId) {
        this.returnId = returnId;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Student getStudentId() {
        return studentId;
    }

    public void setStudentId(Student studentId) {
        this.studentId = studentId;
    }

    public BookIssue getBookIssueId() {
        return bookIssueId;
    }

    public void setBookIssueId(BookIssue bookIssueId) {
        this.bookIssueId = bookIssueId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (returnId != null ? returnId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IssueReturn)) {
            return false;
        }
        IssueReturn other = (IssueReturn) object;
        if ((this.returnId == null && other.returnId != null) || (this.returnId != null && !this.returnId.equals(other.returnId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return returnId;
    }
    
}
