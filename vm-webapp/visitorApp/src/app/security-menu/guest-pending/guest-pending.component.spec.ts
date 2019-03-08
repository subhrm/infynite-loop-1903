import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GuestPendingComponent } from './guest-pending.component';

describe('GuestPendingComponent', () => {
  let component: GuestPendingComponent;
  let fixture: ComponentFixture<GuestPendingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GuestPendingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GuestPendingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
