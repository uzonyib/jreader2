import { Injectable } from '@angular/core';
import { Group } from '../model/group';

@Injectable({
    providedIn: 'root'
})
export class GroupStore {

    groups: Group[] = [];

    constructor() { }

    public setGroups(groups: Group[]): void {
        groups.forEach(group => this.init(group));
        this.groups = groups;
    }

    public addGroup(group: Group): void {
        this.init(group);
        this.groups.push(group);
    }

    private init(group: Group): void {
        group.collapsed = true;
    }

}
