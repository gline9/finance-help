import { Component, OnInit, ViewChild } from '@angular/core';
import { AccountsService, Account } from './accounts';
import { Subscription, timer } from 'rxjs';
import ArrayStore from 'devextreme/data/array_store';
import { DxDataGridComponent } from 'devextreme-angular';


@Component({
    selector: 'app-account-grid',
    templateUrl: './account-grid.component.html',
    styleUrls: [
        './account-grid.component.scss'
    ]
})
export class AccountGridComponent implements OnInit
{
    @ViewChild('dataGrid', {static: true}) dataGrid: DxDataGridComponent;

    accountList: Account[] = [];

    dataSource = new ArrayStore({
        data: this.accountList,
        key: 'id',
        onInserted: (data: any) => {
            this.createAccount(data);
        }
    });

    private accountSubscription: Subscription;

    constructor(
        private readonly accountService: AccountsService
    )
    {}

    public ngOnInit(): void
    {

        this.accountSubscription = timer(0, 10000).subscribe(() => {
            this.updateAccountList();
        });
    }

    private updateAccountList(): void
    {
        this.accountService.getAllAccounts().subscribe(
            (accounts) =>
            {
                this.accountList.splice(0);
                this.accountList.push(...accounts);
                this.dataGrid.instance.refresh();
            }
        );
    }

    public createAccount(accountData: any)
    {
        this.accountService.createAccount(
            Account.newAccount(accountData.balance, accountData.rate, accountData.compoundsPerYear)
        ).subscribe(() => {
            this.updateAccountList();
        });
    }

}
