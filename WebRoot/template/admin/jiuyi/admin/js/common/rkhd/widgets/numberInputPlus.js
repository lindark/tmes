    (function(){
        var MAX_CURRENCY = 99999999999;
        var MAX_NUMBER = 10000000000000000000/1000;//如果按系统默认的最大值，js会丢失后三位精度，因此除以1000

        var FLOAT = 'float';
        var INTEGER = 'integer';
        var ASCII_0 = 48;
        var ASCII_9 = 57;
        var ASCII_ZERO = 96;
        var ASCII_NINE = 105;
        var ASCII_DOT1 = 110;
        var ASCII_DOT2 = 190;
        $.widget('rk.numberInputPlus', {
            options:{
                 type: FLOAT//float, integer
                ,resolution: 5
                ,isCurrency: false
            },
                _create: function(){
                    var me = this;
                    var elem = me.element;
                },
                getValue: function(){
                    var me = this;
                    var elem = me.element;
                    var val = elem.val();
                    if(me.options.isCurrency && val){
                        var string = numeral().unformat(val);
                        return string;
                    }else{
                        return val;
                    }
                },
                setValue: function(val){
                    var me = this;
                    var elem = me.element;
                    if(val){
                        val = numeral(val).value();
                    }
                    elem.val(val);
                    if(me.options.isCurrency){
                        me._displayText();
                    }
                },
                _displayText: function(){
                    var me = this;
                    var elem = me.element;
                    //格式化
                    var val = elem.val();
                    var formator = '0,0';
                    if(val && val.indexOf('.')>=0)
                    if(me.options.type != INTEGER && me.options.resolution){
                        var tailNum = val.split('.')[1];
                        if(tailNum){
                            formator = formator + '.';
                            for(var i = 0, len = tailNum.length; i < len; i++){
                                formator = formator + '0';//小数点后几位
                            }
                        }
                    }
                    if(val){
                        var string = numeral(val).format(formator);
                        //format方法会造成小数点后过多的数字，该处代码是为了消掉这些数字
                        if(me.options.resolution && string && string.indexOf('.')){
                            var arr = string.split('.');
                            var tailNum = arr[1];
                            if(tailNum){
                                string = arr[0] + '.' + tailNum.substring(0, me.options.resolution)
                            }
                        }
                        elem.val(string);
                    }
                },
                beforeValue: null,
                _init: function(){
                    var me = this;
                    var elem = me.element;
                    if(me.options.resolution === 0){//小数点位数为0等价于整数
                        me.options.type = INTEGER;
                    }
                    var type = me.options.type;
                    elem
                    .focusin(function(){
                        var val = elem.val();
                        if(me.options.isCurrency){
                            if(val){
                                var string = numeral().unformat(val);
                                elem.val(string);
                            }
                        }
                    })
                    .focusout(function(){
                        var val = elem.val();
                        if(me.options.isCurrency){
                            if(!isNaN(val)){
                                var num = parseFloat(val);
                                if(num > MAX_CURRENCY){
                                    elem.val(MAX_CURRENCY);
                                }
                            }
                            me._displayText();
                        }
                    })
                    .keydown(function(e){
                        var keycode = e.keyCode;
                        var val = elem.val();
                        var v = numeral(val).value();
                        if(v < MAX_NUMBER)
                            me.beforeValue = val;
                        if(type == INTEGER){
                            if(keycode == ASCII_DOT1 || keycode == ASCII_DOT2) return false;//key: dot
                        }
                        if(type == FLOAT){
                            //if((keycode >= ASCII_0 && keycode <= ASCII_9) || (keycode >= ASCII_ZERO && keycode <= ASCII_NINE))//数字
                        }
                        if(val && val.indexOf('.')>=0){
                            if(keycode == ASCII_DOT1 || keycode == ASCII_DOT2) return false;//不允许重复输入小数点
                        }
                    }).keyup(function(e){
                        var keycode = e.keyCode;
                        var val = elem.val();
                        //消掉非法字符，比如重复的小数点，字母等
                        if(val && isNaN(val)){
                            var arr = val.split('.');
                            if(arr.length > 2){
                                var newVal = '';
                                for(var i = 0; i < arr.length;i++){
                                    newVal = newVal + (i==1?'.':'') + arr[i];
                                }
                                val = newVal;
                            }
                            val = numeral().unformat(val);
                            elem.val(val);
                        }
                        var val = elem.val();
                        if(type == INTEGER){
                            if(val && val.indexOf('.')>=0){//消掉漏网的小数点
                                var arr = val.split('.');
                                var newVal = '';
                                for(var i = 0; i < arr.length;i++){
                                    newVal = newVal + arr[i];
                                }
                                val = newVal;
                                elem.val(val);
                            }
                        }
                        if(type == FLOAT){
                            if(me.options.resolution !== null && val && val.indexOf('.')){
                                var leftNum = val.split('.')[0];
                                var rightNum = val.split('.')[1];
                                if(rightNum && rightNum.length > me.options.resolution){
                                    var newright = rightNum.substring(0, me.options.resolution);
                                    elem.val(leftNum+'.'+newright);
                                }
                            }
                        }
                        if(!me.options.isCurrency){
                             var val = elem.val();
                        //     __alert(val)
                             if(numeral(val).value() > MAX_NUMBER){
                                 elem.val(me.beforeValue);
                             }
                        }
                    });
                }
        });
    })();