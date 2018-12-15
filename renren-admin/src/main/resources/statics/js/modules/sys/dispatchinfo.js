$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/dispatchinfo/list',
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
                        label: '月份',
                        name: 'month',
                        index: 'month',
                        width: 80
                    }, 
                                                                {
                        label: '快递员id',
                        name: 'courierId',
                        index: 'courier_id',
                        width: 80
                    }, 
                                                                {
                        label: '',
                        name: 'allOrderTotal',
                        index: 'all_order_total',
                        width: 80
                    }, 
                                                                {
                        label: '',
                        name: 'countOrderTotal',
                        index: 'count_order_total',
                        width: 80
                    }, 
                                                                {
                        label: '大件',
                        name: 'large',
                        index: 'large',
                        width: 80
                    }, 
                                                                {
                        label: '',
                        name: 'small',
                        index: 'small',
                        width: 80
                    }, 
                                                                {
                        label: '三同',
                        name: 'thrIdentical',
                        index: 'thr_identical',
                        width: 80
                    }, 
                                                                {
                        label: '售后取件',
                        name: 'afterSale',
                        index: 'after_sale',
                        width: 80
                    }, 
                                                                {
                        label: '商家接货',
                        name: 'sellerPick',
                        index: 'seller_pick',
                        width: 80
                    }, 
                                                                {
                        label: '工资',
                        name: 'salary',
                        index: 'salary',
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
                        label: '1:删除0：正常',
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
dispatchInfo: {
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
        vm.dispatchInfo = {};
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
    .dispatchInfo.id ==
        null ? "sys/dispatchinfo/save" : "sys/dispatchinfo/update";
        $.ajax({
            type: "POST",
            url: baseURL + url,
            contentType: "application/json",
            data: JSON.stringify(vm.dispatchInfo),
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
                url: baseURL + "sys/dispatchinfo/delete",
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
        $.get(baseURL + "sys/dispatchinfo/info/" +id, function (r) {
            vm.dispatchInfo = r.dispatchInfo;
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