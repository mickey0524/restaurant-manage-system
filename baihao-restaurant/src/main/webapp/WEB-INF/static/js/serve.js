/**
 * Created by 皓 on 2016/11/23.
 */

var menu_name = ['breadCrumbsSoup', 'deliciousCarrotBread', 'yummyNoodles',
    'vegetablePasta', 'mixedVegSalad', 'breakFastSpecial',
    'healthyTomatoSoup', 'deciliousSushi'];

var menu_time = {};

window.onload = function () {
    if(sessionStorage.userName == undefined || sessionStorage.userName == '') {
        $('.is_check').css('display', 'none');
    }
    $.ajax({
        url : '/purchase/menu',
        cache : false,
        async : false,
        type : 'get',
        dataType : 'json',
        success : function (data) {
            //console.log(data);
            for(var i in data) {
               // console.log(i);
                var time = data[i].menuTime;
                //console.log(time);
                var minute = Math.floor(Number(time) / 60);
                var second = Number(time) % 60;
                time = minute + 'min' + second + 's';
                menu_time[data[i].menuName] = time;
            }
            //console.log(time);
            //console.log(menu_time);
        }
    });

    $.ajax({
        url : '/serve/Serve',
        cache : false,
        async : false,
        type : 'get',
        dataType : 'json',
        success : function (data) {
            console.log(data);
            var is_data = false;
            for(var i = 0; i < data.length; i++) {
                build_pipe(data[i], i);
                if(data[i].length != 0) {
                    is_data = true;
                }
            }
            if(is_data) {
                countDown();
            }
        }
    });
};

function build_pipe(data, cookerNum) {
    var parent = $('.cook_menu').eq(cookerNum)[0];
    //console.log(parent);
    for(var i = 0; i < data.length; i++) {
        var menu = document.createElement('div');
        menu.className = 'menu';

        var count_down = menu_time[data[i].menuName];
        if(i == 0) {
            var date = new Date();
            var hour = date.getHours();
            var minute = date.getMinutes();
            var second = date.getSeconds();
            //console.log(hour + ' ' + minute + ' ' + second);

            var finish_time = data[i].finishTime;
            var finish_hour = finish_time.substring(0, finish_time.length - 4);
            var finish_minute = finish_time.substring(finish_time.length - 4, finish_time.length - 2);
            var finish_second = finish_time.substring(finish_time.length - 2, finish_time.length);
            //console.log(finish_hour + ' ' + finish_minute + ' ' + finish_second);

            var time_need = Number(finish_second) - Number(second) + (Number(finish_minute) - Number(minute)) * 60 +
                            (Number(finish_hour) - Number(hour)) * 3600;
            if(time_need < 0) {
                $.ajax({
                    url : '/serve/delete',
                    data : {tableNum : data[i].tableNum, menuName : data[i].menuName, cookerName : data[i].cookerName},
                    cache : false,
                    async : false,
                    type : 'get',
                    success : function () {}
                });
                data.splice(i, 1);
                i -= 1;
                continue;
            }
            //console.log('time_need : ' + time_need);
            count_down = Math.floor(time_need / 60) + 'min' + time_need % 60 + 's';
        }
        menu.innerHTML = '<h3>' + (i + 1) + '</h3>' +
                         '<img src="images/k' + (menu_name.indexOf(data[i].menuName) + 1) + '.jpg"' +  'class="img-responsive">' +
                         '<p>桌号:<span>' + data[i].tableNum + '</span></p>' +
                         '<p>菜名:<span>' + data[i].menuName + '</span></p>' +
                         '<p>烹饪所需时间:<span class="time">' + count_down + '</span></p>';

        parent.appendChild(menu);
    }
}

function countDown() {
    var first_menu = [];
    var timer = setInterval(function () {
        first_menu[0] = $('.cook_menu_1').children('.menu:first');
        first_menu[1] = $('.cook_menu_2').children('.menu:first');
        first_menu[2] = $('.cook_menu_3').children('.menu:first');
        is_complete = [];
        for(var i = 0; i < first_menu.length; i++) {
            var time = first_menu[i].find('.time').html();
            console.log(time);
            is_complete[i] = time;

            if(is_complete.length == 3) {
                var complete = true;
                for(var j = 0; j < 3; j++) {
                    if(is_complete[j] != undefined) {
                        complete = false;
                    }
                }
                if(complete) {
                    clearInterval(timer);
                }
            }
            if(time == undefined) {
                continue;
            }

            var n_index = time.indexOf('n');
            var m_index = n_index - 2;
            var minute = time.slice(0, m_index);
            var second = time.slice(n_index + 1, time.length - 1);
            console.log(minute + ' ' + second);
            if(second == '0' && minute == '0') {
               // first_menu[i].parentNode.removeChild(first_menu[i]);
                //first_menu[i].fadeOut('100', function () {
                deleteMenu(first_menu[i], first_menu[i].prev());
                first_menu[i].remove();
                //});

                continue;
            }
            if(second == '0') {
                second = '59';
                minute = Number(minute) - 1;
            }
            else {
                second = Number(second) - 1;
            }
            first_menu[i].find('.time').html(minute + 'min' + second + 's');
        }
    }, 1000);
}

function deleteMenu(node, pre) {
    //console.log('delete');
    //console.log(node);
    var table_num = node.find('span').eq(0).html();
    //console.log(table_num);
    var menu_name = node.find('span').eq(1).html();
    //console.log(menu_name);
    //console.log(node.prev());
    var cooker_name = pre.children('h3').html().split(' ').join('_');
   // console.log(table_num + ' ' + menu_name + ' ' + cooker_name);
    $.ajax({
        url : '/serve/delete',
        data : {tableNum : table_num, menuName : menu_name, cookerName : cooker_name},
        cache : false,
        async : false,
        type : 'get',
        success : function () {}
    });
}


