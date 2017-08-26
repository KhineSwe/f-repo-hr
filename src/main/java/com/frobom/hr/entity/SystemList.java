package com.frobom.hr.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "system_list")
public class SystemList extends BaseEntity {
    @NotEmpty
    @Column(name = "system_id", unique = false)
    @Size(min = 4, max = 8, message = "Input characters must be between 4 and 8!")
    private String systemId;

    @NotEmpty
    @Column(name = "company_name", unique = false)
    @Size(max = 50, message = "Input characters must be at most 50!")
    private String companyName;

    @Column(name = "archived_time", unique = false)
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date archivedTime;

    @Column(name = "created", unique = false)
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date created;

    @Column(name = "updated", unique = false)
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date updated;

    @Column(name = "deleted", unique = false)
    private String deleted;

    @Transient
    private String createdDate;

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    @OneToMany(mappedBy = "systemList", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<LoginUserAccount> loginUserAccounts = new HashSet<>();

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Date getArchivedTime() {
        return archivedTime;
    }

    public void setArchivedTime(Date archivedTime) {
        this.archivedTime = archivedTime;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Set<LoginUserAccount> getLoginUserAccounts() {
        return loginUserAccounts;
    }

    public void setLoginUserAccounts(Set<LoginUserAccount> loginUserAccounts) {
        this.loginUserAccounts = loginUserAccounts;
    }
}
