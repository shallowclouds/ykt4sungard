package org.king.security.domain;

import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;



/**
 * AbstractResource generated by MyEclipse - Hibernate Tools
 */

public abstract class AbstractResource extends org.king.framework.domain.BaseObject implements java.io.Serializable {


    // Fields    

     private String id;
     private String code;
     private String name;
     private String parentName;
     private String url;
     private String aclClass;

     private Set roles;

    // Constructors

    /** default constructor */
    public AbstractResource() {
    }

	/** minimal constructor */
    public AbstractResource(String name) {
        this.name = name;
    }
    

	/** full constructor */
    public AbstractResource(String code, String name, String parentName, String url, String aclClass) {
		this.code = code;
		this.name = name;
		this.parentName = parentName;
		this.url = url;
		this.aclClass = aclClass;
	}


   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getParentName() {
        return this.parentName;
    }
    
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }

    public String getAclClass() {
        return this.aclClass;
    }
    
    public void setAclClass(String aclClass) {
        this.aclClass = aclClass;
    }

    
    
	public Set getRoles() {
		return roles;
	}

	public void setRoles(Set roles) {
		this.roles = roles;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof AbstractResource)) {
			return false;
		}
		AbstractResource rhs = (AbstractResource) object;
		return new EqualsBuilder().append(this.code, rhs.code).append(
				this.aclClass, rhs.aclClass).append(this.url, rhs.url).append(
				this.parentName, rhs.parentName).append(this.name, rhs.name)
				.append(this.id, rhs.id).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-525039511, 1176896435).append(this.code)
				.append(this.aclClass).append(this.url).append(this.parentName)
				.append(this.name).append(this.id).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("name", this.name).append("id", this.id).append("code",
						this.code).append("aclClass", this.aclClass).append(
						"url", this.url).append("parentName", this.parentName)
				.toString();
	}
   








}