
<div class="row">
	<div class="col-xs-12 ceshi">
		<form class="form-horizontal" id="searchform"
			action="admin!ajlist.action" role="form">
			<div class="operateBar">
				<div class="form-group">
					<label class="col-sm-2 col-md-offset-1" style="text-align:right">部门:</label>
					<div class="col-sm-3">
						<input type="text" name="productGroupCode"
							class="input input-sm form-control" value=""
							id="form-field-icon-1">
					</div>
					<label class="col-sm-2" style="text-align:right">名称:</label>
					<div class="col-sm-3">
						<input type="text" name="adminName"
							class="input input-sm form-control" value=""
							id="form-field-icon-1">
					</div>
				</div>
				<div class="form-group" style="text-align:center">
					<a id="searchButton"
						class="btn btn-white btn-default btn-sm btn-round"> <i
						class="ace-icon fa fa-filter blue"></i> 搜索 </a>
				</div>

			</div>
		</form>
		
		<table id="grid-table2"></table>

		<div id="grid-pager"></div>
	</div>
</div>
<script type="text/javascript" src="${base} /template/admin/js/browser/adminBrowser.js"></script>