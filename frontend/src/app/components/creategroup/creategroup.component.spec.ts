import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateGroupComponent } from './creategroup.component';

describe('CreategroupComponent', () => {
    let component: CreateGroupComponent;
    let fixture: ComponentFixture<CreateGroupComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [CreateGroupComponent]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(CreateGroupComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
