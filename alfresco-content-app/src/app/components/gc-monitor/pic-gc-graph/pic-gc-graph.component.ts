import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'aca-pic-gc-graph',
  templateUrl: './pic-gc-graph.component.html',
  styleUrls: ['./pic-gc-graph.component.scss']
})
export class PicGcGraphComponent implements OnInit {
  @Input() graphWidth: number;

  @Input() source: string;

  @Input() link: string;

  constructor() {}

  ngOnInit() {}
}
