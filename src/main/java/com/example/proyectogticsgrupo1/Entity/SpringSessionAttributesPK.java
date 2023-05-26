package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;

public class SpringSessionAttributesPK implements Serializable {
    @Column(name = "SESSION_PRIMARY_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String sessionPrimaryId;
    @Column(name = "ATTRIBUTE_NAME")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String attributeName;

    public String getSessionPrimaryId() {
        return sessionPrimaryId;
    }

    public void setSessionPrimaryId(String sessionPrimaryId) {
        this.sessionPrimaryId = sessionPrimaryId;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpringSessionAttributesPK that = (SpringSessionAttributesPK) o;

        if (sessionPrimaryId != null ? !sessionPrimaryId.equals(that.sessionPrimaryId) : that.sessionPrimaryId != null)
            return false;
        if (attributeName != null ? !attributeName.equals(that.attributeName) : that.attributeName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sessionPrimaryId != null ? sessionPrimaryId.hashCode() : 0;
        result = 31 * result + (attributeName != null ? attributeName.hashCode() : 0);
        return result;
    }
}
