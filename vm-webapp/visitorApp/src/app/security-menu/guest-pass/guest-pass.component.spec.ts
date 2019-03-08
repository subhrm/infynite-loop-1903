import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GuestPassComponent } from './guest-pass.component';

describe('GuestPassComponent', () => {
  let component: GuestPassComponent;
  let fixture: ComponentFixture<GuestPassComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GuestPassComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GuestPassComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
