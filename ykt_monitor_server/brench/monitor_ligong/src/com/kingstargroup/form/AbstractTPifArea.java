/*
 * WARNING: DO NOT EDIT THIS FILE. This is a generated file that is synchronized
 * by MyEclipse Hibernate tool integration.
 *
 * Created Thu Nov 03 19:27:52 CST 2005 by MyEclipse Hibernate Tool.
 */
package com.kingstargroup.form;

import java.io.Serializable;

/**
 * A class that represents a row in the T_PIF_AREA table. 
 * You can customize the behavior of this class by editing the class, {@link TPifArea()}.
 * WARNING: DO NOT EDIT THIS FILE. This is a generated file that is synchronized
 * by MyEclipse Hibernate tool integration.
 */
public abstract class AbstractTPifArea 
    implements Serializable
{
    /** The cached hash code value for this instance.  Settting to 0 triggers re-calculation. */
    private int hashValue = 0;

    /** The composite primary key value. */
    private java.lang.Integer areaNo;

    /** The value of the simple fatherAreaNo property. */
    private java.lang.Integer fatherAreaNo;

    /** The value of the simple areaName property. */
    private java.lang.String areaName;

    /** The value of the simple arealevel property. */
    private java.lang.Integer arealevel;

    /** The value of the simple address property. */
    private java.lang.String address;

    /** The value of the simple comments property. */
    private java.lang.String comments;

    /**
     * Simple constructor of AbstractTPifArea instances.
     */
    public AbstractTPifArea()
    {
    }

    /**
     * Constructor of AbstractTPifArea instances given a simple primary key.
     * @param areaNo
     */
    public AbstractTPifArea(java.lang.Integer areaNo)
    {
        this.setAreaNo(areaNo);
    }

    /**
     * Return the simple primary key value that identifies this object.
     * @return java.lang.Integer
     */
    public java.lang.Integer getAreaNo()
    {
        return areaNo;
    }

    /**
     * Set the simple primary key value that identifies this object.
     * @param areaNo
     */
    public void setAreaNo(java.lang.Integer areaNo)
    {
        this.hashValue = 0;
        this.areaNo = areaNo;
    }

    /**
     * Return the value of the FATHER_AREA_NO column.
     * @return java.lang.Integer
     */
    public java.lang.Integer getFatherAreaNo()
    {
        return this.fatherAreaNo;
    }

    /**
     * Set the value of the FATHER_AREA_NO column.
     * @param fatherAreaNo
     */
    public void setFatherAreaNo(java.lang.Integer fatherAreaNo)
    {
        this.fatherAreaNo = fatherAreaNo;
    }

    /**
     * Return the value of the AREA_NAME column.
     * @return java.lang.String
     */
    public java.lang.String getAreaName()
    {
        return this.areaName;
    }

    /**
     * Set the value of the AREA_NAME column.
     * @param areaName
     */
    public void setAreaName(java.lang.String areaName)
    {
        this.areaName = areaName;
    }

    /**
     * Return the value of the AREALEVEL column.
     * @return java.lang.Integer
     */
    public java.lang.Integer getArealevel()
    {
        return this.arealevel;
    }

    /**
     * Set the value of the AREALEVEL column.
     * @param arealevel
     */
    public void setArealevel(java.lang.Integer arealevel)
    {
        this.arealevel = arealevel;
    }

    /**
     * Return the value of the ADDRESS column.
     * @return java.lang.String
     */
    public java.lang.String getAddress()
    {
        return this.address;
    }

    /**
     * Set the value of the ADDRESS column.
     * @param address
     */
    public void setAddress(java.lang.String address)
    {
        this.address = address;
    }

    /**
     * Return the value of the COMMENTS column.
     * @return java.lang.String
     */
    public java.lang.String getComments()
    {
        return this.comments;
    }

    /**
     * Set the value of the COMMENTS column.
     * @param comments
     */
    public void setComments(java.lang.String comments)
    {
        this.comments = comments;
    }

    /**
     * Implementation of the equals comparison on the basis of equality of the primary key values.
     * @param rhs
     * @return boolean
     */
    public boolean equals(Object rhs)
    {
        if (rhs == null)
            return false;
        if (! (rhs instanceof TPifArea))
            return false;
        TPifArea that = (TPifArea) rhs;
        if (this.getAreaNo() == null || that.getAreaNo() == null)
            return false;
        return (this.getAreaNo().equals(that.getAreaNo()));
    }

    /**
     * Implementation of the hashCode method conforming to the Bloch pattern with
     * the exception of array properties (these are very unlikely primary key types).
     * @return int
     */
    public int hashCode()
    {
        if (this.hashValue == 0)
        {
            int result = 17;
            int areaNoValue = this.getAreaNo() == null ? 0 : this.getAreaNo().hashCode();
            result = result * 37 + areaNoValue;
            this.hashValue = result;
        }
        return this.hashValue;
    }
}
