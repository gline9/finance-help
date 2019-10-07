import { Subscription } from 'rxjs';
import { Component } from '@angular/core';
import { PersistService } from '../open-file/persist.service';
import { ElectronService } from 'ngx-electron';

@Component({
    selector: 'app-account-overview',
    templateUrl: './account-overview.component.html',
    styleUrls: [
        './account-overview.component.scss'
    ]
})
export class AccountOverviewComponent
{
    constructor(
        private readonly persistService: PersistService,
        private readonly electronService: ElectronService
    )
    {}

    save(): void
    {
        if (this.persistService.currentFileName == null)
        {
            this.electronService.remote.dialog.showSaveDialog({
                filters: [{
                    name: 'json',
                    extensions: ['json']
                }
            ]}).then((value) => {
                if (value.canceled)
                {
                    return;
                }

                this.persistService.saveFileAs(value.filePath).subscribe(() => {});
            });
        }
        else
        {
            this.persistService.saveFile().subscribe(() => {});
        }
    }

}
