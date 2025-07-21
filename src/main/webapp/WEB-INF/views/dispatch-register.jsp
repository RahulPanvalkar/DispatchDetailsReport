<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Dispatch Register</title>
		<meta name='viewport' content='width=device-width, initial-scale=1'>
        <%@include file="./header.jsp" %>
        <script src="${pageContext.request.contextPath}/script/main.js"></script>
        <script>
            $(document).ready(function() {
                console.log("makeAjaxRequest:", typeof makeAjaxRequest);
                //makeAjaxRequest("get-data?type=default", handleDefaultDataList);
            });
        </script>
	</head>
	<body>
	    <!-- alert message -->
        <s:if test="message != null">
            <script>
                $(document).ready(function() {
                    alert('<s:property value="message" />');
                });
            </script>
        </s:if>

        <main class="form-section">

            <s:form id="dispRegForm" action="dispatch-register-submit" method="post">
                <fieldset>
                    <legend class="form-title">Dispatch Register</legend>
                    <!-- Branch -->
                    <label for="branch" class="required">Branch</label>
                    <div class="input-box">
                        <select name="dto.branch" id="branch">
                            <option value="">Select</option>
                        </select>
                        <h5 id="branchCheck" class="error"></h5>
                    </div>

                    <!-- Stock Point -->
                    <label for="stockPoint" class="required">Stock Point</label>
                    <div class="input-box">
                        <select name="dto.stockPoint" id="stockPoint">
                            <option value="">Select</option>
                        </select>
                        <h5 id="stockPointCheck" class="error"></h5>
                    </div>

                    <!-- Division -->
                    <label for="division" class="required">Division</label>
                    <div class="input-box">
                        <select name="dto.division" id="division">
                            <option value="0">All</option>
                        </select>
                        <h5 id="divisionCheck" class="error"></h5>
                    </div>

                    <!-- Report Type -->
                    <label for="reportType" class="required">Report Type</label>
                    <div class="input-box">
                        <select name="dto.reportType" id="reportType">
                            <option value="">Select</option>
                            <option value="N">SUMMARY</option>
                            <option value="Y">DETAILED</option>
                        </select>
                        <h5 id="reportTypeCheck" class="error"></h5>
                    </div>

                    <!-- Financial Year -->
                    <label for="financialYear" class="required">Financial Year</label>
                    <div class="input-box">
                        <select name="dto.financialYear" id="financialYear">
                            <option value="">Select</option>
                        </select>
                        <h5 id="finYearCheck" class="error"></h5>
                    </div>

                    <!-- Customers -->
                    <label for="customer" class="required">Customer</label>
                    <div class="input-box">
                        <select name="dto.customer" id="customer">
                            <option value="0">All</option>
                        </select>
                        <h5 id="customerCheck" class="error"></h5>
                    </div>

                    <!-- Start Date -->
                    <label for="startDate" class="required">Start Date</label>
                    <div class="input-box">
                        <input type="text" name="dto.startDate" class="datePicker date" value="" id="startDate" placeholder="dd/mm/yyyy">
                        <h5 id="startDtCheck" class="error"></h5>
                    </div>

                    <!-- End Date -->
                    <label for="endDate" class="required">End Date</label>
                    <div class="input-box">
                        <input type="text" name="dto.endDate" class="datePicker date" value="" id="endDate" placeholder="dd/mm/yyyy">
                        <h5 id="endDtCheck" class="error"></h5>
                    </div>

                    <div class="form-actions">
                        <button type="submit" id="submit-btn" class="btn">Submit</button>
                        <button type="reset" id="exit-btn" class="btn">Reset</button>
                    </div>
                </fieldset>
            </s:form>
        </main>
	</body>
</html>