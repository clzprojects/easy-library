/*
 * Copyright (c) 2015 Developer Bhuwan
 * Author : Bhuwan Pd. Upadhyay 
 * Created at : 2015-00-23
 * Website : http://www.bhuwanupadhyay.com.np
 */
package com.developerbhuwan.easylibrary.entities;

import java.io.Serializable;
import java.util.Collection;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Bhuwan Pd. Upadhyay
 */
@Entity
@Table(name = "student")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Student.findAll", query = "SELECT s FROM Student s"),
    @NamedQuery(name = "Student.findByStudentId", query = "SELECT s FROM Student s WHERE s.studentId = :studentId"),
    @NamedQuery(name = "Student.findByStudentName", query = "SELECT s FROM Student s WHERE s.studentName = :studentName"),
    @NamedQuery(name = "Student.findByGender", query = "SELECT s FROM Student s WHERE s.gender = :gender"),
    @NamedQuery(name = "Student.findByCurrentAddress", query = "SELECT s FROM Student s WHERE s.currentAddress = :currentAddress"),
    @NamedQuery(name = "Student.findByEmail", query = "SELECT s FROM Student s WHERE s.email = :email"),
    @NamedQuery(name = "Student.findByPhoneNo", query = "SELECT s FROM Student s WHERE s.phoneNo = :phoneNo"),
    @NamedQuery(name = "Student.findByClassLevel", query = "SELECT s FROM Student s WHERE s.classLevel = :classLevel"),
    @NamedQuery(name = "Student.findByBatchYear", query = "SELECT s FROM Student s WHERE s.batchYear = :batchYear"),
    @NamedQuery(name = "Student.findByYearOrSemester", query = "SELECT s FROM Student s WHERE s.yearOrSemester = :yearOrSemester"),
    @NamedQuery(name = "Student.findByClassSection", query = "SELECT s FROM Student s WHERE s.classSection = :classSection"),
    @NamedQuery(name = "Student.findByStatus", query = "SELECT s FROM Student s WHERE s.status = :status"),
    @NamedQuery(name = "Student.findByPicture", query = "SELECT s FROM Student s WHERE s.picture = :picture")})
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "student_id")
    private String studentId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "student_name")
    private String studentName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "gender")
    private String gender;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "current_address")
    private String currentAddress;
    @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "phone_no")
    private String phoneNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "class_level")
    private String classLevel;
    @Basic(optional = false)
    @NotNull
    @Column(name = "batch_year")
    private int batchYear;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "year_or_semester")
    private String yearOrSemester;
    @Size(max = 45)
    @Column(name = "class_section")
    private String classSection;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "status")
    private String status;
    @Size(max = 150)
    @Column(name = "picture")
    private String picture;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "studentId")
    private Collection<BookIssue> bookIssueCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "studentId")
    private Collection<IssueReturn> issueReturnCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "studentId")
    private Collection<Fine> fineCollection;
    @JoinColumn(name = "category_name", referencedColumnName = "category_name")
    @ManyToOne(optional = false)
    private StudentCategory categoryName;

    public Student() {
    }

    public Student(String studentId) {
        this.studentId = studentId;
    }

    public Student(String studentId, String studentName, String gender, String currentAddress, String email, String phoneNo, String classLevel, int batchYear, String yearOrSemester, String status) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.gender = gender;
        this.currentAddress = currentAddress;
        this.email = email;
        this.phoneNo = phoneNo;
        this.classLevel = classLevel;
        this.batchYear = batchYear;
        this.yearOrSemester = yearOrSemester;
        this.status = status;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getClassLevel() {
        return classLevel;
    }

    public void setClassLevel(String classLevel) {
        this.classLevel = classLevel;
    }

    public int getBatchYear() {
        return batchYear;
    }

    public void setBatchYear(int batchYear) {
        this.batchYear = batchYear;
    }

    public String getYearOrSemester() {
        return yearOrSemester;
    }

    public void setYearOrSemester(String yearOrSemester) {
        this.yearOrSemester = yearOrSemester;
    }

    public String getClassSection() {
        return classSection;
    }

    public void setClassSection(String classSection) {
        this.classSection = classSection;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @XmlTransient
    public Collection<BookIssue> getBookIssueCollection() {
        return bookIssueCollection;
    }

    public void setBookIssueCollection(Collection<BookIssue> bookIssueCollection) {
        this.bookIssueCollection = bookIssueCollection;
    }

    @XmlTransient
    public Collection<IssueReturn> getIssueReturnCollection() {
        return issueReturnCollection;
    }

    public void setIssueReturnCollection(Collection<IssueReturn> issueReturnCollection) {
        this.issueReturnCollection = issueReturnCollection;
    }

    @XmlTransient
    public Collection<Fine> getFineCollection() {
        return fineCollection;
    }

    public void setFineCollection(Collection<Fine> fineCollection) {
        this.fineCollection = fineCollection;
    }

    public StudentCategory getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(StudentCategory categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentId != null ? studentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Student)) {
            return false;
        }
        Student other = (Student) object;
        if ((this.studentId == null && other.studentId != null) || (this.studentId != null && !this.studentId.equals(other.studentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return studentId;
    }
    
}
