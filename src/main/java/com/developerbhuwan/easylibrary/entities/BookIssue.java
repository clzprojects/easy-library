/*
 * Copyright (c) 2015 Developer Bhuwan
 * Author : Bhuwan Pd. Upadhyay 
 * Created at : 2015-00-23
 * Website : http://www.bhuwanupadhyay.com.np
 */
package com.developerbhuwan.easylibrary.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Bhuwan Pd. Upadhyay
 */
@Entity
@Table(name = "book_issue")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BookIssue.findAll", query = "SELECT b FROM BookIssue b"),
    @NamedQuery(name = "BookIssue.findByIssueId", query = "SELECT b FROM BookIssue b WHERE b.issueId = :issueId"),
    @NamedQuery(name = "BookIssue.findByIssueSid", query = "SELECT b FROM BookIssue b WHERE b.studentId = :studentId"),
    @NamedQuery(name = "BookIssue.findByIssuedDate", query = "SELECT b FROM BookIssue b WHERE b.issuedDate = :issuedDate"),
    @NamedQuery(name = "BookIssue.findByIssuedReturnDate", query = "SELECT b FROM BookIssue b WHERE b.issuedReturnDate = :issuedReturnDate")})
public class BookIssue implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "issue_id")
    private String issueId;
    @Column(name = "issued_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date issuedDate;
    @Column(name = "issued_return_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date issuedReturnDate;
    @JoinColumn(name = "book_id", referencedColumnName = "book_id")
    @ManyToOne(optional = false)
    private Book bookId;
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    @ManyToOne(optional = false)
    private Student studentId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bookIssueId")
    private Collection<IssueReturn> issueReturnCollection;

    public BookIssue() {
    }

    public BookIssue(String issueId) {
        this.issueId = issueId;
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public Date getIssuedReturnDate() {
        return issuedReturnDate;
    }

    public void setIssuedReturnDate(Date issuedReturnDate) {
        this.issuedReturnDate = issuedReturnDate;
    }

    public Book getBookId() {
        return bookId;
    }

    public void setBookId(Book bookId) {
        this.bookId = bookId;
    }

    public Student getStudentId() {
        return studentId;
    }

    public void setStudentId(Student studentId) {
        this.studentId = studentId;
    }

    @XmlTransient
    public Collection<IssueReturn> getIssueReturnCollection() {
        return issueReturnCollection;
    }

    public void setIssueReturnCollection(Collection<IssueReturn> issueReturnCollection) {
        this.issueReturnCollection = issueReturnCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (issueId != null ? issueId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BookIssue)) {
            return false;
        }
        BookIssue other = (BookIssue) object;
        if ((this.issueId == null && other.issueId != null) || (this.issueId != null && !this.issueId.equals(other.issueId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return issueId;
    }
    
}
