import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatListModule } from '@angular/material/list';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { NavBarComponent } from './navbar/navbar.component';
import { MenuComponent } from './menu/menu.component';
import { HomeComponent } from './home/home.component';
import { SettingsComponent } from './settings/settings.component';
import { ItemsComponent } from './items/items.component';

const routes: Routes = [
  { path: '',   redirectTo: '/reader/home', pathMatch: 'full' },
  { path: 'reader/home', component: HomeComponent },
  { path: 'reader/settings', component: SettingsComponent },
  { path: 'reader/items', component: ItemsComponent },
  { path: 'reader/items/groups/:groupId', component: ItemsComponent },
  { path: 'reader/items/groups/:groupId/subscription/:subscriptionId', component: ItemsComponent },
  { path: '**', redirectTo: 'reader/home' }
]

@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent,
    MenuComponent,
    HomeComponent,
    SettingsComponent,
    ItemsComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    RouterModule.forRoot(routes),
    MatButtonModule,
    MatIconModule,
    MatListModule,
    MatSidenavModule,
    MatToolbarModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
