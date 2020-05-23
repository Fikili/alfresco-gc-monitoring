import { TestBed } from '@angular/core/testing';

import { GcServiceService } from './gc-service.service';

describe('GcServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: GcServiceService = TestBed.get(GcServiceService);
    expect(service).toBeTruthy();
  });
});
