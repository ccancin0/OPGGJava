<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<section class="container summonerStats">
	<div class="header">
		<h1>${userName}</h1>
		<div class="profileIcon">
			<img
				src="<%= request.getContextPath()%>/resources/img/${soloTier}.png">
			<img
				src="<%= request.getContextPath()%>/resources/img/profileIcon${profileIcon}.jpg"
				id="centerImg">
		</div>
	</div>
	<div class="summonerInfo">
		<div class="RankInfo">
			<img
				src="<%= request.getContextPath()%>/resources/img/${soloTier}_${soloRank}.png">
			<div>
				<p class="rankTier">${soloTier} ${soloRank}</p>
				<p>
					<span>${soloLp} LP </span>/ ${soloWins}W ${soloLoses}L
				</p>
        <p>Win Ratio ${soloWinRatio}%</p>
				<p>${soloLeague}</p>
			</div>
		</div>
		<div class="RankInfo">
			<img
				src="<%= request.getContextPath()%>/resources/img/${flexTier}_${flexRank}.png">
			<div>
				<p class="rankTier">${flexTier} ${flexRank}</p>
				<p>
					<span>${flexLp} LP </span>/ ${flexWins}W ${flexLoses}L
				</p>
        <p>Win Ratio ${flexWinRatio}%</p>
				<p>${flexLeague}</p>
			</div>
		</div>
	</div>
</section>
<c:forEach var = "item" items="${champs}">
	<c:forEach var="elem" items="${item}">
		<p>Champ <c:out value="${elem.value}"></c:out></p>
	</c:forEach>
</c:forEach>