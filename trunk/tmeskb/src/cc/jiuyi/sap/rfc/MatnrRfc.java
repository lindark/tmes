package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import cc.jiuyi.util.CustomerException;

public interface MatnrRfc extends BaserfcService{
	public List<HashMap<String,String>> getMaterialList(String matnr,String werks,String maktx) throws IOException,CustomerException;
}
