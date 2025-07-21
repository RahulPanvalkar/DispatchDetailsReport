<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Summary Report</title>
		<meta name='viewport' content='width=device-width, initial-scale=1'>
		<%@include file="./header.jsp" %>
		<script src="${pageContext.request.contextPath}/script/report.js"></script>
        <link rel='stylesheet' type='text/css' media='screen' href='${pageContext.request.contextPath}/stylesheet/report.css'>
	</head>
	<body>
	    <main class="main-div">
            <div class="header">
                <h2>HEALTHCARE PVT LTD.</h2>
                <h3>HEALTHCARE LTD-BHIW</h3>
            </div>

            <div class="sub-header">
                <p><strong>Dispatch Register Summary Report From:</strong> 01/04/2023 To 31/03/2024</p>
            </div>

            <div class="date-time">Report Date: 01/10/2024 Time: 6:07:48 PM</div>
            <div class="financial-year">Financial Year From 01/04/2023 To 31/03/2024</div>

            <div style="clear: both;"></div>

            <div class="buttons">
                <button  class="btn" onclick="history.back()">Back</button>
                <span id="download">
                    <img src="${pageContext.request.contextPath}/images/excel_icon.png" alt="download" />
                </span>
            </div>

            <div class="report-container">

                <!-- Header Tables -->
                <table class="outer-tbl"  cellspacing="0" cellpadding="0">
                    <tr class="outer-tbl-tr">
                        <td class="outer-tbl-td">
                            <div class="fixed">
                                <table class="inner-tbl fixed-table">
                                    <thead>
                                        <tr class="inner-tbl-tr">
                                            <th>TRANSACTION NO</th>
                                            <th>DISPATCH DATE</th>
                                            <th>PARTY</th>
                                        </tr>
                                    </thead>
                                </table>
                            </div>
                        </td>
                        <td>
                            <div class="scroll-header fixed"  style="overflow-y: scroll;">
                                <table class="inner-tbl">
                                    <thead>
                                        <tr class="inner-tbl-tr">
                                            <th>DESTINATION</th>
                                            <th>TRANSPORTER</th>
                                            <th>GOODS VALUE</th>
                                            <th>INVOICE NO</th>
                                            <th>LR NO</th>
                                            <th>DRIVER NAME</th>
                                            <th>LORRY NUMBER</th>
                                            <th>LR DATE</th>
                                            <th>DISPATCH DELAY</th>
                                            <th>NO OF CASES</th>
                                            <th>FORM NUM</th>
                                            <th>C FORM DATE</th>
                                            <th>C FORM VALUE</th>
                                            <th>POD DATE</th>
                                            <th>POD NUMBER</th>
                                            <th>REASON</th>
                                        </tr>
                                    </thead>
                                </table>
                            </div>
                        </td>
                    </tr>

                    <tr class="outer-tbl-tr">
                        <td class="outer-tbl-td fixed-table"> <div class="division-label">Division : MAIN</div></td>
                        <td class="outer-tbl-td"> <div class="division-label  scroll-header"><span>&nbsp;</span></div></td>
                    </tr>

                    <!-- Data Row Tables -->
                    <tr class="outer-tbl-tr">
                        <td class="outer-tbl-td">
                            <div class="fixed" style="overflow-x: scroll;">
                                <table id="value-table-1" class="inner-tbl fixed-table">
                                    <tbody>
                                        <!-- Dynamically populated using jQuery -->
                                    </tbody>
                                </table>
                            </div>
                        </td>
                        <td class="outer-tbl-td">
                            <div class="scroll-body">
                                <table id="value-table-2" class="inner-tbl">
                                    <tbody>
                                        <!-- Dynamically populated using jQuery -->
                                    </tbody>
                                </table>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>

            <div class="btn-right">
                <button class="btn">Exit</button>
            </div>
        </main>

	</body>
</html>