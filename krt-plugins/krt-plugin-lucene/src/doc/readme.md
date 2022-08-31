# demo 代码
    public void createIndex() throws IOException {
            log.debug("################先删除全部索引#####################");
            FSDirectory directory = null;
            IndexWriter iWriter = null;
            try {
                //设置存储索引的位置
                directory = LuceneUtils.openFSDirectory("d://test");
                // 配置索引
                IndexWriterConfig writerConfig = new IndexWriterConfig(new IKAnalyzer());
                //这行代码设置了存放索引的文件夹将以覆盖或者新建的方式建立。如果没有这样设置，并且在原索引文件夹中索引没有被删除的情况下，新的索引文件将会append到原来索引文件之后，这样会导致索引结果发生错误。
                iWriter = LuceneUtils.getIndexWritier(directory, writerConfig);
                LuceneUtils.deleteAllIndex(iWriter);
                List<Log> list = logService.selectList();
                log.debug("################开始创建索引#####################");
                for (Log log : list) {
                    Document doc = new Document();
                    doc.add(new Field("id", log.getId().toString(), TextField.TYPE_STORED));
                    doc.add(new Field("username", log.getUsername() == null ? "" : log.getUsername(), TextField.TYPE_STORED));
                    doc.add(new Field("description", log.getDescription() == null ? "" : log.getDescription(), TextField.TYPE_STORED));
                    doc.add(new Field("requestUrl", log.getRequestUrl() == null ? "" : log.getRequestUrl(), TextField.TYPE_STORED));
                    iWriter.addDocument(doc);
                }
                //先关闭index再关闭directory
                iWriter.commit();
            } catch (IOException e) {
                log.error("添加索引失败", e);
            } finally {
                if (iWriter != null) {
                    LuceneUtils.closeIndexWriter(iWriter);
                }
                if (directory != null) {
                    LuceneUtils.closeDirectory(directory);
                }
                log.debug("################创建索引完成#####################");
            }
    
        }
    
        public static void main(String[] args) throws ParseException {
            //测试查询索引
            FSDirectory directory = LuceneUtils.openFSDirectory("d://test");
            IndexReader iReader = LuceneUtils.getIndexReader(directory,false);
            IndexSearcher iSearcher = LuceneUtils.getIndexSearcher(iReader);
            //指定搜索字段和分析器
            String searchField = "description";
            QueryParser parser = new QueryParser(searchField, new IKAnalyzer());
            //用户输入内容
            Query query = parser.parse("用户");
            //执行查询，并打印查询到的记录数
            List<Document> list = LuceneUtils.query(iSearcher,query);
            for(Document document:list) {
                System.out.println(document.toString());
            }
        }