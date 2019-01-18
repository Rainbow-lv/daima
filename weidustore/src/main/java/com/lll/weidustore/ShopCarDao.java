package com.lll.weidustore;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.lll.weidustore.bean.ShopCar;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SHOP_CAR".
*/
public class ShopCarDao extends AbstractDao<ShopCar, Long> {

    public static final String TABLENAME = "SHOP_CAR";

    /**
     * Properties of entity ShopCar.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "_id");
        public final static Property CommodityId = new Property(1, int.class, "commodityId", false, "COMMODITY_ID");
        public final static Property CommodityName = new Property(2, String.class, "commodityName", false, "COMMODITY_NAME");
        public final static Property Count = new Property(3, int.class, "count", false, "COUNT");
        public final static Property Pic = new Property(4, String.class, "pic", false, "PIC");
        public final static Property Price = new Property(5, double.class, "price", false, "PRICE");
        public final static Property Check = new Property(6, boolean.class, "check", false, "CHECK");
        public final static Property Selected = new Property(7, int.class, "selected", false, "SELECTED");
    }


    public ShopCarDao(DaoConfig config) {
        super(config);
    }
    
    public ShopCarDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SHOP_CAR\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," + // 0: id
                "\"COMMODITY_ID\" INTEGER NOT NULL ," + // 1: commodityId
                "\"COMMODITY_NAME\" TEXT," + // 2: commodityName
                "\"COUNT\" INTEGER NOT NULL ," + // 3: count
                "\"PIC\" TEXT," + // 4: pic
                "\"PRICE\" REAL NOT NULL ," + // 5: price
                "\"CHECK\" INTEGER NOT NULL ," + // 6: check
                "\"SELECTED\" INTEGER NOT NULL );"); // 7: selected
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SHOP_CAR\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ShopCar entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
        stmt.bindLong(2, entity.getCommodityId());
 
        String commodityName = entity.getCommodityName();
        if (commodityName != null) {
            stmt.bindString(3, commodityName);
        }
        stmt.bindLong(4, entity.getCount());
 
        String pic = entity.getPic();
        if (pic != null) {
            stmt.bindString(5, pic);
        }
        stmt.bindDouble(6, entity.getPrice());
        stmt.bindLong(7, entity.getCheck() ? 1L: 0L);
        stmt.bindLong(8, entity.getSelected());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ShopCar entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
        stmt.bindLong(2, entity.getCommodityId());
 
        String commodityName = entity.getCommodityName();
        if (commodityName != null) {
            stmt.bindString(3, commodityName);
        }
        stmt.bindLong(4, entity.getCount());
 
        String pic = entity.getPic();
        if (pic != null) {
            stmt.bindString(5, pic);
        }
        stmt.bindDouble(6, entity.getPrice());
        stmt.bindLong(7, entity.getCheck() ? 1L: 0L);
        stmt.bindLong(8, entity.getSelected());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public ShopCar readEntity(Cursor cursor, int offset) {
        ShopCar entity = new ShopCar( //
            cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // commodityId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // commodityName
            cursor.getInt(offset + 3), // count
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // pic
            cursor.getDouble(offset + 5), // price
            cursor.getShort(offset + 6) != 0, // check
            cursor.getInt(offset + 7) // selected
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ShopCar entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setCommodityId(cursor.getInt(offset + 1));
        entity.setCommodityName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCount(cursor.getInt(offset + 3));
        entity.setPic(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setPrice(cursor.getDouble(offset + 5));
        entity.setCheck(cursor.getShort(offset + 6) != 0);
        entity.setSelected(cursor.getInt(offset + 7));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(ShopCar entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(ShopCar entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ShopCar entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
