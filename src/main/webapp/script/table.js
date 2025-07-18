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