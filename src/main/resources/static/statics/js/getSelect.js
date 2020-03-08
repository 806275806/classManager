function getDept(setVal,setCode) {
    //院系选择框加载
    $.ajax({
        type: "GET",
        url: '../../justGetDept' ,
        contentType: "application/json",
        data: {},
        success: function (r) {
            var List=eval(r);
            $('#deptSel_Modal').empty();
            $('#deptSel_Modal').append('<option value=""  >全部</option>');
            for (i=0;i<List.length;i++){
                if(List[i].deptId==0||List[i].deptId==''){}else {
                    $('#deptSel_Modal').append(
                        "<option value='" + List[i].deptId + "'>" + List[i].deptName + "</option>"
                    )
                }
            }
            if (setVal!=null||setVal!=''){
                $('#deptSel_Modal').selectpicker('val',setVal);
                $('#deptSel_Modal').selectpicker('refresh');
            }
        }
    });
    getMajor(setCode,setVal);
}
function deptChange() {
    var deptid= $('#deptSel_Modal').selectpicker('val');
    var setCode=$('#majorSel_Modal').selectpicker('val');
    getMajor(setCode,deptid);
}
function getMajor(setCode,deptId) {
    $.ajax({
        type: "GET",
        url: '../../getMajor' ,
        contentType: "application/json",
        data: {
            deptId:deptId
        },
        success: function (r) {
            var List=eval(r);
            $('#majorSel_Modal').empty();
            $('#majorSel_Modal').append('<option value=""  >全部</option>')
            for (i=0;i<List.length;i++){
                $('#majorSel_Modal').append(
                    "<option value='"+List[i].majorCode+"'>"+List[i].majorName+"</option>"
                )
            }
            if (setCode!=null||setCode!=''){
                $('#majorSel_Modal').val(setCode);
            }
            $('#majorSel_Modal').selectpicker('refresh');
            $('#majorSel_Modal').selectpicker('refresh');
        }
    });
}
function getYear(t) {
    t.datetimepicker({
        format: 'yyyy', startView : "decade", minView: 'decade',language : 'zh-CN',autoclose: true, clearBtn : true
    });
}
