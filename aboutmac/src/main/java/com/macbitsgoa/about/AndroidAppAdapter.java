package com.macbitsgoa.about;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.macbitsgoa.about.models.AndroidApp;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;

/**
 * @author Rushikesh Jogdand.
 */
public class AndroidAppAdapter extends RecyclerView.Adapter<AndroidAppVh> {
    private final Realm realm = Realm.getDefaultInstance();
    private final Browser browser;
    private final List<AndroidApp> apps = new ArrayList<>(0);

    public AndroidAppAdapter(final Browser browser) {
        getData();
        this.browser = browser;
    }

    private void getData() {
        realm.where(AndroidApp.class).findAllAsync().addChangeListener(androidApps -> {
            apps.clear();
            for (final AndroidApp app : androidApps) {
                apps.add(realm.copyFromRealm(app));
            }
        });
    }

    @NonNull
    @Override
    public AndroidAppVh onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        return new AndroidAppVh(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.vh_android_app, parent, false),
                browser
        );
    }

    @Override
    public void onBindViewHolder(@NonNull final AndroidAppVh holder, final int position) {
        holder.populate(apps.get(position));
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }
}
