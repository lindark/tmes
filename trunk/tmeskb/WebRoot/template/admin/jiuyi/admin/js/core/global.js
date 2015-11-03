/**
依赖于： global.config.js
**/
var isMac = navigator.platform.toUpperCase().indexOf('MAC') >= 0;

$(function() {
    $.ui.autocomplete.prototype.options.appendTo = 'body';
    if ($.datepicker) {
        $.datepicker.setDefaults && $.datepicker.setDefaults($.datepicker.regional["zh-CN"]);
        $.datepicker._defaults.changeMonth = true;
        $.datepicker._defaults.changeYear = true;
    }

    $.timepicker && $.timepicker.setDefaults && $.timepicker.setDefaults($.timepicker.regional['zh']);
});


$.widget('mxx.formbean', {
    options: {},
    _create: function() {
        var $this = this,
            $elem = this.element,
            opt = this.options;
    },
    _getElements: function() {
        return this.element.find('[name]:not(param)');
    },
    _has: function(name) {
        return this._filter(name).size();
    },
    _filter: function(name) {
        return this._getElements().filter('[name="' + name + '"]');
    },
    get: function(name) {
        var $this = this,
            $elem = this.element,
            opt = this.options;
        var $element = $this._filter(name),
            value;
        if ($element.data('datepicker')) {
            value = $element.datepicker('getDate');
        } else if ($element.is(':hidden') && $element.next().is('ul.holder')) { //fcbkcomplete
            value = $this._filter(name).val();
        } else if ($element.data('uiAutocomplete')) { //autocomplete
            value = $element.data('uiAutocomplete').selectedItem;
        } else if ($element.data('selectmenu')) {
            value = $element.val();
        } else if ($element.is(':radio')) {
            value = $element.filter(':checked').val();
        } else if ($element.is(':checkbox')) {
            if ($element.size() > 1) {
                value = $element.filter(':checked').map(function() {
                    return this.checked;
                }).get();
            } else {
                value = $element.get(0).checked;
            }
        } else if($element.data('wtype') == 'snsmultipeopleselector'){
            value = $element.snsmultipeopleselector('val');
        } else {
            value = $element[$element.is(':input') ? 'val' : 'text']();
        }
        return value;
    },
    set: function(name, value) {
        var $this = this,
            $elem = this.element,
            opt = this.options;
        var $element = $this._filter(name);
        if ($element.data('datepicker')) {
            $element.datepicker('setDate', value);
            var onSelect = $element.datepicker('option', 'onSelect');
            $.isFunction(onSelect) && onSelect();
        } else if ($element.is(':hidden') && $element.next().is('ul.holder')) { //fcbkcomplete
            if ($.isArray(value) && value.length) {
                $.each(value, function(i, item) {
                    $this._filter(name).triggerHandler('addItem', item);
                });
            } else {

            }
        } else if ($element.data('uiAutocomplete')) { //uiAutocomplete
            var instance = $element.data('uiAutocomplete');
            if (value) {
                instance._value(value.label);
                instance.term = instance._value();
                instance.selectedItem = value;
            } else {
                instance._value('');
                instance.term = '';
                instance.selectedItem = null;
            }
        } else if ($element.data('selectmenu')) {
            $element.val(value);
            $element.selectmenu('refresh');
        } else if ($element.is(':radio')) {
            $element.filter('value=["' + value + '"]').trigger('click');
        } else if ($element.is(':checkbox')) {
            if ($.isArray(value) && value.length) {
                $.each(value, function(i, item) {
                    $element.eq(i).prop('checked', item).triggerHandler('click');
                });
            } else {
                $element.prop('checked', !!value).triggerHandler('click');
            }
        } else if($element.data('wtype') == 'snsmultipeopleselector'){
            if(value && value.length > 0){
                $element.snsmultipeopleselector('val', value);
            }
        } else {
            $element[$element.is(':input') ? 'val' : 'text'](value).triggerHandler('change');
        }
    },
    getAll: function() {
        var $this = this,
            $elem = this.element,
            opt = this.options;
        var oRslt = {};
        $this._getElements().each(function() {
            var $element = $(this),
                name = $element.attr('name');
            oRslt[name] = $this.get(name);
        });
        return oRslt;
    },
    setAll: function(oVal) {
        var $this = this,
            $elem = this.element,
            opt = this.options;
        $.each(oVal, function(name, value) {
            $this.set(name, value);
        });
    }
});

$.widget('mxx.loading', {
    options: {
        html: '处理中…',
        delayHtml: '稍后再试'
    },
    _create: function() {},
    _init: function() {
        var $this = this,
            $elem = this.element,
            opt = this.options;
        $this.$ghost = $elem.clone().addClass('disable').removeAttr('act').html(opt.html).insertAfter($elem.hide());
        $this.$ghost.on('click', function(evt) {
            return false;
        });
    },
    end: function() {
        var $this = this,
            $elem = this.element,
            opt = this.options;
        $this.$ghost && $this.$ghost.remove();
        $elem.show();
    },
    delay: function(html) {
        var $this = this,
            $elem = this.element,
            opt = this.options;
        $this.$ghost && $this.$ghost.html(html || opt.delayHtml);
        this._delay(function() {
            $this.end();
        }, 5000);
    }
});
/*! http://mths.be/placeholder v2.0.7 by @mathias */
(function(window, document, $) {

    // Opera Mini v7 doesn’t support placeholder although its DOM seems to indicate so
    var isOperaMini = Object.prototype.toString.call(window.operamini) == '[object OperaMini]';
    var isInputSupported = 'placeholder' in document.createElement('input') && !isOperaMini;
    var isTextareaSupported = 'placeholder' in document.createElement('textarea') && !isOperaMini;
    var prototype = $.fn;
    var valHooks = $.valHooks;
    var propHooks = $.propHooks;
    var hooks;
    var placeholder;

    if (isInputSupported && isTextareaSupported) {

        placeholder = prototype.placeholder = function() {
            return this;
        };

        placeholder.input = placeholder.textarea = true;

    } else {

        placeholder = prototype.placeholder = function() {
            var $this = this;
            $this
                .filter((isInputSupported ? 'textarea' : ':input') + '[placeholder]')
                .not('.placeholder')
                .bind({
                    'focus.placeholder': clearPlaceholder,
                    'blur.placeholder': setPlaceholder
                })
                .data('placeholder-enabled', true)
                .trigger('blur.placeholder');
            return $this;
        };

        placeholder.input = isInputSupported;
        placeholder.textarea = isTextareaSupported;

        hooks = {
            'get': function(element) {
                var $element = $(element);

                var $passwordInput = $element.data('placeholder-password');
                if ($passwordInput) {
                    return $passwordInput[0].value;
                }

                return $element.data('placeholder-enabled') && $element.hasClass('placeholder') ? '' : element.value;
            },
            'set': function(element, value) {
                var $element = $(element);

                var $passwordInput = $element.data('placeholder-password');
                if ($passwordInput) {
                    return $passwordInput[0].value = value;
                }

                if (!$element.data('placeholder-enabled')) {
                    return element.value = value;
                }
                if (value == '') {
                    element.value = value;
                    // Issue #56: Setting the placeholder causes problems if the element continues to have focus.
                    if (element != safeActiveElement()) {
                        // We can't use `triggerHandler` here because of dummy text/password inputs :(
                        setPlaceholder.call(element);
                    }
                } else if ($element.hasClass('placeholder')) {
                    clearPlaceholder.call(element, true, value) || (element.value = value);
                } else {
                    element.value = value;
                }
                // `set` can not return `undefined`; see http://jsapi.info/jquery/1.7.1/val#L2363
                return $element;
            }
        };

        if (!isInputSupported) {
            valHooks.input = hooks;
            propHooks.value = hooks;
        }
        if (!isTextareaSupported) {
            valHooks.textarea = hooks;
            propHooks.value = hooks;
        }

        $(function() {
            // Look for forms
            $(document).delegate('form', 'submit.placeholder', function() {
                // Clear the placeholder values so they don't get submitted
                var $inputs = $('.placeholder', this).each(clearPlaceholder);
                setTimeout(function() {
                    $inputs.each(setPlaceholder);
                }, 10);
            });
        });

        // Clear placeholder values upon page reload
        $(window).bind('beforeunload.placeholder', function() {
            $('.placeholder').each(function() {
                this.value = '';
            });
        });

    }

    function args(elem) {
        // Return an object of element attributes
        var newAttrs = {};
        var rinlinejQuery = /^jQuery\d+$/;
        $.each(elem.attributes, function(i, attr) {
            if (attr.specified && !rinlinejQuery.test(attr.name)) {
                newAttrs[attr.name] = attr.value;
            }
        });
        return newAttrs;
    }

    function clearPlaceholder(event, value) {
        var input = this;
        var $input = $(input);
        if (input.value == $input.attr('placeholder') && $input.hasClass('placeholder')) {
            if ($input.data('placeholder-password')) {
                $input = $input.hide().next().show().attr('id', $input.removeAttr('id').data('placeholder-id'));
                // If `clearPlaceholder` was called from `$.valHooks.input.set`
                if (event === true) {
                    return $input[0].value = value;
                }
                $input.focus();
            } else {
                input.value = '';
                $input.removeClass('placeholder');
                input == safeActiveElement() && input.select();
            }
        }
    }

    function setPlaceholder() {
        var $replacement;
        var input = this;
        var $input = $(input);
        var id = this.id;
        if (input.value == '') {
            if (input.type == 'password') {
                if (!$input.data('placeholder-textinput')) {
                    try {
                        $replacement = $input.clone().attr({
                            'type': 'text'
                        });
                    } catch (e) {
                        $replacement = $('<input>').attr($.extend(args(this), {
                            'type': 'text'
                        }));
                    }
                    $replacement
                        .removeAttr('name')
                        .data({
                            'placeholder-password': $input,
                            'placeholder-id': id
                        })
                        .bind('focus.placeholder', clearPlaceholder);
                    $input
                        .data({
                            'placeholder-textinput': $replacement,
                            'placeholder-id': id
                        })
                        .before($replacement);
                }
                $input = $input.removeAttr('id').hide().prev().attr('id', id).show();
                // Note: `$input[0] != input` now!
            }
            $input.addClass('placeholder');
            $input[0].value = $input.attr('placeholder');
        } else {
            $input.removeClass('placeholder');
        }
    }

    function safeActiveElement() {
        // Avoid IE9 `document.activeElement` of death
        // https://github.com/mathiasbynens/jquery-placeholder/pull/99
        try {
            return document.activeElement;
        } catch (err) {}
    }

}(this, document, jQuery));

