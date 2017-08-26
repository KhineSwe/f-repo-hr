package com.frobom.hr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table
public class Setting extends BaseEntity{
    @NotEmpty
    @Column(name = "system_log")
    public String systemLog;
    
    @NotEmpty
    @Column(name = "system_error_log")
    public String systemErrorLog;
    
    @NotEmpty
    @Column(name = "system_access_log")
    public String systemAccessLog;
    
    @NotEmpty
    @Column(name = "file_path")
    public String filePath;
    
    @NotEmpty
    @Column(name = "database")
    public String database;

    public String getSystemLog() {
        return systemLog;
    }

    public void setSystemLog(String systemLog) {
        this.systemLog = systemLog;
    }

    public String getSystemErrorLog() {
        return systemErrorLog;
    }

    public void setSystemErrorLog(String systemErrorLog) {
        this.systemErrorLog = systemErrorLog;
    }

    public String getSystemAccessLog() {
        return systemAccessLog;
    }

    public void setSystemAccessLog(String systemAccessLog) {
        this.systemAccessLog = systemAccessLog;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
}
