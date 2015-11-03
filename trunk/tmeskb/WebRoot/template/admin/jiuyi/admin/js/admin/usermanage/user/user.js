$register("ingage.admin.user");
ingage.admin.user.validateUser = function() {
    $.validity.start();
    $("#userName").require();
    $("#phone").match("phone");
    $("#passport").match("email");
    $(".nc_date").match("date");
    return $.validity.end().valid;
};
ingage.admin.user.initDate = function() {
    var currentYear = new Date().getFullYear();
    var rangeStart = currentYear - 50;
    var rangeEnd = currentYear + 10;
    var yearRange = rangeStart + ":" + rangeEnd;
    $(".nc_date").datepicker({
        changeMonth: true,
        changeYear: true,
        yearRange: yearRange
    });
    //    $(".nc_date").datepicker({ changeMonth: true,changeYear: true});
};
ingage.admin.user.initlist = function() {
	 return false; //weitao modify
    var url = pageContextPath + "/user-manage/search-user.action?decorator=gridJson&confirm=true";
    var postData = {};
    var treeObj = $.fn.zTree.getZTreeObj("ingageTree");
    var nodes = treeObj.getSelectedNodes();
    if (nodes != null && nodes.length > 0 && nodes[0].getParentNode() != null) {
        postData['depart.id'] = nodes[0].id;
    } else {
        postData['depart.id'] = ""
    }
    var userGrid = $("#listGrid").jqGrid({
        datatype: "json",
        url: url,
        mtype: 'post',
        height: 300,
        autowidth: true,
        shrinkToFit: false,
        colModel: [
            //                    {name:'employeeCode',index:'employeeCode',label:'员工编号', sortable:false},
            {
                name: 'userName',
                index: 'userName',
                label: '姓名',
                width: 80,
                sortable: false
            }, {
                name: 'personalEmail',
                index: 'personalEmail',
                label: '邮箱',
                width: 200,
                sortable: false
            }, {
                name: 'phone',
                index: 'phone',
                label: '手机�?',
                width: 100,
                sortable: false
            }, {
                name: 'depart',
                index: 'depart',
                label: '部门',
                width: 80,
                sortable: false
            }, {
                name: 'post',
                index: 'post',
                label: '职位',
                width: 80,
                sortable: false
            }, {
                name: 'manager',
                index: 'manager',
                label: '直属上级',
                width: 60,
                sortable: false
            }, {
                name: 'userRoles',
                index: 'userRoles',
                label: '角色',
                width: 80,
                sortable: false
            }, {
                name: 'license',
                index: 'license',
                sortable: false,
                width: 120,
                label: '授权'
            }, {
                name: 'status',
                index: 'status',
                sortable: false,
                label: '状�?',
                width: 50,
                formatter: 'select',
                editoptions: {
                    value: "-1,未创�?;0:未激�?;1:已激�?;-10:禁用;-11:离职"
                }
            }, {
                name: 'gender',
                index: 'gender',
                label: '性别',
                width: 30,
                sortable: false,
                edittype: 'select',
                formatter: 'select',
                editoptions: {
                    value: "1:�?;2:�?"
                }
            }, {
                name: 'joinAt',
                index: 'joinAt',
                label: '入职日期',
                width: 80,
                sortable: false
            }, {
                name: 'birthday',
                index: 'birthday',
                label: '出生日期',
                width: 80,
                sortable: false
            }, {
                name: 'lastLoginAt',
                index: 'lastLoginAt',
                label: '最近登录时�?',
                width: 120,
                sortable: false
            }
        ],
        pager: 'listGridPager',
        rowNum: 20,
        viewrecords: true,
        rowList: [20, 50, 100],
        jsonReader: {
            userData: 'userData',
            total: "pageCount",
            rows: "pageSize",
            page: "pageNo",
            records: "dataCount",
            root: "gridData",
            cell: "datacell"
        },
        loadComplete: function(data) {
            if ($(this).getGridParam('reccount') <= 6) {
                $(this).setGridHeight(330);
            } else {
                $(this).setGridHeight("100%");
            }
            $("#groupAdmins_span").html(data.userData['departAdmins']);
        },
        gridComplete: function() {
            var ids = $(this).jqGrid('getDataIDs');
            for (var i = 0; i < ids.length; i++) {
                var id = ids[i];
                var val = $(this).getCell(id, "status");
                if (val == -11 || val == -10) {
                    $("#" + id).css("text-decoration", "line-through");
                }
            }
            ingage.common.tip($(".verify_yes[title]"), "bottom center", "top left", {
                x: 7,
                y: -2
            });
        },
        postData: postData,
        prmNames: {
            page: "pagination.pageNo",
            rows: "pagination.pageSize"
        },
        multiselect: true
    });
    userGrid.jqGrid('navGrid', '#listGridPager', {
        edit: false,
        add: false,
        del: false,
        search: false
    });
};
ingage.admin.user.openUserCreateWin = function(id) {
    var url = "admin!add.action?departid="+id;
    $.openPopupLayer({
        width: 650,
        url: url
    });
};
ingage.admin.user.openUserUpdateWin = function(id) {
    var url = "admin!edit.action?id="+id;
    $.openPopupLayer({
        width: 650,
        url: url
    });
};
ingage.admin.user.openAssignRolesWin = function(ids) {
    var url = pageContextPath + "/user-manage/toAssignRoles.action?userIds=" + ids.toString();
    $.openPopupLayer({
        width: 535,
        url: url
    });
};

