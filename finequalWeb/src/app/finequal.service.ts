import { Injectable } from '@angular/core';
import { ApiService } from 'src/app/api.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FinequalService {

  constructor(private apiService:ApiService) { }

  public getDashboardData(){
    var chartData = [
      {
          "name":"2010",
          "value":120
      },
      {
          "name":"2011",
          "value":150
      },
      {
          "name":"2012",
          "value":200
      },
      {
          "name":"2013",
          "value":220
      },
      {
          "name":"2014",
          "value":70
      },
      {
          "name":"2015",
          "value":160
      },
      {
          "name":"2016",
          "value":190
      },
      {
          "name":"2017",
          "value":20
      },
      {
          "name":"2018",
          "value":80
      },
      {
          "name":"2019",
          "value":160
      },
      {
          "name":"2020",
          "value":190
      }
    ];
    return {
      chartData : chartData,
      loanData : [
          {id:1, firstname:'Ole', lastname:'Namus', score:'200', amount:'$400'},
          {id:2, firstname:'John', lastname:'Ken', score:'200', amount:'$400'},
          {id:3, firstname:'Laury', lastname:'James', score:'200', amount:'$400'},
          {id:4, firstname:'Mark', lastname:'Mathew', score:'200', amount:'$400'}],
      scoreChartData: {income : 76, debtIncome: 56, locationScore: 45, loyaltyScore: 80},
      activityList:["You have a new loan to review", 
        "You have a new loan to review", 
        "You have a new loan to review", 
        "You have a new loan to review"],
      headerData:{header1:'$450', header2:'$450', header3: '$450', header4:'$450'}
    }
  }

  public getBorrowerData(){
    return {

      personalData:{  
        name:'Ole Namus', 
        avatar:'assets/user11.png', 
        income:'2460.20', 
        ethnicity:'Afro American', 
        ethnicityenum:'AFRICAN_AMERICAN',
        coethnicity:'AFRICAN_AMERICAN',
        gender:'FEMALE',
        cogender:'FEMALE',
        city:'RICHMOND',
        coapplicant:'Emma Namus', 
        loanAmount:34625.00
      },
      loanData:{
        loanType:'CONVENTIONAL',
        purpose:'PURCHASE',
        payoffDate:'Dec. 2025',
        monthlyPayment: 540,
        totalInterest: 5680,
        interestRate:6.05,
        score:760,
        loanStatus:'Pending'
      },
      scoreChartData: {income : 76, debtIncome: 56, locationScore: 45, loyaltyScore: 80},
      historyData: [
        {
          title:'Body 1',
          text: 'If you look at any designer you admire'
        },
        {
          title:'Body 2',
          text: 'If you look at any designer you admire'
        },
        {
          title:'Body 3',
          text: 'If you look at any designer you admire'
        },
        {
          title:'Body 4',
          text: 'If you look at any designer you admire'
        }
      ]
    };
  }

  public getBiasData(payload:any): Observable<any>{
    return this.apiService.post('inference', payload);
  }

  public getInitialBiasData(){
    return { biasScore:200, biasRate:5.80, biasStatus:true}
  }
}
