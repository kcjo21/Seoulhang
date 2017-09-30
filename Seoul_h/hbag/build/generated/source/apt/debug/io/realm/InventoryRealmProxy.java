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

public class InventoryRealmProxy extends com.hbag.seoulhang.model.database.Inventory
    implements RealmObjectProxy, InventoryRealmProxyInterface {

    static final class InventoryColumnInfo extends ColumnInfo
        implements Cloneable {

        public long regionnameIndex;
        public long questioncodeIndex;
        public long questionIndex;
        public long answerIndex;
        public long playercodeIndex;
        public long playernameIndex;
        public long pointIndex;
        public long hintIndex;
        public long gradeIndex;

        InventoryColumnInfo(String path, Table table) {
            final Map<String, Long> indicesMap = new HashMap<String, Long>(9);
            this.regionnameIndex = getValidColumnIndex(path, table, "Inventory", "regionname");
            indicesMap.put("regionname", this.regionnameIndex);
            this.questioncodeIndex = getValidColumnIndex(path, table, "Inventory", "questioncode");
            indicesMap.put("questioncode", this.questioncodeIndex);
            this.questionIndex = getValidColumnIndex(path, table, "Inventory", "question");
            indicesMap.put("question", this.questionIndex);
            this.answerIndex = getValidColumnIndex(path, table, "Inventory", "answer");
            indicesMap.put("answer", this.answerIndex);
            this.playercodeIndex = getValidColumnIndex(path, table, "Inventory", "playercode");
            indicesMap.put("playercode", this.playercodeIndex);
            this.playernameIndex = getValidColumnIndex(path, table, "Inventory", "playername");
            indicesMap.put("playername", this.playernameIndex);
            this.pointIndex = getValidColumnIndex(path, table, "Inventory", "point");
            indicesMap.put("point", this.pointIndex);
            this.hintIndex = getValidColumnIndex(path, table, "Inventory", "hint");
            indicesMap.put("hint", this.hintIndex);
            this.gradeIndex = getValidColumnIndex(path, table, "Inventory", "grade");
            indicesMap.put("grade", this.gradeIndex);

            setIndicesMap(indicesMap);
        }

        @Override
        public final void copyColumnInfoFrom(ColumnInfo other) {
            final InventoryColumnInfo otherInfo = (InventoryColumnInfo) other;
            this.regionnameIndex = otherInfo.regionnameIndex;
            this.questioncodeIndex = otherInfo.questioncodeIndex;
            this.questionIndex = otherInfo.questionIndex;
            this.answerIndex = otherInfo.answerIndex;
            this.playercodeIndex = otherInfo.playercodeIndex;
            this.playernameIndex = otherInfo.playernameIndex;
            this.pointIndex = otherInfo.pointIndex;
            this.hintIndex = otherInfo.hintIndex;
            this.gradeIndex = otherInfo.gradeIndex;

            setIndicesMap(otherInfo.getIndicesMap());
        }

        @Override
        public final InventoryColumnInfo clone() {
            return (InventoryColumnInfo) super.clone();
        }

    }
    private InventoryColumnInfo columnInfo;
    private ProxyState<com.hbag.seoulhang.model.database.Inventory> proxyState;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("regionname");
        fieldNames.add("questioncode");
        fieldNames.add("question");
        fieldNames.add("answer");
        fieldNames.add("playercode");
        fieldNames.add("playername");
        fieldNames.add("point");
        fieldNames.add("hint");
        fieldNames.add("grade");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    InventoryRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (InventoryColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.hbag.seoulhang.model.database.Inventory>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @SuppressWarnings("cast")
    public String realmGet$regionname() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.regionnameIndex);
    }

    public void realmSet$regionname(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.regionnameIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.regionnameIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.regionnameIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.regionnameIndex, value);
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
    public String realmGet$playercode() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.playercodeIndex);
    }

    public void realmSet$playercode(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.playercodeIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.playercodeIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.playercodeIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.playercodeIndex, value);
    }

    @SuppressWarnings("cast")
    public String realmGet$playername() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.playernameIndex);
    }

    public void realmSet$playername(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.playernameIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.playernameIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.playernameIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.playernameIndex, value);
    }

    @SuppressWarnings("cast")
    public int realmGet$point() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.pointIndex);
    }

    public void realmSet$point(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.pointIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.pointIndex, value);
    }

    @SuppressWarnings("cast")
    public int realmGet$hint() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.hintIndex);
    }

    public void realmSet$hint(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.hintIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.hintIndex, value);
    }

    @SuppressWarnings("cast")
    public String realmGet$grade() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.gradeIndex);
    }

    public void realmSet$grade(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.gradeIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.gradeIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.gradeIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.gradeIndex, value);
    }

    public static RealmObjectSchema createRealmObjectSchema(RealmSchema realmSchema) {
        if (!realmSchema.contains("Inventory")) {
            RealmObjectSchema realmObjectSchema = realmSchema.create("Inventory");
            realmObjectSchema.add(new Property("regionname", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("questioncode", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED));
            realmObjectSchema.add(new Property("question", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("answer", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("playercode", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("playername", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("point", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED));
            realmObjectSchema.add(new Property("hint", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED));
            realmObjectSchema.add(new Property("grade", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            return realmObjectSchema;
        }
        return realmSchema.get("Inventory");
    }

    public static Table initTable(SharedRealm sharedRealm) {
        if (!sharedRealm.hasTable("class_Inventory")) {
            Table table = sharedRealm.getTable("class_Inventory");
            table.addColumn(RealmFieldType.STRING, "regionname", Table.NULLABLE);
            table.addColumn(RealmFieldType.INTEGER, "questioncode", Table.NOT_NULLABLE);
            table.addColumn(RealmFieldType.STRING, "question", Table.NULLABLE);
            table.addColumn(RealmFieldType.STRING, "answer", Table.NULLABLE);
            table.addColumn(RealmFieldType.STRING, "playercode", Table.NULLABLE);
            table.addColumn(RealmFieldType.STRING, "playername", Table.NULLABLE);
            table.addColumn(RealmFieldType.INTEGER, "point", Table.NOT_NULLABLE);
            table.addColumn(RealmFieldType.INTEGER, "hint", Table.NOT_NULLABLE);
            table.addColumn(RealmFieldType.STRING, "grade", Table.NULLABLE);
            table.setPrimaryKey("");
            return table;
        }
        return sharedRealm.getTable("class_Inventory");
    }

    public static InventoryColumnInfo validateTable(SharedRealm sharedRealm, boolean allowExtraColumns) {
        if (sharedRealm.hasTable("class_Inventory")) {
            Table table = sharedRealm.getTable("class_Inventory");
            final long columnCount = table.getColumnCount();
            if (columnCount != 9) {
                if (columnCount < 9) {
                    throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is less than expected - expected 9 but was " + columnCount);
                }
                if (allowExtraColumns) {
                    RealmLog.debug("Field count is more than expected - expected 9 but was %1$d", columnCount);
                } else {
                    throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is more than expected - expected 9 but was " + columnCount);
                }
            }
            Map<String, RealmFieldType> columnTypes = new HashMap<String, RealmFieldType>();
            for (long i = 0; i < columnCount; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }

            final InventoryColumnInfo columnInfo = new InventoryColumnInfo(sharedRealm.getPath(), table);

            if (table.hasPrimaryKey()) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary Key defined for field " + table.getColumnName(table.getPrimaryKey()) + " was removed.");
            }

            if (!columnTypes.containsKey("regionname")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'regionname' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("regionname") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'regionname' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.regionnameIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'regionname' is required. Either set @Required to field 'regionname' or migrate using RealmObjectSchema.setNullable().");
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
            if (!columnTypes.containsKey("playercode")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'playercode' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("playercode") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'playercode' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.playercodeIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'playercode' is required. Either set @Required to field 'playercode' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("playername")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'playername' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("playername") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'playername' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.playernameIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'playername' is required. Either set @Required to field 'playername' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("point")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'point' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("point") != RealmFieldType.INTEGER) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'int' for field 'point' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.pointIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'point' does support null values in the existing Realm file. Use corresponding boxed type for field 'point' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("hint")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'hint' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("hint") != RealmFieldType.INTEGER) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'int' for field 'hint' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.hintIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'hint' does support null values in the existing Realm file. Use corresponding boxed type for field 'hint' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("grade")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'grade' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("grade") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'grade' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.gradeIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'grade' is required. Either set @Required to field 'grade' or migrate using RealmObjectSchema.setNullable().");
            }
            return columnInfo;
        } else {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'Inventory' class is missing from the schema for this Realm.");
        }
    }

    public static String getTableName() {
        return "class_Inventory";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.hbag.seoulhang.model.database.Inventory createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.hbag.seoulhang.model.database.Inventory obj = realm.createObjectInternal(com.hbag.seoulhang.model.database.Inventory.class, true, excludeFields);
        if (json.has("regionname")) {
            if (json.isNull("regionname")) {
                ((InventoryRealmProxyInterface) obj).realmSet$regionname(null);
            } else {
                ((InventoryRealmProxyInterface) obj).realmSet$regionname((String) json.getString("regionname"));
            }
        }
        if (json.has("questioncode")) {
            if (json.isNull("questioncode")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'questioncode' to null.");
            } else {
                ((InventoryRealmProxyInterface) obj).realmSet$questioncode((int) json.getInt("questioncode"));
            }
        }
        if (json.has("question")) {
            if (json.isNull("question")) {
                ((InventoryRealmProxyInterface) obj).realmSet$question(null);
            } else {
                ((InventoryRealmProxyInterface) obj).realmSet$question((String) json.getString("question"));
            }
        }
        if (json.has("answer")) {
            if (json.isNull("answer")) {
                ((InventoryRealmProxyInterface) obj).realmSet$answer(null);
            } else {
                ((InventoryRealmProxyInterface) obj).realmSet$answer((String) json.getString("answer"));
            }
        }
        if (json.has("playercode")) {
            if (json.isNull("playercode")) {
                ((InventoryRealmProxyInterface) obj).realmSet$playercode(null);
            } else {
                ((InventoryRealmProxyInterface) obj).realmSet$playercode((String) json.getString("playercode"));
            }
        }
        if (json.has("playername")) {
            if (json.isNull("playername")) {
                ((InventoryRealmProxyInterface) obj).realmSet$playername(null);
            } else {
                ((InventoryRealmProxyInterface) obj).realmSet$playername((String) json.getString("playername"));
            }
        }
        if (json.has("point")) {
            if (json.isNull("point")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'point' to null.");
            } else {
                ((InventoryRealmProxyInterface) obj).realmSet$point((int) json.getInt("point"));
            }
        }
        if (json.has("hint")) {
            if (json.isNull("hint")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'hint' to null.");
            } else {
                ((InventoryRealmProxyInterface) obj).realmSet$hint((int) json.getInt("hint"));
            }
        }
        if (json.has("grade")) {
            if (json.isNull("grade")) {
                ((InventoryRealmProxyInterface) obj).realmSet$grade(null);
            } else {
                ((InventoryRealmProxyInterface) obj).realmSet$grade((String) json.getString("grade"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.hbag.seoulhang.model.database.Inventory createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        com.hbag.seoulhang.model.database.Inventory obj = new com.hbag.seoulhang.model.database.Inventory();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("regionname")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((InventoryRealmProxyInterface) obj).realmSet$regionname(null);
                } else {
                    ((InventoryRealmProxyInterface) obj).realmSet$regionname((String) reader.nextString());
                }
            } else if (name.equals("questioncode")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'questioncode' to null.");
                } else {
                    ((InventoryRealmProxyInterface) obj).realmSet$questioncode((int) reader.nextInt());
                }
            } else if (name.equals("question")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((InventoryRealmProxyInterface) obj).realmSet$question(null);
                } else {
                    ((InventoryRealmProxyInterface) obj).realmSet$question((String) reader.nextString());
                }
            } else if (name.equals("answer")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((InventoryRealmProxyInterface) obj).realmSet$answer(null);
                } else {
                    ((InventoryRealmProxyInterface) obj).realmSet$answer((String) reader.nextString());
                }
            } else if (name.equals("playercode")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((InventoryRealmProxyInterface) obj).realmSet$playercode(null);
                } else {
                    ((InventoryRealmProxyInterface) obj).realmSet$playercode((String) reader.nextString());
                }
            } else if (name.equals("playername")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((InventoryRealmProxyInterface) obj).realmSet$playername(null);
                } else {
                    ((InventoryRealmProxyInterface) obj).realmSet$playername((String) reader.nextString());
                }
            } else if (name.equals("point")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'point' to null.");
                } else {
                    ((InventoryRealmProxyInterface) obj).realmSet$point((int) reader.nextInt());
                }
            } else if (name.equals("hint")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'hint' to null.");
                } else {
                    ((InventoryRealmProxyInterface) obj).realmSet$hint((int) reader.nextInt());
                }
            } else if (name.equals("grade")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((InventoryRealmProxyInterface) obj).realmSet$grade(null);
                } else {
                    ((InventoryRealmProxyInterface) obj).realmSet$grade((String) reader.nextString());
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        obj = realm.copyToRealm(obj);
        return obj;
    }

    public static com.hbag.seoulhang.model.database.Inventory copyOrUpdate(Realm realm, com.hbag.seoulhang.model.database.Inventory object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return object;
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (com.hbag.seoulhang.model.database.Inventory) cachedRealmObject;
        } else {
            return copy(realm, object, update, cache);
        }
    }

    public static com.hbag.seoulhang.model.database.Inventory copy(Realm realm, com.hbag.seoulhang.model.database.Inventory newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.hbag.seoulhang.model.database.Inventory) cachedRealmObject;
        } else {
            // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
            com.hbag.seoulhang.model.database.Inventory realmObject = realm.createObjectInternal(com.hbag.seoulhang.model.database.Inventory.class, false, Collections.<String>emptyList());
            cache.put(newObject, (RealmObjectProxy) realmObject);
            ((InventoryRealmProxyInterface) realmObject).realmSet$regionname(((InventoryRealmProxyInterface) newObject).realmGet$regionname());
            ((InventoryRealmProxyInterface) realmObject).realmSet$questioncode(((InventoryRealmProxyInterface) newObject).realmGet$questioncode());
            ((InventoryRealmProxyInterface) realmObject).realmSet$question(((InventoryRealmProxyInterface) newObject).realmGet$question());
            ((InventoryRealmProxyInterface) realmObject).realmSet$answer(((InventoryRealmProxyInterface) newObject).realmGet$answer());
            ((InventoryRealmProxyInterface) realmObject).realmSet$playercode(((InventoryRealmProxyInterface) newObject).realmGet$playercode());
            ((InventoryRealmProxyInterface) realmObject).realmSet$playername(((InventoryRealmProxyInterface) newObject).realmGet$playername());
            ((InventoryRealmProxyInterface) realmObject).realmSet$point(((InventoryRealmProxyInterface) newObject).realmGet$point());
            ((InventoryRealmProxyInterface) realmObject).realmSet$hint(((InventoryRealmProxyInterface) newObject).realmGet$hint());
            ((InventoryRealmProxyInterface) realmObject).realmSet$grade(((InventoryRealmProxyInterface) newObject).realmGet$grade());
            return realmObject;
        }
    }

    public static long insert(Realm realm, com.hbag.seoulhang.model.database.Inventory object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.hbag.seoulhang.model.database.Inventory.class);
        long tableNativePtr = table.getNativeTablePointer();
        InventoryColumnInfo columnInfo = (InventoryColumnInfo) realm.schema.getColumnInfo(com.hbag.seoulhang.model.database.Inventory.class);
        long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
        cache.put(object, rowIndex);
        String realmGet$regionname = ((InventoryRealmProxyInterface)object).realmGet$regionname();
        if (realmGet$regionname != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.regionnameIndex, rowIndex, realmGet$regionname, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.questioncodeIndex, rowIndex, ((InventoryRealmProxyInterface)object).realmGet$questioncode(), false);
        String realmGet$question = ((InventoryRealmProxyInterface)object).realmGet$question();
        if (realmGet$question != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.questionIndex, rowIndex, realmGet$question, false);
        }
        String realmGet$answer = ((InventoryRealmProxyInterface)object).realmGet$answer();
        if (realmGet$answer != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.answerIndex, rowIndex, realmGet$answer, false);
        }
        String realmGet$playercode = ((InventoryRealmProxyInterface)object).realmGet$playercode();
        if (realmGet$playercode != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.playercodeIndex, rowIndex, realmGet$playercode, false);
        }
        String realmGet$playername = ((InventoryRealmProxyInterface)object).realmGet$playername();
        if (realmGet$playername != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.playernameIndex, rowIndex, realmGet$playername, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.pointIndex, rowIndex, ((InventoryRealmProxyInterface)object).realmGet$point(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.hintIndex, rowIndex, ((InventoryRealmProxyInterface)object).realmGet$hint(), false);
        String realmGet$grade = ((InventoryRealmProxyInterface)object).realmGet$grade();
        if (realmGet$grade != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.gradeIndex, rowIndex, realmGet$grade, false);
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.hbag.seoulhang.model.database.Inventory.class);
        long tableNativePtr = table.getNativeTablePointer();
        InventoryColumnInfo columnInfo = (InventoryColumnInfo) realm.schema.getColumnInfo(com.hbag.seoulhang.model.database.Inventory.class);
        com.hbag.seoulhang.model.database.Inventory object = null;
        while (objects.hasNext()) {
            object = (com.hbag.seoulhang.model.database.Inventory) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
                cache.put(object, rowIndex);
                String realmGet$regionname = ((InventoryRealmProxyInterface)object).realmGet$regionname();
                if (realmGet$regionname != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.regionnameIndex, rowIndex, realmGet$regionname, false);
                }
                Table.nativeSetLong(tableNativePtr, columnInfo.questioncodeIndex, rowIndex, ((InventoryRealmProxyInterface)object).realmGet$questioncode(), false);
                String realmGet$question = ((InventoryRealmProxyInterface)object).realmGet$question();
                if (realmGet$question != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.questionIndex, rowIndex, realmGet$question, false);
                }
                String realmGet$answer = ((InventoryRealmProxyInterface)object).realmGet$answer();
                if (realmGet$answer != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.answerIndex, rowIndex, realmGet$answer, false);
                }
                String realmGet$playercode = ((InventoryRealmProxyInterface)object).realmGet$playercode();
                if (realmGet$playercode != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.playercodeIndex, rowIndex, realmGet$playercode, false);
                }
                String realmGet$playername = ((InventoryRealmProxyInterface)object).realmGet$playername();
                if (realmGet$playername != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.playernameIndex, rowIndex, realmGet$playername, false);
                }
                Table.nativeSetLong(tableNativePtr, columnInfo.pointIndex, rowIndex, ((InventoryRealmProxyInterface)object).realmGet$point(), false);
                Table.nativeSetLong(tableNativePtr, columnInfo.hintIndex, rowIndex, ((InventoryRealmProxyInterface)object).realmGet$hint(), false);
                String realmGet$grade = ((InventoryRealmProxyInterface)object).realmGet$grade();
                if (realmGet$grade != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.gradeIndex, rowIndex, realmGet$grade, false);
                }
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.hbag.seoulhang.model.database.Inventory object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.hbag.seoulhang.model.database.Inventory.class);
        long tableNativePtr = table.getNativeTablePointer();
        InventoryColumnInfo columnInfo = (InventoryColumnInfo) realm.schema.getColumnInfo(com.hbag.seoulhang.model.database.Inventory.class);
        long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
        cache.put(object, rowIndex);
        String realmGet$regionname = ((InventoryRealmProxyInterface)object).realmGet$regionname();
        if (realmGet$regionname != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.regionnameIndex, rowIndex, realmGet$regionname, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.regionnameIndex, rowIndex, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.questioncodeIndex, rowIndex, ((InventoryRealmProxyInterface)object).realmGet$questioncode(), false);
        String realmGet$question = ((InventoryRealmProxyInterface)object).realmGet$question();
        if (realmGet$question != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.questionIndex, rowIndex, realmGet$question, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.questionIndex, rowIndex, false);
        }
        String realmGet$answer = ((InventoryRealmProxyInterface)object).realmGet$answer();
        if (realmGet$answer != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.answerIndex, rowIndex, realmGet$answer, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.answerIndex, rowIndex, false);
        }
        String realmGet$playercode = ((InventoryRealmProxyInterface)object).realmGet$playercode();
        if (realmGet$playercode != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.playercodeIndex, rowIndex, realmGet$playercode, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.playercodeIndex, rowIndex, false);
        }
        String realmGet$playername = ((InventoryRealmProxyInterface)object).realmGet$playername();
        if (realmGet$playername != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.playernameIndex, rowIndex, realmGet$playername, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.playernameIndex, rowIndex, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.pointIndex, rowIndex, ((InventoryRealmProxyInterface)object).realmGet$point(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.hintIndex, rowIndex, ((InventoryRealmProxyInterface)object).realmGet$hint(), false);
        String realmGet$grade = ((InventoryRealmProxyInterface)object).realmGet$grade();
        if (realmGet$grade != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.gradeIndex, rowIndex, realmGet$grade, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.gradeIndex, rowIndex, false);
        }
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.hbag.seoulhang.model.database.Inventory.class);
        long tableNativePtr = table.getNativeTablePointer();
        InventoryColumnInfo columnInfo = (InventoryColumnInfo) realm.schema.getColumnInfo(com.hbag.seoulhang.model.database.Inventory.class);
        com.hbag.seoulhang.model.database.Inventory object = null;
        while (objects.hasNext()) {
            object = (com.hbag.seoulhang.model.database.Inventory) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
                cache.put(object, rowIndex);
                String realmGet$regionname = ((InventoryRealmProxyInterface)object).realmGet$regionname();
                if (realmGet$regionname != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.regionnameIndex, rowIndex, realmGet$regionname, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.regionnameIndex, rowIndex, false);
                }
                Table.nativeSetLong(tableNativePtr, columnInfo.questioncodeIndex, rowIndex, ((InventoryRealmProxyInterface)object).realmGet$questioncode(), false);
                String realmGet$question = ((InventoryRealmProxyInterface)object).realmGet$question();
                if (realmGet$question != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.questionIndex, rowIndex, realmGet$question, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.questionIndex, rowIndex, false);
                }
                String realmGet$answer = ((InventoryRealmProxyInterface)object).realmGet$answer();
                if (realmGet$answer != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.answerIndex, rowIndex, realmGet$answer, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.answerIndex, rowIndex, false);
                }
                String realmGet$playercode = ((InventoryRealmProxyInterface)object).realmGet$playercode();
                if (realmGet$playercode != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.playercodeIndex, rowIndex, realmGet$playercode, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.playercodeIndex, rowIndex, false);
                }
                String realmGet$playername = ((InventoryRealmProxyInterface)object).realmGet$playername();
                if (realmGet$playername != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.playernameIndex, rowIndex, realmGet$playername, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.playernameIndex, rowIndex, false);
                }
                Table.nativeSetLong(tableNativePtr, columnInfo.pointIndex, rowIndex, ((InventoryRealmProxyInterface)object).realmGet$point(), false);
                Table.nativeSetLong(tableNativePtr, columnInfo.hintIndex, rowIndex, ((InventoryRealmProxyInterface)object).realmGet$hint(), false);
                String realmGet$grade = ((InventoryRealmProxyInterface)object).realmGet$grade();
                if (realmGet$grade != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.gradeIndex, rowIndex, realmGet$grade, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.gradeIndex, rowIndex, false);
                }
            }
        }
    }

    public static com.hbag.seoulhang.model.database.Inventory createDetachedCopy(com.hbag.seoulhang.model.database.Inventory realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.hbag.seoulhang.model.database.Inventory unmanagedObject;
        if (cachedObject != null) {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.hbag.seoulhang.model.database.Inventory)cachedObject.object;
            } else {
                unmanagedObject = (com.hbag.seoulhang.model.database.Inventory)cachedObject.object;
                cachedObject.minDepth = currentDepth;
            }
        } else {
            unmanagedObject = new com.hbag.seoulhang.model.database.Inventory();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        }
        ((InventoryRealmProxyInterface) unmanagedObject).realmSet$regionname(((InventoryRealmProxyInterface) realmObject).realmGet$regionname());
        ((InventoryRealmProxyInterface) unmanagedObject).realmSet$questioncode(((InventoryRealmProxyInterface) realmObject).realmGet$questioncode());
        ((InventoryRealmProxyInterface) unmanagedObject).realmSet$question(((InventoryRealmProxyInterface) realmObject).realmGet$question());
        ((InventoryRealmProxyInterface) unmanagedObject).realmSet$answer(((InventoryRealmProxyInterface) realmObject).realmGet$answer());
        ((InventoryRealmProxyInterface) unmanagedObject).realmSet$playercode(((InventoryRealmProxyInterface) realmObject).realmGet$playercode());
        ((InventoryRealmProxyInterface) unmanagedObject).realmSet$playername(((InventoryRealmProxyInterface) realmObject).realmGet$playername());
        ((InventoryRealmProxyInterface) unmanagedObject).realmSet$point(((InventoryRealmProxyInterface) realmObject).realmGet$point());
        ((InventoryRealmProxyInterface) unmanagedObject).realmSet$hint(((InventoryRealmProxyInterface) realmObject).realmGet$hint());
        ((InventoryRealmProxyInterface) unmanagedObject).realmSet$grade(((InventoryRealmProxyInterface) realmObject).realmGet$grade());
        return unmanagedObject;
    }

    @Override
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("Inventory = [");
        stringBuilder.append("{regionname:");
        stringBuilder.append(realmGet$regionname() != null ? realmGet$regionname() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{questioncode:");
        stringBuilder.append(realmGet$questioncode());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{question:");
        stringBuilder.append(realmGet$question() != null ? realmGet$question() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{answer:");
        stringBuilder.append(realmGet$answer() != null ? realmGet$answer() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{playercode:");
        stringBuilder.append(realmGet$playercode() != null ? realmGet$playercode() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{playername:");
        stringBuilder.append(realmGet$playername() != null ? realmGet$playername() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{point:");
        stringBuilder.append(realmGet$point());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{hint:");
        stringBuilder.append(realmGet$hint());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{grade:");
        stringBuilder.append(realmGet$grade() != null ? realmGet$grade() : "null");
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
        InventoryRealmProxy aInventory = (InventoryRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aInventory.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aInventory.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aInventory.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }

}
