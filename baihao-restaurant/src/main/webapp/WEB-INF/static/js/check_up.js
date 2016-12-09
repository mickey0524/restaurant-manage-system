/**
 * Created by 皓 on 2016/11/21.
 */


var menu_name = ['breadCrumbsSoup', 'deliciousCarrotBread', 'yummyNoodles',
    'vegetablePasta', 'mixedVegSalad', 'breakFastSpecial',
    'healthyTomatoSoup', 'deciliousSushi'];

var menu_cost = {};
var bill = {expense : [], income : [], net_income : []};
window.onload = function () {
    $.ajax({
        url : '/balance/getall',
        cache : false,
        async : false,
        type : 'get',
        dataType : 'json',
        success : function (data) {
            //console.log(data);
            $('.income').children('p').eq(0).children('span').html('¥' + data.expense);
            $('.income').children('p').eq(1).children('span').html('¥' + data.income);
            $('.income').children('p').eq(2).children('span').html('¥' + String(Number(data.income) - Number(data.expense)));
        }
    });

    $.ajax({
        url :  '/purchase/menu',
        cache : false,
        async : false,
        type : 'get',
        dataType : 'json',
        success : function (data) {
            console.log(data.length);
            for(var i = 0; i < data.length; i++) {
                console.log(data[i]);
                //console.log(data[i][menuName]);
                var name_list = data[i].menuName.split('_');
                for(var j = 1; j < name_list.length; j++) {
                    name_list[j] = name_list[j][0].toUpperCase() + name_list[j].substring(1);
                }
                menu_cost[name_list.join('')] = data[i].menuCost;
            }
            console.log(menu_cost);
        }
    });

    $.ajax({
        url : '/indent/getOrder',
        cache : false,
        async : false,
        type : 'get',
        dataType : 'json',
        success : function (data) {
            console.log(data);
            for(var i = 0; i < data.length; i++) {
                console.log(data[i]);
                build_order(data[i]);
            }
            //order_list = data;
           // build_order(data);
        }
    });

    // $.ajax({
    //     url : '/balance/bill',
    //     cache : false,
    //     async : false,
    //     type : 'get',
    //     dataType : 'json',
    //     success : function (data) {
    //         console.log(data);
    //
    //         var expense = [], income = [], net_income = [];
    //         for(var i = 0; i < data.length; i++) {
    //             // console.log(data[i]);
    //             // console.log(data[i].expense + ' ' + data[i].income);
    //             // console.log(data[i]['expense'] + ' ' + data[i]['income']);
    //             bill.expense[i] = Number(data[i].expense);
    //             bill.income[i] = Number(data[i].income);
    //             bill.net_income[i] = Number(data[i].income) - Number(data[i].expense);
    //         }
    //         console.log(bill);
    //         var seriesData = [];
    //         for(var i = 0; i < 3; i++) {
    //             seriesData.push({
    //
    //                 name:'支出',
    //                 type:'bar',
    //                 data:bill.expense,
    //                 markLine : {
    //                     lineStyle: {
    //                         normal: {
    //                             type: 'dashed'
    //                         }
    //                     },
    //                     data : [
    //                         [{type : 'min'}, {type : 'max'}]
    //                     ]
    //                 }
    //
    //             } )
    //         }
    //
    //         echarts.setSeries(seriesData);
    //         // create_bill(bill);
    //     }
    // });
};

function build_order(data) {
    var parent = $('.settle_accounts')[0];
    var order = document.createElement('div');
    var clear_fix = $('.settle_accounts').children('.clearfix')[0];
    order.className = 'order';
    order.id = data.tableNum;

    var number = document.createElement('div');
    number.className = 'number';
    number.innerHTML =  '<p>桌号:<span>' + data.tableNum + '</span></p><p>客人要求:<br><span>' + data.menuRequire + '</span></p>';
    order.appendChild(number);

    var food = document.createElement('div');
    food.className = 'food';
    var food_body = '';
    for(var i in data) {
        if(menu_name.indexOf(i) != -1 && data[i] != '0') {
            food_body = food_body + '<p>' + i + ':<span>* ' + data[i] + '</span>单价:<span>¥' + menu_cost[i] + '</span></p>';
        }
    }
    food_body = food_body + '<div class="result">总价:<span>¥' + data.sum + '</span></div>';
    food.innerHTML = food_body;
    order.appendChild(food);

    var a = document.createElement('a');
    a.className = 'hvr-bounce-to-top btn_1';
    a.innerHTML = '结账';
    order.appendChild(a);

    parent.insertBefore(order, clear_fix);
};

$('.settle_accounts').on('click', '.hvr-bounce-to-top', function () {
    var order = $(this).parent();
    order.remove();
    var table_num = order.children('.number').find('span').eq(0).html();
    var money = order.find('.result').children('span').html().substring(1);
    $.ajax({
        url : '/balance/income',
        data : {Income : money, tableNum : table_num},
        cache : false,
        async : false,
        type : 'get',
        success : function () {}
    });
    alert('成功买单!');
});

$('.check_up_info').find('button').click(function (event) {
    var table_num = $(this).parent().prev().val().trim();
    $(this).parent().attr('href', '#' + table_num);
    var $anchor = $(this).parent();
    $('html, body').stop().animate({
        scrollTop: $($anchor.attr('href')).offset().top
    }, 1000);
    event.preventDefault();
});

// $('.income').find('button').click(function () {
//
// });

// function create_bill(data) {
//     console.log(data);

// }
var bill = {expense : [], income : [], net_income : []};
$.ajax({
    url: '/balance/bill',
    cache: false,
    async: false,
    type: 'get',
    dataType: 'json',
    success: function (data) {
        console.log(data);

        for (var i = 0; i < data.length; i++) {
            bill.expense[i] = Number(data[i].expense);
            bill.income[i] = Number(data[i].income);
            bill.net_income[i] = Number(data[i].income) - Number(data[i].expense);
        }
        console.log('bill ' + bill);
    }

});
var echarts = echarts.init($('#echarts')[0]);
//console.log('asd');
echarts.setOption({
    tooltip : {
        trigger: 'axis',
        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    legend: {
        data:['支出','收入','净收入']
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis : [
        {
            type : 'category',
            data : ['七','六','五','四','三','二','一']
        }
    ],
    yAxis : [
        {
            type : 'value'
        }
    ],
    label: {
        normal: {
            show : true,
            position : 'top'
        }
    },
    series : [
        {
            name:'支出',
            type:'bar',
            data:bill.expense,
            markLine : {
                lineStyle: {
                    normal: {
                        type: 'dashed'
                    }
                },
                data : [
                    [{type : 'min'}, {type : 'max'}]
                ]
            }
        },

        {
            name:'收入',
            type:'bar',
            stack: '广告',
            data:bill.income,
            markLine : {
                lineStyle: {
                    normal: {
                        type: 'dashed'
                    }
                },
                data : [
                    [{type : 'min'}, {type : 'max'}]
                ]
            }
        },

        {
            name:'净收入',
            type:'bar',
            data:bill.net_income,
            markLine : {
                lineStyle: {
                    normal: {
                        type: 'dashed'
                    }
                },
                data : [
                    [{type : 'min'}, {type : 'max'}]
                ]
            }
        }
    ]
});

