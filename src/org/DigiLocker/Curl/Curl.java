package org.DigiLocker.Curl;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import javax.xml.parsers.*;

import org.jsoup.* ;    
import org.jsoup.nodes.*;
import  org.jsoup.parser.*;
import org.jsoup.select.Elements;

public class Curl extends HttpServlet {
	public static boolean flag =false;
	String uri = new String();
	String txn = new String();
	String orgId = new String();
	String appId = new String();
	String secret_key  = new String();
	String text = new String();
	String timestamp = new String();
	String hash = new String();
	String xml = new String();
	public String type = new String();
	public String content = new String();
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	  uri = request.getParameter("uri");
	  txn = request.getParameter("txn");
	  try {
		compute();
	} catch (NoSuchAlgorithmException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
	  sendData();
	  request.getSession().setAttribute("type", type);
	  request.getSession().setAttribute("content", content);
	  flag = true;
	  RequestDispatcher rd = request.getRequestDispatcher("/popup.jsp");
	  rd.forward(request, response);
	}
	public void sendData()  {
	try {
	String urly = "https://devpartners.digitallocker.gov.in/public/requestor/api/pulldoc/1/xml";
    URL url = new URL(urly);
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("POST");
    con.setRequestProperty("Content-Type","application/xml");
    con.setDoOutput(true);
    DataOutputStream wr = new DataOutputStream(con.getOutputStream());
    wr.writeBytes(xml);
    wr.flush();
    wr.close();    
    int responseCode = con.getResponseCode();
    BufferedReader iny = new BufferedReader(new InputStreamReader(con.getInputStream()));
    		  String output;
    		  StringBuffer res = new StringBuffer();

    		  while ((output = iny.readLine()) != null) {
    		   res.append(output);
    		  }
    		  
    		  iny.close();
    		 
    		  //printing result from response
    		  handleResponse(res.toString());
	
	}
	catch (IOException e) {
        System.out.println("error" + e.getMessage());
    }
	}
	public void handleResponse(String xml)  {
		Document doc = Jsoup.parse(xml, "", Parser.xmlParser());
		Elements con = doc.getElementsByTag("docContent");
		type = con.attr("contentType");
		content = doc.select("docContent").text();
	}
	public void compute() throws NoSuchAlgorithmException {
		ResourceBundle rb =  ResourceBundle.getBundle("config");
		orgId = rb.getString("orgId");
		appId = rb.getString("appId");
		secret_key = rb.getString("secret_key");
		timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(new Date());
		text = secret_key + timestamp;
		MessageDigest md = MessageDigest.getInstance( "SHA-256" );
		md.update( text.getBytes( StandardCharsets.UTF_8 ) );
		byte[] digest = md.digest();
		hash = String.format( "%064x", new BigInteger( 1, digest ) );
		xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><PullDocRequest xmlns:ns2=\"http://tempuri.org/\" ver=\"1.0\" ts=\""+ timestamp +"\" txn= \""+ txn +"\" orgId=\""+ orgId+"\" appId=\""+appId+"\" keyhash=\""+ hash+"\"><DocDetails><URI>"+uri+"</URI></DocDetails></PullDocRequest>";

		
	}
	

}


