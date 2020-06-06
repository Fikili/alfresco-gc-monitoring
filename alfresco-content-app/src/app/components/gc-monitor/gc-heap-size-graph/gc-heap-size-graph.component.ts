import { Component, OnInit } from '@angular/core';
import * as Highcharts from 'highcharts';
import { GcServiceService } from '../../../services/gc-service.service';
import { concatMap } from 'rxjs/operators';
import { Observable, forkJoin } from 'rxjs';

@Component({
  selector: 'aca-gc-heap-size-graph',
  templateUrl: './gc-heap-size-graph.component.html',
  styleUrls: ['./gc-heap-size-graph.component.scss']
})
export class GcHeapSizeGraphComponent implements OnInit {
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
    plotOptions: {
      pie: {
        allowPointSelect: true,
        cursor: 'pointer',
        dataLabels: {
          enabled: true
        }
      }
    },
    series: [{ type: 'line' }]
  };

  constructor(private gcService: GcServiceService) {}

  ngOnInit() {
    const graphData: Array<number> = new Array<number>();
    this.chartOptions.series = [
      {
        type: 'line',
        name: 'Garbage collection heap',
        data: []
      }
    ];
    this.updateFromInput = true;

    const nodes = this.gcService.getChildrenContent();
    nodes.subscribe(children => {
      console.log('ooo');
      children.forEach(content => {
        const size: number = parseFloat(
          content.jvmHeapSize.total.peakSize.slice(0, -3)
        );
        graphData.push(size);
      });
      this.chartOptions.series = [
        {
          type: 'line',
          name: 'Garbage collection heap',
          data: [...graphData]
        }
      ];
      this.updateFromInput = true;
    });
  }
}
