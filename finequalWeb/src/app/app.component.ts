import { Component } from '@angular/core';
import { Location } from '@angular/common';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent{
  title = 'finequalwebapp';
  isLenderDashboard:boolean;
  constructor( private route: Location){
    var location = route.path();
    if(location.includes('lenderdashboard')){
      this.isLenderDashboard = true;
    }
  }

}
