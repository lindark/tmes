package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.sap.mw.jco.JCO.Table;

import cc.jiuyi.entity.Locationonside;
import cc.jiuyi.util.CustomerException;


public interface LocationonsideRfc extends BaserfcService {
	
	/**
	 * 读取线边仓数据
	 * @param warehouse   库存地点
	 * @param materialCodeList  物料编码集合
	 * @return 
	 */
	public List<Locationonside> findWarehouse(String warehouse,List<String> materialCodeList) throws IOException, CustomerException;
}
