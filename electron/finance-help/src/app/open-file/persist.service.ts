import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({providedIn: 'root'})
export class PersistService
{

    private readonly URL = 'http://localhost:5150/persist';
    public currentFileName: string;

    constructor(
        private httpClient: HttpClient
    )
    {}

    public loadFile(file: string): Observable<void>
    {
        this.currentFileName = file;
        return this.performCommand(file, 'LOAD');
    }

    public saveFile(): Observable<void>
    {
        return this.performCommand(this.currentFileName, 'SAVE');
    }

    public saveFileAs(file: string): Observable<void>
    {
        this.currentFileName = file;
        return this.saveFile();
    }

    private performCommand(fileName: string, commandType: 'LOAD' | 'SAVE'): Observable<void>
    {
        return this.httpClient.post(this.URL, {commandType, fileName}).pipe(map(body => null));
    }

}
