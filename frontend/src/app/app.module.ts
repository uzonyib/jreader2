import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule } from '@angular/forms';
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

import { NavBarComponent } from './components/navbar/navbar.component';
import { MenuComponent } from './components/menu/menu.component';
import { HomeComponent } from './components/home/home.component';
import { SettingsComponent } from './components/settings/settings.component';
import { ItemsComponent } from './components/items/items.component';
import { CreateGroupComponent } from './components/creategroup/creategroup.component';
import { MockRestInterceptor } from './interceptors/mockrestinterceptor';

const routes: Routes = [
    { path: '', redirectTo: '/reader/home', pathMatch: 'full' },
    { path: 'reader/home', component: HomeComponent },
    { path: 'reader/settings', component: SettingsComponent },
    { path: 'reader/items', component: ItemsComponent },
    { path: 'reader/items/groups/:groupId', component: ItemsComponent },
    { path: 'reader/items/groups/:groupId/subscription/:subscriptionId', component: ItemsComponent },
    { path: '**', redirectTo: 'reader/home' }
];

@NgModule({
    declarations: [
        AppComponent,
        NavBarComponent,
        MenuComponent,
        HomeComponent,
        SettingsComponent,
        ItemsComponent,
        CreateGroupComponent
    ],
    imports: [
        BrowserModule,
        HttpClientModule,
        FormsModule,
        BrowserAnimationsModule,
        RouterModule.forRoot(routes),
        MatButtonModule,
        MatIconModule,
        MatListModule,
        MatSidenavModule,
        MatToolbarModule,
        MatFormFieldModule,
        MatInputModule
    ],
    providers: [{ provide: HTTP_INTERCEPTORS, useClass: MockRestInterceptor, multi: true }],
    bootstrap: [AppComponent]
})
export class AppModule { }