$.widget('mxx.ellipsis', {
    options: {},
    _create: function() {
        var $this = this,
            $elem = this.element,
            opt = this.options;
        var style = {
                overflow: $elem.css('overflow'),
                width: $elem.width(),
                height: $elem.height()
            },
            html = $elem.attr('title');
        $elem.css('overflow', 'hidden');
        var ch = $elem.prop('offsetHeight'),
            sh = $elem.prop('scrollHeight');
        if (ch < sh) {
            while (ch < sh) {
                html = html.slice(0, Math.ceil(html.length * ch / sh));
                $elem.html(html.replace(/\n/g, '<br>'));
                ch = $elem.prop('offsetHeight'), sh = $elem.prop('scrollHeight');
            }
            html = $.trim(html).replace(/[\S\s]{3}$/, '...');
            $elem.html(html.replace(/\n/g, '<br>'));
        } else {
            $elem.css(style);
        }
    }
});

$.widget('rk.videoPopup', {
    options: {},
    _create: function() {
        var $this = this,
            $elem = this.element,
            opt = this.options;
        var arConfig = [{
            name: '手机端培训录制',
            url: 'http://www.jiuyi.com/bksp/sjdpxlz.wmv',
            pic: 'video_demo1.png'
        }, {
            name: 'CRM模块使用指导',
            url: 'http://www.jiuyi.com/bksp/crmmksyzd.wmv',
            pic: 'video_demo2.png'
        }, {
            name: '办公协作模块使用指导',
            url: 'http://www.jiuyi.com/bksp/bgxzmksyzd.wmv',
            pic: 'video_demo3.png'
        }, {
            name: '系统管理员使用指导',
            url: 'http://www.jiuyi.com/bksp/xtglysyzd.wmv',
            pic: 'video_demo4.png'
        }, {
            name: '销售管理者使用指导',
            url: 'http://www.jiuyi.com/bksp/xsglzsyzd.wmv',
            pic: 'video_demo5.png'
        }];
        var tmplLi = '<li><a href="{url}" target="_blank"><img src="../img/{pic}"><span class="cover"></span><p>{name}</p></a></li>';
        var htmlLis = $.format(tmplLi, arConfig);
        var tmplHtml = '<div class="pop_up" style="border-radius:0px;"><div class="pop_content clear"><ul class="pop_list video_list">' + htmlLis + '</ul></div></div>';
        $elem.on('click', function(evt) {
            evt.preventDefault();
            var $content = $(tmplHtml);
            $content.popup({
                width: 480,
                title: '使用教学视频'
            });
        });
    }
});

(function() {
    $.widget('mxx.rktree', {
        options: {
            source: '',
            multiple: true,
            jstreeOptions: {
                plugins: [ /*,"checkbox"  "search", "contextmenu" "themes", "json_data", "ui", "crrm", "cookies", "dnd", "search", "types", "hotkeys", "contextmenu", "core"*/ ], //configure which plugins will be active on an instance. Should be an array of strings, where each element is a plugin name. The default is []
                core: { //stores all defaults for the core
                    data: false, //data configuration
                    strings: {
                        'Loading ...': '加载中 ...'
                    }, //configure the various strings used throughout the tree
                    check_callback: false, //determines what happens when a user tries to modify the structure of the tree
                    animation: 200, //the open / close animation duration in milliseconds - set this to false to disable the animation (default is 200)
                    multiple: true, //a boolean indicating if multiple nodes can be selected
                    themes: { //theme configuration object
                        name: 'default', //the name of the theme to use (if left as false the default theme is used)
                        url: false, //the URL of the theme's CSS file, leave this as false if you have manually included the theme CSS (recommended). You can set this to true too which will try to autoload the theme.
                        dir: false, //the location of all jstree themes - only used if url is set to true
                        dots: false, //a boolean indicating if connecting dots are shown
                        icons: false, //a boolean indicating if node icons are shown
                        stripes: false, //a boolean indicating if the tree background is striped
                        variant: false, //a string (or boolean false) specifying the theme variant to use (if the theme supports variants)
                        responsive: false //a boolean specifying if a reponsive version of the theme should kick in on smaller screens (if the theme supports it). Defaults to false.
                    },
                    expand_selected_onload: true, //if left as true all parents of all selected nodes will be opened once the tree loads (so that all selected nodes are visible to the user)
                    worker: true, //if left as true web workers will be used to parse incoming JSON data where possible, so that the UI will not be blocked by large requests. Workers are however about 30% slower. Defaults to true
                    force_text: true //Force node text to plain text (and escape HTML). Defaults to false
                },
                checkbox: { //stores all defaults for the checkbox plugin
                    visible: true, //a boolean indicating if checkboxes should be visible (can be changed at a later time using show_checkboxes() and hide_checkboxes). Defaults to true.
                    three_state: true, //a boolean indicating if checkboxes should cascade down and have an undetermined state. Defaults to true.
                    whole_node: true, //a boolean indicating if clicking anywhere on the node should act as clicking on the checkbox. Defaults to true.
                    keep_selected_style: true, //a boolean indicating if the selected style of a node should be kept, or removed. Defaults to true.
                    cascade: "", //This setting controls how cascading and undetermined nodes are applied. If 'up' is in the string - cascading up is enabled, if 'down' i
                    tie_selection: true //This setting controls if checkbox are bound to the general tree selection or to an internal array maintained by the checkbox plugin. Defaults to true, only set to false if you know exactly what you are doing.
                },
                contextmenu: { //stores all defaults for the contextmenu plugin
                    select_node: true, //a boolean indicating if the node should be selected when the context menu is invoked on it. Defaults to true.
                    show_at_node: true, //a boolean indicating if the menu should be shown aligned with the node. Defaults to true, otherwise the mouse coordinates are used.   
                    items: null //an object of actions, or a function that accepts a node and a callback function and calls the callback function with an object of actions available for that node (you can also return the items too).
                },
                dnd: { //stores all defaults for the drag'n'drop plugin
                    copy: true, //a boolean indicating if a copy should be possible while dragging (by pressint the meta key or Ctrl). Defaults to true.
                    open_timeout: 500, //a number indicating how long a node should remain hovered while dragging to be opened. Defaults to 500.
                    is_draggable: true, //a function invoked each time a node is about to be dragged, invoked in the tree's scope and receives the nodes about to be dragged as an argument (array) - return false to prevent dragging
                    check_while_dragging: true, //a boolean indicating if checks should constantly be made while the user is dragging the node (as opposed to checking only on drop), default is true
                    always_copy: false, //a boolean indicating if nodes from this tree should only be copied with dnd (as opposed to moved), default is false
                    inside_pos: 0 //when dropping a node "inside", this setting indicates the position the node should go to - it can be an integer or a string: "first" (same as 0) or "last", default is 0
                },
                search: { //stores all defaults for the search plugin
                    ajax: false, //a jQuery-like AJAX config, which jstree uses if a server should be queried for results.
                    fuzzy: false, //Indicates if the search should be fuzzy or not (should chnd3 match child node 3). Default is false.
                    case_sensitive: false, //Indicates if the search should be case sensitive. Default is false.
                    show_only_matches: false, //Indicates if the tree should be filtered (by default) to show only matching nodes (keep in mind this can be a heavy on large trees in old browsers). 
                    close_opened_onclear: true, //Indicates if all nodes opened to reveal the search result, should be closed when the search is cleared or a new search is performed. Default is true.
                    search_leaves_only: false, //Indicates if only leaf nodes should be included in search results. Default is false.
                    search_callback: false //If set to a function it wil be called in the instance's scope with two arguments - search string and node (where node will be every node in the structure, so use with caution).
                },
                state: { //stores all defaults for the state plugin
                    key: "jstree", //A string for the key to use when saving the current tree (change if using multiple trees in your project). Defaults to jstree.
                    events: "changed.jstree open_node.jstree close_node.jstree", //A space separated list of events that trigger a state save. Defaults to changed.jstree open_node.jstree close_node.jstree.
                    ttl: false, //Time in milliseconds after which the state will expire. Defaults to 'false' meaning - no expire.
                    filter: false //Time in milliseconds after which the state will expire. Defaults to 'false' meaning - no expire.Time in milliseconds after which the state will expire. Defaults to 'false' meaning - no expire.
                },
                types: { //An object storing all types as key value pairs, where the key is the type name and the value is an object that could contain following keys (all optional).
                    "#": {}, //
                    'default': {} //
                },
                unique: { //stores all defaults for the unique plugin
                    case_sensitive: false //Indicates if the comparison should be case sensitive. Default is false.
                }
            },
            jstreeEvents: {},
            scrollBarOptions: {
                setWidth: false,
                setHeight: false,
                setTop: 0,
                setLeft: 0,
                axis: "y",
                scrollbarPosition: "inside",
                scrollInertia: 950,
                autoDraggerLength: true,
                autoHideScrollbar: false,
                autoExpandScrollbar: false,
                alwaysShowScrollbar: 0,
                snapAmount: null,
                snapOffset: 0,
                mouseWheel: {
                    enable: true,
                    scrollAmount: "auto",
                    axis: "y",
                    preventDefault: false,
                    deltaFactor: "auto",
                    normalizeDelta: false,
                    invert: false,
                    disableOver: ["select", "option", "keygen", "datalist", "textarea"]
                },
                scrollButtons: {
                    enable: false,
                    scrollType: "stepless",
                    scrollAmount: "auto"
                },
                keyboard: {
                    enable: true,
                    scrollType: "stepless",
                    scrollAmount: "auto"
                },
                contentTouchScroll: 25,
                advanced: {
                    autoExpandHorizontalScroll: false,
                    autoScrollOnFocus: "input,textarea,select,button,datalist,keygen,a[tabindex],area,object,[contenteditable='true']",
                    updateOnContentResize: true,
                    updateOnImageLoad: true,
                    updateOnSelectorChange: false
                },
                theme: "3d-dark",
                callbacks: {
                    onScrollStart: false,
                    onScroll: false,
                    onTotalScroll: false,
                    onTotalScrollBack: false,
                    whileScrolling: false,
                    onTotalScrollOffset: 0,
                    onTotalScrollBackOffset: 0,
                    alwaysTriggerOffsets: true,
                    onOverflowY: false,
                    onOverflowX: false,
                    onOverflowYNone: false,
                    onOverflowXNone: false
                },
                live: false,
                liveSelector: null
            }
        },
        _create: function() {
            var $this = this,
                $elem = this.element,
                opt = this.options;
            $this.$jstree = $elem.find('.jstree-container');
            if (!$this.$jstree.size()) {
                $this.$jstree = $('<div class="jstree-container"></div>').appendTo($elem);
            }
            $this.loadTreeData(function() {
                $this.createTree();
            });
        },
        loadTreeData: function(callback) {
            var $this = this,
                $elem = this.element,
                opt = this.options;
            if ($.isArray(opt.source)) {
                setTimeout(function() {
                    $this.treeData = opt.source;
                    callback();
                }, 0);
            } else if (typeof opt.source == 'string') {
                $.getJSON(opt.source, function(json) {
                    $this.treeData = json;
                    callback();
                });
            } else {
                //
            }
        },
        createTree: function() {
            var $this = this,
                $elem = this.element,
                opt = this.options;
            var mcsbOption = $.extend(true, opt.scrollBarOptions, {}),
                jsTreeOption = $.extend(true, opt.jstreeOptions, {
                    core: {
                        data: $this.treeData,
                        multiple: opt.multiple
                    },
                    checkbox: {
                        visible: opt.multiple
                    }
                });
            $elem.mCustomScrollbar(mcsbOption);
            $.each(opt.jstreeEvents, function(evtname, handler) {
                $this.$jstree.on(evtname, handler);
            });
            $this.$jstree.jstree(jsTreeOption);
        },
        relay: function() {
            return this.$jstree.jstree.apply(this.$jstree, arguments);
        },
        getTreeInstance: function(){
            var $this = this,
                $elem = this.element,
                opt = this.options;
            return $this.$jstree.jstree(true);
        },
        destroy: function(){
            var $this = this,
                $elem = this.element,
                opt = this.options;
            $elem.off();
            $elem.remove();
            var treeInstance = $this.getTreeInstance();
            if(treeInstance){
                treeInstance.destroy();
            }            
        }
    });
})();
$.widget('mxx.rkautocomplete', $.ui.autocomplete, {
    options: {
        /* 
        Options:   appendTo autoFocus delay disabled minLength position source 
        Methods:   close destroy disable enable instance option search widget 
        Extension: Points _renderItem _renderMenu _resizeMenu 
        Events:    change close create focus open response search select 
        */
        source: '/search/u.action?pageNo=1&s=m&key=',
        alignWith: '',
        appendTo: "body"//防止弹在popup内部
    },
    _create: function() {
        var $this = this;
        this._super();
        this._off(this.element, 'focus');//jquery的focus默认会将this.selectedItem置为null，不需要此功能，因此关掉
        this._offOnInput();
        this._off(this.menu.element, 'menuselect menufocus');
        this._on(this.menu.element, {
            menufocus: function(event, ui) {
                if (this.isNewMenu) {
                    this.isNewMenu = false;
                    if (event.originalEvent && /^mouse/.test(event.originalEvent.type)) {
                        this.menu.blur();

                        this.document.one("mousemove", function() {
                            $(event.target).trigger(event.originalEvent);
                        });
                        return;
                    }
                }

                var item = ui.item.data("ui-autocomplete-item");
                if (false !== this._trigger("focus", event, {
                        item: item
                    })) {
                    if (event.originalEvent && /^key/.test(event.originalEvent.type)) {
                        this._value(item.label);
                    }
                } else {
                    this.liveRegion.text(item.label);
                }
            },
            menuselect: function(event, ui) {
                var item = ui.item.data("ui-autocomplete-item"),
                    previous = this.previous;

                if (this.element[0] !== this.document[0].activeElement) {
                    this.element.focus();
                    this.previous = previous;
                    this._delay(function() {
                        this.previous = previous;
                        this.selectedItem = item;
                    });
                }

                if (false !== this._trigger("select", event, {
                        item: item
                    })) {
                    this._value(item.label);
                }
                this.term = this._value();

                this.close(event);
                this.selectedItem = item;
                $.isFunction($this.options.onSelect) && $this.options.onSelect(item);
            }
        });
    },
    _offOnInput: function(){
        var $this = this;
        //说明（by张磊）：由于jquery-ui给autocomplete事件默认绑定了一个input事件，并在该事件中将value置为了null（搜索源码中"_searchTimeout"即可看到），导致crm中弹框初始化时会触发input事件从而将刚赋的值置空，这不符合设计，因此在10ms内将input置为disable，从而避过在IE中触发input事件。
        //该问题只会在IE9+的crm弹框中出现
        var isIE = ($.browser.msie || !!navigator.userAgent.match(/Trident/));
        var elem = $this.element;
        if(isIE && $.browser.version >=9 && elem.prop('disabled') != true){
            elem.prop('disabled', true);//置为disable避免初始时触发input事件
            window.setTimeout(function(){
                elem.prop('disabled', false);//恢复原状
            }, 10);
        }
    },
    _normalize: function(items) {
        if (!$.isArray(items)) {
            items = items.data;
        }

        if (items.length && items[0].key != undefined && items[0].value != undefined) {
            // /search/u.action /search/fcbk-account.action /search/fcbk-product.action /search/u-no-self.action /search/search-campaign.action
            //[{"key":904,"value":"<p class=\"facebook_auto_human\"><img  class=\"human_face_24x24\" src=\"https://www.ingageapp.com/upload/i/904/904/s_3b854183edfb4967817473a3fd727ded.jpg\" alt=\"张英男h\" title=\"张英男h\"/><span>张英男h<\/span><\/p>"}]
            items = $.map(items, function(item) {
                var $p = $(item.value),
                    $img = $p.find('img');
                return {
                    label: $img.attr('title'),
                    value: item.key,
                    pic: $img.attr('src')
                };
            });
        } else if (items.length && items[0].id != undefined && items[0].label != undefined && items[0].value != undefined) {
            // /search/search-user.action /search/all-account.action /search/search-account.action /search/opportunity.action /search/search-contact.action /search/lead.action
            //{"id":904,"label":"<p class=\"jq_auto_human\"><img  class=\"human_face_24x24\" src=\"https://www.ingageapp.com/upload/i/904/904/s_3b854183edfb4967817473a3fd727ded.jpg\" alt=\"张英男h\" title=\"张英男h\"/><span>张英男h<\/span><\/p>","value":"张英男h"}
            //{"id":1001,"label":"<span class=\"auto_select_ico opp_ico\">自由<\/span>","value":"自由"}
            items = $.map(items, function(item) {
                var $p = $(item.label),
                    $img = $p.find('img');
                return {
                    label: item.value,
                    value: item.id,
                    icon: $p.attr('class'),
                    pic: $img.size() ? $img.attr('src') : undefined
                };
            });
        } else if (items.length && items[0].id != undefined && items[0].parent != undefined) {
            items = $.map(items, function(item) {
                return {
                    label: item.text,
                    value: item.id
                };
            });
        }
        // assume all items have the right format when the first item is complete
        if (items.length && items[0].label && items[0].value) {
            return items;
        }
        return $.map(items, function(item) {
            if (typeof item === "string") {
                return {
                    label: item,
                    value: item
                };
            }
            return $.extend({
                label: item.label || item.value,
                value: item.value || item.label
            }, item);
        });
    },
    _renderItem: function(ul, item) {
        {//支持id，name，icon的格式
            item.label = item.name;
            item.value = item.id;
            item.pic = item.icon ? item.icon : '/admin/img/default_face.png';
        }
        var $icon = item.pic ? $('<img>').attr('src', item.pic) : item.icon ? $('<span>').attr('class', item.icon) : 0;
        var $lnk = $('<a>').text(item.label);
        $icon && $lnk.prepend($icon);
        return $("<li>").append($lnk).appendTo(ul);
    },
    _search: function(value) {
        this.pending++;
        this.element.addClass("ui-autocomplete-loading");
        this.cancelSearch = false;

        this.source({
            term: value,
            key: value
        }, this._response());
    },
    _suggest: function(items) {
        var ul = this.menu.element.empty();
        this._renderMenu(ul, items);
        this.isNewMenu = true;
        this.menu.refresh();

        // size and position menu
        ul.show();
        this._resizeMenu();
        ul.position($.extend({
            of: this.options.alignWith ? $(this.options.alignWith) : this.element
        }, this.options.position));

        if (this.options.autoFocus) {
            this.menu.next();
        }
    },
    _resizeMenu: function() {
        var ul = this.menu.element;
        ul.outerWidth(Math.max(
            // Firefox wraps long text (possibly a rounding bug)
            // so we add 1px to avoid the wrapping (#7513)
            ul.width("").outerWidth() + 1, (this.options.alignWith ? $(this.options.alignWith) : this.element).outerWidth()
        ));
    }
});

