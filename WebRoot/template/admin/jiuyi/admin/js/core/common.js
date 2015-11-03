(function($) {
    var rRoute, rFormat;
    /**
     * 在一个对象中查询指定路径代表的值，找不到时返回undefined
     *
     * @param {Object}
     *            obj 被路由的对象
     * @param {Object}
     *            path 路径
     */
    $.route = function(obj, path) {
        obj = obj || {};
        var m;
        (rRoute || (rRoute = /([\d\w_]+)/g)).lastIndex = 0;
        while ((m = rRoute.exec(path)) !== null) {
            obj = obj[m[0]];
            if (obj == undefined) {
                break;
            }
        }
        return obj;
    };
    /**
     * 格式化一个字符串
     */
    $.format = function() {
        var args = $.makeArray(arguments),
            str = String(args.shift() || ""),
            ar = [],
            first = args[0],
            callee = arguments.callee;
        args = $.isArray(first) ? first : typeof(first) == 'object' ? args : [args];
        $.each(args, function(i, o) {
            ar.push(str.replace(rFormat || (rFormat = /\{([\d\w\.]+)((\W)\3[\d\D]+?\3\3)?\}/g), function(m, n, t, v, r) {
                v = n === 'INDEX' ? i : n.indexOf(".") < 0 ? o[n] : ((r = $.route(o, n)) === undefined ? $.route(window, n) : r);
                return v === undefined ? m : (t ? callee(t.slice(2, -2), v) : $.isFunction(v) ? v.call(o, n) : v);
            }));
        });
        return ar.join('');
    };
    var rlenw = /[^\x00-\xff]/g;
    /**
     * 计算字符串的字节长度
     *
     * @param {Object}
     *            str
     */
    $.lenW = function(str) {
        return str.replace(rlenw, "**").length;
    };
    /**
     * 对一个字符串截取指定字节长度
     *
     * @param {Object}
     *            str 被截取的字符串
     * @param {Object}
     *            bitLen 字节长度
     * @param {Object}
     *            tails 补充结尾字符串
     */
    $.ellipsis = function(str, bitLen, tails) {
        str = String(str);
        if (isNaN(bitLen -= 0)) {
            return str;
        }
        var len = str.length,
            i = Math.min(Math.floor(bitLen / 2), len),
            cnt = $.lenW(str.slice(0, i));
        for (; i < len && cnt < bitLen; i++) {
            cnt += 1 + (str.charCodeAt(i) > 255);
        }
        return str.slice(0, cnt > bitLen ? i - 1 : i) + (i < len ? (tails || '') : '');
    };
    /**
     * 随机方法，不传参数时，返回一个[0,1)随机数 传一个参数时，表示传入的数组，在数组中随机取一个元素 传两个参数时，表示一个区间，返回这个[a,
     * b)区间里的数值
     *
     * @param {Object}
     *            a
     * @param {Object}
     *            b
     */
    $.r = function(a, b) {
        var r = Math.random(),
            l = arguments.length;
        return l == 2 ? (a + Math.floor(r * (b - a))) : l == 1 ? a[Math.floor(r * a.length)] : r;
    };
    /**
     * 将一个数组随机排序
     *
     * @param {Object}
     *            ar
     */
    $.randomArray = function(ar) {
        var rslt = [],
            len;
        while (len = ar.length) {
            rslt.push(ar.splice(Math.floor(Math.random() * len), 1)[0]);
        }
        return rslt;
    };
    /**
     * 按数组某一字段为索引，构建hash表
     *
     * @param {Object}
     *            ar
     */
    $.searchIndex = function(ar, key) {
        key = key || 'id';
        var oRslt = {},
            i = 0,
            len = ar.length;
        for (; i < len; i++) {
            oRslt[ar[i][key]] = ar[i];
        }
        return oRslt;
    };
    $.keys = function(obj) {
        if (Object.keys) {
            return Object.keys(obj);
        }
        var ar = [];
        for (var k in obj) {
            ar.push(k);
        }
        return ar;
    };
    $.values = function(obj) {
        var ar = [];
        for (var k in obj) {
            ar.push(obj[k]);
        }
        return ar;
    };
    $.convertKeys = function(obj, keyMap) {
        if (!$.isArray(obj)) {
            obj = $.values(obj);
        }
        var ar = [];
        for (var i = 0, len = obj.length; i < len; i++) {
            var tmp = {},
                o = obj[i];
            for (var k in o) {
                tmp[keyMap[k] || k] = o[k];
            }
            ar.push(tmp);
        }
        return ar;
    };
    $.postJson = function(url, data, callback) {
        return $.post(url, data, callback, "json");
    };
    var queryJson, str;
    /**
     * 从url中取得参数的值
     * @param {Object} name
     */
    $.query = function(name) {
        if (!queryJson) {
            queryJson = {};
            if (str = location.search.slice(1) + '&' + location.hash.slice(1)) {
                $.each(str.split('&'), function(i, s, key, value) {
                    s = s.split('='), key = s[0], value = s[1];
                    if (key in queryJson) {
                        if ($.isArray(queryJson[key])) {
                            queryJson[key].push(value);
                        } else {
                            queryJson[key] = [queryJson[key], value];
                        }
                    } else {
                        queryJson[key] = value;
                    }
                });
            }
        }
        return queryJson[name];
    };
    $.setDefIcon = function(users) {
        for (var k in users) {
            users[k].icon = users[k].icon || window.DEFAULTS.USER_DEFAULT_ICON;
        }
    };
    // 最多保留limit位小数
    $.numFixed = function(num, limit) {
        var n = Math.pow(10, limit);
        return Math.round(num * n) / n;
    }

})(jQuery);
$.fn.trimHtml = function(oFmt) {
    var html = $.trim(this.html());
    if (oFmt) {
        html = $.format(html, oFmt);
    }
    return html;
};
$.fn.$html = function(oFmt) {
    return $(this.trimHtml(oFmt));
};
$.fn.autoTextarea = function(options) {
    var defaults = {
        maxHeight: null, //文本框是否自动撑高，默认：null，不自动撑高；如果自动撑高必须输入数值，该值作为文本框自动撑高的最大高度
        minHeight: $(this).height() //默认最小高度，也就是文本框最初的高度，当内容高度小于这个高度的时候，文本以这个高度显示
    };
    var opts = $.extend({}, defaults, options);
    return $(this).each(function() {
        $(this).bind("paste cut keydown keyup focus blur", function() {
            var height, style = this.style;
            this.style.height = opts.minHeight + 'px';
            if (this.scrollHeight > opts.minHeight) {
                if (opts.maxHeight && this.scrollHeight > opts.maxHeight) {
                    height = opts.maxHeight;
                    //                        style.overflowY = 'scroll';
                } else {
                    height = this.scrollHeight;
                    //                        style.overflowY = 'hidden';
                }
                style.height = height + 'px';
            }
        });
    });
};
$.fn.focusText = function() {
    var setpos = function(o) {
        if (o.setSelectionRange) {
            setTimeout(function() {
                o.setSelectionRange(o.value.length, o.value.length);
                o.focus()
            }, 0)
        } else if (o.createTextRange) {
            var textrange = o.createTextRange();
            textrange.moveStart("character", o.value.length);
            textrange.moveEnd("character", 0);
            textrange.select();
        }
    };
    setpos(this[0]);
};

