<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>Bookshelves</title>
</head>
<body>
<!--<div th:switch="${bookshelves}">-->
<!--    <div th:case="*">-->
        <table>
            <tr>
                <th>id</th>
                <th>title</th>
                <th>author</th>
                <th>publicationYear</th>
                <th>price</th>
                <th>arrivalDate</th>
            </tr>

            <#list bookshelves as bookshelf>
            <tr th: each="bookshelf: $ {bookshelves}">
                <!--                <td th:text="${bookshelf.id}"></td>-->
                <!--                <td th:text="${bookshelf.book.title}"></td>-->
                <!--                <td th:text="${bookshelf.book.author}"></td>-->
                <!--                <td th:text="${bookshelf.book.publicationYear}"></td>-->
                <!--                <td th:text="${bookshelf.price}"></td>-->
                <!--                <td th:text="${bookshelf.arrivalDate}"></td>-->
                <td>${bookshelf.id}</td>
                <td>${bookshelf.book.title}</td>
                <td>${bookshelf.book.author}</td>
                <td>${bookshelf.book.publicationYear}</td>
                <td>${bookshelf.price}</td>
                <td>${bookshelf.arrivalDate}</td>
            </tr>
        </#list>
        </table>
<!--    </div>-->
<!--</div>-->
<br/>
<a href="/"> go to start page</a>
</body>
</html>