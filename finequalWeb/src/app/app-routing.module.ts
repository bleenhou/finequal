import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from 'src/app/dashboard/dashboard.component';
import { CreditreportComponent } from 'src/app/creditreport/creditreport.component';
import { LenderdashboardComponent } from 'src/app/lenderdashboard/lenderdashboard.component';


const routes: Routes = [
  { path:'', component:DashboardComponent  },
  { path:'dashboard', component:DashboardComponent  },
  { path:'creditReport', component: CreditreportComponent },
  {path:'lenderdashboard', component:LenderdashboardComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
