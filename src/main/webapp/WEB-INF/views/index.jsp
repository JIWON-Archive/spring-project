<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>devopscat cafe</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="common/header.jsp" %>
    <div id="container">
        <div id="menuAdmin">
            <h2 id="menuAdminH2">공지사항</h2>
            <!-- jstl로 세션에 있는 변수에 조건을 건다. -->
            <!-- 세션 공간에 저장되어 있는 MANAGER의 값이 true 일때 작성이라는 버튼이 보이게 한다.-->
            <c:if test="${not empty MANAGER}">
                <!-- location.herf=localhost:8080/noticeAdd -->
                <button type="button" onclick="location.href=`${pageContext.request.contextPath}/noticeAdd`">작성</button>
            </c:if>
        <div id="menuList">

        </div>
        </div>
    </div>
    <%@ include file="common/footer.jsp" %>
</body>
</html>
