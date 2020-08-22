import { APP_URL } from './../app.service';
import { Injectable, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({providedIn: 'root'})
export class AccountsService
{
    private readonly ACCOUNT_URL = this.url + '/accounts';

    constructor(
        private httpClient: HttpClient,
        @Inject(APP_URL) private readonly url: string
    )
    {}

    public createAccount(account: Account): Observable<Account>
    {
        // tslint:disable-next-line:no-string-literal
        return this.httpClient.post(this.ACCOUNT_URL, account).pipe(map(body => Account['fromMomento'](body)));
    }

    public deleteAccount(id: string): Observable<Account>
    {
        // tslint:disable-next-line:no-string-literal
        return this.httpClient.request('DELETE', this.ACCOUNT_URL, {body: {id}}).pipe(map(body => Account['fromMomento'](body)));
    }

    public getAllAccounts(): Observable<Account[]>
    {
        return this.httpClient.get<any[]>(this.ACCOUNT_URL);
    }

}

export class Account
{
    id?: string;
    name: string;
    balance: number;
    rate: number;
    compoundsPerYear: number;

    private constructor(
        args: {name: string, balance: number, rate: number, compoundsPerYear: number, id?: string}
    )
    {
        if (args.id)
        {
            this.id = args.id;
        }

        this.name = args.name;
        this.balance = args.balance;
        this.rate = args.rate;
        this.compoundsPerYear = args.compoundsPerYear;
    }

    public static newAccount(name: string, balance: number, rate: number, compoundsPerYear: number): Account
    {
        return new Account({name, balance, rate, compoundsPerYear});
    }

    private static fromMomento(json: any): Account
    {
        return new Account(json);
    }
}

