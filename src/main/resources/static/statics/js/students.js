loadTable();
getdept($('#deptSel'));
getmajor($('#majorSel'),$('#deptSel'));
$('#search_btn').click(function () {
    $('#stu-tab').bootstrapTable('refreshOptions',{pageNumber:1});
});
//加载表格
function loadTable() {
    $('#stu-tab').bootstrapTable({
        url: '../../getStuList',
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
            var temp = {
                offset :params.offset + 0,// SQL语句起始索引
                pageNumber : params.limit,// 每页显示数量
                sGrade:$('#sGrade').val(),
                sNo:$('#sNo').val(),
                deptId:$('#deptSel').val(),
                majorCode:$('#majorSel').val(),
                className:$('#sClass').val(),
                sName:$('#sName').val(),
                sGender:$('#gender-sel').val()
            };
            return temp;
        },
        columns: [{
            field: 'ischeck', checkbox: true
        },
            {
                field: 'sGrade',
                title: '年级'
            },
            {
                field: 'sNo',
                title: '学号'
            },
            {
                field: 'deptId',
                title: '所属院系',
                visible: false
            },
            {
                field: 'deptName',
                title: '院系',
            },
            {
                field: 'majorCode',
                title: '所属专业',
                visible: false
            },
            {
                field: 'majorName',
                title: '专业',
            },
            {
                field: 'classCode',
                title: '所属班级',
                visible: false
            },
            {
                field: 'className',
                title: '班级',
            },
            {
                field: 'sName',
                title: '姓名'
            },
            {
                field: 'sGender',
                title: '性别'
            },
            {
                field: 'sTel',
                title: '联系方式'
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
        result += "<a href='javascript:;' class='btn btn-xs' onclick=\"dealStu('2'," + data + ")\" title='编辑'><span class='glyphicon glyphicon-pencil' style='color: #3E8F3E'></span></a>";
        result += "<a href='javascript:;' class='btn btn-xs' onclick=\"delStudents(" + data + ")\" title='删除'><span class='glyphicon glyphicon-trash' style='color: red'></span></a>";
        return result;

    }
}
//弹出模态框
function dealStu(dealType,row) {
    $('#sGrade-modal').datetimepicker({
        format: 'yyyy', startView : "decade", minView: 'decade',language : 'zh-CN',autoclose: true, clearBtn : true
    });
    if (dealType==1){
        $('#addStu_h4').css('display','block');
        $('#updateStu_h4').css('display','none');
        $('#addStu_footer').css('display','block');
        $('#updateStu_footer').css('display','none');
        setclass($('#sClass-modal'),$('#major-modal'),$('#sGrade-modal'));
        $('#sGrade-modal').val('');
        $('#sNo-modal').val('');
        $('#dept-modal').val('');
        $('#major-modal').val('');
        $('#sClass-modal').val('');
        $('#sName-modal').val('');
        $('#sGender-modal').val('');
        $('#sTel-modal').val('');
        getdept( $('#dept-modal'));
        getmajor($('#major-modal'),$('#dept-modal'));
    } else {
        $('#addStu_h4').css('display','none');
        $('#updateStu_h4').css('display','block');
        $('#addStu_footer').css('display','none');
        $('#updateStu_footer').css('display','block');
        $('#sGrade-modal').val(row.sGrade);
        $('#sNo-modal').val(row.sNo);
        $('#sName-modal').val(row.sName);
        $('#sGender-modal').selectpicker('val',row.sGender);
        $('#sGender-modal').selectpicker('refresh');
        $('#sTel-modal').val(row.sTel);
        $('#dept-modal').val(row.deptId);
        $('#major-modal').val(row.majorCode);
        getdept( $('#dept-modal'),row.deptId);
        getmajor($('#major-modal'),$('#dept-modal'),row.majorCode);
        setclass($('#sClass-modal'),$('#major-modal'),$('#sGrade-modal'),row.classCode);
    }
    $('#StuModal').modal();
}
function dealImportStu() {
    $('#importModal').modal();
    $('#if_finish').css('display','none');
}
//学生信息导入
function importStu() {
    var fileName=$('#inFile').val();
    if (fileName==null||fileName==''){
        Swal.fire({
            position: 'center',
            type: 'error',
            title: '请选择导入文件',
            showConfirmButton: false,
            timer: 1500
        });
    }else {
        var excelFile=new FormData($("#files-form")[0]);
        $.ajax({
            url : "../../importexcel",
            data: excelFile,
            type : "POST",
            // 告诉jQuery不要去处理发送的数据，用于对data参数进行序列化处理 这里必须false
            processData : false,
            // 告诉jQuery不要去设置Content-Type请求头
            contentType : false,
            dataType:"json",
            success : function(json) {
                var error=json.errors;
                for (i=0;i<error.length;i++){
                    $('#if_error').append(error[i]+'<br/>');
                }
                $('#if_finish').css('display','block');
                $('#if_finish').text(json.message);
                if (error.length!=0){
                    $('#if_error').css('display','block');
                }
                $('#stu-tab').bootstrapTable('refreshOptions',{pageNumber:1});
            },
            error : function(json) {
            }
        });
    }
}
//添加学生信息
function addStu() {
    var obj=serializeObject($('#Stu_form'));
    if (obj.sGrade==null||obj.sGrade==''){
        Swal.fire({
            position: 'center',
            type: 'error',
            title: '请输入年级',
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
    }else if (obj.majorCode==null||obj.majorCode==''){
        Swal.fire({
            position: 'center',
            type: 'error',
            title: '请选择专业',
            showConfirmButton: false,
            timer: 1500
        });
    }else if (obj.classCode==null||obj.classCode==''){
        Swal.fire({
            position: 'center',
            type: 'error',
            title: '请选择班级',
            showConfirmButton: false,
            timer: 1500
        });
    }else if (obj.sNo==null||obj.sNo==''){
        Swal.fire({
            position: 'center',
            type: 'error',
            title: '请输入学号',
            showConfirmButton: false,
            timer: 1500
        });
    }else if (obj.sName==null||obj.sName==''){
        Swal.fire({
            position: 'center',
            type: 'error',
            title: '请输入姓名',
            showConfirmButton: false,
            timer: 1500
        });
    }else if (obj.sGender==null||obj.sGender==''){
        Swal.fire({
            position: 'center',
            type: 'error',
            title: '请选择性别',
            showConfirmButton: false,
            timer: 1500
        });
    }
    else {
        $.ajax({
            type: "POST",
            url: '../../addStudents',
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
                var page=$("#stu-tab").bootstrapTable("getOptions").pageNumber;
                $('#stu-tab').bootstrapTable('refreshOptions', {pageNumber: page});
                $('#StuModal').modal('hide');
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
//修改学生信息
function updateStu() {
    var obj=serializeObject($('#Stu_form'));
    if (obj.sGrade==null||obj.sGrade==''){
        Swal.fire({
            position: 'center',
            type: 'error',
            title: '请输入年级',
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
    }else if (obj.majorCode==null||obj.majorCode==''){
        Swal.fire({
            position: 'center',
            type: 'error',
            title: '请选择专业',
            showConfirmButton: false,
            timer: 1500
        });
    }else if (obj.classCode==null||obj.classCode==''){
        Swal.fire({
            position: 'center',
            type: 'error',
            title: '请选择班级',
            showConfirmButton: false,
            timer: 1500
        });
    }else if (obj.sNo==null||obj.sNo==''){
        Swal.fire({
            position: 'center',
            type: 'error',
            title: '请输入学号',
            showConfirmButton: false,
            timer: 1500
        });
    }else if (obj.sName==null||obj.sName==''){
        Swal.fire({
            position: 'center',
            type: 'error',
            title: '请输入姓名',
            showConfirmButton: false,
            timer: 1500
        });
    }else if (obj.sGender==null||obj.sGender==''){
        Swal.fire({
            position: 'center',
            type: 'error',
            title: '请选择性别',
            showConfirmButton: false,
            timer: 1500
        });
    }
    else {
        $.ajax({
            type: "POST",
            url: '../../updateStudents',
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
                var page=$("#stu-tab").bootstrapTable("getOptions").pageNumber;
                $('#stu-tab').bootstrapTable('refreshOptions', {pageNumber: page});
                $('#StuModal').modal('hide');
            },
            error: function () {
                Swal.fire({
                    position: 'center',
                    type: 'error',
                    title: '修改失败，学号重复',
                    showConfirmButton: false,
                    timer: 1500
                });
            }
        });
    }
}
// 删除学生信息
function delStudents(row) {
    swal.fire({

        title: '确认删除？',

        text: '删除该学生信息后将无法恢复',

        type: 'warning',

        showCancelButton: true,

        confirmButtonText: '确认',

        cancelButtonText: '取消',

    }).then(function(isConfirm) {
        if(isConfirm.value){
            $.ajax({
                type: "POST",
                url: '../../delStudents',
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
                    var page=$("#stu-tab").bootstrapTable("getOptions").pageNumber;
                    $('#stu-tab').bootstrapTable('refreshOptions', {pageNumber: page});
                },
                error: function () {
                    Swal.fire({
                        position: 'center',
                        type: 'error',
                        title: '删除失败，该学生信息已与其他信息关联',
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
    var selected = $('#stu-tab').bootstrapTable('getSelections');
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
                        url: '../../delStudents',
                        contentType: "application/json",
                        data: JSON.stringify(item),
                        success: function (r) {
                            count=parseInt(count)+parseInt(1);
                            var page=$("#stu-tab").bootstrapTable("getOptions").pageNumber;
                            $('#stu-tab').bootstrapTable('refreshOptions', {pageNumber: page});
                        },
                        error: function () {
                            Swal.fire({
                                position: 'center',
                                type: 'error',
                                title: '删除失败，‘'+item.sName+'’的信息已与其他信息关联',
                                showConfirmButton: false,
                                timer: 1500
                            });
                        }
                    });
                });
                var page=$("#stu-tab").bootstrapTable("getOptions").pageNumber;
                $('#stu-tab').bootstrapTable('refreshOptions', {pageNumber: page});
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
            for (i=0;i<List.length;i++){if (List[i].deptId==0||List[i].deptId==''){

            } else {
                t.append(
                    "<option value='"+List[i].deptId+"'>"+List[i].deptName+"</option>"
                )
            }
            }
            t.selectpicker({width: '70%'});
            t.selectpicker('val',selected);
            t.selectpicker('refresh');
        }
    });
}
function getmajor(t,t2,selected) {
    var deptId=t2.val();
    //专业选择框加载
    $.ajax({
        type: "GET",
        url: '../../getMajor' ,
        contentType: "application/json",
        data: {
            deptId:deptId
        },
        success: function (r) {
            var List=eval(r);
            t.empty();
            t.append('<option value=""  >全部</option>')
            for (i=0;i<List.length;i++){
                t.append(
                    "<option value='"+List[i].majorCode+"'>"+List[i].majorName+"</option>"
                )
            }
            t.selectpicker({width: '70%'});
            t.selectpicker('val',selected);
            t.selectpicker('refresh');
        }
    });
}
function setclass(t,t2,t3,selected) {
    var grade=t3.val();
    getclass(t,t2,grade,selected);
}
function getclass(t,t2,grade,selected) {
    var major=t2.val();
    //专业选择框加载
    $.ajax({
        type: "GET",
        url: '../../getClasstoSel' ,
        contentType: "application/json",
        data: {
            majorCode:major,
            classGrade:grade
        },
        success: function (r) {
            var List=eval(r);
            t.empty();
            t.append('<option value=""  >全部</option>')
            for (i=0;i<List.length;i++){
                t.append(
                    "<option value='"+List[i].classCode+"'>"+List[i].className+"</option>"
                )
            }
            t.selectpicker({width: '70%'});
            t.selectpicker('val',selected);
            t.selectpicker('refresh');
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