$(document).ready(function() {
   //console.log("page loaded >> jquery working..");

    // Initialize Datepicker for DATE fields and set min max value
    $(".datePicker").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: "dd/mm/yy"
    });

    // GENERIC FUNCTION TO SEND AJAX REQUEST
    function makeAjaxRequest(URL, callback) {
       //console.log("sending ajax request to >> [",URL,"]");
        $.ajax({
            url: URL,
            type: 'GET',
            dataType: 'json',
            success: function(response) {
                //console.log("makeAjaxRequest >> response >> ", response);
                callback(response);
            },
            error: function(jqXhr, textStatus, errorMessage) {
                console.error("Error occurred: ", errorMessage);
                callback([]);
            }
        });
    }

    // FUNCTION TO HANDLE DEFAULT DATA LIST
    function handleDefaultDataList(response) {
       //console.log("handleDefaultDataList >> response >> ", response);
        let data = response.data;

        // Check if the response contains data
        if (data.branches && Array.isArray(data.branches)) {
            // Wrapping the data in a response-like object
            handleLocationData({ data: data.branches });
        }

        if (data.divisions && Array.isArray(data.divisions)) {
            handleDivisionData({ data: data.divisions });
        }

        if (data.fin_years && Array.isArray(data.fin_years)) {
            handleFinancialYearData({ data: data.fin_years });
        }

        if (data.stock_points && Array.isArray(data.stock_points)) {
            handleStockPointData({ data: data.stock_points });
        }
    }


    // FUNCTION TO POPULATE BRANCH LIST
    function handleLocationData(response) {
        let locationData = response.data;
        //console.log("json locationData >> ", locationData);

        // Populate branch
        locationData.forEach(function (location){
            //console.log(`-- ${location.loc_id} : ${location.loc_name}`);
            $('#branch').append(
                $('<option>', {
                    value: location.loc_id,
                    text: location.loc_name
                })
            );
        });
    }

    // FUNCTION TO POPULATE STOCK POINT LIST
    function handleStockPointData(response) {
        let stockPointData = response.data;
        //console.log("json stockPointData >> ", stockPointData);

        // Populate branch
        stockPointData.forEach(function (stockPoint){
            //console.log(`-- ${stockPoint.stock_point_id} : ${stockPoint.loc_name}`);
            $('#stockPoint').append(
                $('<option>', {
                    value: stockPoint.stock_point_id,
                    text: stockPoint.loc_name
                })
            );
        });
    }

    // TO CHANGE STOCK POINT and CUSTOMER VALUE BASED ON BRANCH
    $("#branch").on("change", function() {
        let branchId = $(this).val();
       //console.log("branchId >> ", branchId);
        if(!branchId) {
            // set stock point
            $('#stockPoint').empty();
            $('#stockPoint').append(
                $('<option>', {
                    value: "",
                    text: "Select"
                })
            );
            //set customer
            $('#customer').empty();
            $('#customer').append(
                $('<option>', {
                    value: "",
                    text: "Select"
                })
            );
            makeAjaxRequest("get-data?type=stockPointList", handleStockPointData);
            return;
        }
        if(branchId === '0') {
            // Set stockPoint as 'All'
            $('#stockPoint').empty();
            $('#stockPoint').append(
                $('<option>', {
                    value: "0",
                    text: "All",
                    selected: true
                })
            );
            $('#stockPoint').trigger("change");

            // Set stockPoint as 'All'
            $('#customer').empty();
            $('#customer').append(
                $('<option>', {
                    value: "",
                    text: "Select"
                }),
                $('<option>', {
                    value: "0",
                    text: "All",
                    selected: true
                })
            );
            //$('#customer').trigger("change");
            // change customer list
            makeAjaxRequest(`get-data?type=customerList&locId=${branchId}`, setCustomerListByBranch);
            return;
        }

        // change stock point
        makeAjaxRequest(`get-data?type=stockPoint&locId=${branchId}`, setStockPointByBranch);
        // change customer list
        makeAjaxRequest(`get-data?type=customerList&locId=${branchId}`, setCustomerListByBranch);
    });

    // FUNCTION TO SET STOCK POINT VALUE BASED ON BRANCH
    function setStockPointByBranch(response) {
        let stockPoint = response.data;
       //console.log("json stockPoint >> ", stockPoint);
        $('#stockPoint').empty();
        // if stock point not found
        if(!stockPoint){
            $('#stockPoint').append(
                $('<option>', {
                    value: "",
                    text: "Select"
                })
            );
            $('#stockPoint').trigger("change");
            return;
        }
        // set stock point
        $('#stockPoint').append(
            $('<option>', {
                value: stockPoint.stock_point_id,
                text: stockPoint.loc_name
            })
        );
        $('#stockPoint').trigger("change"); // trigger change event
    }

    // FUNCTION TO SET CUSTOMER LIST BASED ON BRANCH
    function setCustomerListByBranch(response) {
        let customerList = response.data;
       //console.log("json customerList >> ", stockPoint);
        $('#customer').empty();
        // All customer option
        $('#customer').append(
            $('<option>', {
                value: "",
                text: "Select"
            }),
            $('<option>', {
                value: "0",
                text: "All",
                selected: true
            })
        );

        // if customer not found
        if(!customerList || customerList.length === 0){
            return;
        }

        // Populate customer list
        customerList.forEach(function (customer){
            //console.log(`-- ${customer.cust_id} : ${customer.cust_name}`);
            $('#customer').append(
                $('<option>', {
                    value: customer.cust_id,
                    text: customer.cust_name
                })
            );
        });
        $('#customer').trigger("change"); // trigger change event
    }

    // FUNCTION TO POPULATE DIVISION LIST
    function handleDivisionData(response) {
        let divisionList = response.data;
        //console.log("json divisionList >> ", divisionList);
        //$('#division').empty();
        // if customer not found
        if(!divisionList || divisionList.length === 0){
            $('#division').append(
                $('<option>', {
                    value: "0",
                    text: "All"
                })
            );
            return;
        }

        // Populate branch
        divisionList.forEach(function (division){
            //console.log(`-- ${division.para_id} : ${division.para_descr}`);
            $('#division').append(
                $('<option>', {
                    value: division.para_id,
                    text: division.para_descr
                })
            );
        });
    }

    // FUNCTION TO POPULATE FINANCIAL YEAR LIST
    function handleFinancialYearData(response) {
        let financialYearList = response.data;
        //console.log("json financialYearList >> ", financialYearList);
        $('#financialYear').empty();

        $('#financialYear').append(
            $('<option>', {
                value: "",
                text: "Select"
            })
        );

        // if financial year list not found
        if(!financialYearList || financialYearList.length === 0){
            return;
        }

        // Populate financial year list
        financialYearList.forEach(function (financialYear){
            //console.log(`-- ${financialYear.fin_year_id} : ${financialYear.start_dt} - ${financialYear.end_dt}`);
            $('#financialYear').append(
                $('<option>', {
                    value: financialYear.fin_year_id,
                    text: `${financialYear.start_dt} To ${financialYear.end_dt}`,
                    'data-start': financialYear.start_dt,
                    'data-end': financialYear.end_dt
                })
            );
        });

        // Set start date and end date values
        //$('#startDate').datepicker("setDate", financialYearList[0].start_dt);
        //$('#endDate').datepicker("setDate", financialYearList[0].end_dt);
        //setDateValues(financialYearList[0].start_dt, financialYearList[0].end_dt);
    }

    // FUNCTION TO SET START DATE & END DATE
    function handleFinYearDates(response) {
        let finYearDates = response.data;
       //console.log("json finYearDates >> ", finYearDates);
        // if financial year dates not found
        if(!finYearDates || finYearDates.length === 0){
            // Set values to empty
            $('#startDate').datepicker("setDate", "");
            $('#endDate').datepicker("setDate", "");
            //remove date restriction
            $('#startDate, #endDate').datepicker("option", {
                minDate: "",
                maxDate: ""
            });
            return;
        }
        // Set start date and end date values
        setDateValues(finYearDates.start_dt, finYearDates.end_dt);

    }

    // Function to parse dateStr (DD/MM/YYYY)
    function parseDateString(dateStr) {
        const parts = dateStr.split('/');
        return new Date(+parts[2], parts[1] - 1, +parts[0]); // year, month (0-based), day
    }
    // Function to set Start Date and End Date values
    function setDateValues(startDtStr, endDtStr) {
        const startDateObj = parseDateString(startDtStr);
        const endDateObj = parseDateString(endDtStr);

       //console.log("setDateValues >> start:", startDateObj, "end:", endDateObj);

        $('.datePicker').datepicker("option", {
            minDate: startDateObj,
            maxDate: endDateObj
        });

        $('#startDate').datepicker("setDate", startDateObj).trigger("change");
        $('#endDate').datepicker("setDate", endDateObj).trigger("change");

    }

    // TO CHANGE START DATE & END DATE VALUES BASED ON FINANCIAL YEAR
    $("#financialYear").on("change", function() {
        let finYearId = $(this).val();
       //console.log("onChange >> finYearId >> ", finYearId);
        if(!finYearId) {
            $('#startDate').datepicker("setDate", "");
            $('#endDate').datepicker("setDate", "");
            return;
        }
        // Set start date and end date values
        makeAjaxRequest(`get-data?type=finYearDates&finYearId=${finYearId}`, handleFinYearDates);
    });

