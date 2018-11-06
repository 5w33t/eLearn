package tk.tends2zero.elearnenglish;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class WritingComplaintLetter extends AppCompatActivity {
    private DatabaseReference mydb;
    private ListView myComplaintLetter;
    private ArrayList<String> ComplaintLetter = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing_complaint_letter);

        mydb = FirebaseDatabase.getInstance().getReference().child("complaintLetter");
        myComplaintLetter = (ListView) findViewById(R.id.listComplaint);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.notice_listview,ComplaintLetter);
        myComplaintLetter.setAdapter(arrayAdapter);
        mydb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String value = dataSnapshot.getKey();
                ComplaintLetter.add(value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        myComplaintLetter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = ComplaintLetter.get(position);
                Intent intent = new Intent(WritingComplaintLetter.this,ShowContent.class);
                Bundle bundle = new Bundle();
                bundle.putString("child","complaintLetter");
                bundle.putString("subchild",name);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


    }


    public void refresh(View view)
    {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }
}