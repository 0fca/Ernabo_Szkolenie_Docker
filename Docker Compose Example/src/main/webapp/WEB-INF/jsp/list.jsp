<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>Notebook</title>
    <style>
        :root { color-scheme: light dark; }
        * { box-sizing: border-box; }
        body {
            margin: 0;
            font-family: system-ui, -apple-system, "Segoe UI", sans-serif;
            background: linear-gradient(135deg, #1e3c72, #2a5298);
            color: #ffffff;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 2rem 1rem 3rem;
        }
        main {
            width: min(900px, 100%);
            background: rgba(0,0,0,0.35);
            padding: 2rem 2.5rem;
            border-radius: 1rem;
            box-shadow: 0 1.5rem 3rem rgba(0,0,0,0.35);
        }
        h1 { margin: 0 0 1.5rem; }
        a.button {
            display: inline-block;
            padding: 0.75rem 1.25rem;
            border-radius: 999px;
            background: #f6ad55;
            color: #1a202c;
            text-decoration: none;
            font-weight: 600;
            transition: transform 0.2s ease, box-shadow 0.2s ease;
        }
        a.button:hover {
            transform: translateY(-2px);
            box-shadow: 0 0.75rem 1.2rem rgba(0,0,0,0.25);
        }
        .flash {
            margin: 0 0 1.5rem;
            padding: 0.75rem 1rem;
            border-radius: 0.75rem;
            font-size: 0.95rem;
            background: rgba(56,161,105,0.9);
        }
        .flash-error { background: rgba(229,62,62,0.9); }
        table {
            width: 100%;
            border-collapse: collapse;
            background: rgba(0,0,0,0.2);
        }
        th, td {
            padding: 0.9rem 0.75rem;
            text-align: left;
        }
        th {
            text-transform: uppercase;
            font-size: 0.75rem;
            letter-spacing: 0.08rem;
        }
        tr:nth-child(even) { background: rgba(0,0,0,0.18); }
        .actions {
            display: flex;
            gap: 0.5rem;
        }
        .actions form button,
        .actions a {
            border: none;
            padding: 0.4rem 0.8rem;
            border-radius: 0.5rem;
            font-size: 0.85rem;
            cursor: pointer;
            font-weight: 600;
            text-decoration: none;
        }
        .actions a { background: #63b3ed; color: #1a202c; }
        .actions form button { background: #fc8181; color: #1a202c; }
        .empty-state {
            text-align: center;
            padding: 3rem 1rem;
            opacity: 0.9;
        }
        footer {
            margin-top: 2rem;
            opacity: 0.75;
            font-size: 0.85rem;
        }
    </style>
</head>
<body>
<main>
    <header style="display:flex;justify-content:space-between;align-items:center;flex-wrap:wrap;gap:1rem;">
        <h1>Notebook</h1>
        <a class="button" href="<c:url value='/notes/new'/>">Add note</a>
    </header>

    <c:if test="${not empty message}">
        <p class="flash">${message}</p>
    </c:if>
    <c:if test="${not empty error}">
        <p class="flash flash-error">${error}</p>
    </c:if>

    <c:choose>
        <c:when test="${not empty notes}">
            <table>
                <thead>
                <tr>
                    <th scope="col">Title</th>
                    <th scope="col">Updated</th>
                    <th scope="col">Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="note" items="${notes}">
                    <tr>
                        <td>${note.title}</td>
                        <td><time datetime="${note.updatedAt}">${note.updatedAt}</time></td>
                        <td class="actions">
                            <a href="<c:url value='/notes/${note.id}/edit'/>">Edit</a>
                            <form method="post" action="<c:url value='/notes/${note.id}/delete'/>" onsubmit="return confirm('Delete this note?');">
                                <button type="submit">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <div class="empty-state">
                <p>No notes yet. Create the first one to get started.</p>
            </div>
        </c:otherwise>
    </c:choose>
</main>
<footer>
    Served by Spring Boot and JSP inside Docker Compose.
</footer>
</body>
</html>
