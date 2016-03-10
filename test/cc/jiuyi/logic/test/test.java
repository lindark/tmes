package cc.jiuyi.logic.test;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.sun.xml.stream.XMLScanner;

public class test {
		  static String [][] data = {
		   {"1001","科幻","哈利波特","罗琳","60","2005","en"},
		   {"1002","Web","Spring","Bruce","54","2008","en" },
		   {"1003","武侠","笑傲江湖","金庸","70","1970","zh" },
		   {"1004","武侠","小李飞刀","古龙","45","1965","zh" }
		  };
		 
		  /**
		   * @param args
		   */
		  public static void main(String[] args) {
		    writeBook("mybook.xml");
		  }
		  /**
		   * 把书的数据生成到指定名字的xml文件中
		   * @param filename 要生成的xml文件名
		   */
		  public static void writeBook(String filename){
		    //1. 构造空的Document
		    Document doc = DocumentHelper.createDocument();
		     
		    //2. 构造根元素
		    Element rootElmt = doc.addElement("booklist");
		    //3. 递归构造子元素
		    for(String[] book : data){
		      Element bookElmt 
		        = rootElmt.addElement("book"); 
		       
		      //book元素增加属性
		      bookElmt.addAttribute("isbn", book[0]);
		      bookElmt.addAttribute("catalog", book[1]);
		     
		      //book元素增加四个子元素
		      Element nameElmt 
		        = bookElmt.addElement("name");
		      nameElmt.addAttribute("lang", book[6]);
		      //name元素设置数据
		      nameElmt.setText(book[2]);
		      Element authorElmt 
		        = bookElmt.addElement("author");
		      authorElmt.setText(book[3]);
		      Element priceElmt 
		        = bookElmt.addElement("price");
		      priceElmt.setText(book[4]);
		      Element yearElmt 
		        = bookElmt.addElement("year");
		      yearElmt.setText(book[5]);
		    }
		     
		    System.out.println(doc.asXML());
		    //4. 输出
		   // outputXml(doc, filename);
		  }
		  /**
		   * 将doc写出到filename中
		   * @param doc 文档对象
		   * @param filename 要输出的xml文件名
		   */
		  public static void outputXml(Document doc, String filename) {
		    try {
		      //定义输出流的目的地
		      FileWriter fw = new FileWriter(filename);
		       
		      //定义输出格式和字符集
		      OutputFormat format 
		        = OutputFormat.createPrettyPrint();
		      format.setEncoding("GBK");
		       
		      //定义用于输出xml文件的XMLWriter对象
		      XMLWriter xmlWriter 
		        = new XMLWriter(fw, format);
		      xmlWriter.write(doc);//*****
		      xmlWriter.close(); 
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		     
		  }
		 
}
