import { Injectable } from '@angular/core';
import { Group } from '../model/group';

@Injectable({
    providedIn: 'root'
})
export class GroupStore {

    groups: Group[] = [];

    constructor() { }

    public isEmpty(): boolean {
        return this.groups.length === 0;
    }

    public setGroups(groups: Group[]): void {
        groups.forEach(group => this.init(group));
        this.groups = groups;
    }

    public addGroup(group: Group): void {
        this.init(group);
        this.groups.push(group);
    }

    public getLastGroup(): Group {
        if (this.isEmpty()) {
            return undefined;
        }
        return this.groups[this.groups.length - 1];
    }

    private init(group: Group): void {
        group.collapsed = true;
    }

}
