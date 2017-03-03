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
import javax.persistence.Lob;
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
@Table(name = "book")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Book.findAll", query = "SELECT b FROM Book b"),
    @NamedQuery(name = "Book.findByBookId", query = "SELECT b FROM Book b WHERE b.bookId = :bookId"),
    @NamedQuery(name = "Book.findByBookTitle", query = "SELECT b FROM Book b WHERE b.bookTitle = :bookTitle"),
    @NamedQuery(name = "Book.findByAuthorName", query = "SELECT b FROM Book b WHERE b.authorName = :authorName"),
    @NamedQuery(name = "Book.findByPublisherName", query = "SELECT b FROM Book b WHERE b.publisherName = :publisherName"),
    @NamedQuery(name = "Book.findByEdition", query = "SELECT b FROM Book b WHERE b.edition = :edition"),
    @NamedQuery(name = "Book.findByAmount", query = "SELECT b FROM Book b WHERE b.amount = :amount"),
    @NamedQuery(name = "Book.findByPublishedYear", query = "SELECT b FROM Book b WHERE b.publishedYear = :publishedYear"),
    @NamedQuery(name = "Book.findByNoOfPages", query = "SELECT b FROM Book b WHERE b.noOfPages = :noOfPages"),
    @NamedQuery(name = "Book.findByRackNo", query = "SELECT b FROM Book b WHERE b.rackNo = :rackNo"),
    @NamedQuery(name = "Book.findByStatus", query = "SELECT b FROM Book b WHERE b.status = :status"),
    @NamedQuery(name = "Book.findByNoOfBooks", query = "SELECT b FROM Book b WHERE b.noOfBooks = :noOfBooks"),
    @NamedQuery(name = "Book.findByRegisteredAt", query = "SELECT b FROM Book b WHERE b.registeredAt = :registeredAt")})
public class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "book_id")
    private String bookId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "book_title")
    private String bookTitle;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "author_name")
    private String authorName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "publisher_name")
    private String publisherName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "edition")
    private int edition;
    @Basic(optional = false)
    @NotNull
    @Column(name = "amount")
    private double amount;
    @Column(name = "published_year")
    @Temporal(TemporalType.TIMESTAMP)
    private Date publishedYear;
    @Basic(optional = false)
    @NotNull
    @Column(name = "no_of_pages")
    private int noOfPages;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "rack_no")
    private String rackNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "status")
    private String status;
    @Lob
    @Size(max = 65535)
    @Column(name = "remarks")
    private String remarks;
    @Basic(optional = false)
    @NotNull
    @Column(name = "no_of_books")
    private int noOfBooks;
    @Column(name = "registered_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registeredAt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bookId")
    private Collection<BookIssue> bookIssueCollection;
    @JoinColumn(name = "category_name", referencedColumnName = "category_name")
    @ManyToOne(optional = false)
    private BookCategory categoryName;

    public Book() {
    }

    public Book(String bookId) {
        this.bookId = bookId;
    }

    public Book(String bookId, String bookTitle, String authorName, String publisherName, int edition, double amount, int noOfPages, String rackNo, String status, int noOfBooks) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.authorName = authorName;
        this.publisherName = publisherName;
        this.edition = edition;
        this.amount = amount;
        this.noOfPages = noOfPages;
        this.rackNo = rackNo;
        this.status = status;
        this.noOfBooks = noOfBooks;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(Date publishedYear) {
        this.publishedYear = publishedYear;
    }

    public int getNoOfPages() {
        return noOfPages;
    }

    public void setNoOfPages(int noOfPages) {
        this.noOfPages = noOfPages;
    }

    public String getRackNo() {
        return rackNo;
    }

    public void setRackNo(String rackNo) {
        this.rackNo = rackNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getNoOfBooks() {
        return noOfBooks;
    }

    public void setNoOfBooks(int noOfBooks) {
        this.noOfBooks = noOfBooks;
    }

    public Date getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(Date registeredAt) {
        this.registeredAt = registeredAt;
    }

    @XmlTransient
    public Collection<BookIssue> getBookIssueCollection() {
        return bookIssueCollection;
    }

    public void setBookIssueCollection(Collection<BookIssue> bookIssueCollection) {
        this.bookIssueCollection = bookIssueCollection;
    }

    public BookCategory getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(BookCategory categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bookId != null ? bookId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Book)) {
            return false;
        }
        Book other = (Book) object;
        if ((this.bookId == null && other.bookId != null) || (this.bookId != null && !this.bookId.equals(other.bookId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return bookId;
    }
    
}
