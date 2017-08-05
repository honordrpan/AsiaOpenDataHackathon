var X_values = [];
var series_data = [];
function drawcompanycasechart(){
	Highcharts.chart('container', {
    chart: {
        type: 'bar'
    },
    title: {
        text: ''
    },
    subtitle: {
        text: ''
    },
    xAxis: {
        categories: X_values,
        title: {
            text: null
        },
		labels: {
			formatter: function () {
				var text = this.value,
					formatted = text.length > 25 ? text.substring(0, 25) + '...' : text;

				return '<div class="js-ellipse" style="width:150px; overflow:hidden" title="' + text + '">' + formatted + '</div>';
			},
			style: {
				width: '150px'
			},
			useHTML: true
		}
    },
    yAxis: {
        min: 0,
        title: {
            text: '新台幣(NT)千元',
            align: 'high'
        },
        labels: {
            overflow: 'justify'
        }
    },
    tooltip: {
        valueSuffix: ''
    },
    plotOptions: {
        bar: {
            dataLabels: {
                enabled: true
            }
        }
    },
    legend: {
        layout: 'vertical',
        align: 'right',
        verticalAlign: 'top',
        x: -40,
        y: 80,
        floating: true,
        borderWidth: 0,
        backgroundColor: ((Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'),
        shadow: true
    },
    credits: {
        enabled: false
    },
    series: [{
        name: '建案金額',
        data: series_data,
		pointWidth: 20
    }]
});
}