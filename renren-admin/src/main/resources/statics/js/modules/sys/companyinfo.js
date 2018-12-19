$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/companyinfo/list2',
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
                        label: '公司名称',
                        name: 'name',
                        index: 'name',
                        width: 80
                    }, 
                    {
                        label: '城市',
                        name: 'cityName',
                        index: 'city_name',
                        width: 80
                    }, 
                    {
                        label: '法人',
                        name: 'legalPersonName',
                        index: 'legal_person_name',
                        width: 80
                    }, 
                    {
                        label: '地址',
                        name: 'address',
                        index: 'address',
                        width: 80
                    }, 
                    {
                        label: '邮箱',
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
                        label: '联系人姓名',
                        name: 'contactName',
                        index: 'contact_name',
                        width: 80
                    }, 
                    {
                        label: '联系人电话',
                        name: 'phone',
                        index: 'phone',
                        width: 80
                    }, 
                    {
                        label: '营业执照',
                        name: 'businessFileid',
                        index: 'business_fileid',
                        width: 80,
                        formatter: function(value, options, row){
                            return '<img class="caozuo" src="'+row.businessFileUrl+'">';
                        }
                    }, 
                    {
                        label: '法人身份证',
                        name: 'cardFileid',
                        index: 'card_fileid',
                        width: 80,
                        formatter: function(value, options, row){
                            return '<img class="caozuo" src="'+row.cardFileUrl+'">';
                        }
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
        action: baseURL + "sys/companyinfo/upload",
        name: 'file',
        autoSubmit:true,
        responseType:"json",
        data:{dbname:"businessFile"},
        onSubmit:function(file, extension){
            if (!(extension && /^(jpg|png)$/.test(extension.toLowerCase()))){
                alert('只支持jpg、png格式的图片！');
                return false;
            }
        },
        onComplete : function(file, r){
            if(r.code == 0){
                vm.companyInfo.businessFileid = r.data.fileId
                $("#aaa").attr("src",r.data.fileUrl)
                // vm.companyInfo.businessFileUrl = r.data.fileUrl
            }else{
                alert(r.msg);
            }
            console.log(vm.companyInfo);
        }
    });
    new AjaxUpload('#upload2', {
        action: baseURL + "sys/companyinfo/upload",
        name: 'file',
        autoSubmit:true,
        responseType:"json",
        data:{dbname:"cardFile"},
        onSubmit:function(file, extension){
            if (!(extension && /^(jpg|png)$/.test(extension.toLowerCase()))){
                alert('只支持jpg、png格式的图片！');
                return false;
            }
        },
        onComplete : function(file, r){
            if(r.code == 0){
                vm.companyInfo.cardFileid = r.data.fileId
                $("#bbb").attr("src",r.data.fileUrl)
                // vm.companyInfo.cardFileUrl = r.data.fileUrl
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
        companyInfo: {

        },
        citylist: []
},
methods: {
    query: function () {
        vm.reload();
    }
,
    add: function () {
        vm.showList = false;
        vm.title = "新增";
        vm.companyInfo = { cityId: ''
            // businessFileUrl : 'http://localhost:8080/renren-admin/sys/companyinfo/getFile?fileId=1545211156491&dbname=businessFile'
        };
        $("#aaa").attr("src","/renren-admin/statics/default.png")
        $("#bbb").attr("src","/renren-admin/statics/default.png")
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