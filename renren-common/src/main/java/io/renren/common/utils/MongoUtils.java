package io.renren.common.utils;

import java.io.File;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.renren.common.config.Constants;
import net.sf.json.JSONObject;

import org.apache.commons.lang.Validate;
import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import com.mongodb.gridfs.GridFSInputFile;
/**
 *
 * mongoDB 工具类
 * mongoDB 工具类
 * @author    刘昌雨
 * @since     1.0
 */
public class MongoUtils {
    private static DB db = null;
    private final static MongoUtils instance = new MongoUtils();
    static {
        try {
            //Mongo mongo = new Mongo("59.44.117.214", 27017);
            //Mongo mongo = new Mongo("59.44.117.239", 27017);
            Mongo mongo = new Mongo(Constants.MONGODB_ADDR, 27017);
            db = mongo.getDB(Constants.MONGODB_NAME);
            boolean auth = db.authenticate(Constants.MONGODB_USER, Constants.MONGODB_PASSWORD.toCharArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static GridFS getGridFS(DB db) {
        GridFS gridFS = new GridFS(db);
        return gridFS;
    }
    public String toMd5(String Str) throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(Str.getBytes());
        String pwd = new BigInteger(1, digest).toString(16);
        System.out.println(pwd);
        return pwd;
    }
    /**
     * 获取集合（表）
     *
     * @param collection
     * @author liucy
     */
    public static DBCollection getCollection(String collection) {
        return db.getCollection(collection);
    }

    public DBObject map2Obj(Map<String, Object> map) {
        DBObject obj = new BasicDBObject();
        obj.putAll(map);
        return obj;
    }
    /**
     * 插入
     *
     * @param collection
     * @param map
     * @author liucy
     */
    public void insert(String collection, Map<String, Object> map) {
        try {
            DBObject dbObject = map2Obj(map);
            getCollection(collection).insert(dbObject);
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }
    /**
     * 批量插入
     *
     * @param collection
     * @param list
     * @author liucy
     */
    public void insertBatch(String collection, List<Map<String, Object>> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        try {
            List<DBObject> listDB = new ArrayList<DBObject>();
            for (int i = 0; i < list.size(); i++) {
                DBObject dbObject = map2Obj(list.get(i));
                listDB.add(dbObject);
            }
            getCollection(collection).insert(listDB);
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }
    /**
     * 删除
     *
     * @param collection
     * @param map
     * @author liucy
     */
    public int delete(String collection, Map<String, Object> map) {
        DBObject obj = map2Obj(map);
        return getCollection(collection).remove(obj).getN();
    }
    /**
     * 根据ID删除
     *
     * @param collection
     * @param id
     * @author liucy
     * @return
     */
    public int deleteById(String collection, String id) {
        return getCollection(collection).remove(
                new BasicDBObject("_id", new ObjectId(id))).getN();
    }

    /**
     * 删除全部
     *
     * @param collection
     * @param
     * @author liucy
     */
    public int deleteAll(String collection) {
        List<DBObject> rs = findAll(collection);
        int ret = 0;
        if (rs != null && !rs.isEmpty()) {
            for (int i = 0; i < rs.size(); i++) {
                ret += getCollection(collection).remove(rs.get(i)).getN();
            }
        }
        return ret;
    }
    /**
     * 批量删除
     *
     * @param collection
     * @param list
     * @author liucy
     */
    public void deleteBatch(String collection, List<Map<String, Object>> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            getCollection(collection).remove(map2Obj(list.get(i)));
        }
    }
    /**
     * 根据主键_id 批量删除
     *
     * @param collection
     * @param
     * @author liucy
     */
    public void deleteBatchByIds(String collection, List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        for (int i = 0; i < ids.size(); i++) {
            getCollection(collection).remove(
                    new BasicDBObject("_id", new ObjectId(ids.get(i)))).getN();
        }
    }
    /**
     * 计算满足条件条数
     *
     * @param collection
     * @param map
     * @author liucy
     */
    public long getCount(String collection, Map<String, Object> map) {
        return getCollection(collection).getCount(map2Obj(map));
    }
    /**
     * 计算集合总条数
     *
     * @param collection
     * @param
     * @author liucy
     */
    public int getCount(String collection) {
        return getCollection(collection).find().count();
    }
    /**
     * 更新
     *
     * @param collection
     * @param setFields
     * @param whereFields
     * @author liucy
     */
    public int update(String collection, Map<String, Object> whereFields,
                      Map<String, Object> setFields) {
        DBObject obj1 = map2Obj(whereFields);
        DBObject obj2 = map2Obj(setFields);
        return getCollection(collection).update(obj1, obj2).getN();
    }
    /**
     * 更新
     *
     * @param collection
     * @param setFields
     * @param
     * @author liucy
     */
    public int updateById(String collection, String id,
                          Map<String, Object> setFields) {
        DBObject obj = map2Obj(setFields);
        return getCollection(collection).update(
                new BasicDBObject("_id", new ObjectId(id)), obj).getN();
    }

    /**
     * 查找集合所有对象
     *
     * @param collection
     * @author liucy
     */
    public List<DBObject> findAll(String collection) {
        return getCollection(collection).find().toArray();
    }
    /**
     * 查找（返回一个对象）
     *
     * @param map
     * @param collection
     * @author liucy
     */
    public DBObject findOne(String collection, Map<String, Object> map) {
        DBCollection coll = getCollection(collection);
        return coll.findOne(map2Obj(map));
    }
    public <T> T getObject(final String collection,Map<String, Object> map, Class<T> clz) {
        Validate.notNull(clz);
        DBCollection coll = getCollection(collection);
        DBObject dbObject=coll.findOne(map2Obj(map));
        JSONObject jsObj = JSONObject.fromObject(dbObject.toString());
        return (T) JSONObject.toBean(jsObj, clz);
    }
    /**
     * 查找（返回一个List<DBObject>）
     *
     * @param
     * @param map
     * @param collection
     * @throws Exception
     * @author liucy
     */
    public List<DBObject> find(String collection, Map<String, Object> map)
            throws Exception {
        DBCollection coll = getCollection(collection);
        DBCursor c = coll.find(map2Obj(map));
        if(c != null) {
            return c.toArray();
        }
        else {
            return null;
        }
    }

    public List<DBObject> find(String collection, DBObject obj) {
        DBCollection coll = getCollection(collection);
        DBCursor c = coll.find(obj);
        if (c != null) {
            return c.toArray();
        }
        else {
            return null;
        }
    }
    /**
     * 查询+排序
     * @param collection  查询表
     * @param whereFields 查询匹配条件
     * @param orderBy  排序字段
     * @param limit  限制条数
     * @return
     * @author liucy
     */
    public List<DBObject> findAndSort(String collection,
                                      Map<String, Object> whereFields, DBObject orderBy, int limit) {
        DBCursor dbCursor = null;
        if (limit == -1) {
            dbCursor = getCollection(collection).find(map2Obj(whereFields))
                    .sort(orderBy);
        } else {
            dbCursor = getCollection(collection).find(map2Obj(whereFields))
                    .sort(orderBy).limit(limit);
        }
        if (dbCursor != null){
            return dbCursor.toArray();
        }
        else {
            return null;
        }
    }
    /**
     * mongdb分页
     * @param collection
     * @param whereFields
     * @param orderBy
     * @param pagesize
     * @param currentpage
     * @return
     * @author liucy
     */
    public List<DBObject> findAndSortAndPage(String collection,
                                             Map<String, Object> whereFields, DBObject orderBy, int pagesize, int currentpage) {
        DBCursor dbCursor = null;
        int skipValue = 0;
        if(currentpage == 1){
            skipValue = 0;
        }else{
            skipValue = (currentpage-1)*pagesize;
        }
        dbCursor = getCollection(collection).find(map2Obj(whereFields))
                .sort(orderBy).skip(skipValue).limit(pagesize);
        if (dbCursor != null) {
            return dbCursor.toArray();
        }
        else {
            return null;
        }
    }
    /**
     * 根据表名和字段名 对字段进行降序排序
     * @param collection 表名
     * @param colum 排序列名
     * @param i 获取的第几个（ps.如果想要获取最。。。的一列的话
     * 			且mongodb中的表是使用spring管理则i=1 否则 i=0）
     * @return
     * @author liucy
     */
    public JSONObject findAndDesc(String collection, String colum, int i) {
        Map<String, Object> whereFields = new HashMap<String, Object>();
        DBObject orderBy = new BasicDBObject();
        orderBy.put(colum, -1);
        List<DBObject> listMerchantVs = this.findAndSort(collection,
                whereFields, orderBy, -1);
        DBObject obj = listMerchantVs.get(i);
        JSONObject jsonObj = JSONObject.fromObject(obj);
        return jsonObj;
    }
    /**
     * 保存文件
     *
     * @param collection
     * @param options
     * @author liucy
     */
    public void createCollection(String collection, DBObject options) {
        if (!db.collectionExists(collection)) {
        }
    }
    /**
     * 保存文件
     *
     * @param in
     * @param filename
     * @author liucy
     */
    public void saveFile(InputStream in, String filename) {
        GridFS f = new GridFS(db);
        GridFSFile mongofile = f.createFile(in, filename);
        mongofile.put("filename", filename);
        mongofile.put("uploadDate", new Date());
        mongofile.save();
    }



    public void saveFile(InputStream in, String filename,String fileid) {
        GridFS f = new GridFS(db);
        GridFSFile mongofile = f.createFile(in, filename);
        mongofile.put("fileId", fileid);
        mongofile.put("filename", filename);
        mongofile.put("uploadDate", new Date());
        mongofile.save();
    }

    public void saveFile(InputStream in, String fileid,String filename,String dbName){
        GridFS f = new GridFS(db, dbName);
        GridFSFile mongofile = f.createFile(in, filename);
        mongofile.put("fileId", fileid);
        mongofile.put("filename", filename);
        mongofile.put("uploadDate", new Date());
        mongofile.save();
    }
    public GridFSDBFile findFirstFile(Map<String, Object> map,String dbname) {
        return new GridFS(db, dbname).findOne(map2Obj(map));
    }
    /**
     * 保存文件
     *
     * @param file
     * @param filename
     * @author liucy
     */
    public void saveFile(File file, String filename,String imgnum,String imgtype) {
        GridFS f = getGridFS();
        try {
            GridFSFile mongofile = f.createFile(file.getAbsolutePath());
            mongofile.put("filename", filename);
            mongofile.put("imgnum", imgnum);
            mongofile.put("imgtype", imgtype);
            mongofile.put("uploadDate", new Date());
            mongofile.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void saveFile(byte[] bytes, String filename,String imgnum,String imgtype) {
        GridFS gridFS = getGridFS();
        GridFSFile gridFSFile = gridFS.createFile(bytes);
        gridFSFile.put("filename", filename);
        gridFSFile.put("imgnum", imgnum);
        gridFSFile.put("imgtype", imgtype);
        gridFSFile.put("uploadDate", new Date());
        gridFSFile.save();
    }
    public void saveFile(byte[] bytes, String filename,String imgnum,String imgtype,String number) {
        GridFS gridFS = getGridFS();
        GridFSFile gridFSFile = gridFS.createFile(bytes);
        gridFSFile.put("filename", filename);
        gridFSFile.put("imgnum", imgnum);
        gridFSFile.put("imgtype", imgtype);
        gridFSFile.put("number", number);
        gridFSFile.put("uploadDate", new Date());
        gridFSFile.save();
    }
    /**
     * 结果录入用
     * @param bytes
     * @param filename
     */
    public void saveFile(byte[] bytes, String filename,int ranking) {
        GridFS gridFS = getGridFS();
        GridFSFile gridFSFile = gridFS.createFile(bytes);
        gridFSFile.put("filename", filename);
        gridFSFile.put("ranking", ranking);
        gridFSFile.put("uploadDate", new Date());
        gridFSFile.save();
    }
    public void saveFile(MultipartFile file, String fileId,String imgtype) {
        try {
            GridFS fs = getGridFS();
            GridFSInputFile fsFile = fs.createFile(file.getInputStream());
            fsFile.put("filename", file.getOriginalFilename());
            fsFile.put("fileId", fileId);
            fsFile.put("imgtype", imgtype);
            fsFile.put("uploadDate", new Date());
            fsFile.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void saveFile(byte[] data, String fileId, String fileName, String contentType, long fileSize, String nodeId) {
        GridFS gridFS = getGridFS();
        GridFSFile gridFSFile = gridFS.createFile(data);
        gridFSFile.put("fileId", fileId);
        gridFSFile.put("fileName", fileName);
        gridFSFile.put("filename", fileName);
        gridFSFile.put("contentType", contentType);
        gridFSFile.put("fileSize", fileSize);
        gridFSFile.put("nodeId", nodeId);
        gridFSFile.save();
    }
    public void saveNodeChangingRelatedFile(byte[] data, String fileId, String fileName, String contentType, long fileSize, String changeId) {
        GridFS gridFS = getGridFS();
        GridFSFile gridFSFile = gridFS.createFile(data);
        gridFSFile.put("fileId", fileId);
        gridFSFile.put("fileName", fileName);
        gridFSFile.put("filename", fileName);
        gridFSFile.put("contentType", contentType);
        gridFSFile.put("fileSize", fileSize);
        gridFSFile.put("changeId", changeId);
        gridFSFile.save();
    }
    public List<GridFSDBFile> findFilesByName(String fileName) {
        GridFS f = getGridFS();
        List<GridFSDBFile> list = f.find(fileName);
        return list;
    }
    public List<GridFSDBFile> findFilesByName(Map<String, Object> map){
        GridFS f = getGridFS(db);
        GridFS gfsPhoto =new GridFS(db);
        GridFSDBFile imageForOutput = gfsPhoto.findOne(map2Obj(map));
        List<GridFSDBFile> list = gfsPhoto.find(map2Obj(map));
        return list;
    }

    public void removeFile(String filename) {
        getGridFS().remove(filename);
    }
    /**
     * 删除文件,结果录入用
     * @param filename
     * @param ranking
     */
    public void removeFile(String filename,int ranking) {
        BasicDBObject obj=new BasicDBObject();
        obj.put("filename",filename);
        obj.put("ranking",ranking);
        //String param=obj.to
        getGridFS().remove(obj);
    }
    public void removeFile(BasicDBObject query) {
        getGridFS().remove(query);
    }

    public InputStream getf(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("imgnum", "1121453007");
        map.put("imgtype", "1");
        GridFS f = getGridFS(db);
        GridFS gfsPhoto =new GridFS(db);
        GridFSDBFile imageForOutput = gfsPhoto.findOne(map2Obj(map));
        List<GridFSDBFile> list = gfsPhoto.find(map2Obj(map));
        return list.get(0).getInputStream();
    }
    public GridFSDBFile findFileByName(String filename) {
        return getGridFS().findOne(filename);
    }
    public InputStream getFileInputStream(String filename) {
        return getGridFS().findOne(filename).getInputStream();
    }
    public long getFileLength(String filename) {
        return getGridFS().findOne(filename).getLength();
    }
    public GridFSDBFile findFirstFile(Map<String, Object> map) {
        return getGridFS().findOne(map2Obj(map));
    }
    public GridFS getGridFS() {
        GridFS gridFS = new GridFS(db);
        return gridFS;
    }
    public void removeFile(ObjectId id) {
        getGridFS().remove(id);
    }

    public static void main(String[] args) {

    }
    /**
     * 保存节点问题反馈关联文件
     * @param data
     * @param fileId
     * @param originalFilename
     * @param contentType
     * @param size
     * @param incidentId
     */
    public void saveNodeIncidentRelatedFile(byte[] data, String fileId,
                                            String originalFilename, String contentType, long size,
                                            String incidentId) {
        GridFS gridFS = getGridFS();
        GridFSFile gridFSFile = gridFS.createFile(data);
        gridFSFile.put("fileId", fileId);
        gridFSFile.put("fileName", originalFilename);
        gridFSFile.put("filename", originalFilename);
        gridFSFile.put("contentType", contentType);
        gridFSFile.put("fileSize", size);
        gridFSFile.put("incidentId", incidentId);
        gridFSFile.save();
    }

    /**
     * 保存设备关联文件
     * @param bData
     * @param fileId
     * @param originalFilename
     * @param contentType
     * @param size
     * @param equipmentId
     */
    public void saveEquipmentRelatedFile(byte[] bData, String fileId, String originalFilename, String contentType, long size, String equipmentId) {
        GridFS gridFS = getGridFS();
        GridFSFile gridFSFile = gridFS.createFile(bData);
        gridFSFile.put("fileId", fileId);
        gridFSFile.put("fileName", originalFilename);
        gridFSFile.put("filename", originalFilename);
        gridFSFile.put("contentType", contentType);
        gridFSFile.put("fileSize", size);
        gridFSFile.put("equipmentId", equipmentId);
        gridFSFile.save();
    }
}
