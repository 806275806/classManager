loadTable();//初始页面加载表格
$('#gender-sel').selectpicker('refresh');//解决bootstrap-select的一个小问题，刷新才能显示
getdept($('#deptSel-ForSearch'));
getRoles($('#roles-forsearch'));
$('#search_btn').click(function () {
    $('#user-tab').bootstrapTable('refreshOptions',{pageNumber:1});
});
function loadTable() {
    $('#user-tab').bootstrapTable({
        url: '../../getUserList',
        striped:true,
        pageNumber : 1, //初始化加载第一页
        sidePagination : 'server',//server:服务器端分页|client：前端分页
        pagination: true,//是否分页
        sortable: true, // 是否启用排序
        showRefresh : true,//刷新按钮
        toolbar:"#tab-tool",
        pageSize:10,
        pageList : [ 5, 10, 20, 30 ],//可选择单页记录数
        singleSelect:false,//是否单选，false表示多选;true标识只能单选
        clickToSelect: true,//启用点击某行就选中某行
        queryParams : function(params) {//上传服务器的参数
            var getRole='';
            var arr=$('#roles-forsearch').val();//存放多选下拉框的选择数组
            if (arr==null){
                getRole='';
            } else {
                 getRole=arr.join(',');//将数组内容用","拼接为字符串
            }
            var temp = {
                offset :params.offset + 0,// SQL语句起始索引
                pageNumber : params.limit, // 每页显示数量
                userCode:$('#userCode-Forsearch').val(),
                deptId:$('#deptSel-ForSearch').val(),
                userName:$('#userName-Forsearch').val(),
                userGender:$('#gender-sel').val(),
                haveRole:getRole
            };
            return temp;
        },
        columns: [{
            field: 'ischeck', checkbox: true
        },
            {
                field: 'userCode',
                title: '职工编号（用户名）'
            },
            {
                field: 'deptName',
                title: '所属院系'
            },
            {
                field: 'deptId',
                title: '所属院系',
                visible: false
            },
            {
                field: 'userName',
                title: '姓名'
            },
            {
                field: 'userGender',
                title: '性别'
            },
            {
                field: 'userTel',
                title: '联系方式'
            },
            {
                field: 'haveRole',
                title: '角色id',
                visible: false
            },
            {
                field: 'roleName',
                title: '角色',
                // formatter:function (value,row) {
                //     var data=JSON.stringify(row);
                //     var List=JSON.parse(data);
                //     var roles=List.roles;
                //     var result='';
                //     for (i=0;i<roles.length;i++){
                //         result+=roles[i].role+';';
                //     }
                //     return result;
                // }
            },
            {
                field: 'operate',
                title: '操作',
                width: 120,
                align: 'center',
                valign: 'middle',
                formatter: actionFormatter,
            }
        ]
    });
    //操作栏的格式化,value代表当前单元格中的值，row代表当前行数据，index表示当前行的下标
    function actionFormatter(value, row, index) {
        var id = index;
        var data = JSON.stringify(row).replace(/\"/g, "'");
        var result = "";
        result += "<a href='javascript:;' class='btn btn-xs' onclick=\"dealUser('2'," + data + ")\" title='编辑'><span class='glyphicon glyphicon-pencil' style='color: #3E8F3E'></span></a>";
        result += "<a href='javascript:;' class='btn btn-xs' onclick=\"delUser(" + data + ")\" title='删除'><span class='glyphicon glyphicon-trash' style='color: red'></span></a>";
        return result;

    }
}
function getdept(t,selected) {
    //院系选择框加载
    $.ajax({
        type: "GET",
        url: '../../justGetDept' ,
        contentType: "application/json",
        data: {},
        success: function (r) {
            var List=eval(r);
            t.empty();
            t.append('<option value=""  >全部</option>');
            for (i=0;i<List.length;i++){
                    t.append(
                        "<option value='" + List[i].deptId + "'>" + List[i].deptName + "</option>"
                    )
                }
            t.selectpicker('val',selected);
            t.selectpicker({width: '180px'});
            t.selectpicker('refresh');
        }
    });
}
function getRoles(t,selected) {
    $.ajax({
        type: "GET",
        url: '../../getRoleList' ,
        contentType: "application/json",
        data: {},
        success: function (r) {
            var List=eval(r);
            t.empty();
            t.append('<option value="0"  >全部</option>');
            for (i=0;i<List.length;i++){
                t.append(
                    "<option value='" + List[i].roleId + "'>" + List[i].role + "</option>"
                )
            }
            t.selectpicker('val',selected);
            t.selectpicker({width: '180px'});
            t.selectpicker('refresh');
        }
    });
}
//弹出用户模态框
function dealUser(dealType,row) {
    if (dealType==1){
        $('#addUser_h4').css('display','block');
        $('#updateUser_h4').css('display','none');
        $('#addUser_footer').css('display','block');
        $('#updateUser_footer').css('display','none');
        $('#userCode').val('');
        $('#dept_Modal').val('');
        $('#userName').val('');
        $('#gender_Modal').val('');
        $('#userTel').val('');
        $('#roles-modal').val('');
        getdept($('#dept_Modal'));
        getRoles($('#roles-modal'));
    } else {
        $('#addUser_h4').css('display','none');
        $('#updateUser_h4').css('display','block');
        $('#addUser_footer').css('display','none');
        $('#updateUser_footer').css('display','block');
        getdept($('#dept_Modal'),row.deptId);
        $('#userCode').val(row.userCode);
        $('#userName').val(row.userName);
        $('#gender_Modal').val(row.userGender);
        $('#userTel').val(row.userTel);
        var selected=(row.haveRole).split(',');//将haveRole(角色对应的Id)的字符串转为数组
        getRoles($('#roles-modal'),selected);
        $('#role_a').val(selected);
    }
    $('#UserModal').modal();
}
// 添加用户
function addUser() {
    var obj=serializeObject($('#User_form'));
    if (obj.userCode==null||obj.userCode==''){
        Swal.fire({
            position: 'center',
            type: 'error',
            title: '请输入职工号(用户名)',
            showConfirmButton: false,
            timer: 1500
        });
    }else if (obj.deptId==null||obj.deptId=='') {
        Swal.fire({
            position: 'center',
            type: 'error',
            title: '请选择院系',
            showConfirmButton: false,
            timer: 1500
        });
    }else if (obj.userName==null||obj.userName==''){
        Swal.fire({
            position: 'center',
            type: 'error',
            title: '请输入姓名',
            showConfirmButton: false,
            timer: 1500
        });
    }else if (obj.userGender==null||obj.userGender==''){
        Swal.fire({
            position: 'center',
            type: 'error',
            title: '请选择性别',
            showConfirmButton: false,
            timer: 1500
        });
    }else if (obj.userTel==null||obj.userTel==''){
        Swal.fire({
            position: 'center',
            type: 'error',
            title: '请输入联系方式',
            showConfirmButton: false,
            timer: 1500
        });
    }else if (obj.haveRole==null||obj.haveRole==''){
        Swal.fire({
            position: 'center',
            type: 'error',
            title: '请选择角色',
            showConfirmButton: false,
            timer: 1500
        });
    }else {
        $.ajax({
            type: "POST",
            url: '../../addUser',
            contentType: "application/json",
            data: JSON.stringify(obj),
            success: function () {
                Swal.fire({
                    position: 'center',
                    type: 'success',
                    title: '添加成功',
                    showConfirmButton: false,
                    timer: 1500
                });
                var page=$("#user-tab").bootstrapTable("getOptions").pageNumber;
                $('#user-tab').bootstrapTable('refreshOptions', {pageNumber: page});
                $('#UserModal').modal('hide');
            },
            error: function () {
                Swal.fire({
                    position: 'center',
                    type: 'error',
                    title: '添加失败',
                    showConfirmButton: false,
                    timer: 1500
                });
            }
        });
    }
}
// 更新用户
function updateUser() {
    var obj=serializeObject($('#User_form'));
    if (obj.userCode==null||obj.userCode==''){
        Swal.fire({
            position: 'center',
            type: 'error',
            title: '请输入职工号(用户名)',
            showConfirmButton: false,
            timer: 1500
        });
    }else if (obj.deptId==null||obj.deptId=='') {
        Swal.fire({
            position: 'center',
            type: 'error',
            title: '请选择院系',
            showConfirmButton: false,
            timer: 1500
        });
    }else if (obj.userName==null||obj.userName==''){
        Swal.fire({
            position: 'center',
            type: 'error',
            title: '请输入姓名',
            showConfirmButton: false,
            timer: 1500
        });
    }else if (obj.userGender==null||obj.userGender==''){
        Swal.fire({
            position: 'center',
            type: 'error',
            title: '请选择性别',
            showConfirmButton: false,
            timer: 1500
        });
    }else if (obj.userTel==null||obj.userTel==''){
        Swal.fire({
            position: 'center',
            type: 'error',
            title: '请输入联系方式',
            showConfirmButton: false,
            timer: 1500
        });
    }else if (obj.haveRole==null||obj.haveRole==''){
        Swal.fire({
            position: 'center',
            type: 'error',
            title: '请选择角色',
            showConfirmButton: false,
            timer: 1500
        });
    }else {
        $.ajax({
            type: "POST",
            url: '../../updateUser',
            contentType: "application/json",
            data: JSON.stringify(obj),
            success: function () {
                Swal.fire({
                    position: 'center',
                    type: 'success',
                    title: '修改成功',
                    showConfirmButton: false,
                    timer: 1500
                });
                var page=$("#user-tab").bootstrapTable("getOptions").pageNumber;
                $('#user-tab').bootstrapTable('refreshOptions', {pageNumber: page});
                $('#UserModal').modal('hide');
            },
            error: function () {
                Swal.fire({
                    position: 'center',
                    type: 'error',
                    title: '修改失败，已存在职工号',
                    showConfirmButton: false,
                    timer: 1500
                });
            }
        });
    }
}
// 删除用户
function delUser(row) {
    swal.fire({

        title: '确认删除？',

        text: '删除该用户信息后将无法恢复',

        type: 'warning',

        showCancelButton: true,

        confirmButtonText: '确认',

        cancelButtonText: '取消',

    }).then(function(isConfirm) {
        if(isConfirm.value){
            $.ajax({
                type: "POST",
                url: '../../delUser',
                contentType: "application/json",
                data: JSON.stringify(row),
                success: function (r) {
                    Swal.fire({
                        position: 'center',
                        type: 'success',
                        title: '删除成功',
                        showConfirmButton: false,
                        timer: 1500
                    });
                    var page=$("#user-tab").bootstrapTable("getOptions").pageNumber;
                    $('#user-tab').bootstrapTable('refreshOptions', {pageNumber: page});
                },
                error: function () {
                    Swal.fire({
                        position: 'center',
                        type: 'error',
                        title: '删除失败，该用户已与其他信息关联',
                        showConfirmButton: false,
                        timer: 1500
                    });
                }
            });
        }
    });
}
//获取表格多选数据数组，并删除
function table_select(){
    var selected = $('#user-tab').bootstrapTable('getSelections');
    swal.fire({

        title: '是否删除选中项？',

        text: '删除选中信息后将无法恢复',

        type: 'warning',

        showCancelButton: true,

        confirmButtonText: '确认',

        cancelButtonText: '取消',

    }).then(function(isConfirm) {
        if(isConfirm.value){
            var all='';//计数单位，用于计算总共操作的数据条数。暂时无用
            var count='';
            //返回值为数据对象数组
            if(selected&&selected.length>0){
                //非空数组时候进行的操作
                all=parseInt(0);
                count=parseInt(0);
                $.each(selected,function(index,item){
                    all=parseInt(all)+parseInt(1);
                    $.ajax({
                        type: "POST",
                        url: '../../delUser',
                        contentType: "application/json",
                        data: JSON.stringify(item),
                        success: function (r) {
                            count=parseInt(count)+parseInt(1);
                        },
                        error: function () {
                            Swal.fire({
                                position: 'center',
                                type: 'error',
                                title: '删除失败，‘'+item.className+'’已与其他信息关联',
                                showConfirmButton: false,
                                timer: 1500
                            });
                        }
                    });
                });
                var page=$("#user-tab").bootstrapTable("getOptions").pageNumber;
                $('#user-tab').bootstrapTable('refreshOptions', {pageNumber: page});
            }else{
                //空数组的操作
                Swal.fire({
                    position: 'center',
                    type: 'warning',
                    title: '请选择要删除的数据',
                    showConfirmButton: false,
                    timer: 1500
                });
            }
        }
    });
}
//表单序列化转对象
function serializeObject(t) {
    var data = t.serializeArray(),
        obj = {};
    $.each(data, function(i, v) {
        obj[v.name] = v.value;
    });
    return obj;
}
//角色多选信息同步到隐藏的input
function setinput() {
    $('#role_a').val($('#roles-modal').val());
}