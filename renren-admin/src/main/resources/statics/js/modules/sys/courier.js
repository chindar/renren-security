$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/courier/list',
        datatype: "json",
        colModel: [
            {
                label: 'ID',
                name: 'erpId',
                index: 'erp_id',
                width: 80
            },
            {
                label: '合同名称',
                name: 'pactName',
                index: 'pact_name',
                width: 80
            },
            {
                label: '姓名',
                name: 'courierName',
                index: 'courier_name',
                width: 80
            },
            {
                label: '电话',
                name: 'phone',
                index: 'phone',
                width: 80
            },
            {
                label: '身份证号',
                name: 'cardId',
                index: 'card_id',
                width: 80
            },
            {
                label: '状态',
                name: 'status',
                index: 'status',
                width: 80,
                formatter: function (value, options, row) {
                    return value === 0 ?
                        '<span class="label label-danger">未绑定第三方</span>' :
                        '<span class="label label-success">已绑定第三方</span>';
                }
            },
            // {
            //     label: '银行卡号',
            //     name: 'bankCardId',
            //     index: 'bank_card_id',
            //     width: 80
            // },
            // {
            //     label: '开户行名称',
            //     name: 'depositBank',
            //     index: 'deposit_bank',
            //     width: 80
            // },
            // {
            //     label: '联行号',
            //     name: 'joinBankNumber',
            //     index: 'Join_bank_number',
            //     width: 80
            // },
            // {
            //     label: '入职时间',
            //     name: 'entryDate',
            //     index: 'entry_date',
            //     width: 80
            // },
            // {
            //     label: '离职时间',
            //     name: 'leaveDate',
            //     index: 'leave_date',
            //     width: 80
            // },
            //
            // {
            //     label: '备注',
            //     name: 'comment',
            //     index: 'comment',
            //     width: 80
            // },
            //
            // {
            //     label: '片区',
            //     name: 'area',
            //     index: 'area',
            //     width: 80
            // },
            // {
            //     label: '站点',
            //     name: 'site',
            //     index: 'site',
            //     width: 80
            // },
            //
            // {
            //     label: '城市',
            //     name: 'cityId',
            //     index: 'city_id',
            //     width: 80
            // },
            // {
            //     label: '创建人',
            //     name: 'creater',
            //     index: 'creater',
            //     width: 80
            // },
            // {
            //     label: '创建时间',
            //     name: 'createDate',
            //     index: 'create_date',
            //     width: 80
            // },
            // {
            //     label: '修改人',
            //     name: 'modify',
            //     index: 'modify',
            //     width: 80
            // },
            // {
            //     label: '修改时间',
            //     name: 'modifyDate',
            //     index: 'modify_date',
            //     width: 80
            // }
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });

    new AjaxUpload('#upload', {
        action: baseURL + "sys/courier/import",
        name: 'file',
        autoSubmit: true,
        responseType: "json",
        onSubmit: function (file, extension) {

            if (vm.pactId == null) {
                alert("录入之前请先选择某一个合同, 之后再错做");
                return false;
            }
            if (!(extension && /^(xlsx)$/.test(extension.toLowerCase()))) {
                alert('只支持xlsx格式的文件！');
                return false;
            }
        },
        onComplete: function (file, r) {
            if (r.code == 0) {
                vm.batchId = r.batchId;
            } else {
                alert(r.msg);
            }
        },
    });
});


var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        keyword: 0,
        q: {
            name: null
        },
        courier: {},
        pactId: null,
        pactList: [],
        batchId: null
    },
    watch: {
        batchId: function (newBatchId, oldBatchId) {
            if (newBatchId != null) {
                this.editBatch(newBatchId);
            }
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.showImport = true;
            vm.title = "新增";
            vm.courier = {};
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null
            ) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            var url = vm.courier.id == null ? "sys/courier/save" : "sys/courier/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.courier),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },

        del: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/courier/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function (index) {
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        getInfo: function (id) {
            $.get(baseURL + "sys/courier/info/" + id, function (r) {
                vm.courier = r.courier;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            if (vm.keyword == 0) {
                $("#jqGrid").jqGrid('setGridParam', {
                    postData: {"courierName": vm.q.name},
                    page: page
                }).trigger("reloadGrid");
            } else if (vm.keyword == 1) {
                $("#jqGrid").jqGrid('setGridParam', {
                    postData: {"cardId": vm.q.name},
                    page: page
                }).trigger("reloadGrid");
            } else if (vm.keyword == 2) {
                $("#jqGrid").jqGrid('setGridParam', {
                    postData: {"phone": vm.q.name},
                    page: page
                }).trigger("reloadGrid");
            } else {
                $("#jqGrid").jqGrid('setGridParam', {
                    page: page
                }).trigger("reloadGrid");
            }

        },

        /**********************************************************************
         * 获取合同信息
         * @author Wang Chinda
         **********************************************************************/
        getPact: function () {
            $.ajax({
                type: "GET",
                url: baseURL + "sys/pactinfo/all",
                contentType: "application/json",
                success: function (r) {
                    if (r.code == 0) {
                        vm.pactList = r.pactList;
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },

        editBatch: function (batchId) {
            var data = {};
            data["batchId"] = batchId;
            data["pactId"] = vm.pactId;
            $.ajax({
                type: "POST",
                url: baseURL + "sys/courier/editPact",
                data: data,
                success: function (r) {
                    if (r.code == 0) {
                        alert('导入成功', function (index) {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },

        /**********************************************************************
         * 表单校验
         * @author Wang Chinda
         **********************************************************************/
        validator: function () {
            if (isBlank(vm.courier.name)) {
                alert("快递员名称不能为空");
                return true;
            }
        }
    },

    created: function () {
        this.getPact();
    }
});