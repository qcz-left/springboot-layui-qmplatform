(/**
 * Layui table表头搜索
 * @param $
 */
function ($) {
    $.extend($.fn, {
        /**
         * opt参数说明：<br>
         * layFilter：from查询表单的lay-filter属性<br>
         * data：查询过滤下拉列表<br>
         * timeQuery：是否开启时间查询<br>
         * timeKey：开启时间查询时传到后台的参数名<br>
         * defaultTimeSelected：开启时间查询时默认选中的查询项<br>
         * defaultDataSelected：条件过滤默认选中的项<br>
         * doSearch：点击搜索按钮执行的回调函数<br>
         * @param opt
         */
        laySearch: function (opt) {
            let form = window.layui.form;
            let laydate = window.layui.laydate;

            let $this = this;
            let layFilter = opt.layFilter;
            let data = opt.data || [];
            let doSearchFun = opt.doSearch;
            let timeQuery = (typeof opt.timeQuery === "boolean" && opt.timeQuery);
            let timeKey = opt.timeKey;
            let defaultTimeSelected = opt.defaultTimeSelected || 'today';
            let defaultDataSelected = opt.defaultDataSelected || data[0].key;

            let selectLayFilter = $this.selector + "-select-filter";
            let timeQueryLayFilter = $this.selector + "-timeQuery-filter";

            let dataMap = {};
            let optionHtml = '';
            for (let i = 0; i < data.length; i++) {
                let dataItem = data[i];
                let key = dataItem.key;
                dataMap[key] = dataItem;
                let label = dataItem.label;

                optionHtml += '<option value="' + key + '">' + label + '</option>';
            }

            /**
             * 选择一个搜索条件
             */
            let doSelectSearch = function (value, selectDom) {
                let item = dataMap[value];
                let type = item.type;
                let key = item.key;
                let defaultValue = item.default;
                let placeholder = item.placeholder || '';
                let inputHtml;
                if (type === "text") {
                    // 输入框
                    inputHtml = '<input type="text" name="' + key + '" autocomplete="off" class="layui-input" placeholder="' + placeholder + '">';
                } else if (type === "select") {
                    // 选择框
                    inputHtml = '<select name="' + key + '">' +
                        '<option value="">请选择</option>';
                    let values = item.values;
                    for (let i = 0; i < values.length; i++) {
                        let valuesItem = values[i];
                        inputHtml += '<option value="' + valuesItem.value + '">' + valuesItem.name + '</option>';
                    }
                    inputHtml += '</select>'
                } else if (type === "dateRanger") {
                    // 时间范围选择
                    inputHtml = '' +
                        '<div class="layui-inline">' +
                        '   <input type="text" class="layui-input" autocomplete="off" name="' + key + 'Start">' +
                        '</div>' +
                        '<div class="layui-inline">' +
                        '   -' +
                        '</div>' +
                        '<div class="layui-inline">' +
                        '   <input type="text" class="layui-input" autocomplete="off" name="' + key + 'End">' +
                        '</div>';
                } else if (type === "selectTree") {
                    // 下拉树
                    inputHtml = '<div id="' + key + '" name="' + key + '" style="width: 190px;"></div>';
                }

                $(selectDom).parents(".search-where:first").find(".input-content").html(inputHtml);

                if (type === "select") {
                    form.render('select');
                } else if (type === "dateRanger") {
                    laydate.render({
                        elem: '[name=' + key + 'Start]',
                        type: 'datetime',
                        value: defaultValue[0]
                    });
                    laydate.render({
                        elem: '[name=' + key + 'End]',
                        type: 'datetime',
                        value: defaultValue.length > 1 ? defaultValue[1] : ""
                    });
                } else if (type === "selectTree") {
                    SelectUtil.render(xmSelect, {
                        el: '#' + key,
                        name: key,
                        radio: true,
                        tree: true,
                        filterable: true,
                        data: item.treeData
                    });
                }

            }

            /**
             * 选择一个时间条件
             */
            let timeQueryEvent = function (value) {
                let $timeSelectContent = $this.find(".time-select-content");
                if (value === 'other') {
                    $timeSelectContent.removeClass("hide");
                    $timeSelectContent.find("[name^=" + timeKey + "]").val("");
                } else {
                    $timeSelectContent.addClass("hide");
                }
            }

            let searchWhereHtml =
                '       <div class="search-where layui-inline">' +
                '           <label class="layui-form-label">在</label>' +
                '           <div class="layui-input-inline">' +
                '               <select lay-filter="' + selectLayFilter + '">' +
                optionHtml +
                '               </select>' +
                '           </div>' +
                '           <label class="layui-form-label">中搜索</label>' +
                '           <div class="layui-input-inline input-content" style="width: auto; max-height: 38px;">' +
                '           </div>' +
                '       </div>';

            let timeQueryHtml =
                '       <div class="time-query layui-inline">' +
                '           <div class="layui-input-inline" style="width: 100px;">' +
                '               <select lay-filter="' + timeQueryLayFilter + '">' +
                '                   <option value="today">今天</option>' +
                '                   <option value="week">最近一周</option>' +
                '                   <option value="month">最近一月</option>' +
                '                   <option value="threeMonth">最近三月</option>' +
                '                   <option value="year">最近一年</option>' +
                '                   <option value="other">其它时间</option>' +
                '               </select>' +
                '           </div>' +
                '           <div class="layui-input-inline time-select-content hide" style="width: auto; max-height: 38px;">' +
                '               <div class="layui-inline">' +
                '                   <input type="text" class="layui-input" autocomplete="off" name="' + timeKey + 'Start">' +
                '               </div>' +
                '               <div class="layui-inline">' +
                '                   -' +
                '               </div>' +
                '               <div class="layui-inline">' +
                '                   <input type="text" class="layui-input" autocomplete="off" name="' + timeKey + 'End">' +
                '               </div>' +
                '           </div>' +
                '           <label class="layui-form-label">的记录</label>' +
                '       </div>';

            $this.append('' +
                '<div class="layui-form search-form" lay-filter="' + layFilter + '">' +
                '   <div class="layui-form-item">' +
                searchWhereHtml +
                (timeQuery ? timeQueryHtml : '') +
                '       <div class="searcher layui-inline">' +
                '           <div class="layui-btn-group">' +
                '               <button type="button" class="layui-btn layui-btn-normal btn-search"><i class="layui-icon layui-icon-search"></i>搜索</button>' +
                '           </div>' +
                '       </div>' +
                '   </div>' +
                '</div>');

            // 重新渲染选择搜索框
            form.render('select');

            if (timeQuery) {
                // 默认选中时间查询项
                $this.find(".time-query [lay-filter='" + timeQueryLayFilter + "']").val(defaultTimeSelected);
                timeQueryEvent(defaultTimeSelected);

                laydate.render({
                    elem: '[name=' + timeKey + 'Start]',
                    type: 'datetime'
                });
                laydate.render({
                    elem: '[name=' + timeKey + 'End]',
                    type: 'datetime'
                });
            }
            // 默认选择条件搜索
            $this.find(".search-where [lay-filter='" + selectLayFilter + "']").val(defaultDataSelected);
            doSelectSearch(defaultDataSelected, $this.find(".layui-form-item:first select"));

            // 条件搜索选择框选择事件
            form.on('select(' + selectLayFilter + ')', function (data) {
                doSelectSearch(data.value, data.elem)
            });

            // 时间选择框选择事件
            form.on('select(' + timeQueryLayFilter + ')', function (data) {
                let value = data.value;
                timeQueryEvent(value);
            });

            // 点击搜索按钮事件
            $this.find(".btn-search").click(function () {
                let timeSelect = $this.find(".time-query [lay-filter='" + timeQueryLayFilter + "']").val();
                if (timeQuery && timeSelect !== "other") {
                    let nowDate = new Date();
                    let timeStart = "";
                    if (timeSelect === "today") {
                        timeStart = DateUtil.getDate(nowDate) + " 00:00:00";
                    } else if (timeSelect === "week") {
                        timeStart = DateUtil.getLastWeek(nowDate);
                    } else if (timeSelect === "month") {
                        timeStart = DateUtil.getLastMonth(nowDate);
                    } else if (timeSelect === "threeMonth") {
                        timeStart = DateUtil.getBeforeMonth(nowDate, 3);
                    } else if (timeSelect === "year") {
                        timeStart = DateUtil.getLastYear(nowDate);
                    }
                    $this.find("[name=" + timeKey + "Start]").val(timeStart);
                }
                doSearchFun();
            });
        }
    });
})(jQuery);