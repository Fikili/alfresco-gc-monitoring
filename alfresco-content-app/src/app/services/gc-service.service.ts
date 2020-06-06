import { Injectable } from '@angular/core';
import { NodesApiService, ContentService } from '@alfresco/adf-core';
import { Observable, forkJoin } from 'rxjs';
import { concatMap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class GcServiceService {
  constructor(
    private nodesApi: NodesApiService,
    private contentService: ContentService
  ) {}

  public getChildrenContent(): Observable<any[]> {
    return this.nodesApi
      .getNodeChildren('dfce9cd3-7c51-4c39-ad10-19ccb2cd4ba8')
      .pipe(
        concatMap(children => {
          const nodesData: Array<Observable<any>> = new Array<
            Observable<any>
          >();
          children.list.entries.forEach(element => {
            const nodeId = element.entry.id;
            const nodeContent = this.getContent(nodeId);
            nodesData.push(nodeContent);
          });
          return forkJoin(nodesData);
        })
      );
  }

  public getContent(nodeId: string) {
    return this.contentService.getNodeContent(nodeId);
  }
}
