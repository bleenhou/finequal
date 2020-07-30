import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { FinequalService } from 'src/app/finequal.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class DashboardComponent implements OnInit { 
  public displayedColumns: Array<string> = ['checkbox','firstname', 'lastname', 'score', 'amount'];
  public dashboardData:any;  
  public color:any;
  public showgridlines:any;
  constructor(  private router: Router, private finequalService:FinequalService ) {
    
   }

  ngOnInit() {
      this.color = {domain:['#a4b7e1']}
      this.showgridlines = false;
      this.dashboardData = this.finequalService.getDashboardData();
  }

  selectloan():void{
    this.router.navigate(['/creditReport']);
  }
}
