import { Injectable } from "@angular/core";

@Injectable({
    providedIn: 'root'
})
export class SelectionService {

    isNonDefault(selection: string): boolean {
        return selection === 'all' || selection === 'bookmarked';
    }

    getOrDefault(selection: string): string {
        return this.isNonDefault(selection) ? selection : 'unread';
    }

}
