<#include "/include/common.ftl" /> <#include "/include/common-entry.ftl"
/>
<script src="static/scripts/demo/typehead.js"></script>

</head>
<body>
  <#include "/include/top.ftl" />

  <div id="section">
    <#include "/include/left.ftl" />

    <div class="right">

      <form name="form1" class="form-horizontal"
        ng-controller="ApartyController" novalidate>
        <div class="panel panel-default">
          <div class="panel-heading">Typehead Demo</div>
          <div class="panel-body">
            <div class="form-group col-md-6">
              <label class="control-label">甲方：</label> <input
                type="text" ng-model="apartyIdDisp" class="form-control"
                uib-typeahead="pair as pair.text for pair in doTypeaheadApartys($viewValue)"
                typeahead-on-select="onTypeaheadApartySelect($item, $model, $label)"
                typeahead-min-length="2" typeahead-loading="loadingTest"
                typeahead-no-results="noResults"> <i
                ng-show="loadingTest"
                class="glyphicon glyphicon-refresh"></i>
              <div ng-show="noResults">
                <i class="glyphicon glyphicon-remove"></i>没有找到相关信息
              </div>
              <label>对应的ID值： {{apartyId}}</label>

            </div>
          </div>
        </div>
      </form>
    </div>
  </div>
</body>
</html>
