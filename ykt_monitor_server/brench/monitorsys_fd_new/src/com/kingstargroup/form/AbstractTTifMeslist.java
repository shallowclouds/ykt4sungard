/*
 * WARNING: DO NOT EDIT THIS FILE. This is a generated file that is synchronized
 * by MyEclipse Hibernate tool integration.
 *
 * Created Tue Nov 01 13:26:31 CST 2005 by MyEclipse Hibernate Tool.
 */
package com.kingstargroup.form;

import java.io.Serializable;

/**
 * A class that represents a row in the T_TIF_MESLIST table. 
 * You can customize the behavior of this class by editing the class, {@link TTifMeslist()}.
 * WARNING: DO NOT EDIT THIS FILE. This is a generated file that is synchronized
 * by MyEclipse Hibernate tool integration.
 */
public abstract class AbstractTTifMeslist 
    implements Serializable
{
    /** The cached hash code value for this instance.  Settting to 0 triggers re-calculation. */
    private int hashValue = 0;

    /** The composite primary key value. */
    private java.lang.Integer mesid;

    /** The value of the simple funid property. */
    private java.lang.Integer funid;

    /** The value of the simple stateid property. */
    private java.lang.Integer stateid;

    /** The value of the simple sendcount property. */
    private java.lang.Integer sendcount;

    /** The value of the simple level property. */
    private java.lang.Integer level;

    /** The value of the simple deviceId property. */
    private java.lang.Integer deviceId;

    /** The value of the simple devphy999Id property. */
    private java.lang.String devphy999Id;

    /** The value of the simple indt property. */
    private java.lang.String indt;

    /** The value of the simple startdt property. */
    private java.lang.String startdt;

    /** The value of the simple enddt property. */
    private java.lang.String enddt;

    /** The value of the simple ecode property. */
    private java.lang.Integer ecode;

    /** The value of the simple emsg property. */
    private java.lang.String emsg;

    /** The value of the simple incontent property. */
    private java.lang.String incontent;

    /** The value of the simple outcontent property. */
    private java.lang.String outcontent;

    /** The value of the simple pfunid property. */
    private java.lang.Integer pfunid;

    /** The value of the simple cardNo property. */
    private java.lang.Integer cardNo;

    /** The value of the simple maxSendCnt property. */
    private java.lang.Integer maxSendCnt;

    /** The value of the simple delFlag property. */
    private java.lang.String delFlag;

    /**
     * Simple constructor of AbstractTTifMeslist instances.
     */
    public AbstractTTifMeslist()
    {
    }

    /**
     * Constructor of AbstractTTifMeslist instances given a simple primary key.
     * @param mesid
     */
    public AbstractTTifMeslist(java.lang.Integer mesid)
    {
        this.setMesid(mesid);
    }

    /**
     * Return the simple primary key value that identifies this object.
     * @return java.lang.Integer
     */
    public java.lang.Integer getMesid()
    {
        return mesid;
    }

    /**
     * Set the simple primary key value that identifies this object.
     * @param mesid
     */
    public void setMesid(java.lang.Integer mesid)
    {
        this.hashValue = 0;
        this.mesid = mesid;
    }

    /**
     * Return the value of the FUNID column.
     * @return java.lang.Integer
     */
    public java.lang.Integer getFunid()
    {
        return this.funid;
    }

    /**
     * Set the value of the FUNID column.
     * @param funid
     */
    public void setFunid(java.lang.Integer funid)
    {
        this.funid = funid;
    }

    /**
     * Return the value of the STATEID column.
     * @return java.lang.Integer
     */
    public java.lang.Integer getStateid()
    {
        return this.stateid;
    }

    /**
     * Set the value of the STATEID column.
     * @param stateid
     */
    public void setStateid(java.lang.Integer stateid)
    {
        this.stateid = stateid;
    }

    /**
     * Return the value of the SENDCOUNT column.
     * @return java.lang.Integer
     */
    public java.lang.Integer getSendcount()
    {
        return this.sendcount;
    }

    /**
     * Set the value of the SENDCOUNT column.
     * @param sendcount
     */
    public void setSendcount(java.lang.Integer sendcount)
    {
        this.sendcount = sendcount;
    }

    /**
     * Return the value of the LEVEL column.
     * @return java.lang.Integer
     */
    public java.lang.Integer getLevel()
    {
        return this.level;
    }

    /**
     * Set the value of the LEVEL column.
     * @param level
     */
    public void setLevel(java.lang.Integer level)
    {
        this.level = level;
    }

    /**
     * Return the value of the DEVICE_ID column.
     * @return java.lang.Integer
     */
    public java.lang.Integer getDeviceId()
    {
        return this.deviceId;
    }

    /**
     * Set the value of the DEVICE_ID column.
     * @param deviceId
     */
    public void setDeviceId(java.lang.Integer deviceId)
    {
        this.deviceId = deviceId;
    }

    /**
     * Return the value of the DEVPHY999_ID column.
     * @return java.lang.String
     */
    public java.lang.String getDevphy999Id()
    {
        return this.devphy999Id;
    }

    /**
     * Set the value of the DEVPHY999_ID column.
     * @param devphy999Id
     */
    public void setDevphy999Id(java.lang.String devphy999Id)
    {
        this.devphy999Id = devphy999Id;
    }

    /**
     * Return the value of the INDT column.
     * @return java.lang.String
     */
    public java.lang.String getIndt()
    {
        return this.indt;
    }

    /**
     * Set the value of the INDT column.
     * @param indt
     */
    public void setIndt(java.lang.String indt)
    {
        this.indt = indt;
    }

    /**
     * Return the value of the STARTDT column.
     * @return java.lang.String
     */
    public java.lang.String getStartdt()
    {
        return this.startdt;
    }

    /**
     * Set the value of the STARTDT column.
     * @param startdt
     */
    public void setStartdt(java.lang.String startdt)
    {
        this.startdt = startdt;
    }

    /**
     * Return the value of the ENDDT column.
     * @return java.lang.String
     */
    public java.lang.String getEnddt()
    {
        return this.enddt;
    }

    /**
     * Set the value of the ENDDT column.
     * @param enddt
     */
    public void setEnddt(java.lang.String enddt)
    {
        this.enddt = enddt;
    }

    /**
     * Return the value of the ECODE column.
     * @return java.lang.Integer
     */
    public java.lang.Integer getEcode()
    {
        return this.ecode;
    }

    /**
     * Set the value of the ECODE column.
     * @param ecode
     */
    public void setEcode(java.lang.Integer ecode)
    {
        this.ecode = ecode;
    }

    /**
     * Return the value of the EMSG column.
     * @return java.lang.String
     */
    public java.lang.String getEmsg()
    {
        return this.emsg;
    }

    /**
     * Set the value of the EMSG column.
     * @param emsg
     */
    public void setEmsg(java.lang.String emsg)
    {
        this.emsg = emsg;
    }

    /**
     * Return the value of the INCONTENT column.
     * @return java.lang.String
     */
    public java.lang.String getIncontent()
    {
        return this.incontent;
    }

    /**
     * Set the value of the INCONTENT column.
     * @param incontent
     */
    public void setIncontent(java.lang.String incontent)
    {
        this.incontent = incontent;
    }

    /**
     * Return the value of the OUTCONTENT column.
     * @return java.lang.String
     */
    public java.lang.String getOutcontent()
    {
        return this.outcontent;
    }

    /**
     * Set the value of the OUTCONTENT column.
     * @param outcontent
     */
    public void setOutcontent(java.lang.String outcontent)
    {
        this.outcontent = outcontent;
    }

    /**
     * Return the value of the PFUNID column.
     * @return java.lang.Integer
     */
    public java.lang.Integer getPfunid()
    {
        return this.pfunid;
    }

    /**
     * Set the value of the PFUNID column.
     * @param pfunid
     */
    public void setPfunid(java.lang.Integer pfunid)
    {
        this.pfunid = pfunid;
    }

    /**
     * Return the value of the CARD_NO column.
     * @return java.lang.Integer
     */
    public java.lang.Integer getCardNo()
    {
        return this.cardNo;
    }

    /**
     * Set the value of the CARD_NO column.
     * @param cardNo
     */
    public void setCardNo(java.lang.Integer cardNo)
    {
        this.cardNo = cardNo;
    }

    /**
     * Return the value of the MAX_SEND_CNT column.
     * @return java.lang.Integer
     */
    public java.lang.Integer getMaxSendCnt()
    {
        return this.maxSendCnt;
    }

    /**
     * Set the value of the MAX_SEND_CNT column.
     * @param maxSendCnt
     */
    public void setMaxSendCnt(java.lang.Integer maxSendCnt)
    {
        this.maxSendCnt = maxSendCnt;
    }

    /**
     * Return the value of the DEL_FLAG column.
     * @return java.lang.String
     */
    public java.lang.String getDelFlag()
    {
        return this.delFlag;
    }

    /**
     * Set the value of the DEL_FLAG column.
     * @param delFlag
     */
    public void setDelFlag(java.lang.String delFlag)
    {
        this.delFlag = delFlag;
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
        if (! (rhs instanceof TTifMeslist))
            return false;
        TTifMeslist that = (TTifMeslist) rhs;
        if (this.getMesid() == null || that.getMesid() == null)
            return false;
        return (this.getMesid().equals(that.getMesid()));
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
            int mesidValue = this.getMesid() == null ? 0 : this.getMesid().hashCode();
            result = result * 37 + mesidValue;
            this.hashValue = result;
        }
        return this.hashValue;
    }
}
