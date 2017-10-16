<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>파일 업로드 예제</h1>
<!-- action="upload"라고 하면 form에서 상대경로를 찾으니까 form.jsp를 부르는 url위치에서 /upload로 가게 된다. -->
<!-- enctype은 전송되는 데이터 형식을 설정하는데, default는 application/www-form-urlencoded이다. text/plain은 인코딩 하지 않은 문자 상태로 전송한다. -->
<!-- enctype에서 multipart/form-data라고 쓰면 파일이나 이미지를 서버로 전송할 경우, 파트별로 나눠서 전송한다. -->
<form method="post" action="upload" enctype="multipart/form-data">

	<label>email:</label>
	<input type="text" name="email" value="kickscar@gmail.com">
	<br><br>
	
	<label>파일1:</label>
	<input type="file" name="file">
	<br><br>
	
	<label>파일2:</label>
	<input type="file" name="file">
	<br><br>
	
	<br>
	<input type="submit" value="upload">
</form>
</body>
</html>