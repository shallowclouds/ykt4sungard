/*
 * WARNING: DO NOT EDIT THIS FILE. This is a generated file that is synchronized
 * by MyEclipse Hibernate tool integration.
 *
 * Created Thu Feb 23 16:21:43 CST 2006 by MyEclipse Hibernate Tool.
 */
package com.kingstargroup.form;

import java.io.Serializable;

/**
 * A class that represents a row in the T_PIF_TRADECODE table. 
 * You can customize the behavior of this class by editing the class, {@link TPifTradecode()}.
 * WARNING: DO NOT EDIT THIS FILE. This is a generated file that is synchronized
 * by MyEclipse Hibernate tool integration.
 */
public abstract class AbstractTPifTradecode 
    implements Serializable
{
    /** The cached hash code value for this instance.  Settting to 0 triggers re-calculation. */
    private int hashValue = 0;

    /** The composite primary key value. */
    private java.lang.Integer tradecode;

    /** The value of the simple trademsg property. */
    private java.lang.String trademsg;

    /**
     * Simple constructor of AbstractTPifTradecode instances.
     */
    public AbstractTPifTradecode()
    {
    }

    /**
     * Constructor of AbstractTPifTradecode instances given a simple primary key.
     * @param tradecode
     */
    public AbstractTPifTradecode(java.lang.Integer tradecode)
    {
        this.setTradecode(tradecode);
    }

    /**
     * Return the simple primary key value that identifies this object.
     * @return java.lang.Integer
     */
    public java.lang.Integer getTradecode()
    {
        return tradecode;
    }

    /**
     * Set the simple primary key value that identifies this object.
     * @param tradecode
     */
    public void setTradecode(java.lang.Integer tradecode)
    {
        this.hashValue = 0;
        this.tradecode = tradecode;
    }

    /**
     * Return the value of the TRADEMSG column.
     * @return java.lang.String
     */
    public java.lang.String getTrademsg()
    {
        return this.trademsg;
    }

    /**
     * Set the value of the TRADEMSG column.
     * @param trademsg
     */
    public void setTrademsg(java.lang.String trademsg)
    {
        this.trademsg = trademsg;
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
        if (! (rhs instanceof TPifTradecode))
            return false;
        TPifTradecode that = (TPifTradecode) rhs;
        if (this.getTradecode() == null || that.getTradecode() == null)
            return false;
        return (this.getTradecode().equals(that.getTradecode()));
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
            int tradecodeValue = this.getTradecode() == null ? 0 : this.getTradecode().hashCode();
            result = result * 37 + tradecodeValue;
            this.hashValue = result;
        }
        return this.hashValue;
    }
}
