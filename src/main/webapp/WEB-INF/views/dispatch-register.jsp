<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.ddr.model.DispatchRegisterDTO" %>

<!DOCTYPE html>
<html>
	<head>
        <meta charset="UTF-8">
        <title>Dispatch Register</title>
        <meta name='viewport' content='width=device-width, initial-scale=1'>
        <%@include file="./header.jsp" %>
        <script src="${pageContext.request.contextPath}/script/main.js"></script>

        <%
            DispatchRegisterDTO dto = (DispatchRegisterDTO) session.getAttribute("dispatch_dto");
            System.out.println("previous dispachRegister dto >> "+dto);
        %>

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
                            <option value="0">All</option>
                        </select>
                        <h5 id="branchCheck" class="error"></h5>
                    </div>

                    <!-- Stock Point -->
                    <label for="stockPoint" class="required">Stock Point</label>
                    <div class="input-box">
                        <select name="dto.stockPoint" id="stockPoint">
                            <option value="">Select</option>
                            <option value="0">All</option>
                        </select>
                        <h5 id="stockPointCheck" class="error"></h5>
                    </div>

                    <!-- Division -->
                    <label for="division" class="required">Division</label>
                    <div class="input-box">
                        <select name="dto.division" id="division">
                            <option value="">Select</option>
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
                            <option value="">Select</option>
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
                        <button type="reset" id="reset-btn" class="btn">Reset</button>
                    </div>
                </fieldset>
            </s:form>
        </main>


        <s:if test="error">
            <script>
                var formData = {
                    branch: "<%= dto != null ? dto.getBranch() : "" %>",
                    stockPoint: "<%= dto != null ? dto.getStockPoint() : "" %>",
                    division: "<%= dto != null ? dto.getDivision() : "" %>",
                    reportType: "<%= dto != null ? dto.getReportType() : "" %>",
                    financialYear: "<%= dto != null ? dto.getFinancialYear() : "" %>",
                    customer: "<%= dto != null ? dto.getCustomer() : "" %>",
                    startDate: "<%= dto != null ? dto.getStartDate() : "" %>",
                    endDate: "<%= dto != null ? dto.getEndDate() : "" %>"
                };

                // Wait for dropdown to be populated before setting value
                function setSelectValueWhenReady(selector, value) {
                    const interval = setInterval(() => {
                        const $select = $(selector);
                        if ($select.find('option').length > 2) {
                            $select.val(value);
                            clearInterval(interval);
                        }
                    }, 100);
                }

                $(document).ready(function () {
                    //console.log("##formData >> ", formData);
                    // Set select values
                    //setSelectValueWhenReady('#branch', formData.branch);
                    //setSelectValueWhenReady('#stockPoint', formData.stockPoint);
                    setSelectValueWhenReady('#division', formData.division);
                    setSelectValueWhenReady('#financialYear', formData.financialYear);
                    //setSelectValueWhenReady('#customer', formData.customer);

                    // These fields don't depend on dropdown options
                    $('#reportType').val(formData.reportType);
                    $('#startDate').val(formData.startDate);
                    $('#endDate').val(formData.endDate);

                    setTimeout(() => {
                        // trigger validation
                        $('#reportType').trigger("change");
                        $('#division').trigger("change");
                        $('#financialYear').trigger("change");
                        $('#startDate').trigger("change");
                        $('#endDate').trigger("change");
                    }, 500);
                });
            </script>
        </s:if>

        <%
            session.removeAttribute("dispatch_dto");
            System.out.println("after session cleared >> dto >> "+dto);
        %>
	</body>
</html>