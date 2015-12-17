package cc.jiuyi.entity;

import javax.persistence.Entity;

/**
 * 实体类 - 处理过程
 */
@Entity
public class DeviceProcess extends BaseEntity{

	private static final long serialVersionUID = -1133611445214021115L;

	private String content;//内容
	private String rowNo;//行号
	
	private Device device;//设备

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRowNo() {
		return rowNo;
	}

	public void setRowNo(String rowNo) {
		this.rowNo = rowNo;
	}

	
	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}
	
	
	
} 
