/**
 * Created by 皓 on 2016/11/19.
 */
/*window.onload = function () {
    $('.is_check').css('display', 'none');
}*/

//$(function () {
    //去判断管理员是否登录
    //console.log('123');
    window.onload = function () {
        //console.log('456');
        //console.log(sessionStorage.userName);
        if(sessionStorage.userName == undefined || sessionStorage.userName == '') {
             //console.log($('.is_check') + 'asd');
             $('.is_check').css('display', 'none');
             $('#password').css('display', 'block');
             $('#password').prev('p').css('display', 'block');
             $('#LOGIN').css('display', 'block');
             $('#UnLogin').css('display', 'none');
        }
        else {
             $('.is_check').css('display', 'inline-block');
             $('#user_name').val(sessionStorage.userName);
             $('#password').css('display', 'none');
             $('#password').prev('p').css('display', 'none');
             $('#UnLogin').css('display', 'block');
             $('#LOGIN').css('display', 'none');
        }
    }

    //登录
    $('#LOGIN').click(function () {
        var user_name = $('#user_name').val();
        var user_password = $('#password').val();

       // console.log(user_name + ' ' + user_password);
        $.ajax({
            url : '/login/checkin',
            data : {userName : user_name, userPassword : user_password},
            cache : false,
            async : false,
            type : 'get',
            dataType : 'text',
            success : function (data) {
                //console.log(data);
                if(data == 'true') {
                   // $('#LOGIN').css('color', 'black');
                    $('.is_check').css('display', 'inline-block');
                    $('#user_name').val(user_name);
                    $('#password').css('display', 'none');
                    $('#password').prev('p').css('display', 'none');
                    $('#UnLogin').css('display', 'block');
                    $('#LOGIN').css('display', 'none');
                    alert('您已经成功登录!');
                    sessionStorage.userName = user_name;
                }
                else if(data == 'false') {
                    $('#password').val('');
                    $('#user_name').val('');
                    alert('用户名或密码错误');
                }
            }
        });
    });

    //注销
    $('#UnLogin').click(function () {
        $('.is_check').css('display', 'none');
        $('#password').css('display', 'block');
        $('#password').prev('p').css('display', 'block');
        $('#LOGIN').css('display', 'block');
        $('#UnLogin').css('display', 'none');
        $('#password').val('');
        $('#user_name').val('');
        sessionStorage.userName = '';
    });
//});
