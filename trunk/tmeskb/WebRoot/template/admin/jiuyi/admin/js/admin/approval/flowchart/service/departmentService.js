(function() {

    window.departmentService = {};
    // [{
    //   "id": 10401,
    //   "parent": "#",
    //   "pinyin": "quangongsi",
    //   "text": "全公司"
    // }]
    var departInfo = {};
    departmentService.loadUser = function(tree) {
        if (!tree) return;
        departmentService.appendDepartInfo(tree);
    };
    departmentService.appendDepartInfo = function(json) {
        if (!json) return;
        if ($.isArray(json)) {
            for (var i = 0; i < json.length; i++) {
                var depart = json[i];
                var id = depart.id + '';
                appendDepartInfo(id, depart);
            }
        } else {
            for (id in json) {
                appendDepartInfo(id, json[id]);
            }
        }
    };
    var appendDepartInfo = function(id, depart) {
        if (departInfo[id]) {
            $.extend(true, departInfo[id], depart);
        } else {
            departInfo[id] = depart;
        }
    };
    departmentService.hasUser = function(id) {
        return !!departInfo[id];
    };
    departmentService.getDepartInfo = function(id) {
        id = id + '';
        var info = departInfo[id];
        return info;
    };
    departmentService.getAllDeparts = function() {
        var arr = [];
        for(id in departInfo){
            arr.push(departInfo[id]);
        }
        return arr;
    };
})();
