package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;

/**
 * 实体类 - 班组
 * @param args
 */


@Entity
@Searchable
@Table(name = "Team")
public class Team extends BaseEntity{

	private static final long serialVersionUID = 1L;

    private String teamCode;//班组编码
    private String teamName;//班组名称
    private String state;//状态
    private String isDel;//是否删除
    private String stateRemark;//状态描述
    
    
    private Set<Admin> admin;//员工
    
    @OneToMany(fetch = FetchType.LAZY,mappedBy="team")
	public Set<Admin> getAdmin() {
		return admin;
	}
	public void setAdmin(Set<Admin> admin) {
		this.admin = admin;
	}
	public String getTeamCode() {
		return teamCode;
	}
	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
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

   

    
	
}
