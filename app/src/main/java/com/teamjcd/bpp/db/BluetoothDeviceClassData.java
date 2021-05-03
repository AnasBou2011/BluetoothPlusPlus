package com.teamjcd.bpp.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import static com.teamjcd.bpp.db.BluetoothDeviceClassContentProvider.DEFAULT_ID;
import static com.teamjcd.bpp.db.BluetoothDeviceClassDatabaseHelper.DEVICE_CLASS_NAME;
import static com.teamjcd.bpp.db.BluetoothDeviceClassDatabaseHelper.DEVICE_CLASS_NAME_INDEX;
import static com.teamjcd.bpp.db.BluetoothDeviceClassDatabaseHelper.DEVICE_CLASS_VALUE;
import static com.teamjcd.bpp.db.BluetoothDeviceClassDatabaseHelper.DEVICE_CLASS_VALUE_INDEX;
import static com.teamjcd.bpp.db.BluetoothDeviceClassDatabaseHelper.ID_INDEX;

public class BluetoothDeviceClassData implements BaseColumns {
    private int id;
    private String name;
    private int deviceClass;

    public BluetoothDeviceClassData(String name, int deviceClass) {
        this.name = name;
        this.deviceClass = deviceClass;
    }

    public BluetoothDeviceClassData(int id, String name, int deviceClass) {
        this(name, deviceClass);
        setId(id);
    }

    public BluetoothDeviceClassData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDeviceClass() {
        return deviceClass;
    }

    public void setDeviceClass(int deviceClass) {
        this.deviceClass = deviceClass;
    }

    public boolean isUserEditable() {
        return id != DEFAULT_ID;
    }

    public static BluetoothDeviceClassData readFromCursor(Cursor cursor) {
        return new BluetoothDeviceClassData(
                cursor.getInt(ID_INDEX),
                cursor.getString(DEVICE_CLASS_NAME_INDEX),
                cursor.getInt(DEVICE_CLASS_VALUE_INDEX));
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(DEVICE_CLASS_NAME, name);
        values.put(DEVICE_CLASS_VALUE, deviceClass);
        return values;
    }
}
