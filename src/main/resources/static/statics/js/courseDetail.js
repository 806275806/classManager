var course=sessionStorage.getItem("courseItem");
var obj = eval('(' + course + ')');
var nickname=sessionStorage.getItem("selectedNickName");
var CourseSession=obj.courseCode+'-'+nickname;//每个课程考勤情况的session的唯一标识
var CourseSignOutSession=obj.courseCode+'-'+nickname+"-SignOut";//每个课程签退情况的session的唯一标识
var attendanceId=sessionStorage.getItem("attendanceId");
var year=obj.courseTerm.substring(0,9);
var term=obj.courseTerm.substring(9);
var userkey=sessionStorage.getItem("userCode")+"-courseStu";
// var attendanceSession=sessionStorage.getItem(CourseSession);//判断是否正在进行考勤
// var signOutSession=sessionStorage.getItem(CourseSignOutSession);//判断是否正在进行签退
var count=0;
var countOfSignOut=0;
var attendanceHistoryId='';
var numofAll=0;//全部人数
var numofNoSign=0;//未签到人数
var numoflate=0;//迟到人数
var numofSign=0;//签到人数
var numofLeave=0;//请假人数
var numofabsent=0;//旷课人数
// alert(userkey);
function loadClassInfo() {
    $('#sNo_input').val(sessionStorage.getItem("Isselected"));
    $('#courseCode').html(obj.courseCode);
    $('#courseName').html(obj.courseName);
    // $('#courseName').html(obj.courseName);
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
    // 判断是否正在考勤，（正在考勤则无需判断是否有未提交的考勤信息）
    // alert('attendanceSession:'+attendanceSession+'signOutSession'+signOutSession);
    if (sessionStorage.getItem(CourseSession)==null&&sessionStorage.getItem(CourseSignOutSession)==null) {
        IssubmitAttendance();
    }else {
        var List={
            courseCode:obj.courseCode,
            classnickName:$('#classes').val(),
            attendanceState:'待提交',
            // attendanceType:'签到',
        };
        $.ajax({
            type: "POST",
            url: '../../isSumbit' ,
            async:false,
            contentType: "application/json",
            data: JSON.stringify(List),
            success: function (r) {
                if (r.length!=0){
                    attendanceHistoryId=r[0].attendanceId;
                }}});
    }
    // alert(attendanceHistoryId);
    getStuNum();
    getStuByCourseCode();
    getAllNum();
    loadTab(sessionStorage.getItem("Isselected"));
    var timer=parseInt(60);
    setInterval(function () {
        timer=parseInt(timer)-1;
        $('#timer').html(timer);
    },1000);
    // setInterval(function () {
    //     window.location.reload();
    // },60*1000)
}
function loadTab(select){
    if (!select){
        $("#tabContainer").tabs({
        data: [{
            id: 'all',
            text: '全部'+'('+numofAll+')',
            url: "../../getAttendanceStu?courseCode="+obj.courseCode+"&classNickname="+$('#classes').val()+"&attendanceId="+attendanceHistoryId+"&history="+"0"
        }, {
            id: 'nosignin',
            text: '未签到'+'('+numofNoSign+')',
            url: "../../getAttendanceStu?courseCode="+obj.courseCode+"&classNickname="+$('#classes').val()+"&attendanceId="+attendanceHistoryId+"&attendanceResult="+"未签到"+"&history="+"0"
        },{
            id: 'signin',
            text: '正常'+'('+numofSign+')',
            url: "../../getAttendanceStu?courseCode="+obj.courseCode+"&classNickname="+$('#classes').val()+"&attendanceId="+attendanceHistoryId+"&attendanceResult=正常"+"&history="+"0"
        },{
            id: 'late',
            text: '迟到'+'('+numoflate+')',
            url: "../../getAttendanceStu?courseCode="+obj.courseCode+"&classNickname="+$('#classes').val()+"&attendanceId="+attendanceHistoryId+"&attendanceResult=迟到"+"&history="+"0"
        },{
            id: 'leave',
            text: '请假'+'('+numofLeave+')',
            url: "../../getAttendanceStu?courseCode="+obj.courseCode+"&classNickname="+$('#classes').val()+"&attendanceId="+attendanceHistoryId+"&attendanceResult=假"+"&history="+"0"
        },{
            id: 'absent',
            text: '旷课'+'('+numofabsent+')',
            url: "../../getAttendanceStu?courseCode="+obj.courseCode+"&classNickname="+$('#classes').val()+"&attendanceId="+attendanceHistoryId+"&attendanceResult=旷课"+"&history="+"0"
        }],
        showIndex: 0,
        loadAll: false
    });
    }else {
        $("#tabContainer").tabs({
            data: [{
                id: 'all',
                text: '全部'+'('+numofAll+')',
                url: "../../getAttendanceStu?courseCode="+obj.courseCode+"&classNickname="+$('#classes').val()+"&attendanceId="+attendanceHistoryId+"&sNo="+sessionStorage.getItem("Isselected")+"&history="+"0"
            }, {
                id: 'nosignin',
                text: '未签到'+'('+numofNoSign+')',
                url: "../../getAttendanceStu?courseCode="+obj.courseCode+"&classNickname="+$('#classes').val()+"&attendanceId="+attendanceHistoryId+"&attendanceResult="+"未签到"+"&sNo="+sessionStorage.getItem("Isselected")+"&history="+"0"
            },{
                id: 'signin',
                text: '正常'+'('+numofSign+')',
                url: "../../getAttendanceStu?courseCode="+obj.courseCode+"&classNickname="+$('#classes').val()+"&attendanceId="+attendanceHistoryId+"&attendanceResult=正常"+"&sNo="+sessionStorage.getItem("Isselected")+"&history="+"0"
            },{
                id: 'late',
                text: '迟到'+'('+numoflate+')',
                url: "../../getAttendanceStu?courseCode="+obj.courseCode+"&classNickname="+$('#classes').val()+"&attendanceId="+attendanceHistoryId+"&attendanceResult=迟到"+"&sNo="+sessionStorage.getItem("Isselected")+"&history="+"0"
            },{
                id: 'leave',
                text: '请假'+'('+numofLeave+')',
                url: "../../getAttendanceStu?courseCode="+obj.courseCode+"&classNickname="+$('#classes').val()+"&attendanceId="+attendanceHistoryId+"&attendanceResult=假"+"&sNo="+sessionStorage.getItem("Isselected")+"&history="+"0"
            },{
                id: 'absent',
                text: '旷课'+'('+numofabsent+')',
                url: "../../getAttendanceStu?courseCode="+obj.courseCode+"&classNickname="+$('#classes').val()+"&attendanceId="+attendanceHistoryId+"&attendanceResult=旷课"+"&sNo="+sessionStorage.getItem("Isselected")+"&history="+"0"
            }],
            showIndex: 0,
            loadAll: false
        });
    }
}
//判断是否有未提交的考勤
function IssubmitAttendance() {
    var List={
        courseCode:obj.courseCode,
        classnickName:$('#classes').val(),
        attendanceState:'待提交',
        attendanceType:'',
    };
    $.ajax({
        type: "POST",
        url: '../../isSumbit' ,
        async:false,
        contentType: "application/json",
        data: JSON.stringify(List),
        success: function (r) {
            if (r.length!=0){
                attendanceHistoryId=r[0].attendanceId;

                // getAteendanceDetail(attendanceHistoryId);
                swal.fire({

                    title: '提交考勤？',

                    text: '您有一个考勤尚未提交，是否现在提交？',

                    type: 'warning',

                    showCancelButton: true,

                    confirmButtonText: '确认',

                    cancelButtonText: '取消考勤',

                }).then(function(isConfirm) {
                    var editList={
                        attendanceId:r[0].attendanceId,
                        attendanceState:'已结束',
                        attendanceSignin: numofSign,
                        attendanceLate: numoflate,
                        attendanceLeave: numofLeave,
                        attendanceAbsent: numofabsent,
                        attendanceNosign: numofNoSign
                    }
                    if(isConfirm.value){
                        $.ajax({
                            type: "POST",
                            url: '../../editAttendance' ,
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
                                window.location.reload();
                            }
                        });
                    }
                    else {
                        var delList={
                            attendanceId:r[0].attendanceId
                        };
                        $.ajax({
                            type: "POST",
                            url: '../../delAttendance' ,
                            async:false,
                            contentType: "application/json",
                            data: JSON.stringify(delList),
                            success: function (r) {
                                Swal.fire({
                                    position: 'center',
                                    type: 'success',
                                    title: '已取消',
                                    showConfirmButton: false,
                                    timer: 1500
                                });
                                window.location.reload();
                            }
                        });
                    }
                    // attendanceSession='';
                    // signOutSession='';
                    sessionStorage.removeItem(CourseSession)
                    sessionStorage.removeItem(CourseSignOutSession);
                });
            }
        }
    });
}
//判断是否有正在进行且未提交的签到，用于签退前提交
function IsHasAttendance() {
    var List={
        courseCode:obj.courseCode,
        classnickName:$('#classes').val(),
        attendanceState:'待提交',
        attendanceType:'签到'
    };
    $.ajax({
        type: "POST",
        url: '../../isSumbit' ,
        async:false,
        contentType: "application/json",
        data: JSON.stringify(List),
        success: function (r) {
            if (r.length!=0){
                attendanceHistoryId=r[0].attendanceId;

                // getAteendanceDetail(attendanceHistoryId);
                swal.fire({

                    title: '提交考勤？',

                    text: '您有一个签到记录尚未提交，是否现在提交？',

                    type: 'warning',

                    showCancelButton: true,

                    confirmButtonText: '确认',

                    cancelButtonText: '取消考勤',

                }).then(function(isConfirm) {
                        if (isConfirm.value) {
                            var editList = {
                                attendanceId: r[0].attendanceId,
                                attendanceState: '已结束',
                                attendanceSignin: numofSign,
                                attendanceLate: numoflate,
                                attendanceLeave: numofLeave,
                                attendanceAbsent: numofabsent,
                                attendanceNosign:numofNoSign
                            }
                            $.ajax({
                                type: "POST",
                                url: '../../editAttendance',
                                async: false,
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
                                    window.location.reload();
                                }
                            });
                        }
                        else {
                            var delList = {
                                attendanceId: r[0].attendanceId
                            };
                            $.ajax({
                                type: "POST",
                                url: '../../delAttendance',
                                async: false,
                                contentType: "application/json",
                                data: JSON.stringify(delList),
                                success: function (r) {
                                    Swal.fire({
                                        position: 'center',
                                        type: 'success',
                                        title: '已取消',
                                        showConfirmButton: false,
                                        timer: 1500
                                    });
                                    window.location.reload();
                                }
                            });
                        }
                });
                // attendanceSession='';
                sessionStorage.removeItem(CourseSession);
            }
            else {

            }
        }
    });
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
    getStuByCourseCode();
}
function getStuByCourseCode() {
    var stuNumIncourse=0;
    if (!window.localStorage){}else {
        var List = {
            courseCode: obj.courseCode,
            classnickName: $('#classes').val()
        };
        $.ajax({
            type: "POST",
            url: '../../getCourseOfStuList',
            async: false,
            contentType: "application/json",
            data: JSON.stringify(List),
            success: function (r) {
                for (var i=0;i<r.length;i++){
                    stuNumIncourse=stuNumIncourse+1;
                }
                $('#courseStuNum').text(stuNumIncourse);
                $('#courseStuNum_Out').text(stuNumIncourse);
                // alert(stuNumIncourse);
                localStorage.setItem(userkey,JSON.stringify(r));
                // alert(JSON.stringify(localStorage.getItem(userkey)));
            }
        });
    }
}
//创建考勤并打开扫码弹窗
function openSignInModal() {
    var currtime = new Date().Format("yyyyMMddHHmm");
    if (sessionStorage.getItem(CourseSession)!='正在考勤'&&sessionStorage.getItem(CourseSignOutSession)!='正在考勤'){ //首次点击考勤，添加考勤信息并设置一个正在考勤的会话
        var attendanceList={
            courseCode:obj.courseCode,
            classnickName:$('#classes').val(),
            attendanceId:obj.courseCode+'-'+currtime,
            attendanceType:'签到'
        };
        $.ajax({
            type: "POST",
            url: '../../addAttendance',
            async: false,
            contentType: "application/json",
            data: JSON.stringify(attendanceList),
            success: function (r) {
                // alert(CourseSession);
                    sessionStorage.setItem("attendanceId",attendanceList.attendanceId);
                    sessionStorage.setItem(CourseSession,'正在考勤');//添加一个正在考勤的会话
                    count=count+1;
                    $('#signInModal').modal();
                // setInterval(function(){
                //     getCountOfSignIn();
                // }, 2000);
            }
        });

    }else if(sessionStorage.getItem(CourseSignOutSession)=='正在考勤'&&sessionStorage.getItem(CourseSession)!='正在考勤') {
        Swal.fire({
            position: 'center',
            type: 'error',
            title: '正在进行签退',
            showConfirmButton: false,
            timer: 1500
        });
    }else {
        count=count+1;
        // setInterval(function(){
        //     getCountOfSignIn();
        // }, 2000);
        $('#signInModal').modal();
    }
    if (sessionStorage.getItem("attendanceId")) {
        $('#QRCode_frame').attr('src','/wechat/toQRCode?attendanceId='+sessionStorage.getItem("attendanceId")+"&signInType="+'签到');
        setInterval(function(){
            getCountOfSignIn();
        }, 2000);
    }
}
//进入课程详情页
function toCourseDetail(){
    $.ajax({
        type: "GET",
        url: '../../toCourseDetail',
        contentType: "application/json",
        data: {

        },
        success: function (r) {
            sessionStorage.setItem("selectedNickName",$('#classes').val());
            window.location.href = '../../toCourseDetail';
            // this.$router.push( '../../toCourseDetail');
        }
    });
}
//获取当前考勤人数并刷新
function getCountOfSignIn(){
    $.ajax({
        type: "GET",
        url: '../../getSignInSum',
        // async: false,
        contentType: "application/json",
        data: {
            attendanceId:sessionStorage.getItem("attendanceId")
        },
        success: function (r) {
            $('#signInStuNum').html(r);
            $('#signOutStuNum').html(r);
        }
    });
}
// 提交考勤
function submitAttendance() {
    var attId=sessionStorage.getItem("attendanceId");
    if (attId == null || attId == '') {
        Swal.fire({
            position: 'center',
            type: 'error',
            title: '您没有正在进行的考勤',
            showConfirmButton: false,
            timer: 1500
        });
    } else {
        swal.fire({

            title: '是否提交考勤？',

            text: '课程提交后可在"考勤历史"中查看，是否现在提交？',

            type: 'warning',

            showCancelButton: true,

            confirmButtonText: '确认',

            cancelButtonText: '取消',

        }).then(function (isConfirm) {
            var editList = {
                attendanceId: attId,
                attendanceState: '已结束',
                attendanceSignin: numofSign,
                attendanceLate: numoflate,
                attendanceLeave: numofLeave,
                attendanceAbsent: numofabsent,
                attendanceNosign:numofNoSign
            }
            if (isConfirm.value) {
                $.ajax({
                    type: "POST",
                    url: '../../editAttendance',
                    async: false,
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
                        // attendanceSession='';
                        // signOutSession='';
                        sessionStorage.removeItem("attendanceId");
                        sessionStorage.removeItem(CourseSession);
                        sessionStorage.removeItem(CourseSignOutSession);
                        window.location.reload();
                    }
                });
            }
        });
    }
}
//手动考勤
function mamualAttend() {
    if (sessionStorage.getItem("attendanceId")!=null){
        swal.fire({

            title: '是否取消现有考勤进行手动签到？',

            text: '',

            type: 'warning',

            showCancelButton: true,

            confirmButtonText: '确认',

            cancelButtonText: '取消',

        }).then(function(isConfirm) {
            list={
                attendanceId:sessionStorage.getItem("attendanceId")
            }
            if(isConfirm.value){
                $.ajax({
                    type: "POST",
                    url: '../../delAttendance' ,
                    async:false,
                    contentType: "application/json",
                    data: JSON.stringify(list),
                    success: function (r) {
                        Swal.fire({
                            position: 'center',
                            type: 'success',
                            title: '已取消',
                            showConfirmButton: false,
                            timer: 1500
                        });
                        sessionStorage.removeItem(CourseSession)
                        sessionStorage.removeItem(CourseSignOutSession);
                        createAttendance();
                    }
                });
            }
        });
    }else {
        sessionStorage.removeItem(CourseSession)
        sessionStorage.removeItem(CourseSignOutSession);
        createAttendance();
    }
}
function getAllNum(){
    $.ajax({
        type: "GET",
        url: '../../wechat/getAttendanceStuNum',
        async: false,
        contentType: "application/json",
        data: {
            attendanceId:sessionStorage.getItem("attendanceId"),
            classNickname:$('#classes').val(),
            courseCode:obj.courseCode,
            attendanceResult:''
        },
        success: function (r) {
            // alert(JSON.stringify(r));
            for (var i=0;i<r.length;i++){
                numofAll=parseInt(numofAll)+1;
                // alert(i+':'+numofAll);
                if (r[i].attendanceResult=='未签到'){
                    numofNoSign=parseInt(numofNoSign)+1;
                }
                if (r[i].attendanceResult=='正常'){
                    numofSign=parseInt(numofSign)+1;
                }
                if (r[i].attendanceResult=='迟到') {
                    numoflate=parseInt(numoflate)+1;
                }
                if (r[i].attendanceResult=='病假') {
                    numofLeave=parseInt(numofLeave)+1;
                }
                if (r[i].attendanceResult=='事假') {
                    numofLeave=parseInt(numofLeave)+1;
                }
                if (r[i].attendanceResult=='旷课') {
                    numofabsent=parseInt(numofabsent)+1;
                }
            }
            // alert(numofAll);
        }
    });
}

