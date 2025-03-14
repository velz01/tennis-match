<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tennis Scoreboard | Finished Matches</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">
    <style><%@ include file="/css/style.css"%></style>
    <style><%@ include file="/js/app.js"%></style>
</head>

<body>
<header class="header">
    <section class="nav-header">
        <div class="brand">
            <div class="nav-toggle">
                <img src="images/menu.png" alt="Logo" class="logo">
            </div>
            <span class="logo-text">TennisScoreboard</span>
        </div>


        <div>
            <nav class="nav-links">
                <a class="nav-link" href="${pageContext.request.contextPath}/index">Home</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/matches">Matches</a>
            </nav>
        </div>
    </section>
</header>
<main>
    <div class="container">
        <h1>Matches</h1>
        <div class="input-container">
            <form action="${pageContext.request.contextPath}/matches">
                <input class="input-filter" placeholder="Filter by name" name="filter_by_player_name" type="text" />
            </form>

            <div>
                <a href="${pageContext.request.contextPath}/matches">
                    <button class="btn-filter">Reset Filter</button>
                </a>
            </div>
        </div>

        <table class="table-matches">
            <tr>
                <th>Player One</th>
                <th>Player Two</th>
                <th>Winner</th>
            </tr>


            <c:forEach var="match" items="${requestScope.matches}">
                <tr>
                    <th>${match.player1().getName()}</th>
                    <th>${match.player2().getName()}</th>
                    <th>${match.winner().getName()}</th>
                </tr>
            </c:forEach>

            <c:if test="${not empty requestScope.error}">
                <p style="color: red;">${requestScope.error}</p>
                <button value="First page"><a class="nav-link" href="${pageContext.request.contextPath}/matches">Matches</a></button>
            </c:if>

        </table>

        <div class="pagination">
            <c:if test="${requestScope.pageNumber != 1 && empty requestScope.filterName}">
                <a class="prev" href="${pageContext.request.contextPath}/matches?page=${ requestScope.pageNumber - 1}"> < </a>

            </c:if>
            <c:if test="${requestScope.pageNumber != 1 && not empty requestScope.filterName}">
                <a class="prev" href="${pageContext.request.contextPath}/matches?page=${ requestScope.pageNumber - 1}&filter_by_player_name=${requestScope.filterName}"> < </a>
            </c:if>

            <c:if test="${requestScope.matches.size() == requestScope.pageSize && empty requestScope.filterName}">
                <a class="next" href="${pageContext.request.contextPath}/matches?page=${requestScope.pageNumber >= requestScope.amountPages ? requestScope.amountPages : requestScope.pageNumber + 1}"> > </a>

            </c:if>
            <c:if test="${requestScope.matches.size() == requestScope.pageSize && not empty requestScope.filterName}">
                <a class="next" href="${pageContext.request.contextPath}/matches?page=${requestScope.pageNumber >= requestScope.amountPages ? requestScope.amountPages : requestScope.pageNumber + 1}&filter_by_player_name=${requestScope.filterName}"> > </a>
            </c:if>

        </div>
    </div>
</main>
<footer>
    <div class="footer">
        <p>&copy; Tennis Scoreboard, project from <a href="https://zhukovsd.github.io/java-backend-learning-course/">zhukovsd/java-backend-learning-course</a>
            roadmap.</p>
    </div>
</footer>
</body>
</html>
