import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';

@Injectable({providedIn: 'root'})
export class AppService
{
    constructor(
        private readonly httpClient: HttpClient
    )
    {}

    public getMessage(): Observable<string>
    {
        return this.httpClient.get('http://localhost:5150', {observe: 'body', responseType: 'text'});
    }

}