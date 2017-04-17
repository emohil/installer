<div class="row">
  <div class="col-md-8">
    <uib-pagination direction-links="true" boundary-links="true"
      total-items="pager.totalItems" items-per-page="pager.itemsPerPage"
      max-size="pager.maxSize" ng-model="pager.currentPage"
      previous-text="&lsaquo;" next-text="&rsaquo;" first-text="&laquo;"
      last-text="&raquo;"></uib-pagination>
  </div>
  <div class="col-md-4 pagination">
    <div class="pull-left text-middle">第</div>
    <div class="col-md-3 pull-left">
      <input type="text" class="form-control"
        ng-model="pager.currentPage">
    </div>
    <div class="pull-left text-middle">页，共 {{pager.totalItems}}条记录</div>
  </div>
</div>