(function() {
    var otherElementEvtIdSequece = 1;
    //其他element（即排除当前ele）所触发的事件
    $.fn.otherElementEvt = function(evt, callback) {
        var $target = $(this);
        if (!$target.data("bindOtherElementEvt")) {
            $target.data("bindOtherElementEvt", true);
            if (!$target.attr("id")) {
                $target.attr("id", "other_element_evt_" + (otherElementEvtIdSequece++));
            }
            var eleId = $target.attr("id");
            $(document).bind(evt, function(event) { //点击页面其他地方关闭参考页
                var e = event || window.event;
                var elem = e.srcElement || e.target;
                while (elem) {
                    if (elem.id == eleId) {
                        return;
                    }
                    elem = elem.parentNode;
                }
                callback();
            });
        }
        return $target;
    };
})();

/**
 * jQuery plugin: 获取/设置text/textarea里的光标/选中位置
 * @param {Object} begin
 * @param {Object} end
 */
$.fn.caret = function(begin, end) {
    if (this.length == 0)
        return;
    if (typeof begin == 'number') {
        end = (typeof end == 'number') ? end : begin;
        return this.each(function() {
            if (this.setSelectionRange) {
                this.focus();
                this.setSelectionRange(begin, end);
            } else if (this.createTextRange) {
                var range = this.createTextRange();
                range.collapse(true);
                range.moveEnd('character', end);
                range.moveStart('character', begin);
                range.select();
            }
        });
    } else {
        var text = '';
        if (this[0].setSelectionRange) {
            begin = this[0].selectionStart;
            end = this[0].selectionEnd;
            text = this[0].value.substring(begin, end);
        } else if (document.selection && document.selection.createRange) {
            var range = document.selection.createRange();
            text = range.text;
            begin = 0 - range.duplicate().moveStart('character', -100000);
            end = begin + range.text.length;
        }
        return {
            begin: begin,
            end: end,
            text: text
        };
    }
};

/*! Copyright (c) 2013 Brandon Aaron (http://brandon.aaron.sh)
 * Licensed under the MIT License (LICENSE.txt).
 *
 * Version: 3.1.9
 *
 * Requires: jQuery 1.2.2+
 */
(function($) {

    var toFix = ['wheel', 'mousewheel', 'DOMMouseScroll', 'MozMousePixelScroll'],
        toBind = ('onwheel' in document || document.documentMode >= 9) ? ['wheel'] : ['mousewheel', 'DomMouseScroll', 'MozMousePixelScroll'],
        slice = Array.prototype.slice,
        nullLowestDeltaTimeout, lowestDelta;

    if ($.event.fixHooks) {
        for (var i = toFix.length; i;) {
            $.event.fixHooks[toFix[--i]] = $.event.mouseHooks;
        }
    }

    var special = $.event.special.mousewheel = {
        version: '3.1.9',

        setup: function() {
            if (this.addEventListener) {
                for (var i = toBind.length; i;) {
                    this.addEventListener(toBind[--i], handler, false);
                }
            } else {
                this.onmousewheel = handler;
            }
            // Store the line height and page height for this particular element
            $.data(this, 'mousewheel-line-height', special.getLineHeight(this));
            $.data(this, 'mousewheel-page-height', special.getPageHeight(this));
        },

        teardown: function() {
            if (this.removeEventListener) {
                for (var i = toBind.length; i;) {
                    this.removeEventListener(toBind[--i], handler, false);
                }
            } else {
                this.onmousewheel = null;
            }
        },

        getLineHeight: function(elem) {
            return parseInt($(elem)['offsetParent' in $.fn ? 'offsetParent' : 'parent']().css('fontSize'), 10);
        },

        getPageHeight: function(elem) {
            return $(elem).height();
        },

        settings: {
            adjustOldDeltas: true
        }
    };

    $.fn.extend({
        mousewheel: function(fn) {
            return fn ? this.bind('mousewheel', fn) : this.trigger('mousewheel');
        },

        unmousewheel: function(fn) {
            return this.unbind('mousewheel', fn);
        }
    });

    function handler(event) {
        var orgEvent = event || window.event,
            args = slice.call(arguments, 1),
            delta = 0,
            deltaX = 0,
            deltaY = 0,
            absDelta = 0;
        event = $.event.fix(orgEvent);
        event.type = 'mousewheel';

        // Old school scrollwheel delta
        if ('detail' in orgEvent) {
            deltaY = orgEvent.detail * -1;
        }
        if ('wheelDelta' in orgEvent) {
            deltaY = orgEvent.wheelDelta;
        }
        if ('wheelDeltaY' in orgEvent) {
            deltaY = orgEvent.wheelDeltaY;
        }
        if ('wheelDeltaX' in orgEvent) {
            deltaX = orgEvent.wheelDeltaX * -1;
        }

        // Firefox < 17 horizontal scrolling related to DOMMouseScroll event
        if ('axis' in orgEvent && orgEvent.axis === orgEvent.HORIZONTAL_AXIS) {
            deltaX = deltaY * -1;
            deltaY = 0;
        }

        // Set delta to be deltaY or deltaX if deltaY is 0 for backwards compatabilitiy
        delta = deltaY === 0 ? deltaX : deltaY;

        // New school wheel delta (wheel event)
        if ('deltaY' in orgEvent) {
            deltaY = orgEvent.deltaY * -1;
            delta = deltaY;
        }
        if ('deltaX' in orgEvent) {
            deltaX = orgEvent.deltaX;
            if (deltaY === 0) {
                delta = deltaX * -1;
            }
        }

        // No change actually happened, no reason to go any further
        if (deltaY === 0 && deltaX === 0) {
            return;
        }

        if (orgEvent.deltaMode === 1) {
            var lineHeight = $.data(this, 'mousewheel-line-height');
            delta *= lineHeight;
            deltaY *= lineHeight;
            deltaX *= lineHeight;
        } else if (orgEvent.deltaMode === 2) {
            var pageHeight = $.data(this, 'mousewheel-page-height');
            delta *= pageHeight;
            deltaY *= pageHeight;
            deltaX *= pageHeight;
        }

        // Store lowest absolute delta to normalize the delta values
        absDelta = Math.max(Math.abs(deltaY), Math.abs(deltaX));

        if (!lowestDelta || absDelta < lowestDelta) {
            lowestDelta = absDelta;

            // Adjust older deltas if necessary
            if (shouldAdjustOldDeltas(orgEvent, absDelta)) {
                lowestDelta /= 40;
            }
        }

        // Adjust older deltas if necessary
        if (shouldAdjustOldDeltas(orgEvent, absDelta)) {
            // Divide all the things by 40!
            delta /= 40;
            deltaX /= 40;
            deltaY /= 40;
        }

        // Get a whole, normalized value for the deltas
        delta = Math[delta >= 1 ? 'floor' : 'ceil'](delta / lowestDelta);
        deltaX = Math[deltaX >= 1 ? 'floor' : 'ceil'](deltaX / lowestDelta);
        deltaY = Math[deltaY >= 1 ? 'floor' : 'ceil'](deltaY / lowestDelta);

        // Add information to the event object
        event.deltaX = deltaX;
        event.deltaY = deltaY;
        event.deltaFactor = lowestDelta;
        event.deltaMode = 0;

        args.unshift(event, delta, deltaX, deltaY);

        if (nullLowestDeltaTimeout) {
            clearTimeout(nullLowestDeltaTimeout);
        }
        nullLowestDeltaTimeout = setTimeout(nullLowestDelta, 200);

        return ($.event.dispatch || $.event.handle).apply(this, args);
    }

    function nullLowestDelta() {
        lowestDelta = null;
    }

    function shouldAdjustOldDeltas(orgEvent, absDelta) {
        return special.settings.adjustOldDeltas && orgEvent.type === 'mousewheel' && absDelta % 120 === 0;
    }

})(jQuery);

