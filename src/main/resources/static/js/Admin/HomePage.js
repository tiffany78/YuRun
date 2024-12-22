$(function () {
    // Chart 1: Column chart
    Highcharts.chart('container', {
        chart: {
            type: 'column',
            backgroundColor: null
        },
        title: {
            text: 'Participant Join The Race'
        },
        xAxis: {
            categories: surveyKeys, // Variabel global untuk chart 1
            crosshair: true,
        },
        yAxis: {
            min: 0,
            max: 5,
            title: {
                text: 'Count'
            }
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y:.1f} K</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            }
        },
        series: [{
            name: 'The Race',
            data: surveyValues, // Variabel global untuk chart 1
            color: '#ff6f37'
        }]
    });

    // Chart 2: Line chart
    Highcharts.chart('container2', {
        chart: {
            type: 'line',
            width: 500,
            backgroundColor: null
        },
        title: {
            text: 'Line chart'
        },
        xAxis: {
            categories: surveyKeys2 // Variabel global untuk chart 2
        },
        tooltip: {
            formatter: function () {
                console.log(this);
            }
        },
        series: [{
            data: surveyValues2 // Variabel global untuk chart 2
        }]
    });

    console.log("surveyKeys:", surveyKeys);
    console.log("surveyValues:", surveyValues);
    console.log("surveyKeys2:", surveyKeys2);
    console.log("surveyValues2:", surveyValues2);
});
