package com.example.androidapp.ui.comments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidapp.JSONParser;
import com.example.androidapp.R;
import com.example.androidapp.ui.locations.LocationsViewModel;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CommentsFragment extends Fragment {
    private CommentsViewModel commentsViewModel;
    private Dialog commentDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        commentsViewModel =
                new ViewModelProvider(requireActivity()).get(CommentsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_location_comments, container, false);
        commentDialog = new Dialog(requireContext());
        Button addCommentButton = root.findViewById(R.id.addCommentButton);
        addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(root);
            }
        });
        commentsViewModel.buildRecyclerView(root.findViewById(R.id.commentsView), root.getContext());
        commentsViewModel.showComments();
        return root;
    }

    private void showPopup(View v){
        commentDialog.setContentView(R.layout.add_comment_layout);
        Button acceptButton = (Button) commentDialog.findViewById(R.id.acceptCommentButton);
        EditText commentText = commentDialog.findViewById(R.id.addCommentText);

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = requireActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                commentsViewModel.addComment(new CommentItem(sp.getString("login", ""), commentText.getText().toString(), Calendar.getInstance().getTime().toString()));
                commentsViewModel.showComments();

                LocationsViewModel locationsViewModel =
                        new ViewModelProvider(requireActivity()).get(LocationsViewModel.class);

                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("id_u", sp.getString("id", "")));
                params.add(new BasicNameValuePair("id_l", String.valueOf(locationsViewModel.getLocationId())));
                params.add(new BasicNameValuePair("text", commentText.getText().toString()));
                //params.add(new BasicNameValuePair("date", Calendar.getInstance().getTime().toString()));
                new UpdateCommentsList().execute(params);
                commentDialog.dismiss();
            }
        });
        commentDialog.show();
    }

    @SuppressLint("StaticFieldLeak")
    class UpdateCommentsList extends AsyncTask<List<NameValuePair>, Void, JSONObject> {
        private final String link = getString(R.string.server_address) + "updateCommentsList.php";

        @Override
        protected void onPreExecute() { super.onPreExecute(); }
        @SafeVarargs
        @Override
        protected final JSONObject doInBackground(List<NameValuePair>... args) {
            return new JSONParser().makeHttpRequest(link, "POST", args[0]);
        }
        @Override
        protected void onPostExecute(JSONObject feedback) {
            try{
                if(feedback.getInt("success") != 1) {
                    Toast.makeText(requireContext(), R.string.locationFragment_errorOnServerUpdate, Toast.LENGTH_SHORT).show();
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
