import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GuestAccessComponent } from './guest-access.component';

describe('GuestAccessComponent', () => {
  let component: GuestAccessComponent;
  let fixture: ComponentFixture<GuestAccessComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GuestAccessComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GuestAccessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
