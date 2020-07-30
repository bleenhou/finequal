import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { catchError, finalize } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private httpClient: HttpClient) { }
  public baseUrl = " http://81.249.199.17:1234/finequal/v1/";
  public isLoading$: BehaviorSubject<boolean> = new BehaviorSubject(false);

  public get(url: string): Observable<any> {   
    this.isLoading$.next(true);
    let headers = new HttpHeaders();
    headers = headers.set("Accept", "application/json");
    const options = { headers: headers };
    return this.httpClient.get(this.baseUrl + url, options).pipe(      
      finalize(() => this.isLoading$.next(false))
    );
  }

  public post(url: string, payload: any): Observable<any> {
    this.isLoading$.next(true);
    let headers = new HttpHeaders();
    headers = headers.set("Accept", "application/json");
    headers.set("Allow-Origin", "*");
    const options = { headers: headers };
    return this.httpClient.post(this.baseUrl + url, payload, options).pipe(
      finalize(() => this.isLoading$.next(false))
    );
  }

}
