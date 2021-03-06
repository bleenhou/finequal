import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { NavigationComponent } from './navigation/navigation.component';
import { HeaderComponent } from './header/header.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FlexLayoutModule } from "@angular/flex-layout";
import { MatToolbarModule } from "@angular/material/toolbar";
import { MatIconModule } from "@angular/material/icon";
import { MatTableModule } from '@angular/material/table';
import { CreditreportComponent } from './creditreport/creditreport.component';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { NgxGaugeModule } from 'ngx-gauge';
import { BiasdialogComponent } from './creditreport/biasdialog/biasdialog.component';
import { MatDialogModule } from '@angular/material/dialog';
import { MatTabsModule } from '@angular/material/tabs';
import {MatRadioModule} from '@angular/material/radio';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { FormsModule } from '@angular/forms';
import { LenderdashboardComponent } from './lenderdashboard/lenderdashboard.component';
import { MatInputModule } from '@angular/material/input';
import { NgxCurrencyModule } from "ngx-currency";
@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    NavigationComponent,
    HeaderComponent,
    CreditreportComponent,
    BiasdialogComponent,
    LenderdashboardComponent
  ],
  imports: [
    FormsModule,
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    FlexLayoutModule,
    MatToolbarModule,
    MatIconModule,
    NgxCurrencyModule,
    MatTabsModule,
    MatTableModule,
    MatInputModule,
    MatDialogModule,
    NgxChartsModule,
    NgxGaugeModule,
    MatRadioModule,
    MatCheckboxModule    
  ],
  entryComponents:[BiasdialogComponent],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