ingage.admin.user.isUserName = function(name) {
    return /^[0-9a-zA-Z\u4e00-\u9fa5_-]+$/.test(name);
};

ingage.admin.user.saveUser = function() {
    $("#btnCreateUser").click(function() {
        var err = false;
        if (!ingage.admin.user.validateUser()) {
            err = true;
        }

        var item = $("input[name='account_radio']:checked").val();
        if ( item == 1 && $("#passport").val() == '') {
            ingage.common.errorResult("登录邮箱为必填项");
            $('#passport_msg').html('                     *');
            $("#phone").match("phone");
            err = true;
        }
        if ( item == 2 && $("#phone").val() == '') {
            ingage.common.errorResult("登录手机为必填项");
            $('#phone_msg').html('                     *');
            $("#passport").match("email");
            err = true;
        }

        if (!ingage.admin.user.isUserName($.trim($("#userName").val()))) {
            $("#userName_msg").html("姓名只能为中英文、数字以及常用字�?");
            err = true;
        }

        if ($("#managerText").val() == "") {
            $("#managerId").val("");
        }

        var departId = $('#js-depart-main').singletreeselector('val');
        if (!departId) {
            $("#depart_msg").html("部门为必填项�?");
            err = true;
        }

        $.map($('#js-depart-related').multitreeselector('val'), function(o) {
            return o.value;
        }).join(',');

        var dimDepart = {
            'type': '1',
            'main': departId && departId.value,
            'related': $.map($('#js-depart-related').multitreeselector('val'), function(o) {
                return o.value;
            }).join(',')
        };

        $.map($('#js-colleague-depart-related').multitreeselector('val'), function(o) {
            return o.value;
        }).join(',');

        var colleagueDepart = {
            'type': '111',
            'related': $.map($('#js-colleague-depart-related').multitreeselector('val'), function(o) {
                return o.value;
            }).join(',')
        };

        var dimArray = [];

        dimArray.push(dimDepart);
        dimArray.push(colleagueDepart);

        $('.js-dim-main').each(function() {
            var $elem = $(this);
            var dimId = $elem.singletreeselector('val');
            if(dimId){
                dimArray.push({
                    'type': $elem.attr('dim'),
                    'main': dimId && dimId.value
                });
            }else{
                dimArray.push({
                    'type': $elem.attr('dim')
                });
            }
        });
        //console.dir(dimArray);

        $('.js-dim-related').each(function() {
            var $elem = $(this);
            var dimIds = $.map($elem.multitreeselector('val'), function(o) {
                return o.value;
            }).join(',')

            $.each(dimArray, function(i, arr) {
                if (arr.type == $elem.attr('dim')) {
                    arr.related = dimIds;
                }
            })

        });

        if (err) {
            return false;
        }

        $('#dimensions').val(JSON.stringify(dimArray));
        $('#departId').val(departId.value)
        var options = {
            success: function(responseText) {
                ingage.common.dealResponse(responseText, function(status, statusText) {
                    if (status == 0) {
                        ingage.common.successResult("用户创建成功");
                        $.closePopupLayer("popup_layer");
                        $("#status-select option:first").attr("selected", "selected");
                        $("#searchUser_a").trigger("click");
                    } else {
                        ingage.common.errorResult(statusText)
                    }
                    if ($("#listGrid").length > 0) {
                        $("#listGrid").trigger("reloadGrid");
                    }
                });
            }
        };
        $('#createUserForm').ajaxSubmit(options);
    });
};

