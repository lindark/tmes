package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.List;

import cc.jiuyi.entity.ScrapOut;
import cc.jiuyi.util.CustomerException;

public interface ScrapOutRfc extends BaserfcService{
public List<ScrapOut> getScrapOut(String matnr,String grout,String maktx,String ganme)throws IOException,CustomerException;
}
