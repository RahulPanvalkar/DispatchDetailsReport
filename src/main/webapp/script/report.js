$(document).ready(function () {

    // GENERIC FUNCTION TO SEND AJAX REQUEST
    function makeAjaxRequest(URL, callback) {
        console.log("sending ajax request to >> [",URL,"]");
        // Show loader
        $("#loader").show();
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

    // get register data
    makeAjaxRequest("dispatch-register-data", handleDispatchReportData);

    // function to handle the ajax response and populate table with data
    function handleDispatchReportData(response) {
        console.log("handleDispatchReportData >> response >> ", response);

        // Ensure data is present
        if (!response.data || response.data.length === 0) {
            $("#fixed-body-table tbody").html("<tr><td colspan='3'>No data available</td></tr>");
            $("#scroll-body-table tbody").html("<tr><td colspan='16'>No data available</td></tr>");
            return;
        }

        let fixedRow = "", scrollRow = "";

        response.data.forEach(dispData => {
            fixedRow += `
                <tr class='inner-tbl-tr'>
                    <td>${dispData.dspTrnNo}</td>
                    <td>${dispData.dspDtStr || ''}</td>
                    <td>${dispData.custName}</td>
                </tr>
            `;

            scrollRow += `
                <tr class='inner-tbl-tr'>
                    <td>${dispData.destination}</td>
                    <td>${dispData.transporter}</td>
                    <td>${dispData.goodsValue}</td>
                    <td>${dispData.invNo}</td>
                    <td>${dispData.lrNum}</td>
                    <td>${dispData.driverName}</td>
                    <td>${dispData.lorryNo}</td>
                    <td>${dispData.lrDateStr || ''}</td>
                    <td>${dispData.delayDays}</td>
                    <td>${dispData.noOfCases}</td>
                    <td>${dispData.formNum}</td>
                    <td>${dispData.cFormDate || ''}</td>
                    <td>${dispData.cFormValue}</td>
                    <td>${dispData.podDateStr || ''}</td>
                    <td>${dispData.podNum}</td>
                    <td>${dispData.podReason}</td>
                </tr>
            `;
        });

        $("#fixed-body-table tbody").html(fixedRow);
        $("#scroll-body-table tbody").html(scrollRow);
        // Hide loader
        $("#loader").hide();
    }

    // To scroll the header names horizontally or values vertically  
    $('.scroll-body').on('scroll', function () {
        $('.scroll-header').scrollLeft($(this).scrollLeft());
        $('.fixed-body').scrollTop($(this).scrollTop());
    });
    
    
    /*$('.fixed-body').on('scroll', function () {
        $('.scroll-body').scrollTop($(this).scrollTop());
    });*/

    // Function to Sync header and body cell width
    function syncTableColumnWidths(headerSelector, bodySelector) {
        console.log(`Syncing widths for ${headerSelector} and ${bodySelector}...`);

        let $headerCols = $(`${headerSelector} th`);
        let $rows = $(`${bodySelector} tr`);

        $headerCols.each(function(index) {
            let headerCol = $(this);
            let maxWidth = 0;

            // Measure header cell
            const $tempHeader = $('<span>').text(headerCol.text()).css({
                visibility: 'hidden',
                whiteSpace: 'nowrap',
                position: 'absolute'
            }).appendTo('body');

            maxWidth = Math.max(maxWidth, $tempHeader.outerWidth());

            $tempHeader.remove();

            // Measure each cell in this column
            $rows.each(function() {
                let bodyCol = $(this).find('td').eq(index);
                if (bodyCol.length) {
                    const $tempBody = $('<span>').text(bodyCol.text()).css({
                        visibility: 'hidden',
                        whiteSpace: 'nowrap',
                        position: 'absolute'
                    }).appendTo('body');

                    maxWidth = Math.max(maxWidth, $tempBody.outerWidth());

                    $tempBody.remove();
                }
            });

            maxWidth += 16; // Add padding

            // List of columns to right-align
            const rightAlignHeaders = [
                "GOODSVALUE", "LRNO", "LORRYNUMBER", "PODNUMBER", "DISPATCHDELAY", "NOOFCASES"
            ];

            // Apply width to header and all body cells in this column
            console.log(headerCol.text());

            headerCol.css('min-width', maxWidth + 'px');
            $rows.each(function () {
                const bodyCol = $(this).find('td').eq(index);
                bodyCol.css('min-width', maxWidth + 'px');
                if (rightAlignHeaders.includes(headerCol.text())) {
                    bodyCol.addClass("text-right");
                }
            });
        });
    }

    // to sync the header and data cell width in table
    $(document).ready(function () {
        syncTableColumnWidths('#fixed-header-table', '#fixed-body-table');
        syncTableColumnWidths('#scroll-header-table', '#scroll-body-table');
    });

    // on window resize sync width
    $(window).on('resize', function () {
        syncTableColumnWidths('#fixed-header-table', '#fixed-body-table');
        syncTableColumnWidths('#scroll-header-table', '#scroll-body-table');
    });

});

// Handle button click event
/*$('#download').click(function() {
    makeAjaxRequest("dispatch-register-data", handleDispatchReportData);
});*/

/*

    /*function populateDispatchReportData() {
            console.log("populateDispatchReportData >> rows >> ", rows);

            // Ensure data is present
            if (!rows || rows.length === 0) {
                $("#fixed-body-table tbody").html("<tr><td colspan='3'>No data available</td></tr>");
                $("#scroll-body-table tbody").html("<tr><td colspan='16'>No data available</td></tr>");
                return;
            }

            let fixedRow = "", scrollRow = "";

            rows.forEach(row => {
                fixedRow += `
                    <tr class='inner-tbl-tr'>
                        <td>${dispData.dspTrnNo}</td>
                        <td>${dispData.dspDtStr || ''}</td>
                        <td>${dispData.custName}</td>
                    </tr>
                `;

                scrollRow += `
                    <tr class='inner-tbl-tr'>
                        <td>${dispData.destination}</td>
                        <td>${dispData.transporter}</td>
                        <td>${dispData.goodsValue}</td>
                        <td>${dispData.invNo}</td>
                        <td>${dispData.lrNum}</td>
                        <td>${dispData.driverName}</td>
                        <td>${dispData.lorryNo}</td>
                        <td>${dispData.lrDateStr || ''}</td>
                        <td>${dispData.delayDays}</td>
                        <td>${dispData.noOfCases}</td>
                        <td>${dispData.formNum}</td>
                        <td>${dispData.cFormDate || ''}</td>
                        <td>${dispData.cFormValue}</td>
                        <td>${dispData.podDateStr || ''}</td>
                        <td>${dispData.podNum}</td>
                        <td>${dispData.podReason}</td>
                    </tr>
                `;
            });

            $("#fixed-body-table tbody").html(fixedRow);
            $("#scroll-body-table tbody").html(scrollRow);
        }*/

        //populateDispatchReportData();
/*
const rows = [
   {
       dspTrnNo: "000000000001",
       dspDtStr: "04/04/2023",
       custName: "RUBY PHARMA",
       destination: "SOLAPUR",
       transporter: "BTI",
       goodsValue: "12934.00",
       invNo: "BWMNI2300001",
       lrNum: "675433",
       driverName: "000000000001",
       lorryNumber: "000000000001",
       lrDateStr: "000000000001",
       dispatchDelay: "PADHARPUR",
       noOfCases: "PADHARPUR",
       formNum: "000000000001",
       cFormDate: "SOLAPUR",
       cFormValue: "SOLAPUR",
       podDate: "12934.00",
       podNumber: "12934.00",
       reason: "12934.00"
   },
   {
       dspTrnNo: "000000000002",
       dspDtStr: "04/04/2023",
       custName: "VORA MEDICALS",
       destination: "PADHARPUR",
       transporter: "BATCO ROADLINES LLP",
       goodsValue: "5335.50",
       invNo: "BWMNI2300002",
       lrNum: "675432",
       driverName: "PADHARPUR",
       lorryNumber: "PADHARPUR",
       lrDateStr: "PADHARPUR",
       dispatchDelay: "PADHARPUR",
       noOfCases: "12934.00",
       formNum: "12934.00",
       cFormDate: "",
       cFormValue: "",
       podDate: "12934.00",
       podNumber: "",
       reason: ""
   },
   {
      dspTrnNo: "000000000001",
      dspDtStr: "04/04/2023",
      custName: "RUBY PHARMA",
      destination: "SOLAPUR",
      transporter: "BTI",
      goodsValue: "12934.00",
      invNo: "BWMNI2300001",
      lrNum: "675433",
      driverName: "",
      lorryNumber: "PADHARPUR",
      lrDateStr: "",
      dispatchDelay: "",
      noOfCases: "PADHARPUR",
      formNum: "12934.00",
      cFormDate: "12934.00",
      cFormValue: "PADHARPUR",
      podDate: "",
      podNumber: "BWMNI2300001",
      reason: "12934.00"
  },
  {
      dspTrnNo: "000000000002",
      dspDtStr: "04/04/2023",
      custName: "VORA MEDICALS",
      destination: "PADHARPUR",
      transporter: "BATCO ROADLINES LLP",
      goodsValue: "5335.50",
      invNo: "BWMNI2300002",
      lrNum: "675432",
      driverName: "12934.00",
      lorryNumber: "BWMNI2300001",
      lrDateStr: "",
      dispatchDelay: "12934.00",
      noOfCases: "",
      formNum: "12934.00",
      cFormDate: "BWMNI2300001",
      cFormValue: "",
      podDate: "",
      podNumber: "12934.00",
      reason: ""
  },
  {
     dspTrnNo: "000000000001",
     dspDtStr: "04/04/2023",
     custName: "RUBY PHARMA",
     destination: "SOLAPUR",
     transporter: "BTI",
     goodsValue: "12934.00",
     invNo: "BWMNI2300001",
     lrNum: "675433",
     driverName: "000000000001",
     lorryNumber: "000000000001",
     lrDateStr: "000000000001",
     dispatchDelay: "PADHARPUR",
     noOfCases: "PADHARPUR",
     formNum: "000000000001",
     cFormDate: "SOLAPUR",
     cFormValue: "SOLAPUR",
     podDate: "12934.00",
     podNumber: "12934.00",
     reason: "12934.00"
     },
     {
         dspTrnNo: "000000000002",
         dspDtStr: "04/04/2023",
         custName: "VORA MEDICALS",
         destination: "PADHARPUR",
         transporter: "BATCO ROADLINES LLP",
         goodsValue: "5335.50",
         invNo: "BWMNI2300002",
         lrNum: "675432",
         driverName: "PADHARPUR",
         lorryNumber: "PADHARPUR",
         lrDateStr: "PADHARPUR",
         dispatchDelay: "PADHARPUR",
         noOfCases: "12934.00",
         formNum: "12934.00",
         cFormDate: "",
         cFormValue: "",
         podDate: "12934.00",
         podNumber: "",
         reason: ""
     },
     {
        dspTrnNo: "000000000001",
        dspDtStr: "04/04/2023",
        custName: "RUBY PHARMA",
        destination: "SOLAPUR",
        transporter: "BTI",
        goodsValue: "12934.00",
        invNo: "BWMNI2300001",
        lrNum: "675433",
        driverName: "",
        lorryNumber: "PADHARPUR",
        lrDateStr: "",
        dispatchDelay: "",
        noOfCases: "PADHARPUR",
        formNum: "12934.00",
        cFormDate: "12934.00",
        cFormValue: "PADHARPUR",
        podDate: "",
        podNumber: "BWMNI2300001",
        reason: "12934.00"
    },
    {
        dspTrnNo: "000000000002",
        dspDtStr: "04/04/2023",
        custName: "VORA MEDICALS",
        destination: "PADHARPUR",
        transporter: "BATCO ROADLINES LLP",
        goodsValue: "5335.50",
        invNo: "BWMNI2300002",
        lrNum: "675432",
        driverName: "12934.00",
        lorryNumber: "BWMNI2300001",
        lrDateStr: "",
        dispatchDelay: "12934.00",
        noOfCases: "",
        formNum: "12934.00",
        cFormDate: "BWMNI2300001",
        cFormValue: "",
        podDate: "",
        podNumber: "12934.00",
        reason: ""
    },
        {
           dspTrnNo: "000000000001",
           dspDtStr: "04/04/2023",
           custName: "RUBY PHARMA",
           destination: "SOLAPUR",
           transporter: "BTI",
           goodsValue: "12934.00",
           invNo: "BWMNI2300001",
           lrNum: "675433",
           driverName: "000000000001",
           lorryNumber: "000000000001",
           lrDateStr: "000000000001",
           dispatchDelay: "PADHARPUR",
           noOfCases: "PADHARPUR",
           formNum: "000000000001",
           cFormDate: "SOLAPUR",
           cFormValue: "SOLAPUR",
           podDate: "12934.00",
           podNumber: "12934.00",
           reason: "12934.00"
       },
       {
           dspTrnNo: "000000000002",
           dspDtStr: "04/04/2023",
           custName: "VORA MEDICALS VORA MEDICALS",
           destination: "PADHARPUR",
           transporter: "BATCO ROADLINES LLP",
           goodsValue: "5335.50",
           invNo: "BWMNI2300002",
           lrNum: "675432",
           driverName: "PADHARPUR",
           lorryNumber: "PADHARPUR",
           lrDateStr: "PADHARPUR",
           dispatchDelay: "PADHARPUR",
           noOfCases: "12934.00",
           formNum: "12934.00",
           cFormDate: "",
           cFormValue: "",
           podDate: "12934.00",
           podNumber: "",
           reason: ""
       },
       {
          dspTrnNo: "000000000001",
          dspDtStr: "04/04/2023",
          custName: "RUBY PHARMA",
          destination: "SOLAPUR",
          transporter: "BTI",
          goodsValue: "12934.00",
          invNo: "BWMNI2300001",
          lrNum: "675433",
          driverName: "",
          lorryNumber: "PADHARPUR",
          lrDateStr: "",
          dispatchDelay: "",
          noOfCases: "PADHARPUR",
          formNum: "12934.00",
          cFormDate: "12934.00",
          cFormValue: "PADHARPUR",
          podDate: "",
          podNumber: "BWMNI2300001",
          reason: "12934.00"
      },
      {
          dspTrnNo: "000000000002",
          dspDtStr: "04/04/2023",
          custName: "VORA MEDICALS",
          destination: "PADHARPUR",
          transporter: "BATCO ROADLINES LLP",
          goodsValue: "5335.50",
          invNo: "BWMNI2300002",
          lrNum: "675432",
          driverName: "12934.00",
          lorryNumber: "BWMNI2300001",
          lrDateStr: "",
          dispatchDelay: "12934.00",
          noOfCases: "",
          formNum: "12934.00",
          cFormDate: "BWMNI2300001",
          cFormValue: "",
          podDate: "",
          podNumber: "12934.00",
          reason: ""
      }
   ];*/