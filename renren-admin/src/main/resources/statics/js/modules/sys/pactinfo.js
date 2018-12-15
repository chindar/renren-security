$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/pactinfo/list',
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
                        label: '',
                        name: 'name',
                        index: 'name',
                        width: 80
                    }, 
                                                                {
                        label: '',
                        name: 'businessName',
                        index: 'business_name',
                        width: 80
                    }, 
                                                                {
                        label: '',
                        name: 'cityId',
                        index: 'city_id',
                        width: 80
                    }, 
                                                                {
                        label: '',
                        name: 'startDate',
                        index: 'start_date',
                        width: 80
                    }, 
                                                                {
                        label: '',
                        name: 'endDate',
                        index: 'end_date',
                        width: 80
                    }, 
                                                                {
                        label: '',
                        name: 'createTime',
                        index: 'create_time',
                        width: 80
                    }, 
                                                                {
                        label: '',
                        name: 'pactStatus',
                        index: 'pact_status',
                        width: 80
                    }, 
                                                                {
                        label: '',
                        name: 'fileId',
                        index: 'file_id',
                        width: 80
                    }, 
                                                                {
                        label: '',
                        name: 'fileName',
                        index: 'file_name',
                        width: 80
                    }, 
                                                                {
                        label: '',
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
pactInfo: {
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
        vm.pactInfo = {};
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
    .pactInfo.id ==
        null ? "sys/pactinfo/save" : "sys/pactinfo/update";
        $.ajax({
            type: "POST",
            url: baseURL + url,
            contentType: "application/json",
            data: JSON.stringify(vm.pactInfo),
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
                url: baseURL + "sys/pactinfo/delete",
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
        $.get(baseURL + "sys/pactinfo/info/" +id, function (r) {
            vm.pactInfo = r.pactInfo;
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