import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GcHeapSizeGraphComponent } from './gc-heap-size-graph.component';

describe('GcHeapSizeGraphComponent', () => {
  let component: GcHeapSizeGraphComponent;
  let fixture: ComponentFixture<GcHeapSizeGraphComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [GcHeapSizeGraphComponent]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GcHeapSizeGraphComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
