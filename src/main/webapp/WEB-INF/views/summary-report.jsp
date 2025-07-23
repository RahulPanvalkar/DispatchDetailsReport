<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

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
        <%@include file="./loader.jsp" %>

	    <!-- alert message -->
        <s:if test="message != null">
            <script>
                $(document).ready(function() {
                    alert('<s:property value="message" />');
                });
            </script>
        </s:if>

	    <main class="main-div">

            <div class="header">
                <h2 id="compName">HEALTHCARE PVT LTD.</h2>
                <h3 id="location">HEALTHCARE LTD-BHIW</h3>
            </div>

            <div class="sub-header">
                <p id="reportHeader"><strong>Dispatch Register Summary Report From:</strong> 01/04/2023 To 31/03/2024</p>
            </div>

            <div class="date-time">Report Date: 01/10/2024 Time: 6:07:48 PM</div>
            <div class="financial-year">Financial Year From 01/04/2023 To 31/03/2024</div>

            <div style="clear: both;"></div>

            <div class="buttons">
                <button class="btn" onclick="window.location.href='dispatch-register'">Back</button>
                <a id="download" href="download-file">
                    <img src="${pageContext.request.contextPath}/images/excel_icon.png" alt="download" />
                </a>
            </div>

            <div class="report-container">

                <!-- Header Tables -->
                <table class="outer-tbl"  cellspacing="0" cellpadding="0">
                    <tr class="outer-tbl-tr">
                        <td class="outer-tbl-td">
                            <div class="fixed-header">
                                <table id="fixed-header-table" class="inner-tbl fixed-table">
                                    <thead>
                                        <tr class="inner-tbl-tr">
                                            <th class="tran"><div class="header-name"><span>TRANSACTION</span><span>NO</span></div></th>
                                            <th class="disp-date"><div class="header-name"><span>DISPATCH</span><span>DATE</span></div></th>
                                            <th class="party"><div class="header-name"><span>PARTY</span></div></th>
                                        </tr>
                                    </thead>
                                </table>
                            </div>
                        </td>
                        <td>
                            <div class="scroll-header">
                                <table id="scroll-header-table" class="inner-tbl">
                                    <thead>
                                        <tr class="inner-tbl-tr">
                                            <th class="dest"><div class="header-name"><span>DESTINATION</span></div></th>
                                            <th class="transp"><div class="header-name"><span>TRANSPORTER</span></div></th>
                                            <th class="goods-val"><div class="header-name text-right"><span>GOODS</span><span>VALUE</span></div></th>
                                            <th class="inv-no"><div class="header-name"><span>INVOICE</span><span>NO</span></div></th>
                                            <th class="lr-no"><div class="header-name text-right">LR NO</div></th>
                                            <th class="driv-no"><div class="header-name"><span>DRIVER</span><span>NAME</span></div></th>
                                            <th class="lorry-no"><div class="header-name"><span>LORRY</span><span>NUMBER</span></div></th>
                                            <th class="lr-date"><div class="header-name"><span>LR</span><span>DATE</span></div></th>
                                            <th class="disp-delay"><div class="header-name"><span>DISPATCH</span><span>DELAY</span></div></th>
                                            <th class="cases"><div class="header-name text-right"><span>NO</span><span>OF CASES</span></div></th>
                                            <th class="form-no"><div class="header-name"><span>FORM</span><span>NUM</span></div></th>
                                            <th class="form-date"><div class="header-name"><span>C FORM</span><span>DATE</span></div></th>
                                            <th class="form-val"><div class="header-name"><span>C FORM</span><span>VALUE</span></div></th>
                                            <th class="pod-date"><div class="header-name"><span>POD</span><span>DATE</span></div></th>
                                            <th class="pod-num"><div class="header-name text-right"><span>POD</span><span>NUMBER</span></div></th>
                                            <th class="reason"><div class="header-name"><span>REASON</span></div></th>
                                        </tr>
                                    </thead>
                                </table>
                            </div>
                        </td>
                    </tr>

                    <tr class="outer-tbl-tr">
                        <td class="outer-tbl-td fixed-table"> <div id="divLabel" class="division-label">Division : MAIN</div></td>
                        <td class="outer-tbl-td"> <div class="division-label  scroll-header"><span>&nbsp;</span></div></td>
                    </tr>

                    <!-- Data Row Tables -->
                    <tr class="outer-tbl-tr">
                        <td class="outer-tbl-td">
                            <div class="fixed-body">
                                <table id="fixed-body-table" class="inner-tbl fixed-table">
                                    <tbody>
                                        <!-- Dynamically populated using jQuery -->
                                    </tbody>
                                </table>
                            </div>
                        </td>
                        <td class="outer-tbl-td">
                            <div class="scroll-body">
                                <table id="scroll-body-table" class="inner-tbl">
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