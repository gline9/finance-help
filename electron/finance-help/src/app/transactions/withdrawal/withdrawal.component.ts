import { Component, Input, Output, EventEmitter } from '@angular/core';
import { TransactionService, Withdrawal } from '../transactions';
import CustomStore from 'devextreme/data/custom_store';

@Component({
    selector: 'app-withdrawal',
    templateUrl: './withdrawal.component.html',
    styleUrls: [
        './withdrawal.component.scss'
    ]
})
export class WithdrawalComponent
{
    @Input() accountStore: CustomStore;
    @Input()
    set selectedAccount(account: Account)
    {
        this.withdrawalData.account = account && account.id;
    }

    @Output() withdrawalSuccessful = new EventEmitter<undefined>();

    withdrawalData: {account?: string, amount?: number} = {};

    constructor(
        private readonly transactionService: TransactionService
    )
    {}

    public onWithdrawal(): void
    {
        this.accountStore.byKey(this.withdrawalData.account).then((account) => {

            return this.transactionService.performTransaction(
                Withdrawal.fromAccount(
                    account,
                    this.withdrawalData.amount)).toPromise();
        }).then(() => {
            this.withdrawalSuccessful.emit();
        });
    }
}
