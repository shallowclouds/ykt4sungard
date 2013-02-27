/*
 * WARNING: DO NOT EDIT THIS FILE. This is a generated file that is synchronized
 * by MyEclipse Hibernate tool integration.
 *
 * Created Fri Mar 03 18:12:17 CST 2006 by MyEclipse Hibernate Tool.
 */
package com.kingstargroup.form;

import java.io.Serializable;

/**
 * A class that represents a row in the MAP_SETS_TABLE table. 
 * You can customize the behavior of this class by editing the class, {@link MapSetsTable()}.
 * WARNING: DO NOT EDIT THIS FILE. This is a generated file that is synchronized
 * by MyEclipse Hibernate tool integration.
 */
public abstract class AbstractMapSetsTable 
    implements Serializable
{
    /** The cached hash code value for this instance.  Settting to 0 triggers re-calculation. */
    private int hashValue = 0;

    /** The composite primary key value. */
    private java.lang.Integer id;

    /** The value of the simple setsid property. */
    private java.lang.String setsid;

    /** The value of the simple setname property. */
    private java.lang.String setname;

    /** The value of the simple fatherid property. */
    private java.lang.String fatherid;

    /** The value of the simple settype property. */
    private java.lang.String settype;

    /** The value of the simple setstatus property. */
    private java.lang.Short setstatus;

    /** The value of the simple xcoor property. */
    private java.lang.Double xcoor;

    /** The value of the simple ycoor property. */
    private java.lang.Double ycoor;

    /** The value of the simple feature property. */
    private java.lang.String feature;

    /**
     * Simple constructor of AbstractMapSetsTable instances.
     */
    public AbstractMapSetsTable()
    {
    }

    /**
     * Constructor of AbstractMapSetsTable instances given a simple primary key.
     * @param id
     */
    public AbstractMapSetsTable(java.lang.Integer id)
    {
        this.setId(id);
    }

    /**
     * Return the simple primary key value that identifies this object.
     * @return java.lang.Integer
     */
    public java.lang.Integer getId()
    {
        return id;
    }

    /**
     * Set the simple primary key value that identifies this object.
     * @param id
     */
    public void setId(java.lang.Integer id)
    {
        this.hashValue = 0;
        this.id = id;
    }

    /**
     * Return the value of the SETSID column.
     * @return java.lang.String
     */
    public java.lang.String getSetsid()
    {
        return this.setsid;
    }

    /**
     * Set the value of the SETSID column.
     * @param setsid
     */
    public void setSetsid(java.lang.String setsid)
    {
        this.setsid = setsid;
    }

    /**
     * Return the value of the SETNAME column.
     * @return java.lang.String
     */
    public java.lang.String getSetname()
    {
        return this.setname;
    }

    /**
     * Set the value of the SETNAME column.
     * @param setname
     */
    public void setSetname(java.lang.String setname)
    {
        this.setname = setname;
    }

    /**
     * Return the value of the FATHERID column.
     * @return java.lang.String
     */
    public java.lang.String getFatherid()
    {
        return this.fatherid;
    }

    /**
     * Set the value of the FATHERID column.
     * @param fatherid
     */
    public void setFatherid(java.lang.String fatherid)
    {
        this.fatherid = fatherid;
    }

    /**
     * Return the value of the SETTYPE column.
     * @return java.lang.String
     */
    public java.lang.String getSettype()
    {
        return this.settype;
    }

    /**
     * Set the value of the SETTYPE column.
     * @param settype
     */
    public void setSettype(java.lang.String settype)
    {
        this.settype = settype;
    }

    /**
     * Return the value of the SETSTATUS column.
     * @return java.lang.Short
     */
    public java.lang.Short getSetstatus()
    {
        return this.setstatus;
    }

    /**
     * Set the value of the SETSTATUS column.
     * @param setstatus
     */
    public void setSetstatus(java.lang.Short setstatus)
    {
        this.setstatus = setstatus;
    }

    /**
     * Return the value of the XCOOR column.
     * @return java.lang.Double
     */
    public java.lang.Double getXcoor()
    {
        return this.xcoor;
    }

    /**
     * Set the value of the XCOOR column.
     * @param xcoor
     */
    public void setXcoor(java.lang.Double xcoor)
    {
        this.xcoor = xcoor;
    }

    /**
     * Return the value of the YCOOR column.
     * @return java.lang.Double
     */
    public java.lang.Double getYcoor()
    {
        return this.ycoor;
    }

    /**
     * Set the value of the YCOOR column.
     * @param ycoor
     */
    public void setYcoor(java.lang.Double ycoor)
    {
        this.ycoor = ycoor;
    }

    /**
     * Return the value of the FEATURE column.
     * @return java.lang.String
     */
    public java.lang.String getFeature()
    {
        return this.feature;
    }

    /**
     * Set the value of the FEATURE column.
     * @param feature
     */
    public void setFeature(java.lang.String feature)
    {
        this.feature = feature;
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
        if (! (rhs instanceof MapSetsTable))
            return false;
        MapSetsTable that = (MapSetsTable) rhs;
        if (this.getId() == null || that.getId() == null)
            return false;
        return (this.getId().equals(that.getId()));
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
            int idValue = this.getId() == null ? 0 : this.getId().hashCode();
            result = result * 37 + idValue;
            this.hashValue = result;
        }
        return this.hashValue;
    }
}
