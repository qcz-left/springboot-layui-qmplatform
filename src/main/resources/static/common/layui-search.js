(/**
 * Layui table表头搜索
 * @param $
 */
function ($) {
    $.extend($.fn, {
        laySearch: function (opt) {
            let form = window.layui.form;
            let laydate = window.layui.laydate;

            let $this = this;
            let data = opt.data || [];
            let layFilter = opt.layFilter;
            let doSearchFun = opt.doSearch;
            let selectLayFilter = $this.selector + "-select-filter";

            let dataMap = {};
            let optionHtml = '';
            for (let i = 0; i < data.length; i++) {
                let dataItem = data[i];
                let key = dataItem.key;
                dataMap[key] = dataItem;
                let label = dataItem.label;

                optionHtml += '<option value="' + key + '" ' + (i === 0 ? "selected" : "") + '>' + label + '</option>';
            }

            /**
             * 选择一个搜索条件
             */
            let doSelectSearch = function (value) {
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
                }

                $this.find(".input-content").html(inputHtml);

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
                }
            }

            $this.append('' +
                '<div class="layui-form" lay-filter="' + layFilter + '">' +
                '   <div class="layui-form-item">' +
                '       <div class="search-where layui-inline">' +
                '           <label class="layui-form-label">在</label>' +
                '           <div class="layui-input-inline">' +
                '               <select class="layui-input-inline" lay-filter="' + selectLayFilter + '">' +
                optionHtml +
                '               </select>' +
                '           </div>' +
                '           <label class="layui-form-label">中搜索</label>' +
                '           <div class="layui-input-inline input-content" style="width: auto; max-height: 38px;">' +
                '           </div>' +
                '       </div>' +
                '       <div class="searcher layui-inline">' +
                '           <div class="layui-input-inline">' +
                '               <button type="button" class="layui-btn layui-btn-normal btn-search"><i class="layui-icon layui-icon-search"></i>搜索</button>' +
                '           </div>' +
                '       </div>' +
                '   </div>' +
                '</div>');

            // 重新渲染选择搜索框
            form.render('select');
            // 默认选择第一个条件搜索
            doSelectSearch(data[0].key);
            // 条件搜索选择框选择事件
            form.on('select(' + selectLayFilter + ')', function (data) {
                doSelectSearch(data.value)
            });

            $this.find(".layui-form-label").css("width", "unset");
            // 点击搜索按钮事件
            $this.find(".btn-search").click(function () {
                doSearchFun();
            });
        }
    });
})(jQuery);