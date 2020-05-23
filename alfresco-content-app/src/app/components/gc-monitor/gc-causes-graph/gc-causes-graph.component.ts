import { Component, OnInit } from '@angular/core';
import * as Highcharts from 'highcharts';
import { GcServiceService } from '../../../services/gc-service.service';

@Component({
  selector: 'aca-gc-causes-graph',
  templateUrl: './gc-causes-graph.component.html',
  styleUrls: ['./gc-causes-graph.component.scss']
})
export class GcCausesGraphComponent implements OnInit {
  updateFromInput = false;

  Highcharts: typeof Highcharts = Highcharts;
  chartOptions: Highcharts.Options = {
    chart: {
      plotBorderWidth: null,
      plotShadow: false
    },
    title: {
      text: 'Garbage collection causes'
    },
    tooltip: {
      pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
    },
    plotOptions: {
      pie: {
        allowPointSelect: true,
        cursor: 'pointer',
        dataLabels: {
          enabled: true,
          format: '<b>{point.name}%</b>: {point.percentage:.1f} %'
        }
      }
    },
    series: [{ type: 'pie' }]
  };

  constructor(private gcService: GcServiceService) {}

  ngOnInit() {
    const nodes = this.gcService.getChildren();
    nodes.subscribe(children => {
      children.list.entries.forEach(element => {
        const nodeId = element.entry.id;
        const nodeContent = this.gcService.getContent(nodeId);
        nodeContent.subscribe(content => {
          const graphData: Array<any> = new Array<any>();
          const causes = content.gcCauses;
          causes.forEach(cause => {
            const obj = [cause.cause, cause.count];
            graphData.push(obj);
          });
          this.chartOptions.series = [
            {
              type: 'pie',
              name: 'Garbage collection causes',
              data: [...graphData]
            }
          ];
          this.updateFromInput = true;
        });
      });
    });
  }
}
