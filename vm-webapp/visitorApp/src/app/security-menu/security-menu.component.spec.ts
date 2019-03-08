import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SecurityMenuComponent } from './security-menu.component';

describe('SecurityMenuComponent', () => {
  let component: SecurityMenuComponent;
  let fixture: ComponentFixture<SecurityMenuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SecurityMenuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SecurityMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
