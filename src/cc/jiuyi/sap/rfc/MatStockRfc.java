package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import cc.jiuyi.util.CustomerException;

public interface MatStockRfc extends BaserfcService{
	public List<HashMap<String,String>> getMatStockList(List<HashMap<String, String>> list) throws IOException,CustomerException;
}
