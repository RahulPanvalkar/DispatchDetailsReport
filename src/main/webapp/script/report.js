


   $(document).ready(function () {
       let fixedRow = "", scrollRow = "";
       console.log("rows : ", rows);

       rows.forEach(row => {
           fixedRow += `
               <tr class='inner-tbl-tr'>
                   <td>${row.transactionNo}</td>
                   <td>${row.dispatchDate}</td>
                   <td>${row.party}</td>
               </tr>
           `;

           scrollRow += `
               <tr class='inner-tbl-tr'>
                   <td>${row.destination}</td>
                   <td>${row.transporter}</td>
                   <td>${row.goodsValue}</td>
                   <td>${row.invoiceNo}</td>
                   <td>${row.lrNo}</td>
                   <td>${row.driverName}</td>
                   <td>${row.lorryNumber}</td>
                   <td>${row.lrDate}</td>
                   <td>${row.dispatchDelay}</td>
                   <td>${row.noOfCases}</td>
                   <td>${row.formNum}</td>
                   <td>${row.cFormDate}</td>
                   <td>${row.cFormValue}</td>
                   <td>${row.podDate}</td>
                   <td>${row.podNumber}</td>
                   <td>${row.reason}</td>
               </tr>
           `;
       });

       $("#value-table-1 tbody").html(fixedRow);
       $("#value-table-2 tbody").html(scrollRow);
   });


// When body scrolls, sync the header
$('.scroll-body').on('scroll', function () {
    console.log("scroll event triggerred..");
    $('.scroll-header').scrollLeft($(this).scrollLeft());
});



// function to handle the dispatch data
function handleDispatchReportData(response) {
    console.log("handleDispatchReportData >> response >> ", response);

    // Clear existing table data
    $('#dispatch-table tbody').empty();

    // Check if there's data in the response
    if (response.data && response.data.length > 0) {
        // Loop through the response data and append rows to the table
        $.each(response.data, function(index, dispatch) {
            var row = '<tr>';
            row += '<td>' + dispatch.dspTrnNo + '</td>';
            row += '<td>' + (dispatch.dspDt ? dispatch.dspDt : '') + '</td>';
            row += '<td>' + dispatch.custName + '</td>';
            row += '<td>' + dispatch.destination + '</td>';
            row += '<td>' + dispatch.transporter + '</td>';
            row += '<td>' + dispatch.goodsValue + '</td>';
            row += '<td>' + dispatch.invNo + '</td>';
            row += '<td>' + dispatch.lrNum + '</td>';
            row += '<td>' + dispatch.driverName + '</td>';
            row += '<td>' + dispatch.lorryNo + '</td>';
            row += '<td>' + (dispatch.lrDate ? dispatch.lrDate : '') + '</td>';
            row += '<td>' + dispatch.delayDays + '</td>';
            row += '<td>' + dispatch.noOfCases + '</td>';
            row += '<td>' + dispatch.formNum + '</td>';
            row += '<td>' + (dispatch.cFormDate ? dispatch.cFormDate : '') + '</td>';
            row += '<td>' + dispatch.cFormValue + '</td>';
            row += '<td>' + (dispatch.podDate ? dispatch.podDate : '') + '</td>';
            row += '<td>' + dispatch.podNum + '</td>';
            row += '<td>' + dispatch.podReason + '</td>';
            row += '</tr>';

            // Append the row to the table body
            $('#dispatch-table tbody').append(row);
        });
    } else {
        // If no data is available
        $('#dispatch-table tbody').append('<tr><td colspan="18">No data available</td></tr>');
    }
}

// Handle button click event
/*$('#load-data-btn').click(function() {
    makeAjaxRequest("dispatch-register-data", handleDispatchReportData);
});*/

const rows = [
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
   ];