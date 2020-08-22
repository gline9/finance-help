import { Component, Input } from '@angular/core';
import { TransactionService, Deposit } from '../transactions';
import CustomStore from 'devextreme/data/custom_store';

@Component({
    selector: 'app-deposit',
    templateUrl: './deposit.component.html',
    styleUrls: [
        './deposit.component.scss'
    ]
})
export class DepositComponent
{
    @Input() accountStore: CustomStore;
    @Input()
    set selectedAccount(account: Account)
    {
        this.depositData.account = account.id;
    }

    depositData: {account?: string, amount?: number} = {};

    constructor(
        private readonly transactionService: TransactionService
    )
    {}

    public onDeposit(args: any): void
    {
        this.accountStore.byKey(args.account).then((account) => {

            return this.transactionService.performTransaction(
                Deposit.intoAccount(
                    account,
                    args.amount)).toPromise();
        });
    }
}
