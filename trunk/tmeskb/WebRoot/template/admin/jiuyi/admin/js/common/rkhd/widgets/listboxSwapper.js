/****
使用说明：
1，AJAX取值：
    $('.swapper').listboxSwapper({
        leftLabel: '左侧字段',
        rightLabel: '右侧字段',
        source: '/js/common/rkhd/widgets/test.json'
    });
  
1.1 AJAX数据结构：
        {
          "status": 0,
          "statusText": "??????",
          "data": {
            "items": [
               {"label": '标题0', value: 0}, 
               {"label": '标题1', value: 1}, 
               {"label": '标题2', value: 2}, 
               {"label": '标题3', value: 3}, 
               {"label": '标题4', value: 4}
            ],
            "values":[
               {value: 2}, 
               {value: 3}
            ]}
        }
1.2 非ajax方式，自定义数组
  var sourceArr = [
               {"label": '标题0', value: 0}, 
               {"label": '标题1', value: 1}, 
               {"label": '标题2', value: 2}, 
               {"label": '标题3', value: 3}, 
               {"label": '标题4', value: 4}
            ]
  var valueArr = [
               {value: 2}, 
               {value: 3}
            ]

  $('.swapper').listboxSwapper({
        leftLabel: '左侧字段',
        rightLabel: '右侧字段',
        source: source,
        value: value
  });

2.1，取值
   var array = $('.swapper').listboxSwapper('val'); //return [{value:0},{value:1},{value:2}]
2.2，赋值
   $('.swapper').listboxSwapper('val', [{value:0},{value:1},{value:2}]);

****/
$.widget('zhl.listboxSwapper', {
    options: {
        width: '100%',
        height: '100%',
        source: [],//左侧列表数据，URL or Array
        value: [],//右侧列表数据
        leftLabel: '',//左侧列表标题
        rightLabel: ''//右侧列表标题
    },
    currentSource: [],
    _create: function(){
        var me = this,
            elem = me.element,
            opt = me.options;

            me._render();
            me._initEvents();
            me._initData();
    },
    _init: function(){

    },
    _render: function(){
        var me = this, elem = me.element, opt = me.options;

            var html =  '<ul class="word_list" side="left">'
                       +    '<li class="title">'+opt.leftLabel+'</li>'
                       +    '<li class="list_box">'
                       +          '<div class="left_listbox listbox_select">'
                       +               '<ul class="left_ul sortable"></ul>'
                       +           '</div>'
                       +     '</li>'
                       +'</ul>'
                       +'<ul class="move_button">'
                       +     '<li><a title="右移" class="right_button" href="javascript:;">&gt;</a></li>'
                       +     '<li><a title="左移" class="left_button" href="javascript:;">&lt;</a></li>'
                       + '</ul>'
                       + '<ul class="word_list_show" side="right">'
                       +       '<li class="title">'+opt.rightLabel+'</li>'
                       +       '<li class="list_box js-listbox-visible">'
                       +             '<div class="right_listbox">'
                       +                 '<ul class="right_ul sortable"></ul>'
                       +             '</div>'
                       +       '</li>'
                       +'</ul>'
                       +'<ul class="sort_button">'
                       +       '<li><a class="up_button" href="javascript:;"></a></li>'
                       +       '<li><a class="down_button" href="javascript:;"></a></li>'
                       +'</ul>'

            elem.html( html );

            me.leftUl = elem.find('.word_list ul.left_ul');
            me.rightUl = elem.find('.right_listbox ul.right_ul');
            me.ltrBtn = elem.find('.move_button .right_button');
            me.rtlBtn = elem.find('.move_button .left_button');
            me.upBtn = elem.find('.sort_button .up_button');
            me.downBtn = elem.find('.sort_button .down_button');

            me.leftUl.css('min-height', '100%');
            me.rightUl.css('min-height', '100%');
    },
    _initData: function(){
        var me = this, elem = me.element, opt = me.options;
        var itemsHtml = '';
        var isAjax = false;
        var ajaxConfig = {};
        if(typeof opt.source == 'string'){
            isAjax = true;
            ajaxConfig = {
                url: opt.source
            }
        }else
        if(opt.source && opt.source.url){
            isAjax = true;
            ajaxConfig = opt.source;
        }
        if(isAjax){
            me._loadData(ajaxConfig, function(source, value){
                me._renderSource(source, value);
            });
        }else{
            me._renderSource(opt.source, opt.value);
        }
    },
    _loadData: function(config, callback){
        var me = this, elem = me.element, opt = me.options;
            $.ajax({
                url: config.url,
                data: config.data,
                //dataType: 'json',
                type:'POST',
            }).success(function(data) {
                if(data){
                    eval('data = ' + data);
                }
                if(data && data.status === 0 && data.data){
                    var items = data.data.items;
                    var value = data.data.values;
                    (callback)(items, value);
                }
            }).error(function() {}).complete(function() {});
    },
    _renderSource: function(source, value){
        var me = this;
            me.currentSource = source;
            me._drawLeft(source);
            me._drawRight(value);
            //me._setValue(value);
    },
    _initEvents: function(){
        var me = this, elem = me.element, opt = me.options;
        //dnd
        elem.find('.word_list ul.left_ul, .right_listbox ul.right_ul').sortable({
            placeholder: "placeholder",
            connectWith: ".sortable",
            opacity: 0.7,
            sort: function( event, ui ){
                me._disSelectAll();
            },
            stop: function(event, ui){
                me._selectLi(ui.item);
            }
        })
        .disableSelection()
        .delegate('li', 'click', function(){
            var li = $(this);
            me._selectLi(li);
        })
        .delegate('li', 'dblclick', function(){
            var li = $(this);
            var ul = li.parent();
            var targetUl = (li.parents('ul[side]').attr('side')=='left' ? elem.find('ul[side="right"]') : elem.find('ul[side="left"]'));
            targetUl = targetUl.find('ul');
            me._horizontalMove(li, targetUl);
        })
        me.ltrBtn.click(function(){ me._ltr(); });
        me.rtlBtn.click(function(){ me._rtl(); });
        me.upBtn.click(function(){ me._goUp(); });
        me.downBtn.click(function(){ me._goDown(); });
    },
    _selectLi: function(li){
        li.addClass('selected');
        li.siblings().removeClass('selected');
        //
        var ulSelClass = 'listbox_select';
        var ul = li.parent();
        var div = ul.parent();
        div.addClass(ulSelClass);
        var pul = div.parents('ul[side]');
        var side = pul.attr('side');
        var otherSideName = (side=='left'?'right':'left');
        var otherSideUl = pul.siblings('ul[side="'+otherSideName+'"]');
        otherSideUl.find('div.'+otherSideName+'_listbox').removeClass(ulSelClass)
    },
    _disSelectLi: function(li){
        li.removeClass('selected');
    },
    _disSelectUl: function(ul){
        ul.find('li').removeClass('selected');
    },
    _disSelectAll: function(){
        var me = this;
        me._disSelectUl(me.leftUl);
        me._disSelectUl(me.rightUl);
    },
    _ltr: function(){
        var me = this;
        var li = me.leftUl.find('li.selected');
        me._horizontalMove(li, me.rightUl);
    },
    _rtl: function(){
        var me = this;
        var li = me.rightUl.find('li.selected');
        me._horizontalMove(li, me.leftUl);
    },
    _horizontalMove: function(li, toUl){
        if(li.size() == 0)return;
        var me = this, elem = me.element, opt = me.options;        
        var targetLi = (toUl.find('li.selected').size() > 0 ? toUl.find('li.selected') : toUl.find('li:last'));
        if(toUl.find('li').size() > 0){
            li.insertAfter(targetLi);
            //alert(li.html())
        }else{
            li.appendTo(toUl);
        }
        me._selectLi(li);
        toUl.parent().scrollTop(li.position().top);
    },
    _goUp: function(){
        var me = this, elem = me.element, opt = me.options;
        me._verticalMove(true);
    },
    _goDown: function(){
        var me = this, elem = me.element, opt = me.options;
        me._verticalMove(false);
    },
    _verticalMove: function(goUp){
        var me = this, elem = me.element, opt = me.options;
        var selectedLi = me.rightUl.find('li.selected');
        if(selectedLi.size() == 0) return;
        var brotherLi = selectedLi[(goUp ? 'prev' : 'next')]();
        if(brotherLi.size() == 0) return;
        selectedLi[(goUp ? 'insertBefore' : 'insertAfter')](brotherLi);
    },
    val: function(array){
        var me = this;
        if(typeof array == 'undefined')return me._getValue();
        me._setValue(array);
    },
    _getValue: function(){
        var me = this, elem = me.element, opt = me.options;
        var lis = me.rightUl.find('li');
        var results = [];
        lis.each(function(i, li){
            results.push({value: $(li).attr('val')});
        });
        return results;
    },
    _setValue: function(valueArray){
        if(!$.isArray(valueArray))valueArray = [valueArray];
        var me = this;
        var currentSource = me.currentSource;
        var lis = me.rightUl.find('li');
        
        var rightArray = [];
        var leftArray = [];

        $.each(valueArray, function(i, item){
            var srcItem = _.find(currentSource, function(obj){return (obj.value+'') == (item.value+''); });
            if(srcItem)
            rightArray.push(srcItem);            
        });
        var valueMap = [];
        for(var i = 0;i<valueArray.length;i++){
            var val = valueArray[i];
            valueMap[val.value+''] = val;
        }
        for(var i = 0;i<currentSource.length;i++){
            var item = currentSource[i];
            if(!valueMap[item.value+''])leftArray.push(item);
        }
        me._drawLeft(leftArray);
        me._drawRight(rightArray);
    },
    _drawLeft: function(arr){
        var me = this;
        me._drawList(arr, true);
    },
    _drawRight: function(arr){
        var me = this;
        me._drawList(arr, false);
    },
    _drawList: function(arr, isLeft){
        var me = this, elem = me.element, opt = me.options;
        var html = '';
        for(var i = 0; i< arr.length; i++){
            var item = arr[i];
            html = html + '<li val="'+item.value+'"><span>'+item.label+'</span></li>';
        }
        me[(isLeft ? 'leftUl' : 'rightUl')].html('').html(html);
    }
});