package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "SPRING_SESSION", schema = "bdclinica", catalog = "")
public class SpringSession {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "PRIMARY_ID")
    private String primaryId;
    @Basic
    @Column(name = "SESSION_ID")
    private String sessionId;
    @Basic
    @Column(name = "CREATION_TIME")
    private long creationTime;
    @Basic
    @Column(name = "LAST_ACCESS_TIME")
    private long lastAccessTime;
    @Basic
    @Column(name = "MAX_INACTIVE_INTERVAL")
    private int maxInactiveInterval;
    @Basic
    @Column(name = "EXPIRY_TIME")
    private long expiryTime;
    @Basic
    @Column(name = "PRINCIPAL_NAME")
    private String principalName;

    public String getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(String primaryId) {
        this.primaryId = primaryId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(long lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public int getMaxInactiveInterval() {
        return maxInactiveInterval;
    }

    public void setMaxInactiveInterval(int maxInactiveInterval) {
        this.maxInactiveInterval = maxInactiveInterval;
    }

    public long getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(long expiryTime) {
        this.expiryTime = expiryTime;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpringSession that = (SpringSession) o;

        if (creationTime != that.creationTime) return false;
        if (lastAccessTime != that.lastAccessTime) return false;
        if (maxInactiveInterval != that.maxInactiveInterval) return false;
        if (expiryTime != that.expiryTime) return false;
        if (primaryId != null ? !primaryId.equals(that.primaryId) : that.primaryId != null) return false;
        if (sessionId != null ? !sessionId.equals(that.sessionId) : that.sessionId != null) return false;
        if (principalName != null ? !principalName.equals(that.principalName) : that.principalName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = primaryId != null ? primaryId.hashCode() : 0;
        result = 31 * result + (sessionId != null ? sessionId.hashCode() : 0);
        result = 31 * result + (int) (creationTime ^ (creationTime >>> 32));
        result = 31 * result + (int) (lastAccessTime ^ (lastAccessTime >>> 32));
        result = 31 * result + maxInactiveInterval;
        result = 31 * result + (int) (expiryTime ^ (expiryTime >>> 32));
        result = 31 * result + (principalName != null ? principalName.hashCode() : 0);
        return result;
    }
}
