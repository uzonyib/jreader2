export class PostFilter {

    groupId: number;
    subscriptionId: number;
    sort: string;

    constructor(groupId: number, subscriptionId: number, sort: string) {
        this.groupId = groupId;
        this.subscriptionId = subscriptionId;
        this.sort = sort;
    }

}