// 处理对象及其子集中所有的null、undefined为空
function isNull(obj) {
    for (var k in obj) {
        if (obj[k] instanceof Object) {
            isNull(obj[k]);
        } else {
            if (obj[k] == null || obj[k] == 'null' || obj[k] == undefined || obj[k] == 'undefined') {
                obj[k] = '';
            }
        }
    }
    return obj;
};

// 计算一个对象中有多少个无下级的子集
function checkspans(obj) {
    var span = 0;

    function checkspan(obj) {
        if (obj.sortData && obj.sortData.length > 0) {
            obj.sortData.forEach(function (item) {
                checkspan(item);
            })
        } else {
            span++;
        }
    }
    if (obj.sortData && obj.sortData.length > 0) {
        checkspan(obj);
    };
    // if (span == 0) {
    //     span = 1;
    // } else if (span == 1) {
    //     span = 2;
    // };
    return span;
};

function displayTable(indicators) {
    // 获取到表格数据之后将所有的null、undefined置为空
    indicators = isNull(indicators);

    // 列头部分
    if (indicators.yDataType !== 'tree') {
        indicators.gradedYdata = [];
        // 计算总共有多少列
        var yline = 1;

        // 将列头中所有的层级依次展开
        function ydatagrades(arr) {
            arr.forEach(function (item) {
                function ydatagrade(obj) {
                    indicators.gradedYdata.push([{
                        name: obj.name,
                        style: obj.style ? obj.style : '',
                        id: obj.id,
                        rowspan: 1,
                        colspan: 1,
                        whetherData: obj.whetherData,
                        dataType: "dataY"
                    }]);
                    if (obj.sortData && obj.sortData.length > 0) {
                        ydatagrades(obj.sortData);
                    }
                };
                ydatagrade(item);
            });
        }

        ydatagrades(indicators.ydata);
    } else {
        // 表格主体部分
        var ygrade = 0;
        indicators.gradedYdata = {};
        indicators.gradedYdata.grades = [];

        // 将表头数据按层级列出来
        // var yTempDataId = 0;
        // function yoneToTwo(arr) {
        //     arr.forEach(function (item) {
        //         if (item.sortData && item.sortData.length == 1) {
        //             item.sortData.push({
        //                 name: 'needtodelete1',
        //                 style: '',
        //                 rowspan: 1,
        //                 id: 'yTempDataId' + (yTempDataId++),
        //                 whetherData: 1
        //             })
        //         };
        //         if (item.sortData && item.sortData.length > 0) {
        //             yoneToTwo(item.sortData);
        //         }
        //     });
        // };
        // yoneToTwo(indicators.ydata);
        function ydatagrade(arr) {
            if (arr && arr.length > 0) {
                var tempArr = [];
                indicators.gradedYdata['grade' + ygrade] = [];
                arr.forEach(function (item) {
                    var rowspan = checkspans(item);
                    indicators.gradedYdata['grade' + ygrade].push({
                        name: item.name,
                        style: item.style ? item.style : '',
                        rowspan: rowspan,
                        id: item.id,
                        whetherData: item.whetherData,
                        dataType: "dataY"
                    });
                    if (rowspan > 0) {
                        for (var i = 1; i < rowspan; i++) {
                            indicators.gradedYdata['grade' + ygrade].push({ name: 'needtodelete' });
                        }
                    } else if (rowspan == 0) {
                        tempArr.push({ name: 'needtodelete' });
                    }

                    if (item.sortData && item.sortData.length > 0) {
                        tempArr = tempArr.concat(item.sortData);
                    }
                });
                indicators.gradedYdata.grades.push(indicators.gradedYdata['grade' + ygrade]);
                if (tempArr.length > 0) {
                    var flag = tempArr.every(function (item) {
                        if (item.name == 'needtodelete') {
                            return true;
                        } else {
                            return false;
                        }
                    })
                    if (!flag) {
                        ygrade++;
                        ydatagrade(tempArr);
                    }
                }
            }
        };
        ydatagrade(indicators.ydata);
        indicators.gradedYdata = indicators.gradedYdata.grades;

        // 计算总共有多少列
        var yline = indicators.gradedYdata.length;
        // 给每一列的数据打上标记，属于第几列
        indicators.gradedYdata.forEach(function (item, index) {
            var tempindex = index;
            item.forEach(function (item) {
                item.index = tempindex;
            })
        })
        // 把每一列的数据拼成一行一行的
        indicators.gradedYdata1 = [];
        for (var i = 0; i < indicators.gradedYdata[0].length; i++) {
            var tempArr = [];
            indicators.gradedYdata.forEach(function (item) {
                tempArr.push(item[i]);
            });
            indicators.gradedYdata1.push(tempArr);
        }
        indicators.gradedYdata = indicators.gradedYdata1;
        indicators.gradedYdata1 = [];
        // 将之前放置的needtodelete删掉
        indicators.gradedYdata.forEach(function (item, index) {
            var itemtemp = item, indextemp = index;
            var temparr = [];
            item.forEach(function (item) {
                if (item.name != 'needtodelete') {
                    // if (item.name == 'needtodelete1') {
                    //     item.name = '空白行';
                    //     item.style = 'font-size: 12px;text-align:center;';
                    // }
                    temparr.push(item);
                }
            });
            indicators.gradedYdata1.push(temparr);
        });
        indicators.gradedYdata = indicators.gradedYdata1;
        indicators.gradedYdata1 = null;
        // 根据每一格的rowspan给每一行设置colspan
        indicators.gradedYdata.forEach(function (item) {
            item.forEach(function (item) {
                if (item.rowspan > 0) {
                    item.colspan = 1;
                } else {
                    item.rowspan = 1;
                    item.colspan = yline - item.index;
                }
            })
        });
    }
    // 在拼接数据之前把需要合并单元格的行设置好
    indicators.gradedYdata.forEach(function (item) {
        if (!item[item.length - 1].whetherData) {
            var dataNum = 0;
            indicators.xdata.forEach(function (item) {
                var cols = checkspans(item) == 0 ? 1 : checkspans(item);
                dataNum += cols;
            });
            item[item.length - 1].colspan += (dataNum - 1);
        }
    });
    // 将列头与data数据拼接起来组成一行
    var datatotalcols = 0;
    indicators.gradedYdata.forEach(function (item) {
        var tempitem = item;
        indicators.data.forEach(function (item) {
            if (item.id == tempitem[tempitem.length - 1].id && tempitem[tempitem.length - 1].whetherData) {
                for (var i = 0; i >= 0; i++) {
                    if (('data' + i) in item) {
                        tempitem.push({
                            name: item['data' + i],
                            style: item['style' + i] ? item['style' + i] : '',
                            rowspan: 1,
                            colspan: 1,
                            id: item.id,
                            code: item['code' + i],
                            earlyWarnings: item['earlyWarnings'+i]?item['earlyWarnings'+i]:"",
                            dataType: "data"
                        });
                    } else {
                        if (datatotalcols == 0) {
                            datatotalcols = i;
                        }
                        break;
                    }
                }
            };
        });
    });
    datatotalcols += yline;

    // 表头部分
    var xgrade = 0;
    indicators.gradedXdata = {};
    indicators.gradedXdata.grades = [];
    // 如果第一个表头不是开头的数据，则找到开头并把它拎到开头
    if (indicators.xdata[0].dhead != 1) {
        var dheadindex;
        indicators.xdata.forEach(function (item, index) {
            if (item.dhead == 1) {
                dheadindex = index;
            }
        });
        if (dheadindex) {
            var tempitem = indicators.xdata[dheadindex];
            indicators.xdata.splice(dheadindex, 1);
            indicators.xdata.unshift(tempitem);
        } else {
            var dheadindex;
            indicators.xdata.forEach(function (item, index) {
                if (item.sortData && item.sortData.length == 1 && item.sortData[0].dhead == 1) {
                    dheadindex = index;
                    item.noaddson = 1;
                    item.sortData[0].noaddson1 = 1;
                }
            });
            if (dheadindex) {
                var tempitem = indicators.xdata[dheadindex];
                indicators.xdata.splice(dheadindex, 1);
                indicators.xdata.unshift(tempitem);
            }
        }
    };
    // 将表头数据按层级列出来，所有的同一级表头全部放到同一个对象中
    // function xoneToTwo(arr) {
    //     arr.forEach(function (item) {
    //         if (item.sortData && item.sortData.length == 1) {
    //             item.sortData.push({
    //                 name: '-',
    //                 style: 'font-size: 12px;text-align: center;',
    //                 colspan: 1
    //             })
    //         };
    //         if (item.sortData && item.sortData.length > 0) {
    //             xoneToTwo(item.sortData);
    //         }
    //     });
    // };
    // xoneToTwo(indicators.xdata);
    function xdatagrade(arr) {
        if (arr && arr.length > 0) {
            var tempArr = [];
            indicators.gradedXdata['grade' + xgrade] = [];
            arr.forEach(function (item) {
                var colspan = checkspans(item);
                // if (item.noaddson && colspan == 2) {
                //   colspan = 1;
                // }
                indicators.gradedXdata['grade' + xgrade].push({
                    name: item.name,
                    style: item.style ? item.style : '',
                    colspan: colspan,
                    noaddson: item.noaddson,
                    noaddson1: item.noaddson1
                });
                if (item.sortData && item.sortData.length > 0) {
                    tempArr = tempArr.concat(item.sortData);
                }
            });
            indicators.gradedXdata.grades.push(indicators.gradedXdata['grade' + xgrade]);
            if (tempArr.length > 0) {
                xgrade++;
                xdatagrade(tempArr);
            }
        }
    };
    xdatagrade(indicators.xdata);
    indicators.gradedXdata = indicators.gradedXdata.grades;
    // 设置表头每个th单元格的rowspan和colspan
    indicators.gradedXdata.forEach(function (item, index, arr) {
        var tempindex = index;
        var temparr = arr;
        item.forEach(function (item) {
            if (item.colspan == 0 && !item.noaddson) {
                item.colspan = 1;
                item.rowspan = temparr.length - tempindex;
            } else if (item.colspan > 0 && !item.noaddson) {
                item.rowspan = 1;
            } else if (item.noaddson) {
                item.rowspan = 1;
            }
        })
    });
    for (var i = 0; i < indicators.gradedXdata.length; i++) {
        indicators.gradedXdata[i][0].colspan += (yline - 1);
        if (indicators.gradedXdata[i][0].rowspan > 1) {
            break;
        }
    }

    // 先将之前生成的表格数据清楚
    $(".tableDisplay table").remove();
    var table = '<table>' +
        '<caption></caption>' +
        '<thead></thead>' +
        '<tbody></tbody>' +
        '<tfoot></tfoot>' +
        '</table>';
    $(".tableDisplay").append(table);
    // 标题部分
    if (indicators.title.name) {
        $(".tableDisplay table caption").html(indicators.title.name);
        $(".tableDisplay table caption").attr('style', indicators.title.style);
    } else {
        $(".tableDisplay table caption").html(indicators.title);
    }

    // 备注部分
    if (indicators.reminderBottom && indicators.reminderBottom.name) {
        $(".tableDisplay table tfoot").html('<tr><td>'+indicators.reminderBottom.name+'</td></tr>');
        $(".tableDisplay table tfoot tr td").attr('style', indicators.reminderBottom.style).attr('colspan', datatotalcols)
    }else if(indicators.reminderBottom && !indicators.reminderBottom.name){
        $(".tableDisplay table tfoot").html('<tr><td></td></tr>');
        $(".tableDisplay table tfoot tr td").attr('colspan', datatotalcols);
    }

    // 将表头的数据展示出来
    indicators.gradedXdata.forEach(function (item) {
        $(".tableDisplay table thead").append("<tr></tr>");
        item.forEach(function (item) {
            $(".tableDisplay table thead tr:last").append('<th placeholder="横向表头" style="' + item.style + '" rowspan="' + item.rowspan + '" colspan=' + item.colspan + ' mark="dataX" >' + item.name + '</th>');
            if (item.noaddson1) {
                $(".tableDisplay table thead th:last").css({ 'text-align': 'left', 'border-top': 'none', 'position': 'relative', 'padding': '0' }).addClass('SlantlineParent').append('<div class="Slantline"></div>');
            };
            if (item.noaddson) {
                $(".tableDisplay table thead th:last").css({ 'text-align': 'right', 'padding': '0' }).addClass('SlantlineParentTop');
            }
        });
    });
    setTimeout(function () {
        $(".Slantline").css({
            width: Math.sqrt(Math.pow($(".SlantlineParent").outerWidth(), 2) + Math.pow($(".SlantlineParent").outerHeight() + $(".SlantlineParentTop").outerHeight(), 2)),
            height: '2px',
            background: '#000',
            position: 'absolute',
            right: '-1px',
            bottom: '-2px',
            "transform-origin": "right bottom",
            transform: 'rotate(' + Math.atan(($(".SlantlineParent").outerHeight() + $(".SlantlineParentTop").outerHeight()) / $(".SlantlineParent").outerWidth()) * 180 / Math.PI + 'deg)'
        });
    });

    indicators.gradedYdata.forEach(function (item,index) {
        $('.tableDisplay table tbody').append('<tr></tr>');
        item.forEach(function (item, index1, arr) {
            var warningClass = '';
            if(item.earlyWarnings && (item.earlyWarnings*1) > (item.name*1)){
                warningClass = 'green';
            }else if(item.earlyWarnings && (item.earlyWarnings*1) < (item.name*1)){
                warningClass = 'red';
            }
            $(".tableDisplay table tbody tr:last").append('<td data-earlywarning="'+item.earlyWarnings+'" class="'+warningClass+'" placeholder="无" data-code="' + (!item.code ? "" : item.code) + '" style="' + item.style + '" rowspan="' + item.rowspan + '" colspan=' + item.colspan + '" mark="'+item.dataType+'" y="'+index+'" x="'+(index1-1)+'">' + item.name + '</td>');
        });
    });
    // $('table tbody tr').each(function () {
    //     $(this).children("td:first").attr("placeholder", "纵向表头");
    // });
    var indexOf = -1;//纵向表头一列为-1，两列为-2，以此类推  --用于横向表头添加和删除
    function retrievalSon(arr) {
        arr.forEach(function (item) {
            if(item.sortData == undefined || item.sortData.length == 0){
                item.index = indexOf; //给最底层横向表头标识
                indexOf++;
            }
            if (item.sortData && item.sortData.length > 0) {
                if("index" in item){delete item.index;}//存在属性就把它移除
                retrievalSon(item.sortData);
            }
        });
    }
    retrievalSon(indicators.xdata);
    window["jsonHtml"] = indicators;
};

// 向后台查询报表数据，并展示，获取到的数据格式见 ./json/table.json
function getTableData(apiPath,reportId,year,month,operationType){
    $.ajax({
        url: apiPath+'table/viewTemplateCDSInd?templateId='+reportId+'&year='+year+'&month='+month+'&operationType='+operationType,
        dataType: 'json',
        success: function (res) {
            var indicators = res.data.data;
            displayTable(indicators);
            $(".deityTitle input").val($("body caption").text());
            $(".deitySize.titleFz input").val(parseFloat($("body caption").css('font-size') || '24px'));
            $(".deitySize.xFz input").val(parseFloat($("body thead tr th").eq(0).css('font-size') || '12px'));
            $(".deitySize.yFz input").val(parseFloat($("body tbody tr td").eq(0).css('font-size') || '12px'));
            $(".deitySize.dFz input").val(parseFloat($("body tbody tr td:last").css('font-size') || '12px'));
            $(".deitySize.rFz input").val(parseFloat($("body tfoot tr td").css('font-size') || '12px'));
            $(".deityReminder input").val( $(".tableDisplay table tfoot td").text());
          //  $(".deitySize input").val(parseFloat( $(".tableDisplay table tfoot td").css("font-size")));

        }
    })
}