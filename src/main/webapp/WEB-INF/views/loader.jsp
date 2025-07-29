<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name='viewport' content='width=device-width, initial-scale=1'>
		<style>
		    .loader-container {
		        width: 100%;
                height: 100vh;
                z-index: 9999;
                background-color: rgb(80 71 71 / 17%);
                padding: 1rem;
                border-radius: 0.5rem;
            }

            .loader-container div {
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                z-index: 9999;
            }

		</style>
	</head>
	<body>
        <div id="loader" class="loader-container" style="display: none;">
            <div>
                <img src="${pageContext.request.contextPath}/images/loading.svg" alt="Loading..." />
                <span style="color:gray">Please wait</span>
            </div>
        </div>
        <script>
            $('#loader').hide();
        </script>
	</body>
</html>