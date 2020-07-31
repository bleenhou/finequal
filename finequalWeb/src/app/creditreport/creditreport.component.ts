import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import { BiasdialogComponent } from 'src/app/creditreport/biasdialog/biasdialog.component';
import { FinequalService } from 'src/app/finequal.service';
import { isNaN } from 'lodash';
import { isNumber } from 'util';

@Component({
  selector: 'app-creditreport',
  templateUrl: './creditreport.component.html',
  styleUrls: ['./creditreport.component.scss'],
  encapsulation: ViewEncapsulation.None
})


export class CreditreportComponent implements OnInit {

  constructor(public dialog: MatDialog, public finequalService:FinequalService) { }
  gaugeType = "full";
  gaugepreppend = "";
  biasrate = "";
  biasData:any;
  imgSrc = "assets/BiasDetect.png";
  biasimgSrc = "assets/BiasDetect.png";
  unbiasImgSrc = "assets/bias-badge-success.png";
  borrowerData:any;
  havebias:boolean;
  ngOnInit(): void {
    this.borrowerData = this.finequalService.getBorrowerData();
    this.biasData = this.finequalService.getInitialBiasData();
    this.havebias = true;
    this.finequalService.isLenderDashboard = false;

    this.borrowerData.loanData.monthlyPayment = (this.borrowerData.personalData.loanAmount*(1+this.borrowerData.loanData.interestRate )^20)/240;
    this.borrowerData.loanData.totalInterest =this.borrowerData.personalData.loanAmount*Math.pow(1+(this.borrowerData.loanData.interestRate/100),20)-this.borrowerData.personalData.loanAmount;
   
  } 

  biasmouseEnter():void{
    if(this.havebias){
      this.gaugepreppend = this.biasData.biasScore;
      this.biasrate = this.biasData.biasRate;
      this.imgSrc = 'assets/bias-hover.png';
    }
    else{
      this.imgSrc = this.unbiasImgSrc;
    }
  }

  biasmouseLeave():void{
    if(this.havebias){
      this.gaugepreppend = "";
      this.biasrate = "";
      this.imgSrc = 'assets/BiasDetect.png';
    }
    else{
      this.imgSrc = this.unbiasImgSrc;
    }
  }

  onbiasclick():void{
    const dialogRef = this.dialog.open(BiasdialogComponent, {
      width: '1208px',
      height: '683px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if(isNumber(result)){
        console.log(result);
        this.havebias = false;
        this.borrowerData.loanData.interestRate = result;
        this.borrowerData.loanData.monthlyPayment = (this.borrowerData.personalData.loanAmount*(1+result)^20)/240;
        this.borrowerData.loanData.totalInterest = this.borrowerData.personalData.loanAmount*Math.pow(1+(result/100),20)-this.borrowerData.personalData.loanAmount;
        this.imgSrc = this.unbiasImgSrc;  
      }
      console.log('The dialog was closed');
      
    });
  }
}
