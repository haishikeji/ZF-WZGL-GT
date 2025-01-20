package com.px.project.common.gencode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.yaml.snakeyaml.Yaml;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.px.project.common.customannotation.AttributeAnnotation;
import com.px.project.common.utils.StringUtil;
import com.px.project.web.entity.SeqModel;

/**
 * @Package com.px.project.common.gencode
 * @ClassName CodeGenerator
 * @Description CodeGenerator
 * @author 品讯科技
 * @time 2019/09/20
 * @version V 1.0
 */
@SuppressWarnings("unused")
public class CodeGenerator {


    private final String CONS_CONTROLLER = "controller";//标记controller

    private final String CONS_ISERVICE = "IService";//标记是接口

    private final String CONS_SERVICEIMPL = "serviceimpl";//标记接口实现

    private final String CONS_IMAPPER = "Imapper";//标记是mapper接口

    private final String CONS_PROVIDER = "provider";//标记是provider接口

    private final String CONS_DTO = "Dto";//标记是Dto接口

    private String authorName="";//创建人

    private String srcRoot = "D:\\src_temp\\";//生成路径

    private String tableName="";//表名

    private String isRecord="1";//是否记录修改人等 1：是 0 不是

    private String isTableGenerator="0";//是否通过表名生成 1：是 0：不是

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getIsRecord() {
        return isRecord;
    }

    public void setIsRecord(String isRecord) {
        this.isRecord = isRecord;
    }

    public String getIsTableGenerator() {
        return isTableGenerator;
    }

    public void setIsTableGenerator(String isTableGenerator) {
        this.isTableGenerator = isTableGenerator;
    }

    public String getSrcRoot() {
        return srcRoot;
    }

    public void setSrcRoot(String srcRoot) {
        this.srcRoot = srcRoot;
    }

