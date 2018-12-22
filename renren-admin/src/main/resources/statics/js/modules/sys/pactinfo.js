$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/pactinfo/list2',
        datatype: "json",
        colModel: [
                    //                             {
                    //     label: 'id',
                    //     name: 'id',
                    //     index: 'id',
                    //     width: 50,
                    //     key: true
                    // },
                                                                {
                        label: '合同名称',
                        name: 'name',
                        index: 'name',
                        width: 80
                    }, 
                                                                {
                        label: '业务',
                        name: 'businessName',
                        index: 'business_name',
                        width: 80
                    }, 
                                                                {
                        label: '城市',
                        name: 'cityName',
                        index: 'city_name',
                        width: 80,
                        sortable: false
                    }, 
                                                                {
                        label: '起始日期',
                        name: 'startDate',
                        index: 'start_date',
                        width: 80
                    }, 
                                                                {
                        label: '终止日期',
                        name: 'endDate',
                        index: 'end_date',
                        width: 80
                    }, 
                                                                {
                        label: '创建时间',
                        name: 'createTime',
                        index: 'create_time',
                        width: 80
                    }, 
                                                                {
                        label: '状态',
                        name: 'pactStatus',
                        index: 'pact_status',
                        width: 80,
                        formatter: function(value, options, row){
                            return value === 0 ?
                                '<span class="label label-danger">无效</span>' :
                                '<span class="label label-success">已生效</span>';
                        }
                    }, 
                                                                {
                        label: '操作',
                        name: 'fileId',
                        index: 'file_id',
                        width: 80,
                        sortable: false,
                        formatter: function(value, options, row){
                            return '<a class="caozuo" href="'+row.fileUrl+'">合同</a>';
                                // '&nbsp;&nbsp;<a class="caozuo">人员</a>';
                        }
                    }
                    // ,
                    //                                             {
                    //     label: '',
                    //     name: 'fileName',
                    //     index: 'file_name',
                    //     width: 80
                    // },
                    //                                             {
                    //     label: '',
                    //     name: 'isDelete',
                    //     index: 'is_delete',
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
    $.ajax({
        type: "POST",
        url: baseURL + "sys/cityinfo/list",
        contentType: "application/json",
        // data: JSON.stringify(ids),
        success: function (r) {
            if (r.code == 0) {
                // alert('操作成功', function (index) {
                //     $("#jqGrid").trigger("reloadGrid");
                // });
                vm.citylist = r.page.list
            } else {
                alert(r.msg);
            }
        }
    });
    new AjaxUpload('#upload', {
        action: baseURL + "sys/pactinfo/upload",
        name: 'file',
        autoSubmit:true,
        responseType:"json",
        onSubmit:function(file, extension){
            // if(vm.config.type == null){
            //     alert("云存储配置未配置");
            //     return false;
            // }
            if (!(extension && /^(pdf)$/.test(extension.toLowerCase()))){
                alert('只支持pdf格式的合同文件！');
                return false;
            }
        },
        onComplete : function(file, r){
            if(r.code == 0){
                // alert(r.url);
                vm.pactInfo.fileId = r.data.fileId
                vm.pactInfo.fileName = r.data.fileName
                $(".filename_class").text(r.data.fileName)
                // vm.reload();
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
        pactInfo: {
        },
        q:{
            businessName: null,
            cityId:'',
            pactStatus:''
        },
        citylist:[]
    },
methods: {
    query: function () {
        vm.reload();
    }
,
    add: function () {
        vm.showList = false;
        vm.title = "新增";
        vm.pactInfo = {
            cityId: ''
        };
        $(".filename_class").text("");
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
        console.info(vm.pactInfo)
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
            postData:{'businessName': vm.q.businessName,'cityId':vm.q.cityId,'pactStatus':vm.q.pactStatus},
            page: page
        }).trigger("reloadGrid");

    },
}
});