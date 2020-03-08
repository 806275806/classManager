var deptCode='';//树形组件对应的id,deptcode用于标识院系的id
var deptIdForAll='';//deptId用于标识专业所属院系的Id
var deptName='';
var majorCode='';
var majorName='';
//以下变量记录班级模态框的数据，用于为班级代码动态赋值
var Grade='';
var Major='';
var name='';
var ClassNum='';
var ClassCode='';
//搜索变量
var findbyDeptId;//院系id
$('#search_btn').click(function () {
    $('#class-tab').bootstrapTable('refreshOptions',{pageNumber:1});
});
loadtree();
getYear($('#classGrade'));
loadDept();
loadMajor_select();
//加载查询工具栏的下拉选择框内容
function loadDept() {
    //院系选择框加载
    $.ajax({
        type: "GET",
        url: '../../justGetDept' ,
        contentType: "application/json",
        data: {},
        success: function (r) {
            var List=eval(r);
            $('#deptSel').empty();
            $('#deptSel').append('<option value=""  >全部</option>');
            for (i=0;i<List.length;i++){
                if (List[i].deptId==0||List[i].deptId==''){

                } else {
                    $('#deptSel').append(
                        "<option value='"+List[i].deptId+"'>"+List[i].deptName+"</option>"
                    )
                }
            }
            $('#deptSel').selectpicker('refresh');
        }
    });
}
function loadMajor_select() {
    findbyDeptId=$('#deptSel').val();
    //专业选择框加载
    $.ajax({
        type: "GET",
        url: '../../getMajor' ,
        contentType: "application/json",
        data: {
            deptId:findbyDeptId
        },
        success: function (r) {
            var List=eval(r);
                $('#majorSel').empty();
                $('#majorSel').append('<option value=""  >全部</option>')
                for (i=0;i<List.length;i++){
                    $('#majorSel').append(
                        "<option value='"+List[i].majorCode+"'>"+List[i].majorName+"</option>"
                    )
                }
                $('#majorSel').selectpicker('refresh');
            }
    });
}
//加载院系部门树型组件、获取班级信息
function loadtree(){
    var arr='';
    var fortree=[];
    var tree=[];
    $.ajax({
        type: "GET",
        url: '../../getdept' ,
        contentType: "application/json",
        data: {},
        success: function (r) {
            arr=JSON.stringify(r);
            fortree=JSON.parse(arr);
            for (var i=0;i<fortree.length;i++){
                var majorarr=JSON.stringify(fortree[i].majors);
                var marjors=JSON.parse(majorarr);
                var majorsList=[];
                for (var j=0;j<marjors.length;j++){
                    majorsList.push({"text":"<a  id='"+marjors[j].majorCode+"'>"+marjors[j].majorName+"</a>"
                    +"<div style='float: right'><a  onclick=\"DealMajor(2,this)\" style='color: #3e8f3e' class='glyphicon glyphicon-pencil' " +"updateDeptId='"+fortree[i].deptId+"'"+
                        " updatecode='"+marjors[j].majorCode+"' updateMajorName="+marjors[j].majorName+"></a>&nbsp;"
                    + "<a style='color: red' onclick=\"deleteMajor(this)\" class='glyphicon glyphicon-trash' delcode='"+marjors[j].majorCode+"'></a></div>"
                    });
                    var isNull=marjors[j].majorName;
                }
                if(fortree[i].deptId==0||fortree[i].deptId==''){}else {
                if (isNull==null){
                    tree.push({"text":"<a  id='"+fortree[i].deptId+"'>"+fortree[i].deptName+"</a>"
                        +"<div style='float: right'><a  onclick=\"DealDept(2,this)\" style='color: #3e8f3e' class='glyphicon glyphicon-pencil' updatecode='"+fortree[i].deptId+"' updateDeptName='"+fortree[i].deptName+"' updateDeptId='"+fortree[i].deptId+"'></a>&nbsp;" +
                        "<a onclick='delDept(this)' style='color: red' class='glyphicon glyphicon-trash' delcode='"+fortree[i].deptId+"'></a></div>"
                    });
                } else {
                    tree.push({"text":"<a id='"+fortree[i].deptId+"'>"+fortree[i].deptName+"</a>"
                        +"<div style='float: right'><a onclick=\"DealDept(2,this)\" style='color: #3e8f3e' class='glyphicon glyphicon-pencil' updatecode='"+fortree[i].deptId+"' updateDeptName='"+fortree[i].deptName+"' updateDeptId='"+fortree[i].deptId+"'></a>&nbsp;"+
                        "<a onclick='delDept(this)' style='color: red' class='glyphicon glyphicon-trash' delcode='"+fortree[i].deptId+"'></a></div>"
                        ,"nodes":majorsList});
                }
                }
            }
            $('#class-tab').bootstrapTable({
                url: '../../getClass',
                striped:true,
                pageNumber : 1, //初始化加载第一页
                sidePagination : 'server',//server:服务器端分页|client：前端分页
                pagination: true,//是否分页
                sortable: true, // 是否启用排序
                showRefresh : true,//刷新按钮
                toolbar:"#class-tab-tool",
                pageSize:10,
                pageList : [ 5, 10, 20, 30 ],//可选择单页记录数
                singleSelect:false,//是否单选，false表示多选;true标识只能单选
                clickToSelect: true,//启用点击某行就选中某行
                queryParams : function(params) {//上传服务器的参数
                    var temp = {
                        offset :params.offset + 0,// SQL语句起始索引
                        pageNumber : params.limit, // 每页显示数量
                        deptId:$('#deptSel').val(),
                        majorCode:$('#majorSel').val(),
                        className:$('#className').val(),
                        classGrade:$('#classGrade').val()
                    };
                    return temp;
                },
                columns: [{
                    field: 'ischeck', checkbox: true
                },
                {
                    field: 'classCode',
                    title: '班级代码'
                },
                {
                    field: 'deptId',
                    title: '院系名称',
                    visible: false
                },
                {
                    field: 'deptName',
                    title: '所属院系'
                },
                {
                    field: 'majorName',
                    title: '所属专业'
                },
                {
                    field: 'majorCode',
                    title: '专业代码'
                },
                {
                    field: 'classGrade',
                    title: '年级'
                },
                {
                    field: 'className',
                    title: '班级名称'
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
                result += "<a href='javascript:;' class='btn btn-xs' onclick=\"DealClass('2'," + data + ")\" title='编辑'><span class='glyphicon glyphicon-pencil' style='color: #3E8F3E'></span></a>";
                result += "<a href='javascript:;' class='btn btn-xs' onclick=\"delClass(" + data + ")\" title='删除'><span class='glyphicon glyphicon-trash' style='color: red'></span></a>";
                return result;

            }
            $('#tree').treeview({
                data:tree,
                color: "#428bca",
                selectedColor:"#428bca",
                selectedBackColor:"#DDDDDD",
                showBorder:false
            });
        }
    });
}
function DealDept(dealType,t) {//1为添加，2为编辑
    if (dealType==1){
        $('#addDept_h4').css('display','block');
        $('#updateDept_h4').css('display','none');
        $('#addDept_footer').css('display','block');
        $('#updateDept_footer').css('display','none');
        $('#deptName').val("");
    } else {
        // 获取点击院系或专业的信息
        deptCode=$(t).attr('updatecode');
        deptIdForAll=$(t).attr('updateDeptId');
        deptName=$(t).attr('updateDeptName');
        majorName=$(t).attr('updateMajorName');
        majorCode=$(t).attr('updatecode');
        $('#updateDept_h4').css('display','block');
        $('#addDept_h4').css('display','none');
        $('#addDept_footer').css('display','none');
        $('#updateDept_footer').css('display','block');
        $('#deptName').val(deptName);
    }
    $('#DeptModal').modal();
}
// 添加、编辑院系信息
function AddOrUpdateDept(dealType) {
    if (dealType==1) {
        var deptName1 = $('#deptName').val();
        var deptId = '';
        var List =
            {
                deptId: deptId,
                deptName: deptName1
            };
        if (deptName1 != null && deptName1 != '') {
            $.ajax({
                type: "POST",
                url: '../../addDept',
                contentType: "application/json",
                data: JSON.stringify(List),
                success: function (r) {
                    Swal.fire({
                        position: 'center',
                        type: 'success',
                        title: '添加成功',
                        showConfirmButton: false,
                        timer: 1500
                    });
                    loadtree();
                    $('#DeptModal').modal('hide');
                },
                error: function () {
                    // alert("添加院系失败，院系名已存在");
                    Swal.fire({
                        position: 'center',
                        type: 'error',
                        title: '添加失败，院系名已存在',
                        showConfirmButton: false,
                        timer: 1500
                    });
                }
            });
        } else {
            Swal.fire({
                position: 'center',
                type: 'error',
                title: '添加失败，院系名不能为空',
                showConfirmButton: false,
                timer: 1500
            });
        }
    }else {
        var deptName1 = $('#deptName').val();
        var deptId1 = deptIdForAll;
        var List =
            {
                deptId: deptId1,
                deptName: deptName1
            };
        if (deptName1 != null && deptName1 != '') {
            $.ajax({
                type: "POST",
                url: '../../updateDept',
                contentType: "application/json",
                data: JSON.stringify(List),
                success: function (r) {
                    Swal.fire({
                        position: 'center',
                        type: 'success',
                        title: '修改成功',
                        showConfirmButton: false,
                        timer: 1500
                    });
                    loadtree();
                    $('#DeptModal').modal('hide');
                },
                error: function () {
                    // alert("添加院系失败，院系名已存在");
                    Swal.fire({
                        position: 'center',
                        type: 'error',
                        title: '修改失败，院系名已存在',
                        showConfirmButton: false,
                        timer: 1500
                    });
                }
            });
        } else {
            // toastr.error("院系名不能为空");
            Swal.fire({
                position: 'center',
                type: 'success',
                title: '修改成功',
                showConfirmButton: false,
                timer: 1500
            });
        }
    }
}
//删除院系信息
function delDept(t) {
    var delId=$(t).attr('delcode');
    List={
        deptId:delId
    }
    swal.fire({

        title: '确认删除？',

        text: '删除该院系信息后将无法恢复',

        type: 'warning',

        showCancelButton: true,

        confirmButtonText: '确认',

        cancelButtonText: '取消',

    }).then(function(isConfirm) {
        if(isConfirm.value){
            $.ajax({
                type: "POST",
                url: '../../deleteDept',
                contentType: "application/json",
                data: JSON.stringify(List),
                success: function (r) {
                    Swal.fire({
                        position: 'center',
                        type: 'success',
                        title: '删除成功',
                        showConfirmButton: false,
                        timer: 1500
                    });
                    loadtree();
                },
                error: function () {
                    Swal.fire({
                        position: 'center',
                        type: 'error',
                        title: '删除失败，该院系已与其他信息关联',
                        showConfirmButton: false,
                        timer: 1500
                    });
                }
            });
        }
    });
}

//弹出专业模态框，并获取院系内容到下拉框
function DealMajor(dealType,t) {
    if (dealType==1){
        $('#AddMajor_h4').css('display','block');
        $('#UpdateMajor_h4').css('display','none');
        $('#AddMajor_footer').css('display','block');
        $('#UpdateMajor_footer').css('display','none');
        $('#MajorName').val("");
        $('#MajorCode').val("");
        $('#Dept_sel').val("");
        getDeptToSel();
    } else {
        deptIdForAll=$(t).attr('updateDeptId');
        majorName=$(t).attr('updateMajorName');
        majorCode=$(t).attr('updatecode');
        $('#AddMajor_h4').css('display','none');
        $('#UpdateMajor_h4').css('display','block');
        $('#AddMajor_footer').css('display','none');
        $('#UpdateMajor_footer').css('display','block');
        $('#MajorName').val(majorName);
        $('#MajorCode').val(majorCode);
        getDeptToSel(deptIdForAll);
    }
    $('#MajorModal').modal();
}
//弹出班级模态框，并获取院系内容到下拉框
function DealClass(dealType,row) {
    $('#ClassGrade-Modal').datetimepicker({
        format: 'yyyy', startView : "decade", minView: 'decade',language : 'zh-CN',autoclose: true, clearBtn : true
    });
    $('#years-1').datetimepicker({
        format: 'yyyy', startView : "decade", minView: 'decade',language : 'zh-CN',autoclose: true, clearBtn : true
    });
    $('#years-2').datetimepicker({
        format: 'yyyy', startView : "decade", minView: 'decade',language : 'zh-CN',autoclose: true, clearBtn : true
    });
    if (dealType==1){
        getDept();
        $('#addClass_h4').css('display','block');
        $('#updateClass_h4').css('display','none');
        $('#addClass_footer').css('display','block');
        $('#updateClass_footer').css('display','none');
        $('#showclassName').css('display','none');
        $('#ClassCode').val("");
        $('#ClassGrade-Modal').val("");
        $('#deptSel_Modal').val("");
        $('#majorSel_Modal').val("");
        $('#ClassName_modal').text("");
        $('#ClassNum').val("");
        Grade='';
        Major='';
        ClassNum='';
        name='';
        ClassCode='';
        $('#classNum').val("");
    } else {
        var index=(row.className).indexOf('班' );
        var num=(row.className).charAt(index-1);
        // var classname=(row.className).substring(0,index-1);
        Grade=(row.classCode).substring(0,4);
        Major=(row.classCode).substring(4,8);
        ClassNum=(row.classCode).substring(8,11);
        $('#Id').val(row.classCode);
        $('#ClassCode').val(row.classCode);
        $('#ClassGrade-Modal').val(row.classGrade);
        $('#ClassNum').val(num);
        getDept(row.deptId,row.majorCode);
        name=row.majorName;
        $('#ClassName_modal').text(Grade.substr(2,2)+'级'+name);
        // getMajor($('#deptSel_Modal'),$('#majorSel_Modal'),row.majorCode);
        $('#addClass_h4').css('display','none');
        $('#updateClass_h4').css('display','block');
        $('#addClass_footer').css('display','none');
        $('#updateClass_footer').css('display','block');
        $('#showclassName').css('display','block');
    }
    $('#ClassModal').modal();
}
//获取院系的信息，并加入下拉框内
function getDeptToSel(deptIdForAll) {
    $.ajax({
        type: "GET",
        url: '../../justGetDept' ,
        contentType: "application/json",
        data: {},
        success: function (r) {
            var List=eval(r);
            $('#Dept_sel').empty();
            $('#Dept_sel').append('<option value=""  disabled selected hidden>请选择所属院系</option>')
            for (i=0;i<List.length;i++){
                $('#Dept_sel').append(
                    "<option value='"+List[i].deptId+"'>"+List[i].deptName+"</option>"
                )
            }
            $('#Dept_sel').val(deptIdForAll);
        }
    });
}
//添加专业
function addMajor() {
    var obj=serializeObject($('#Major-form'));
    if(obj.deptId==null||obj.deptId==''){
        Swal.fire({
            position: 'center',
            type: 'error',
            title: '请选择院系',
            showConfirmButton: false,
            timer: 1500
        });
    }else if(obj.majorCode==null||obj.majorCode==''){
        Swal.fire({
            position: 'center',
            type: 'error',
            title: '请输入专业代码',
            showConfirmButton: false,
            timer: 1500
        });
    }else if(obj.majorName==null||obj.majorName==''){
        Swal.fire({
            position: 'center',
            type: 'error',
            title: '请输入专业名称',
            showConfirmButton: false,
            timer: 1500
        });
    }else {
        $.ajax({
            type: "POST",
            url: '../../addMajor',
            contentType: "application/json",
            data: JSON.stringify(obj),
            success: function (r) {
                Swal.fire({
                    position: 'center',
                    type: 'success',
                    title: '添加成功',
                    showConfirmButton: false,
                    timer: 1500
                });
                loadtree();
                $('#MajorModal').modal('hide');
            },
            error: function () {
                Swal.fire({
                    position: 'center',
                    type: 'error',
                    title: '添加失败,专业代码已存在',
                    showConfirmButton: false,
                    timer: 1500
                });
            }
        });
    }
}
//修改专业
function updateMajor() {
    var obj=serializeObject($('#Major-form'));
    if(obj.deptId==null||obj.deptId==''){
        Swal.fire({
            position: 'center',
            type: 'error',
            title: '请选择院系',
            showConfirmButton: false,
            timer: 1500
        });
    }else if(obj.majorCode==null||obj.majorCode==''){
        Swal.fire({
            position: 'center',
            type: 'error',
            title: '请输入专业代码',
            showConfirmButton: false,
            timer: 1500
        });
    }else if(obj.majorName==null||obj.majorName==''){
        Swal.fire({
            position: 'center',
            type: 'error',
            title: '请输入专业名称',
            showConfirmButton: false,
            timer: 1500
        });
    }else {
        $.ajax({
            type: "POST",
            url: '../../updateMajor' ,
            contentType: "application/json",
            data: JSON.stringify(obj),
            success: function (r) {
                Swal.fire({
                    position: 'center',
                    type: 'success',
                    title: '修改成功',
                    showConfirmButton: false,
                    timer: 1500
                });
                loadtree();
                $('#MajorModal').modal('hide');
            },
            error:function () {
                Swal.fire({
                    position: 'center',
                    type: 'error',
                    title: '修改失败',
                    showConfirmButton: false,
                    timer: 1500
                });
            }
        });
    }
}
//删除专业
function deleteMajor(t) {
    var marjorCode=$(t).attr('delcode');
    List={
        majorCode:marjorCode
    }
    swal.fire({

        title: '确认删除？',

        text: '删除该专业信息后将无法恢复',

        type: 'warning',

        showCancelButton: true,

        confirmButtonText: '确认',

        cancelButtonText: '取消',

    }).then(function(isConfirm) {
        if(isConfirm.value){
            $.ajax({
                type: "POST",
                url: '../../deleteMajor',
                contentType: "application/json",
                data: JSON.stringify(List),
                success: function (r) {
                    Swal.fire({
                        position: 'center',
                        type: 'success',
                        title: '删除成功',
                        showConfirmButton: false,
                        timer: 1500
                    });
                    loadtree();
                },
                error: function () {
                    Swal.fire({
                        position: 'center',
                        type: 'error',
                        title: '删除失败，该专业已与其他信息关联',
                        showConfirmButton: false,
                        timer: 1500
                    });
                }
            });
        }
    });
}
// 增加班级
function addClass() {
    var ClassName=$('#ClassName_modal').text()+$('#ClassNum').val()+'班'
    $('#classNameform').val(ClassName);
    var obj=serializeObject($('#Class_form'));
    if(obj.deptId==null||obj.deptId==''){
        Swal.fire({
            position: 'center',
            type: 'error',
            title: '请选择院系',
            showConfirmButton: false,
            timer: 1500
        });
    }else if(obj.majorCode==null||obj.majorCode==''){
        Swal.fire({
            position: 'center',
            type: 'error',
            title: '请选择专业',
            showConfirmButton: false,
            timer: 1500
        });
    }else if (obj.classGrade==null||obj.classGrade==''){
        Swal.fire({
            position: 'center',
            type: 'error',
            title: '请选择年级',
            showConfirmButton: false,
            timer: 1500
        });
    }else {
        $.ajax({
            type: "POST",
            url: '../../addClass',
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
                var page=$("#class-tab").bootstrapTable("getOptions").pageNumber;
                $('#class-tab').bootstrapTable('refreshOptions', {pageNumber: page});
                $('#ClassModal').modal('hide');
            },
            error: function () {
                Swal.fire({
                    position: 'center',
                    type: 'error',
                    title: '添加失败,班级代码、班级名称不能重复',
                    showConfirmButton: false,
                    timer: 1500
                });
            }
        });
    }
}
//编辑班级
function updateClass() {
    var ClassName=$('#ClassName_modal').text()+$('#ClassNum').val()+'班'
    $('#classNameform').val(ClassName);
    var obj=serializeObject($('#Class_form'));
    if(obj.deptId==null||obj.deptId==''){
        Swal.fire({
            position: 'center',
            type: 'error',
            title: '请选择院系',
            showConfirmButton: false,
            timer: 1500
        });
    }else if(obj.majorCode==null||obj.majorCode==''){
        Swal.fire({
            position: 'center',
            type: 'error',
            title: '请选择专业',
            showConfirmButton: false,
            timer: 1500
        });
    }else if (obj.classGrade==null||obj.classGrade==''){
        Swal.fire({
            position: 'center',
            type: 'error',
            title: '请选择年级',
            showConfirmButton: false,
            timer: 1500
        });
    }else {
        $.ajax({
            type: "POST",
            url: '../../updateClass',
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
                var page=$("#class-tab").bootstrapTable("getOptions").pageNumber;
                $('#class-tab').bootstrapTable('refreshOptions', {pageNumber: page});
                $('#ClassModal').modal('hide');
            },
            error: function () {
                Swal.fire({
                    position: 'center',
                    type: 'error',
                    title: '修改失败,班级代码、班级名称不能重复',
                    showConfirmButton: false,
                    timer: 1500
                });
            }
        });
    }
}
//删除班级
function delClass(data) {
    swal.fire({

        title: '确认删除？',

        text: '删除该班级信息后将无法恢复',

        type: 'warning',

        showCancelButton: true,

        confirmButtonText: '确认',

        cancelButtonText: '取消',

    }).then(function(isConfirm) {
        List={
            classCode:data.classCode
        }
        if(isConfirm.value){
            $.ajax({
                type: "POST",
                url: '../../deleteClass',
                contentType: "application/json",
                data: JSON.stringify(List),
                success: function (r) {
                    Swal.fire({
                        position: 'center',
                        type: 'success',
                        title: '删除成功',
                        showConfirmButton: false,
                        timer: 1500
                    });
                    var page=$("#class-tab").bootstrapTable("getOptions").pageNumber;
                    $('#class-tab').bootstrapTable('refreshOptions', {pageNumber: page});
                },
                error: function () {
                    Swal.fire({
                        position: 'center',
                        type: 'error',
                        title: '删除失败，该专业已与其他信息关联',
                        showConfirmButton: false,
                        timer: 1500
                    });
                }
            });
        }
    });
}
//班级代码动态赋值
function setClassCode(param) {
    if (param=='Grade'){
        Grade=$('#ClassGrade-Modal').val();
        $('#ClassName_modal').text(Grade.substr(2,2)+'级'+name);
    } if (param=='Major'){
        Major=$('#majorSel_Modal').val();
        name=$('#majorSel_Modal').find("option:selected").text();
        $('#ClassName_modal').text(Grade.substr(2,2)+'级'+name);
    }if (param=='ClassNum'){
        var num=$('#ClassNum').val();
        if (num.length==1&&ClassNum.length!=2){
            ClassNum='0'+num;
        }if (num.length==1&&ClassNum.length==2){
            ClassNum='0'+num;
        }if (num.length==2) {
            ClassNum=num;
        }
    }
    ClassCode=Grade+Major+ClassNum;
    $('#ClassCode').val(ClassCode);
    $('#showclassName').css('display','block');
}
//获取表格多选数据数组，并删除
function table_select(){
    var selected = $('#class-tab').bootstrapTable('getSelections');
    swal.fire({

        title: '是否删除选中项？',

        text: '删除选中信息后将无法恢复',

        type: 'warning',

        showCancelButton: true,

        confirmButtonText: '确认',

        cancelButtonText: '取消',

    }).then(function(isConfirm) {
        if(isConfirm.value){
            var all='';
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
                        url: '../../deleteClass',
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
                })
                    var page=$("#class-tab").bootstrapTable("getOptions").pageNumber;
                    $('#class-tab').bootstrapTable('refreshOptions', {pageNumber: page});
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