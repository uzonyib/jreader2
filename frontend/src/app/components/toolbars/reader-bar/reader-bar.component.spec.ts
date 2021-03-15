import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReaderBarComponent } from './reader-bar.component';

describe('ReaderBarComponent', () => {
    let component: ReaderBarComponent;
    let fixture: ComponentFixture<ReaderBarComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [ReaderBarComponent]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(ReaderBarComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
