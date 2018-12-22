$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/dispatchinfo/list2',
        datatype: "json",
        colModel: [
                    // {
                    //     label: 'id',
                    //     name: 'id',
                    //     index: 'id',
                    //     width: 50,
                    //     key: true
                    // },
                    {
                        label: '月份',
                        name: 'month',
                        index: 'month',
                        width: 80
                    },{
                        label: '片区',
                        name: 'area',
                        index: 'area',
                        width: 80,
                        sortable: false
                    },{
                        label: '城市',
                        name: 'cityName',
                        index: 'city_name',
                        width: 80,
                        sortable: false
                    },{
                        label: '站点',
                        name: 'site',
                        index: 'site',
                        width: 80,
                        sortable: false
                    },{
                        label: 'erp账号',
                        name: 'erpId',
                        index: 'erp_id',
                        width: 80,
                        sortable: false
                    },
                    {
                        label: '配送员姓名',
                        name: 'courierName',
                        index: 'courier_name',
                        width: 80,
                        sortable: false
                    }, 
                    {
                        label: '总单量',
                        name: 'allOrderTotal',
                        index: 'all_order_total',
                        width: 80
                    }, 
                    {
                        label: '合并后单量',
                        name: 'countOrderTotal',
                        index: 'count_order_total',
                        width: 80
                    },{
                        label: '费用合计',
                        name: 'totalMoney',
                        index: 'total_money',
                        width: 80
                    },
                    {
                        label: '大件',
                        name: 'large',
                        index: 'large',
                        width: 80,
                        sortable: false
                    }, 
                    {
                        label: '小件',
                        name: 'small',
                        index: 'small',
                        width: 80,
                        sortable: false
                    }, 
                    {
                        label: '三同',
                        name: 'thrIdentical',
                        index: 'thr_identical',
                        width: 80,
                        sortable: false
                    }, 
                    {
                        label: '售后取件',
                        name: 'afterSale',
                        index: 'after_sale',
                        width: 80,
                        sortable: false
                    }, 
                    {
                        label: '接货首单量',
                        name: 'firstCount',
                        index: 'first_count',
                        width: 80,
                        sortable: false
                    },
                    {
                        label: '接货续单量',
                        name: 'againCount',
                        index: 'again_count',
                        width: 80,
                        sortable: false
                    },
                    {
                        label: '其他单量',
                        name: 'otherCount',
                        index: 'other_count',
                        width: 80,
                        sortable: false
                    },
                    {
                        label: '差评',
                        name: 'badCount',
                        index: 'bad_count',
                        width: 80
                    },
                    {
                        label: '投诉',
                        name: 'complaintCount',
                        index: 'complaint_count',
                        width: 80
                    },
                    {
                        label: '罚款合计',
                        name: 'fineMoney',
                        index: 'fine_money',
                        width: 80
                    },
                    {
                        label: '其他扣款',
                        name: 'deductMoney',
                        index: 'deduct_money',
                        width: 80
                    },
                    {
                        label: '实发工资',
                        name: 'salary',
                        index: 'salary',
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
    new AjaxUpload('#upload', {
        action: baseURL + "sys/dispatchinfo/importdata",
        name: 'file',
        autoSubmit:true,
        responseType:"json",
        onSubmit:function(file, extension){
            if (!(extension && /^(xls|xlsx)$/.test(extension.toLowerCase()))){
                alert('只支持导入excel格式的文件！');
                return false;
            }
        },
        onComplete : function(file, r){
            if(r.code == 0){
                console.log(r)
                vm.reload();
            }else{
                alert(r.msg);
            }
        }
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        dispatchInfo: {
        },
        q:{
            courierName: null,
            month:null
        }
},
methods: {
    query: function () {
        vm.reload();
    },
    //下载模板
    down: function () {
        location.href = encodeURI("/admin/statics/快递员配送数据导入模板1.xlsx");
    },
    importFile: function () {
        console.info("@@@@@@@@@@")
    },
    exportFile: function (event) {
        var ids = getSelectedRows();
        if (ids == null) {
            return;
        }else{

        }
        // confirm('确定要导出选中的记录？', function () {
            window.open("/admin/sys/dispatchinfo/exportdata?id="+ids.join(","));
        // })
    },
    add: function () {
        vm.showList = false;
        vm.title = "新增";
        vm.dispatchInfo = {};
    },
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
    },
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
        console.log(vm.q)
        vm.showList = true;
        var page = $("#jqGrid").jqGrid('getGridParam', 'page');
        $("#jqGrid").jqGrid('setGridParam', {
            postData:{'courierName': vm.q.courierName,'month':vm.q.month},
            page: page
        }).trigger("reloadGrid");
    }
}
});