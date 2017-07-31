package io.realm;


import android.util.JsonReader;
import io.realm.RealmObjectSchema;
import io.realm.internal.ColumnInfo;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.RealmProxyMediator;
import io.realm.internal.Row;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

@io.realm.annotations.RealmModule
class DefaultRealmModuleMediator extends RealmProxyMediator {

    private static final Set<Class<? extends RealmModel>> MODEL_CLASSES;
    static {
        Set<Class<? extends RealmModel>> modelClasses = new HashSet<Class<? extends RealmModel>>();
        modelClasses.add(com.szb.ARMODULE.model.database.Quest.class);
        modelClasses.add(com.szb.ARMODULE.model.database.Inventory.class);
        modelClasses.add(com.szb.ARMODULE.model.database.Player.class);
        MODEL_CLASSES = Collections.unmodifiableSet(modelClasses);
    }

    @Override
    public Table createTable(Class<? extends RealmModel> clazz, SharedRealm sharedRealm) {
        checkClass(clazz);

        if (clazz.equals(com.szb.ARMODULE.model.database.Quest.class)) {
            return io.realm.QuestRealmProxy.initTable(sharedRealm);
        } else if (clazz.equals(com.szb.ARMODULE.model.database.Inventory.class)) {
            return io.realm.InventoryRealmProxy.initTable(sharedRealm);
        } else if (clazz.equals(com.szb.ARMODULE.model.database.Player.class)) {
            return io.realm.PlayerRealmProxy.initTable(sharedRealm);
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public RealmObjectSchema createRealmObjectSchema(Class<? extends RealmModel> clazz, RealmSchema realmSchema) {
        checkClass(clazz);

        if (clazz.equals(com.szb.ARMODULE.model.database.Quest.class)) {
            return io.realm.QuestRealmProxy.createRealmObjectSchema(realmSchema);
        } else if (clazz.equals(com.szb.ARMODULE.model.database.Inventory.class)) {
            return io.realm.InventoryRealmProxy.createRealmObjectSchema(realmSchema);
        } else if (clazz.equals(com.szb.ARMODULE.model.database.Player.class)) {
            return io.realm.PlayerRealmProxy.createRealmObjectSchema(realmSchema);
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public ColumnInfo validateTable(Class<? extends RealmModel> clazz, SharedRealm sharedRealm, boolean allowExtraColumns) {
        checkClass(clazz);

        if (clazz.equals(com.szb.ARMODULE.model.database.Quest.class)) {
            return io.realm.QuestRealmProxy.validateTable(sharedRealm, allowExtraColumns);
        } else if (clazz.equals(com.szb.ARMODULE.model.database.Inventory.class)) {
            return io.realm.InventoryRealmProxy.validateTable(sharedRealm, allowExtraColumns);
        } else if (clazz.equals(com.szb.ARMODULE.model.database.Player.class)) {
            return io.realm.PlayerRealmProxy.validateTable(sharedRealm, allowExtraColumns);
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public List<String> getFieldNames(Class<? extends RealmModel> clazz) {
        checkClass(clazz);

        if (clazz.equals(com.szb.ARMODULE.model.database.Quest.class)) {
            return io.realm.QuestRealmProxy.getFieldNames();
        } else if (clazz.equals(com.szb.ARMODULE.model.database.Inventory.class)) {
            return io.realm.InventoryRealmProxy.getFieldNames();
        } else if (clazz.equals(com.szb.ARMODULE.model.database.Player.class)) {
            return io.realm.PlayerRealmProxy.getFieldNames();
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public String getTableName(Class<? extends RealmModel> clazz) {
        checkClass(clazz);

        if (clazz.equals(com.szb.ARMODULE.model.database.Quest.class)) {
            return io.realm.QuestRealmProxy.getTableName();
        } else if (clazz.equals(com.szb.ARMODULE.model.database.Inventory.class)) {
            return io.realm.InventoryRealmProxy.getTableName();
        } else if (clazz.equals(com.szb.ARMODULE.model.database.Player.class)) {
            return io.realm.PlayerRealmProxy.getTableName();
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public <E extends RealmModel> E newInstance(Class<E> clazz, Object baseRealm, Row row, ColumnInfo columnInfo, boolean acceptDefaultValue, List<String> excludeFields) {
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        try {
            objectContext.set((BaseRealm) baseRealm, row, columnInfo, acceptDefaultValue, excludeFields);
            checkClass(clazz);

            if (clazz.equals(com.szb.ARMODULE.model.database.Quest.class)) {
                return clazz.cast(new io.realm.QuestRealmProxy());
            } else if (clazz.equals(com.szb.ARMODULE.model.database.Inventory.class)) {
                return clazz.cast(new io.realm.InventoryRealmProxy());
            } else if (clazz.equals(com.szb.ARMODULE.model.database.Player.class)) {
                return clazz.cast(new io.realm.PlayerRealmProxy());
            } else {
                throw getMissingProxyClassException(clazz);
            }
        } finally {
            objectContext.clear();
        }
    }

    @Override
    public Set<Class<? extends RealmModel>> getModelClasses() {
        return MODEL_CLASSES;
    }

    @Override
    public <E extends RealmModel> E copyOrUpdate(Realm realm, E obj, boolean update, Map<RealmModel, RealmObjectProxy> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<E> clazz = (Class<E>) ((obj instanceof RealmObjectProxy) ? obj.getClass().getSuperclass() : obj.getClass());

        if (clazz.equals(com.szb.ARMODULE.model.database.Quest.class)) {
            return clazz.cast(io.realm.QuestRealmProxy.copyOrUpdate(realm, (com.szb.ARMODULE.model.database.Quest) obj, update, cache));
        } else if (clazz.equals(com.szb.ARMODULE.model.database.Inventory.class)) {
            return clazz.cast(io.realm.InventoryRealmProxy.copyOrUpdate(realm, (com.szb.ARMODULE.model.database.Inventory) obj, update, cache));
        } else if (clazz.equals(com.szb.ARMODULE.model.database.Player.class)) {
            return clazz.cast(io.realm.PlayerRealmProxy.copyOrUpdate(realm, (com.szb.ARMODULE.model.database.Player) obj, update, cache));
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public void insert(Realm realm, RealmModel object, Map<RealmModel, Long> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((object instanceof RealmObjectProxy) ? object.getClass().getSuperclass() : object.getClass());

        if (clazz.equals(com.szb.ARMODULE.model.database.Quest.class)) {
            io.realm.QuestRealmProxy.insert(realm, (com.szb.ARMODULE.model.database.Quest) object, cache);
        } else if (clazz.equals(com.szb.ARMODULE.model.database.Inventory.class)) {
            io.realm.InventoryRealmProxy.insert(realm, (com.szb.ARMODULE.model.database.Inventory) object, cache);
        } else if (clazz.equals(com.szb.ARMODULE.model.database.Player.class)) {
            io.realm.PlayerRealmProxy.insert(realm, (com.szb.ARMODULE.model.database.Player) object, cache);
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public void insert(Realm realm, Collection<? extends RealmModel> objects) {
        Iterator<? extends RealmModel> iterator = objects.iterator();
        RealmModel object = null;
        Map<RealmModel, Long> cache = new HashMap<RealmModel, Long>(objects.size());
        if (iterator.hasNext()) {
            //  access the first element to figure out the clazz for the routing below
            object = iterator.next();
            // This cast is correct because obj is either
            // generated by RealmProxy or the original type extending directly from RealmObject
            @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((object instanceof RealmObjectProxy) ? object.getClass().getSuperclass() : object.getClass());

            if (clazz.equals(com.szb.ARMODULE.model.database.Quest.class)) {
                io.realm.QuestRealmProxy.insert(realm, (com.szb.ARMODULE.model.database.Quest) object, cache);
            } else if (clazz.equals(com.szb.ARMODULE.model.database.Inventory.class)) {
                io.realm.InventoryRealmProxy.insert(realm, (com.szb.ARMODULE.model.database.Inventory) object, cache);
            } else if (clazz.equals(com.szb.ARMODULE.model.database.Player.class)) {
                io.realm.PlayerRealmProxy.insert(realm, (com.szb.ARMODULE.model.database.Player) object, cache);
            } else {
                throw getMissingProxyClassException(clazz);
            }
            if (iterator.hasNext()) {
                if (clazz.equals(com.szb.ARMODULE.model.database.Quest.class)) {
                    io.realm.QuestRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.szb.ARMODULE.model.database.Inventory.class)) {
                    io.realm.InventoryRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.szb.ARMODULE.model.database.Player.class)) {
                    io.realm.PlayerRealmProxy.insert(realm, iterator, cache);
                } else {
                    throw getMissingProxyClassException(clazz);
                }
            }
        }
    }

    @Override
    public void insertOrUpdate(Realm realm, RealmModel obj, Map<RealmModel, Long> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((obj instanceof RealmObjectProxy) ? obj.getClass().getSuperclass() : obj.getClass());

        if (clazz.equals(com.szb.ARMODULE.model.database.Quest.class)) {
            io.realm.QuestRealmProxy.insertOrUpdate(realm, (com.szb.ARMODULE.model.database.Quest) obj, cache);
        } else if (clazz.equals(com.szb.ARMODULE.model.database.Inventory.class)) {
            io.realm.InventoryRealmProxy.insertOrUpdate(realm, (com.szb.ARMODULE.model.database.Inventory) obj, cache);
        } else if (clazz.equals(com.szb.ARMODULE.model.database.Player.class)) {
            io.realm.PlayerRealmProxy.insertOrUpdate(realm, (com.szb.ARMODULE.model.database.Player) obj, cache);
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public void insertOrUpdate(Realm realm, Collection<? extends RealmModel> objects) {
        Iterator<? extends RealmModel> iterator = objects.iterator();
        RealmModel object = null;
        Map<RealmModel, Long> cache = new HashMap<RealmModel, Long>(objects.size());
        if (iterator.hasNext()) {
            //  access the first element to figure out the clazz for the routing below
            object = iterator.next();
            // This cast is correct because obj is either
            // generated by RealmProxy or the original type extending directly from RealmObject
            @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((object instanceof RealmObjectProxy) ? object.getClass().getSuperclass() : object.getClass());

            if (clazz.equals(com.szb.ARMODULE.model.database.Quest.class)) {
                io.realm.QuestRealmProxy.insertOrUpdate(realm, (com.szb.ARMODULE.model.database.Quest) object, cache);
            } else if (clazz.equals(com.szb.ARMODULE.model.database.Inventory.class)) {
                io.realm.InventoryRealmProxy.insertOrUpdate(realm, (com.szb.ARMODULE.model.database.Inventory) object, cache);
            } else if (clazz.equals(com.szb.ARMODULE.model.database.Player.class)) {
                io.realm.PlayerRealmProxy.insertOrUpdate(realm, (com.szb.ARMODULE.model.database.Player) object, cache);
            } else {
                throw getMissingProxyClassException(clazz);
            }
            if (iterator.hasNext()) {
                if (clazz.equals(com.szb.ARMODULE.model.database.Quest.class)) {
                    io.realm.QuestRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.szb.ARMODULE.model.database.Inventory.class)) {
                    io.realm.InventoryRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.szb.ARMODULE.model.database.Player.class)) {
                    io.realm.PlayerRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else {
                    throw getMissingProxyClassException(clazz);
                }
            }
        }
    }

    @Override
    public <E extends RealmModel> E createOrUpdateUsingJsonObject(Class<E> clazz, Realm realm, JSONObject json, boolean update)
        throws JSONException {
        checkClass(clazz);

        if (clazz.equals(com.szb.ARMODULE.model.database.Quest.class)) {
            return clazz.cast(io.realm.QuestRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        } else if (clazz.equals(com.szb.ARMODULE.model.database.Inventory.class)) {
            return clazz.cast(io.realm.InventoryRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        } else if (clazz.equals(com.szb.ARMODULE.model.database.Player.class)) {
            return clazz.cast(io.realm.PlayerRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public <E extends RealmModel> E createUsingJsonStream(Class<E> clazz, Realm realm, JsonReader reader)
        throws IOException {
        checkClass(clazz);

        if (clazz.equals(com.szb.ARMODULE.model.database.Quest.class)) {
            return clazz.cast(io.realm.QuestRealmProxy.createUsingJsonStream(realm, reader));
        } else if (clazz.equals(com.szb.ARMODULE.model.database.Inventory.class)) {
            return clazz.cast(io.realm.InventoryRealmProxy.createUsingJsonStream(realm, reader));
        } else if (clazz.equals(com.szb.ARMODULE.model.database.Player.class)) {
            return clazz.cast(io.realm.PlayerRealmProxy.createUsingJsonStream(realm, reader));
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public <E extends RealmModel> E createDetachedCopy(E realmObject, int maxDepth, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<E> clazz = (Class<E>) realmObject.getClass().getSuperclass();

        if (clazz.equals(com.szb.ARMODULE.model.database.Quest.class)) {
            return clazz.cast(io.realm.QuestRealmProxy.createDetachedCopy((com.szb.ARMODULE.model.database.Quest) realmObject, 0, maxDepth, cache));
        } else if (clazz.equals(com.szb.ARMODULE.model.database.Inventory.class)) {
            return clazz.cast(io.realm.InventoryRealmProxy.createDetachedCopy((com.szb.ARMODULE.model.database.Inventory) realmObject, 0, maxDepth, cache));
        } else if (clazz.equals(com.szb.ARMODULE.model.database.Player.class)) {
            return clazz.cast(io.realm.PlayerRealmProxy.createDetachedCopy((com.szb.ARMODULE.model.database.Player) realmObject, 0, maxDepth, cache));
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

}
