import { Component, Input, Output, EventEmitter } from '@angular/core';
import { TransactionService, Withdrawal, Transfer } from '../transactions';
import CustomStore from 'devextreme/data/custom_store';

@Component({
    selector: 'app-transfer',
    templateUrl: './transfer.component.html',
    styleUrls: [
        './transfer.component.scss'
    ]
})
export class TransferComponent
{
    @Input() accountStore: CustomStore;
    @Output() transferSuccessful = new EventEmitter<undefined>();

    transferData: {fromAccount?: string, toAccount?: string, amount?: number} = {};

    constructor(
        private readonly transactionService: TransactionService
    )
    {}

    public onTransfer(): void
    {
        this.accountStore.byKey(this.transferData.fromAccount).then((fromAccount) => {
            return this.accountStore.byKey(this.transferData.toAccount).then((toAccount) => {
                return this.transactionService.performTransaction(
                    Transfer.betweenAccounts(fromAccount,
                        toAccount,
                        this.transferData.amount)).toPromise();
            });
        }).then(() => {
            this.transferSuccessful.emit();
        });
    }
}