$.widget('mxx.acSingle', $.mxx.rkautocomplete, {
    options: {
        /* 
        Options:   appendTo autoFocus delay disabled minLength position source 
        Methods:   close destroy disable enable instance option search widget 
        Extension: Points _renderItem _renderMenu _resizeMenu 
        Events:    change close create focus open response search select 
        */
    },
    val: function(item) {
        if (item) {
            this._value(item.label);
            this.term = this._value();
            this.selectedItem = item;
        } else if (item === null) {
            this._value('');
            this.term = this._value();
            this.selectedItem = null;
        } else {
            return this.selectedItem || null;
        }
    }
});

$.widget('mxx.acMulti', $.mxx.rkautocomplete, {
    options: {},
    _create: function() {
        this._super();
        this.$box = $('<dl class="ui-autocomplete-box"><dd class="ui-autocomplete-ipt-box"></dd></dl>').insertAfter(this.element);
        this.$iptBox = this.$box.find('dd.ui-autocomplete-ipt-box');
        this.element.addClass('ui-autocomplete-ipt').appendTo(this.$iptBox);
        this.$box.on('click.acMulti', 'a.js-ac-del', function(evt) {
            evt.preventDefault();
            $(this).closest('dt').hide('fade', 200, function() {
                $this.remove([$(this).data("item-ac")]);
            });
        });
        var $this = this;
        this.$box.on('click.acMulti', function(evt) {
            $this._delay(function() {
                $this.element.focus();
            }, 10);
        });
        $this.element.on('keydown.acMulti', function(evt) {
            if (evt.keyCode == 8 && $this.element.val() == '') {
                var $dtItems = $this.$box.find(':data(item-ac)');
                if ($dtItems.size()) {
                    var $lastItem = $dtItems.last();
                    if ($lastItem.hasClass('ui-autocomplete-item-removing')) {
                        $this.remove([$lastItem.data("item-ac")]);
                    } else {
                        $lastItem.addClass('ui-autocomplete-item-removing');
                    }
                }
            } else {
                $this.$box.find(':data(item-ac)').filter('.ui-autocomplete-item-removing').removeClass('ui-autocomplete-item-removing');
            }
        });

        this._off(this.menu.element, 'menuselect');
        this._on(this.menu.element, {
            menuselect: function(event, ui) {
                var item = ui.item.data("ui-autocomplete-item"),
                    previous = this.previous;

                if (this.element[0] !== this.document[0].activeElement) {
                    this.element.focus();
                    this.previous = previous;
                    this._delay(function() {
                        this.previous = previous;
                        this.selectedItem = item;
                    });
                }

                if (false !== this._trigger("select", event, {
                        item: item
                    })) {
                    this.add([item]);
                    this._value('');
                }
                this.term = this._value();

                this.close(event);
                this.selectedItem = item;
            }
        });
    },
    val: function(items) {
        if (items) {
            this.clear();
            this.add(items);
        } else {
            return this.$box.find(':data(item-ac)').map(function() {
                return $(this).data('item-ac');
            }).get();
        }
    },
    add: function(items, notrigger) {
        var $this = this;
        $.each(items, function(_, item) {
            if (!$this.$box.find(':data(item-ac)').filter('[value="' + item.value + '"]').size()) {
                var html = ['<dt class="ui-autocomplete-item" value="', item.value, '">', item.icon ? '<span class="' + item.icon + '"></span>' : '', item.pic ? '<img src="' + item.pic + '"/>' : '', '<span class="ui-autocomplete-item-name js-ac-result">', item.label, '</span><a href="javascript:;" class="ui-autocomplete-item-remove js-ac-del">x</a></dt>'].join('');
                $(html).data("item-ac", item).insertBefore($this.$iptBox);
                notrigger || $this._trigger('add', null, item);
            }
        });
        $this._trigger('sysOnUpdate');//内部事件
    },
    remove: function(items, notrigger) {
        var $this = this;
        $.each(items, function(_, item) {
            $this.$box.find(':data(item-ac)').filter('[value="' + item.value + '"]').remove();
            notrigger || $this._trigger('remove', null, item);
        });
        $this._trigger('sysOnUpdate');//内部事件
    },
    clear: function(notrigger) {
        var $this = this;
        this.$box.find(':data(item-ac)').remove();
        notrigger || this._trigger('clear');
        $this._trigger('sysOnUpdate');//内部事件
    },
    _destroy: function() {
        this._super();
        this.element.off('keydown.acMulti');
        this.element.removeClass('ui-autocomplete-ipt').appendTo(this.$box);
        this.$box.off('click.acMulti', 'a.js-ac-del');
        this.$box.off('click.acMulti');
        this.$box.remove();
    }
});

