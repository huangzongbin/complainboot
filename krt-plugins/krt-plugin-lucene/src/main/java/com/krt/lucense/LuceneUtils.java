package com.krt.lucense;

import com.krt.common.util.HtmlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;

/**
 * lucene工具类
 *
 * @author 殷帅
 * @version 1.0
 * @date 2016年11月24日
 */
@Slf4j
public class LuceneUtils {

    private static final LuceneManager LUCENE_MANAGER = LuceneManager.getInstance();

    /**
     * 打开索引目录
     *
     * @param luceneDir 索引文件路径
     * @return 索引文件路径
     */
    public static FSDirectory openFSDirectory(String luceneDir) {
        FSDirectory directory = null;
        try {
            directory = FSDirectory.open(Paths.get(luceneDir));
            //注意：isLocked方法内部会试图去获取Lock,如果获取到Lock，会关闭它，否则return
            //false表示索引目录没有被锁， 这也就是为什么unlock方法被从IndexWriter类中移除的原因
            IndexWriter.isLocked(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return directory;
    }

    /**
     * 关闭索引目录并销毁
     *
     * @param directory 索引文件路径
     */
    public static void closeDirectory(Directory directory) throws IOException {
        if (null != directory) {
            directory.close();
        }
    }

    /**
     * 获取IndexWriter
     *
     * @param dir    索引文件路径
     * @param config 配置
     * @return 返回IndexWriter
     */
    public static IndexWriter getIndexWritier(Directory dir, IndexWriterConfig config) {
        return LUCENE_MANAGER.getIndexWriter(dir, config);
    }

    /**
     * 获取IndexReader
     *
     * @param dir             索引文件路径
     * @param enableNRTReader 是否开启NRTReader
     * @return 返回IndexReader
     */
    public static IndexReader getIndexReader(Directory dir, boolean enableNRTReader) {
        return LUCENE_MANAGER.getIndexReader(dir, enableNRTReader);
    }

    /**
     * 获取IndexSearcher
     *
     * @param reader   IndexReader对象
     * @param executor 如果你需要开启多线程查询，请提供ExecutorService对象参数
     * @return 返回IndexSearcher
     */
    public static IndexSearcher getIndexSearcher(IndexReader reader, ExecutorService executor) {
        return LUCENE_MANAGER.getIndexSearcher(reader, executor);
    }

    /**
     * 获取IndexSearcher(不支持多线程查询)
     *
     * @param reader IndexReader对象
     * @return 返回IndexSearcher
     */
    public static IndexSearcher getIndexSearcher(IndexReader reader) {
        return LUCENE_MANAGER.getIndexSearcher(reader);
    }

    /**
     * 创建QueryParser对象
     *
     * @param field    字段名
     * @param analyzer 分析器
     * @return 返回QueryParser
     */
    public static QueryParser createQueryParser(String field, Analyzer analyzer) {
        return new QueryParser(field, analyzer);
    }

    /**
     * 关闭IndexReader
     *
     * @param reader 索引对象
     */
    public static void closeIndexReader(IndexReader reader) {
        if (null != reader) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭IndexWriter
     *
     * @param writer 写索引的对象
     */
    public static void closeIndexWriter(IndexWriter writer) {
        if (null != writer) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭IndexReader和IndexWriter
     *
     * @param reader 读索引的对象
     * @param writer 写索引的对象
     */
    public static void closeAll(IndexReader reader, IndexWriter writer) {
        closeIndexReader(reader);
        closeIndexWriter(writer);
    }

    /**
     * 删除索引[注意：请自己关闭IndexWriter对象]
     *
     * @param writer 写索引的对象
     * @param field  字段
     * @param value  字段值
     */
    public static void deleteIndex(IndexWriter writer, String field, String value) {
        try {
            writer.deleteDocuments(new Term[]{new Term(field, value)});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除索引[注意：请自己关闭IndexWriter对象]
     *
     * @param writer 写索引的对象
     * @param query  查询对象
     */
    public static void deleteIndex(IndexWriter writer, Query query) {
        try {
            writer.deleteDocuments(query);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量删除索引[注意：请自己关闭IndexWriter对象]
     *
     * @param writer 写索引的对象
     * @param terms  索引
     */
    public static void deleteIndexs(IndexWriter writer, Term[] terms) {
        try {
            writer.deleteDocuments(terms);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量删除索引[注意：请自己关闭IndexWriter对象]
     *
     * @param writer 写索引的对象
     * @param querys 查询索引
     */
    public static void deleteIndexs(IndexWriter writer, Query[] querys) {
        try {
            writer.deleteDocuments(querys);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除所有索引文档
     *
     * @param writer 写索引的对象
     */
    public static void deleteAllIndex(IndexWriter writer) {
        try {
            if (writer != null && writer.isOpen()) {
                writer.deleteAll();
                writer.commit();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 更新索引文档
     *
     * @param writer   写索引的对象
     * @param term     字段
     * @param document 文档
     */
    public static void updateIndex(IndexWriter writer, Term term, Document document) {
        try {
            writer.updateDocument(term, document);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新索引文档
     *
     * @param writer   写索引的对象
     * @param field    字段名
     * @param value    字段值
     * @param document 文档
     */
    public static void updateIndex(IndexWriter writer, String field, String value, Document document) {
        updateIndex(writer, new Term(field, value), document);
    }

    /**
     * 添加索引文档
     *
     * @param writer   写索引的对象
     * @param document 文档
     */
    public static void addIndex(IndexWriter writer, Document document) {
        updateIndex(writer, null, document);
    }

    /**
     * 索引文档查询
     *
     * @param searcher 查询对象
     * @param query    查询
     * @return 文本的列表
     */
    public static List<Document> query(IndexSearcher searcher, Query query) {
        TopDocs topDocs = null;
        try {
            topDocs = searcher.search(query, Integer.MAX_VALUE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ScoreDoc[] scores = topDocs.scoreDocs;
        int length = scores.length;
        if (length <= 0) {
            return Collections.emptyList();
        }
        List<Document> docList = new ArrayList<Document>();
        try {
            for (int i = 0; i < length; i++) {
                Document doc = searcher.doc(scores[i].doc);
                docList.add(doc);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return docList;
    }

    /**
     * 索引文档查询
     *
     * @param searcher 查询
     * @param query    查询
     * @return 文本的列表
     */
    public static List<Document> query(IndexSearcher searcher, Query query, Sort sort) {
        TopDocs topDocs = null;
        try {
            topDocs = searcher.search(query, Integer.MAX_VALUE, sort);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ScoreDoc[] scores = topDocs.scoreDocs;
        int length = scores.length;
        if (length <= 0) {
            return Collections.emptyList();
        }
        List<Document> docList = new ArrayList<Document>();
        try {
            for (int i = 0; i < length; i++) {
                Document doc = searcher.doc(scores[i].doc);
                docList.add(doc);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return docList;
    }

    /**
     * 返回索引文档的总数[注意：请自己手动关闭IndexReader]
     *
     * @param reader 读索引的对象
     * @return 总条数
     */
    public static int getIndexTotalCount(IndexReader reader) {
        return reader.numDocs();
    }

    /**
     * 返回索引文档中最大文档ID[注意：请自己手动关闭IndexReader]
     *
     * @param reader 读索引的对象
     * @return 最大文本的id
     */
    public static int getMaxDocId(IndexReader reader) {
        return reader.maxDoc();
    }

    /**
     * 返回已经删除尚未提交的文档总数[注意：请自己手动关闭IndexReader]
     *
     * @param reader 读索引的对象
     * @return 总共文本的数量
     */
    public static int getDeletedDocNum(IndexReader reader) {
        return getMaxDocId(reader) - getIndexTotalCount(reader);
    }

    /**
     * 根据docId查询索引文档
     *
     * @param reader       IndexReader对象
     * @param docID        documentId
     * @param fieldsToLoad 需要返回的field
     * @return 指定文本
     */
    public static Document findDocumentByDocId(IndexReader reader, int docID, Set<String> fieldsToLoad) {
        try {
            return reader.document(docID, fieldsToLoad);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 根据docId查询索引文档
     *
     * @param reader IndexReader对象
     * @param docID  documentId 文本id
     * @return 文本对象
     */
    public static Document findDocumentByDocId(IndexReader reader, int docID) {
        return findDocumentByDocId(reader, docID, null);
    }

    /**
     * 创建高亮器 createHighlighter
     *
     * @param query            索引查询对象
     * @param prefix           高亮前缀字符串
     * @param stuffix          高亮后缀字符串
     * @param fragmenterLength 摘要最大长度
     * @return 高亮
     */
    public static Highlighter createHighlighter(Query query, String prefix, String stuffix, int fragmenterLength) {
        Formatter formatter = new SimpleHTMLFormatter((prefix == null || prefix.trim().length() == 0) ? "<font color=\"red\">" : prefix, (stuffix == null || stuffix.trim().length() == 0) ? "</font>"
                : stuffix);
        Scorer fragmentScorer = new QueryScorer(query);
        Highlighter highlighter = new Highlighter(formatter, fragmentScorer);
        Fragmenter fragmenter = new SimpleFragmenter(fragmenterLength <= 0 ? 50 : fragmenterLength);
        highlighter.setTextFragmenter(fragmenter);
        return highlighter;
    }

    /**
     * 生成高亮文本
     *
     * @param document    索引文档对象
     * @param highlighter 高亮器
     * @param analyzer    索引分词器
     * @param field       高亮字段
     * @return 高亮字符串
     */
    public static String highlight(Document document, Highlighter highlighter, Analyzer analyzer, String field) throws IOException {
        List<IndexableField> list = document.getFields();
        for (IndexableField fieldAble : list) {
            String fieldValue = fieldAble.stringValue();
            //这里要把文本去格式化
            fieldValue = HtmlUtils.getText(fieldValue);
            if (fieldAble.name().equals(field)) {
                try {
                    fieldValue = highlighter.getBestFragment(analyzer, field, fieldValue);
                } catch (Exception e) {
                    fieldValue = fieldAble.stringValue();
                }
                return (fieldValue == null || fieldValue.trim().length() == 0) ? fieldAble.stringValue() : fieldValue;
            }
        }
        return null;
    }

    /**
     * 获取符合条件的总记录数
     *
     * @param query 搜索
     * @return 返回总记录数量
     */
    public static int searchTotalRecord(IndexSearcher search, Query query) {
        ScoreDoc[] docs = null;
        try {
            TopDocs topDocs = search.search(query, Integer.MAX_VALUE);
            if (topDocs == null || topDocs.scoreDocs == null || topDocs.scoreDocs.length == 0) {
                return 0;
            }
            docs = topDocs.scoreDocs;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return docs.length;
    }

    /**
     * Lucene分页查询
     *
     * @param searcher 搜索
     * @param query    查询
     * @param page     分页
     */
    public static void pageQuery(IndexSearcher searcher, Directory directory, Query query, Page<Document> page) {
        int totalRecord = searchTotalRecord(searcher, query);
        // 设置总记录数
        page.setTotalRecord(totalRecord);
        TopDocs topDocs = null;
        try {
            topDocs = searcher.searchAfter(page.getAfterDoc(), query, page.getPageSize());
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Document> docList = new ArrayList<Document>();
        ScoreDoc[] docs = topDocs.scoreDocs;
        int index = 0;
        for (ScoreDoc scoreDoc : docs) {
            int docID = scoreDoc.doc;
            Document document = null;
            try {
                document = searcher.doc(docID);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (index == docs.length - 1) {
                page.setAfterDoc(scoreDoc);
                page.setAfterDocId(docID);
            }
            docList.add(document);
            index++;
        }
        page.setItems(docList);
    }

    /**
     * Lucene分页查询
     *
     * @param searcher 搜索
     * @param query    查询
     * @param page     分页
     */
    public static void pageQuery(IndexSearcher searcher, Query query, Page<Document> page, Sort sort) {
        int totalRecord = searchTotalRecord(searcher, query);
        // 设置总记录数
        page.setTotalRecord(totalRecord);
        TopDocs topDocs = null;
        try {
            ScoreDoc lastSdDoc = getLastScoreDoc(query, page.getCurrentPage(), page.getPageSize(), searcher, sort);
            topDocs = searcher.searchAfter(lastSdDoc, query, page.getPageSize(), sort);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Document> docList = new ArrayList<Document>();
        ScoreDoc[] docs = topDocs.scoreDocs;
        int index = 0;
        for (ScoreDoc scoreDoc : docs) {
            int docID = scoreDoc.doc;
            Document document = null;
            try {

                document = searcher.doc(docID);

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (index == docs.length - 1) {
                page.setAfterDoc(scoreDoc);
                page.setAfterDocId(docID);
            }
            docList.add(document);
            index++;
        }
        page.setItems(docList);
    }

    /**
     * 获取最后一次搜索的最后一个元素
     *
     * @param query     查询
     * @param pageIndex 页码
     * @param pageSize  每页数量
     * @param searcher  搜索
     * @return 返回页面
     */
    public static ScoreDoc getLastScoreDoc(Query query, int pageIndex, int pageSize, IndexSearcher searcher, Sort sort) {
        if (pageIndex == 1) {
            return null;
        } else {
            int num = (pageIndex - 1) * pageSize;
            TopDocs tds = null;
            try {
                tds = searcher.search(query, num, sort);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return tds.scoreDocs[num - 1];
        }
    }

    /**
     * 获取自定义排序
     * 先按相关度再按时间
     *
     * @param field 字段
     * @param desc  是否降序
     * @return 返回排序对象
     */
    public static Sort getSortByField(String field, Boolean desc) {
        return new Sort(new SortField(field, SortField.Type.LONG, desc));
    }


}
