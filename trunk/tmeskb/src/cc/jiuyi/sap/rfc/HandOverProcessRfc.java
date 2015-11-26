package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public interface HandOverProcessRfc extends BaserfcService {
	
	/**
	 * 读取线边仓数据
	 * @param warehouse   库存地点
	 * @param materialCodeList  物料编码集合
	 * @return 
	 */
	public List<HashMap<String, Object>> findWarehouse(Integer warehouse,List<String> materialCodeList) throws IOException;
}
