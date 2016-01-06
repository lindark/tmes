package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import cc.jiuyi.util.CustomerException;

public interface RepairInRfc  extends BaserfcService{
	public List<Map<Object,Object>> repairCrt(List<Map<Object,Object>> list,List<Map<Object,Object>> list2) throws IOException,CustomerException;
	}
