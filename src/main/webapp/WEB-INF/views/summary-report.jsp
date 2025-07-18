<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Summary Report</title>
		<meta name='viewport' content='width=device-width, initial-scale=1'>
		<%@include file="./header.jsp" %>
		<script src="${pageContext.request.contextPath}/script/table.js"></script>
        <link rel='stylesheet' type='text/css' media='screen' href='${pageContext.request.contextPath}/stylesheet/table.css'>
	</head>
	<body>
        <h1>Dispatch Register Summary</h1>

            <!-- Table to Display Data -->
           <table id="dispatch-outer-table" border="1">
               <!-- Row for header tables -->
               <tr>
                   <td>
                       <!-- Header Table 1 -->
                       <table border="1">
                           <thead>
                               <tr>
                                   <th>TRANSACTION NO</th>
                                   <th>DISPATCH DATE</th>
                                   <th>PARTY</th>
                               </tr>
                           </thead>
                       </table>
                   </td>
                   <td>
                       <!-- Header Table 2 -->
                       <table border="1">
                           <thead>
                               <tr>
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
                   </td>
               </tr>

               <!-- Row for value tables -->
               <tr>
                   <td>
                       <!-- Value Table 1 -->
                       <table border="1" id="value-table-1">
                           <tbody>
                               <!-- JS inserts 3 values here -->
                           </tbody>
                       </table>
                   </td>
                   <td>
                       <!-- Value Table 2 -->
                       <table border="1" id="value-table-2">
                           <tbody>
                               <!-- JS inserts remaining 16 values here -->
                           </tbody>
                       </table>
                   </td>
               </tr>
           </table>

           <button id="load-data-btn">Load Dispatch Data</button>

            <script>
                $("#load-data-btn").click(function () {
                    const data = {
                        transactionNo: "TR001",
                        dispatchDate: "18/07/2025",
                        party: "ABC Ltd",

                        destination: "Mumbai",
                        transporter: "XYZ Transport",
                        goodsValue: "₹50,000",
                        invoiceNo: "INV1234",
                        lrNo: "LR5678",
                        driverName: "John Doe",
                        lorryNumber: "MH12AB1234",
                        lrDate: "19/07/2025",
                        dispatchDelay: "2 Days",
                        noOfCases: 10,
                        formNum: "F123",
                        cFormDate: "20/07/2025",
                        cFormValue: "₹50,000",
                        podDate: "25/07/2025",
                        podNumber: "POD789",
                        reason: "Heavy Rain"
                    };

                    // First 3 values
                    const values1 = `
                        <tr>
                            <td>${data.transactionNo}</td>
                            <td>${data.dispatchDate}</td>
                            <td>${data.party}</td>
                        </tr>
                    `;
                    $("#value-table-1 tbody").html(values1);

                    // Remaining 16 values
                    const values2 = `
                        <tr>
                            <td>${data.destination}</td>
                            <td>${data.transporter}</td>
                            <td>${data.goodsValue}</td>
                            <td>${data.invoiceNo}</td>
                            <td>${data.lrNo}</td>
                            <td>${data.driverName}</td>
                            <td>${data.lorryNumber}</td>
                            <td>${data.lrDate}</td>
                            <td>${data.dispatchDelay}</td>
                            <td>${data.noOfCases}</td>
                            <td>${data.formNum}</td>
                            <td>${data.cFormDate}</td>
                            <td>${data.cFormValue}</td>
                            <td>${data.podDate}</td>
                            <td>${data.podNumber}</td>
                            <td>${data.reason}</td>
                        </tr>
                    `;
                    $("#value-table-2 tbody").html(values2);
                });
            </script>


	</body>
</html>