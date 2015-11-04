package cc.jiuyi.entity;

import javax.persistence.Entity;

import org.compass.annotations.Searchable;

/**
 * 实体类——产品组
 * 
 */
@Entity
@Searchable
public class ProductGroup extends BaseEntity {

	private static final long serialVersionUID = 1196867753616820638L;

	private String productGroupCode;// 产品组编码
	private String productGroupName;// 产品组名称
	private String state;// 状态
	private String isDel;// 是否删除

	public String getProductGroupCode() {
		return productGroupCode;
	}

	public void setProductGroupCode(String productGroupCode) {
		this.productGroupCode = productGroupCode;
	}

	public String getProductGroupName() {
		return productGroupName;
	}

	public void setProductGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
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
		if(isDel == null){
			isDel = "N";
		}
		this.isDel = isDel;
	}

}
