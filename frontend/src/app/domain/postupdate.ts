export class PostUpdate {

    groupId: number;
    subscriptionId: number;
    uri: string;
    read: boolean;
    bookmarked: boolean;

    constructor(groupId: number, subscriptionId: number, uri: string) {
        this.groupId = groupId;
        this.subscriptionId = subscriptionId;
        this.uri = uri;
    }

}
