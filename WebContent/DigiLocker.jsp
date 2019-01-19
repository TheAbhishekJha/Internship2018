<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@page import="java.util.Date,java.text.*,java.io.*,java.util.Enumeration,
java.security.MessageDigest,java.security.NoSuchAlgorithmException,
java.nio.charset.StandardCharsets,java.math.BigInteger,java.net.HttpURLConnection,java.net.URL,
org.json.simple.*,org.DigiLocker.Curl.*"%>



<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>


</head>
<body>
<h1>My Page</h1>


<div class="share_fm_dl" id="attachment_poi"></div> 
<%
boolean Jflag = false;
String type = new String();
String content = new String();
long timeStamp = System.currentTimeMillis() / 1000L;

MessageDigest md = MessageDigest.getInstance( "SHA-256" );
String requester_id = "****";
String secret_key = "****";
String text = requester_id + secret_key + timeStamp;

// Change this to UTF-16 if needed
md.update( text.getBytes( StandardCharsets.UTF_8 ) );
byte[] digest = md.digest();

String hex = String.format( "%064x", new BigInteger( 1, digest ) );

%>

</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<script type="text/javascript"
src="https://devservices.digitallocker.gov.in/requester/api/2/dl.js"
id="dlshare" data-app-id='<%=requester_id%>' data-app-hash='<%=hex%>'
time-stamp='<%=timeStamp%>' data-callback="my_callback">
</script>
<script type="text/javascript">
function my_callback( args ){
	var stringxml;
	var flag= false;
	var obj = JSON.parse(args);
	console.log(obj.uri);
	var txn = obj.txn;
    var  uri = obj.uri;
    stringxml = '<?xml version="1.0" encoding="UTF-8" standalone="yes"?><PullDocRequest xmlns:ns2="http://tempuri.org/" ver="1.0"  ts="2018-01-24T12:23:27+05:30" txn= '+txn +'appId="NSDLPAN" keyhash="acdfac9a2126c8b0e420747eb8da7e06d1e68a5ee2ff906698b0740415e6baa0"><DocDetails><URI>'+uri+'</URI></DocDetails></PullDocRequest>'
	console.log(stringxml);
    if(stringxml!=null){
   $.ajax({
	   url : "Curl",
	   type : 'GET',
	   data : {txn : obj.txn,
		   	   uri : obj.uri},
	    success : function(){
	    	 var xmhr =new XMLHttpRequest();
	    	 xmhr.open('GET','Curl',true);	
	    	 window.location.href = "http://digilocker.local.com:8080/Button/popup.jsp";

	   }
		   	 
   }); 
    }
}

</script>
</html>
