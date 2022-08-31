package com.krt.gen.util;

import com.krt.common.util.FreeMarkerUtils;
import com.krt.common.util.SpringUtils;
import com.krt.common.util.StringUtils;
import com.krt.gen.entity.GenTable;
import com.krt.gen.entity.GenTableColumn;
import com.krt.gen.enums.BooleanEnum;
import com.krt.gen.enums.FileTypeEnum;
import com.krt.gen.enums.ShowTypeEnum;
import com.krt.gen.enums.TemplateFileEnum;
import com.krt.gen.properties.GenProperties;
import com.krt.sys.entity.Res;
import com.krt.sys.service.IResService;
import com.krt.sys.service.impl.ResServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器工具类
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年05月03日
 */
@Slf4j
public class GenUtils {

    /**
     * 参数
     */
    private static GenProperties genProperties = SpringUtils.getBean(GenProperties.class);

    /**
     * 代码生成
     *
     * @param genTable 数据表
     */
    public static void genCode(GenTable genTable) throws IOException {
        String fileType = genTable.getGenScheme().getFileType();
        if (StringUtils.contains(fileType, FileTypeEnum.ENTITY.getValue())) {
            genEntity(genTable);
        }
        if (StringUtils.contains(fileType, FileTypeEnum.MAPPER.getValue())) {
            genMapper(genTable);
        }
        if (StringUtils.contains(fileType, FileTypeEnum.MAPPERXML.getValue())) {
            genMapperXml(genTable);
        }
        if (StringUtils.contains(fileType, FileTypeEnum.SERVER.getValue())) {
            genService(genTable);
        }
        if (StringUtils.contains(fileType, FileTypeEnum.CONTROLLER.getValue())) {
            genController(genTable);
        }
       if (StringUtils.contains(fileType, FileTypeEnum.FTL.getValue())) {
            genFtl(genTable);
        }
        if (StringUtils.contains(fileType, FileTypeEnum.PERMISSION.getValue())) {
            genPermission(genTable);
        }
    }

    /**
     * 实体生成
     *
     * @param genTable
     * @throws IOException
     */
    public static void genEntity(GenTable genTable) throws IOException {
        log.debug("开始生成{}实体", genTable.getClassName());
        //设置渲染参数
        Map<String, Object> root = new HashMap<>(1);
        root.put("genTable", genTable);
        //获取文件名
        String packagePath = "\\" + genTable.getGenScheme().getPackageName().replace(".", "\\") + "\\entity\\";
        String fileName = genTable.getGenScheme().getCodePath() + genProperties.getJavaPath() + packagePath + genTable.getClassName() + ".java";
        //生成文件
        genFile(root, fileName, TemplateFileEnum.ENTITY.getValue());
        log.debug("生成{}实体完毕", genTable.getClassName());
    }

    /**
     * Mapper生成
     *
     * @param genTable
     * @throws IOException
     */
    public static void genMapper(GenTable genTable) throws IOException {
        log.debug("开始生成{}Mapper", genTable.getClassName());
        //设置渲染参数
        Map<String, Object> root = new HashMap<>(3);
        root.put("genTable", genTable);
        //获取文件名
        String packagePath = "\\" + genTable.getGenScheme().getPackageName().replace(".", "\\") + "\\mapper\\";
        String fileName = genTable.getGenScheme().getCodePath() + genProperties.getJavaPath() + packagePath + genTable.getClassName() + "Mapper.java";
        //生成文件
        genFile(root, fileName, TemplateFileEnum.MAPPER.getValue());
        log.debug("生成{}Mapper完毕", genTable.getClassName());
    }

    /**
     * MapperXml生成
     *
     * @param genTable
     * @throws IOException
     */
    public static void genMapperXml(GenTable genTable) throws IOException {
        log.debug("开始生成{}MapperXml", genTable.getClassName());
        //设置渲染参数
        Map<String, Object> root = new HashMap<>(3);
        root.put("genTable", genTable);
        //获取文件名
        String packagePath = "\\" + genTable.getGenScheme().getPackageName().replace(".", "\\") + "\\mapper\\";
        String fileName = genTable.getGenScheme().getCodePath() + genProperties.getJavaPath() + packagePath + genTable.getClassName() + "Mapper.xml";
        //生成文件
        genFile(root, fileName, TemplateFileEnum.MAPPERXML.getValue());
        log.debug("生成{}MapperXml完毕", genTable.getClassName());
    }

