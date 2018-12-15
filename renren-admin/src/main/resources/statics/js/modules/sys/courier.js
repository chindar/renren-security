$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/courier/list',
        datatype: "json",
        colModel: [
                                                {
                        label: 'id',
                        name: 'id',
                        index: 'id',
                        width: 50,
                        key: true
                    },
                                                                {
                        label: '快递员姓名',
                        name: 'courierName',
                        index: 'courier_name',
                        width: 80
                    }, 
                                                                {
                        label: '',
                        name: 'cardId',
                        index: 'card_id',
                        width: 80
                    }, 
                                                                {
                        label: '',
                        name: 'phone',
                        index: 'phone',
                        width: 80
                    }, 
                                                                {
                        label: '',
                        name: 'bankCardId',
                        index: 'bank_card_id',
                        width: 80
                    }, 
                                                                {
                        label: '开户行名称',
                        name: 'depositBank',
                        index: 'deposit_bank',
                        width: 80
                    }, 
                                                                {
                        label: '联行号',
                        name: 'joinBankNumber',
                        index: 'Join_bank_number',
                        width: 80
                    }, 
                                                                {
                        label: '入职时间',
                        name: 'entryDate',
                        index: 'entry_date',
                        width: 80
                    }, 
                                                                {
                        label: '离职时间',
                        name: 'leaveDate',
                        index: 'leave_date',
                        width: 80
                    }, 
                                                                {
                        label: '1:已绑定第三方 0:未绑定第三方',
                        name: 'status',
                        index: 'status',
                        width: 80
                    }, 
                                                                {
                        label: '',
                        name: 'comment',
                        index: 'comment',
                        width: 80
                    }, 
                                                                {
                        label: 'ERP账号',
                        name: 'erpId',
                        index: 'erp_id',
                        width: 80
                    }, 
                                                                {
                        label: '片区',
                        name: 'area',
                        index: 'area',
                        width: 80
                    }, 
                                                                {
                        label: '站点',
                        name: 'site',
                        index: 'site',
                        width: 80
                    }, 
                                                                {
                        label: '合同id',
                        name: 'pactId',
                        index: 'pact_id',
                        width: 80
                    }, 
                                                                {
                        label: '城市id',
                        name: 'cityId',
                        index: 'city_id',
                        width: 80
                    }, 
                                                                {
                        label: '',
                        name: 'creater',
                        index: 'creater',
                        width: 80
                    }, 
                                                                {
                        label: '',
                        name: 'createDate',
                        index: 'create_date',
                        width: 80
                    }, 
                                                                {
                        label: '',
                        name: 'modify',
                        index: 'modify',
                        width: 80
                    }, 
                                                                {
                        label: '',
                        name: 'modifyDate',
                        index: 'modify_date',
                        width: 80
                    }, 
                                                                {
                        label: '1:删除0:正常',
                        name: 'isDelete',
                        index: 'is_delete',
                        width: 80
                    }
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
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
courier: {
}
},
methods: {
    query: function () {
        vm.reload();
    }
,
    add: function () {
        vm.showList = false;
        vm.title = "新增";
        vm.courier = {};
    }
,
    update: function (event) {
        var id =
        getSelectedRow();
        if (id== null
    )
        {
            return;
        }
        vm.showList = false;
        vm.title = "修改";

        vm.getInfo(id)
    }
,
    saveOrUpdate: function (event) {
        var url = vm
    .courier.id ==
        null ? "sys/courier/save" : "sys/courier/update";
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
    }
,
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
    }
,
    getInfo: function (id) {
        $.get(baseURL + "sys/courier/info/" +id, function (r) {
            vm.courier = r.courier;
        });
    }
,
    reload: function (event) {
        vm.showList = true;
        var page = $("#jqGrid").jqGrid('getGridParam', 'page');
        $("#jqGrid").jqGrid('setGridParam', {
            page: page
        }).trigger("reloadGrid");
    }
}
});