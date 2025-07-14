$(document).ready(function() {
    console.log("page loaded >> jquery working..");

    // Initialize Datepicker for DATE fields and set min max value
    $(".datePicker").datepicker({
        changeMonth: true,
        changeYear: true,
        //yearRange: "-100:-18", // to select from 100 to 18 years ago
        dateFormat: "dd/mm/yy",
        //defaultDate: "-18y",   // default position to 18 years ago
        //maxDate: "-18Y",       // Max date is 18 years ago
        //minDate: "-100Y"       // Min date is 100 years ago
    });

    // GENERIC FUNCTION TO SEND AJAX REQUEST
    function makeAjaxRequest(URL, callback) {
        console.log("sending ajax request to >> [",URL,"]");
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

    // TO CHANGE STOCK POINT VALUE BASED ON BRANCH
    $("#branch").on("change", function() {
        let branchId = $(this).val();
        console.log("branchId >> ", branchId);
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
                    value: "0",
                    text: "All"
                })
            );
            makeAjaxRequest("get-data?type=stockPointList", handleStockPointData);
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
        console.log("json stockPoint >> ", stockPoint);
        $('#stockPoint').empty();
        // if stock point not found
        if(!stockPoint){
            $('#stockPoint').append(
                $('<option>', {
                    value: "",
                    text: "Select"
                })
            );
            return;
        }
        // set stock point
        $('#stockPoint').append(
            $('<option>', {
                value: stockPoint.stock_point_id,
                text: stockPoint.loc_name
            })
        );
    }

    // FUNCTION TO SET CUSTOMER LIST BASED ON BRANCH
    function setCustomerListByBranch(response) {
        let customerList = response.data;
        console.log("json customerList >> ", stockPoint);
        $('#customer').empty();
        // All customer option
        $('#customer').append(
            $('<option>', {
                value: "0",
                text: "All"
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
                    value: "",
                    text: "Select"
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
        // if financial year list not found
        if(!financialYearList || financialYearList.length === 0){
            $('#financialYear').append(
                $('<option>', {
                    value: "",
                    text: "Select"
                })
            );
            return;
        }

        // Populate financial year list
        financialYearList.forEach(function (financialYear){
            //console.log(`-- ${financialYear.fin_year_id} : ${financialYear.start_dt} - ${financialYear.end_dt}`);
            $('#financialYear').append(
                $('<option>', {
                    value: financialYear.fin_year_id,
                    text: `${financialYear.start_dt} To ${financialYear.end_dt}`
                })
            );
        });

        // Set start date and end date values
        //$('#startDate').datepicker("setDate", financialYearList[0].start_dt);
        //$('#endDate').datepicker("setDate", financialYearList[0].end_dt);
        setDateValues(financialYearList[0].start_dt, financialYearList[0].end_dt);
    }

    // FUNCTION TO SET START DATE & END DATE
    function handleFinYearDates(response) {
        let finYearDates = response.data;
        console.log("json finYearDates >> ", finYearDates);
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

        console.log("setDateValues >> start:", startDateObj, "end:", endDateObj);

        $('.datePicker').datepicker("option", {
            minDate: startDateObj,
            maxDate: endDateObj
        });

        $('#startDate').datepicker("setDate", startDateObj);
        $('#endDate').datepicker("setDate", endDateObj);

    }

    // TO CHANGE START DATE & END DATE VALUES BASED ON FINANCIAL YEAR
    $("#financialYear").on("change", function() {
        let finYearId = $(this).val();
        console.log("finYearId >> ", finYearId);
        if(!finYearId) {
            $('#startDate').datepicker("setDate", "");
            $('#endDate').datepicker("setDate", "");
            return;
        }
        // Set start date and end date values
        makeAjaxRequest(`get-data?type=finYearDates&finYearId=${finYearId}`, handleFinYearDates);
    });

    makeAjaxRequest("get-data?type=branchList", handleLocationData);
    makeAjaxRequest("get-data?type=stockPointList", handleStockPointData);
    makeAjaxRequest("get-data?type=divisionList", handleDivisionData);
    makeAjaxRequest("get-data?type=financialYearList", handleFinancialYearData);

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
        console.log("checkErrors: ", errors);
        let isAllValid = true;

        for (let [key, value] of errors) {
            //console.log(key,"::", value);
            if(value === true) {	// If any error is true mark as invalid
                isAllValid = false;
                break;
            }
        }
        console.log("isAllValid", isAllValid);
        // if isAllValid is false disable submit button
        //toggleSubmitBtn(isAllValid);
    }

    checkErrors(errors);

    $('#submit-btn').on('click', function(event) {
        //event.preventDefault();
        console.log("submit button clicked::", event.target.id);
    });


    // function to validate fields
    function validateField(event) {
        console.log("validateCaptcha is called..");
        let captchaValue = $("#captchaTxt").val();
        console.log(captchaValue);

        let regex = /^[A-Za-z0-9]+$/;

        if (!captchaValue) {
            $("#captchaCheck").show();
            $("#captchaCheck").html("CAPTCHA is missing");
            return false;
        } else if (!regex.test(captchaValue) || captchaValue.length !== 5) {
                $("#captchaCheck").show();
                $("#captchaCheck").html("Invalid CAPTCHA");
                $("#captchaTxt").html("");
                return false;
        }

        $("#captchaCheck").hide();
        return true;
    }
});

