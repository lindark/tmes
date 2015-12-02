package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.List;

import cc.jiuyi.entity.Material;
import cc.jiuyi.util.CustomerException;

public interface MaterialRfc extends BaserfcService{
	public List<Material> getBomList(String matnr,String werks) throws IOException,CustomerException;
}
