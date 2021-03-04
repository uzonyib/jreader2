export class Subscription {

    id: number;
    groupId: number;
    url: string;
    name: string;
    rank: number;

    constructor(groupId: number, url: string, name: string) {
        this.groupId = groupId;
        this.url = url;
        this.name = name;
    }

}
