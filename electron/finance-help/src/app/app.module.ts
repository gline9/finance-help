import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { AppService } from './app.service';
import { TotalBalanceComponent } from './total-balance/total-balance.component';
import { DxTextBoxModule, DxFormModule, DxDataGridModule } from 'devextreme-angular';
import { AccountGridComponent } from './account-grid/account-grid.component';

@NgModule({
  declarations: [
    AppComponent,
    TotalBalanceComponent,
    AccountGridComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    DxTextBoxModule,
    DxFormModule,
    DxDataGridModule
  ],
  providers: [AppService],
  bootstrap: [AppComponent]
})
export class AppModule { }
