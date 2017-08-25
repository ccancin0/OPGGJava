<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<section class="searchForm searchErr">
  <h1>This ${error} at OP.GG. Please check spelling.</h1>
  <p>Did you select the right server? Try searching for the summoner in another region</p>
</section>
<hr>
<section class="searchForm searchErr">
  <img src="<%= request.getContextPath()%>/resources/img/error.png">
  <form method="GET" action="/OP.GG/summoner">
    <input type="text" name="userName" placeholder="Enter the Summoner's name">
    <button type="submit"><i class="fa fa-search" aria-hidden="true"></i></button>
  </form>
</section>
<hr>
<c:forEach var = "item" items="${champs}">
	<c:forEach var="elem" items="${item}">
		<p>Champ <c:out value="${elem.value}"></c:out></p>
	</c:forEach>
</c:forEach>
<section class="searchForm searchReq">
  <div>
    <p>You can view a Summoner's ranked record for season 1 to 7.</p>
    <p>You can view a summary of details entirely solo ranked, team ranked, normal games.</p>
    <p>You can try calculating the MMR of their Ranked Solo games.</p>
    <p>You can view a Summoner's Pick-rate, Win-rate and KDA. for every Champion.</p>
    <p>You can watch the replay or record. (Click on the 'Open Current Game Info' button)</p>
  </div>
</section>