$.widget('mxx.acAt', $.mxx.rkautocomplete, {
    options: {},
    _create: function() {
        var that = this,
            url = this.options.source;
        if (typeof(url) == 'string') {
            this.options.source = function(request, response) {
                var term = request.term,
                    pos = term.indexOf(' ', that.element.caret().begin);
                if (pos == -1) {
                    pos = term.length;
                }
                var front = term.slice(0, pos),
                    end = term.slice(pos);
                var posAt = front.lastIndexOf('@'),
                    posBlank = front.lastIndexOf(' ');
                if (posAt == -1 || posBlank > posAt) {
                    return response();
                }
                term = front.slice(posAt + 1);
                front = front.slice(0, posAt + 1);

                $.getJSON(url + term, function(data, status, xhr) {
                    response(data);
                });
            };
        }
        this._super();
        this._off(this.menu.element, 'menuselect menufocus');
        this._on(this.menu.element, {
            menufocus: function(event, ui) {
                // support: Firefox
                // Prevent accidental activation of menu items in Firefox (#7024 #9118)
                if (this.isNewMenu) {
                    this.isNewMenu = false;
                    if (event.originalEvent && /^mouse/.test(event.originalEvent.type)) {
                        this.menu.blur();

                        this.document.one("mousemove", function() {
                            $(event.target).trigger(event.originalEvent);
                        });
                        return;
                    }
                }

                var item = ui.item.data("ui-autocomplete-item");
                if (false !== this._trigger("focus", event, {
                        item: item
                    })) {
                    // use value to match what will end up in the input, if it was a key event
                    if (event.originalEvent && /^key/.test(event.originalEvent.type)) {
                        this._replaceTerm(item);
                    }
                } else {
                    // Normally the input is populated with the item's value as the
                    // menu is navigated, causing screen readers to notice a change and
                    // announce the item. Since the focus event was canceled, this doesn't
                    // happen, so we update the live region so that screen readers can
                    // still notice the change and announce it.
                    this.liveRegion.text(item.label);
                }
            },
            menuselect: function(event, ui) {
                var item = ui.item.data("ui-autocomplete-item"),
                    previous = this.previous;

                // only trigger when focus was lost (click on menu)
                if (this.element[0] !== this.document[0].activeElement) {
                    this.element.focus();
                    this.previous = previous;
                    // #6109 - IE triggers two focus events and the second
                    // is asynchronous, so we need to reset the previous
                    // term synchronously and asynchronously :-(
                    this._delay(function() {
                        this.previous = previous;
                        this.selectedItem = item;
                    });
                }

                if (false !== this._trigger("select", event, {
                        item: item
                    })) {
                    this._replaceTerm(item);
                }
                // reset the term after the select event
                // this allows custom select handling to work properly
                this.term = this._value();

                this.close(event);
                this.selectedItem = item;
            }
        });
    },
    _replaceTerm: function(item) {
        var pos = this.element.caret().begin,
            term = this.element.val();
        var front = term.slice(0, pos),
            end = term.slice(pos);
        var posAt = front.lastIndexOf('@'),
            label = item.label;
        front = front.slice(0, posAt + 1) + label + ' ';
        end = end.charAt(0) == ' ' ? end.slice(1) : end;
        this.element.val(front + end);
        this.element.caret(front.length);
    }
});

$.widget('mxx.treeselector', {
    options: {
        source: '', //数据 url or json array
        width: '100%', //控件宽度
        height: '100%', //控件高度
        dataMap: globalOptions('treeselector.dataMap'), //树数据在请求返回中的位置
        menuWidth: '100%', //下拉菜单宽度
        menuHeight: 240, //下拉菜单高度
        menuZindex: 1000, //下拉菜单层级
        appendTo: 'body', //下拉菜单在dom中的位置
        position: null, //下拉菜单定位参数
        show: null, //下拉菜单显示动画设置
        hide: null, //下拉菜单隐藏动画设置
        expandMenuWidth: true//内容超长时，menu宽度是否撑开
    },
    _urls: {
        'os': '/admin/user-manage/tree.action',
        'ar': '/admin/dimension/area/privilege-tree.action',
        'bu': '/admin/dimension/business/privilege-tree.action',
        'in': '/admin/dimension/industry/privilege-tree.action',
        'pr': '/admin/dimension/product/privilege-tree.action'
    },
    _create: function() {
        var $this = this,
            $elem = this.element,
            opt = this.options;
        opt.source = $this._urls[opt.source] || opt.source;
        if ($.isArray(opt.source)) {
            $this.uniformSource(opt.source);
        }
        $elem.wrap('<div class="ui-powerinput-box"></div>');
        $this.$container = $elem.parent().css({
            width: opt.width,
            height: opt.height
        });
        $this.$button = $('<a href="javascript:;" class="ui-powerinput-button"><span>+</span></a>').prependTo($this.$container).on('click', function(evt) {
            evt.preventDefault();
            $this.toggleMenu(evt);
        });
    },
    loadData: function(callback) {
        var $this = this,
            opt = this.options;
        if ($.isArray(opt.source)) {
            setTimeout(function() {
                callback();
            }, 0);
        } else if (typeof opt.source == 'string') {
            $.getJSON(opt.source, function(json) {
                $this.uniformSource($.route(json, opt.dataMap));
                callback();
            });
        }
    },
    uniformSource: function(data) {
        var $this = this;
        if (data && data.length) {
            if (data[0].id) {
                $this.treedata = data;
                $this.acdata = $.convertKeys(data, {
                    text: 'label',
                    id: 'value'
                });
            } else {
                $this.acdata = data;
                $this.treedata = $.convertKeys(data, {
                    label: 'text',
                    value: 'id'
                });
            }
            var firstLevelNodes = $.searchIndex($.map($this.treedata, function(o) {
                return o.parent == '#' ? o : null;
            }), 'id');
            $.each($this.treedata, function(_, o) {
                if (!o.state) {
                    o.state = {};
                }
                o.state.opened = !!firstLevelNodes[o.id];
            });
            $this.loadData = function(callback) {
                setTimeout(function() {
                    callback();
                }, 0);
            };
        } else {
            $this.acdata = [];
            $this.treedata = [{
                id: '0',
                parent: '#',
                text: '没有数据',
                state: {
                    disabled: true
                }
            }];
        }
    },
    uniformData: function(data) {
        var $this = this;
        if (data && data.length) {
            if (data[0].id) {
                data = $.convertKeys(data, {
                    text: 'label',
                    id: 'value'
                });
            } else if (typeof(data[0]) == 'string' || typeof(data[0]) == 'number' && $this.acdata) {
                data = $.map(data, function(key) {
                    var ar = $.grep($this.acdata, function(o) {
                        return o.value == key || o.label == key;
                    });
                    return ar.length ? ar[0] : null;
                });
            }
        }
        return data;
    },
    _getNewSource: function(jsonArray) {
        var $this = this;
        return function(request, response) {
            response($this._grep(jsonArray, $.trim(request.term.toLowerCase())));
        };
    },
    _grep: function(ar, term) {
        var $this = this;
        return $.grep(ar, function(o, i) {
            return ~o.label.toLowerCase().indexOf(term) || ~o.pinyin.toLowerCase().indexOf(term) || ~$this._containsInOrder(term, o.label.toLowerCase()) || ~$this._containsInOrder(term, o.pinyin.toLowerCase());
        });
    },
    _containsInOrder: function(term, label) {
        term = term.split('');
        var i = 0,
            index;
        while ((index = label.indexOf(term[i], index)) > -1) {
            i++;
            if (!term[i]) {
                return index;
            }
        }
        return -1;
    },
    createMenu: function() {
        var $this = this,
            opt = this.options;
        if (!$this.$menu) {
            $this.$menu = $('<div class="ui-powerinput-menu" style="display:none;"><p><span>加载中</span></p></div>').css('zIndex', opt.menuZindex).appendTo(opt.appendTo);
            $this.$menu.on('click', function(evt) {
                evt.stopPropagation();
            });
            $this.setMenuSize();
        }
    },
    toggleMenu: function(evt) {
        var $this = this;
        if (!$this.$menu || $this.$menu.is(':hidden')) {
            $this.showMenu();
            $this._trigger('sysTreeMenuOpened', null, $this.$menu);
            $this._handleMenuOpen ? $this._handleMenuOpen() : null;
        } else {
            $this.hideMenu();
            $this._trigger('sysTreeMenuClosed', null, $this.$menu);
            $this._handleMenuClose ? $this._handleMenuClose() : null;
        }
    },
    showMenu: function() {
        var $this = this,
            $elem = this.element,
            opt = this.options;
        $this._trigger('focus', opt);
        $this.createMenu();
        $this.$menu.show(opt.show);
        $this.setMenuPosition();
        $(document).on('click.powerinput' + $this.uuid, function(evt) {
            if (!$(evt.target).closest($this.$container).size()) {
                $this.hideMenu();
            }
        });
        $this.initMenu();
    },
    hideMenu: function() {
        var $this = this,
            opt = this.options;
        $this._trigger('blur', opt);
        if ($this.$menu) {
            $this.$menu.hide(opt.hide);
        }
        $(document).off('click.powerinput' + $this.uuid);
    },
    initMenu: function() {

    },
    setMenuSize: function() {
        var $this = this,
            opt = this.options;
        var width = opt.menuWidth == '100%' ? $this.$container.width() : opt.menuWidth;
        if(opt.expandMenuWidth){
            $this.$menu.css('min-width',width+'px');//tree节点会横向展开，因此不限定绝对宽度
        }else{
            $this.$menu.width(width);
        }
        $this.$menu.height(opt.menuHeight);
    },
    setMenuPosition: function() {
        this.$menu.position($.extend(true, {
            my: 'left top',
            at: 'left bottom',
            of: this.$container
        }, this.options.position));
    },
    _renderTree: function() {
        var $this = this,
            opt = this.options;
        $this.createMenu();
        $this.$menu.empty();
    },
    _getSelectedNodes: function(e, data) {
        var $this = this,
            $elem = this.element,
            opt = this.options;
        var i, j, n, r = [];
        for (i = 0, j = data.selected.length; i < j; i++) {
            n = data.instance.get_node(data.selected[i]);
            r.push({
                label: n.text,
                value: n.id,
                parent: n.parent
            });
        }
        return r;
    },
    _syncWith: function(e, data) {},
    relay: function() {
        var args = $.makeArray(arguments);
        args.unshift('relay');
        return this.$menu && this.$menu.rktree.apply(this.$menu, args);
    },
    _destroy: function() {
        var $this = this,
            $elem = this.element;
        $this._super();
        $elem.appendTo($this.$container);
        $this.$container.remove();
        $this.$menu && $this.$menu.remove();
        $(document).off('click.powerinput' + $this.uuid);
        $this.treedata = null;
        $this.acdata = null;
    }
});

