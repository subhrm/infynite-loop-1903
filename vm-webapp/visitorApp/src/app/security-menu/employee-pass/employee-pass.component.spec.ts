import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeePassComponent } from './employee-pass.component';

describe('EmployeePassComponent', () => {
  let component: EmployeePassComponent;
  let fixture: ComponentFixture<EmployeePassComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmployeePassComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmployeePassComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
