import { Injectable } from '@angular/core';
import { NodesApiService, ContentService } from '@alfresco/adf-core';
import { Observable } from 'rxjs';
import { NodePaging } from '@alfresco/js-api';

@Injectable({
  providedIn: 'root'
})
export class GcServiceService {
  constructor(
    private nodesApi: NodesApiService,
    private contentService: ContentService
  ) {}

  public getChildren(): Observable<NodePaging> {
    return this.nodesApi.getNodeChildren(
      'dfce9cd3-7c51-4c39-ad10-19ccb2cd4ba8'
    );
  }

  public getContent(nodeId: string) {
    return this.contentService.getNodeContent(nodeId);
  }
}
