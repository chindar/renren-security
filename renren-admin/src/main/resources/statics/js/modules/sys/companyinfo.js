$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/companyinfo/list',
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
                        name: 'cityId',
                        index: 'city_id',
                        width: 80
                    }, 
                                                                {
                        label: '法人',
                        name: 'legalPersonName',
                        index: 'legal_person_name',
                        width: 80
                    }, 
                                                                {
                        label: '',
                        name: 'address',
                        index: 'address',
                        width: 80
                    }, 
                                                                {
                        label: '',
                        name: 'email',
                        index: 'email',
                        width: 80
                    }, 
                                                                {
                        label: '邮编',
                        name: 'zipCode',
                        index: 'zip_code',
                        width: 80
                    }, 
                                                                {
                        label: '',
                        name: 'contactName',
                        index: 'contact_name',
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
                        name: 'businessFileid',
                        index: 'business_fileid',
                        width: 80
                    }, 
                                                                {
                        label: '',
                        name: 'cardFileid',
                        index: 'card_fileid',
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
companyInfo: {
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
        vm.companyInfo = {};
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
    .companyInfo.id ==
        null ? "sys/companyinfo/save" : "sys/companyinfo/update";
        $.ajax({
            type: "POST",
            url: baseURL + url,
            contentType: "application/json",
            data: JSON.stringify(vm.companyInfo),
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
                url: baseURL + "sys/companyinfo/delete",
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
        $.get(baseURL + "sys/companyinfo/info/" +id, function (r) {
            vm.companyInfo = r.companyInfo;
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