(/**
 * 步骤条
 * @param $
 */
function ($) {
    $.extend($.fn, {
        /**
         * opt参数说明：<br>
         * items：步骤项<br>
         * bindPre：绑定的上一步按钮选择器<br>
         * bindNext：绑定的下一步按钮选择器<br>
         * direction：展示方向，有水平“horizontal”、垂直“vertical”两种可选，默认：horizontal <br>
         * @param opt
         */
        steps: function (opt) {
            let $this = this;
            let selector = $this.selector;

            let items = opt.items || [];
            let bindPre = opt.bindPre;
            let bindNext = opt.bindNext;
            let direction = opt.direction || "horizontal";
            let isVertical = direction === "vertical";

            let stepsContentHtml = '';
            let itemsLength = items.length;
            for (let i = 0; i < itemsLength; i++) {
                let item = items[i];
                let label = item.label;
                let stepNum = i + 1;

                // 水平方向
                if (isVertical) {
                    stepsContentHtml += '<div class="steps-item steps-item-vertical">'
                    stepsContentHtml += '' +
                        '<div class="vh-center" style="display: inline-block; position: relative;">' +
                        '   <div class="step-circle ' + (i === 0 ? 'step-circle-process' : 'step-circle-wait') + ' vh-center" step-index="' + stepNum + '">' +
                        stepNum +
                        '   </div>' +
                        '   <div class="step-title step-title-vertical">' +
                        label +
                        '   </div>' +
                        '</div>';
                    // 最后一项
                    if (i !== itemsLength - 1) {
                        stepsContentHtml += '' +
                            '<div class="step-line-vertical">' +
                            '</div>';
                    }

                    stepsContentHtml += '</div>';

                    continue;
                }

                // 垂直方向
                stepsContentHtml += '<div class="steps-item">'
                stepsContentHtml += '' +
                    '<div class="vh-center" style="display: inline-flex;">' +
                    '   <div class="step-circle ' + (i === 0 ? 'step-circle-process' : 'step-circle-wait') + ' vh-center" step-index="' + stepNum + '">' +
                    stepNum +
                    '   </div>' +
                    '   <div class="step-title">' +
                    label +
                    '   </div>' +
                    '</div>';
                // 最后一项
                if (i !== itemsLength - 1) {
                    stepsContentHtml += '' +
                        '<div class="step-line-horizontal">' +
                        '</div>';
                }

                stepsContentHtml += '</div>';

            }

            $this.append('' +
                '<div class="' + (isVertical ? 'steps-vertical' : 'steps') + '">' +
                '   <div class="steps-content">' +
                stepsContentHtml +
                '   </div>' +
                '</div>');

            if (isVertical) {
                let lineLength = ($this.find('.steps-content').width() - itemsLength * 29) / (itemsLength - 1) - 10;
                $this.find(".step-line-vertical").css("width", lineLength);
            } else {
                let lineLength = ($this.find('.steps-content').height() - itemsLength * 29) / (itemsLength - 1) - 10;
                $this.find(".step-line-horizontal").css("height", lineLength);
            }

            /**
             * 获取步骤条下标
             * @returns {number}
             */
            let getStepIndex = function () {
                return parseInt($this.find(".step-circle-process").attr("step-index"));
            }

            // 上一步
            $(bindPre).click(function () {
                let stepIndex = getStepIndex();
                if (stepIndex === 1) {
                    return;
                }
                let preStepIndex = stepIndex - 1;
                let dataItem = items[preStepIndex - 1];
                $this.parent().find(".steps-form-item").addClass("hide");
                $this.parent().find(dataItem.bindForm).removeClass("hide");

                $this.find(".step-circle-process").removeClass("step-circle-process").addClass("step-circle-wait");
                $this.find('.step-circle[step-index=' + preStepIndex + ']').html(preStepIndex).removeClass("step-circle-wait").addClass("step-circle-process");
            });
            // 下一步
            $(bindNext).click(function () {
                let stepIndex = getStepIndex();
                if (stepIndex === itemsLength) {
                    return;
                }
                let nextStepIndex = stepIndex + 1;
                let dataItem = items[stepIndex - 1];
                let nextItem = items[nextStepIndex - 1];
                // 下一步 提交前的操作
                let beforeSubmitResult = true;
                let beforeSubmit = dataItem.beforeSubmit;
                if (beforeSubmit && typeof beforeSubmit === "function") {
                    beforeSubmitResult = beforeSubmit();
                    if (typeof beforeSubmitResult === "undefined" || typeof beforeSubmitResult !== "boolean") {
                        beforeSubmitResult = true;
                    }
                }

                if (!beforeSubmitResult) {
                    return;
                }

                let $stepsFormContent = $('.steps-form-content[for=\'' + selector + '\']');
                $stepsFormContent.find(".steps-form-item").addClass("hide");
                $stepsFormContent.find(nextItem.bindForm).removeClass("hide");

                $this.find(".step-circle-process").html('<i class="layui-icon layui-icon-ok"></i>').removeClass("step-circle-process").addClass("step-circle-finished");
                $this.find('.step-circle[step-index=' + nextStepIndex + ']').removeClass("step-circle-wait").addClass("step-circle-process");
            });
        }
    });
})(jQuery);