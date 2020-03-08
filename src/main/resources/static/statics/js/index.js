function firstLoad() {
    $('#main_panel').load("../../myclass");
}
function change_active(name) {
	if(name == 'myclass-li') {
		$('#myclass-li').addClass('active');
		$('#data-li').removeClass('active');
		$('#user-li').removeClass('active');
		$('#stu-li').removeClass('active');
		$('#main_panel').load("../../myclass");
	} else if(name == 'data-li') {
		$('#myclass-li').removeClass('active');
		$('#data-li').addClass('active');
		$('#user-li').removeClass('active');
		$('#stu-li').removeClass('active');
		$('#main_panel').load("../../data");
	}else if(name == 'user-li') {
		$('#myclass-li').removeClass('active');
		$('#data-li').removeClass('active');
		$('#user-li').addClass("active");
		$('#stu-li').removeClass('active');
        $('#main_panel').load("../../user");
	}else if(name == 'stu-li') {
		$('#myclass-li').removeClass('active');
		$('#data-li').removeClass('active');
		$('#user-li').removeClass('active');
		$('#stu-li').addClass("active");
        $('#main_panel').load("../../stu");
	}
}