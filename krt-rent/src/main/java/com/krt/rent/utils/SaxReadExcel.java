package com.krt.rent.utils;

import cn.afterturn.easypoi.excel.entity.ImportParams;
//import cn.afterturn.easypoi.excel.imports.sax.SheetHandler;
import cn.afterturn.easypoi.excel.imports.sax.parse.ISaxRowRead;
import cn.afterturn.easypoi.excel.imports.sax.parse.SaxRowRead;
import cn.afterturn.easypoi.exception.excel.ExcelImportException;
import cn.afterturn.easypoi.handler.inter.IExcelReadRowHanlder;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.imports.sax.parse.ISaxRowRead;
import cn.afterturn.easypoi.excel.imports.sax.parse.SaxRowRead;
import cn.afterturn.easypoi.exception.excel.ExcelImportException;
import cn.afterturn.easypoi.handler.inter.IExcelReadRowHanlder;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SaxReadExcel {
    private static final Logger LOGGER = LoggerFactory.getLogger(cn.afterturn.easypoi.excel.imports.sax.SaxReadExcel.class);

    public SaxReadExcel() {
    }

    public <T> List<T> readExcel(InputStream inputstream, Class<?> pojoClass, ImportParams params, ISaxRowRead rowRead, IExcelReadRowHanlder hanlder) {
        try {
            OPCPackage opcPackage = OPCPackage.open(inputstream);
            return this.readExcel(opcPackage, pojoClass, params, rowRead, hanlder);
        } catch (Exception var7) {
            LOGGER.error(var7.getMessage(), var7);
            throw new ExcelImportException(var7.getMessage());
        }
    }


    private <T> List<T> readExcel(OPCPackage opcPackage, Class<?> pojoClass, ImportParams params,
                                  ISaxRowRead rowRead, IExcelReadRowHanlder hanlder) {
        try {
            XSSFReader xssfReader = new XSSFReader(opcPackage);
            if (rowRead == null) {
                rowRead = new SaxRowRead(pojoClass, params, hanlder);
            }
            ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(opcPackage);
            StylesTable styles = xssfReader.getStylesTable();
            XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
            int index = 0;
            while (iter.hasNext()) {
                InputStream stream = iter.next();
                String sheetNameTemp = iter.getSheetName();
//                if (this.sheetName.equals(sheetNameTemp)) {
                processSheet(styles, strings, stream,rowRead);
                stream.close();
                ++index;
//                }
            }
            return rowRead.getList();


//            System.out.println(1);
//            SharedStringsTable sst = xssfReader.getSharedStringsTable();
//            System.out.println(2);
//            if (rowRead == null) {
//                rowRead = new SaxRowRead(pojoClass, params, hanlder);
//                System.out.println(3);
//            }
//            XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
//            System.out.println(4);
//            ContentHandler handler = new SheetHandler(sst, rowRead,xssfReader.getStylesTable());
//            System.out.println(5);
//            parser.setContentHandler(handler);
//            Iterator<InputStream> sheets = xssfReader.getSheetsData();
//            int sheetIndex = 0;
//            while (sheets.hasNext() && sheetIndex < params.getSheetNum()) {
//                sheetIndex++;
//                InputStream sheet = sheets.next();
//                InputSource sheetSource = new InputSource(sheet);
//                parser.parse(sheetSource);
//                sheet.close();
//            }
//            return rowRead.getList();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ExcelImportException("SAX导入数据失败");
        }
    }

    /**
     * Parses and shows the content of one sheet using the specified styles and
     * shared-strings tables.
     *
     * @param styles
     * @param strings
     * @param sheetInputStream
     */
    public void  processSheet(StylesTable styles,
                              ReadOnlySharedStringsTable strings, InputStream sheetInputStream,ISaxRowRead rowRead)
            throws IOException, ParserConfigurationException, SAXException {

        InputSource sheetSource = new InputSource(sheetInputStream);
        SAXParserFactory saxFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxFactory.newSAXParser();
        XMLReader sheetParser = saxParser.getXMLReader();
//        XLSXCovertCSVReader.MyXSSFSheetHandler handler =
// new XLSXCovertCSVReader.MyXSSFSheetHandler(styles, strings,this.minColumns, this.output);

        ContentHandler handler = new SheetHandler(strings, rowRead,styles);
        sheetParser.setContentHandler(handler);
        sheetParser.parse(sheetSource);
    }
//    private <T> List<T> readExcel(OPCPackage opcPackage, Class<?> pojoClass, ImportParams params, ISaxRowRead rowRead, IExcelReadRowHanlder hanlder) {
//        try {
//            XSSFReader xssfReader = new XSSFReader(opcPackage);
//            SharedStringsTable sst = xssfReader.getSharedStringsTable();
//            if (rowRead == null) {
//                rowRead = new SaxRowRead(pojoClass, params, hanlder);
//            }
//
//            XMLReader parser = this.fetchSheetParser(sst, (ISaxRowRead)rowRead);
//            Iterator<InputStream> sheets = xssfReader.getSheetsData();
//            int sheetIndex = 0;
//
//            while(sheets.hasNext() && sheetIndex < params.getSheetNum()) {
//                ++sheetIndex;
//                InputStream sheet = (InputStream)sheets.next();
//                InputSource sheetSource = new InputSource(sheet);
//                parser.parse(sheetSource);
//                sheet.close();
//            }
//
//            return ((ISaxRowRead)rowRead).getList();
//        } catch (Exception var13) {
//            LOGGER.error(var13.getMessage(), var13);
//            throw new ExcelImportException("SAX导入数据失败");
//        }
//    }
//
//    /**
//     *  修改了直接使用 SAXParser 会报错问题
//     */
//    private XMLReader fetchSheetParser(SharedStringsTable sst, ISaxRowRead rowRead) throws SAXException, ParserConfigurationException {
//        SAXParserFactory m_parserFactory = null;
//        m_parserFactory = SAXParserFactory.newInstance();
//        m_parserFactory.setNamespaceAware(true);
//        XMLReader parser = m_parserFactory.newSAXParser().getXMLReader();
////        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
//        ContentHandler handler = new SheetHandler(sst, rowRead);
//        parser.setContentHandler(handler);
//        return parser;
//    }
}

