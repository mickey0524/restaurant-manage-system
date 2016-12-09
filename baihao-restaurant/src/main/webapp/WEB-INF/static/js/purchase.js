/**
 * Created by 皓 on 2016/11/19.
 */
//$(function () {

    var menu_name = ['breadCrumbsSoup', 'deliciousCarrotBread', 'yummyNoodles',
        'vegetablePasta', 'mixedVegSalad', 'breakFastSpecial',
        'healthyTomatoSoup', 'deciliousSushi'];

    window.onload = function () {
        $.ajax({
            url : '/purchase/num',
            cache : false,
            async : false,
            type : 'get',
            dataType : 'json',
            success : function (data) {
                console.log(data);
                // for(var i = 0; i < 8; i++) {
                //     $('.surplus').eq(i).html(data.menu_name[i]);
                // }
                $('.surplus').eq(0).html(data.breadCrumbsSoup);
                $('.surplus').eq(1).html(data.deliciousCarrotBread);
                $('.surplus').eq(2).html(data.yummyNoodles);
                $('.surplus').eq(3).html(data.vegetablePasta);
                $('.surplus').eq(4).html(data.mixedVegSalad);
                $('.surplus').eq(5).html(data.breakFastSpecial);
                $('.surplus').eq(6).html(data.healthyTomatoSoup);
                $('.surplus').eq(7).html(data.deciliousSushi);
            }
        });
        var money1 = 8;
        var money2 = 9;
        var money3 = 10;
        $('.money_1').each(function() {
            $(this).html(Math.ceil(Math.random() * money1));
        });
        $('.money_2').each(function() {
            $(this).html(Math.ceil(Math.random() * money2));
        });
        $('.money_3').each(function() {
            $(this).html(Math.ceil(Math.random() * money3));
        });
    };
    
    $('.modal-footer').children('.btn-primary').click(function () {
        var footer = this.parentNode;
        var modal_body = footer.previousElementSibling;
        var modal_title = modal_body.previousElementSibling.getElementsByTagName('h2')[0].innerHTML.trim();
        modal_title = modal_title.replace(/[\u4E00-\u9FA5]/g, '');
        var market_list = '';
        var sp_list = modal_body.getElementsByTagName('input');
        var sum = 0;
        for(var i = 0; i < sp_list.length; i++) {
            var buy_num = sp_list[i].value;
            if(buy_num == '') {
                continue;
            }
            var market = sp_list[i].parentNode.parentNode;
            var sp_name = market.getElementsByTagName('h3')[0].innerHTML;
            market_list = market_list + sp_name + ' : ' + buy_num + ' ';
            sum += Number(buy_num);
        }
        market_list = modal_title + ' ' + market_list + '总数 : ' + sum;
        if(sum != 0) {
            if ($('.purchase_set').children('textarea').val() == '') {
                $('.purchase_set').children('textarea').val(market_list);
            }
            else {
                $('.purchase_set').children('textarea').val($('.purchase_set').children('textarea').val() + '\n' + market_list);
            }
        }
    });

    $('.purchase_set').children('input').click(function () {
        if($('.purchase_set').children('textarea').val() == '') {
            alert('没有购买任何菜!');
        }
        else {
            var buy_list = $('.purchase_set').children('textarea').val().split('\n');
            var buy_num = {};

            for (var i = 0; i < buy_list.length; i++) {
                var num = '';
                for (var j = buy_list[i].length - 1; buy_list[i][j] != ' '; j--) {
                    num = buy_list[i][j] + num;
                }
                var name = buy_list[i].slice(0, buy_list[i].search('菜') - 1);
                var name_list = name.split(' ');
                for(var j = 1; j < name_list.length; j++) {
                    name_list[j] = name_list[j][0].toUpperCase() + name_list[j].substring(1);
                }
                name = name_list.join('');
                //console.log(name + ' ' + num);
                buy_num[name] = num;
            }

            var menu = '';
            for (var i in buy_num) {
                menu = menu + i + ':' + buy_num[i] + ' ';
            }
            menu = menu.slice(0, menu.length - 1);
            console.log(menu);

            $.ajax({
                url: '/purchase/buy',
                data: {Menu: menu},
                cache: false,
                async: false,
                type: 'get',
                dataType: 'text',
                success: function (data) {
                    console.log(data);
                    if (data == 'true') {
                        for (var i in buy_num) {

                            var index = menu_name.indexOf(i);
                            $('.surplus').eq(index).html(Number($('.surplus').eq(index).html()) + Number(buy_num[i]));
                        }
                    }
                }
            });

            var sum = 0;
            for (var i = 0; i < buy_list.length; i++) {
                var num_list = buy_list[i].replace(/[^0-9]+/g, ' ').split(' ');
                var name = buy_list[i].slice(0, buy_list[i].search('菜') - 1);
                name = name.split(' ').join('_');
                num_list.shift();
                num_list.pop();
                var sp_list = buy_list[i].replace(/[^\u4E00-\u9FA5]+/g, ' ').split(' ');
                sp_list.shift();
                sp_list.pop();
                sp_list.pop();
                for (var j = 0; j < sp_list.length; j++) {
                    sp_list[j] = sp_list[j].charAt(sp_list[j].length - 1);
                    if (sp_list[j] == '一') {
                        sp_list[j] = '1';
                    }
                    else if (sp_list[j] == '二') {
                        sp_list[j] = '2';
                    }
                    else {
                        sp_list[j] = '3';
                    }
                }

                for (var j = 0; j < num_list.length; j++) {
                    var money = $('.' + name + '.money_' + sp_list[j]).html();
                    //console.log(money);
                    sum += Number(num_list[j]) * Number(money);
                }
            }

            $.ajax({
                url: 'balance/expense',
                data: {Expense: String(sum)},
                cache: false,
                async: false,
                type: 'get',
                success: function () {}
            });
            alert('成功买菜!');
        }
    });
//});