    /**
     * Service && ServiceImpl生成
     *
     * @param genTable
     * @throws IOException
     */
    public static void genService(GenTable genTable) throws IOException {
        log.debug("开始生成{}Service && {}ServiceImpl", genTable.getClassName(), genTable.getClassName());
        //设置渲染参数
        Map<String, Object> root = new HashMap<>(3);
        root.put("genTable", genTable);
        //获取文件名
        String servicePackagePath = "\\" + genTable.getGenScheme().getPackageName().replace(".", "\\") + "\\service\\";
        String serviceFileName = genTable.getGenScheme().getCodePath() + genProperties.getJavaPath() + servicePackagePath + "I" + genTable.getClassName() + "Service.java";
        //生成service文件
        genFile(root, serviceFileName, TemplateFileEnum.SERVER.getValue());
        String serviceImplPackagePath = "\\" + genTable.getGenScheme().getPackageName().replace(".", "\\") + "\\service\\impl\\";
        String serviceImplFileName = genTable.getGenScheme().getCodePath() + genProperties.getJavaPath() + serviceImplPackagePath + genTable.getClassName() + "ServiceImpl.java";
        //生成serviceImpl文件
        genFile(root, serviceImplFileName, TemplateFileEnum.SERVERIMPL.getValue());
        log.debug("生成{}Service && {}ServiceImpl完毕", genTable.getClassName(), genTable.getClassName());
    }

    /**
     * Controller生成
     *
     * @param genTable
     * @throws IOException
     */
    public static void genController(GenTable genTable) throws IOException {
        log.debug("开始生成{}Controller", genTable.getClassName());
        //设置渲染参数
        Map<String, Object> root = new HashMap<>(2);
        root.put("genTable", genTable);
        String namespace = genTable.getGenScheme().getPackageName().replace(genProperties.getPackagePrefix(), "").replace(".", "/");
        root.put("namespace", namespace);
        //获取文件名
        String packagePath = "\\" + genTable.getGenScheme().getPackageName().replace(".", "\\") + "\\controller\\";
        String fileName = genTable.getGenScheme().getCodePath() + genProperties.getJavaPath() + packagePath + genTable.getClassName() + "Controller.java";
        //生成文件
        genFile(root, fileName, TemplateFileEnum.CONTROLLER.getValue());
        log.debug("生成{}Controller", genTable.getClassName());
    }


    /**
     * FTL生成
     *
     * @param genTable
     * @throws IOException
     */
    public static void genFtl(GenTable genTable) throws IOException {
        log.debug("开始生成{} FTL文件", genTable.getClassName());
        //生成entity
        Map<String, Object> root = new HashMap<>(5);
        root.put("genTable", genTable);
        genTable.setEntityName(getEntityName(genTable.getClassName()));
        root.put("genTableColumns", genTable.getGenTableColumns());
        String namespace = genTable.getGenScheme().getPackageName().replace(genProperties.getPackagePrefix(), "").replace(".", "/");
        root.put("namespace", namespace);
        String packagePath = "\\" + genTable.getGenScheme().getPackageName().replace(genProperties.getPackagePrefix(), "").replace(".", "\\") + "\\" + getEntityName(genTable.getClassName()) + "\\";
        log.debug("packagePath:{}", packagePath);
        root.put("formType", genTable.getGenScheme().getFormType());
        //生成list文件
        String fileName1 = genTable.getGenScheme().getCodePath() + genProperties.getViewPath() + packagePath + "list.ftl";
        genFile(root, fileName1, "list.ftl");
        //insert
        String fileName2 = genTable.getGenScheme().getCodePath() + genProperties.getViewPath() + packagePath + "insert.ftl";
        genFile(root, fileName2, "insert.ftl");
        //update
        String fileName3 = genTable.getGenScheme().getCodePath() + genProperties.getViewPath() + packagePath + "update.ftl";
        genFile(root, fileName3, "update.ftl");
        //包含绑定数据list组件
        if (BooleanEnum.YES.getValue().equals(genTable.getRList()) || BooleanEnum.YES.getValue().equals(genTable.getCList())) {
            for (GenTableColumn column : genTable.getGenTableColumns()) {
                String fileName = null;
                if (ShowTypeEnum.RLIST.getValue().equals(column.getShowType())) {
                    fileName = genTable.getGenScheme().getCodePath() + genProperties.getViewPath() + packagePath + getEntityName(column.getGenTable().getClassName()) + "List.ftl";
                } else if (ShowTypeEnum.CLIST.getValue().equals(column.getShowType())) {
                    fileName = genTable.getGenScheme().getCodePath() + genProperties.getViewPath() + packagePath + getEntityName(column.getGenTable().getClassName()) + "Lists.ftl";
                } else {
                    log.error("数据list组件 类型错误");
                }
                if (fileName != null) {
                    String cnamespace = column.getGenTable().getGenScheme().getPackageName().replace(genProperties.getPackagePrefix(), "").replace(".", "/");
                    root.put("namespace", cnamespace);
                    root.put("column", column);
                    genFile(root, fileName, "dataList.ftl");
                }
            }
        }
        log.debug("生成{}  FTL文件完毕", genTable.getClassName());
    }

