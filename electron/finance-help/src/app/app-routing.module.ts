import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { OpenFileComponent } from './open-file/open-file.component';
import { AccountOverviewComponent } from './account-overview/account-overview.component';


const routes: Routes = [
  {
    path: '',
    component: OpenFileComponent
  },
  {
    path: 'account-overview',
    component: AccountOverviewComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
