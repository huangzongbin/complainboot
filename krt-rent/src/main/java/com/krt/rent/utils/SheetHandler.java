package com.krt.rent.utils;
/**
 * Copyright 2013-2015 JueYue (qrb.jueyue@gmail.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import cn.afterturn.easypoi.excel.entity.enmus.CellValueType;
import cn.afterturn.easypoi.excel.entity.sax.SaxReadCellEntity;
import cn.afterturn.easypoi.excel.imports.sax.parse.ISaxRowRead;
import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.List;

/**
 * 回调接口
 * @author JueYue
 *  2014年12月29日 下午9:50:09
 */
public class SheetHandler extends DefaultHandler {
    private StylesTable stylesTable;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private ReadOnlySharedStringsTable strings;
    private String                  lastContents;

    //当前行
    private int                     curRow  = 0;
    //当前列
    private int                     curCol  = 0;

    private CellValueType type;

    private ISaxRowRead read;

    //存储行记录的容器
    private List<SaxReadCellEntity> rowlist = Lists.newArrayList();

    public SheetHandler(ReadOnlySharedStringsTable strings, ISaxRowRead rowRead,StylesTable stylesTable) {
        this.strings = strings;
        this.read = rowRead;
        this.stylesTable=stylesTable;
    }

    private boolean nextIsString;


    //定义前一个元素和当前元素的位置，用来计算其中空的单元格数量，如A6和A8等
    private String preRef = null, ref = null;
    //定义该文档一行最大的单元格数，用来补全一行最后可能缺失的单元格
    private String maxRef = null;

    private CellDataType nextDataType = CellDataType.SSTINDEX;
    private final DataFormatter formatter = new DataFormatter();
    private short formatIndex;
    private String formatString;

    //用一个enum表示单元格可能的数据类型
    enum CellDataType{
        BOOL, ERROR, FORMULA, INLINESTR, SSTINDEX, NUMBER, DATE, NULL
    }

    private SheetHandler(ReadOnlySharedStringsTable strings) {
        this.strings = strings;
    }

    /**
     * 解析一个element的开始时触发事件
     */
    public void startElement(String uri, String localName, String name,
                             Attributes attributes) throws SAXException {

        // c => cell  c代表单元格
        if(name.equals("c")) {
            //前一个单元格的位置
            if(preRef == null){
                preRef = attributes.getValue("r");
            }
            //当前单元格的位置
            ref = attributes.getValue("r");

            this.setNextDataType(attributes);

            // Figure out if the value is an index in the SST
            String cellType = attributes.getValue("t");
            if(cellType != null && cellType.equals("s")) {
                nextIsString = true;
            } else {
                nextIsString = false;
            }

        }
        // Clear contents cache
        lastContents = "";
    }

    /**
     * 根据element属性设置数据类型
     * @param attributes
     */
    public void setNextDataType(Attributes attributes){

        nextDataType = CellDataType.NUMBER;
        formatIndex = -1;
        formatString = null;
        String cellType = attributes.getValue("t");
        String cellStyleStr = attributes.getValue("s");
        if ("b".equals(cellType)){
            nextDataType = CellDataType.BOOL;
        }else if ("e".equals(cellType)){
            nextDataType = CellDataType.ERROR;
        }else if ("inlineStr".equals(cellType)){
            nextDataType = CellDataType.INLINESTR;
        }else if ("s".equals(cellType)){
            nextDataType = CellDataType.SSTINDEX;
        }else if ("str".equals(cellType)){
            nextDataType = CellDataType.FORMULA;
        }
        if (cellStyleStr != null){
            int styleIndex = Integer.parseInt(cellStyleStr);
            XSSFCellStyle style = stylesTable.getStyleAt(styleIndex);
            formatIndex = style.getDataFormat();
            formatString = style.getDataFormatString();
            if ("m/d/yy" == formatString){
                nextDataType = CellDataType.DATE;
                //full format is "yyyy-MM-dd hh:mm:ss.SSS";
                formatString = "yyyy-MM-dd";
            }
            if (formatString == null){
                nextDataType = CellDataType.NULL;
                formatString = BuiltinFormats.getBuiltinFormat(formatIndex);
            }
        }
    }
    /**
     * @param name  excel是一个xml,name代表在xml中其属性名称  如 sheet,row,单元格(c),单元格值(v) 等
     *
     * 解析一个element元素结束时触发事件
     */
    public void endElement(String uri, String localName, String name)throws SAXException {
        // Process the last contents as required.
        // Do now, as characters() may be called more than once
        if(nextIsString) {
            int idx = Integer.parseInt(lastContents);
            lastContents = new XSSFRichTextString(strings.getEntryAt(idx)).toString();
            nextIsString = false;
        }
//        logger.info("endElement-ref:{},preRef:{},name:{},type:{},lastContents:{},curRow:{},curCol:{},",
//                new Object[]{ref,preRef,name,type,lastContents,curRow,curCol});

        // v => contents of a cell
        // Output after we've seen the string contents
        if (name.equals("v")) {
            String value = this.getDataValue(lastContents.trim(), "");
            //补全单元格之间的空单元格
            if(!ref.equals(preRef)){
                int len = countNullCell(ref, preRef);
                for(int i=0;i<len;i++){
                    rowlist.add(curCol, new SaxReadCellEntity(CellValueType.String, ""));
                    curCol++;
                }
            }
            rowlist.add(curCol, new SaxReadCellEntity(CellValueType.String, value));
            preRef= ref;
            curCol++;
        }else {
            //如果标签名称为 row，这说明已到行尾，调用 optRows() 方法
            if (name.equals("row")) {
                String value = "";
                //默认第一行为表头，以该行单元格数目为最大数目
                if(curRow == 0){
                    maxRef = ref;
                }
                //补全一行尾部可能缺失的单元格
                if(maxRef != null){
                    int len = countNullCell(maxRef, ref);
                    for(int i=0;i<=len;i++){
                        rowlist.add(curCol, new SaxReadCellEntity(CellValueType.String, value));
                        curCol++;
                    }
                }
                read.parse(curRow, rowlist);
                curRow++;
                //一行的末尾重置一些数据
                rowlist.clear();
                curCol = 0;
                preRef = null;
                ref = null;
            }
        }
    }

