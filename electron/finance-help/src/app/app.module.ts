import { DepositComponent } from './transactions/deposit/deposit.component';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { AppService } from './app.service';
import { TotalBalanceComponent } from './total-balance/total-balance.component';
import { DxTextBoxModule, DxFormModule, DxDataGridModule, DxButtonModule, DxPopupModule } from 'devextreme-angular';
import { AccountGridComponent } from './account-grid/account-grid.component';
import { NgxElectronModule } from 'ngx-electron';
import { OpenFileComponent } from './open-file/open-file.component';
import { AccountOverviewComponent } from './account-overview/account-overview.component';

@NgModule({
  declarations: [
    AppComponent,
    TotalBalanceComponent,
    AccountGridComponent,
    OpenFileComponent,
    AccountOverviewComponent,
    DepositComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    DxTextBoxModule,
    DxFormModule,
    DxDataGridModule,
    DxButtonModule,
    DxPopupModule,
    NgxElectronModule
  ],
  providers: [
    AppService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
