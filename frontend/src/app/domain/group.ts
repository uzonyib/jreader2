import { Subscription } from './subscription';

export class Group {

    id: number;
    name: string;
    subscriptions: Subscription[];

    constructor(name: string) {
        this.name = name;
    }

}