(function() {
    var defOption = {
        dialogClass: 'pop_up_02',
        minWidth: 400,
        modal: true,
        resizable: false,
        title: '提示'
    };

    var alertContent = ['<div><div class="pop_up_content"><div class="confirm clear"><p>', '</p></div></div><div class="pop_up_bottom"><div class="pop_up_button"><a btn="dialogConfirm" href="javascript:;" class="green_button">确定</a></div></div></div>'];
    $.alert = function(html, opt) {
        $(alertContent.join(html)).dialog($.extend(true, {}, defOption, {
            //dialogClass : 'pop_up_02',
            create: function(event) {
                var $dialog = $(event.target);
                $dialog.find('a[btn="dialogConfirm"]').on('click', function() {
                    $dialog.dialog("close");
                });
            }
        }, opt));
    };

    var confirmContent = ['<div><div class="pop_up_content"><div class="confirm clear"><p>', '</p></div></div><div class="pop_up_bottom"><div class="pop_up_button"><a btn="dialogCancel" href="javascript:;" class="cancel">取消</a><a btn="dialogConfirm" href="javascript:;" class="green_button">确定</a></div></div></div>'];
    $.confirm = function(html, onConfirm, opt) {
        $(confirmContent.join(html)).dialog($.extend(true, {}, defOption, {
            //dialogClass : 'pop_up_02',
            create: function(evt) {
                var $dialog = $(evt.target);
                $dialog.find('a[btn="dialogCancel"]').on('click', function() {
                    $dialog.dialog("close");
                });
                $dialog.find('a[btn="dialogConfirm"]').on('click', function() {
                    $.isFunction(onConfirm) && onConfirm();
                    $dialog.dialog("close");
                }).focus();
            },
            open: function(evt) {
                var $dialog = $(evt.target),
                    $layout = $dialog.parent().prev();
                $dialog.parent().add($layout).on('click', function(evt) {
                    if($dialog.parent().is(':visible')){
                        evt.stopPropagation();
                    }                    
                });
            }
        }, opt));
    };

    $.msg = function(html, type, duration, posOption, onClose) {
        var $msg = $(['<div class="system_prompt"><div class="', type ? 'sucess' : 'error', '">', html, '<span class="hide"></span></div></div>'].join('')).hide().appendTo('body');
        $msg.show('fade', 'fast').delay(duration || 2000).hide('fade', 'fast', function() {
            $msg.remove();
            $.isFunction(onClose) && onClose();
            $msg = null;
        }).position($.extend({

        }, posOption));
        $msg.find('span.hide').on('click', function() {
            $msg.stop(true, true).hide();
        });
    };
    $.msgYes = function(html, duration, posOption, onClose) {
        $.msg(html, 1, duration, posOption, onClose);
    };
    $.msgNo = function(html, duration, posOption, onClose) {
        $.msg(html, 0, duration, posOption, onClose);
    };

    $.popup = function(title, html, options) {
        $(html).popup(options);
    };

    $.widget('mxx.popup', $.ui.dialog, {
        options: $.extend({
            autofocus: false,
            panel: false,
            actions: {
                close: function(evt, dialog) {
                    dialog.close(evt);
                    $(window).off('resize.popup');
                }
            }
        }, defOption),
        _create: function() {
            var $this = this,
                $elem = this.element,
                opt = this.options;
            $this._super();
            $this.customContent = $this.element.find('>div.pop_up_content');
            if (opt.panel) {
                $this.customContent.jqxPanel({
                    autoUpdate: true,
                    scrollBarSize: 2,
                    width: opt.width,
                    sizeMode: "fixed"
                });
            }
            $this.customBottom = $this.element.find('>div.pop_up_bottom');
            $this.customButtons = $this.customBottom.find('[act]');
            $this.customButtons.on('click', function(evt) {
                evt.preventDefault();
                var $btn = $(this),
                    act = $btn.attr('act'),
                    fn = opt.actions[act];
                $.isFunction(fn) && fn.apply(this, [evt, $this]);
            });
            $(window).on('resize.popup', function() {
                $this.refresh();
            });
        },
        close: function(event) {
            var $this = this,
                $elem = this.element,
                opt = this.options;
            var activeElement,
                that = this;

            if (!this._isOpen || this._trigger("beforeClose", event) === false) {
                return;
            }

            this._isOpen = false;
            this._focusedElement = null;
            this._destroyOverlay();
            this._untrackInstance();

            if (!this.opener.filter(":focusable").focus().length) {

                // support: IE9
                // IE9 throws an "Unspecified error" accessing document.activeElement from an <iframe>
                try {
                    activeElement = this.document[0].activeElement;

                    // Support: IE9, IE10
                    // If the <body> is blurred, IE will switch windows, see #4520
                    if (activeElement && activeElement.nodeName.toLowerCase() !== "body") {

                        // Hiding a focused element doesn't trigger blur in WebKit
                        // so in case we have nothing to focus on, explicitly blur the active element
                        // https://bugs.webkit.org/show_bug.cgi?id=47182
                        $(activeElement).blur();
                    }
                } catch (error) {}
            }

            $(window).off('resize.popup');
            $this._hide($this.uiDialog, $this.options.hide, function() {
                $this._trigger("close", event);
                $this.uiDialog.remove();
                $this.uiDialog = null;
            });
        },
        refresh: function() {
            var $this = this,
                $elem = this.element,
                opt = this.options;

            if (opt.panel) {
                var height = 0;
                $this.customContent.jqxPanel('getInstance').content.children(':visible').each(function() {
                    height += $(this).outerHeight(true);
                });
                $this.customContent.jqxPanel({
                    height: Math.min(height + 5, $(window).height() - 110)
                });
            }
            $this._position();
        },
        _title: function(title) {
            if (!this.options.title) {
                title.html("&#160;");
            }
            title.html(this.options.title);
            $.isFunction(this.options.titleCallback) && this.options.titleCallback.call(this, title);
        },
        setTitle: function(html, titleCallback) {
            this.options.title = html;
            this.options.titleCallback = titleCallback;
            this._title(this.uiDialogTitlebar.find('span.ui-dialog-title'));
        }
    });
})();

