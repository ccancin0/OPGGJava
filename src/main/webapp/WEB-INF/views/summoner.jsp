<jsp:include page="../layouts/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:choose>
	<c:when test="${empty error}">
		<jsp:include page="./success.jsp" />
	</c:when>
	<c:otherwise>
		<jsp:include page="./error.jsp" />
	</c:otherwise>
</c:choose>
