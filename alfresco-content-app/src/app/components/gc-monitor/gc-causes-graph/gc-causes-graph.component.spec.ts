import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GcCausesGraphComponent } from './gc-causes-graph.component';

describe('GcCausesGraphComponent', () => {
  let component: GcCausesGraphComponent;
  let fixture: ComponentFixture<GcCausesGraphComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [GcCausesGraphComponent]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GcCausesGraphComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
