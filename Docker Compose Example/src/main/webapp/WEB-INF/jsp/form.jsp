<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>${formTitle}</title>
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
            width: min(720px, 100%);
            background: rgba(0,0,0,0.35);
            padding: 2rem 2.5rem;
            border-radius: 1rem;
            box-shadow: 0 1.5rem 3rem rgba(0,0,0,0.35);
        }
        h1 { margin: 0 0 1.5rem; }
        form {
            display: flex;
            flex-direction: column;
            gap: 1.25rem;
        }
        label {
            font-weight: 600;
            display: flex;
            flex-direction: column;
            gap: 0.5rem;
        }
        input[type="text"],
        textarea {
            padding: 0.75rem 1rem;
            border: none;
            border-radius: 0.75rem;
            font-size: 1rem;
            font-family: inherit;
        }
        textarea {
            min-height: 12rem;
            resize: vertical;
        }
        .actions {
            display: flex;
            gap: 1rem;
            flex-wrap: wrap;
        }
        button,
        a.button {
            padding: 0.75rem 1.5rem;
            border-radius: 999px;
            border: none;
            font-weight: 600;
            cursor: pointer;
            text-decoration: none;
        }
        button.save { background: #63b3ed; color: #1a202c; }
        button.delete { background: #fc8181; color: #1a202c; }
        a.button { background: #f6ad55; color: #1a202c; }
        .field-error { color: #fed7d7; font-size: 0.9rem; }
    </style>
</head>
<body>
<main>
    <h1>${formTitle}</h1>
    <c:choose>
        <c:when test="${not empty noteId}">
            <form method="post" action="<c:url value='/notes/${noteId}/update'/>">
        </c:when>
        <c:otherwise>
            <form method="post" action="<c:url value='/notes'/>">
        </c:otherwise>
    </c:choose>
        <label>
            Title
            <input type="text" name="title" value="${noteForm.title}" maxlength="120" required/>
            <c:if test="${not empty errors['title']}">
                <span class="field-error">${errors['title']}</span>
            </c:if>
        </label>
        <label>
            Content
            <textarea name="content" maxlength="4000" required>${noteForm.content}</textarea>
            <c:if test="${not empty errors['content']}">
                <span class="field-error">${errors['content']}</span>
            </c:if>
        </label>
        <div class="actions">
            <button class="save" type="submit">Save</button>
            <a class="button" href="<c:url value='/notes'/>">Cancel</a>
        </div>
    </form>
</main>
</body>
</html>
