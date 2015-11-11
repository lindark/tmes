jQuery(function($) {
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";
	// resize to fit page size
	$(window).on('resize.jqGrid', function() {
		$(grid_selector).jqGrid('setGridWidth', $(".page-content").width());
	})
	// resize on sidebar collapse/expand
	var parent_column = $(grid_selector).closest('[class*="col-"]');
	$(document).on(
			'settings.ace.jqGrid',
			function(ev, event_name, collapsed) {
				if (event_name === 'sidebar_collapsed'
						|| event_name === 'main_container_fixed') {
					// setTimeout is for webkit only to give time for DOM
					// changes and then redraw!!!
					setTimeout(function() {
						$(grid_selector).jqGrid('setGridWidth',
								parent_column.width());
					}, 0);
				}
			})

	// jQuery(grid_selector).jqGrid({
	// onSelectRow: function(id){
	// if(id && id!==lastSel){
	// jQuery('pickAmount').restoreRow(lastSel);
	// lastSel=id;
	// }
	// jQuery(grid_selector).editRow(id, true);
	// }
	//    	
	// });

	jQuery(grid_selector)
			.jqGrid(
					{

						url : "pick_detail!ajlist.action",
						datatype : "local",
						height : "250",// weitao 修改此参数可以修改表格的高度
						jsonReader : {
							repeatitems : false,
							root : "list",
							id:"id",
						// total:"pageCount",
						// records:"totalCount"
						},

						colNames : [ 'ID','组件编码', '组件名称', '库存数量', '领/退料数量', '操作' ],
						colModel : [ {name : 'ID',
							index : 'ID',
							width : 150,
							editable : true,
							hidden:true,
							editoptions : {
								size : "20",
								maxlength : "30"
							}
						},{
							name : 'materialCode',
							index : 'materialCode',
							width : 150,
							editable : true,
							editoptions : {
								size : "20",
								maxlength : "30"
							}
						}, {
							name : 'materialName',
							index : 'materialName',
							width : 200,
							editable : true,
							editoptions : {
								size : "20",
								maxlength : "30"
							}
						}, {
							name : 'stockAmount',
							index : 'materialName',
							width : 200,
							editable : true,
							editoptions : {
								size : "20",
								maxlength : "30"
							}
						}, {
							name : 'pickAmount',
							index : 'pickAmount',
							width : 200,
							editable : true,
							editoptions : {
								size : "20",
								maxlength : "30"
							}
						}, {
							name : 'pickType',
							index : 'pickType',
							width : 80,
							editable : true,
							edittype : "checkbox",
							editoptions : {
								value : "Yes:No"
							},
							unformat : aceSwitch
						} ],

						viewrecords : true,
						rowNum : "all",
						// rowList:[10,20,30],
						// pager : pager_selector,
						altRows : true,
						// toppager: true,

						multiselect : true,
						// multikey: "ctrlKey",
						multiboxonly : true,

						gridComplete : function() {
							var ids = jQuery(grid_selector)
									.jqGrid('getDataIDs');
							for ( var i = 0; i < ids.length; i++) {
								var cl = ids[i];
								be = "<input  type='input' value='' name='pt'/>"
								br = "<label><select name='rbg'><option value='1'>领料</option><option value='2'>退料</option></select><label>";
								jQuery(grid_selector).jqGrid('setRowData',
										ids[i], {
											pickAmount : be
										});
								jQuery(grid_selector).jqGrid('setRowData',
										ids[i], {
											pickType : br 
										});
							}
						},

						loadComplete : function() {
							var table = this;
							setTimeout(function() {
								styleCheckbox(table);

								updateActionIcons(table);
								updatePagerIcons(table);
								enableTooltips(table);
							}, 0);
						},

						editurl : "pick_detail!delete.action",// 用它做标准删除动作
						caption : "领/退料单"

					});
	$(window).triggerHandler('resize.jqGrid');// trigger window resize to make
												// the grid get the correct size

	// navButtons
	jQuery(grid_selector).jqGrid(
			'navGrid',
			pager_selector,
			{ // navbar options
				edit : true,
				editicon : 'ace-icon fa fa-pencil blue',
				// add: true,
				addfunc : function(rowId) {
					window.location.href = "pick_detail!add.action";
				},
				addicon : 'ace-icon fa fa-plus-circle purple',
				del : true,
				delicon : 'ace-icon fa fa-trash-o red',
				search : true,
				searchicon : 'ace-icon fa fa-search orange',
				refresh : true,
				refreshicon : 'ace-icon fa fa-refresh green',
				view : true,
				viewicon : 'ace-icon fa fa-search-plus grey',
			},
			{
				// edit record form
				// closeAfterEdit: true,
				// width: 700,
				recreateForm : true,
				beforeShowForm : function(e) {
					var form = $(e[0]);
					form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar')
							.wrapInner('<div class="widget-header" />')
					style_edit_form(form);
				}
			},
			{
				// new record form
				// width: 700,
				closeAfterAdd : true,
				recreateForm : true,
				viewPagerButtons : false,
				beforeShowForm : function(e) {
					var form = $(e[0]);
					form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar')
							.wrapInner('<div class="widget-header" />')
					style_edit_form(form);

				}
			},
			{
				// delete record form
				recreateForm : true,
				beforeShowForm : function(e) {
					var form = $(e[0]);
					if (form.data('styled'))
						return false;

					form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar')
							.wrapInner('<div class="widget-header" />')
					style_delete_form(form);

					form.data('styled', true);
				},
				onClick : function(e) {
					alert(1);
				}
			},
			{
				// search form
				recreateForm : true,
				afterShowSearch : function(e) {
					var form = $(e[0]);
					form.closest('.ui-jqdialog').find('.ui-jqdialog-title')
							.wrap('<div class="widget-header" />')
					style_search_form(form);
				},
				afterRedraw : function() {
					style_search_filters($(this));
				},
				multipleSearch : true,

				multipleGroup : false,
				showQuery : true

			},
			{
				// view record form
				recreateForm : true,
				beforeShowForm : function(e) {
					var form = $(e[0]);
					form.closest('.ui-jqdialog').find('.ui-jqdialog-title')
							.wrap('<div class="widget-header" />')
				}
			})

	// function style_edit_form(form) {
	// alert(1);
	// form.find('input[name=sdate]').datepicker({format:'yyyy-mm-dd' ,
	// autoclose:true})
	// .end().find('input[name=pickType]')
	// .addClass('ace ace-switch ace-switch-5').after('<span
	// class="lbl"></span>');
	// var buttons = form.next().find('.EditButton .fm-button');
	// buttons.addClass('btn btn-sm').find('[class*="-icon"]').hide();//ui-icon,
	// s-icon
	// buttons.eq(0).addClass('btn-primary').prepend('<i class="ace-icon fa
	// fa-check"></i>');
	// buttons.eq(1).prepend('<i class="ace-icon fa fa-times"></i>')
	//		
	// buttons = form.next().find('.navButton a');
	// buttons.find('.ui-icon').hide();
	// buttons.eq(0).append('<i class="ace-icon fa fa-chevron-left"></i>');
	// buttons.eq(1).append('<i class="ace-icon fa fa-chevron-right"></i>');
	// }

	$(function() {
		$("#btnSubmit").click(function() {
			// var val=$('input:radio[name="rbg"]:checked').val();
			// var val1=$("input[name='pt']").val()
			// if(val==null){
			// alert("请选择类型!");
			// return false;
			// }
			// else{
			// alert(val);
			// $.ajax({
			// url:"pick_detail!addAmount.action",
			// dataType:"json",
			// async:false,
			// success:function(data){
			// alert("SUCCESS");
			// }
			// });
			//
			// }
//			$("#grid-table tr").each(function(element) {
//				var text = $(this).attr("id");
//				alert(text);
//				$.ajax({
//					url : "pick_detail!addAmount.action?text=",
//					dataType : "json",
//					async : false,
//					success : function(data) {
//						alert("SUCCESS");
//					}
//				});
//			});	
			

			
			$("#grid-table tr").each(function(element) {
				var text = $(this).attr("id");
				alert(text);				
			  });	
                 $.ajax({
					url : "pick_detail!addAmount.action?text=",
					dataType : "json",
					async : false,					
					success : function(data) {
						alert("SUCCESS");
					}
				});

			
			
			alert($("#inputform").serialize());
			//alert($("#inputform").attr("action"));
			$.ajax({
				url : $("#inputform").attr("action"),
				data:$("#inputform").serialize(),
				dataType : "json",
				async : false,
				success : function(data) {
					alert("SUCCESS");
				}
			});

		});
	});

});