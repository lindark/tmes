package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.List;

import cc.jiuyi.entity.EndProduct;
import cc.jiuyi.util.CustomerException;

public interface EndProductRfc extends BaserfcService{
	public List<EndProduct>  EndProductCrt(String testrun,List<EndProduct> list) throws IOException,CustomerException;
}