$.widget('mxx.singletreeselector', $.mxx.treeselector, {
    options: {},
    _create: function() {
        var $this = this,
            $elem = this.element,
            opt = this.options;
        $this._super();
        $elem.acSingle({
            alignWith: $this.$container,
            source: function(request, response) {
                $this.loadData(function() {
                    var $data = $this.acdata;
                    if( opt.checkRootFlg == 0 ) {
                        $data = $.grep($this.acdata, function(o, i) {
                            return o.parent !='#';
                        });
                    }
                    response($this._grep($data, $.trim(request.term.toLowerCase())));
                    $elem.acSingle('option', 'source', $this._getNewSource($data));
                    $this._renderTree();
                });
            },
            open: $.proxy($this.hideMenu, $this),
            onSelect: function (item) {
                $.isFunction(opt.onSuccess) && opt.onSuccess(item);
            }
        });
    },
    initMenu: function() {
        var $this = this,
            $elem = this.element,opt = this.options;
        if (!$this.$menu.data('inited')) {
            $this.loadData(function() {
                $this._renderTree();
                var $data = $this.acdata;
                if( opt.checkRootFlg == 0 ) {
                    $data = $.grep($this.acdata, function(o, i) {
                        return o.parent !='#';
                    });
                }
                $elem.acSingle('option', 'source', $this._getNewSource($data));
            });
        }
    },
    _renderTree: function() {
        var $this = this,
            $elem = this.element,
            opt = this.options;
        $this._super();
        var selected = $.searchIndex($elem.acSingle('val') ? [$elem.acSingle('val')] : [], 'value'),
            source = $.map($this.treedata, function(o) {
                if (!o.state) {
                    o.state = {};
                }
                o.state.selected = !!selected[o.id];
                return o;
            });
        $this.$menu.rktree({
            source: $this.treedata,
            jstreeOptions: {
                checkbox: {
                    three_state: false
                }
            },
            jstreeEvents: {
                'check_node.jstree': $.proxy($this._syncWith, $this),
                'uncheck_node.jstree': $.proxy($this._syncWith, $this),
                'select_node.jstree': $.proxy($this._syncWith, $this),
                'deselect_node.jstree': $.proxy($this._syncWith, $this)
            }
        });
        $this.$menu.data('inited', true);
    },
    val: function(item) {
        var $this = this;
        if (item || item === null) {
            $this.element.acSingle('val', $this.uniformData([item])[0]);
        } else {
            return $this.element.acSingle('val');
        }
    },
    _syncWith: function(e, data) {
        var $this = this, $elem = this.element, opt = this.options ;
        var node = $this._getSelectedNodes(e, data)[0];
        if(opt.checkRootFlg == 0 && node.parent ==='#') {
            return;
        }
        $elem.acSingle('val', node, true);
        $.isFunction(opt.onSuccess) && opt.onSuccess(node);
        $this.hideMenu();
    },
    _destroy: function() {
        var $this = this;
        $this.element.acSingle('destroy');
        $this._super();
    }
});
$.widget('mxx.multitreeselector', $.mxx.treeselector, {
    options: {},
    _create: function() {
        var $this = this,
            $elem = this.element,
            opt = this.options;
        $this._super();
        $elem.acMulti({
            alignWith: $this.$container,
            source: function(request, response) {
                $this.loadData(function() {
                    response($this._grep($this.acdata, $.trim(request.term.toLowerCase())));
                    $elem.acMulti('option', 'source', $this._getNewSource($this.acdata));
                    $this._renderTree();
                });
            },
            open: $.proxy($this.hideMenu, $this),
            add: function(evt, item) {
                if ($this.$menu && $this.$menu.data('inited')) {
                    $this.$menu.rktree('relay', 'check_node', {
                        id: item.value
                    });
                }
            },
            remove: function(evt, item) {
                if ($this.$menu && $this.$menu.data('inited')) {
                    $this.$menu.rktree('relay', 'deselect_node', {
                        id: item.value
                    });
                }
            },
            clear: function() {
                if ($this.$menu && $this.$menu.data('inited')) {
                    $this.$menu.rktree('relay', 'uncheck_all');
                }
            }
        });
    },
    initMenu: function() {
        var $this = this,
            $elem = this.element;
        if (!$this.$menu.data('inited')) {
            $this.loadData(function() {
                $this._renderTree();
                $elem.acMulti('option', 'source', $this._getNewSource($this.acdata));
            });
        }
    },
    _renderTree: function() {
        var $this = this,
            $elem = this.element,
            opt = this.options;
            
        $this._super();
        var selected = $.searchIndex($elem.acMulti('val'), 'value'),
            source = $.map($this.treedata, function(o) {
                if (!o.state) {
                    o.state = {};
                }
                o.state.selected = !!selected[o.id];
                return o;
            });
        $this.$menu.rktree({
            source: source,
            jstreeOptions: {
                plugins: ['checkbox'],
                checkbox: {
                    three_state: false
                }
            },
            jstreeEvents: {
                'check_node.jstree': $.proxy($this._syncWith, $this),
                'uncheck_node.jstree': $.proxy($this._syncWith, $this),
                'select_node.jstree': $.proxy($this._syncWith, $this),
                'deselect_node.jstree': $.proxy($this._syncWith, $this),
                'before_open.jstree': function(n,m){
                    if(opt.noCheckbox == 'parent') $this._hideCheckbox(m.node.id);
                },
                'ready.jstree': function(){
                    if(opt.noCheckbox == 'parent') $this._hideCheckbox();
                }
            }
        });
        $this.$menu.data('inited', true);
    },
    _hideCheckbox: function(id){
        var $this = this,
            $elem = this.element,
            opt = this.options;
        if(typeof id != 'undefined'){
            var node = $('#'+id+'_anchor').parent();
            $this.__hidecheckbox(node);
        }else{
            var nodes = $this.$menu.find('ul.jstree-container-ul > li.jstree-node');
            $this.__hidecheckbox(nodes);
        }
    },
    __hidecheckbox: function(nodeElem){
        var $this = this;
        nodeElem.find('> a.jstree-anchor > i.jstree-checkbox').remove();
        nodeElem.find('> ul > li.jstree-node').css('margin-left', '0');
    },
    val: function(items) {
        var $this = this;
        if (items && items.length) {
            $this.element.acMulti('val', $this.uniformData(items));
        } else {
            return $this.element.acMulti('val');
        }
    },
    _syncWith: function(e, data) {
        var $this = this,
            $elem = this.element,
            opt = this.options;
        $elem.acMulti('clear', true);
        $elem.acMulti('add', $this._getSelectedNodes(e, data), true);
        $this.setMenuPosition();
        $elem.focus();
    },
    _destroy: function() {
        var $this = this;
        $this.element.acMulti('destroy');
        $this._super();
    },
    clear: function() {
        var $this = this;
        $this.element.acMulti('clear');
    }
});
$.widget('zhl.multitreepanel', $.mxx.treeselector, {
    options: {
        cascadeChecking: false,//当为false时，勾选父节点的checkbox后不会全部勾选子节点
        onInit: function(){}//渲染成功后触发
    },
    inited: false,
    rktree: null,
    _create: function() {
        var $this = this,
            $elem = this.element,
            opt = this.options;
            if (!$elem.data('mtree-inited')) {
                $this.loadData(function() {
                    $this._renderTree();
                });
            }
    },
    getTreeInstance: function(){
        return this.rktree.rktree('getTreeInstance');
    },
    _getSource: function(){
        var $this = this;
        var selected = {};
        return $.map($this.treedata, function(o) {
                if (!o.state) {
                    o.state = {};
                }
                o.state.selected = !!selected[o.id];
                return o;
            });
    },
    rendering: false,
    source: {},
    _renderTree: function(source) {
        var $this = this,
            $elem = this.element,
            opt = this.options;
        $this.rendering = true;
        var initSource = (typeof source == 'undefined' ? $this._getSource() : source);
        $this.source = initSource;
        $elem.append('<div class="rktree"></div>');
        $this.rktree = $elem.find('div.rktree');
        $this.rktree.rktree({
            source: initSource,
            jstreeOptions: {
                plugins: ['checkbox'],
                checkbox: {
                    three_state: false
                    //cascade: (opt.cascadeChecking ? 'down'/*仅向下全勾选*/ : '')
                }
            },
            jstreeEvents: {
                'ready.jstree': function(){
                    if(!$this.inited){                         
                            $this._trigger('onInit');
                            $this.inited = true;
                    }                    
                },
                'loaded.jstree': function(){
                    $this.rendering = false;
                },
                'select_node.jstree': function(e, node){//由于check_node事件不管用，因此暂时用select_node代替，在该版本上两者是等价的
                    if(!$this.settingValue){//val()方法也会触发select_node，此时不应当触发选择子节点
                        $this._checkSubNodes(node);
                    }
                }
            }
        });
        $elem.data('mtree-inited', true);
    },
    _checkSubNodes: function(node){
        var $this = this,
            $elem = this.element,
            opt = this.options;
        if(!opt.cascadeChecking)return;//若为FALSE，则用three_status默认行为即可
        var treeInstance = $this.getTreeInstance();
        var _node = node.node;
        var children = _node.children;
        var results = [];
        $this._getAllChildren(children, results);//递归将子节点id刷到results上
        treeInstance.check_node(results);
    },
    _getAllChildren: function(children, results){
        var $this = this;
        var treeInstance = $this.getTreeInstance();
        var allvalue = $this.currentValue;
        for(var z=0;z<children.length;z++){
                results.push(children[z]+'');
        }
        $.each(children, function(i, id){
            var sub = treeInstance.get_node(id).children;
            $this._getAllChildren(sub, results);
        });
    },
    beCascadeChecking: function(cascadeChecking){//选中checkbox后，子节点是否也选中
        var $this = this,
            $elem = this.element,
            opt = this.options;
        opt.cascadeChecking = cascadeChecking;
        // var items = $this.val();
        // $this.val(items);
    },
    _getCheckedNodes: function(){
        var $this = this,
            $elem = this.element,
            opt = this.options;

        var checkedlist = $this.rktree.rktree('getTreeInstance').get_checked();
        var results = [];
        $.each(checkedlist, function(i, id){
            results.push({
                value: id
            });
        })
        return results;
    },
    currentValue: null,
    settingValue: false,
    val: function(items){
        var $this = this,
            $elem = this.element,
            opt = this.options;
        //GET
        if(typeof items == 'undefined'){
            return ($this.rendering ? $this.currentValue : $this._getCheckedNodes());
        }
        //SET
        $this.settingValue = true;
        var treeInstance = $this.getTreeInstance();
        treeInstance.uncheck_all();
        var map = [];
        $.each(items, function(i, it){
            var v = it.value+'';
            map.push(v);
        });
        treeInstance.check_node(map);
        $this.settingValue = false;

        // return;
        // $this.currentValue = items;
        // var source = $this._getSource();
        // var map = [];
        // $.each(items, function(i, it){
        //     map[it.value+''] = true;
        // });
        // $.each(source, function(i, src){
        //     if(map[src.id+'']){
        //         src.state.selected = true;
        //     }else{
        //         src.state.selected = false;
        //     }
        // });
        // $this.rktree.rktree('destroy');
        // $this._renderTree(source);
    }
});
$.widget('mxx.singletreepicker', $.mxx.treeselector, {
    options: {
        eventtype: 'click',
        clickSelector: undefined
    },
    _create: function() {
        var $this = this,
            $elem = this.element,
            opt = this.options;
        opt.source = $this._urls[opt.source] || opt.source;
        $this.$container = $elem;
        $elem.on(opt.eventtype + '.singletreepicker' + $this.uuid, opt.clickSelector, function(evt) {
            evt.preventDefault();
            $this.toggleMenu(evt);
        });
    },
    initMenu: function() {
        var $this = this,
            $elem = this.element;
        if (!$this.$menu.data('inited')) {
            $this.loadData(function() {
                $this._renderTree();
            });
        }
    },
    _renderTree: function() {
        var $this = this,
            $elem = this.element,
            opt = this.options;
        $this._super();
        var treeOptions = {
            source: $this.treedata,
            jstreeOptions: {
                checkbox: {
                    three_state: false
                }
            },
            jstreeEvents: {}
        };
        treeOptions.jstreeEvents['check_node.jstree'] = treeOptions.jstreeEvents['select_node.jstree'] = function(e, data) {
            $this._trigger('select', e, $this._getSelectedNodes(e, data)[0]);
            $this.hideMenu();
        };
        $this.$menu.rktree(treeOptions);
        $this.$menu.data('inited', true);
    },
    _destroy: function() {
        var $this = this;
        $this.$menu && $this.$menu.remove();
        $(document).off('click.powerinput' + $this.uuid);
        $this.treedata = null;
        $this.acdata = null;
    }
});

