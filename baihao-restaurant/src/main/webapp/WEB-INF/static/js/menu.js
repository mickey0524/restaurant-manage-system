/**
 * Created by 皓 on 2016/11/20.
 */

var menu_name = ['breadCrumbsSoup', 'deliciousCarrotBread', 'yummyNoodles',
    'vegetablePasta', 'mixedVegSalad', 'breakFastSpecial',
    'healthyTomatoSoup', 'deciliousSushi'];

$('.order-top').find('.hvr-shutter-in-horizontal').click(function () {
    var menu_name = $(this).parent().prev().children('h4').html();
    var menu_money = $(this).parent().prev().children('h3').html().substring(1);
    var sum_money = $('.contact-left').children('input').eq(2).val();
    $('.contact-left').children('input').eq(2).val(String(Number(sum_money) + Number(menu_money)));
   // console.log(menu_name);
    var menu_list = $('.contact-right').children('textarea').val().split('\n');
    //console.log(menu_list);
    var exist = false;
    for(var i = 0; i < menu_list.length; i++) {
        var index = menu_list[i].indexOf('*');
        if(menu_name == menu_list[i].substring(0, index - 1)) {
            var money_index = menu_list[i].indexOf('¥');
            var num = menu_list[i].substring(index + 2, money_index - 1);
            menu_list[i] = menu_list[i].substring(0, index + 2) + String(Number(num) + 1) + menu_list[i].substring(money_index - 1);
            exist = true;
            break;
        }
    }
    if(exist) {
        $('.contact-right').children('textarea').val(menu_list.join('\n'));
    }
    else {
        $('.contact-right').children('textarea').val($('.contact-right').children('textarea').val() + menu_name + ' ' + '* 1 ' + '¥' + menu_money + '\n');
    }
});

$('.contact-form').children(':submit').click(function () {

    var data = '';
    var table_num = $('.contact-left').children('input').eq(0).val();
    data = data + 'tableNum:' + table_num + ' ';
    var table_sum = $('.contact-left').children('input').eq(2).val();
    var menu_list = $('.contact-right').children('textarea').val().split('\n');
    var menu_require = $('.contact-left').children('input').eq(1).val();
    data = data + 'menuRequire:' + menu_require + ' ';
    menu_list.pop();
    for(var i = 0; i < menu_list.length; i++) {
        menu_list[i] = menu_list[i].substring(0, menu_list[i].indexOf('¥') - 1);
        menu_list[i] = menu_list[i].replace(/ \* /g, ':');
        var item_list = menu_list[i].substring(0, menu_list[i].indexOf(':')).split(' ');
        for(var j = 0; j < item_list.length; j++) {
            item_list[j] = item_list[j][0].toUpperCase() + item_list[j].substring(1);
        }
        menu_list[i] = item_list.join('') + menu_list[i].substring(menu_list[i].indexOf(':'));
        menu_list[i] = menu_list[i][0].toLowerCase() + menu_list[i].substring(1);
        data = data + menu_list[i] + ' ';
    }

    for(var i = 0; i < menu_name.length; i++) {
        if(data.indexOf(menu_name[i]) == '-1') {
            data = data + menu_name[i] + ':0 ';
        }
    }
    data = data + 'sum:' + table_sum;
    //data = data[0].toLowerCase() + data.substring(1);
    var isprim = true;
    for(var i = 0; i < 3; i++) {
        if($('.contact-left').children('input').eq(i).val() == '') {
            alert('请完善所有订单信息');
            isprim = false;
            break;
        }
    }
    if(isprim) {
        $.ajax({
            url : 'indent/order',
            data : {Order : data},
            type : 'get',
            cache : false,
            async : false,
            dataType : 'text',
            success : function (data) {
                console.log(data);
                if(data == 'false') {
                    alert("请不要提交重复的桌号!");
                    $('.contact-left').children('input').eq(0).val('');
                }
                else if(data == 'true'){
                    $('.contact-left').children('input').val('');
                    $('.contact-right').children('textarea').val('');
                    alert('您已经成功提交订单!');
                }
                else {
                    $('.contact-left').children('input').val('');
                    $('.contact-right').children('textarea').val('');
                    alert(data + '已经卖完了，去提醒老板进货吧，其他菜马上就上哟!');
                }
            }
        });
    }
});

$('.hvr-shutter-in-horizontal').mouseup(function (e) {
   // $('.bag').css('display', 'none');
    console.log(e.pageX + ' ' + e.pageY);
    var body = $('body')[0];
    var tag = document.createElement('div');
    tag.className = 'bag';
    tag.style.top = e.pageY + 'px';
    tag.style.left = e.pageX + 'px';
    tag.innerHTML = '<img src="images/bag.png">';
    body.appendChild(tag);

    var textarea = $('.contact-right').children('textarea')[0];
    console.log('宽:' + textarea.offsetLeft);
    console.log('高:' + textarea.offsetTop);
    var go_left = textarea.offsetLeft - e.pageX + 5;
    var go_top = textarea.offsetTop - e.pageY;
    //console.log(go_left + ' ' + go_top);
    tag.style.transform = 'translate(' + go_left + 'px, ' + go_top + 'px)';
    tag.style.webkitTransform =  'translate(' + go_left + 'px, ' + go_top + 'px)';
});
