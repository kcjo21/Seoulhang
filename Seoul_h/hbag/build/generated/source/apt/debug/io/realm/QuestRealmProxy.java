package io.realm;


import android.annotation.TargetApi;
import android.os.Build;
import android.util.JsonReader;
import android.util.JsonToken;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.LinkView;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import io.realm.internal.android.JsonUtils;
import io.realm.log.RealmLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class QuestRealmProxy extends com.hbag.seoulhang.model.database.Quest
    implements RealmObjectProxy, QuestRealmProxyInterface {

    static final class QuestColumnInfo extends ColumnInfo
        implements Cloneable {

        public long questionIndex;
        public long answerIndex;
        public long questioncodeIndex;
        public long regioncodeIndex;
        public long contenttypeIndex;
        public long qresultIndex;

        QuestColumnInfo(String path, Table table) {
            final Map<String, Long> indicesMap = new HashMap<String, Long>(6);
            this.questionIndex = getValidColumnIndex(path, table, "Quest", "question");
            indicesMap.put("question", this.questionIndex);
            this.answerIndex = getValidColumnIndex(path, table, "Quest", "answer");
            indicesMap.put("answer", this.answerIndex);
            this.questioncodeIndex = getValidColumnIndex(path, table, "Quest", "questioncode");
            indicesMap.put("questioncode", this.questioncodeIndex);
            this.regioncodeIndex = getValidColumnIndex(path, table, "Quest", "regioncode");
            indicesMap.put("regioncode", this.regioncodeIndex);
            this.contenttypeIndex = getValidColumnIndex(path, table, "Quest", "contenttype");
            indicesMap.put("contenttype", this.contenttypeIndex);
            this.qresultIndex = getValidColumnIndex(path, table, "Quest", "qresult");
            indicesMap.put("qresult", this.qresultIndex);

            setIndicesMap(indicesMap);
        }

        @Override
        public final void copyColumnInfoFrom(ColumnInfo other) {
            final QuestColumnInfo otherInfo = (QuestColumnInfo) other;
            this.questionIndex = otherInfo.questionIndex;
            this.answerIndex = otherInfo.answerIndex;
            this.questioncodeIndex = otherInfo.questioncodeIndex;
            this.regioncodeIndex = otherInfo.regioncodeIndex;
            this.contenttypeIndex = otherInfo.contenttypeIndex;
            this.qresultIndex = otherInfo.qresultIndex;

            setIndicesMap(otherInfo.getIndicesMap());
        }

        @Override
        public final QuestColumnInfo clone() {
            return (QuestColumnInfo) super.clone();
        }

    }
    private QuestColumnInfo columnInfo;
    private ProxyState<com.hbag.seoulhang.model.database.Quest> proxyState;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("question");
        fieldNames.add("answer");
        fieldNames.add("questioncode");
        fieldNames.add("regioncode");
        fieldNames.add("contenttype");
        fieldNames.add("qresult");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    QuestRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (QuestColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.hbag.seoulhang.model.database.Quest>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @SuppressWarnings("cast")
    public String realmGet$question() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.questionIndex);
    }

    public void realmSet$question(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.questionIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.questionIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.questionIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.questionIndex, value);
    }

    @SuppressWarnings("cast")
    public String realmGet$answer() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.answerIndex);
    }

    public void realmSet$answer(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.answerIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.answerIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.answerIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.answerIndex, value);
    }

    @SuppressWarnings("cast")
    public int realmGet$questioncode() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.questioncodeIndex);
    }

    public void realmSet$questioncode(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.questioncodeIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.questioncodeIndex, value);
    }

    @SuppressWarnings("cast")
    public int realmGet$regioncode() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.regioncodeIndex);
    }

    public void realmSet$regioncode(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.regioncodeIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.regioncodeIndex, value);
    }

    @SuppressWarnings("cast")
    public String realmGet$contenttype() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.contenttypeIndex);
    }

    public void realmSet$contenttype(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.contenttypeIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.contenttypeIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.contenttypeIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.contenttypeIndex, value);
    }

    @SuppressWarnings("cast")
    public int realmGet$qresult() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.qresultIndex);
    }

    public void realmSet$qresult(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.qresultIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.qresultIndex, value);
    }

    public static RealmObjectSchema createRealmObjectSchema(RealmSchema realmSchema) {
        if (!realmSchema.contains("Quest")) {
            RealmObjectSchema realmObjectSchema = realmSchema.create("Quest");
            realmObjectSchema.add(new Property("question", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("answer", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("questioncode", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED));
            realmObjectSchema.add(new Property("regioncode", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED));
            realmObjectSchema.add(new Property("contenttype", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("qresult", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED));
            return realmObjectSchema;
        }
        return realmSchema.get("Quest");
    }

    public static Table initTable(SharedRealm sharedRealm) {
        if (!sharedRealm.hasTable("class_Quest")) {
            Table table = sharedRealm.getTable("class_Quest");
            table.addColumn(RealmFieldType.STRING, "question", Table.NULLABLE);
            table.addColumn(RealmFieldType.STRING, "answer", Table.NULLABLE);
            table.addColumn(RealmFieldType.INTEGER, "questioncode", Table.NOT_NULLABLE);
            table.addColumn(RealmFieldType.INTEGER, "regioncode", Table.NOT_NULLABLE);
            table.addColumn(RealmFieldType.STRING, "contenttype", Table.NULLABLE);
            table.addColumn(RealmFieldType.INTEGER, "qresult", Table.NOT_NULLABLE);
            table.setPrimaryKey("");
            return table;
        }
        return sharedRealm.getTable("class_Quest");
    }

    public static QuestColumnInfo validateTable(SharedRealm sharedRealm, boolean allowExtraColumns) {
        if (sharedRealm.hasTable("class_Quest")) {
            Table table = sharedRealm.getTable("class_Quest");
            final long columnCount = table.getColumnCount();
            if (columnCount != 6) {
                if (columnCount < 6) {
                    throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is less than expected - expected 6 but was " + columnCount);
                }
                if (allowExtraColumns) {
                    RealmLog.debug("Field count is more than expected - expected 6 but was %1$d", columnCount);
                } else {
                    throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is more than expected - expected 6 but was " + columnCount);
                }
            }
            Map<String, RealmFieldType> columnTypes = new HashMap<String, RealmFieldType>();
            for (long i = 0; i < columnCount; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }

            final QuestColumnInfo columnInfo = new QuestColumnInfo(sharedRealm.getPath(), table);

            if (table.hasPrimaryKey()) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary Key defined for field " + table.getColumnName(table.getPrimaryKey()) + " was removed.");
            }

            if (!columnTypes.containsKey("question")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'question' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("question") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'question' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.questionIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'question' is required. Either set @Required to field 'question' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("answer")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'answer' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("answer") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'answer' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.answerIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'answer' is required. Either set @Required to field 'answer' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("questioncode")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'questioncode' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("questioncode") != RealmFieldType.INTEGER) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'int' for field 'questioncode' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.questioncodeIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'questioncode' does support null values in the existing Realm file. Use corresponding boxed type for field 'questioncode' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("regioncode")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'regioncode' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("regioncode") != RealmFieldType.INTEGER) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'int' for field 'regioncode' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.regioncodeIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'regioncode' does support null values in the existing Realm file. Use corresponding boxed type for field 'regioncode' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("contenttype")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'contenttype' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("contenttype") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'contenttype' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.contenttypeIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'contenttype' is required. Either set @Required to field 'contenttype' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("qresult")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'qresult' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("qresult") != RealmFieldType.INTEGER) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'int' for field 'qresult' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.qresultIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'qresult' does support null values in the existing Realm file. Use corresponding boxed type for field 'qresult' or migrate using RealmObjectSchema.setNullable().");
            }
            return columnInfo;
        } else {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'Quest' class is missing from the schema for this Realm.");
        }
    }

    public static String getTableName() {
        return "class_Quest";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.hbag.seoulhang.model.database.Quest createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.hbag.seoulhang.model.database.Quest obj = realm.createObjectInternal(com.hbag.seoulhang.model.database.Quest.class, true, excludeFields);
        if (json.has("question")) {
            if (json.isNull("question")) {
                ((QuestRealmProxyInterface) obj).realmSet$question(null);
            } else {
                ((QuestRealmProxyInterface) obj).realmSet$question((String) json.getString("question"));
            }
        }
        if (json.has("answer")) {
            if (json.isNull("answer")) {
                ((QuestRealmProxyInterface) obj).realmSet$answer(null);
            } else {
                ((QuestRealmProxyInterface) obj).realmSet$answer((String) json.getString("answer"));
            }
        }
        if (json.has("questioncode")) {
            if (json.isNull("questioncode")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'questioncode' to null.");
            } else {
                ((QuestRealmProxyInterface) obj).realmSet$questioncode((int) json.getInt("questioncode"));
            }
        }
        if (json.has("regioncode")) {
            if (json.isNull("regioncode")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'regioncode' to null.");
            } else {
                ((QuestRealmProxyInterface) obj).realmSet$regioncode((int) json.getInt("regioncode"));
            }
        }
        if (json.has("contenttype")) {
            if (json.isNull("contenttype")) {
                ((QuestRealmProxyInterface) obj).realmSet$contenttype(null);
            } else {
                ((QuestRealmProxyInterface) obj).realmSet$contenttype((String) json.getString("contenttype"));
            }
        }
        if (json.has("qresult")) {
            if (json.isNull("qresult")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'qresult' to null.");
            } else {
                ((QuestRealmProxyInterface) obj).realmSet$qresult((int) json.getInt("qresult"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.hbag.seoulhang.model.database.Quest createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        com.hbag.seoulhang.model.database.Quest obj = new com.hbag.seoulhang.model.database.Quest();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("question")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((QuestRealmProxyInterface) obj).realmSet$question(null);
                } else {
                    ((QuestRealmProxyInterface) obj).realmSet$question((String) reader.nextString());
                }
            } else if (name.equals("answer")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((QuestRealmProxyInterface) obj).realmSet$answer(null);
                } else {
                    ((QuestRealmProxyInterface) obj).realmSet$answer((String) reader.nextString());
                }
            } else if (name.equals("questioncode")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'questioncode' to null.");
                } else {
                    ((QuestRealmProxyInterface) obj).realmSet$questioncode((int) reader.nextInt());
                }
            } else if (name.equals("regioncode")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'regioncode' to null.");
                } else {
                    ((QuestRealmProxyInterface) obj).realmSet$regioncode((int) reader.nextInt());
                }
            } else if (name.equals("contenttype")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((QuestRealmProxyInterface) obj).realmSet$contenttype(null);
                } else {
                    ((QuestRealmProxyInterface) obj).realmSet$contenttype((String) reader.nextString());
                }
            } else if (name.equals("qresult")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'qresult' to null.");
                } else {
                    ((QuestRealmProxyInterface) obj).realmSet$qresult((int) reader.nextInt());
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        obj = realm.copyToRealm(obj);
        return obj;
    }

    public static com.hbag.seoulhang.model.database.Quest copyOrUpdate(Realm realm, com.hbag.seoulhang.model.database.Quest object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return object;
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (com.hbag.seoulhang.model.database.Quest) cachedRealmObject;
        } else {
            return copy(realm, object, update, cache);
        }
    }

    public static com.hbag.seoulhang.model.database.Quest copy(Realm realm, com.hbag.seoulhang.model.database.Quest newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.hbag.seoulhang.model.database.Quest) cachedRealmObject;
        } else {
            // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
            com.hbag.seoulhang.model.database.Quest realmObject = realm.createObjectInternal(com.hbag.seoulhang.model.database.Quest.class, false, Collections.<String>emptyList());
            cache.put(newObject, (RealmObjectProxy) realmObject);
            ((QuestRealmProxyInterface) realmObject).realmSet$question(((QuestRealmProxyInterface) newObject).realmGet$question());
            ((QuestRealmProxyInterface) realmObject).realmSet$answer(((QuestRealmProxyInterface) newObject).realmGet$answer());
            ((QuestRealmProxyInterface) realmObject).realmSet$questioncode(((QuestRealmProxyInterface) newObject).realmGet$questioncode());
            ((QuestRealmProxyInterface) realmObject).realmSet$regioncode(((QuestRealmProxyInterface) newObject).realmGet$regioncode());
            ((QuestRealmProxyInterface) realmObject).realmSet$contenttype(((QuestRealmProxyInterface) newObject).realmGet$contenttype());
            ((QuestRealmProxyInterface) realmObject).realmSet$qresult(((QuestRealmProxyInterface) newObject).realmGet$qresult());
            return realmObject;
        }
    }

    public static long insert(Realm realm, com.hbag.seoulhang.model.database.Quest object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.hbag.seoulhang.model.database.Quest.class);
        long tableNativePtr = table.getNativeTablePointer();
        QuestColumnInfo columnInfo = (QuestColumnInfo) realm.schema.getColumnInfo(com.hbag.seoulhang.model.database.Quest.class);
        long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
        cache.put(object, rowIndex);
        String realmGet$question = ((QuestRealmProxyInterface)object).realmGet$question();
        if (realmGet$question != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.questionIndex, rowIndex, realmGet$question, false);
        }
        String realmGet$answer = ((QuestRealmProxyInterface)object).realmGet$answer();
        if (realmGet$answer != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.answerIndex, rowIndex, realmGet$answer, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.questioncodeIndex, rowIndex, ((QuestRealmProxyInterface)object).realmGet$questioncode(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.regioncodeIndex, rowIndex, ((QuestRealmProxyInterface)object).realmGet$regioncode(), false);
        String realmGet$contenttype = ((QuestRealmProxyInterface)object).realmGet$contenttype();
        if (realmGet$contenttype != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.contenttypeIndex, rowIndex, realmGet$contenttype, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.qresultIndex, rowIndex, ((QuestRealmProxyInterface)object).realmGet$qresult(), false);
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.hbag.seoulhang.model.database.Quest.class);
        long tableNativePtr = table.getNativeTablePointer();
        QuestColumnInfo columnInfo = (QuestColumnInfo) realm.schema.getColumnInfo(com.hbag.seoulhang.model.database.Quest.class);
        com.hbag.seoulhang.model.database.Quest object = null;
        while (objects.hasNext()) {
            object = (com.hbag.seoulhang.model.database.Quest) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
                cache.put(object, rowIndex);
                String realmGet$question = ((QuestRealmProxyInterface)object).realmGet$question();
                if (realmGet$question != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.questionIndex, rowIndex, realmGet$question, false);
                }
                String realmGet$answer = ((QuestRealmProxyInterface)object).realmGet$answer();
                if (realmGet$answer != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.answerIndex, rowIndex, realmGet$answer, false);
                }
                Table.nativeSetLong(tableNativePtr, columnInfo.questioncodeIndex, rowIndex, ((QuestRealmProxyInterface)object).realmGet$questioncode(), false);
                Table.nativeSetLong(tableNativePtr, columnInfo.regioncodeIndex, rowIndex, ((QuestRealmProxyInterface)object).realmGet$regioncode(), false);
                String realmGet$contenttype = ((QuestRealmProxyInterface)object).realmGet$contenttype();
                if (realmGet$contenttype != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.contenttypeIndex, rowIndex, realmGet$contenttype, false);
                }
                Table.nativeSetLong(tableNativePtr, columnInfo.qresultIndex, rowIndex, ((QuestRealmProxyInterface)object).realmGet$qresult(), false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.hbag.seoulhang.model.database.Quest object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.hbag.seoulhang.model.database.Quest.class);
        long tableNativePtr = table.getNativeTablePointer();
        QuestColumnInfo columnInfo = (QuestColumnInfo) realm.schema.getColumnInfo(com.hbag.seoulhang.model.database.Quest.class);
        long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
        cache.put(object, rowIndex);
        String realmGet$question = ((QuestRealmProxyInterface)object).realmGet$question();
        if (realmGet$question != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.questionIndex, rowIndex, realmGet$question, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.questionIndex, rowIndex, false);
        }
        String realmGet$answer = ((QuestRealmProxyInterface)object).realmGet$answer();
        if (realmGet$answer != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.answerIndex, rowIndex, realmGet$answer, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.answerIndex, rowIndex, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.questioncodeIndex, rowIndex, ((QuestRealmProxyInterface)object).realmGet$questioncode(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.regioncodeIndex, rowIndex, ((QuestRealmProxyInterface)object).realmGet$regioncode(), false);
        String realmGet$contenttype = ((QuestRealmProxyInterface)object).realmGet$contenttype();
        if (realmGet$contenttype != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.contenttypeIndex, rowIndex, realmGet$contenttype, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.contenttypeIndex, rowIndex, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.qresultIndex, rowIndex, ((QuestRealmProxyInterface)object).realmGet$qresult(), false);
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.hbag.seoulhang.model.database.Quest.class);
        long tableNativePtr = table.getNativeTablePointer();
        QuestColumnInfo columnInfo = (QuestColumnInfo) realm.schema.getColumnInfo(com.hbag.seoulhang.model.database.Quest.class);
        com.hbag.seoulhang.model.database.Quest object = null;
        while (objects.hasNext()) {
            object = (com.hbag.seoulhang.model.database.Quest) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
                cache.put(object, rowIndex);
                String realmGet$question = ((QuestRealmProxyInterface)object).realmGet$question();
                if (realmGet$question != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.questionIndex, rowIndex, realmGet$question, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.questionIndex, rowIndex, false);
                }
                String realmGet$answer = ((QuestRealmProxyInterface)object).realmGet$answer();
                if (realmGet$answer != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.answerIndex, rowIndex, realmGet$answer, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.answerIndex, rowIndex, false);
                }
                Table.nativeSetLong(tableNativePtr, columnInfo.questioncodeIndex, rowIndex, ((QuestRealmProxyInterface)object).realmGet$questioncode(), false);
                Table.nativeSetLong(tableNativePtr, columnInfo.regioncodeIndex, rowIndex, ((QuestRealmProxyInterface)object).realmGet$regioncode(), false);
                String realmGet$contenttype = ((QuestRealmProxyInterface)object).realmGet$contenttype();
                if (realmGet$contenttype != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.contenttypeIndex, rowIndex, realmGet$contenttype, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.contenttypeIndex, rowIndex, false);
                }
                Table.nativeSetLong(tableNativePtr, columnInfo.qresultIndex, rowIndex, ((QuestRealmProxyInterface)object).realmGet$qresult(), false);
            }
        }
    }

    public static com.hbag.seoulhang.model.database.Quest createDetachedCopy(com.hbag.seoulhang.model.database.Quest realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.hbag.seoulhang.model.database.Quest unmanagedObject;
        if (cachedObject != null) {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.hbag.seoulhang.model.database.Quest)cachedObject.object;
            } else {
                unmanagedObject = (com.hbag.seoulhang.model.database.Quest)cachedObject.object;
                cachedObject.minDepth = currentDepth;
            }
        } else {
            unmanagedObject = new com.hbag.seoulhang.model.database.Quest();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        }
        ((QuestRealmProxyInterface) unmanagedObject).realmSet$question(((QuestRealmProxyInterface) realmObject).realmGet$question());
        ((QuestRealmProxyInterface) unmanagedObject).realmSet$answer(((QuestRealmProxyInterface) realmObject).realmGet$answer());
        ((QuestRealmProxyInterface) unmanagedObject).realmSet$questioncode(((QuestRealmProxyInterface) realmObject).realmGet$questioncode());
        ((QuestRealmProxyInterface) unmanagedObject).realmSet$regioncode(((QuestRealmProxyInterface) realmObject).realmGet$regioncode());
        ((QuestRealmProxyInterface) unmanagedObject).realmSet$contenttype(((QuestRealmProxyInterface) realmObject).realmGet$contenttype());
        ((QuestRealmProxyInterface) unmanagedObject).realmSet$qresult(((QuestRealmProxyInterface) realmObject).realmGet$qresult());
        return unmanagedObject;
    }

    @Override
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("Quest = [");
        stringBuilder.append("{question:");
        stringBuilder.append(realmGet$question() != null ? realmGet$question() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{answer:");
        stringBuilder.append(realmGet$answer() != null ? realmGet$answer() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{questioncode:");
        stringBuilder.append(realmGet$questioncode());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{regioncode:");
        stringBuilder.append(realmGet$regioncode());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{contenttype:");
        stringBuilder.append(realmGet$contenttype() != null ? realmGet$contenttype() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{qresult:");
        stringBuilder.append(realmGet$qresult());
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public ProxyState realmGet$proxyState() {
        return proxyState;
    }

    @Override
    public int hashCode() {
        String realmName = proxyState.getRealm$realm().getPath();
        String tableName = proxyState.getRow$realm().getTable().getName();
        long rowIndex = proxyState.getRow$realm().getIndex();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (rowIndex ^ (rowIndex >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestRealmProxy aQuest = (QuestRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aQuest.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aQuest.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aQuest.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }

}
