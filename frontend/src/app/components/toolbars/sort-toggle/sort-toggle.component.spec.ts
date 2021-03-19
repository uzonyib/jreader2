import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SortToggleComponent } from './sort-toggle.component';

describe('SortToggleComponent', () => {
    let component: SortToggleComponent;
    let fixture: ComponentFixture<SortToggleComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [SortToggleComponent]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(SortToggleComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
