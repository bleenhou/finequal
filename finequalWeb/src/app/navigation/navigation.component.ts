import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Location } from '@angular/common';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class NavigationComponent implements OnInit {

  isLenderDashboard:boolean;
  constructor(private route: Location) {
    var location = route.path();
    if(location.includes('lenderdashboard')){
      this.isLenderDashboard = true;
    }
   }

  ngOnInit() {
    
  }

}
