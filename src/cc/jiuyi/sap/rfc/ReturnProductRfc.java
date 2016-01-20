package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.List;

import cc.jiuyi.entity.ReturnProduct;
import cc.jiuyi.util.CustomerException;

public interface ReturnProductRfc extends BaserfcService {
	public List<ReturnProduct>  returnProductCrt(String testrun,List<ReturnProduct> list) throws IOException,CustomerException;
}
