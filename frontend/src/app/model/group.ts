import { Subscription } from './subscription';

export class Group {

    id: number;
    name: string;
    collapsed = true;
    subscriptions: Subscription[] = [];

    constructor(name: string) {
        this.name = name;
    }

}
