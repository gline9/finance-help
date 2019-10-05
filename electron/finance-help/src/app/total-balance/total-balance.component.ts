import { HttpClient } from '@angular/common/http';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription, Observable, timer } from 'rxjs';


@Component({
    selector: 'app-total-balance',
    templateUrl: './total-balance.component.html',
    styleUrls: [
        './total-balance.component.scss'
    ]
})
export class TotalBalanceComponent implements OnInit, OnDestroy
{
    private readonly totalBalanceURL = 'http://localhost:5150/total/balance';
    totalBalance = 0;

    subscription: Subscription;

    constructor(
        private httpClient: HttpClient
    )
    {}

    public ngOnInit(): void
    {
        this.subscription = timer(0, 10000).subscribe(() => {
            this.refreshTotalBalance();
        });
    }

    private refreshTotalBalance()
    {
        this.httpClient.get(this.totalBalanceURL, {responseType: 'text'}).subscribe(
            (balance) => {
                this.totalBalance = Number(balance);
            }
        );
    }

    public ngOnDestroy(): void
    {
        this.subscription.unsubscribe();
    }

}
