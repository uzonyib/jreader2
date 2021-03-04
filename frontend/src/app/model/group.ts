import { Subscription } from './subscription';

export class Group {

    id: number;
    name: string;
    rank: number;
    subscriptions: Subscription[] = [];
    collapsed = true;

}
