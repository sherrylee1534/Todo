package com.example.todo.ui.viewmods;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.adapter.UserModulesAdapter;
import com.example.todo.models.Module;
import com.example.todo.ui.addmods.AddModsActivity;
import com.example.todo.R;
import com.example.todo.adapter.ModsAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ViewModsFragment extends Fragment {

    // TODO: This one too
    private ViewModsViewModel viewModsViewModel;
    private RecyclerView viewModsRecyclerView;
    private UserModulesAdapter viewModsAdapter;
    FloatingActionButton toAddModsActionButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        viewModsViewModel =
                ViewModelProviders.of(this).get(ViewModsViewModel.class);
        viewModsViewModel.setContext(getActivity());

        View root = inflater.inflate(R.layout.fragment_viewmods, container, false);

        viewModsRecyclerView = root.findViewById(R.id.recycler_view);
        addTouchHelper();

        viewModsAdapter = new UserModulesAdapter();

        toAddModsActionButton = root.findViewById(R.id.fab);

        toAddModsActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddModsActivity.class);
                startActivity(intent);

                }
            }
        );

        viewModsViewModel.getModules().observe(getViewLifecycleOwner(), new Observer<List<Module>>() {
            @Override
            public void onChanged(List<Module> modules) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(RecyclerView.VERTICAL);

                viewModsAdapter.setUserModules(modules);

                viewModsRecyclerView.setLayoutManager(layoutManager);
                viewModsRecyclerView.setAdapter(viewModsAdapter);
            }
        });

        return root;
    }

    private void addTouchHelper() {

        // Swipe left to delete
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                int deletedModId = viewModsAdapter.removeModule(position);

                viewModsViewModel.removeModule(deletedModId);
            }
        };

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(viewModsRecyclerView);
    }

}

