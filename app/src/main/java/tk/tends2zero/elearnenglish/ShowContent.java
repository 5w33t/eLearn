package tk.tends2zero.elearnenglish;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShowContent extends AppCompatActivity {
    private DatabaseReference elearnDB;
    TextView contentName;
    TextView contentDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_content);
        contentName = (TextView) findViewById(R.id.contentName);
        contentDetails = (TextView) findViewById(R.id.contentBody);
        Bundle extras = getIntent().getExtras();
        String cat = extras.getString("child");
        String subcat = extras.getString("subchild");
        contentName.setText(subcat);

        elearnDB = FirebaseDatabase.getInstance().getReference().child(cat).child(subcat);


        elearnDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getValue(String.class);
                contentDetails.setText(value);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
       // Toast.makeText( ShowContent.this,name,Toast.LENGTH_SHORT).show();
    }
}
