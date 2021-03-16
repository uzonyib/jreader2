import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatListModule } from '@angular/material/list';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatDialogModule } from '@angular/material/dialog';
import { MatMenuModule } from '@angular/material/menu';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatBadgeModule } from '@angular/material/badge';

import { MenuComponent } from './components/menu/menu.component';
import { OverviewComponent } from './components/overview/overview.component';
import { GroupSettingsComponent } from './components/overview/group-settings/group-settings.component';
import { SubscriptionSettingsComponent } from './components/overview/subscription-settings/subscription-settings.component';
import { PostsComponent } from './components/posts/posts.component';
import { MenuBarComponent } from './components/toolbars/menu-bar/menu-bar.component';
import { SettingsBarComponent } from './components/toolbars/settings-bar/settings-bar.component';
import { CreateGroupComponent } from './components/dialogs/create-group/create-group.component';
import { SubscribeComponent } from './components/dialogs/subscribe/subscribe.component';
import { ConfirmGroupDeletionComponent } from './components/dialogs/confirm-group-deletion/confirm-group-deletion.component';
import { ConfirmUnsubscribeComponent } from './components/dialogs/confirm-unsubscribe/confirm-unsubscribe.component';
import { MenuToggleComponent } from './components/toolbars/menu-toggle/menu-toggle.component';
import { MockRestInterceptor } from './interceptors/mockrestinterceptor';
import { ReaderBarComponent } from './components/toolbars/reader-bar/reader-bar.component';

const routes: Routes = [
    { path: '', redirectTo: '/reader/overview?menu=on', pathMatch: 'full' },
    { path: 'reader/overview', component: OverviewComponent },
    { path: 'reader/posts', component: PostsComponent },
    { path: 'reader/posts/groups/:groupId', component: PostsComponent },
    { path: 'reader/posts/groups/:groupId/subscription/:subscriptionId', component: PostsComponent },
    { path: '**', redirectTo: '/reader/overview?menu=on' }
];

@NgModule({
    declarations: [
        AppComponent,
        MenuComponent,
        OverviewComponent,
        GroupSettingsComponent,
        SubscriptionSettingsComponent,
        PostsComponent,
        MenuBarComponent,
        SettingsBarComponent,
        CreateGroupComponent,
        SubscribeComponent,
        ConfirmGroupDeletionComponent,
        ConfirmUnsubscribeComponent,
        MenuToggleComponent,
        ReaderBarComponent
    ],
    imports: [
        BrowserModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule,
        BrowserAnimationsModule,
        RouterModule.forRoot(routes),
        MatButtonModule,
        MatIconModule,
        MatListModule,
        MatSidenavModule,
        MatToolbarModule,
        MatFormFieldModule,
        MatInputModule,
        MatSelectModule,
        MatTooltipModule,
        MatDialogModule,
        MatMenuModule,
        MatButtonToggleModule,
        MatExpansionModule,
        MatBadgeModule
    ],
    providers: [{ provide: HTTP_INTERCEPTORS, useClass: MockRestInterceptor, multi: true }],
    bootstrap: [AppComponent]
})
export class AppModule { }
