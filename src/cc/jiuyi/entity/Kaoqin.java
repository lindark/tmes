package cc.jiuyi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 考勤
 * @author lenovo
 *
 */
@Entity
public class Kaoqin extends BaseEntity{

	private static final long serialVersionUID = 6948365437771959825L;
	
	private String cardNumber;//卡号
	private String classtime;//班次
	private String empname;//名字
	private String postname;//技能名称
	private String team;// 班组
	private String workState;//工作状态
	
	@Column
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	@Column
	public String getClasstime() {
		return classtime;
	}
	public void setClasstime(String classtime) {
		this.classtime = classtime;
	}
	@Column
	public String getEmpname() {
		return empname;
	}
	public void setEmpname(String empname) {
		this.empname = empname;
	}
	@Column
	public String getPostname() {
		return postname;
	}
	public void setPostname(String postname) {
		this.postname = postname;
	}
	@Column
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	@Column
	public String getWorkState() {
		return workState;
	}
	public void setWorkState(String workState) {
		this.workState = workState;
	}
}