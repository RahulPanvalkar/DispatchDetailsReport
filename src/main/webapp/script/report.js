


   $(document).ready(function () {

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

        // get register data
        makeAjaxRequest("dispatch-register-data", handleDispatchReportData);

        // function to handle the ajax response and populate table with data
        function handleDispatchReportData(response) {
            console.log("handleDispatchReportData >> response >> ", response);

            // Ensure data is present
            if (!response.data || response.data.length === 0) {
                $("#value-table-1 tbody").html("<tr><td colspan='3'>No data available</td></tr>");
                $("#value-table-2 tbody").html("<tr><td colspan='16'>No data available</td></tr>");
                return;
            }

            let fixedRow = "", scrollRow = "";

            response.data.forEach(row => {
                fixedRow += `
                    <tr class='inner-tbl-tr'>
                        <td>${row.dspTrnNo}</td>
                        <td>${row.dspDtStr || ''}</td>
                        <td>${row.custName}</td>
                    </tr>
                `;

                scrollRow += `
                    <tr class='inner-tbl-tr'>
                        <td>${row.destination}</td>
                        <td>${row.transporter}</td>
                        <td>${row.goodsValue}</td>
                        <td>${row.invNo}</td>
                        <td>${row.lrNum}</td>
                        <td>${row.driverName}</td>
                        <td>${row.lorryNo}</td>
                        <td>${row.lrDateStr || ''}</td>
                        <td>${row.delayDays}</td>
                        <td>${row.noOfCases}</td>
                        <td>${row.formNum}</td>
                        <td>${row.cFormDate || ''}</td>
                        <td>${row.cFormValue}</td>
                        <td>${row.podDateStr || ''}</td>
                        <td>${row.podNum}</td>
                        <td>${row.podReason}</td>
                    </tr>
                `;
            });

            $("#value-table-1 tbody").html(fixedRow);
            $("#value-table-2 tbody").html(scrollRow);
        }


});

// Handle button click event
/*$('#download').click(function() {
    makeAjaxRequest("dispatch-register-data", handleDispatchReportData);
});*/

/*const rows = [
       {
           transactionNo: "000000000001",
           dispatchDate: "04/04/2023",
           party: "RUBY PHARMA",
           destination: "SOLAPUR",
           transporter: "BTI",
           goodsValue: "12934.00",
           invoiceNo: "BWMNI2300001",
           lrNo: "675433",
           driverName: "000000000001",
           lorryNumber: "000000000001",
           lrDate: "000000000001",
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
           transactionNo: "000000000002",
           dispatchDate: "04/04/2023",
           party: "VORA MEDICALS",
           destination: "PADHARPUR",
           transporter: "BATCO ROADLINES LLP",
           goodsValue: "5335.50",
           invoiceNo: "BWMNI2300002",
           lrNo: "675432",
           driverName: "PADHARPUR",
           lorryNumber: "PADHARPUR",
           lrDate: "PADHARPUR",
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
          transactionNo: "000000000001",
          dispatchDate: "04/04/2023",
          party: "RUBY PHARMA",
          destination: "SOLAPUR",
          transporter: "BTI",
          goodsValue: "12934.00",
          invoiceNo: "BWMNI2300001",
          lrNo: "675433",
          driverName: "",
          lorryNumber: "PADHARPUR",
          lrDate: "",
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
          transactionNo: "000000000002",
          dispatchDate: "04/04/2023",
          party: "VORA MEDICALS",
          destination: "PADHARPUR",
          transporter: "BATCO ROADLINES LLP",
          goodsValue: "5335.50",
          invoiceNo: "BWMNI2300002",
          lrNo: "675432",
          driverName: "12934.00",
          lorryNumber: "BWMNI2300001",
          lrDate: "",
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
             transactionNo: "000000000001",
             dispatchDate: "04/04/2023",
             party: "RUBY PHARMA",
             destination: "SOLAPUR",
             transporter: "BTI",
             goodsValue: "12934.00",
             invoiceNo: "BWMNI2300001",
             lrNo: "675433",
             driverName: "000000000001",
             lorryNumber: "000000000001",
             lrDate: "000000000001",
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
                 transactionNo: "000000000002",
                 dispatchDate: "04/04/2023",
                 party: "VORA MEDICALS",
                 destination: "PADHARPUR",
                 transporter: "BATCO ROADLINES LLP",
                 goodsValue: "5335.50",
                 invoiceNo: "BWMNI2300002",
                 lrNo: "675432",
                 driverName: "PADHARPUR",
                 lorryNumber: "PADHARPUR",
                 lrDate: "PADHARPUR",
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
                transactionNo: "000000000001",
                dispatchDate: "04/04/2023",
                party: "RUBY PHARMA",
                destination: "SOLAPUR",
                transporter: "BTI",
                goodsValue: "12934.00",
                invoiceNo: "BWMNI2300001",
                lrNo: "675433",
                driverName: "",
                lorryNumber: "PADHARPUR",
                lrDate: "",
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
                transactionNo: "000000000002",
                dispatchDate: "04/04/2023",
                party: "VORA MEDICALS",
                destination: "PADHARPUR",
                transporter: "BATCO ROADLINES LLP",
                goodsValue: "5335.50",
                invoiceNo: "BWMNI2300002",
                lrNo: "675432",
                driverName: "12934.00",
                lorryNumber: "BWMNI2300001",
                lrDate: "",
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
               transactionNo: "000000000001",
               dispatchDate: "04/04/2023",
               party: "RUBY PHARMA",
               destination: "SOLAPUR",
               transporter: "BTI",
               goodsValue: "12934.00",
               invoiceNo: "BWMNI2300001",
               lrNo: "675433",
               driverName: "000000000001",
               lorryNumber: "000000000001",
               lrDate: "000000000001",
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
               transactionNo: "000000000002",
               dispatchDate: "04/04/2023",
               party: "VORA MEDICALS",
               destination: "PADHARPUR",
               transporter: "BATCO ROADLINES LLP",
               goodsValue: "5335.50",
               invoiceNo: "BWMNI2300002",
               lrNo: "675432",
               driverName: "PADHARPUR",
               lorryNumber: "PADHARPUR",
               lrDate: "PADHARPUR",
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
              transactionNo: "000000000001",
              dispatchDate: "04/04/2023",
              party: "RUBY PHARMA",
              destination: "SOLAPUR",
              transporter: "BTI",
              goodsValue: "12934.00",
              invoiceNo: "BWMNI2300001",
              lrNo: "675433",
              driverName: "",
              lorryNumber: "PADHARPUR",
              lrDate: "",
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
              transactionNo: "000000000002",
              dispatchDate: "04/04/2023",
              party: "VORA MEDICALS",
              destination: "PADHARPUR",
              transporter: "BATCO ROADLINES LLP",
              goodsValue: "5335.50",
              invoiceNo: "BWMNI2300002",
              lrNo: "675432",
              driverName: "12934.00",
              lorryNumber: "BWMNI2300001",
              lrDate: "",
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