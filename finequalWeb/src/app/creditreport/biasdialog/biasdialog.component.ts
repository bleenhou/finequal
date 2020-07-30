import { Component, OnInit, Inject, ViewEncapsulation } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FinequalService } from 'src/app/finequal.service';

export class BiasQuery {
  loanType: string;
  purpose: string;
  applicantEthnicity:string;
  coApplicantEthnicity:string;
  applicantGender:string;
  coApplicantGender:string;
  income:number;
  loanAmount:number;
  city:string;
}

@Component({
  selector: 'app-biasdialog',
  templateUrl: './biasdialog.component.html',
  styleUrls: ['./biasdialog.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class BiasdialogComponent implements OnInit {

  loantypechecked:boolean;
  purposechecked:boolean;
  ethnicitychecked:boolean;
  coethnicitychecked:boolean;
  genderchecked:boolean;
  cogenderchecked:boolean;
  incomechecked:boolean;
  loanamountchecked:boolean;
  citychecked:boolean;
  borrowerData:any;
  havebias:boolean;
  biasrate:number;
  biasdetails:any;
  constructor(public dialogRef: MatDialogRef<BiasdialogComponent>, public finequalService:FinequalService) { }

  ngOnInit(): void {
    this.loantypechecked = true;
    this.purposechecked = true;
    this.ethnicitychecked = true;
    this.coethnicitychecked = true;
    this.genderchecked = true;
    this.cogenderchecked = true;
    this.incomechecked= true;
    this.loanamountchecked = true;
    this.citychecked = true;
    this.havebias = true;
    this.borrowerData = this.finequalService.getBorrowerData();
    this.biasdetails = [
      "Ethnicity of applicant and co applicant",
      "Gender of applicant and co applicant",
      "City"
    ];
  }

  onbiasSelect():void{
    var payload = new BiasQuery();
    console.log(this.loantypechecked);
    if(this.loantypechecked){
      payload.loanType = this.borrowerData.loanData.loanType;
    }
    if(this.purposechecked){
      payload.purpose = this.borrowerData.loanData.purpose;
    }
    if(this.ethnicitychecked){
      payload.applicantEthnicity = this.borrowerData.personalData.ethnicity;
    }
    if(this.coethnicitychecked){
      payload.coApplicantEthnicity = this.borrowerData.personalData.coethnicity;
    }
    if(this.genderchecked){
      payload.applicantGender = this.borrowerData.personalData.gender;
    }
    if(this.cogenderchecked){
      payload.coApplicantGender = this.borrowerData.personalData.cogender;
    }
    if(this.incomechecked){
      payload.income = this.borrowerData.personalData.income;
    }
    if(this.loanamountchecked){
      payload.loanAmount = this.borrowerData.personalData.loanAmount;
    }
    if(this.citychecked){
      payload.city = this.borrowerData.personalData.city;
    }
    this.finequalService.getBiasData(payload).subscribe(res=>{
      console.log(res);
      this.biasrate = res.rate*100;     
    });
    if(!this.ethnicitychecked && !this.coethnicitychecked && !this.genderchecked && !this.cogenderchecked && !this.citychecked)
    this.havebias = false;
    this.biasdetails = [];
    if(this.ethnicitychecked && this.ethnicitychecked){
      this.biasdetails.push("Ethnicity of applicant and co applicant");
    }else {
      if(this.ethnicitychecked){
        this.biasdetails.push("Ethnicity of applicant")
      }else if(this.coethnicitychecked){
        this.biasdetails.push("Ethnicity of co applicant")
      }
    }

    if(this.genderchecked && this.cogenderchecked){
      this.biasdetails.push("Gender of applicant and co applicant");
    }else{
      if(this.genderchecked){
        this.biasdetails.push("Gender of applicant");
      }else if(this.cogenderchecked){
        this.biasdetails.push("Gender of co applicant");
      }
    }

    if(this.citychecked){
      this.biasdetails.push("City");
    }
  }

  onapplyclick():void{
    this.dialogRef.close(this.biasrate);
  }
}
