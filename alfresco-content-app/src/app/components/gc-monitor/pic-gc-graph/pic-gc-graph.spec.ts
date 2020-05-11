import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PicGcGraphComponent } from './pic-gc-graph.component';

describe('PicHeapAfterGcComponent', () => {
  let component: PicGcGraphComponent;
  let fixture: ComponentFixture<PicGcGraphComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [PicGcGraphComponent]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PicGcGraphComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
