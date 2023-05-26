package com.example.proyectogticsgrupo1.Entity;

import jakarta.persistence.*;

import java.util.Arrays;

@Entity
@Table(name = "SPRING_SESSION_ATTRIBUTES", schema = "bdclinica", catalog = "")
@IdClass(SpringSessionAttributesPK.class)
public class SpringSessionAttributes {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "SESSION_PRIMARY_ID")
    private String sessionPrimaryId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ATTRIBUTE_NAME")
    private String attributeName;
    @Basic
    @Column(name = "ATTRIBUTE_BYTES")
    private byte[] attributeBytes;

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

    public byte[] getAttributeBytes() {
        return attributeBytes;
    }

    public void setAttributeBytes(byte[] attributeBytes) {
        this.attributeBytes = attributeBytes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpringSessionAttributes that = (SpringSessionAttributes) o;

        if (sessionPrimaryId != null ? !sessionPrimaryId.equals(that.sessionPrimaryId) : that.sessionPrimaryId != null)
            return false;
        if (attributeName != null ? !attributeName.equals(that.attributeName) : that.attributeName != null)
            return false;
        if (!Arrays.equals(attributeBytes, that.attributeBytes)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sessionPrimaryId != null ? sessionPrimaryId.hashCode() : 0;
        result = 31 * result + (attributeName != null ? attributeName.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(attributeBytes);
        return result;
    }
}
