(function() {

    window.rk_userService = {
        DEFAULT_USER_ICON: window.DEFAULTS.USER_DEFAULT_ICON
    };

    var userInfo = {};
    if (window.SESSION && window.SESSION.user) {
        var currentId = window.SESSION.user.id;
        userInfo[currentId + ''] = $.extend(true, {}, window.SESSION.user);
    }
    rk_userService.loadUser = function(data) {
        if (!data.data || !data.data.users) return;
        rk_userService.appendUserInfo(data.data.users);
    };
    rk_userService.appendUserInfo = function(json) {
        if (!json) return;
        if ($.isArray(json)) {
            for (var i = 0; i < json.length; i++) {
                var user = json[i];
                var id = user.id + '';
                appendUserInfo(id, user);
            }
        } else {
            for (id in json) {
                appendUserInfo(id, json[id]);
            }
        }
    };
    var appendUserInfo = function(id, user) {
        if (userInfo[id]) {
            $.extend(true, userInfo[id], user);
        } else {
            userInfo[id] = user;
        }
    };
    rk_userService.hasUser = function(id) {
        return !!userInfo[id];
    };
    rk_userService.getUserInfo = function(id) {
        id = id + '';
        var info = userInfo[id];
        var ctxPath = '';
        if (info) {
            if (!info.icon) info.icon = rk_userService.DEFAULT_USER_ICON;
            return info;
        } else if (id) { //有id，但users中没过来信息
            return {
                icon: rk_userService.DEFAULT_USER_ICON
            };
        }
    };
    rk_userService.getDefaultFace = function() {
        var ctxPath = '';
        return ctxPath + rk_userService.DEFAULT_USER_ICON;
    };
    rk_userService.getAllUsers = function() {
        return $.extend(true, {}, userInfo);
    };
})();
