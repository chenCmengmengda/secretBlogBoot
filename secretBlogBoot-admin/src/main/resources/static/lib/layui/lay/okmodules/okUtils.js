"use strict";
layui.define(["layer"], function (exprots) {
    var $ = layui.jquery;
    var okUtils = {
        /**
         * 是否前后端分离
         */
        isFrontendBackendSeparate: false,
        /**
         * 服务器地址
         */
        baseUrl: "http://rap2api.taobao.org/app/mock/233041",
        /**
         * 获取body的总宽度
         */
        getBodyWidth: function () {
            return document.body.scrollWidth;
        },
        /**
         * 主要用于对ECharts视图自动适应宽度
         */
        echartsResize: function (element) {
            var element = element || [];
            window.addEventListener("resize", function () {
                var isResize = localStorage.getItem("isResize");
                // if (isResize == "false") {
                for (let i = 0; i < element.length; i++) {
                    element[i].resize();
                }
                // }
            });
        },
        /**
         * ajax()函数二次封装
         * @param url
         * @param type
         * @param params
         * @param load
         * @returns {*|never|{always, promise, state, then}}
         */
        ajax: function (url, type, params, load) {
            var deferred = $.Deferred();
            var loadIndex;
            $.ajax({
                url: okUtils.isFrontendBackendSeparate ? okUtils.baseUrl + url : url,
                type: type || "get",
                data: params || {},
                dataType: "json",
                beforeSend: function () {
                    if (load) {
                        loadIndex = layer.load(0, {shade: 0.3});
                    }
                },
                success: function (data) {
                    if (data.status == 200) {
                        // 业务正常
                        deferred.resolve(data)
                    } else {
                        // 业务异常
                        layer.msg(data.message, {icon: 7, time: 2000});
                        deferred.reject("okUtils.ajax warn: " + data.message);
                    }
                },
                complete: function () {
                    if (load) {
                        layer.close(loadIndex);
                    }
                },
                error: function () {
                    layer.close(loadIndex);
                    layer.msg("服务器错误", {icon: 2, time: 2000});
                    deferred.reject("okUtils.ajax error: 服务器错误");
                }
            });
            return deferred.promise();
        },
        /**
         * 主要用于针对表格批量操作操作之前的检查
         * @param table
         * @returns {string}
         */
        tableBatchCheck: function (table) {
            var checkStatus = table.checkStatus("tableId");
            var rows = checkStatus.data.length;
            if (rows > 0) {
                var idsStr = "";
                for (var i = 0; i < checkStatus.data.length; i++) {
                    if(i != checkStatus.data.length-1) {
                        idsStr += checkStatus.data[i].id + ",";
                    } else{
                        idsStr += checkStatus.data[i].id
                    }
                }
                return idsStr;
            } else {
                layer.msg("未选择有效数据", {offset: "t", anim: 6});
            }
        },
        checkFormat:function(str){
            var formatStr = "";
            for (var i = 0; i < str.length; i++) {
                if(i != element.length-1) {
                    formatStr += element[i].val() + ",";
                } else{
                    formatStr += element[i].val();
                }
            }
            return formatStr;
        },
        normalCkecked:function(element){
            console.log(element);
            var idsStr = "";
            for (var i = 0; i < element.length; i++) {
                if(i != element.length-1) {
                    idsStr += element.eq(i).val() + ",";
                } else{
                    idsStr += element.eq(i).val()
                }
            }
            return idsStr;
        },
        /**
         * 获取树id
         * @param treeData
         * @returns {string}
         */
        treeChecked: function(treeData){
            console.log(treeData);
            console.log(treeData.length==0);

            if(treeData.length==0){
                return "";
            }
            if(treeData.length>0){
                var id = "";
                $.each(treeData, function (index, item) {

                    if (id != "" ) {
                        id = id + "," + item.id;
                    }
                    else {
                        id = item.id;
                    }
                    var i = okUtils.treeChecked(item.children);//递归获取树id
                    if (i != "") {
                        id = id + "," + i;
                    }
                });
                return id;
            }
        },
        /**
         * 在表格页面操作成功后弹窗提示
         * @param content
         */
        tableSuccessMsg: function (content) {
            layer.msg(content, {icon: 1, time: 1000}, function () {
                // 刷新当前页table数据
                $(".layui-laypage-btn")[0].click();
            });
        },
        /**
         * 获取父窗体的okTab
         * @returns {string}
         */
        getOkTab: function () {
            return parent.objOkTab;
        },
        /**
         * 格式化当前日期
         * @param date
         * @param fmt
         * @returns {void | string}
         */
        dateFormat: function (date, fmt) {
            var o = {
                "M+": date.getMonth() + 1,
                "d+": date.getDate(),
                "h+": date.getHours(),
                "m+": date.getMinutes(),
                "s+": date.getSeconds(),
                "q+": Math.floor((date.getMonth() + 3) / 3),
                "S": date.getMilliseconds()
            };
            if (/(y+)/.test(fmt))
                fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
            for (var k in o)
                if (new RegExp("(" + k + ")").test(fmt))
                    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            return fmt;
        },
        location: {
            /**
             * 获取当前所有请求参数集
             * 返回数组
             */
            getRequest: function () {
                var url = location.search; //获取url中"?"符后的字串
                var theRequest = new Object();
                if (url.indexOf("?") != -1) {
                    var str = url.substr(1);
                    strs = str.split("&");
                    for (var i = 0; i < strs.length; i++) {
                        theRequest[strs[i].split("=")[0]] = decodeURI(strs[i].split("=")[1]);
                    }
                }
                return theRequest;
            },
            /**
             * 根据键查询url请求参数值
             * @returns {*}
             *  url?kay=value
             */
            getRequestByKey: function (name) {
                var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
                var r = window.location.search.substr(1).match(reg);
                if (r != null) return decodeURI(r[2]);
                return null;

            },
        },
        number: {
            /**
             * 判断是否为一个正常的数字
             * @param num
             */
            isNumber: function (num) {
                if (num && !isNaN(num)) {
                    return true;
                }
                return false;
            },
            /**
             * 判断一个数字是否包括在某个范围
             * @param num
             * @param begin
             * @param end
             */
            isNumberWith: function (num, begin, end) {
                if (this.isNumber(num)) {
                    if (num >= begin && num <= end) {
                        return true;
                    }
                    return false;
                }
            },
        }
    };
    exprots("okUtils", okUtils);
});
