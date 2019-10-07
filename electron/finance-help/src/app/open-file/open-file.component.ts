import { ElectronService } from 'ngx-electron';
import { Component } from '@angular/core';
import { PersistService } from './persist.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-open-file',
    templateUrl: './open-file.component.html',
    styleUrls: [
        './open-file.component.scss'
    ]
})
export class OpenFileComponent
{
    fileName: string;

    constructor(
        private readonly electronService: ElectronService,
        private readonly persistService: PersistService,
        private readonly router: Router
    )
    {}

    findFile = () =>
    {
        this.electronService.remote.dialog.showOpenDialog({
            properties: ['openFile'],
            filters: [
                {
                    name: 'json',
                    extensions: [
                        'json'
                    ]
                }
            ]
        }).then(
            (value) => {

                if (value.filePaths.length === 0)
                {
                    return;
                }

                this.fileName = value.filePaths[0];
            }
        );
    }

    openFile(): void
    {
        this.persistService.loadFile(this.fileName).subscribe(
            () => {
                this.router.navigate(['/account-overview']);
            }
        );
    }

    newFile(): void
    {
        this.router.navigate(['/account-overview']);
    }
}
