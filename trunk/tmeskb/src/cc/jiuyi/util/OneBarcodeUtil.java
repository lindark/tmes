package cc.jiuyi.util;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;

import org.jbarcode.JBarcode;
import org.jbarcode.encode.Code128Encoder;
import org.jbarcode.paint.BaseLineTextPainter;
import org.jbarcode.paint.WidthCodedPainter;

/**
 *条形码打印功能 工具类 
 * @author weita
 *
 */
public class OneBarcodeUtil {
	public static void createCode(String asnno,String path){
		try  
	    {  
		  //System.out.println("开始生成条码");
	      JBarcode localJBarcode = new JBarcode(Code128Encoder.getInstance(), WidthCodedPainter.getInstance(), BaseLineTextPainter.getInstance());
	      // localJBarcode.setBarHeight(10);
	      // localJBarcode.setXDimension(1);
	      localJBarcode.setShowText(false);//不显示条码下面的code
	      BufferedImage localBufferedImage = localJBarcode.createBarcode(asnno); 
	      //localBufferedImage = localJBarcode.createBarcode(asnno);  
	      saveToPNG(localBufferedImage, asnno+".png",path); 
	    }  
	    catch (Exception e)  
	    {  
	      e.printStackTrace();  
	    }  
		
	}
	public static void saveToJPEG(BufferedImage paramBufferedImage, String paramString,String path)  
	  {  
		saveToFile(paramBufferedImage, paramString, "jpeg",path);  
	  }  
  
	public static void saveToPNG(BufferedImage paramBufferedImage, String paramString,String path)  
	  {  
		saveToFile(paramBufferedImage, paramString, "png",path);  
	  }  
	  
	public static void saveToGIF(BufferedImage paramBufferedImage, String paramString,String path)  
	  {  
	    saveToFile(paramBufferedImage, paramString, "gif",path);  
	  }  
  
	public static void saveToFile(BufferedImage paramBufferedImage, String paramString1, String paramString2,String path)  
	  {  
	    try  
	    {  
	     //System.out.println("CODE:"+ JianXinPro.path+ paramString1);
	     FileOutputStream localFileOutputStream = new FileOutputStream(path + paramString1);
         org.jbarcode.util.ImageUtil.encodeAndWrite(paramBufferedImage, paramString2, localFileOutputStream, 128, 128);  
         localFileOutputStream.close();  
	    }  
	    catch (Exception localException)  
	    {  
	      localException.printStackTrace();  
	    }  
	  } 
	
	public static void main(String [] args){
		createCode("ceshi", "");
	}
}
