package com.teamjcd.bpp;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import static com.teamjcd.bpp.BluetoothDeviceClassEditor.URI_EXTRA;
import static com.teamjcd.bpp.BluetoothDeviceClassSettings.ACTION_BLUETOOTH_DEVICE_CLASS_EDIT;
import static com.teamjcd.bpp.db.BluetoothDeviceClassContentProvider.DEVICE_CLASS_URI;

public class BluetoothDeviceClassPreference extends Preference implements CompoundButton.OnCheckedChangeListener {
    private final static String TAG = "BluetoothDeviceClassPreference";

    private static String mSelectedKey = null;
    private static CompoundButton mCurrentChecked = null;
    private boolean mProtectFromCheckedChange = false;
    private boolean mSelectable = true;
    private boolean mHideDetails = false;

    public BluetoothDeviceClassPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public BluetoothDeviceClassPreference(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.bluetoothDeviceClassPreferenceStyle);
        setWidgetLayoutResource(R.layout.bluetooth_device_class_preference_layout);
    }

    public BluetoothDeviceClassPreference(Context context) {
        this(context, null);
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder view) {
        super.onBindViewHolder(view);

        View widget = view.findViewById(R.id.bluetooth_device_class_radiobutton);
        if ((widget != null) && widget instanceof RadioButton) {
            RadioButton rb = (RadioButton) widget;
            if (mSelectable) {
                rb.setOnCheckedChangeListener(this);

                boolean isChecked = getKey().equals(mSelectedKey);
                if (isChecked) {
                    mCurrentChecked = rb;
                    mSelectedKey = getKey();
                }

                mProtectFromCheckedChange = true;
                rb.setChecked(isChecked);
                mProtectFromCheckedChange = false;
                rb.setVisibility(View.VISIBLE);
            } else {
                rb.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onClick() {
        super.onClick();
        Context context = getContext();
        if (context != null) {
            if (mHideDetails) {
                Toast.makeText(context, context.getString(
                        R.string.cannot_change_bluetooth_device_class_toast), Toast.LENGTH_LONG).show();
                return;
            }

            int pos = Integer.parseInt(getKey());
            Uri url = ContentUris.withAppendedId(DEVICE_CLASS_URI, pos);
            Intent editIntent = new Intent(getContext(), BluetoothDeviceClassEditorActivity.class);
            editIntent.setAction(ACTION_BLUETOOTH_DEVICE_CLASS_EDIT);
            editIntent.putExtra(URI_EXTRA, url);
            context.startActivity(editIntent);
        }
    }

    public boolean isChecked() {
        return getKey().equals(mSelectedKey);
    }

    public void setChecked() {
        mSelectedKey = getKey();
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Log.i(TAG, "ID: " + getKey() + " :" + isChecked);
        if (mProtectFromCheckedChange) {
            return;
        }

        if (isChecked) {
            if (mCurrentChecked != null) {
                mCurrentChecked.setChecked(false);
            }
            mCurrentChecked = buttonView;
            mSelectedKey = getKey();
            callChangeListener(mSelectedKey);
        } else {
            mCurrentChecked = null;
            mSelectedKey = null;
        }
    }

    public void setSelectable(boolean selectable) {
        mSelectable = selectable;
    }

    public boolean getSelectable() {
        return mSelectable;
    }

    public void setHideDetails() {
        mHideDetails = true;
    }
}
