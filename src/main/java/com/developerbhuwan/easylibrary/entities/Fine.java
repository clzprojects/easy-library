/*
 * Copyright (c) 2015 Developer Bhuwan
 * Author : Bhuwan Pd. Upadhyay 
 * Created at : 2015-00-23
 * Website : http://www.bhuwanupadhyay.com.np
 */
package com.developerbhuwan.easylibrary.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Bhuwan Pd. Upadhyay
 */
@Entity
@Table(name = "fine")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fine.findAll", query = "SELECT f FROM Fine f"),
    @NamedQuery(name = "Fine.findByFineId", query = "SELECT f FROM Fine f WHERE f.fineId = :fineId"),
    @NamedQuery(name = "Fine.findByTotalLateDay", query = "SELECT f FROM Fine f WHERE f.totalLateDay = :totalLateDay"),
    @NamedQuery(name = "Fine.findByFineRate", query = "SELECT f FROM Fine f WHERE f.fineRate = :fineRate"),
    @NamedQuery(name = "Fine.findByFineAmount", query = "SELECT f FROM Fine f WHERE f.fineAmount = :fineAmount")})
public class Fine implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "fine_id")
    private String fineId;
    @Column(name = "total_late_day")
    private Integer totalLateDay;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "fine_rate")
    private Float fineRate;
    @Column(name = "fine_amount")
    private Double fineAmount;
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    @ManyToOne(optional = false)
    private Student studentId;

    public Fine() {
    }

    public Fine(String fineId) {
        this.fineId = fineId;
    }

    public String getFineId() {
        return fineId;
    }

    public void setFineId(String fineId) {
        this.fineId = fineId;
    }

    public Integer getTotalLateDay() {
        return totalLateDay;
    }

    public void setTotalLateDay(Integer totalLateDay) {
        this.totalLateDay = totalLateDay;
    }

    public Float getFineRate() {
        return fineRate;
    }

    public void setFineRate(Float fineRate) {
        this.fineRate = fineRate;
    }

    public Double getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(Double fineAmount) {
        this.fineAmount = fineAmount;
    }

    public Student getStudentId() {
        return studentId;
    }

    public void setStudentId(Student studentId) {
        this.studentId = studentId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fineId != null ? fineId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fine)) {
            return false;
        }
        Fine other = (Fine) object;
        if ((this.fineId == null && other.fineId != null) || (this.fineId != null && !this.fineId.equals(other.fineId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return fineId;
    }
    
}
