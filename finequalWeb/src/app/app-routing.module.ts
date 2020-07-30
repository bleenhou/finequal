import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from 'src/app/dashboard/dashboard.component';
import { CreditreportComponent } from 'src/app/creditreport/creditreport.component';


const routes: Routes = [
  { path:'', component:DashboardComponent  },
  { path:'dashboard', component:DashboardComponent  },
  { path:'creditReport', component: CreditreportComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