    /**
     * @name generate
     * @author 品讯科技
     * @Description 生成代码
     * @time 2018年9月17日
     * @throws
     */
    private void generate(Class cls) throws IOException {
        System.out.println("Generate files for "+cls.getName() + " | " + cls.getSimpleName());
        Map<String, Object> values = new HashMap<String, Object>();
        //拿到cls对应类的包的名字
        String pkg = cls.getPackage().getName();
        //类的全名
        System.out.println("Generate packageName:"+pkg);
        String className=cls.getName ();
        //这个判断，没理解是什么意思，如果满足条件，类的包名就被截了，相当于取到了该包的上一层（目录）
        if (!pkg.endsWith(".model")) {
            pkg = pkg.substring(0, pkg.lastIndexOf("."));
        }
        System.out.println("Generate packageName:"+pkg);
        //类的简称
        String modelName = cls.getSimpleName();

        //如果类名为XXXModel，就把Model截掉，要剩下的部分
        if(modelName.endsWith("Model")){
            int lastIndex = modelName.lastIndexOf("Model");
            modelName = modelName.substring(0, lastIndex);
        }
        //controller的名称空间？先设置为空
        String controllerNameSpace = "";
        //首字母小写处理
        controllerNameSpace = modelName.substring(0, 1).toLowerCase() + modelName.substring(1);
        System.out.println("Generate controllerNameSpace:"+controllerNameSpace);
        //时间格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println(df.format(new Date ()));// new Date()为获取当前系统时间
        //这个values是上面创建的HashMap，下面是往Map里面放值
        //包路径，被截取过的
        values.put("package", pkg);//包路径
        String p_package = pkg.substring(0, pkg.lastIndexOf("."));
        values.put("p_package", p_package);//包的上一层
        String p_p_package = p_package.substring(0, p_package.lastIndexOf("."));
        values.put("p_p_package", p_p_package);//包的上上一层
        //表名，main方法里面已经设置过
        values.put("tableName", this.getTableName());//表名
        //实体类名截掉Model剩下的部分
        values.put("entity", modelName);//实体除去Model其余部分
        //Controller 命名空间名字
        values.put("controllerNameSpace", controllerNameSpace);//Controller 命名空间名字
        //类名，上面取过了（冗余代码）
        values.put("modelName", cls.getName());//类名
        //当前系统时间
        values.put("systemDate",df.format(new Date ()));//系统时间
        //创建人，main方法中已经设置过了
        values.put("authorName",authorName);//创建人

        List<JSONObject> list=new ArrayList<JSONObject>();
        String selectOrInsertSql="";//查询或者插入的sql字段部分《不包括主键》
        JSONObject primaryKey=new JSONObject ();
        List<Field> parentlist =new ArrayList<>();//实体注解对应的表字段集合
        //判断是否通过表生业务代码，0代表不是，1代表是
        if("0".equals (this.isTableGenerator)){
            //获取所有的表字段集合
            while (cls != null){
                //cls.getDeclaredFields()返回一个所有字段组成的Field类型的数组；Arrays.asList的作用是返回一个受指定数组支持的列表，长度跟数组长度相同
                //再通过构造生成一个新的数组；然后再把这个新数组里面的值依次插入到parentlist中，parentlist也是个列表；这样做最终得到的就是一个cls类中所有字段组成的ArrayList
                parentlist.addAll(new ArrayList<>(Arrays.asList(cls.getDeclaredFields())));
                //cls的超类Class
                cls = cls.getSuperclass();
            }
            for(int i=0;i<parentlist.size();i++) {
                //拿到每一步循环的字段
                Field field = parentlist.get (i);
                //获得所有的注解：field.getDeclaredAnnotations ()
                for (Annotation anno : field.getDeclaredAnnotations ()) {
                    //找到自己的注解
                    //判断这个字段的注解列表中是否存在AttributeAnnotation的注解，因为AttributeAnnotation的value为数据库中的字段值
                    if(anno.annotationType().equals(AttributeAnnotation.class)) {
                        //存储字段中所有AttributeAnnotation类型注解的value
                        //AttributeAnnotation注解中还有一个
                        if(((AttributeAnnotation) anno).isGenerate ()){
                            if(((AttributeAnnotation) anno).isPrimaryKey ()){
                                //primaryKey+="\"id as id,\"+\n";//拼接主键
                                primaryKey.put ("columnPrimaryKeyName",((AttributeAnnotation)anno).value ());//表主键name
                                primaryKey.put ("propertyPrimaryKeyName",field.getName ());//属性主键-首字母小写
                                primaryKey.put ("propertyPrimaryKeyBigName", StringUtil.upperCase (field.getName ()));//属性主键-首字母大写
                            }else {
                                //根据注解的value值来拼sql语句
                                selectOrInsertSql+="\t\t\t\t\t\t\t\t\t \"t."+((AttributeAnnotation)anno).value ()+" as "+field.getName ()+",\"+\n";
                                JSONObject json=new JSONObject ();
                                json.put ("columnName",((AttributeAnnotation)anno).value ());
                                json.put ("propertyName",field.getName ());
                                json.put ("propertyBigName",StringUtil.upperCase (field.getName ()));
                                list.add (json);
                            }
                        }
                    }
                }
            }
        }
        selectOrInsertSql="\"t."+primaryKey.get ("columnPrimaryKeyName")+" as "+primaryKey.get ("propertyPrimaryKeyName")+",\"+\n"+selectOrInsertSql;
        selectOrInsertSql=selectOrInsertSql.substring (0,selectOrInsertSql.length ()-4)+"\"";
        values.put("selectOrInsertSql",selectOrInsertSql);
        values.put("primaryKey",primaryKey);
        values.put("list",list);

        convertFile(CONS_CONTROLLER, values);//生成Controller
        convertFile(CONS_ISERVICE, values);//生成IService
        convertFile(CONS_SERVICEIMPL, values);//生成ServiceImpl
        convertFile(CONS_IMAPPER, values);//生成IMapper
        convertFile(CONS_PROVIDER, values);//生成Provider
        convertFile(CONS_DTO, values);//生成Provider

    }


    /**
     * @name convertFile
     * @author 品讯科技
     * @Description 转换文件
     * @time 2018年9月17日
     * @throws
     */
    private void convertFile(String type, Map<String, Object> values) throws IOException {
        String templateName = getTemplateName(type);
        InputStream is =getClass().getClassLoader().getResourceAsStream (templateName);
        InputStreamReader reader = new InputStreamReader(is, "UTF-8");
        StringBuffer sb = new StringBuffer();
        char[] cbuf = new char[10240];
        for ( ;;) {
            int len = reader.read(cbuf);
            if (len < 0) {
                break;
            }
            sb.append(cbuf, 0, len);
        }
        reader.close();

        File file = getTargetFile(type, (String)values.get("package"), (String)values.get("type"), (String)values.get("entity"), (String)values.get("controllerNameSpace"));

        FreemarkerHelper helper = new FreemarkerHelper();
        String content = FreemarkerHelper.convert(sb.toString(), values);

        FileWriter writer = new FileWriter(file);
        writer.write(content);
        writer.close();
    }

