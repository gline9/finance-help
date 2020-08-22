import { AppModule } from './../app.module';
import { Observable } from 'rxjs';
import { APP_URL } from './../app.service';
import { HttpClient } from '@angular/common/http';
import { Injectable, Inject } from '@angular/core';
import { map } from 'rxjs/operators';
import { Account } from '../account-grid/accounts';

@Injectable({providedIn: 'root'})
export class TransactionService
{
    private readonly DEPOSIT_URL = this.url + '/transactions/deposit';
    private readonly WITHDRAWAL_URL = this.url + '/transactions/withdrawal';
    private readonly TRANSFER_URL = this.url + '/transactions/transfer';

    constructor(
        private readonly httpClient: HttpClient,
        @Inject(APP_URL) private readonly url: string
    )
    {}

    public performTransaction(transaction: Transaction): Observable<void>
    {
        transaction.performClientSide();

        switch (transaction.transactionType)
        {
            case 'deposit':
                return this.httpClient.post(this.DEPOSIT_URL, {
                    amount: transaction.amount,
                    id: transaction.account.id
                }).pipe(map(() => {}));

            case 'withdrawal':
                return this.httpClient.post(this.WITHDRAWAL_URL, {
                    amount: transaction.amount,
                    id: transaction.account.id
                }).pipe(map(() => {}));

            case 'transfer':
                return this.httpClient.post(this.TRANSFER_URL, {
                    from: transaction.from.id,
                    to: transaction.to.id,
                    amount: transaction.amount
                }).pipe(map(() => {}));
        }
    }

}

export type Transaction = Deposit | Withdrawal | Transfer;

export class Deposit
{
    readonly transactionType = 'deposit';

    private constructor(
        readonly account: Account,
        readonly amount: number)
    {}

    public static intoAccount(account: Account, amount: number)
    {
        return new Deposit(account, amount);
    }

    public performClientSide(): void
    {
        this.account.balance += this.amount;
    }
}

export class Withdrawal
{
    readonly transactionType = 'withdrawal';

    private constructor(
        readonly account: Account,
        readonly amount: number)
    {}

    public static fromAccount(account: Account, amount: number)
    {
        return new Withdrawal(account, amount);
    }

    public performClientSide(): void
    {
        this.account.balance -= this.amount;
    }
}

export class Transfer
{
    readonly transactionType = 'transfer';

    private constructor(
        readonly from: Account,
        readonly to: Account,
        readonly amount: number)
    {}

    public static betweenAccounts(from: Account, to: Account, amount: number)
    {
        return new Transfer(from, to, amount);
    }

    public performClientSide(): void
    {
        this.from.balance -= this.amount;
        this.to.balance += this.amount;
    }

}
