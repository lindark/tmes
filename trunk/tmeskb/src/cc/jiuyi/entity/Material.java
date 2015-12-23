package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;

/**
 * 实体类 - 产品Bom
 * @param args
 */


@Entity
@Table(name = "Material")
public class Material extends BaseEntity{

	private static final long serialVersionUID = 994338331353572158L;
	
    private String materialCode;//组件编码
    private String materialName;//组件名称
    private String materialUnit;//组件单位
    
    
    
}
