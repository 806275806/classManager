$('#grade').datetimepicker({
    format: 'yyyy', startView : "decade", minView: 'decade',language : 'zh-CN',autoclose: true, clearBtn : true
});
var course_header=new Vue({
    el: '#course-header',
    data: {

    },
    created: function () {
    },
    methods: {
        pageInit: function () {
            $.ajax({
                type: "GET",
                url: '../../getCourses' ,
                contentType: "application/json",
                data: {
                    userCode:$('#userCode').text(),
                    courseState:'未结课'
                },
                success: function (r) {
                    course.CourseList=r
                }
            });
        },
        dealType:function (dealType) {
            $('#courseType').selectpicker('refresh');
            $('#term').selectpicker('refresh');
            if (dealType==1) {
                $('#addCourse_h4').css('display','block')
                $('#updateCourse_h4').css('display','none');
                $('#addCourse_footer').css('display','block');
                $('#updateCourse_footer').css('display','none');
                course.modal_CourseCode='';
                course.modal_CourseName='';
                $('#courseType').val('');
                $('#years-1').val('');
                $('#years-2').val('');
                $('#term').selectpicker('val','');
                course.classItems=[];
                $('#CourseModal').modal();
            }
        },
        //课程归档模态框弹出
        getEndCourseList:function () {
            EndCourse.pageInit();
            $('#EndCourseModal').modal();
        }
    }
});
var course = new Vue({
    el: '#xa',
    data: {
        isrefresh:0,
        userCode:$('#userCode').text(),//用户编号
        CourseList:"",//课程列表
        CourseName:'',//课程名
        ClassNickName:'',//课程简称
        index0:'',//课程列表下标
        deptToItems:[],//用于存放院系下拉框内容的数组
        majorsToItems:[],//用于存放专业下拉框内容的数组
        classToItems:[],//用于存放班级下拉框内容的数组
        // deptListForSel:[],//用于存放动态获取的院系信息，存入后add给以上Items
        // majorListForSel:[],//用于存放动态获取的专业信息
        // classListForSel:[],//用于存放动态获取的班级信息
        classItems:[],//
        classCodeitems:[],
        classNickNameitems:'',
        modal_CourseCode_0:'',
        modal_CourseCode:'',//模态框表单中的课程编号
        modal_CourseName:'',//模态框表单中的课程名称
        modal_CourseTerm:'',//模态框表单中的学期
        modal_ClassList:[]//模态框表单中用于存放上课班级列表
    },
    created: function () {
        sessionStorage.setItem("userCode",$('#userCode').text());
        this.pageInit();
        this.$nextTick(function(){
            $('.grade').datetimepicker({
                    format: 'yyyy', startView : "decade", minView: 'decade',language : 'zh-CN',autoclose: true, clearBtn : true
                });
            $('.dept-change').selectpicker('refresh');
            $('.major-change').selectpicker('refresh');
            $('.class-change').selectpicker({width: '75%'});
            $('.class-change').selectpicker('refresh');
        })
    },
    methods: {
        pageInit: function () {
            $.ajax({
                type: "GET",
                url: '../../getCourses' ,
                contentType: "application/json",
                data: {
                    userCode:$('#userCode').text(),
                    courseState:'未结课'
                },
                success: function (r) {
                    course.CourseList=r
                }
            });
        },
        //编辑课程信息弹出窗口，并赋值
        EditCourse:function (courseItem) {
            $('#courseType').selectpicker('refresh');
            $('#term').selectpicker('refresh');
                $('#addCourse_h4').css('display','none')
                $('#updateCourse_h4').css('display','block');
                $('#addCourse_footer').css('display','none');
                $('#updateCourse_footer').css('display','block');
            course.classItems=[];
            course.modal_CourseCode=courseItem.courseCode;
            course.modal_CourseCode_0=courseItem.courseCode;
            course.modal_CourseName=courseItem.courseName;
            $('#courseType').selectpicker('val',courseItem.courseType);
            $('#years-1').val(courseItem.courseTerm.substring(0,4));
            $('#years-2').val(courseItem.courseTerm.substring(5,9));
            $('#term').selectpicker('val',courseItem.courseTerm.substring(9));
            $('.dept-change').selectpicker('refresh');
            $('.major-change').selectpicker('refresh');
            $('.class-change').selectpicker({width: '75%'});
            $('.class-change').selectpicker('refresh');
            for (var i=0;i<courseItem.classes.length;i++){
                course.classItems.push(1);
                this.$nextTick(function(){
                    var classList={
                        majorCode:'',
                        classGrade:''
                    }
                    $('.grade').datetimepicker({
                        format: 'yyyy', startView : "decade", minView: 'decade',language : 'zh-CN',autoclose: true, clearBtn : true
                    });
                    $.ajax({
                        type: "GET",
                        url: '../../justGetDept' ,
                        contentType: "application/json",
                        async:false,
                        data: {},
                        success: function (r) {
                            var List=eval(r);
                            // course.deptListForSel=List;
                            course.deptToItems.push(List);
                        }
                    });
                    $.ajax({
                        type: "GET",
                        url: '../../getMajor' ,
                        contentType: "application/json",
                        async:false,
                        data: {
                            deptId:''
                        },
                        success: function (r) {
                            var List=eval(r);
                            // course.majorListForSel=List;
                            course.majorsToItems.push(List);
                        }
                    });
                    $.ajax({
                        type: "POST",
                        url: '../../getClasstoSel' ,
                        async:false,
                        contentType: "application/json",
                        data:JSON.stringify(classList) ,
                        success: function (r) {
                            var List=eval(r);
                            // course.classListForSel=List;
                            course.classToItems.push(List);
                        }
                    });
                    this.$nextTick(function(){
                        for (var i=0;i<courseItem.classes.length;i++){
                            var classGrade=courseItem.classes[i].classCode.substring(0,4);
                            var deptId=courseItem.classes[i].deptId;
                            var majorCode=courseItem.classes[i].classCode.substring(4,8);
                            var classCode=courseItem.classes[i].allClass.split(',');
                            var classNickName=courseItem.classes[i].classNickname;
                            $(this.$refs.classGrade[i]).val(classGrade);
                            $(this.$refs.depts[i]).selectpicker('val',deptId);
                            $(this.$refs.major[i]).selectpicker('val',majorCode);
                            $(this.$refs.classes[i]).selectpicker('val',classCode);
                            $(this.$refs.classNickName[i]).val(classNickName);
                        }
                    });
                });
            }
            $('#CourseModal').modal('show');
        },
        //编辑课程提交
        EditCourseSubmit:function(){

            var CourseLists=[];
            for (var i=0;i<course.classItems.length;i++){
                if (course.classItems[i]==1){
                    for (var j=0;j<$(this.$refs.classes[i]).val().length;j++){
                        course.modal_ClassList.push({
                            courseCode:course.modal_CourseCode,
                            deptId:$(this.$refs.depts[i]).val(),
                            classCode:$(this.$refs.classes[i]).val()[j],
                            classNickname:$(this.$refs.classNickName[i]).val()
                        });
                    }
                }else {
                    continue;
                }
            }
            CourseLists={
                courseCode_1:course.modal_CourseCode_0,
                courseCode:course.modal_CourseCode,
                courseName:course.modal_CourseName,
                courseType:$('#courseType').val(),
                courseTerm:$('#years-1').val()+"-"+$('#years-2').val()+$('#term').val(),
                userCode:course.userCode,
                courseState:'未结课',
                classes:course.modal_ClassList,
            }
            // alert(JSON.stringify(CourseLists));
            // alert(JSON.stringify(CourseLists));
            if (CourseLists.courseCode==null||CourseLists.courseCode==''){
                Swal.fire({
                    position: 'center',
                    type: 'error',
                    title: '请输入课程编号',
                    showConfirmButton: false,
                    timer: 1500
                });
            }else if (CourseLists.courseName==null||CourseLists.courseName==''){
                Swal.fire({
                    position: 'center',
                    type: 'error',
                    title: '请输入课程名称',
                    showConfirmButton: false,
                    timer: 1500
                });
            }else if (CourseLists.courseType==null||CourseLists.courseType==''){
                Swal.fire({
                    position: 'center',
                    type: 'error',
                    title: '请选择课程类型',
                    showConfirmButton: false,
                    timer: 1500
                });
            }else if (CourseLists.courseTerm==null||CourseLists.courseTerm==''){
                Swal.fire({
                    position: 'center',
                    type: 'error',
                    title: '请选择学年与学期',
                    showConfirmButton: false,
                    timer: 1500
                });
            }else if (CourseLists.classes.length==0){
                Swal.fire({
                    position: 'center',
                    type: 'error',
                    title: '请完善上课班级信息',
                    showConfirmButton: false,
                    timer: 1500
                });
            }else {
                $.ajax({
                    type: "POST",
                    url: '../../updateCourse' ,
                    async:false,
                    contentType: "application/json",
                    data: JSON.stringify(CourseLists),
                    success: function (r) {
                        Swal.fire({
                            position: 'center',
                            type: 'success',
                            title: '修改成功',
                            showConfirmButton: false,
                            timer: 1500
                        });
                        $('#CourseModal').modal('hide');
                        course.modal_ClassList=[];
                        // alert("222");
                        if (r==-1){
                            Swal.fire({
                                position: 'center',
                                type: 'error',
                                title: '修改失败，课程编号已存在',
                                showConfirmButton: false,
                                timer: 1500
                            });
                        }
                        course.pageInit();
                    },
                    error: function (r){
                        Swal.fire({
                            position: 'center',
                            type: 'success',
                            title: '修改失败，课程编号已存在',
                            showConfirmButton: false,
                            timer: 1500
                        });
                    }
                });
            }
        },
        // 删除课程
        DelCourse:function(courseItem){
            swal.fire({

                title: '确认删除？',

                text: '删除该课程信息后将无法恢复，所该关联的其他信息也将删除',

                type: 'warning',

                showCancelButton: true,

                confirmButtonText: '确认',

                cancelButtonText: '取消',

            }).then(function(isConfirm) {
                if(isConfirm.value){
                    $.ajax({
                        type: "POST",
                        url: '../../delCourse',
                        contentType: "application/json",
                        data: JSON.stringify(courseItem),
                        success: function (r) {
                            Swal.fire({
                                position: 'center',
                                type: 'success',
                                title: '删除成功',
                                showConfirmButton: false,
                                timer: 1500
                            });
                            course.pageInit();
                        },
                        error: function () {
                            Swal.fire({
                                position: 'center',
                                type: 'error',
                                title: '删除失败',
                                showConfirmButton: false,
                                timer: 1500
                            });
                        }
                    });
                }
            });
        },
        // 课程结课
        EndCourse:function(courseItem){
            swal.fire({

                title: '确认结课？',

                text: '请确认课程是否已结课（已结课的课程信息将归入“课程归档”内）',

                type: 'warning',

                showCancelButton: true,

                confirmButtonText: '确认',

                cancelButtonText: '取消',

            }).then(function(isConfirm) {
                if(isConfirm.value){
                    $.ajax({
                        type: "POST",
                        url: '../../setCourseEnd',
                        contentType: "application/json",
                        data: JSON.stringify(courseItem),
                        success: function (r) {
                            Swal.fire({
                                position: 'center',
                                type: 'success',
                                title: '已结课',
                                showConfirmButton: false,
                                timer: 1500
                            });
                            course.pageInit();
                            EndCourse.pageInit();
                        },
                    });
                }
            });
        },
        getNickName: function(e,index){
            course.ClassNickName=e.target.value;
            course.index0=index;
        },
        getStuNum: function (courseCode,classNickName) {
            var StuNum_Span='';
            $.ajax({
                type: "GET",
                url: '../../getStuNum' ,
                async:false,
                contentType: "application/json",
                data: {
                    courseCode:courseCode,
                    classNickName:classNickName
                },
                success: function (r) {
                    StuNum_Span=JSON.stringify(r[0]);
                    // course.StuNum_Span.push({name:StuNum_Span});
                    return StuNum_Span;
                }
            });
            return StuNum_Span;
        },
        yearsChange:function () {
            var years1= $('#years-1').val();
            var years2=parseInt(years1)+1;
            $('#years-2').val(years2);
        },
        addClasses :function () {
            course.classItems.push('1');
            this.$nextTick(function(){
                var classList={
                    majorCode:'',
                    classGrade:''
                }
                $('.grade').datetimepicker({
                    format: 'yyyy', startView : "decade", minView: 'decade',language : 'zh-CN',autoclose: true, clearBtn : true
                });
                $.ajax({
                    type: "GET",
                    url: '../../justGetDept' ,
                    contentType: "application/json",
                    data: {},
                    success: function (r) {
                        var List=eval(r);
                        // course.deptListForSel=List;
                        course.deptToItems.push(List);
                    }
                });
                $.ajax({
                    type: "GET",
                    url: '../../getMajor' ,
                    contentType: "application/json",
                    data: {
                        deptId:''
                    },
                    success: function (r) {
                        var List=eval(r);
                        // course.majorListForSel=List;
                        course.majorsToItems.push(List);
                    }
                });
                $.ajax({
                    type: "POST",
                    url: '../../getClasstoSel' ,
                    contentType: "application/json",
                    data:JSON.stringify(classList) ,
                    success: function (r) {
                        var List=eval(r);
                        // course.classListForSel=List;
                        course.classToItems.push(List);
                    }
                });
                $('.dept-change').selectpicker('refresh');
                $('.major-change').selectpicker('refresh');
                $('.class-change').selectpicker({width: '75%'});
                $('.class-change').selectpicker('refresh');
            });
        },
        delclasses:function(index){
            course.classItems.splice(index,1,'0');
        },
        deptHaveChange:function (e,index) {
            var deptId=e.target.value;
            $.ajax({
                type: "GET",
                url: '../../getMajor' ,
                async:false,
                contentType: "application/json",
                data: {
                    deptId:deptId
                },
                success: function (r) {
                    var List=eval(r);
                    // course.majorListForSel=List;
                    course.majorsToItems.splice(index,1,List);
                }
            });
            this.$nextTick(function(){
                $(this.$refs.major[index]).selectpicker('refresh');
            });
        },
        majorHaveChange:function (e,index) {
            var majorCode=e.target.value;
            var classGrade=$(this.$refs.classGrade[index]).val();
            var classList={
                majorCode:majorCode,
                classGrade:classGrade
            }
            $.ajax({
                type: "POST",
                url: '../../getClasstoSel' ,
                async:false,
                contentType: "application/json",
                data: JSON.stringify(classList),
                success: function (r) {
                    var List=eval(r);
                    // course.classListForSel=List;
                    course.classToItems.splice(index,1,List);
                }
            });
            this.$nextTick(function() {
                $(this.$refs.classes[index]).selectpicker('refresh');
            });
        },
        //进入课程详情页
        toCourseDetail:function(courseItem){
            $.ajax({
                type: "GET",
                url: '../../toCourseDetail',
                contentType: "application/json",
                data: {

                },
                success: function (r) {
                    sessionStorage.setItem("courseItem",JSON.stringify(courseItem));
                    var classnickName='#'+courseItem.courseCode;
                    sessionStorage.setItem("selectedNickName",$(classnickName).selectpicker('val'));
                    window.location.href = '../../toCourseDetail';
                    // this.$router.push( '../../toCourseDetail');
                }
            });
        },
        // 添加课程
        addCourse:function () {
            var CourseLists=[];
            for (var i=0;i<course.classItems.length;i++){
                if (course.classItems[i]==1){
                for (var j=0;j<$(this.$refs.classes[i]).val().length;j++){
                    // alert('j='+j+'、'+$(this.$refs.classes[i]).val()[j]);
                    course.modal_ClassList.push({
                        courseCode:course.modal_CourseCode,
                        deptId:$(this.$refs.depts[i]).val()[j],
                        classCode:$(this.$refs.classes[i]).val()[j],
                        classNickname:$(this.$refs.classNickName[i]).val()
                    });
                }
                }else {
                    continue;
                }
            }
            CourseLists={
                courseCode:course.modal_CourseCode,
                courseName:course.modal_CourseName,
                courseType:$('#courseType').val(),
                courseTerm:$('#years-1').val()+"-"+$('#years-2').val()+$('#term').val(),
                userCode:course.userCode,
                courseState:'未结课',
                classes:course.modal_ClassList,
            }
            if (CourseLists.courseCode==null||CourseLists.courseCode==''){
                Swal.fire({
                    position: 'center',
                    type: 'error',
                    title: '请输入课程编号',
                    showConfirmButton: false,
                    timer: 1500
                });
            }else if (CourseLists.courseName==null||CourseLists.courseName==''){
                Swal.fire({
                    position: 'center',
                    type: 'error',
                    title: '请输入课程名称',
                    showConfirmButton: false,
                    timer: 1500
                });
            }else if (CourseLists.courseType==null||CourseLists.courseType==''){
                Swal.fire({
                    position: 'center',
                    type: 'error',
                    title: '请选择课程类型',
                    showConfirmButton: false,
                    timer: 1500
                });
            }else if (CourseLists.courseTerm==null||CourseLists.courseTerm==''){
                Swal.fire({
                    position: 'center',
                    type: 'error',
                    title: '请选择学年与学期',
                    showConfirmButton: false,
                    timer: 1500
                });
            }else if (CourseLists.classes.length==0){
                Swal.fire({
                    position: 'center',
                    type: 'error',
                    title: '请完善上课班级信息',
                    showConfirmButton: false,
                    timer: 1500
                });
            }else {
            $.ajax({
                type: "POST",
                url: '../../addCourse' ,
                async:true,
                contentType: "application/json",
                data: JSON.stringify(CourseLists),
                success: function (r) {
                    Swal.fire({
                        position: 'center',
                        type: 'success',
                        title: '添加成功',
                        showConfirmButton: false,
                        timer: 1500
                    });
                    $('#CourseModal').modal('hide');
                    // location.reload();
                    course.pageInit();
                }
            });
            }
        }
    }
});
var EndCourse=new Vue(
    {
    el: '#EndCourseModal',
    data: {
        userCode:course.userCode,
        endCourseList:"",//课程列表
        ClassNickName:"",
        index0:""
    },
    created: function () {
        this.pageInit();
    },
    methods: {
        pageInit: function () {
            $('#years-1-end').val('');
            $('#years-2-end').val('');
            $.ajax({
                type: "GET",
                url: '../../getCourses' ,
                contentType: "application/json",
                data: {
                    userCode:$('#userCode').text(),
                    courseState:'已结课'
                },
                success: function (r) {
                    EndCourse.endCourseList=r
                }
            });
            this.$nextTick(function() {
                $('#term-end').selectpicker('refresh');
            });
        },
        toCourseDetail:function(courseItem){
            $.ajax({
                    type: "POST",
                    url: '../../toCourseDetail',
                    contentType: "application/json",
                    data: JSON.stringify(courseItem),
                    success: function (r) {
                    }
        });
            },
        getStuNum: function (courseCode,classNickName) {
            var StuNum_Span='';
            $.ajax({
                type: "GET",
                url: '../../getStuNum' ,
                async:false,
                contentType: "application/json",
                data: {
                    courseCode:courseCode,
                    classNickName:classNickName
                },
                success: function (r) {
                    StuNum_Span=JSON.stringify(r[0]);
                    // course.StuNum_Span.push({name:StuNum_Span});
                    return StuNum_Span;
                }
            });
            return StuNum_Span;
        },
        DelCourse:function(courseItem){
            swal.fire({

                title: '确认删除？',

                text: '删除该课程信息后将无法恢复，所该关联的其他信息也将删除',

                type: 'warning',

                showCancelButton: true,

                confirmButtonText: '确认',

                cancelButtonText: '取消',

            }).then(function(isConfirm) {
                if(isConfirm.value){
                    $.ajax({
                        type: "POST",
                        url: '../../delCourse',
                        contentType: "application/json",
                        data: JSON.stringify(courseItem),
                        success: function (r) {
                            Swal.fire({
                                position: 'center',
                                type: 'success',
                                title: '删除成功',
                                showConfirmButton: false,
                                timer: 1500
                            });
                            EndCourse.pageInit();
                        },
                        error: function () {
                            Swal.fire({
                                position: 'center',
                                type: 'error',
                                title: '删除失败',
                                showConfirmButton: false,
                                timer: 1500
                            });
                        }
                    });
                }
            });
        },
        getNickName: function(e,index){
            sessionStorage.setItem("selectedNickName",e.target.value);
            EndCourse.ClassNickName=e.target.value;
            EndCourse.index0=index;
        },
        yearsChange:function () {
            var years1= $('#years-1-end').val();
            var years2=parseInt(years1)+1;
            $('#years-2-end').val(years2);
        },
        searchEndCourse:function () {
            var courseTerm=$('#years-1-end').val()+"-"+$('#years-2-end').val()+$('#term-end').val();
            $.ajax({
                type: "GET",
                url: '../../getCoursesListByterm' ,
                contentType: "application/json",
                data: {
                    userCode:$('#userCode').text(),
                    courseState:'已结课',
                    courseTerm:courseTerm
                },
                success: function (r) {
                    EndCourse.endCourseList=r;
                }
            });
        },
        setCourseNotEnd:function (courseItem) {
            swal.fire({

                title: '确认取消归档？',

                text: '请确认是否取消归档（取消归档的课程信息将归入“我的课程”内）',

                type: 'warning',

                showCancelButton: true,

                confirmButtonText: '确认',

                cancelButtonText: '取消',

            }).then(function(isConfirm) {
                if(isConfirm.value){
                    $.ajax({
                        type: "POST",
                        url: '../../setCourseNotEnd',
                        contentType: "application/json",
                        data: JSON.stringify(courseItem),
                        success: function (r) {
                            Swal.fire({
                                position: 'center',
                                type: 'success',
                                title: '已取消',
                                showConfirmButton: false,
                                timer: 1500
                            });
                            course.pageInit();
                            EndCourse.pageInit();
                        },
                    });
                }
            });
        }
    }
    }
    );