export class PostFilter {

    groupId: number;
    subscriptionId: number;
    selection: string;
    sort: string;

    constructor(groupId: number, subscriptionId: number, selection: string, sort: string) {
        this.groupId = groupId;
        this.subscriptionId = subscriptionId;
        this.selection = selection;
        this.sort = sort;
    }

}