ingage.admin.user.disableUser = function(id) {
    var url = pageContextPath + "/user-manage/disable-user.action?decorator=ajaxformat&confirm=true";
    $.post(
        url,
        'userId=' + id,
        function(responseText) {
            ingage.common.dealModelWinResponse(responseText)
        }
    )
};
ingage.admin.user.enableUser = function(id) {
    var url = pageContextPath + "/user-manage/enable-user.action?decorator=ajaxformat&confirm=true";
    $.post(
        url, {
            userId: id
        },
        function(responseText) {
            ingage.common.dealModelWinResponse(responseText)
        }
    )
};
ingage.admin.user.updateUser = function(arDim) {
    $("#btnUpdateUser").click(function() {
        if ($("#managerText").val() == "") {
            $("#managerId").val("");
        }

        var err = false;
        if (!ingage.admin.user.validateUser()) {
            err = true;
        }
        if (!ingage.admin.user.isUserName($.trim($("#userName").val()))) {
            $("#userName_msg").html("姓名只能为中英文、数字以及常用字�?");
            err = true;
        }
        var departId = $('#js-depart-main').singletreeselector('val');
        if (!departId) {
            $("#depart_msg").html("部门为必填项�?");
            err = true;
        }
        // // 部门
        // public static final Short DEPARTMENT = 1;
        // // 产品�?
        // public static final Short PRODUCT = 2;
        // // 区域
        // public static final Short AREA = 3;
        // // 行业
        // public static final Short INDUSTRY = 4;
        // // 业务
        // public static final Short BUSINESS = 5;

        $.map($('#js-depart-related').multitreeselector('val'), function(o) {
            return o.value;
        }).join(',');

        var dimArray = $.map(arDim, function(o) {
            if(o.type == 111){
                return {
                    type: String(o.type),
                    related: $.map($('input.js-dim-related' + String(o.type)).multitreeselector('val'), function(r) {
                        return r.value;
                    }).join(',')
                }
            }
            var type = String(o.type),
                $main = $('input.js-dim-main' + type),
                val = $main.singletreeselector('val'),
                main = val ? val.value : null,
                related = $.map($('input.js-dim-related' + type).multitreeselector('val'), function(r) {
                    return r.value;
                }).join(',');

            if (type == 1) {
                if (!main) {
                    err = true;
                    $("#span_dimension_" + type).html($main.attr('title') + "为必填项�?");
                } else {
                    $('#departId').val(main);
                }
            }
            return {
                type: String(o.type),
                main: main,
                related: related
            };
        });

        if (err) {
            return false;
        }
        $('#dimensions').val(JSON.stringify(dimArray));

        var options = {
            success: ingage.common.dealModelWinResponse
        };
        $('#updateUserForm').ajaxSubmit(options);
    });
};
ingage.admin.user.autocomplete = function() {
    $("#managerText").autocomplete({
        minLength: 0,
        source: function(request, response) {
            var key = $.trim(request.term);
            $.postJson(pageContextPath + "/search/search-active-user.action", {
                pageNo: 1,
                key: key
            }).done(function(data) {
                    var id;
                    if (data.length > 0) {
                        $.each(data, function(i, d) {
                            if (d.value == key) {
                                id = d.id;
                            }
                        });
                    }
                    id ? $("#managerId").val(id) : $("#managerId").val('');
                    response(data);
                });
        },
        select: function(event, ui) {
            var id = ui.item.id;
            $("#managerId").val(id);
        }
    }).data("ui-autocomplete")._renderItem = function(ul, item) {
        return $("<li>").append("<a>" + item.label + "</a>").appendTo(ul);
    };


};
ingage.admin.user.addMember = function() {
    $("#btnAddDir").click(function() {
        var userId = $("#managerId").val();
        if (userId != "" && userId != null) {
            $.post(pageContextPath + "/user-manage/create-member.action?decorator=ajaxformat&confirm=true", {
                "userId": userId,
                "groupId": $("#groupId").val()
            }, function(responseText) {
                ingage.common.dealResponse(responseText, function(status, statusText, title, view) {
                    if (status != 0) {
                        ingage.common.errorResult(statusText);
                    } else {
                        var viewobj = $.parseJSON(view);
                        var optionVal = viewobj.userName + "&lt" + viewobj.passport + "&gt";
                        $("#memberlist").append("<option value='" + viewobj.id + "'>" + optionVal + "</option>");
                        $("#managerId,#managerText").val("");
                        $("#managerText").select();
                    }
                })
            });
        }
    });
};
ingage.admin.user.delMember = function() {
    $("#btnDeleteDir").click(function() {
        var selectOption = $("#memberlist").find("option:selected");
        var arr = $.makeArray(selectOption);
        var textArr = new Array();
        $.each(arr, function() {
            textArr.push($.trim($(this).text()));
        });
        ingage.common.confirm("是否要删除：" + textArr, function() {
            $.each(selectOption, function() {
                var op = $(this);
                $.post(pageContextPath + "/user-manage/delete-member.action?decorator=ajaxformat&confirm=true", {
                    "userId": op.val(),
                    "groupId": $("#groupId").val()
                }, function(responseText) {
                    ingage.common.dealResponse(responseText, function(status, statusText) {
                        if (status != 0) {
                            ingage.common.errorResult(statusText);
                        } else {
                            op.remove();
                        }
                    })
                });
            });
        });
    });
};
ingage.admin.user.cancelAdmin = function(adminId, obj) {
    var departIdVal = $("#departId_hidden").val();
    if (!departIdVal) {
        return false;
    }
    ingage.common.confirm("是否要取消该用户的部门管理员权限", function() {
        $.post(pageContextPath + "/user-manage/cancel-admin.action?decorator=ajaxformat&confirm=true", {
            "userId": adminId,
            "depart.id": departIdVal
        }, function(responseText) {
            ingage.common.dealResponse(responseText, function(status, statusText) {
                if (status != 0) {
                    ingage.common.errorResult(statusText);
                } else {
                    ingage.common.successResult("取消管理员成�?");
                    $(obj).parents("li:first").remove();
                }
            })
        });
    });
};
ingage.admin.user.lrselect = function() {
    $(function() {
        $.fn.LRSelect("roleSelect", "prepareRoleSelect", "add", "remove");
    });
};
ingage.admin.user.assignRole = function(userIds) {
    $("#btnAssignRole").click(function() {
        var val = "";
        $("#prepareRoleSelect option").each(function() {
            val += $(this).val() + ",";
        });
        val = val.substring(0, val.length - 1);
        var url = pageContextPath + "/user-manage/assign-role.action?decorator=ajaxformat&confirm=true";
        var btnLoading = ingage.common.btnLoading({
            loadingEle: $("#btnAssignRole")
        });
        btnLoading.start();
        $.post(
            url, {
                "roleIds": val,
                "userIds": userIds
            },
            function(responseText) {
                btnLoading.stop();
                ingage.common.dealModelWinResponse(responseText)
            }
        );
    });
};
ingage.admin.user.openAssignLicenseWin = function(userIds) {
    var url = pageContextPath + "/user-manage/to-assign-license.action";
    $.openPopupLayer({
        width: 535,
        parameters: {
            decorator: "popups",
            confirm: "true",
            "userIds": userIds.toString()
        },
        url: url
    });
};
ingage.admin.user.assignLicense = function(userIds, licenseIds) {
    var url = pageContextPath + "/user-manage/assign-license.action?decorator=ajaxformat&confirm=true";
    var sendNoticeMail = true;
    if (!$("#sendNoticeMail_checkbox").attr("checked")) {
        sendNoticeMail = false;
    }
    var btnLoading = ingage.common.btnLoading({
        loadingEle: $("#btnAssignRole")
    });
    btnLoading.start();
    $.post(
        url, {
            "userIds": userIds.toString(),
            licenseIds: licenseIds,
            sendNoticeMail: sendNoticeMail
        },
        function(responseText) {
            btnLoading.stop();
            ingage.common.dealModelWinResponse(responseText)
        }
    );
};
ingage.admin.user.assignAdmin = function() {
    $("#assignAdminBtn").click(function() {
        var departIdVal = $("#departId_hidden").val();
        if (departIdVal == "") {
            return false;
        }
        var ids = $("#listGrid").jqGrid('getGridParam', 'selarrrow');
        var treeObj = $.fn.zTree.getZTreeObj("ingageTree");
        var node = treeObj.getSelectedNodes()[0];
        if (ids.length > 1) {
            ingage.common.confirm("是否将多个用户设置为�?" + node.name + "】的管理�??", function() {
                assignAdmins(ids, departIdVal);
            });
        } else if (ids.length == 1) {
            ingage.common.confirm("是否将此用户设置为�?" + node.name + "】的管理�??", function() {
                assignAdmins(ids, departIdVal);
            });
        } else {
            ingage.common.errorResult("请选择一个用户设置为�?" + node.name + "】的管理�?");
        }
    });

    function assignAdmins(adminIds, departIdVal) {
        var url = pageContextPath + "/user-manage/assign-admin.action?decorator=ajaxformat&confirm=true";
        $.post(url, {
            userIds: adminIds.toString(),
            'depart.Id': departIdVal
        }, function(responseText) {
            ingage.common.dealModelWinResponse(responseText);
        })
    }
};
ingage.admin.user.departure = function(userId) {
    var url = pageContextPath + "/user-manage/departure-user.action?decorator=ajaxformat&confirm=true";
    $.post(
        url, {
            "userId": userId
        },
        function(responseText) {
            ingage.common.dealModelWinResponse(responseText)
        }
    );
};
ingage.admin.user.initStatusSelect = function() {
    $("#status-select").change(function() {
        var val = $(this).val();
        if (val == '-10') {
            $("#userEnableBtn").show();
            $("#userDisableBtn").hide();
            $("#userDeleteBtn").hide();
        } else if (val == '-11') {
            $("#userEnableBtn").show();
            $("#userDisableBtn").hide();
            $("#userDeleteBtn").hide();
        } else {
            $("#userEnableBtn").hide();
            $("#userDisableBtn").show();
            $("#userDeleteBtn").show();
        }
        $("#searchUser_input").val("").focus().blur(); //重置按姓名搜�?;
        var url = pageContextPath + "/user-manage/search-user.action?decorator=gridJson&confirm=true";
        var postData = {};
        if (val != "") {
            postData['uStatus'] = val;
        } else {
            postData['uStatus'] = "";
        }
        var treeObj = $.fn.zTree.getZTreeObj("ingageTree");
        var nodes = treeObj.getSelectedNodes();
        if (nodes != null && nodes.length > 0) {
            postData['depart.id'] = nodes[0].id;
        } else {
            postData['depart.id'] = ""
        }
        postData['innerUser.name'] = "";
        $("#listGrid").jqGrid('setGridParam', {
            url: url
        });
        $("#listGrid").jqGrid('setGridParam', {
            'page': 1
        });
        $("#listGrid").setGridParam({
            postData: postData
        });
        $("#listGrid").trigger("reloadGrid");
    });
};
ingage.admin.user.searchUserName = function() {
    $("#searchUser_a").click(function() {
        var val = $("#searchUser_input").val();
        var url = pageContextPath + "/user-manage/search-user.action?decorator=gridJson&confirm=true";
        var postData = {};
        if ($.trim(val) != "") {
            postData['innerUser.name'] = val;
        } else {
            postData['innerUser.name'] = "";
        }
        if ($("#status-select").val() != "") {
            postData['uStatus'] = $("#status-select").val();
        } else {
            postData['uStatus'] = "";
        }
        var treeObj = $.fn.zTree.getZTreeObj("ingageTree");
        var nodes = treeObj.getSelectedNodes();
        if (nodes != null && nodes.length > 0) {
            postData['depart.id'] = nodes[0].id;
        } else {
            postData['depart.id'] = "";
        }
        $("#listGrid").jqGrid('setGridParam', {
            url: url
        });
        $("#listGrid").jqGrid('setGridParam', {
            'page': 1
        });
        $("#listGrid").setGridParam({
            postData: postData
        });
        $("#listGrid").trigger("reloadGrid");
    });
};