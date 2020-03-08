var course=sessionStorage.getItem("courseItem");
var obj = eval('(' + course + ')');
var year=obj.courseTerm.substring(0,9);
var term=obj.courseTerm.substring(9);
var userkey=sessionStorage.getItem("userCode")+"-courseStu";
var nickname=sessionStorage.getItem("selectedNickName");
var attendanceHistoryId='';