$.widget('mxx.peopleselector', $.mxx.treeselector, {
    options: {
        source: globalOptions('peopleselector.source'),//'/search/u.action?s=m&pageNo=1&key='
        departsUrl: globalOptions('peopleselector.departsUrl'),//"/json/sns_privatemsg/all-departs.action",
        departsUserUrl: globalOptions('peopleselector.departsUserUrl'),//"/json/sns_privatemsg/all-depart-user.action",
        menuHeight: 'auto',
        scrollBarOptions: {
            theme: "3d-dark",
            mouseWheel:{ 
                    scrollAmount: (isMac ? 3 : 100)
            }
        },
        expandMenuWidth: false
    },
    initMenu: function() {
        var $this = this,
            $elem = this.element,
            opt = this.options;
        if (!$this.$menu.data('inited')) {
            $this.$menu.html('<div class="ui-powerinput-menutop"><select class="ui-powerinput-depart"></select><p class="ui-powerinput-info"></p></div><div class="ui-powerinput-menumiddle"><ul class="ui-powerinput-peoplelist"></ul></div><div class="ui-powerinput-menubottom"><a href="javascript:;" class="ui-powerinput-clear"><span>清除</span></a><a href="javascript:;" class="ui-powerinput-close"><span>关闭</span></a><p class="ui-powerinput-info ui-powerinput-bat"><label><input type="checkbox" class="ui-powerinput-checkall"/><span class="ui-powerinput-alltext">全选</span></label></p></div>');
            $this.$menutop = $this.$menu.find('div.ui-powerinput-menutop');
            $this.$menumid = $this.$menu.find('div.ui-powerinput-menumiddle');
            $this.$menubtm = $this.$menu.find('div.ui-powerinput-menubottom');

            $this.$depart = $this.$menutop.find('select.ui-powerinput-depart');
            $this.$info = $this.$menutop.find('p.ui-powerinput-info');

            $this.$list = $this.$menumid.find('ul.ui-powerinput-peoplelist');

            $this.$close = $this.$menubtm.find('a.ui-powerinput-close');
            $this.$clear = $this.$menubtm.find('a.ui-powerinput-clear');
            $this.$bat = $this.$menubtm.find('.ui-powerinput-bat');
            $this.$checkall = $this.$menubtm.find('input.ui-powerinput-checkall');

            $this.addMenuHandler();
            $this.special();

            $this.loadDepartData(function() {
                $this.renderDepartsList();
                $this.getUsersByDepartId($this.departData.departId, function(users) {
                    $this.renderUserList(users);
                });
                $this.$menu.data('inited', true);
            });
        }

    },
    special: $.noop,
    addMenuHandler: function() {
        var $this = this,
            $elem = this.element,
            opt = this.options;

        $this.$depart.on('change', function(evt) {
            $this.getUsersByDepartId(this.value, function(users) {
                $this.$list.empty();
                $this.renderUserList(users);
            });
        });

        $this.$menumid.mCustomScrollbar($.extend(true, {
            callbacks: {
                onTotalScroll: function() {
                    console.log('the end');
                },
                onTotalScrollOffset: 30,
                alwaysTriggerOffsets: false
            }
        }, opt.scrollBarOptions));

        $this.$list.on('click', 'a.ui-powerinput-userlink', function(evt) {
            evt.preventDefault();
        });

        $this.$close.on('click', function(evt) {
            evt.preventDefault();
            $this.$menu.hide(opt.hide);
        });

    },
    loadDepartData: function(callback) {
        var $this = this;
        $.getJSON($this.options.departsUrl, {
            pageSize: 500
        }).done(function(json) {
            if (json.status) {
                $.msg(json.status);
                return;
            }
            $this.departData = json.data;
            $.setDefIcon(json.data.userArray);
            $this.updateUserCache(json.data.userArray);
            $this._departCache[$this.departData.departId] = $this.departData.userArray;
            callback();
        }).fail(function(def, code, message) {});
    },
    renderDepartsList: function() {
        var $this = this;
        $($.map($this.departData.departArray, function(o) {
            return ['<option value="', o.id, '">', o.name, '</option>'].join('');
        }).join('')).appendTo($this.$depart).filter('[value="' + $this.departData.departId + '"]').attr('selected', 'selected');
    },
    _departCache: {},
    _userCache: {},
    updateUserCache: function(users) {
        this._userCache = $.extend(true, this._userCache, $.searchIndex($.convertKeys(users, {
            id: 'value',
            name: 'label'
        }), 'value'));
    },
    getUsersByDepartId: function(departId, callback) {
        var $this = this;
        if ($this._departCache[departId]) {
            setTimeout(function() {
                callback($this._departCache[departId]);
            }, 0);
        } else {
            $.getJSON($this.options.departsUserUrl, {
                departId: departId,
                pageSize: 500
            }).done(function(json) {
                if (json.status) {
                    $.msg(json.status);
                    return;
                }
                $this._departCache[departId] = json.data.userArray;
                $.setDefIcon(json.data.userArray);
                $this.updateUserCache(json.data.userArray);
                callback(json.data.userArray);
            }).fail(function(def, code, message) {});
        }
    },
    uniformData: function(data) {
        var $this = this;
        if (data && data.length) {
            if (data[0].id) {
                data = $.convertKeys(data, {
                    name: 'label',
                    id: 'value'
                });
            }
        }
        return data;
    },
    renderUserList: function(users) {
        var $this = this;
        $($.format('<li class="ui-powerinput-user" userid="{id}"><a href="javascript:;" class="ui-powerinput-userlink" userid="{id}"><img class="ui-powerinput-arvata" src="{icon}"/><span class="ui-powerinput-name">{name}</span><i class="ui-powerinput-status"></i></a></li>', users)).appendTo($this.$list);
        $this.menuSyncWith();
    },
    checkUser: function(userid) {
        this.$menu && this.$menu.data('inited') && this.$list.find('li[userid="' + userid + '"]').addClass('ui-powerinput-user-checked');
    },
    unCheckUser: function(userid) {
        this.$menu && this.$menu.data('inited') && this.$list.find('li[userid="' + userid + '"]').removeClass('ui-powerinput-user-checked');
    },
    checkAll: function() {
        this.$menu && this.$menu.data('inited') && this.$list.find('li').addClass('ui-powerinput-user-checked');
    },
    unCheckAll: function() {
        this.$menu && this.$menu.data('inited') && this.$list.find('li.ui-powerinput-user-checked').removeClass('ui-powerinput-user-checked');
    },
    menuSyncWith: $.noop
});

