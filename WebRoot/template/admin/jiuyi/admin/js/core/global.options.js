/**
说明：global.js中应当存放公用控件，工程（web，crm，admin）之间的差异化配置在此声明。
**/
(function (){
    var conf = {
        'treeselector.dataMap': '',
        'peopleselector.source': '/search/u.action?decorator=json&confirm=true&s=m&pageNo=1',
        'peopleselector.departsUrl': '/json/sns_privatemsg/all-departs.action',
        'peopleselector.departsUserUrl': '/json/sns_privatemsg/depart-user.action'
    };
    window.globalOptions = function(key){
        return conf[key];
    };
})();