    /**
     * 添加权限
     */
    public static void genPermission(GenTable genTable) {
        IResService resService = SpringUtils.getBean(ResServiceImpl.class);
        //资源菜单
        Res urlRes = new Res();
        String entityName = getEntityName(genTable.getClassName());
        urlRes.setName(genTable.getComment() + "管理");
        String namespace = genTable.getGenScheme().getPackageName().replace(genProperties.getPackagePrefix(), "").replace(".", "/");
        urlRes.setUrl(namespace + "/" + entityName + "/list");
        if (StringUtils.isBlank(genTable.getGenScheme().getPermissionName())) {
            urlRes.setPermission(entityName + ":list");
        } else {
            urlRes.setPermission(genTable.getGenScheme().getPermissionName() + ":" + entityName + ":list");
        }
        urlRes.setSort(99);
        urlRes.setPid(0);
        urlRes.setType("url");
        resService.insert(urlRes);
        //按钮菜单
        Res insertRes = new Res();
        insertRes.setName("新增");
        if (StringUtils.isBlank(genTable.getGenScheme().getPermissionName())) {
            insertRes.setPermission(entityName + ":insert");
        } else {
            insertRes.setPermission(genTable.getGenScheme().getPermissionName() + ":" + entityName + ":insert");
        }
        insertRes.setSort(1);
        insertRes.setPid(urlRes.getId());
        insertRes.setType("button");
        //修改按钮
        Res updateRes = new Res();
        updateRes.setName("修改");
        if (StringUtils.isBlank(genTable.getGenScheme().getPermissionName())) {
            updateRes.setPermission(entityName + ":update");
        } else {
            updateRes.setPermission(genTable.getGenScheme().getPermissionName() + ":" + entityName + ":update");
        }
        updateRes.setSort(2);
        updateRes.setPid(urlRes.getId());
        updateRes.setType("button");
        //删除按钮
        Res delRes = new Res();
        delRes.setName("删除");
        if (StringUtils.isBlank(genTable.getGenScheme().getPermissionName())) {
            delRes.setPermission(entityName + ":delete");
        } else {
            delRes.setPermission(genTable.getGenScheme().getPermissionName() + ":" + entityName + ":delete");
        }
        delRes.setSort(3);
        delRes.setPid(urlRes.getId());
        delRes.setType("button");
        List<Res> list = new ArrayList<>();
        list.add(insertRes);
        list.add(updateRes);
        list.add(delRes);
        if (BooleanEnum.YES.getValue().equals(genTable.getExcelIn())) {
            //导入按钮
            Res excelIn = new Res();
            excelIn.setName("导入");
            if (StringUtils.isBlank(genTable.getGenScheme().getPermissionName())) {
                excelIn.setPermission(entityName + ":excelIn");
            } else {
                excelIn.setPermission(genTable.getGenScheme().getPermissionName() + ":" + entityName + ":excelIn");
            }
            excelIn.setSort(4);
            excelIn.setPid(urlRes.getId());
            excelIn.setType("button");
            list.add(excelIn);
        }
        if (BooleanEnum.YES.getValue().equals(genTable.getExcelOut())) {
            //导出按钮
            Res excelOut = new Res();
            excelOut.setName("导出");
            if (StringUtils.isBlank(genTable.getGenScheme().getPermissionName())) {
                excelOut.setPermission(entityName + ":excelOut");
            } else {
                excelOut.setPermission(genTable.getGenScheme().getPermissionName() + ":" + entityName + ":excelOut");
            }
            excelOut.setSort(5);
            excelOut.setPid(urlRes.getId());
            excelOut.setType("button");
            list.add(excelOut);
        }
        resService.insertBatch(list);
    }

    /**
     * 生成文件
     *
     * @param root     参数
     * @param fileName 文件名全路径
     * @param ftl      模板名称
     * @throws IOException
     */
    public static void genFile(Map root, String fileName, String ftl) throws IOException {
        GenTable genTable = (GenTable) root.get("genTable");
        if (!"dataList.ftl".equals(ftl)) {
            ftl = genTable.getGenScheme().getTemplate() + "/" + ftl;
        }
        log.debug("模板类型{},模板地址{}", genTable.getGenScheme().getTemplate(), genProperties.getTemplateLoaderPath());
        String content = FreeMarkerUtils.getTemplateContent(root, genProperties.getTemplateLoaderPath(), ftl);
        log.debug("生成文件路径：{}", fileName);
        File file = new File(fileName);
        file.getParentFile().mkdirs();
        OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
        BufferedWriter writer = new BufferedWriter(out);
        writer.write(content);
        writer.close();
    }

    /**
     * 获取实例名（类型首字母小写）
     *
     * @param entityName
     */
    public static String getEntityName(String entityName) {
        char[] ch = entityName.toCharArray();
        if (ch[0] >= 'A' && ch[0] <= 'Z') {
            ch[0] = (char) (ch[0] + 32);
        }
        return new String(ch);
    }

    /**
     * 获取jdbc类型
     *
     * @param jdbcType
     */
    public static String getJdbcType(String jdbcType) {
        if ("int".equalsIgnoreCase(jdbcType)) {
            return "integer";
        }
        if ("datetime".equalsIgnoreCase(jdbcType)) {
            return "timestamp";
        }
        if ("text".equalsIgnoreCase(jdbcType)) {
            return "varchar";
        }
        return jdbcType;
    }
}
