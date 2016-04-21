package com.theleafapps.shopnick.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.theleafapps.shopnick.R;
import com.theleafapps.shopnick.models.UserRecord;
import com.theleafapps.shopnick.models.UserRecords;
import com.theleafapps.shopnick.utils.AppConstants;
import com.theleafapps.shopnick.utils.PrefUtil;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import dfapi.ApiException;
import dfapi.ApiInvoker;
import dfapi.BaseAsyncRequest;

public class DataListActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener{

    private GetUserRecordsTask getUserRecordsTask;
    ArrayList<Integer> idList;
    static ArrayAdapter<String> arrayAdapter;
    static ListView listView;
    List<String> data;
    static List<UserRecord> userRecordListRec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_list);
        idList       = new ArrayList<>();
        data         = new ArrayList<>();
        listView     = (ListView) findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,data);

        GetUserRecordsTask getUserRecordsTask = new GetUserRecordsTask(1);
        getUserRecordsTask.execute();

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,DataActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_data_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.webContent:
                Intent intent = new Intent(this,WebActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }


    public class GetUserRecordsTask extends BaseAsyncRequest {
        private int userRecordId;
        UserRecords userRecordsReceived;

        public GetUserRecordsTask(int id){
            userRecordId = id;
        }

        @Override
        protected void doSetup() throws ApiException, JSONException {
            callerName = "getUserRecords";

            serviceName = AppConstants.DB_SVC;
            endPoint = "user_record";
            verb = "GET";

            // filter to only select the contacts in this group
//            queryParams = new HashMap<>();
//            queryParams.put("filter", "id=" + userRecordId);

            // request without related would return just {id, contact_group_id, contact_id}
            // set the related field to go get the contact mRecordsList referenced by
            // each contact_group_relationship record
            // queryParams.put("related", "contact_by_contact_id");

            // need to include the API key and session token
            applicationApiKey = AppConstants.API_KEY;
            sessionToken = PrefUtil.getString(getApplicationContext(), AppConstants.SESSION_TOKEN);
        }

        @Override
        protected void processResponse(String response) throws ApiException, JSONException {
            userRecordsReceived =
                    (UserRecords) ApiInvoker.deserialize(response, "", UserRecords.class);
        }

        @Override
        protected void onCompletion(boolean success) {
            getUserRecordsTask = null;
            if(success && userRecordsReceived != null && userRecordsReceived.userRecord.size() > 0){
                userRecordListRec = userRecordsReceived.userRecord;
                for(UserRecord record : userRecordListRec){
                    data.add(record.first_name + " " + record.last_name);
                    idList.add(record.id);
                }

                arrayAdapter.notifyDataSetChanged();

            }
        }
    }
}