// 开始签退
function Signout(){
    IsHasAttendance();//如果签到未提交请先提交
    var currtime = new Date().Format("yyyyMMddHHmm");
    if (sessionStorage.getItem(CourseSignOutSession)!='正在考勤'&&countOfSignOut==0){ //首次点击考勤，添加考勤信息并设置一个正在考勤的会话
        var attendanceList={
            courseCode:obj.courseCode,
            classnickName:$('#classes').val(),
            attendanceId:obj.courseCode+'-'+currtime,
            attendanceType:'签退'
        };
        $.ajax({
            type: "POST",
            url: '../../addAttendance',
            async: false,
            contentType: "application/json",
            data: JSON.stringify(attendanceList),
            success: function (r) {
                // alert(CourseSession);
                sessionStorage.setItem("attendanceId",attendanceList.attendanceId);
                sessionStorage.setItem(CourseSignOutSession,'正在考勤');//添加一个正在考勤的会话
                countOfSignOut=countOfSignOut+1;
                $('#signOutModal').modal();
                // setInterval(function(){
                //     getCountOfSignIn();
                // }, 2000);
            }
        });

    }else {
        countOfSignOut=countOfSignOut+1;
        // setInterval(function(){
        //     getCountOfSignIn();
        // }, 2000);
        $('#signOutModal').modal();
    }
    if (sessionStorage.getItem("attendanceId")) {
        $('#QRCode_frame_ofSignOut').attr('src','/wechat/toQRCode?attendanceId='+sessionStorage.getItem("attendanceId")+"&signInType="+'签退');
        setInterval(function(){
            getCountOfSignIn();
        }, 2000);
    }
}
//时间格式化问题
Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
function modalHide(){
    // alert('hide');
    $("#signInModal").modal('hide');
    // 模态框关闭监听
    $('#signInModal').on('hidden.bs.modal', function () {
        // 执行一些动作...
        window.location.reload();
    });
}
// function hideModal(){
//     // alert('hide');
//     $('#signOutModal').modal('hide');
//     // 模态框关闭监听
//     $('#signOutModal').on('hidden.bs.modal', function () {
//         // 执行一些动作...
//         window.location.reload();
//     });
// }
$('#signOutModal').on('hide.bs.modal', function () {
    // 执行一些动作...
    alert("111");
    window.location.reload();
});
// $('#signInModal').on('hide.bs.modal', function () {
//     // 执行一些动作...
//     window.location.reload();
// });
function createAttendance(){
    var currtime = new Date().Format("yyyyMMddHHmm");
    var attendanceList={
        courseCode:obj.courseCode,
        classnickName:$('#classes').val(),
        attendanceId:obj.courseCode+'-'+currtime,
        attendanceType:'签到'
    };
    $.ajax({
        type: "POST",
        url: '../../addAttendance',
        async: false,
        contentType: "application/json",
        data: JSON.stringify(attendanceList),
        success: function (r) {
            // alert(CourseSession);
            sessionStorage.setItem("attendanceId",attendanceList.attendanceId);
            sessionStorage.setItem(CourseSignOutSession,'正在考勤');//添加一个正在考勤的会话
            countOfSignOut=countOfSignOut+1;
            sessionStorage.setItem("IsManual","yes");
            window.location.reload();
            // $('#manualModal').modal();
            // setInterval(function(){
            //     getCountOfSignIn();
            // }, 2000);
        }
    });
}
// 查询学生考勤情况
function getStuIncourse() {
    var attId=sessionStorage.getItem("attendanceId");
    if(attId!==null){
        // $("#tabContainer").css('display','none');
        // $("#tabContainerSel").css('display','block');
        // alert("sss");
        if ($('#sNo_input').val()!=null&&$('#sNo_input').val()!=''){
            sessionStorage.setItem("Isselected",$('#sNo_input').val());
            window.location.reload();
        }else {
            sessionStorage.removeItem("Isselected");
            window.location.reload();
        }
    }else {
        Swal.fire({
            position: 'center',
            type: 'warning',
            title: '查询学生的考勤情况，请先创建一个考勤',
            showConfirmButton: false,
            timer: 1500
        });
    }
}