$.widget('mxx.singlepeopleselector', $.mxx.peopleselector, {
    options: {},
    _create: function() {
        var $this = this,
            $elem = this.element,
            opt = this.options;
        $this._super();
        $elem.acSingle({
            alignWith: $this.$container,
            source: opt.source,
            open: $.proxy($this.hideMenu, $this),
            change: function(evt, ui) {
                $this.unCheckAll();
                if (ui.item && ui.item.value) {
                    $this.checkUser(ui.item.value);
                }
            },
            onSelect: function (item) {
                $.isFunction(opt.onSuccess) && opt.onSuccess(item);
            }
        });
    },
    addMenuHandler: function() {
        var $this = this, opt = this.options;
        $this._super();
        $this.$list.on('click', 'li.ui-powerinput-user', function(evt) {
            var $li = $(this),
                userid = $li.attr('userid');
            if ($li.hasClass('ui-powerinput-user-checked')) {
                $this.unCheckUser(userid);
                $this.element.acSingle('val', null);
            } else {
                $this.unCheckAll();
                $this.checkUser(userid);
                $this.element.acSingle('val', $this._userCache[userid]);
                $.isFunction(opt.onSuccess) && opt.onSuccess($this._userCache[userid]);
            }
        });
    },
    special: function() {
        var $this = this;
        $this.$info.hide();
        $this.$bat.hide();
        $this.$clear.hide();
    },
    menuSyncWith: function() {
        var $this = this;
        var item = $this.element.acSingle('val');
        $this.unCheckAll();
        if (item) {
            $this.checkUser(item.value);
        }
    },
    _updateMenuSelection: function(uitem){
        var $this = this;
            if($this.$menu){
                $this.unCheckAll();
                $this.checkUser(uitem.value);
            }
    },
    val: function(item) {
        var $this = this;
        if (item || item === null) {
            var uitem = $this.uniformData([item])[0];
            $this.element.acSingle('val', uitem);
            $this._updateMenuSelection(uitem);
        } else {
            return $this.element.acSingle('val');
        }
    },
    _destroy: function() {
        var $this = this;
        $this.element.acSingle('destroy');
        $this._super();
    }
});
$.widget('mxx.multipeopleselector', $.mxx.peopleselector, {
    options: {

    },
    _create: function() {
        var $this = this,
            $elem = this.element,
            opt = this.options;
        $this._super();
        $elem.acMulti({
            alignWith: $this.$container,
            source: opt.source,
            open: $.proxy($this.hideMenu, $this),
            add: function(evt, item) {
                $this.checkUser(item.value);
            },
            remove: function(evt, item) {
                $this.unCheckUser(item.value);
            },
            clear: function() {
                $this.unCheckAll();
            },
            sysOnUpdate: function(){
                $this._trigger('onUpdate');
            }
        });
    },
    addMenuHandler: function() {
        var $this = this;
        $this._super();
        $this.$list.on('click', 'li.ui-powerinput-user', function(evt) {
            var $li = $(this),
                userid = $li.attr('userid');
            if ($li.hasClass('ui-powerinput-user-checked')) {
                $this.unCheckUser(userid);
                $this.element.acMulti('remove', [$this._userCache[userid]], true);
            } else {
                $this.checkUser(userid);
                $this.element.acMulti('add', [$this._userCache[userid]], true);
            }
            $this.setMenuPosition();
            $this.updateSelectedInfo();
            $this.element.focus();
        });

        $this.$checkall.on('click', function(evt) {
            if (this.checked) {
                $this.$list.find('li:not(.ui-powerinput-user-checked)').trigger('click');
            } else {
                $this.$list.find('li.ui-powerinput-user-checked').trigger('click');
            }
            $this.setMenuPosition();
            $this.updateSelectedInfo();
            $this.element.focus();
        });

        $this.$clear.on('click', function(evt) {
            $this.unCheckAll();
            $this.element.acMulti('clear', true);
            $this.setMenuPosition();
            $this.updateSelectedInfo();
            $this.element.focus();
        });
    },
    menuSyncWith: function() {
        var $this = this;
        var items = $this.element.acMulti('val');
        if (items) {
            $.each(items, function(_, item) {
                $this.checkUser(item.value);
            })
        }
        $this.updateSelectedInfo();
    },
    updateSelectedInfo: function() {
        var $this = this;
        var $users = $this.$list.find('li'),
            checkedUsers = $users.filter('.ui-powerinput-user-checked');
        $this.$info.text('已选 ' + checkedUsers.size() + ' / ' + $users.size());
    },
    val: function(items) {
        var $this = this;
        if ($.isArray(items)) {
            $this.element.acMulti('val', $this.uniformData(items));
        } else {
            return $this.element.acMulti('val');
        }
    },
    getItems: function(){
        var $this = this;
        var items = $this.element.acMulti('val');
        return $.extend(true, [], items);
    },
    setItems: function(items){
        var $this = this;
        if(!items || items.length == 0)return;
        $this.element.acMulti('val', items);
    },
    _destroy: function() {
        var $this = this;
        $this.element.acMulti('destroy');
        $this._super();
    },
    readonly: function(bool){
        var me = this;
        var elem = me.element;
         if(typeof bool == 'undefined') bool = true;

            var con = elem.parents('.ui-powerinput-box:first');

            if(bool){
                con.find('.ui-autocomplete-item-remove').hide();
                con.find('.ui-autocomplete-input').hide();
                con.find('.ui-powerinput-button').hide();
            }else{
                con.find('.ui-autocomplete-item-remove').show();
                con.find('.ui-autocomplete-input').show();
                con.find('.ui-powerinput-button').show();
            }
        }
});
//为了让multipeopleselector可以在旧版中使用，重写了val等方法
    /**
    使用说明：
        赋值：
        $myElem.snsmultipeopleselector();
        $myElem.snsmultipeopleselector('val');
        $myElem.snsmultipeopleselector('val', [...]); 
        赋值的数据格式：
        //推荐格式：
        [{
            id: '',   //人员id
            name: '', //人员名字
            icon: ''   //人员头像url
        }]
        //旧格式，不推荐使用
        [{
            key: '',   //人员id
            value: '', //人员名字
            icon: ''   //人员头像url
        }]

    **/
    $.widget('mxx.snsmultipeopleselector', $.mxx.multipeopleselector, {
        options: {
            sysTreeMenuOpened: function(e, menu) {
                menu.addClass('schedule-powerinput-menu'); //css高度，在旧版中需特殊处理一下
            },
            dataStd: '2.0'
        },
        _init: function() {
            var me = this;
            me.element.data('wtype', 'snsmultipeopleselector');
            me._super();
        },
        val: function(items) {
            var me = this;
            if ($.isArray(items)) { //set            
                var newItems = [];
                $.each(items, function(i, item) {
                    newItems.push(me._convertItem(item));
                });
                me._super(newItems);
            } else { //get
                var arr = me._super();
                if (items === true) {
                    return arr;
                } else {
                    var result = []
                    $.each(arr, function(i, item) {
                        result.push(item.value);
                    });
                    return result;
                }
            }
        },
        detailVal: function() {
            var me = this;
            return me.val(true); //数组中装的是详情object，而非id
        },
        getItems: function(dataStd){
            var me = this;
            var opt = me.options;
            var items = me._super();
            if(typeof dataStd == 'undefined')dataStd = opt.dataStd;
            if(parseInt(dataStd) < 3) return items;//遵循3.0之前的label，value的数据格式
            var newItems = [];
            $(items).each(function(i, item){
                newItems.push({
                    id: item.value,
                    name: item.label,
                    icon: item.icon
                });
            });
            return newItems;
        },
        addItem: function(arr) {
            var me = this;
            if (!arr) return;
            var newItems = [];
            if ($.isArray(arr)) {
                $.each(arr, function(i, item) {
                    newItems.push(me._convertItem(item));
                });
            } else {
                newItems.push(me._convertItem(arr));
            }
            me.element.acMulti('add', newItems)
        },
        _convertItem: function(item) {
            //新版数据格式，id，name，icon
            if(item.id && !item.key){ item.key = item.id; }
            if(item.name && !item.value){ item.value = item.name; }
            return {
                label: item.value,
                value: item.key,
                pic: item.pic,
                icon: item.icon
            };
        }
    });
$.widget('mxx.salespeopleselector', $.mxx.multipeopleselector, {
    options: {},
    loadDepartData: function(callback) {
        var $this = this;
        $.getJSON("/json/crm_salesgoal/departAndUser.action", {
            pageSize: 500
        }).done(function(json) {
            if (json.status) {
                $.msg(json.status);
                return;
            }
            $this.departData = json.data;
            $.setDefIcon(json.data.userArray);
            $this.updateUserCache(json.data.userArray);
            $this._departCache[$this.departData.departId] = $this.departData.userArray;
            callback();
        }).fail(function(def, code, message) {});
    },
    getUsersByDepartId: function(departId, callback) {
        var $this = this;
        if ($this._departCache[departId]) {
            setTimeout(function() {
                callback($this._departCache[departId]);
            }, 0);
        } else {
            $.getJSON("/json/crm_salesgoal/UsersByDepart.action", {
                departId: departId,
                pageSize: 500
            }).done(function(json) {
                if (json.status) {
                    $.msg(json.status);
                    return;
                }
                $this._departCache[departId] = json.data.userArray;
                $.setDefIcon(json.data.userArray);
                $this.updateUserCache(json.data.userArray);
                callback(json.data.userArray);
            }).fail(function(def, code, message) {});
        }
    },
    clear: function() {
        var $this = this;
        $this.element.acMulti('clear');
    }
});
$.widget('mxx.salessinglepeopleselector', $.mxx.singlepeopleselector, {
    options: {
        belongId:3
    },
    loadDepartData: function(callback) {
        var $this = this,opt = $this.options;
        $.getJSON("/json/sns_privatemsg/sales-departs.action?belongId=" +opt.belongId , {
            pageSize: 500
        }).done(function(json) {
            if (json.status) {
                $.msg(json.status);
                return;
            }
            $this.departData = json.data;
            $.setDefIcon(json.data.userArray);
            $this.updateUserCache(json.data.userArray);
            $this._departCache[$this.departData.departId] = $this.departData.userArray;
            callback();
        }).fail(function(def, code, message) {});
    },
    clear: function() {
        var $this = this;
        $this.element.acMulti('clear');
    }
});

