var students={};
var indexJS;
var getResultId='';

// alert(getResultId);
function getStuInfo(stuList,stuItem,index,result) {
     students=stuList;
    getResultId=result;
     indexJS=index.index;
     $('#'+result+'index').html(index.index);
     $('#'+result+'name').html(stuItem.sName);
     $('#'+result+'sNo').html('学号：'+stuItem.sNo);
     $('#'+result+'dept').html('院系：'+stuItem.deptName);
     // $('#major').html(''+stuItem.majorName);
     $('#'+result+'class').html('班级：'+stuItem.className);
     $('#'+result+'state').selectpicker('val',stuItem.attendanceResult);
    audioPlay(stuItem.sName);
     $('#'+result).modal();
}
//手动考勤时自动弹出
function autoGetStuInfo(stuList,result) {
    students=stuList;
    getResultId=result;
    indexJS=0;
    // $('#'+result+'index').html(index.index);
    $('#'+result+'name').html(stuList[0].sName);
    $('#'+result+'sNo').html('学号：'+stuList[0].sNo);
    $('#'+result+'dept').html('院系：'+stuList[0].deptName);
    // $('#major').html(''+stuItem.majorName);
    $('#'+result+'class').html('班级：'+stuList[0].className);
    $('#'+result+'state').selectpicker('val',stuList[0].attendanceResult);
    audioPlay(stuList[0].sName);
    $('#'+result).modal();
    sessionStorage.removeItem("IsManual");
}
function getNextStu() {
    indexJS=parseInt(indexJS)+1;//下一个学生信息的数组下标
    var stuIndex=parseInt(indexJS)-1;//当前数组下标
    students[stuIndex].attendanceResult=$('#'+getResultId+'state').selectpicker('val');
    var stu=students[stuIndex];
    // alert(JSON.stringify(students[indexJS]));
    if (students[indexJS]!=null){
        var Stuitem=students[indexJS];
        $('#'+getResultId+'index').html();
        $('#'+getResultId+'name').html(Stuitem.sName);
        $('#'+getResultId+'sNo').html('学号：'+Stuitem.sNo);
        $('#'+getResultId+'dept').html('院系：'+Stuitem.deptName);
        // $('#major').html(''+stuItem.majorName);
        $('#'+getResultId+'class').html('班级：'+Stuitem.className);
        $('#'+getResultId+'state').selectpicker('val',Stuitem.attendanceResult);
        audioPlay(Stuitem.sName);
        $.ajax({
            type: "POST",
            url: '../../signByteacher',
            contentType: "application/json",
            data: JSON.stringify(stu),
            success: function (r) {
                // alert("111");
                // $('#'+Stuitem.sNo).selectpicker('val',Stuitem.attendanceResult)
            }
        });
    }else {
        $.ajax({
            type: "POST",
            url: '../../signByteacher',
            contentType: "application/json",
            data: JSON.stringify(stu),
            success: function (r) {
                // alert("111");
                // $('#'+Stuitem.sNo).selectpicker('val',Stuitem.attendanceResult)
            }
        });
        Swal.fire({
            position: 'center',
            type: 'warning',
            title: '已经是最后一个了',
            showConfirmButton: false,
            timer: 1500
        });
    }
}
function getUpStu() {
    indexJS=parseInt(indexJS)-1;//上一个的数组下标
    var stuIndex=parseInt(indexJS)+1;//当前数组下标
    students[stuIndex].attendanceResult=$('#state').selectpicker('val');
    var stu=students[stuIndex];
    // alert(JSON.stringify(students[indexJS]));
    if (students[indexJS]!=null){
        var Stuitem=students[indexJS];
        $('#'+getResultId+'index').html();
        $('#'+getResultId+'name').html(Stuitem.sName);
        $('#'+getResultId+'sNo').html('学号：'+Stuitem.sNo);
        $('#'+getResultId+'dept').html('院系：'+Stuitem.deptName);
        // $('#major').html(''+stuItem.majorName);
        $('#'+getResultId+'class').html('班级：'+Stuitem.className);
        $('#'+getResultId+'state').selectpicker('val',Stuitem.attendanceResult);
        audioPlay(Stuitem.sName);
    }else {
        Swal.fire({
            position: 'center',
            type: 'warning',
            title: '已经是第一个了',
            showConfirmButton: false,
            timer: 1500
        });
    }
}
function audioPlay(text){
    var zhText = text;
    zhText = encodeURI(zhText);
    var audio = "<audio autoplay=\"autoplay\">" + "<source src=\"http://fanyi.baidu.com/gettts?lan=zh&spd=3&source=web&text=" + zhText + "\" type=\"audio/mpeg\">" + "<embed height=\"0\" width=\"0\" src=\"http://tts.baidu.com/text2audio?text=" + zhText + "\">" + "</audio>";
    $('body').append(audio);
}
function hideModal(){
        // alert('hide');
        $('#signOutModal').modal('hide');
        // 模态框关闭监听
        $('#signOutModal').on('hidden.bs.modal', function () {
            // 执行一些动作...
            window.location.reload();
        });
}
