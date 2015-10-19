package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

/**
 * 实体类 - 字典
 */


@Entity
public class Dict extends BaseEntity {

	private static final long serialVersionUID = -6109590619136943215L;
	private String dictname; // name
	private String dictdesp; // desp
	
	private String dictkey;// key
	private String dictvalue;// value
	
	@Column(nullable = false)
	public String getDictname() {
		return dictname;
	}

	public void setDictname(String dictname) {
		this.dictname = dictname;
	}
	
	

	public String getDictdesp() {
		return dictdesp;
	}

	public void setDictdesp(String dictdesp) {
		this.dictdesp = dictdesp;
	}



	
	private Integer orderList;// 排序
	
	@Column(nullable = false)
	public String getDictkey() {
		return dictkey;
	}

	public void setDictkey(String dictkey) {
		this.dictkey = dictkey;
	}
	
	@Column(nullable = false)
	public String getDictvalue() {
		return dictvalue;
	}

	public void setDictvalue(String dictvalue) {
		this.dictvalue = dictvalue;
	}


	@Column(nullable = false)
	public Integer getOrderList() {
		return orderList;
	}

	public void setOrderList(Integer orderList) {
		this.orderList = orderList;
	}

	

}