    /**
     * @name getTemplateName
     * @author 品讯科技
     * @Description 获取模版文件名称
     * @time 2018年9月17日
     * @throws
     */
    private String getTemplateName(String type) {
        if (CONS_CONTROLLER.equals(type)) {
            return "ftl/Controller.ftl";
        } else if (CONS_ISERVICE.equals(type)) {
            return "ftl/Service.ftl";
        } else if (CONS_SERVICEIMPL.equals(type)) {
            return "ftl/ServiceImpl.ftl";
        } else if (CONS_IMAPPER.equals(type)) {
            return "ftl/IMapper.ftl";
        } else if (CONS_PROVIDER.equals(type)) {
            return "ftl/Provider.ftl";
        } else if (CONS_DTO.equals(type)) {
            return "ftl/Dto.ftl";
        }else {
            throw new RuntimeException("Unknown type:"+type);
        }
    }
    /**
     * @name getTargetFile
     * @author 品讯科技
     * @Description 生成相应文件
     * @time 2018年9月17日
     * @throws
     */
    private File getTargetFile(String type, String pkg, String entityType, String entity, String controllerNameSpace) {
        String file = null;
        if (CONS_CONTROLLER.equals(type)) {
            file = "controller/"+entity+"Controller.java";
        } else if (CONS_ISERVICE.equals(type)) {
            file = "service/I"+entity+"Service.java";
        } else if (CONS_SERVICEIMPL.equals(type)) {
            file = "service/impl/"+entity+"ServiceImpl.java";
        } else if (CONS_IMAPPER.equals(type)) {
            file = "dao/I"+entity+"Mapper.java";
        } else if (CONS_PROVIDER.equals(type)) {
            file = "dao/provider/"+entity+"Provider.java";
        }  else if (CONS_DTO.equals(type)) {
            file = "dto/"+entity+"Dto.java";
        } else {
            throw new RuntimeException("Unknown type:"+type);
        }
        file = srcRoot + pkg.replace('.', '\\') + "\\" + file;
        File f = new File(file);
        if (!f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }

        return f;
    }

    /**
     * @name getJdbcTemplate
     * @author 品讯科技
     * @Description 获取 JdbcTemplate
     * @time 2018年9月17日
     * @throws
     */
    private static JdbcTemplate getJdbcTemplate(){
        Map map = null;
        try {
            Yaml yaml = new Yaml();
            URL url = CodeGenerator.class.getClassLoader().getResource("prod/application.yml");
            if (url != null) {
                //也可以将值转换为Map
                map=(Map)yaml.load(new FileInputStream(url.getFile()));
                //通过map我们取值就可以了.

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObject =(JSONObject)JSONObject.toJSON (map);
        System.out.println ("_________________________________________________________");
        System.out.println (JSONPath.eval(jsonObject, "$.spring.datasource.url"));
        System.out.println ("_________________________________________________________");
        DriverManagerDataSource dataSource=new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(JSONPath.eval(jsonObject, "$.spring.datasource.url").toString ());
        dataSource.setUsername(JSONPath.eval(jsonObject, "$.spring.datasource.username").toString ());
        dataSource.setPassword(JSONPath.eval(jsonObject, "$.spring.datasource.password").toString ());
        JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
        return jdbcTemplate;
    }

    public static void main(String[] args) throws IOException {
        try {
            //创建类本身的对象，给属性和方法赋值，因为属性和方法都不是静态的
            CodeGenerator codeGenerator = new CodeGenerator();

            codeGenerator.setIsRecord ("1");//是否要生成创建人等信息 1:是 0：不是
            codeGenerator.setIsTableGenerator ("0");//是否通过表生成业务代码 1:是 0：不是
            //代码生成路径，直接选择项目路径即可
            //--切记  已经生成完的在生成就会覆盖原有的，如果想稳点改下路径手动复制到目录下
            //codeGenerator.setSrcRoot ("E:/myIdeaWorkspace/order/src/main/java/");

            //创建实例对象并给属性赋值,属性和属性的含义如下
            /*
            authorName 创建人 初始值""
            tableName 表名 初始值""
            isRecord 是否记录修改人等 1：是 0 不是 初始值"1"
            isTableGenerator 是否通过表生成业务代码 1：是 0：不是 初始值"0"
            srcRoot 生成路径 初始值"c:\\src_temp\\"
             */
            codeGenerator.setAuthorName ("刘一兵");//设置创建人
            codeGenerator.setTableName ("s_t_seq");//设置表名
            codeGenerator.setSrcRoot ("c:\\src_tempcscscsc\\");
            codeGenerator.generate(SeqModel.class);//实体类--必要  会根据实体的名字生成对应的Controller

        } catch (Exception e ) {
            e.printStackTrace();
        }
        System.out.println("Done.自动化创建脚本完成！");

    }
}
