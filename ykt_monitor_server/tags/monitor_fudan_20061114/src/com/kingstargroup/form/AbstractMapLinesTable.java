/*
 * WARNING: DO NOT EDIT THIS FILE. This is a generated file that is synchronized
 * by MyEclipse Hibernate tool integration.
 *
 * Created Wed Nov 16 19:01:14 CST 2005 by MyEclipse Hibernate Tool.
 */
package com.kingstargroup.form;

import java.io.Serializable;

/**
 * A class that represents a row in the MAP_LINES_TABLE table. 
 * You can customize the behavior of this class by editing the class, {@link MapLinesTable()}.
 * WARNING: DO NOT EDIT THIS FILE. This is a generated file that is synchronized
 * by MyEclipse Hibernate tool integration.
 */
public abstract class AbstractMapLinesTable 
    implements Serializable
{
    /** The cached hash code value for this instance.  Settting to 0 triggers re-calculation. */
    private int hashValue = 0;

    /** The composite primary key value. */
    private java.lang.Integer id;

    /** The value of the simple point1 property. */
    private java.lang.String point1;

    /** The value of the simple point2 property. */
    private java.lang.String point2;

    /** The value of the simple linetype property. */
    private java.lang.Short linetype;

    /** The value of the simple linecolor property. */
    private java.lang.Long linecolor;

    /** The value of the simple linestyle property. */
    private java.lang.Integer linestyle;

    /** The value of the simple linestatus property. */
    private java.lang.Short linestatus;

    /**
     * Simple constructor of AbstractMapLinesTable instances.
     */
    public AbstractMapLinesTable()
    {
    }

    /**
     * Constructor of AbstractMapLinesTable instances given a simple primary key.
     * @param id
     */
    public AbstractMapLinesTable(java.lang.Integer id)
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
     * Return the value of the POINT1 column.
     * @return java.lang.String
     */
    public java.lang.String getPoint1()
    {
        return this.point1;
    }

    /**
     * Set the value of the POINT1 column.
     * @param point1
     */
    public void setPoint1(java.lang.String point1)
    {
        this.point1 = point1;
    }

    /**
     * Return the value of the POINT2 column.
     * @return java.lang.String
     */
    public java.lang.String getPoint2()
    {
        return this.point2;
    }

    /**
     * Set the value of the POINT2 column.
     * @param point2
     */
    public void setPoint2(java.lang.String point2)
    {
        this.point2 = point2;
    }

    /**
     * Return the value of the LINETYPE column.
     * @return java.lang.Short
     */
    public java.lang.Short getLinetype()
    {
        return this.linetype;
    }

    /**
     * Set the value of the LINETYPE column.
     * @param linetype
     */
    public void setLinetype(java.lang.Short linetype)
    {
        this.linetype = linetype;
    }

    /**
     * Return the value of the LINECOLOR column.
     * @return java.lang.Long
     */
    public java.lang.Long getLinecolor()
    {
        return this.linecolor;
    }

    /**
     * Set the value of the LINECOLOR column.
     * @param linecolor
     */
    public void setLinecolor(java.lang.Long linecolor)
    {
        this.linecolor = linecolor;
    }

    /**
     * Return the value of the LINESTYLE column.
     * @return java.lang.Integer
     */
    public java.lang.Integer getLinestyle()
    {
        return this.linestyle;
    }

    /**
     * Set the value of the LINESTYLE column.
     * @param linestyle
     */
    public void setLinestyle(java.lang.Integer linestyle)
    {
        this.linestyle = linestyle;
    }

    /**
     * Return the value of the LINESTATUS column.
     * @return java.lang.Short
     */
    public java.lang.Short getLinestatus()
    {
        return this.linestatus;
    }

    /**
     * Set the value of the LINESTATUS column.
     * @param linestatus
     */
    public void setLinestatus(java.lang.Short linestatus)
    {
        this.linestatus = linestatus;
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
        if (! (rhs instanceof MapLinesTable))
            return false;
        MapLinesTable that = (MapLinesTable) rhs;
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
