package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class DeviceStep extends BaseEntity{
	private String VORNR;//工序
	private String ARBPL;//工作中心
	private String WERKS;//工厂
	private String STEUS;//控制码 （订单类型）PM01,PM02
	private String WORK_ACTIVITY;//涉及作业的工作
	private String DURATION_NORMAL;//作业正常期间
	private String DESCRIPTION;//工序短文本
	private Device device;//设备
	
	public String getVORNR() {
		return VORNR;
	}
	public void setVORNR(String vornr) {
		VORNR = vornr;
	}
	public String getARBPL() {
		return ARBPL;
	}
	public void setARBPL(String arbpl) {
		ARBPL = arbpl;
	}
	public String getWERKS() {
		return WERKS;
	}
	public void setWERKS(String werks) {
		WERKS = werks;
	}
	public String getSTEUS() {
		return STEUS;
	}
	public void setSTEUS(String steus) {
		STEUS = steus;
	}
	public String getWORK_ACTIVITY() {
		return WORK_ACTIVITY;
	}
	public void setWORK_ACTIVITY(String work_activity) {
		WORK_ACTIVITY = work_activity;
	}
	public String getDURATION_NORMAL() {
		return DURATION_NORMAL;
	}
	public void setDURATION_NORMAL(String duration_normal) {
		DURATION_NORMAL = duration_normal;
	}
	public String getDESCRIPTION() {
		return DESCRIPTION;
	}
	public void setDESCRIPTION(String description) {
		DESCRIPTION = description;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	
	
	
}
