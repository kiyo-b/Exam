<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>エラー画面</title>
</head>
<body>

<h1>エラーが発生しました</h1>

<p><strong>例外内容：</strong></p>
<pre>
<%= exception %>
</pre>

<p><strong>スタックトレース：</strong></p>
<pre>
<%
    if (exception != null) {
        exception.printStackTrace(new java.io.PrintWriter(out));
    }
%>
</pre>

</body>
</html>