$register("ingage.common.validator");

ingage.common.validator.isDate = function (str){
    //格式：yyyy-mm-dd
    var cnDateF = /^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29))$/;
    //格式：dd/mm/yyyy
    var enDateF = /^(((0?[1-9]|[12]\d|3[01])\/(0?[13578]|1[02])\/((1[6-9]|[2-9]\d)\d{2}))|((0?[1-9]|[12]\d|30)\/(0?[13456789]|1[012])\/((1[6-9]|[2-9]\d)\d{2}))|((0?[1-9]|1\d|2[0-8])\/0?2\/((1[6-9]|[2-9]\d)\d{2}))|(29-0?2\/((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$/;
    return cnDateF.test(str) || enDateF.test(str);
};

ingage.common.validator.isDigit = function (str){
    var digitF = /^[0-9]*$/;
    return digitF.test(str);
};

//参数resolution为小数点后的位数
ingage.common.validator.isFloat = function (str,resolution){
    var floatF;
    var num = parseInt(resolution);
    switch(num){
        case 1:
            floatF = /^[0-9]+(.[0-9]{0,1})?$/;
            break;
        case 2:
            floatF = /^[0-9]+(.[0-9]{0,2})?$/;
            break;
        case 3:
            floatF = /^[0-9]+(.[0-9]{0,3})?$/;
            break;
        case 4:
            floatF = /^[0-9]+(.[0-9]{0,4})?$/;
            break;
        case 5:
            floatF = /^[0-9]+(.[0-9]{0,5})?$/;
            break;
        default:
            floatF = /^[0-9]+(.[0-9]{0,5})?$/;
    }
    return floatF.test(str);
};