(function() {
    $.widget('mxx.autoComplete', {
        options: {
            url: '/search/u.action?pageNo=1&s=m&key=',
            inputSelector: 'input[type="text"]',
            multi: false,
            width: 120,
            target: '',
            at: false,
            relatePos: 'insertBefore',
            template: '<span class="js-ac-result"><a><img width="16" src="{icon}">{label}</a><a href="javascript:;" class="js-ac-del">x</a></span>',
            enableCache: true,
            formatData: function(data) {
                return $.map(data, function(o) {
                    var $el = $(o.value),
                        icon = $el.find('img').attr('src'),
                        name = $el.find('span').text();
                    return {
                        label: name,
                        value: o.key,
                        icon: icon
                    };
                });
            },
            minLength: 0,
            autoFocus: true,
            appendTo: 'body',
            renderItem: function(ul, item) {
                $(ul).css('zIndex', 70000);
                return $('<li></li>').data("item.autocomplete", item).append('<a class="ac_people_name"><img src="' + item.icon + '">' + item.label + '</a>').appendTo(ul);
            }
        },
        _create: function() {
            var $this = this,
                $elem = this.element,
                opt = this.options;

            var cache = {};
            $this.input = $elem.find(opt.inputSelector).first();
            if (opt.width > 0) {
                $this.input.width(opt.width);
            }
            opt.target = opt.target ? $(opt.target, $elem) : $this.input;
            if (opt.at) {
                // don't navigate away from the field on tab when selecting an item
                $this.input.bind("keydown", function(event) {
                    if (event.keyCode === $.ui.keyCode.TAB && $(this).data("ui-autocomplete").menu.active) {
                        event.preventDefault();
                    }
                });
            }
            $this.input.autocomplete($.extend(true, opt, {
                source: function(request, response) {
                    var term = request.term;
                    if (opt.at) {
                        var pos = $this.input.caret().begin;
                        pos = term.indexOf(' ', pos);
                        if (pos == -1) {
                            pos = term.length;
                        }
                        var front = term.slice(0, pos),
                            end = term.slice(pos);
                        var posAt = front.lastIndexOf('@'),
                            posBlank = front.lastIndexOf(' ');
                        if (posAt == -1 || posBlank > posAt) {
                            return response();
                        } else {
                            term = front.slice(posAt + 1);
                            front = front.slice(0, posAt + 1);
                        }
                    }

                    if (opt.enableCache && term in cache) {
                        response(cache[term]);
                    } else {
                        $.getJSON(opt.url + term, function(data, status, xhr) {
                            data = opt.formatData(data);
                            opt.enableCache && (cache[term] = data);
                            response(data);
                        });
                    }
                },
                focus: function(event, ui) {
                    return false;
                },
                select: function(event, ui) {
                    var item = ui.item;
                    if (opt.at) {
                        var pos = $this.input.caret().begin,
                            term = $this.input.val();
                        var front = term.slice(0, pos),
                            end = term.slice(pos);
                        var posAt = front.lastIndexOf('@'),
                            label = item.label;
                        front = front.slice(0, posAt + 1);
                        front = front + label + ' ';
                        end = end.charAt(0) == ' ' ? end.slice(1) : end;
                        $this.input.val(front + end);
                        $this.input.caret(front.length);
                    } else {
                        if (!opt.multi) {
                            $this.input.prevAll('.js-ac-result').remove();
                        } else {
                            if ($this.getResults().filter(function() {
                                return $(this).data('item.autocomplete').value == item.value;
                            }).size()) {
                                $this.input.val('');
                                return false;
                            }
                        }
                        $($.format(opt.template, item)).data("item.autocomplete", item)[opt.relatePos]($this.input);
                        $this.input.val('');
                    }
                    return false;
                }
            })).data("ui-autocomplete")._renderItem = opt.renderItem;

            $elem.on('click', '.js-ac-del', function(evt) {
                evt.preventDefault();
                $(this).parents('.js-ac-result').remove();
            });
        },
        val: function() {
            var $this = this,
                $elem = this.element,
                opt = this.options;
            return $this.getResults().map(function(i, elem) {
                var item = $(elem).data('item.autocomplete');
                return item.value;
            }).get();
        },
        valItems: function() {
            var $this = this,
                $elem = this.element,
                opt = this.options;
            return $this.getResults().map(function(i, elem) {
                return $(elem).data('item.autocomplete');
            }).get();
        },
        getResults: function() {
            var $this = this,
                $elem = this.element,
                opt = this.options;
            return opt.target.parent().find('.js-ac-result');
        }
    });
})();

