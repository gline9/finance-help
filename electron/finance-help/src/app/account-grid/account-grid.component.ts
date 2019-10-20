import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { AccountsService, Account } from './accounts';
import { Subscription, timer } from 'rxjs';
import CustomStore from 'devextreme/data/custom_store';
import { DxDataGridComponent } from 'devextreme-angular';


@Component({
    selector: 'app-account-grid',
    templateUrl: './account-grid.component.html',
    styleUrls: [
        './account-grid.component.scss'
    ]
})
export class AccountGridComponent implements OnInit, OnDestroy
{
    @ViewChild('dataGrid', {static: true}) dataGrid: DxDataGridComponent;

    dataSource = new CustomStore({
        key: 'id',
        loadMode: 'raw',
        insert: (values) => this.accountService.createAccount(
            Account.newAccount(values.name, values.balance, values.rate, values.compoundsPerYear)
        ).toPromise(),
        remove: (id) => this.accountService.deleteAccount(id).toPromise(),
        load: () => this.accountService.getAllAccounts().toPromise()
    });

    private accountSubscription: Subscription;

    constructor(
        private readonly accountService: AccountsService
    )
    {}

    public ngOnInit(): void
    {
        this.accountSubscription = timer(0, 10000).subscribe(() => {
            this.dataGrid.instance.getDataSource().reload();
        });
    }

    public ngOnDestroy(): void
    {
        this.accountSubscription.unsubscribe();
    }

}
