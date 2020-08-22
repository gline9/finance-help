import { Component, Input, Output, EventEmitter } from '@angular/core';
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
        this.depositData.account = account && account.id;
    }

    @Output() depositSuccessful = new EventEmitter<undefined>();

    depositData: {account?: string, amount?: number} = {};

    constructor(
        private readonly transactionService: TransactionService
    )
    {}

    public onDeposit(): void
    {
        this.accountStore.byKey(this.depositData.account).then((account) => {

            return this.transactionService.performTransaction(
                Deposit.intoAccount(
                    account,
                    this.depositData.amount)).toPromise();
        }).then(() => {
            this.depositSuccessful.emit();
        });
    }
}
