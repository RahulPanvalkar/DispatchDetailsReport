<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name='viewport' content='width=device-width, initial-scale=1'>
		<style>
		    .message-container {
                position: fixed;
                top: 1.8rem;
                right: 1.5rem;
                display: none;
                z-index: 9999;
            }

            .message-box {
                width: 15rem;
                padding: 0.4rem 0.5rem;
                margin-bottom: 0.8rem;
                border-radius: 0.2rem;
                position: relative;
            }

            .error-box {
                background-color: #f8d7da;
                color: #721c24;
                border: 1px solid #f5c6cb;
            }

            .success-box {
                background-color: #d4edda;
                color: #155724;
                border: 1px solid #c3e6cb;
            }

            .close-btn {
                position: absolute;
                top: 0.6rem;
                right: 1rem;
                background: none;
                border: none;
                font-size: 1.15rem;
                font-weight: bold;
                color: inherit;
                cursor: pointer;
            }

            .close-btn:hover {
                border: 1px solid #721c24;
                border-radius: 50%;
            }

            @media (max-width: 30rem) {
                .message-container {
                    top: auto;
                    bottom: 1rem;
                    right: 50%;
                    transform: translateX(50%);
                    left: auto;
                }
            }
        </style>
	</head>

	<body>
	    <div class="message-container" id="msgBox">
            <div class="message-box <s:property value='error ? "error-box" : "success-box"' />" >
                <button class="close-btn" onclick="$('#msgBox').fadeOut();">&times;</button>
                <ul>
                    <li><s:property value="message" /></li>
                </ul>
            </div>
        </div>

        <script>
            $(document).ready(function () {
                $('#msgBox').fadeIn(); // Show on page load
                //setTimeout(() => $('#msgBox').fadeOut(), 5000); // Hide after 5 sec
            });
        </script>
	</body>
</html>