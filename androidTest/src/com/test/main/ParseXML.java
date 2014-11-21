package com.test.main;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.example.test.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Xml;
import android.widget.EditText;

public class ParseXML extends Activity {
	EditText tv1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parse_xml);
		
		tv1 = (EditText) findViewById(R.id.tv);
		  tv1.setText("000");
		  String xml = "<VCOM version='1.1'><loginlink>11111023</loginlink><errmsg>dfft</errmsg></VCOM>";
		  tv1.setText(xml);
		  ByteArrayInputStream tInputStringStream = null;
		  try
		  {
		  if (xml != null && !xml.trim().equals("")) {
		   tInputStringStream = new ByteArrayInputStream(xml.getBytes());
		  }
		  }
		  catch (Exception e) {
		   // TODO: handle exception
		   tv1.setText(e.getMessage());
		   return;
		  }
		  XmlPullParser parser = Xml.newPullParser();
		  try {
		   parser.setInput(tInputStringStream, "UTF-8");
		   int eventType = parser.getEventType();
		   while (eventType != XmlPullParser.END_DOCUMENT) {
		    switch (eventType) {
		    case XmlPullParser.START_DOCUMENT:// 文档开始事件,可以进行数据初始化处理
		    // persons = new ArrayList<Person>();
		     break;
		    case XmlPullParser.START_TAG:// 开始元素事件
		     String name = parser.getName();
		     if (name.equalsIgnoreCase("errmsg")) {
		      // currentPerson = new Person();
		      // currentPerson.setId(new
		      // Integer(parser.getAttributeValue(null, "id")));
		      tv1.setText(parser.nextText());
		     } 
//		      else if (currentPerson != null) {
//		      if (name.equalsIgnoreCase("loginlink")) {
//		       currentPerson.setName(parser.nextText());// 如果后面是Text节点,即返回它的值
//		      } else if (name.equalsIgnoreCase("errmsg")) {
//		       currentPerson.setAge(new Short(parser.nextText()));
//		      }
//		     }
		     break;
		    case XmlPullParser.END_TAG:// 结束元素事件
//		     if (parser.getName().equalsIgnoreCase("person")
//		       && currentPerson != null) {
//		      persons.add(currentPerson);
//		      currentPerson = null;
//		     }
		     break;
		    }
		    eventType = parser.next();
		   }
		   tInputStringStream.close();
		   // return persons;
		  } catch (XmlPullParserException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  } catch (IOException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }
	}
	
	
}
/*
package a.test;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import biz.source_code.base64Coder.Base64Coder;
import a.test.ExampleHandler;
import a.test.ParsedExampleDataSet;
import android.app.Activity;
import android.os.Bundle;
import android.util.Xml;
import android.widget.EditText;
import android.widget.TextView;
public class b extends Activity {
    //** Called when the activity is first created. //
 EditText tv1;
 @Override
 public void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.main);
  tv1 = (EditText) findViewById(R.id.tv);
  tv1.setText("000");
  String xml = "<VCOM version='1.1'><loginlink>11111023</loginlink><errmsg>dfft</errmsg></VCOM>";
  tv1.setText(xml);
  ByteArrayInputStream tInputStringStream = null;
  try
  {
  if (xml != null && !xml.trim().equals("")) {
   tInputStringStream = new ByteArrayInputStream(xml.getBytes());
  }
  }
  catch (Exception e) {
   // TODO: handle exception
   tv1.setText(e.getMessage());
   return;
  }
  XmlPullParser parser = Xml.newPullParser();
  try {
   parser.setInput(tInputStringStream, "UTF-8");
   int eventType = parser.getEventType();
   while (eventType != XmlPullParser.END_DOCUMENT) {
    switch (eventType) {
    case XmlPullParser.START_DOCUMENT:// 文档开始事件,可以进行数据初始化处理
    // persons = new ArrayList<Person>();
     break;
    case XmlPullParser.START_TAG:// 开始元素事件
     String name = parser.getName();
     if (name.equalsIgnoreCase("errmsg")) {
      // currentPerson = new Person();
      // currentPerson.setId(new
      // Integer(parser.getAttributeValue(null, "id")));
      tv1.setText(parser.nextText());
     } 
//      else if (currentPerson != null) {
//      if (name.equalsIgnoreCase("loginlink")) {
//       currentPerson.setName(parser.nextText());// 如果后面是Text节点,即返回它的值
//      } else if (name.equalsIgnoreCase("errmsg")) {
//       currentPerson.setAge(new Short(parser.nextText()));
//      }
//     }
     break;
    case XmlPullParser.END_TAG:// 结束元素事件
//     if (parser.getName().equalsIgnoreCase("person")
//       && currentPerson != null) {
//      persons.add(currentPerson);
//      currentPerson = null;
//     }
     break;
    }
    eventType = parser.next();
   }
   tInputStringStream.close();
   // return persons;
  } catch (XmlPullParserException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  } catch (IOException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  }
 }
}*/