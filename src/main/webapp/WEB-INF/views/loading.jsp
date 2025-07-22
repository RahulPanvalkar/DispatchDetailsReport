<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name='viewport' content='width=device-width, initial-scale=1'>
		<style>
		    .loader-container {
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                z-index: 9999;
                background-color: rgba(255, 255, 255, 0.7);
                padding: 1rem;
                border-radius: 0.5rem;
            }
		</style>
	</head>
	<body>
        <div id="loader" class="loader-container" style="display: none;">
            <img src="${pageContext.request.contextPath}/images/loading.svg" alt="Loading..." />
        </div>
	</body>
</html>