/**
使用简介：
[html]:  <div id="treepanel"></div>
[init]: 
         $('#treepanel').multitreepanel({
                cascadeChecking: true/false,
                source: '/admin/dimension/tree.action?dimType=' + 3, //数据格式：[{"id":1002,"parent":"#","text":"中国","depth":0,"order":1,"open":true},{"id":1201,"parent":1002,"text":"辽宁","depth":1,"order":1,"open":false},{"id":1204,"parent":1201,"text":"沈阳","depth":2,"order":1,"open":false},{"id":1702,"parent":1204,"text":"新民","depth":3,"order":1,"open":false},{"id":1701,"parent":1201,"text":"大连","depth":2,"order":2,"open":false},{"id":1202,"parent":1002,"text":"北京","depth":1,"order":2,"open":false},{"id":1203,"parent":1002,"text":"上海","depth":1,"order":3,"open":false},{"id":1703,"parent":1002,"text":"安徽","depth":1,"order":4,"open":false},{"id":1704,"parent":1703,"text":"合肥","depth":2,"order":3,"open":false},{"id":1705,"parent":1703,"text":"蚌埠","depth":2,"order":4,"open":false},{"id":1706,"parent":1703,"text":"六安","depth":2,"order":5,"open":false}]
                onInit: function (){} //加载后触发
         });
[method]:
         $('#treepanel').multitreepanel('beCascadeChecking', true/false);//切换checkbox行为
         $('#treepanel').multitreepanel('val', [{value: 1202},{value:1203},{value:1701}]);//设置数据
         $('#treepanel').multitreepanel('val');//获取数据

**/
$.widget('zhl.multitreepanel', $.mxx.treeselector, {
    options: {
        cascadeChecking: false,//当为false时，勾选父节点的checkbox后不会全部勾选子节点
        onInit: function(){}//渲染成功后触发
    },
    inited: false,
    rktree: null,
    _create: function() {
        var $this = this,
            $elem = this.element,
            opt = this.options;
            if (!$elem.data('mtree-inited')) {
                $this.loadData(function() {
                    $this._renderTree();
                });
            }
    },
    getTreeInstance: function(){
        return this.rktree.rktree('getTreeInstance');
    },
    _getSource: function(){
        var $this = this;
        var selected = {};
        return $.map($this.treedata, function(o) {
                if (!o.state) {
                    o.state = {};
                }
                o.state.selected = !!selected[o.id];
                return o;
            });
    },
    rendering: false,
    source: {},
    _renderTree: function(source) {
        var $this = this,
            $elem = this.element,
            opt = this.options;
        $this.rendering = true;
        var initSource = (typeof source == 'undefined' ? $this._getSource() : source);
        $this.source = initSource;
        $elem.append('<div class="rktree"></div>');
        $this.rktree = $elem.find('div.rktree');
        $this.rktree.rktree({
            source: initSource,
            jstreeOptions: {
                plugins: ['checkbox'],
                checkbox: {
                    three_state: false
                    //cascade: (opt.cascadeChecking ? 'down'/*仅向下全勾选*/ : '')
                }
            },
            jstreeEvents: {
                'ready.jstree': function(){
                    if(!$this.inited){                         
                            $this._trigger('onInit');
                            $this.inited = true;
                    }                    
                },
                'loaded.jstree': function(){
                    $this.rendering = false;
                },
                'select_node.jstree': function(e, node){//由于check_node事件不管用，因此暂时用select_node代替，在该版本上两者是等价的
                    if(!$this.settingValue){//val()方法也会触发select_node，此时不应当触发选择子节点
                        $this._checkSubNodes(node);
                    }
                }
            }
        });
        $elem.data('mtree-inited', true);
    },
    _checkSubNodes: function(node){
        var $this = this,
            $elem = this.element,
            opt = this.options;
        if(!opt.cascadeChecking)return;//若为FALSE，则用three_status默认行为即可
        var treeInstance = $this.getTreeInstance();
        var _node = node.node;
        var children = _node.children;
        var results = [];
        $this._getAllChildren(children, results);//递归将子节点id刷到results上
        treeInstance.check_node(results);
    },
    _getAllChildren: function(children, results){
        var $this = this;
        var treeInstance = $this.getTreeInstance();
        var allvalue = $this.currentValue;
        for(var z=0;z<children.length;z++){
                results.push(children[z]+'');
        }
        $.each(children, function(i, id){
            var sub = treeInstance.get_node(id).children;
            $this._getAllChildren(sub, results);
        });
    },
    beCascadeChecking: function(cascadeChecking){//选中checkbox后，子节点是否也选中
        var $this = this,
            $elem = this.element,
            opt = this.options;
        opt.cascadeChecking = cascadeChecking;
        // var items = $this.val();
        // $this.val(items);
    },
    _getCheckedNodes: function(){
        var $this = this,
            $elem = this.element,
            opt = this.options;

        var checkedlist = $this.rktree.rktree('getTreeInstance').get_checked();
        var results = [];
        $.each(checkedlist, function(i, id){
            results.push({
                value: id
            });
        })
        return results;
    },
    currentValue: null,
    settingValue: false,
    val: function(items){
        var $this = this,
            $elem = this.element,
            opt = this.options;
        //GET
        if(typeof items == 'undefined'){
            return ($this.rendering ? $this.currentValue : $this._getCheckedNodes());
        }
        //SET
        $this.settingValue = true;
        var treeInstance = $this.getTreeInstance();
        treeInstance.uncheck_all();
        var map = [];
        $.each(items, function(i, it){
            var v = it.value+'';
            map.push(v);
        });
        treeInstance.check_node(map);
        $this.settingValue = false;

        // return;
        // $this.currentValue = items;
        // var source = $this._getSource();
        // var map = [];
        // $.each(items, function(i, it){
        //     map[it.value+''] = true;
        // });
        // $.each(source, function(i, src){
        //     if(map[src.id+'']){
        //         src.state.selected = true;
        //     }else{
        //         src.state.selected = false;
        //     }
        // });
        // $this.rktree.rktree('destroy');
        // $this._renderTree(source);
    }
});
//多选控件
//source接收的数据格式：{"list":[{"id":2101,"name":"aaaaa221bb"},{"id":2305,"name":"dddd"},{"id":2306,"name":"测试审批组1"},{"id":3001,"name":"测试审批组2"},{"id":3002,"name":"打掉的"}]}
$.widget('mxx.multiselector', $.mxx.treeselector, {
    options: {},
    selectedClass: 'selected_checkbox',
    _create: function() {
        var $this = this,
            $elem = this.element,
            opt = this.options;
        $this._super();
        $elem.acMulti({
            source: opt.source,
            alignWith: $this.$container,
            remove: function(e, item){
                var id = item.id ? item.id : item.value;
                $this.setMenuPosition();
                $this.$menu.find('li[val="'+id+'"]').removeClass($this.selectedClass);
            }
        });
        $elem.prop('readonly', 'true').closest('.ui-autocomplete-box').click(function(evt){
            $this.toggleMenu(evt);
        });
    },
    initMenu: function() {
        var $this = this,
            $elem = this.element,
            opt = this.options;
        if (!$this.$menu.data('inited')) {
            if(typeof opt.source == 'string'){//is url
                $.getJSON( opt.source, function( json ) {
                    $this._renderMenu(json);
                    if(opt.onResponse)opt.onResponse(json);
                });
            }else{
                var json = opt.source;
                $this._renderMenu(json);
                if(opt.onResponse)opt.onResponse(json);
            }
        }
    },
    _renderMenu: function(json) {
        var $this = this,
            $elem = this.element,
            opt = this.options;
        $this.$menu.html(template('multiselectorSelectorMenuTpl', json)).on('click', 'li', function(){
            var li = $(this);
            var iel = li.find('i');
            if(li.hasClass($this.selectedClass)){
                li.removeClass($this.selectedClass);
            }else{
                li.addClass($this.selectedClass);
            }
            var selecteddata = $this.$menu.find('li.' + $this.selectedClass);
            var items = [];
            selecteddata.each(function(i, el){
                el = $(el);
                items.push({
                    id: el.attr('val'),
                    label: el.text()
                });
            });
            $this.setMenuPosition();
            $this.element.acMulti('val', items);
        });
        $this.$menu.addClass('approver-group-dropdown').mCustomScrollbar({
            theme:"3d-dark",
            mouseWheel:{ 
                    scrollAmount: (isMac ? 3 : 100)
            }
        });
        $this._markAsSelected();
        $this.$menu.data('inited', true);
    },
    val: function(items) {
        var $this = this;
        if ($.isArray(items)) {
            $this.element.acMulti('val', $this.uniformData(items));
        } else {
            return $this.element.acMulti('val');
        }
    },
    _markAsSelected: function(){
        var $this = this,
            $elem = this.element;
        var items = $this.val();

        var lis = $this.$menu.find('li[val]');
        for(var i = 0, len = items.length; i < len; i++){
            lis.filter('[val="'+items[i].value+'"]').addClass($this.selectedClass);
        }
    },
    _handleMenuOpen: function(){
        var $this = this,
            $elem = this.element;
        $this._markAsSelected();
    }
});
// $(function() {
//     if (location.href.split('crm_account').length > 1 || location.href.split('home').length > 1 || location.href.split('platform_schedule').length > 1) {
//         var html = '<div id="div_tree_single" class="mcsb-container" style="position:absolute; top:10px; left:200px; background: #fff; z-index: 999; width: 300px; "><input id="ipt_tree_single"/></div>';
//         $(html).appendTo('body').find('input').singletreeselector({
//             source: '/json/crm_dimension/dim-tree.action?belongId=1&dimType=1'
//         });
//         var html = '<div id="div_tree_multi" class="mcsb-container" style="position:absolute; top:10px; left:600px; background: #fff; z-index: 999; width: 300px; "><input id="ipt_tree_multi"/></div>';
//         $(html).appendTo('body').find('input').multitreeselector({
//             source: 'os' || genTreeTestData()
//         });

//         var html = '<div id="div_people_multi" class="mcsb-container" style="position:absolute; top:10px; left:1000px; background: #fff; z-index: 999; width: 300px; "><input id="ipt_people_multi"/></div>';
//         $(html).appendTo('body').find('input').singlepeopleselector({});
//         // $(html).appendTo('body').find('input').multipeopleselector({});

//         var html = '<div id="div_tree_single_noac" class="mcsb-container" style="position:absolute; top:10px; left:1400px; background: #fff; z-index: 999; width: 300px; "><a href="javascript:;">这是个测试</a></div>';
//         var $html = $(html).appendTo('body').singletreepicker({
//             source: 'os',
//             select: function(evt, item) {
//                 $html.find('a').text(item.label);
//             }
//         });

//         // var data = [{"id": 10401,"parent": "#","pinyin": "quangongsi","text": "全公司"}, {"id": 20002,"parent": 10401,"pinyin": "chanpinyanfa","text": "产品研发"}, {"id": 30101,"parent": 20002,"pinyin": "web","text": "WEB"}, {"id": 30104,"parent": 30101,"pinyin": "java","text": "JAVA"}, {"id": 30105,"parent": 30101,"pinyin": "js","text": "JS"}, {"id": 30102,"parent": 20002,"pinyin": "mobile","text": "MOBILE"}, {"id": 30103,"parent": 20002,"pinyin": "test","text": "TEST"}, {"id": 20004,"parent": 10401,"pinyin": "xiaoshoubu","text": "销售部"}, {"id": 30001,"parent": 20004,"pinyin": "zhixiao","text": "直销"}];
//         // $('#ipt_tree_single').singletreeselector('val', data[3]);
//         // $('#ipt_tree_multi').multitreeselector('val', data.slice(-5));
//         // $('#ipt_tree_multi').multitreeselector('val');

//     }
// });