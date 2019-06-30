<%--
  Created by IntelliJ IDEA.
  User: zhongyuan
  Date: 2019/2/25
  Time: 19:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="UploadServlet" method="post" enctype="multipart/form-data">
    用户名：<input type="text" name="name"><br/>
    上传文件：<<input type="file" name="file"><br/>
    <input type="submit" value="注册"><br/>
</form>
</body>
</html>
