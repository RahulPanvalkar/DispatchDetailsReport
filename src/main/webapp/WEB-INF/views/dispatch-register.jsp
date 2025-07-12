<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Dispatch Register</title>
		<meta name='viewport' content='width=device-width, initial-scale=1'>
        <%@include file="./header.jsp" %>
	</head>
	<body>
        <main class="form-section">

            <s:form id="dispRegForm" action="dispatch-register-submit" method="post">
                <fieldset>
                    <legend class="form-title">Dispatch Register</legend>
                    <!-- Branch -->
                    <label for="branch" class="required">Branch</label>
                    <select name="dto.branch" id="branch">
                        <option value="">Select</option>
                    </select>

                    <!-- Stock Point -->
                    <label for="stockPoint" class="required">Stock Point</label>
                    <select name="dto.stockPoint" id="stockPoint">
                        <option value="">Select</option>
                    </select>

                    <!-- Division -->
                    <label for="division" class="required">Division</label>
                    <select name="dto.division" id="division">
                        <option value="">Select</option>
                    </select>

                    <!-- Report Type -->
                    <label for="reportType" class="required">Report Type</label>
                    <select name="dto.reportType" id="reportType">
                        <option value="">Select</option>
                        <option value="N">SUMMARY</option>
                        <option value="Y">DETAILED</option>
                    </select>

                    <!-- Financial Year -->
                    <label for="financialYear" class="required">Financial Year</label>
                    <select name="dto.financialYear" id="financialYear">
                        <option value="">Select</option>
                    </select>

                    <!-- Customers -->
                    <label for="customer" class="required">Customers</label>
                    <select name="dto.customer" id="customer">
                        <option value="">All</option>
                    </select>

                    <!-- Start Date -->
                    <label for="startDate" class="required">Start Date</label>
                    <input type="text" name="dto.startDate" class="datePicker date" value="" id="startDate" placeholder="dd/mm/yyyy">

                    <!-- End Date -->
                    <label for="endDate" class="required">End Date</label>
                    <input type="text" name="dto.endDate" class="datePicker date" value="" id="endDate" placeholder="dd/mm/yyyy">

                    <div class="form-actions">
                        <button type="submit" id="submit-btn" class="btn">Submit</button>
                        <button type="reset" id="exit-btn" class="btn">Exit</button>
                    </div>
                </fieldset>
            </s:form>
        </main>
	</body>
</html>