//    makeAjaxRequest("get-data?type=branchList", handleLocationData);
//    makeAjaxRequest("get-data?type=stockPointList", handleStockPointData);
//    makeAjaxRequest("get-data?type=divisionList", handleDivisionData);
//    makeAjaxRequest("get-data?type=financialYearList", handleFinancialYearData);

    makeAjaxRequest("get-data?type=default", handleDefaultDataList);

    $('#reset-btn').on('click', function(){
       //console.log("reset button clicked..");

        $('#stockPoint').empty();
        $('#stockPoint').append(
            $('<option>', {
                value: "",
                text: "Select"
            })
        );

        $('#customer').empty();
        $('#customer').append(
            $('<option>', {
                value: "",
                text: "Select"
            })
        );

        $('#financialYear').empty();
        $('#financialYear').append(
            $('<option>', {
                value: "",
                text: "Select"
            })
        );

        $('#startDate').datepicker("setDate", "");
        $('#endDate').datepicker("setDate", "");
    });

    // VALIDATIONS
    // map to tract user input errors
    let errors = new Map([
        ["branch", true],
        ["division", true],
        ["stockPoint", true],
        ["reportType",true],
        ["customer",true],
        ["finYear", true],
        ["startDt", true],
        ["endDt", true],
    ]);

    // function to enable or disable submit button
    function toggleSubmitBtn(enableBtn){
        const btn = $('#submit-btn');
        if (enableBtn === true) {
            btn.removeAttr('disabled').removeClass('btn-disabled').addClass('btn-enabled');
        }
        else {
            btn.attr('disabled', 'disabled').removeClass('btn-enabled').addClass('btn-disabled');
        }
    }

    // function to check user input errors and if any occurred, disable Submit button
    function checkErrors(errors) {
       //console.log("checkErrors: ", errors);
        let isAllValid = true;

        for (let [key, value] of errors) {
            //console.log(key,"::", value);
            if(value === true) {	// If any error is true mark as invalid
                isAllValid = false;
                break;
            }
        }
       //console.log("isAllValid", isAllValid);
        // if isAllValid is false disable submit button
        toggleSubmitBtn(isAllValid);
    }


    // Validate branch
    $("#branchCheck").hide();
    $('#branch').on('change blur', function() {
        errors.set("branch",!validateBranch());
        checkErrors(errors);
    });

    // Validate stockPoint
    $("#stockPointCheck").hide();
    $('#stockPoint').on('change blur', function() {
        errors.set("stockPoint",!validateStockPoint());
        checkErrors(errors);
    });

    // Validate division
    $("#divisionCheck").hide();
    $('#division').on('change blur', function() {
        errors.set("division",!validateDivision());
        checkErrors(errors);
    });

    // Validate reportType
    $("#reportTypeCheck").hide();
    $('#reportType').on('change blur', function() {
        errors.set("reportType",!validateReportType());
        checkErrors(errors);
    });

    // Validate customer
    $("#customerCheck").hide();
    $('#customer').on('change blur', function() {
        errors.set("customer",!validateCustomer());
        checkErrors(errors);
    });

    // Validate financialYear
    $("#finYearCheck").hide();
    $('#financialYear').on('change blur', function() {
        errors.set("finYear",!validateFinancialYear());
        checkErrors(errors);
    });

    // Validate start date
    $("#startDtCheck").hide();
    $('#startDate').on('change blur', function() {
        errors.set("startDt",!validateStartDate());
        checkErrors(errors);
    });

    // Validate end date
    $("#endDtCheck").hide();
    $('#endDate').on('change blur', function() {
        errors.set("endDt",!validateEndDate());
        checkErrors(errors);
    });


    // function to validate fields
    function validateBranch() {
       //console.log("validateBranch is called..");
        let branch = $("#branch").val();
       //console.log("-- branch >> ", branch);

        let regex = /^[0-9]+$/;

        if (!branch) {
            $("#branchCheck").show();
            $("#branchCheck").html("Branch is required");
            return false;
        } else if (!regex.test(branch)) {
                $("#branchCheck").show();
                $("#branchCheck").html("Invalid Branch");
                //$("#branch").html("");
                return false;
        }

        $("#branchCheck").hide();
        return true;
    }

    function validateStockPoint() {
       //console.log("validateStockPoint is called..");
        let stockPoint = $("#stockPoint").val();
       //console.log("-- stockPoint >> ", stockPoint);

        let regex = /^[0-9]+$/;
        if (!stockPoint) {
            $("#stockPointCheck").show().html("Stock Point is required");
            return false;
        } else if (!regex.test(stockPoint)) {
             $("#stockPointCheck").show();
             $("#stockPointCheck").html("Invalid stock point");
             //$("#branch").html("");
             return false;
         }

        $("#stockPointCheck").hide();
        return true;
    }

    function validateDivision() {
       //console.log("validateDivision is called..");
        let division = $("#division").val();
       //console.log("-- division >> ", division);

        let regex = /^[0-9]+$/;
        if (!division) {
            $("#divisionCheck").show().html("Division is required");
            return false;
        } else if (!regex.test(division)) {
            $("#divisionCheck").show();
            $("#divisionCheck").html("Invalid division");
            //$("#branch").html("");
            return false;
        }

        $("#divisionCheck").hide();
        return true;
    }

    function validateReportType() {
       //console.log("validateReportType is called..");
        let reportType = $("#reportType").val();
       //console.log("-- reportType >> ", reportType);

        if (!reportType) {
            $("#reportTypeCheck").show().html("Report Type is required");
            return false;
        }

        $("#reportTypeCheck").hide();
        return true;
    }

    function validateCustomer() {
       //console.log("validateCustomer is called..");
        let customer = $("#customer").val();
       //console.log("-- customer >> ", customer);

        if (!customer) {
            $("#customerCheck").show().html("Customer is required");
            return false;
        }

        $("#customerCheck").hide();
        return true;
    }

    function validateFinancialYear() {
       //console.log("validateFinancialYear is called..");
        let finYear = $("#financialYear").val();
       //console.log("-- finYear >> ", finYear);

        if (!finYear) {
            $("#finYearCheck").show().html("Financial Year is required");
            return false;
        }

        $("#finYearCheck").hide();
        return true;
    }

    function validateStartDate() {
       //console.log("validateStartDate is called..");

        let finYearId = $("#financialYear").val();
        if(!finYearId) {
            validateFinancialYear();
            $("#startDate").html("");
            return false;
        }

        let startDate = $("#startDate").val();
       //console.log("startDate :: ", startDate);

        let regex = /^(0[1-9]|[12][0-9]|3[01])\/(0[1-9]|1[0-2])\/([0-9]{4})$/;

        if (!startDate) {
            $("#startDtCheck").show().html("Start Date is required");
            return false;
        }  else if (!regex.test(startDate)) {
            $("#startDtCheck").show();
            $("#startDtCheck").html("Invalid start date format");
//            $("#startDate").datepicker("setDate", null);
            return false;
        }

        let startDDt = parseDateString(startDate);

        let endDate = $("#endDate").val();
       //console.log("endDate :: ", endDate);
        if(endDate){
            let endDDt = parseDateString(endDate);
            if(startDDt > endDDt) {
                $("#startDtCheck").show();
                $("#startDtCheck").html("Start date can not be greater than end date");
                return false;
            }
        }

        let selectedOption = $("#financialYear").find(":selected");
        let finStart = selectedOption.data("start"); // e.g., "01/04/2024"
        let finEnd = selectedOption.data("end");     // e.g., "31/03/2025"
       //console.log("finStart >> ",finStart, " :: finEnd >> ",finEnd);


        let finStartDate = parseDateString(finStart);
        let finEndDate = parseDateString(finEnd);

        if (startDDt < finStartDate || startDDt > finEndDate) {
            $("#startDtCheck").show().html("Start date is outside selected financial year.");
            return false;
        }

        $("#startDtCheck").hide();
        return true;
    }

    function validateEndDate() {
       //console.log("validateEndDate is called..");

        let finYearId = $("#financialYear").val();
        if(!finYearId) {
            validateFinancialYear();
            $("#endDate").datepicker("setDate", null);
            return false;
        }

        let endDate = $("#endDate").val();
       //console.log(endDate);

        let regex = /^(0[1-9]|[12][0-9]|3[01])\/(0[1-9]|1[0-2])\/([0-9]{4})$/;

        if (!endDate) {
            $("#endDtCheck").show().html("End Date is required");
            return false;
        }  else if (!regex.test(endDate)) {
             $("#endDtCheck").show();
             $("#endDtCheck").html("Invalid End date format");
             //$("#startDate").datepicker("setDate", null);
             return false;
        }

        let endDDt = parseDateString(endDate);

        let startDate = $("#startDate").val();
       //console.log("startDate :: ", endDate);
        if(startDate){
            let startDDt = parseDateString(startDate);
            if(startDDt > endDDt) {
                $("#endDtCheck").show();
                $("#endDtCheck").html("End date can not be less than start date");
                return false;
            }
        }

        let selectedOption = $("#financialYear").find(":selected");
        let finStart = selectedOption.data("start"); // e.g., "01/04/2024"
        let finEnd = selectedOption.data("end");     // e.g., "31/03/2025"
       //console.log("validateEndDate >> finStart >> ",finStart, " :: finEnd >> ",finEnd);

        let finStartDate = parseDateString(finStart);
        let finEndDate = parseDateString(finEnd);

        if (endDDt < finStartDate || endDDt > finEndDate) {
            $("#endDtCheck").show().html("End date is outside selected financial year.");
            return false;
        }

        $("#endDtCheck").hide();
        return true;
    }

});

