import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmGroupDeletionComponent } from './confirm-group-deletion.component';

describe('ConfirmGroupDeletionComponent', () => {
  let component: ConfirmGroupDeletionComponent;
  let fixture: ComponentFixture<ConfirmGroupDeletionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConfirmGroupDeletionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfirmGroupDeletionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
