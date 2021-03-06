package cc.jiuyi.entity;

import javax.persistence.Entity;

import org.compass.annotations.Searchable;

/**
 * 实体类-产品组
 * 
 * @author 陈为聚
 * 
 */

@Entity
//@Searchable
public class Pros extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String name; //产品组名称
	private String proId;//产品组编号

	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

}
