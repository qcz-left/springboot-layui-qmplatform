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
         * splitLineSize：分割线的长度 <br>
         * @param opt
         */
        steps: function (opt) {
            let $this = this;
            let $stepsNav = $this.find(".steps-nav");

            let items = opt.items || [];
            let bindPre = opt.bindPre;
            let bindNext = opt.bindNext;
            let bindSave = opt.bindSave;
            let direction = opt.direction || "horizontal";
            let isVertical = direction === "vertical";
            let splitLineSize = opt.splitLineSize;

            let stepsContentHtml = '';
            let itemsLength = items.length;
            for (let i = 0; i < itemsLength; i++) {
                let item = items[i];
                let label = item.label;
                let stepNum = i + 1;

                // 水平方向
                if (isVertical) {
                    stepsContentHtml += '<div class="steps-item ' + (i === 0 ? 'steps-item-selected' : 'steps-item-wait') + ' steps-item-vertical">'
                    stepsContentHtml += '' +
                        '<div class="steps-item-content vh-center" style="display: inline-block; position: relative;">' +
                        '   <div class="steps-circle vh-center" steps-index="' + stepNum + '">' +
                        stepNum +
                        '   </div>' +
                        '   <div class="steps-title steps-title-vertical" title="' + label + '">' +
                        label +
                        '   </div>' +
                        '</div>';
                    // 最后一项
                    if (i !== itemsLength - 1) {
                        stepsContentHtml += '' +
                            '<div class="steps-line steps-line-vertical">' +
                            '</div>';
                    }

                    stepsContentHtml += '</div>';

                    continue;
                }

                // 垂直方向
                stepsContentHtml += '<div class="steps-item ' + (i === 0 ? 'steps-item-selected' : 'steps-item-wait') + '">'
                stepsContentHtml += '' +
                    '<div class="steps-item-content vh-center" style="display: inline-flex;">' +
                    '   <div class="steps-circle vh-center" steps-index="' + stepNum + '">' +
                    stepNum +
                    '   </div>' +
                    '   <div class="steps-title" title="' + label + '">' +
                    label +
                    '   </div>' +
                    '</div>';
                // 最后一项
                if (i !== itemsLength - 1) {
                    stepsContentHtml += '' +
                        '<div class="steps-line steps-line-horizontal">' +
                        '</div>';
                }

                stepsContentHtml += '</div>';
            }

            $stepsNav.append('<div class="steps-content">' + stepsContentHtml + '</div>');

            let $stepsFormContent = $this.find(".steps-form-content");
            if (isVertical) {
                $stepsNav.addClass("steps-nav-vertical");
                $stepsNav.find(".steps-content").addClass("vh-center");
                $stepsFormContent.addClass("steps-form-content-vertical");
                if (splitLineSize) {
                    $stepsNav.find(".steps-line").css("width", splitLineSize);
                    $stepsNav.find(".steps-title").css("max-width", splitLineSize + 20);
                }
            } else {
                $stepsNav.addClass("steps-nav-horizontal");
                $this.addClass("jq-steps-horizontal");
                $stepsFormContent.addClass("steps-form-content-horizontal");
                if (splitLineSize) {
                    $stepsNav.find(".steps-line").css("height", splitLineSize);
                    $stepsNav.find(".steps-title").css("max-height", splitLineSize + 20);
                }
            }

            // css
            $this.find(".steps-form-item").addClass("layui-anim layui-anim-scale");

            /**
             * 获取当前步骤项的jQuery对象
             */
            let getSelectedDom = function () {
                return $stepsNav.find(".steps-item-selected");
            }

            /**
             * 获取步骤条下标
             * @returns {number}
             */
            let getStepIndex = function () {
                return parseInt(getSelectedDom().find(".steps-circle").attr("steps-index"));
            }

            let stepsItemToClass = "steps-item-to";
            /**
             * 回到已经完成的步骤时，检查并添加到达点的标记
             */
            let checkStepsItemTo = function () {
                if (!$stepsNav.find(".steps-item").hasClass(stepsItemToClass)) {
                    getSelectedDom().addClass(stepsItemToClass);
                }
            }

            /**
             * 检查一下步骤，是否是第一步或最后一步
             */
            let checkStep = function () {
                let btnDisabledClass = "btn-gray-disabled";
                $(bindPre).addClass(btnDisabledClass);
                $(bindNext).addClass(btnDisabledClass);
                $(bindSave).hide();
                let stepIndex = getStepIndex();
                if (stepIndex > 1) {
                    $(bindPre).removeClass(btnDisabledClass);
                }
                if (stepIndex < itemsLength) {
                    $(bindNext).removeClass(btnDisabledClass);
                }
                if (stepIndex === itemsLength) {
                    $(bindSave).show();
                }

                $this.find(".steps-form-content .steps-form-item").addClass("hide");
                $this.find(".steps-form-content").find(items[stepIndex - 1].bindForm).removeClass("hide");

                $stepsNav.find(".steps-item-finished .steps-circle").html('<i class="layui-icon layui-icon-ok"></i>');
                $stepsNav.find(".steps-item-finished").unbind("click").click(function () {
                    checkStepsItemTo();
                    setSteps($(this).find(".steps-circle").attr("steps-index"));
                    checkStep();
                });
            }
            checkStep();

            /**
             * 设置当前步骤数
             * @param stepIndex 步骤数
             */
            let setSteps = function (stepIndex) {
                getSelectedDom().removeClass("steps-item-selected");
                $stepsNav.find(".steps-circle[steps-index=" + stepIndex + "]").parents(".steps-item").removeClass("steps-item-wait").addClass("steps-item-selected");
            }

            // 上一步
            $(bindPre).click(function () {
                let stepIndex = getStepIndex();
                if (stepIndex === 1) {
                    return;
                }
                let preStepIndex = stepIndex - 1;
                checkStepsItemTo();
                setSteps(preStepIndex);
                checkStep();
            });
            // 下一步
            $(bindNext).click(function () {
                let stepIndex = getStepIndex();
                let nextStepIndex = stepIndex + 1;
                let dataItem = items[stepIndex - 1];
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

                getSelectedDom().addClass("steps-item-finished");
                if (stepIndex === itemsLength) {
                    checkStep();
                    return;
                }

                setSteps(nextStepIndex);
                if ($stepsNav.find(".steps-item").hasClass(stepsItemToClass)) {
                    getSelectedDom().removeClass(stepsItemToClass);
                }
                checkStep();
            });

            // 保存事件
            $(bindSave).click(function () {
                $(bindNext).click();
            });
        }
    });
})(jQuery);