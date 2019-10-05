import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({providedIn: 'root'})
export class AccountsService
{
    private readonly ACCOUNT_URL = 'http://localhost:5150/accounts';

    constructor(
        private httpClient: HttpClient
    )
    {}

    public createAccount(account: Account): Observable<Account>
    {
        // tslint:disable-next-line:no-string-literal
        return this.httpClient.post(this.ACCOUNT_URL, account).pipe(map(body => Account['fromMomento'](body)));
    }

    public getAllAccounts(): Observable<Account[]>
    {
        return this.httpClient.get<any[]>(this.ACCOUNT_URL);
    }

}

export class Account
{
    private id?: string;
    balance: number;
    rate: number;
    compoundsPerYear: number;

    private constructor(
        args: {balance: number, rate: number, compoundsPerYear: number, id?: string}
    )
    {
        if (args.id)
        {
            this.id = args.id;
        }

        this.balance = args.balance;
        this.rate = args.rate;
        this.compoundsPerYear = args.compoundsPerYear;
    }

    public static newAccount(balance: number, rate: number, compoundsPerYear: number): Account
    {
        return new Account({balance, rate, compoundsPerYear});
    }

    private static fromMomento(json: any): Account
    {
        return new Account(json);
    }
}

