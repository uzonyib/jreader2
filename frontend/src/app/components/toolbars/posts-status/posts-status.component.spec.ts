import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostsStatusComponent } from './posts-status.component';

describe('PostsStatusComponent', () => {
    let component: PostsStatusComponent;
    let fixture: ComponentFixture<PostsStatusComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [PostsStatusComponent]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(PostsStatusComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
