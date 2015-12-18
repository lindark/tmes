package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class DeviceModlue extends BaseEntity{
	private static final long serialVersionUID = 12324567898765432L;
	private String MATERIAL;//物料号
	private String MENGE;//数量
	private String POSTP;//项目类别
	private String MEINS;//基本单位
	private String VORNR;//工序 
	private Device device;//设备
	
	public String getMATERIAL() {
		return MATERIAL;
	}
	public void setMATERIAL(String material) {
		MATERIAL = material;
	}
	public String getMENGE() {
		return MENGE;
	}
	public void setMENGE(String menge) {
		MENGE = menge;
	}
	public String getPOSTP() {
		return POSTP;
	}
	public void setPOSTP(String postp) {
		POSTP = postp;
	}
	public String getMEINS() {
		return MEINS;
	}
	public void setMEINS(String meins) {
		MEINS = meins;
	}
	public String getVORNR() {
		return VORNR;
	}
	public void setVORNR(String vornr) {
		VORNR = vornr;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	
	
}