$.widget('mxx.upload', {
    options: {
        uploadify_setting: {
            auto: true, //Boolean
            buttonClass: 'file_select', //String
            buttonCursor: 'hand', //String
            buttonImage: null, //String
            buttonText: '请选择文件', //String
            checkExisting: false, //String
            fileObjName: 'uploadFile', //String
            fileSizeLimit: '80MB', //Number
            fileTypeDesc: 'All Files', //String
            fileTypeExts: '*.*', //String
            formData: {}, //JSON Objec
            height: 24, //Number
            itemTemplate: false, //String
            method: 'post', //String
            multi: true, //Boolean
            overrideEvents: [], //JSON Array
            preventCaching: true, //Boolean
            progressData: 'percentage', //String
            queueID: false, //String
            queueSizeLimit: 999, //Number
            removeCompleted: false, //Boolean
            removeTimeout: 3, //Number
            requeueErrors: false, //Boolean
            successTimeout: 30, //Number
            swf: '/swf/uploadify.swf', //String
            uploader: '/json/global_uploadify/files-upload.action', //String
            uploadLimit: 999, //Number
            width: 100, //Number

            onCancel: null,
            onClearQueue: null,
            onDestroy: null,
            onDialogClose: null,
            onDialogOpen: null,
            onDisable: null,
            onEnable: null,
            onFallback: null,
            onInit: null,
            onQueueComplete: null,
            onSelect: null,
            onSelectError: null,
            onSWFReady: null,
            onUploadComplete: null,
            onUploadError: null,
            onUploadProgress: null,
            onUploadStart: null,
            onUploadSuccess: null
        }
    },
    _create: function() {
        var $this = this,
            $elem = this.element,
            opt = this.options;

        $this.id = $this.getRandomId();
        $elem.attr('id', $this.id);
        if (!opt.onSelectError) {
            opt.onSelectError = $this.selectError;
        }
        $elem.uploadify(opt.uploadify_setting);
    },
    getRandomId: function() {
        var $this = this,
            $elem = this.element,
            opt = this.options;
        return ($elem.attr('id') || 'upd_') + String((Math.random() + 1) * new Date().getTime() * 10000).split('.')[0];
    },
    selectError: function(file, errorCode, errorMsg) {
        var settings = this.settings;
        if ($.inArray('onSelectError', settings.overrideEvents) < 0) {
            switch (errorCode) {
                case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
                    if (settings.queueSizeLimit > errorMsg) {
                        this.queueData.errorMsg = '\n选取的文件超过队列余额了.';
                    } else {
                        this.queueData.errorMsg = '\n选取的文件太多了.';
                    }
                    break;
                case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
                    this.queueData.errorMsg = '\n请选择不超过' + settings.fileSizeLimit + '的' + settings.fileTypeDesc + '.';
                    break;
                case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
                    this.queueData.errorMsg = '\n请不要选择空文件.';
                    break;
                case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
                    this.queueData.errorMsg = '\n请选择如下格式的文件：' + settings.fileTypeExts;
                    break;
            }
        }
    }
});

$.widget('mxx.uploadPic', $.mxx.upload, {
    options: {
        uploadify_setting: {
            uploader: '/json/global_uploadify/img-upload.action',
            fileObjName: 'uploadFile', //String
            fileSizeLimit: '5MB', //Number
            fileTypeDesc: '图片', //String
            fileTypeExts: '*.jpg;*.gif;*.png;*.JPEG;*.BMP' //String
        }
    }
});

