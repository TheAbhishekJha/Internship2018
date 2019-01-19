<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@page import ="org.DigiLocker.Curl.*"%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
String type = (String)session.getAttribute("type");
String content = (String)session.getAttribute("content");
%>

</body>
<script type="text/javascript">
//alert('in popup');
 var win = window.open(); 
 var url='<iframe width="840" height="560" src="data:'+'<%=type%>'+';base64,'+'<%=content%>'+'"frameborder="0"> </iframe>';
 win.document.write(url);
</script>

</html>