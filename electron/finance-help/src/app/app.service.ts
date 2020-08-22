import { Injectable, InjectionToken, Inject } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({providedIn: 'root'})
export class AppService
{
    constructor(
        private readonly httpClient: HttpClient,
        @Inject(APP_URL) private readonly  url: string
    )
    {}

    public getMessage(): Observable<string>
    {
        return this.httpClient.get(this.url, {observe: 'body', responseType: 'text'});
    }

}

export const APP_URL = new InjectionToken('Url for the application', {
    providedIn: 'root',
    factory: () => {
        return 'http://localhost:5150';
    }
});
