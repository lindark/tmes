package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;

/**
 * 实体类 - 岗位
 * @param args
 */


@Entity
//@Searchable
@Table(name = "Post")
public class Post extends BaseEntity{

	private static final long serialVersionUID = 1L;

    private String postCode;//岗位编码
    private String postName;//岗位名称
    private String state;//状态
    private String isDel;//是否删除
    private String stateRemark;//状态描述
    private Set<Admin> adminSet;
    private String station;//工位--现在改为部门编码
    private Set<Station> stationSet;//工位
    
    //假字段
    private String xstation;
    
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getPostName() {
		return postName;
	}
	public void setPostName(String postName) {
		this.postName = postName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
        if(isDel==null){
        	isDel="N";
        }
		this.isDel = isDel;
	}
	
	 @Transient
	public String getStateRemark() {
		return stateRemark;
	}
	public void setStateRemark(String stateRemark) {
		this.stateRemark = stateRemark;
	}
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="post")
	public Set<Admin> getAdminSet()
	{
		return adminSet;
	}
	public void setAdminSet(Set<Admin> adminSet)
	{
		this.adminSet = adminSet;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	@OneToMany(fetch=FetchType.LAZY,mappedBy="posts")
	public Set<Station> getStationSet()
	{
		return stationSet;
	}
	public void setStationSet(Set<Station> stationSet)
	{
		this.stationSet = stationSet;
	}
	@Transient
	public String getXstation()
	{
		return xstation;
	}
	public void setXstation(String xstation)
	{
		this.xstation = xstation;
	}
}
