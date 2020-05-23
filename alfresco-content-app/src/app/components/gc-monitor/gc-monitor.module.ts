import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminPageComponent } from './admin-page/admin-page.component';
import { PicGcGraphComponent } from './pic-gc-graph/pic-gc-graph.component';
import { HighchartsChartModule } from 'highcharts-angular';
import { GcCausesGraphComponent } from './gc-causes-graph/gc-causes-graph.component';
@NgModule({
  declarations: [
    AdminPageComponent,
    PicGcGraphComponent,
    GcCausesGraphComponent
  ],
  imports: [CommonModule, HighchartsChartModule]
})
export class GcMonitorModule {}
