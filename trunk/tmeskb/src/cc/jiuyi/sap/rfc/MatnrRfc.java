package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.List;

import cc.jiuyi.entity.Material;
import cc.jiuyi.util.CustomerException;

public interface MatnrRfc extends BaserfcService{
	public List<Material> getMaterialList(String matnr,String werks,String maktx) throws IOException,CustomerException;
}