    /**
     * 根据数据类型获取数据
     * @param value
     * @param thisStr
     * @return
     */
    public String getDataValue(String value, String thisStr)

    {
        switch (nextDataType)
        {
            //这几个的顺序不能随便交换，交换了很可能会导致数据错误
            case BOOL:
                char first = value.charAt(0);
                thisStr = first == '0' ? "FALSE" : "TRUE";
                break;
            case ERROR:
                thisStr = "\"ERROR:" + value.toString() + '"';
                break;
            case FORMULA:
                thisStr = '"' + value.toString() + '"';
                break;
            case INLINESTR:
                XSSFRichTextString rtsi = new XSSFRichTextString(value.toString());
                thisStr = rtsi.toString();
                rtsi = null;
                break;
            case SSTINDEX:
                String sstIndex = value.toString();
                thisStr = value.toString();
                break;
            case NUMBER:
                if (formatString != null){
                    thisStr = formatter.formatRawCellContents(Double.parseDouble(value), formatIndex, formatString).trim();
                }else{
                    thisStr = value;
                }
                thisStr = thisStr.replace("_", "").trim();
                break;
            case DATE:
                try{
                    thisStr = formatter.formatRawCellContents(Double.parseDouble(value), formatIndex, formatString);
                }catch(NumberFormatException ex){
                    thisStr = value.toString();
                }
                thisStr = thisStr.replace(" ", "");
                break;
            default:
                thisStr = "";
                break;
        }
        return thisStr;
    }

    /**
     * 计算两个单元格之间的单元格数目(同一行)
     * @param ref
     * @param preRef
     * @return
     */
    public int countNullCell(String ref, String preRef){
        //excel2007最大行数是1048576，最大列数是16384，最后一列列名是XFD
        String xfd = ref.replaceAll("\\d+", "");
        String xfd_1 = preRef.replaceAll("\\d+", "");

        xfd = fillChar(xfd, 3, '@', true);
        xfd_1 = fillChar(xfd_1, 3, '@', true);

        char[] letter = xfd.toCharArray();
        char[] letter_1 = xfd_1.toCharArray();
        int res = (letter[0]-letter_1[0])*26*26 + (letter[1]-letter_1[1])*26 + (letter[2]-letter_1[2]);
        return res-1;
    }

    /**
     * 字符串的填充
     * @param str
     * @param len
     * @param let
     * @param isPre
     * @return
     */
    String fillChar(String str, int len, char let, boolean isPre){
        int len_1 = str.length();
        if(len_1 <len){
            if(isPre){
                for(int i=0;i<(len-len_1);i++){
                    str = let+str;
                }
            }else{
                for(int i=0;i<(len-len_1);i++){
                    str = str+let;
                }
            }
        }
        return str;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        //得到单元格内容的值
        lastContents += new String(ch, start, length);
    }

}
