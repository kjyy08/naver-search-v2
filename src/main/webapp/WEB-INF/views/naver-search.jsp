<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>네이버 뉴스 기사 검색</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            padding: 20px;
            background-color: #f4f4f9;
        }
        h1 {
            color: #333;
        }
        .search-form {
            margin-bottom: 20px;
        }
        input[type="text"] {
            padding: 10px;
            font-size: 14px;
            width: 300px;
        }
        input[type="submit"] {
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
        ul {
            list-style-type: none;
            padding: 0;
        }
        li {
            padding: 8px;
            margin: 5px 0;
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
    </style>
</head>
<body>
<h1>Search Results for "${param.keyword}"</h1>

<form class="search-form" action="/" method="get">
    <input type="text" name="keyword" placeholder="Search for news..." value="${param.keyword}">
    <input type="submit" value="Search">
</form>

<ul>
    <c:forEach var="item" items="${searchResults}">
        <li>
            <a href="${item.link}" target="_blank">${item.title}</a><br>
            <small>${item.description}</small><br>
            <small>Published: ${item.pubDate}</small>
        </li>
    </c:forEach>
</ul>

</body>
</html>
