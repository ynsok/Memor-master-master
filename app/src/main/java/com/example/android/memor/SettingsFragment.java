package com.example.android.memor;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.SwitchPreferenceCompat;

import com.example.android.memor.services.MemorUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private void setSummary(Preference preference, Object value) {
        String summaty = value.toString();

        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(summaty);
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        }
    }

    private void hidden(Preference preference) {
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            listPreference.setEnabled(false);
            listPreference.setSummary("");
        }
    }

    private void show(Preference preference){
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            listPreference.setEnabled(true);
        }
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_screen);

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        Preference sw = findPreference(getString(R.string.Show_Notification_Key));
        SwitchPreferenceCompat switchPreferenceCompat = (SwitchPreferenceCompat) sw;
        int count = preferenceScreen.getPreferenceCount();
        for (int i = 0; i < count; i++) {
            Preference preference = preferenceScreen.getPreference(i);

            if (!(preference instanceof SwitchPreferenceCompat)) {

                if (switchPreferenceCompat.isChecked()) {
                    String summary = sharedPreferences.getString(preference.getKey(), "");
                    setSummary(preference, summary);
                } else {
                    hidden(preference);
                }
            }
        }


    }

    @Override
    public void onStart() {
        super.onStart();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onStop() {
        super.onStop();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Activity activity = getActivity();

                Preference preference = findPreference(key);
            if(key.equals(getString(R.string.List_Preference_Key)))
            {
                String changedValue = sharedPreferences.getString(key,"");
                setSummary(preference,changedValue);

                    ListPreference listPreference = (ListPreference)preference;
                   int valueReturned = listPreference.findIndexOfValue(changedValue);
                   String ch = String.valueOf(listPreference.getEntryValues()[valueReturned]);
                     int typeINT = Integer.parseInt(ch);
                     MemorUtils.scheduleFetchData(activity,typeINT);

            }
            else if (key.equals(getString(R.string.Show_Notification_Key)))
            {
                SwitchPreferenceCompat switchPreferenceCompat = (SwitchPreferenceCompat)preference;
                Preference list = findPreference(getString(R.string.List_Preference_Key));
                if (switchPreferenceCompat.isChecked()) {

                    show(list);
                }else {
                    hidden(list);
                    MemorUtils.stopJob(activity);
                }
            }

          }

    }

