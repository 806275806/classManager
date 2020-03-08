var course=sessionStorage.getItem("courseItem");
var nickname=sessionStorage.getItem("selectedNickName");
var obj = eval('(' + course + ')');
var year=obj.courseTerm.substring(0,9);
var term=obj.courseTerm.substring(9);
var fristState='';
// alert(JSON.stringify(obj));
function loadHistory(){
    $('#date_1').datetimepicker({
        format: 'yyyy-mm-dd', minView: "month",language : 'zh-CN',autoclose: true, clearBtn : true
    });
    $('#date_2').datetimepicker({
        format: 'yyyy-mm-dd',minView: "month",language : 'zh-CN',autoclose: true, clearBtn : true
    });
    $('#courseCode').html(obj.courseCode);
    $('#courseName').html(obj.courseName);
    $('#courseName_slide').text(obj.courseName);
    $('#year').html(year);
    $('#term').html(term);
    // alert(JSON.stringify(obj.classes));
    $('#classes').empty();
    for (var i=0;i<obj.classes.length;i++){
        $('#classes').append('<option  style="color: #0a0a0a" value="'+obj.classes[i].classNickname+'">'+obj.classes[i].classNickname+'</option>')
    }
    // alert(nickname);
    $('#classes').val(nickname);
    getStuNum();
    loadHistoryTable();
}
function selHistory() {
        if ($('#date_1').val()==null||$('#date_2').val()==null||$('#date_1').val()==''||$('#date_2').val()==''){
            Swal.fire({
                position: 'center',
                type: 'warning',
                title: '请选择一个完整的时间段',
                showConfirmButton: false,
                timer: 1500
            });
        }else {
            $('#attendanceHistory_tab').bootstrapTable('refreshOptions',{pageNumber:1});
        }
    }
function getStuNum() {
    $.ajax({
        type: "GET",
        url: '../../getStuNum' ,
        async:false,
        contentType: "application/json",
        data: {
            courseCode:obj.courseCode,
            classNickName:$('#classes').val()
        },
        success: function (r) {
            $('#num').html('&emsp;成员：'+r[0]+'人');
        }
    });
    sessionStorage.setItem("selectedNickName",$('#classes').val());
}
//进入课程详情页
function toCourseDetail(){
    $.ajax({
        type: "GET",
        url: '../../toHistory',
        contentType: "application/json",
        data: {

        },
        success: function (r) {
            sessionStorage.setItem("selectedNickName",$('#classes').val());
            window.location.href = '../../toHistory';
            // this.$router.push( '../../toCourseDetail');
        }
    });
}
// 表格加载
function loadHistoryTable(){
    $('#attendanceHistory_tab').bootstrapTable({
        url: '../../getHistory',
        striped:true,
        method: 'get',
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
                courseCode:obj.courseCode,
                classnickName:nickname,
                dateOne:$('#date_1').val(),
                dateTwo:$('#date_2').val(),
                offset :params.offset + 0,// SQL语句起始索引
                pageNumber : params.limit,// 每页显示数量
            };
            return temp;
        },
        columns: [{
            field: 'ischeck', checkbox: true
        },
            {
                field: 'attendanceId',
                title: '考勤id',
                visible: false
            },
            {
                field: 'courseCode',
                title: '课程编号',
                visible: false
            },
            {
                field: 'attendanceTime',
                title: '考勤日期',
            },
            {
                field: 'attendanceNosign',
                title: '未签到人数',
                visible: false
            },
            {
                field: 'attendanceSignin',
                title: '签到人数'
            },
            {
                field: 'attendanceLate',
                title: '迟到人数'
            },{
                field: 'attendanceLeave',
                title: '请假人数'
            },{
                field: 'attendanceAbsent',
                title: '旷课人数'
            },{
                field: 'attendanceType',
                title: '考勤类型'
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
        var datatodeal = JSON.stringify(row).replace(/\"/g, "'");
        var result = "";
        result += "<a href='javascript:;' class='btn btn-xs' onclick=\"updateModal("+datatodeal+")\" title='编辑'>修改&nbsp;<span class='glyphicon glyphicon-pencil'></span></a>";
        return result;
    }
}
function updateModal(datatodeal){
    $('#History_table').bootstrapTable({
        url: '../../getHistoryStu',
        striped:true,
        method: 'get',
        pageNumber : 1, //初始化加载第一页
        sidePagination : 'server',//server:服务器端分页|client：前端分页
        pagination: true,//是否分页
        sortable: true, // 是否启用排序
        showRefresh : true,//刷新按钮
        toolbar:"#search_bar",
        pageSize:10,
        pageList : [ 5, 10, 20, 30 ],//可选择单页记录数
        singleSelect:false,//是否单选，false表示多选;true标识只能单选
        clickToSelect: true,//启用点击某行就选中某行
        queryParams : function(params) {//上传服务器的参数
            var temp = {
                courseCode:obj.courseCode,
                attendanceId:datatodeal.attendanceId,
                offset :params.offset + 0,// SQL语句起始索引
                pageNumber : params.limit// 每页显示数量
            };
            return temp;
        },
        columns: [
            {
                field: 'attendanceId',
                title: '考勤id',
                visible: false
            },
            {
                field: 'courseCode',
                title: '课程编号',
                visible: false
            },
            // {
            //     field: 'courseName',
            //     title: '课程名称'
            // },
            {
                field: 'sNo',
                title: '学号',
            },
            {
                field: 'sName',
                title: '姓名'
            },
            {
                field: 'deptName',
                title: '院系',
                visible:false
            },
            {
                field: 'className',
                title: '班级'
            },
            {
                field: 'attendanceResult',
                title: '考勤状态'
            },{
                field: 'attendanceType',
                title: '考勤类型'
            },{
                field: 'attendanceDate',
                title: '考勤日期'
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
        var dataforStu = JSON.stringify(row).replace(/\"/g, "'");
        var result = "";
        result += "<a href='javascript:;' class='btn btn-xs' onclick=\"stuModalOpen("+dataforStu+")\" title='编辑'>修改&nbsp;<span class='glyphicon glyphicon-pencil'></span></a>";
        return result;
    }
    $('#updateModal').modal();
}
function stuModalOpen(dataforStu) {
    $('#sName').html(dataforStu.sName);
    $('#sNo').html(dataforStu.sNo);
    $('#attendanceId').html(dataforStu.attendanceId);
    $('#dept').html(dataforStu.deptName);
    $('#class').html(dataforStu.className);
    $('#state_sel').selectpicker('val',dataforStu.attendanceResult);
    fristState=dataforStu.attendanceResult;//获取学生原始的考勤状态
    $('#stuModal').modal();
}
//更改学生考勤状态
function setStuState() {
    var newState=$('#state_sel').selectpicker('val');
    var editList={
        sNo:$('#sNo').html(),
        attendanceId:$('#attendanceId').html(),
        attendanceResult:$('#state_sel').selectpicker('val'),
        stateOne:fristState,
        stateTwo:$('#state_sel').selectpicker('val')
    }
    $.ajax({
        type: "POST",
        url: '../../updateHistory' ,
        async:false,
        contentType: "application/json",
        data: JSON.stringify(editList),
        success: function (r) {
            Swal.fire({
                position: 'center',
                type: 'success',
                title: '已提交',
                showConfirmButton: false,
                timer: 1500
            });
            $('#History_table').bootstrapTable('refreshOptions',{pageNumber:1});
        }
    });
}