import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminPageComponent } from './admin-page/admin-page.component';
import { PicGcGraphComponent } from './pic-gc-graph/pic-gc-graph.component';
import { HighchartsChartModule } from 'highcharts-angular';
@NgModule({
  declarations: [AdminPageComponent, PicGcGraphComponent],
  imports: [CommonModule, HighchartsChartModule]
})
export class GcMonitorModule {}
