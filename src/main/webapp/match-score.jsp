<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tennis Scoreboard | Match Score</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto+Mono:wght@300&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">

    <script src="js/app.js"></script>
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
                <a class="nav-link" href="#">Home</a>
                <a class="nav-link" href="#">Matches</a>
            </nav>
        </div>
    </section>
</header>
<main>
    <div class="container">
        <h1>Current match</h1>
        <div class="current-match-image"></div>
        <section class="score">
            <table class="table">
                <thead class="result">
                <tr>
                    <th class="table-text">Player</th>
                    <th class="table-text">Sets</th>
                    <th class="table-text">Games</th>
                    <th class="table-text">Points</th>
                </tr>
                </thead>
                <tbody>
                <tr class="player1">
                    <td class="table-text">${requestScope.player1}</td>
                    <td class="table-text">${requestScope.matchScore.getSetsPlayer1()}</td>
                    <td class="table-text">${requestScope.matchScore.getGamesPlayer1()}</td>
                    <td class="table-text">${requestScope.matchScore.getPointsPlayer1()}</td>
                    <c:if test="${empty requestScope.winner}">
                        <td class="table-text">
                            <form action="${pageContext.request.contextPath}/match-score" method="post">
                                <input type="hidden" name="player" value="player1">
                                <input type="hidden" name="uuid" value="${requestScope.uuid}">
                                <button type="submit" class="score-btn">Score</button>
                            </form>
                        </td>
                    </c:if>
                </tr>
                <tr class="player2">
                    <td class="table-text">${requestScope.player2}</td>
                    <td class="table-text">${requestScope.matchScore.getSetsPlayer2()}</td>
                    <td class="table-text">${requestScope.matchScore.getGamesPlayer2()}</td>
                    <td class="table-text">${requestScope.matchScore.getPointsPlayer2()}</td>

                    <c:if test="${empty requestScope.winner}">
                        <td class="table-text">
                            <form action="${pageContext.request.contextPath}/match-score" method="post">
                                <input type="hidden" name="player" value="player2">
                                <input type="hidden" name="uuid" value="${requestScope.uuid}">
                                <button type="submit" class="score-btn">Score</button>
                            </form>
                        </td>
                    </c:if>


                </tr>
                <c:if test="${not empty requestScope.winner}">
                    Победитель матча: ${requestScope.winner.getName()}
                </c:if>
                <c:if test="${empty requestScope.winner}">
                    <c:if test="${requestScope.matchScore.getGameScore().isTie()}">
                        <h3>Tie</h3>
                    </c:if>
                    <c:if test="${requestScope.matchScore.getGameScore().isAdvantagePlayer1()}">
                        <h5>Преимущество за ${requestScope.player1}</h5>
                    </c:if>
                    <c:if test="${requestScope.matchScore.getGameScore().isAdvantagePlayer2()}">
                        <h5>Преимущество за ${requestScope.player2}</h5>
                    </c:if>
                </c:if>

                </tbody>
            </table>
        </section>
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