$.widget('mxx.uploadCsv', $.mxx.upload, {
    options: {
        uploadify_setting: {
            uploader: '/json/global_uploadify/csv-upload.action',
            fileObjName: 'uploadFile', //String
            fileSizeLimit: '1MB', //Number
            fileTypeDesc: 'CSV文件', //String
            fileTypeExts: '*.csv' //String
        }
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
        $this.$ghost = $elem.clone().addClass('disabled').removeAttr('act').html(opt.html).insertAfter($elem.hide());
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
$.widget('mxx.numberInput', $.ui.spinner, {
    options: {
        min: 0,
        max: 99999999999,
        culture: '',
        nochange: null
    },
    _create: function() {
        this._super();
        this.element.css({
            'ime-mode': 'disabled'
        });
        this.element.on('focus', function() {
            $(this).addClass('form_selected');
        }).on('blur', function() {
            $(this).removeClass('form_selected');
        });
    },
    _buttonHtml: function() {
        return '';
    },
    _format: function(value) {
        if (value === "") {
            return "";
        }
        if (this.options.isCurrency) {
            var IntPart = parseInt(value, 10),
                floatPart = String(value).split('.')[1] || '';
            return window.Globalize && this.options.numberFormat ? Globalize.format(IntPart, this.options.numberFormat, this.options.culture) + (floatPart ? '.' + floatPart : '') : value;
        } else {
            return window.Globalize && this.options.numberFormat ? Globalize.format(value, this.options.numberFormat, this.options.culture) : value;
        }
    },
    _events: {
        blur: function(event) {
            if (this.cancelBlur) {
                delete this.cancelBlur;
                return;
            }
            this._stop();
            this._refresh();
            if (this.previous !== this.element.val()) {
                this._trigger("change", event);
            } else {
                this._trigger("nochange", event);
            }
        },
        keydown: function(event) {
            if (this._start(event) && this._keydown(event)) {
                event.preventDefault();
            }
            var code = event.keyCode,
                map = $.ui.keyCode;
            if (event.shiftKey || event.ctrlKey || !(
                code >= 96 && code <= 105 ||
                code >= 48 && code <= 57 ||
                $.inArray(code, [map.BACKSPACE, map.DELETE, map.UP, map.DOWN, map.LEFT, map.RIGHT, map.HOME, map.END, map.ENTER, map.NUMPAD_ENTER, map.ESCAPE, map.PAGE_DOWN, map.PAGE_UP, map.TAB]) > -1 || this.element.val().indexOf('.') == -1 && (code == map.PERIOD || code == map.NUMPAD_DECIMAL))) {
                event.preventDefault();
            }
            this.downSelection = this.element.caret();
            this.downValue = this.element.val();
        },
        focus: function(event) {
            this.previous = this.element.val();
        },
        //添加回车支持
        keypress: function(evt) {
            evt.keyCode == 13 && $(this.element).trigger('blur');
        },
        keyup: function(event) {
            if (this.options.isCurrency) {
                var code = event.keyCode,
                    map = $.ui.keyCode;
                var current = this.element.val(),
                    length, tailLength, cursorPos;
                if (current && current != this.downValue && (code != map.PERIOD && code != map.NUMPAD_DECIMAL || current.slice(-1) != '.')) {
                    cursorPos = this.element.caret();
                    length = current.length;
                    tailLength = length - cursorPos.end;
                    this._value(this._format(this.value()));
                    this.element.caret(this.element.val().length - tailLength);
                }
            }
            this._stop();
        },
        mousewheel: $.noop
    }
});
$.widget('mxx.integerInput', $.mxx.numberInput, {
    _events: {
        keydown: function(event) {
            if (this._start(event) && this._keydown(event)) {
                event.preventDefault();
            }
            var code = event.keyCode,
                map = $.ui.keyCode;
            if (event.shiftKey || event.ctrlKey || !(
                code >= 96 && code <= 105 ||
                code >= 48 && code <= 57 ||
                $.inArray(code, [map.BACKSPACE, map.DELETE, map.UP, map.DOWN, map.LEFT, map.RIGHT, map.HOME, map.END, map.ENTER, map.ESCAPE, map.PAGE_DOWN, map.PAGE_UP, map.TAB]) > -1)) {
                event.preventDefault();
            }
            this.downSelection = this.element.caret();
            this.downValue = this.element.val();
        }
    }
});

(function($) {
    var increments = 0;

    function addDescribedBy(elem, id) {
        var describedby = (elem.attr("aria-describedby") || "").split(/\s+/);
        describedby.push(id);
        elem.data("ui-tooltip-id", id).attr("aria-describedby", $.trim(describedby.join(" ")));
    }
    $.widget('mxx.rktooltip', $.ui.tooltip, {
        options: {

        },
        _updateContent: function(target, event) {
            var content, contentOption = this.options.content,
                that = this,
                eventType = event ? event.type : null;

            var events = {
                keyup: function(event) {
                    if (event.keyCode === $.ui.keyCode.ESCAPE) {
                        var fakeEvent = $.Event(event);
                        fakeEvent.currentTarget = target[0];
                        this.close(fakeEvent, true);
                    }
                },
                remove: function() {
                    this._removeTooltip(tooltip);
                }
            };
            if (!event || event.type === "mouseover") {
                events.mouseleave = "close";
            }
            if (!event || event.type === "focusin") {
                events.focusout = "close";
            }
            this._on(true, target, events);
            if (typeof contentOption === "string") {
                return this._open(event, target, contentOption);
            }

            content = contentOption.call(target[0], function(response) {
                // ignore async response if tooltip was closed already
                if (!target.data("ui-tooltip-open")) {
                    return;
                }
                // IE may instantly serve a cached response for ajax requests
                // delay this call to _open so the other call to _open runs first
                that._delay(function() {
                    // jQuery creates a special event for focusin when it doesn't
                    // exist natively. To improve performance, the native event
                    // object is reused and the type is changed. Therefore, we can't
                    // rely on the type being correct after the event finished
                    // bubbling, so we set it back to the previous value. (#8740)
                    if (event) {
                        event.type = eventType;
                    }
                    this._open(event, target, response);
                });
            });
            if (content) {
                this._open(event, target, content);
            }
        },

        _open: function(event, target, content) {
            var tooltip, delayedShow, positionOption = $.extend({}, this.options.position);

            if (!content) {
                return;
            }

            // Content can be updated multiple times. If the tooltip already
            // exists, then just update the content and bail.
            tooltip = this._find(target);
            if (tooltip.length) {
                tooltip.find(".ui-tooltip-content").html(content);
                return;
            }

            // if we have a title, clear it to prevent the native tooltip
            // we have to check first to avoid defining a title if none exists
            // (we don't want to cause an element to start matching [title])
            //
            // We use removeAttr only for key events, to allow IE to export the correct
            // accessible attributes. For mouse events, set to empty string to avoid
            // native tooltip showing up (happens only when removing inside mouseover).
            if (target.is("[title]")) {
                if (event && event.type === "mouseover") {
                    target.attr("title", "");
                } else {
                    target.removeAttr("title");
                }
            }

            tooltip = this._tooltip(target);
            addDescribedBy(target, tooltip.attr("id"));
            tooltip.find(".ui-tooltip-content").html(content);

            function position(event) {
                positionOption.of = event;
                if (tooltip.is(":hidden")) {
                    return;
                }
                tooltip.position(positionOption);
            }

            if (this.options.track && event && /^mouse/.test(event.type)) {
                this._on(this.document, {
                    mousemove: position
                });
                // trigger once to override element-relative positioning
                position(event);
            } else {
                tooltip.position($.extend({
                    of: target
                }, this.options.position));
            }

            tooltip.hide();

            this._show(tooltip, this.options.show);
            // Handle tracking tooltips that are shown with a delay (#8644). As soon
            // as the tooltip is visible, position the tooltip using the most recent
            // event.
            if (this.options.show && this.options.show.delay) {
                delayedShow = this.delayedShow = setInterval(function() {
                    if (tooltip.is(":visible")) {
                        position(positionOption.of);
                        clearInterval(delayedShow);
                    }
                }, $.fx.interval);
            }

            this._trigger("open", event, {
                tooltip: tooltip
            });
        }
    });
})(jQuery);

$(function() {
    //$.ui.autocomplete.prototype.options.appendTo = 'body';//modify weitao
    $.datepicker && $.datepicker.setDefaults && $.datepicker.setDefaults($.datepicker.regional["zh-CN"]);
    $.timepicker && $.timepicker.setDefaults && $.timepicker.setDefaults($.timepicker.regional['zh']);
});

$.widget('mxx.hoverTip', {
    options: {
        items: '',
        showDelay: 500,
        hideDelay: 200,
        tipClass: '',
        content: null,
        arrowSize: 8,
        attrName: 'ucard'
    },
    _create: function() {
        var self = this,
            $elem = this.element,
            opt = this.options;

        self.tmpl = '<div class="name_card hidden" style="display:none"><div class="name_card_border"></div><span class="arrow_left"></span><span class="arrow_right"></span><span class="arrow_down"></span><span class="arrow_up"></span></div>';
        self.loadingHtml = '<div class="name_card_content clear"><div class="loading"><span>正在加载，请稍候...</span></div></div>';
        self.$tip = $(self.tmpl).appendTo('body');

        self.content = self.$tip.find('>div.name_card_border');
        self.content.html(self.loadingHtml);

        self.arrows = self.$tip.find('span.arrow_left,span.arrow_right,span.arrow_down,span.arrow_up');
        self.arrowLeft = self.$tip.find('span.arrow_left');
        self.arrowRight = self.$tip.find('span.arrow_right');
        self.arrowDown = self.$tip.find('span.arrow_down');
        self.arrowUp = self.$tip.find('span.arrow_up');
        self.arrows.hide();

        var elemEnterTimer, elemLeaveTimer;
        var attr = '[' + opt.attrName + ']';
        $(['a', attr, ',', 'img', attr, ',', 'span', attr].join('')).live('mouseenter', function(evt) {
            var $target = $(evt.target);
            clearTimeout(elemLeaveTimer);
            clearTimeout(tipLeaveTimer);
            elemEnterTimer = setTimeout(function() {
                self.show($target);
            }, opt.showDelay);
        }).live('mouseleave', function(evt) {
            clearTimeout(elemEnterTimer);
            elemLeaveTimer = setTimeout(function() {
                self.hide();
            }, opt.hideDelay);
        });

        var tipEnterTimer, tipLeaveTimer;
        self.$tip.mouseenter(function(evt) {
            clearTimeout(elemLeaveTimer);
        });
        self.$tip.mouseleave(function(evt) {
            tipLeaveTimer = setTimeout(function() {
                self.hide();
            }, opt.hideDelay);
        });

        self.$tip.click(function(evt) {
            var target = evt.target,
                nodeName = target.nodeName;
            if (nodeName == 'A' || $(target).parents('a').size()) {
                self.hide();
            }
        });
    },
    show: function($target) {
        var self = this,
            $elem = this.element,
            opt = this.options;
        if (typeof(opt.content) == 'string') {

            self.content.html(opt.content);
        } else if ($.isFunction(opt.content)) {
            opt.content.apply(self, [$target, self.content]);
        }
        self.pointTo($target);
    },
    pointTo: function($target) {
        var self = this,
            $elem = this.element,
            opt = this.options;

        self.tipWidth = self.$tip.width();
        self.tipHeight = 300 || self.$tip.height();
        var $win = $(window),
            $doc = $(document);
        var clientWidth = $win.width(),
            clientHeight = $win.height(),
            scrollLeft = $doc.scrollLeft(),
            scrollTop = $doc.scrollTop();
        var elemRect = $target.offset();



        elemRect.width = $target.width();
        elemRect.height = $target.height();

        var pos = {
                collision: 'fit',
                of: $target
            },
            arrow;
        if (elemRect.top - scrollTop - opt.arrowSize >= self.tipHeight) { //up
            pos = {
                my: 'left bottom',
                at: 'left top-8',
                offset: '0 -8px'
            };
            arrow = 'up';
        } else if (clientWidth + scrollLeft - (elemRect.left + elemRect.width) - opt.arrowSize >= self.tipWidth) { //right
            pos = {
                my: 'left top',
                at: 'right+8 top',
                offset: '8px 0'
            };
            arrow = 'right';
        } else if (clientHeight + scrollTop - (elemRect.top + elemRect.height) - opt.arrowSize >= self.tipHeight) { //down
            pos = {
                my: 'left top',
                at: 'left bottom+8',
                offset: '0 8px'
            };
            arrow = 'down';
        } else if (elemRect.left - scrollLeft - opt.arrowSize >= self.tipWidth) { //left
            pos = {
                my: 'right top',
                at: 'left top',
                offset: '-8px 0'
            };
            arrow = 'left';
        } else {
            pos = {
                my: 'left bottom',
                at: 'left top',
                offset: '0 8px'
            };
            arrow = 'up';
        }
        pos.collision = 'fit';
        pos.of = $target;
        self.arrows.hide();
        self.$tip.css('opacity', 0).show().position(pos).css('opacity', 1);
        var newPos = self.$tip.position();
        if (arrow == 'up') {
            self.arrowDown.css('left', elemRect.left - newPos.left + elemRect.width / 2).show();
        } else if (arrow == 'right') {
            self.arrowLeft.css('top', elemRect.top - newPos.top + elemRect.height / 2).show();
        } else if (arrow == 'down') {
            self.arrowUp.css('left', elemRect.left - newPos.left + elemRect.width / 2).show();
        } else {
            self.arrowRight.css('top', elemRect.top - newPos.top + elemRect.height / 2).show();
        }

    },
    hide: function() {
        var self = this,
            $elem = this.element,
            opt = this.options;
        self.$tip.hide();
        self.content.html(self.loadingHtml);
    }
});

$.widget('mxx.UCard', {
    options: {},
    _create: function() {
        var self = this,
            $elem = this.element,
            opt = this.options;

        self.cache = {};

        $elem.hoverTip({
            attrName: 'ucard',
            content: function($target, $container) {
                var uid = $target.attr('ucard').split('=')[1].split(',').join('');
                if (self.cache[uid]) {
                    $.ajaxSetup({
                        cache: true
                    });
                    $container.html(self.cache[uid].data.view);
                    $.ajaxSetup({
                        cache: false
                    });
                    $elem.hoverTip('pointTo', $target);
                } else {
                    $.getJSON('/profile/crm-card.action?decorator=ajaxformat&confirm=true&userCardId=' + uid, {}, function(data) {
                        if (data.status == 0) {
                            $.ajaxSetup({
                                cache: true
                            });
                            $container.html(data.data.view);
                            $.ajaxSetup({
                                cache: false
                            });
                            $elem.hoverTip('pointTo', $target);
                            self.cache[uid] = data;
                        }
                    });
                }
            }
        });
    },
    clearCache: function(uid) {
        delete this.cache[uid];
    }
});
$.widget('mxx.pagination', {
    options: {
        buttons: 10,
        selectPageSize: false,
        pagenum: null,
        pagesize: null,
        rowscount: null,

        change: null
    },
    _create: function() {
        var $this = this,
            $elem = this.element,
            opt = this.options;
        $elem.addClass('mxx-pagination-container');
        $this.pagenum = $this.pagecurr = opt.pagenum;
        $this.rowscount = opt.rowscount;
        $this.pagesize = opt.pagesize;
        $this.pagescount = Math.ceil(opt.rowscount / opt.pagesize);

        // $this.pagenum = 12;
        // $this.pagescount = 50;
        // $this.pagesize = 50;
        // $this.rowscount = $this.pagenum * $this.pagesize - 2;
        // $this.pagecurr = 12;

        var htmlPages = $this.getPagesHtml($this.pagenum);
        $elem.empty().html('<span class="mxx-pagination-pages">' + htmlPages.join('') + '</span>' + (opt.selectPageSize ? '<span class="mxx-pagination-sizelist">' + this.getPageSizeSelectorHtml() + '</span>' : ''));
        $this.bindHandler();
    },
    getPagesHtml: function(pagenum) {
        var $this = this,
            $elem = this.element,
            opt = this.options;
        var arHtml = [];
        if ($this.pagescount > opt.buttons) {
            var min = pagenum - 2,
                max = pagenum + 2;
            arHtml.push('<span class="mxx-pagination-prev" method="gotoprevpage">上一页</span>');
            arHtml.push('<span class="mxx-pagination-goto' + (1 == $this.pagecurr ? ' curr' : '') + '" method="gotopage">' + 1 + '</span>');
            for (var i = 2; i < $this.pagescount; i) {
                if (i >= min && i <= max || pagenum == 5 && i == 2 || pagenum == $this.pagescount - 4 && i == $this.pagescount - 1) {
                    arHtml.push('<span class="mxx-pagination-goto' + (i == $this.pagecurr ? ' curr' : '') + '" method="gotopage">' + i + '</span>');
                    i++;
                } else if (i == $this.pagescount - 1) {
                    break;
                } else {
                    arHtml.push('<span class="mxx-pagination-turnto" method="turnto" pageno="' + (i < min ? (min - 3) : (max + 3)) + '">…</span>');
                    i = i < min ? min : $this.pagescount - 1;
                }
            }
            arHtml.push('<span class="mxx-pagination-goto' + ($this.pagescount == $this.pagecurr ? ' curr' : '') + '" method="gotopage">' + $this.pagescount + '</span>');
            arHtml.push('<span class="mxx-pagination-next" method="gotonextpage">下一页</span>');
        } else {
            for (var i = 1; i <= $this.pagescount; i++) {
                arHtml.push('<span class="mxx-pagination-goto' + (i == $this.pagecurr ? ' curr' : '') + '" method="gotopage">' + i + '</span>');
            }
        }
        arHtml.push('<label class="mxx-pagination-summary"><span class="mxx-pagination-summary-start">' + (($this.pagecurr - 1) * $this.pagesize + 1) + '</span><span class="mxx-pagination-summary-line">-</span><span class="mxx-pagination-summary-end">' +
            Math.min($this.pagecurr * $this.pagesize, $this.rowscount) + '</span><span class="mxx-pagination-summary-splitter">/</span><span class="mxx-pagination-summary-total">共' + $this.rowscount + '条</span></label>');
        arHtml.push('<label class="mxx-pagination-jump">跳转到<input type="text" value="' + $this.pagecurr + '"></label>');
        return arHtml;
    },
    getPageSizeSelectorHtml: function() {
        var $this = this,
            $elem = this.element,
            opt = this.options;
        return $.map(opt.pagesizeoptions, function(size) {
            return '<span class="mxx-pagination-size" style="display:none;">' + size + '</span>';
        }).join('');
    },
    bindHandler: function() {
        var $this = this,
            $elem = this.element,
            opt = this.options;
        $elem.on('click', 'span[method]', function(evt) {
            var $sp = $(this),
                method = $sp.attr('method'),
                pageNo;
            switch (method) {
                case 'gotoprevpage':
                    pageNo = $this.pagenum - 1;
                    break;
                case 'gotonextpage':
                    pageNo = $this.pagenum + 1;
                    break;
                case 'gotopage':
                    pageNo = parseInt($sp.text(), 10);
                    break;
                case 'turnto':
                    $elem.find('span.mxx-pagination-pages').html($this.getPagesHtml(parseInt($sp.attr('pageno'), 10)).join(''));
                    break;
            }
            if (pageNo !== undefined) {
                $elem.find('span[method="gotopage"]').each(function() {
                    var $span = $(this);
                    if ($span.text() == pageNo) {
                        $span.addClass('curr');
                    } else {
                        $span.removeClass('curr');
                    }
                });
                $.isFunction(opt.change) && opt.change.apply($this, [pageNo]);
            }
        });

        $elem.on('blur keypress', 'input', function(evt) {
            var $ipt = $(this);
            if (evt.type == 'focusout' || evt.keyCode == 13) {
                var pageNo = parseInt($.trim($ipt.val()), 10);
                if (!isNaN(pageNo)) {
                    $.isFunction(opt.change) && opt.change.apply($this, [pageNo]);
                }
            }
        });
        $elem.find('span[method]').disableSelection();
    }
});

/*! jquery.cookie v1.4.1 | MIT */
! function(a) {
    "function" == typeof define && define.amd ? define(["jquery"], a) : "object" == typeof exports ? a(require("jquery")) : a(jQuery)
}(function(a) {
    function b(a) {
        return h.raw ? a : encodeURIComponent(a)
    }

    function c(a) {
        return h.raw ? a : decodeURIComponent(a)
    }

    function d(a) {
        return b(h.json ? JSON.stringify(a) : String(a))
    }

    function e(a) {
        0 === a.indexOf('"') && (a = a.slice(1, -1).replace(/\\"/g, '"').replace(/\\\\/g, "\\"));
        try {
            return a = decodeURIComponent(a.replace(g, " ")), h.json ? JSON.parse(a) : a
        } catch (b) {}
    }

    function f(b, c) {
        var d = h.raw ? b : e(b);
        return a.isFunction(c) ? c(d) : d
    }

    var g = /\+/g,
        h = a.cookie = function(e, g, i) {
            if (void 0 !== g && !a.isFunction(g)) {
                if (i = a.extend({}, h.defaults, i), "number" == typeof i.expires) {
                    var j = i.expires,
                        k = i.expires = new Date;
                    k.setTime(+k + 864e5 * j)
                }
                return document.cookie = [b(e), "=", d(g), i.expires ? "; expires=" + i.expires.toUTCString() : "", i.path ? "; path=" + i.path : "", i.domain ? "; domain=" + i.domain : "", i.secure ? "; secure" : ""].join("")
            }
            for (var l = e ? void 0 : {}, m = document.cookie ? document.cookie.split("; ") : [], n = 0, o = m.length; o > n; n++) {
                var p = m[n].split("="),
                    q = c(p.shift()),
                    r = p.join("=");
                if (e && e === q) {
                    l = f(r, g);
                    break
                }
                e || void 0 === (r = f(r)) || (l[q] = r)
            }
            return l
        };
    h.defaults = {}, a.removeCookie = function(b, c) {
        return void 0 === a.cookie(b) ? !1 : (a.cookie(b, "", a.extend({}, c, {
            expires: -1
        })), !a.cookie(b))
    }
});
