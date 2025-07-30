$(document).ready(function () {
    //$("#loader").show();

    // GENERIC FUNCTION TO SEND AJAX REQUEST
    function makeAjaxRequest(URL, callback) {
        //console.log("sending ajax request to >> [",URL,"]");
        // Show loader
        $(".main-div").hide();
        $("#loader").show();
        $.ajax({
            url: URL,
            type: 'GET',
            dataType: 'json',
            success: function(response) {
                ////console.log("makeAjaxRequest >> response >> ", response);
                callback(response);
            },
            error: function(jqXhr, textStatus, errorMessage) {
                console.error("Error occurred: ", errorMessage);
                callback([]);
            }
        });
    }

    // get register data
    makeAjaxRequest("dispatch-register-data", handleDispatchReportData);

    // function to handle the ajax response and populate table with data
    function handleDispatchReportData(response) {
        ////console.log("handleDispatchReportData >> response >> ", response);

        if(response.success === false) {
            alert(response.message);
            window.location.href='dispatch-register';
            return;
        }

        //console.log(response.fin_year_range, response.total_goods_value, response.div_desc, response.loc_name, response.report_type, response.date_time);
        $('#compName').html(response.comp_name);
        $('#location').html(response.loc_name);
        $('#reportHeader').html(`<strong>Dispatch Register ${response.report_type} Report From:</strong> ${response.report_date}`);
        $('.financial-year').html(`Financial Year From ${response.fin_year_range}`);

        if(response.date_time) {
            var dateTimes = response.date_time.split('T');
            $('.date-time').html(`Report Date: ${dateTimes[0]} Time: ${dateTimes[1]}`);
        }

        $('#divLabel').html(`Division : ${response.div_desc}`);

        // Ensure data is present
        if (!response.data || response.data.length === 0) {
            $("#fixed-body-table tbody").html("<tr><td colspan='3'>No data available</td></tr>");
            $("#scroll-body-table tbody").html("<tr><td colspan='16'>No data available</td></tr>");
            return;
        }

        $("#fixed-body-table tbody").empty();
        $("#scroll-body-table tbody").empty();
        if(response.div_desc === "ALL") {
            if(response.data.MAIN && response.data.MAIN.length > 0) {
                createDataRows(response.data.MAIN, "MAIN", response.main_div_total);
            }
            if(response.data.ZENKARE && response.data.ZENKARE.length > 0){
                createDataRows(response.data.ZENKARE, "ZENKARE", response.zen_div_total);
            }
        } else {
            createDataRows(response.data, `${response.div_desc}`, response.grand_total);
        }

        // to create grand total row
        createGrandTotalRow(response.grand_total);

        // Hide loader
        $(".main-div").show();
        $("#loader").hide();
    }


    // Function to create data rows
    function createDataRows(data, divisionName, divisionTotal) {
        //console.log("creating data row >> division :: ", divisionName);
        let fixedRow = "", scrollRow = "";
        // to create division row
        fixedRow += `
            <tr class='division-row'>
                <td class="division-td" colspan="3">
                    <div class="division-label" id="divLabel">Division : ${divisionName}</div>
                </td>
            </tr>
        `;

        scrollRow += `
            <tr class='division-row'>
                <td class="division-td" colspan="18">
                    <div class="division-label"><span>&nbsp;</span></div>
                </td>
            </tr>
        `;

        data.forEach(dispData => {
            fixedRow += `
                <tr class='inner-tbl-tr'>
                    <td class="tran">${dispData.dspTrnNo}</td>
                    <td class="disp-date">${dispData.dspDtStr || ''}</td>
                    <td class="party">${dispData.custName}</td>
                </tr>
            `;

            if(!dispData.goodsValue) {
                dispData.goodsValue = "";
            }

            scrollRow += `
                <tr class='inner-tbl-tr'>
                    <td class="dest">${dispData.destination}</td>
                    <td class="transp">${dispData.transporter}</td>
                    <td class="goods-val text-right">${dispData.goodsValue}</td>
                    <td class="inv-no">${dispData.invNo}</td>
                    <td class="division">${dispData.division}</td>
                    <td class="lr-no">${dispData.lrNum}</td>
                    <td class="driv-no">${dispData.driverName}</td>
                    <td class="lorry-no">${dispData.lorryNo}</td>
                    <td class="lr-date">${dispData.lrDateStr || ''}</td>
                    <td class="disp-delay text-right">${dispData.delayDays}</td>
                    <td class="cases text-right">${dispData.noOfCases}</td>
                    <td class="form-no">${dispData.formNum}</td>
                    <td class="form-date">${dispData.cFormDate || ''}</td>
                    <td class="form-val text-right">${dispData.cFormValue}</td>
                    <td class="pod-date">${dispData.podDateStr || ''}</td>
                    <td class="pod-num text-right">${dispData.podNum}</td>
                    <td class="reason">${dispData.podReason}</td>
                </tr>
            `;
        });

        // to create division total row
        fixedRow += `
            <tr class='division-row'>
                <td class="division-td" colspan="3">
                    <div class="division-label" id="divLabel">Division Total : </div>
                </td>
            </tr>
        `;

        scrollRow += `
            <tr class='division-row'>
                <td class="division-td" colspan="2">
                    <div class="division-labe"><span>&nbsp;</span></div>
                </td>
                <td class="division-td" colspan="1">
                    <div class="division-label text-right">${divisionTotal}</div>
                </td>
                <td class="division-td" colspan="15">
                    <div class="division-label"><span>&nbsp;</span></div>
                </td>
            </tr>
        `;

        $("#fixed-body-table tbody").append(fixedRow);
        $("#scroll-body-table tbody").append(scrollRow);
    }

    function createGrandTotalRow(grandTotal) {
        //console.log("createGrandTotalRow >> total :: ", grandTotal);

        let fixedRow = "", scrollRow = "";
        fixedRow += `
            <tr class='division-row'>
                <td class="division-td" colspan="3">
                    <div class="division-label" id="divLabel">Grand Total : </div>
                </td>
            </tr>
        `;

        scrollRow += `
            <tr class='division-row'>
                <td class="division-td" colspan="2">
                    <div class="division-label"><span>&nbsp;</span></div>
                </td>
                <td class="division-td" colspan="1">
                    <div class="division-label text-right">${grandTotal}</div>
                </td>
                <td class="division-td" colspan="15">
                    <div class="division-label"><span>&nbsp;</span></div>
                </td>
            </tr>
        `;

        $("#fixed-body-table tbody").append(fixedRow);
        $("#scroll-body-table tbody").append(scrollRow);
    }

    // To scroll the header names horizontally or values vertically  
    $('.scroll-body').on('scroll', function () {
        $('.scroll-header').scrollLeft($(this).scrollLeft());
        $('.fixed-body').scrollTop($(this).scrollTop());
    });
    
});

