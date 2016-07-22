package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import cc.jiuyi.entity.UnitdistributeProduct;
import cc.jiuyi.util.CustomerException;

public interface ProductStorageRfc  extends BaserfcService{
	public void sysnProductStorage(HashMap<String,Object> parameter) throws IOException, CustomerException;
}
