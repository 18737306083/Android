<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>��¼</title>
<link rel="stylesheet" type="text/css" href="css/index.css" />
</head>
<body>

   <div class="box">
   
       <div class="tubiao">
           <img src="images/logo.png" />
       </div>
       <div class="content" >
            <div class="zhujiemian">
                <font color="#858585" size="+1" face="Verdana, Geneva, sans-serif">�˻�</font>
                <div style="margin-top:10px;margin-bottom:10px">
                <form method="post" action="<%=path%>/users/User_login"  >
                   <input class="rounded" type="text" name="phone" autocomplete="on"
                   required="required"
                   placeholder="�����������ֻ���"
                   title="�ֻ��Ų���Ϊ�ղ���Ϊ��"
                   outline:none 
                   >
              		
                </div>
                <font color="#858585" size="+1" face="Verdana, Geneva, sans-serif">����</font>
                <div style="margin-top:10px;margin-bottom:50px">
                	
                   <input class="rounded" type="password" name="password" 
                   required="required"
                   placeholder="������������ĸ��ɵ�����"
                   pattern="^[A-Za-z0-9]+$"
                   outline:none 
                   	
                   />
                
                </div> 
                <a href="zhuce.jsp" target="_blank"><font size="+1">register</font></a>
                <div style=" float:right;margin-right:15px;">
                <!-- ###############################################�Ժ�Ҫȡ����-->
                
               <input type="submit" class="button"  value="��¼" />
             </form>
                </div>
            </div>
       </div>
        <p align="center"  style="margin-top:70px"><font color="#858585">δ��������</font></p>
   </div>
 
</body>
</html>
